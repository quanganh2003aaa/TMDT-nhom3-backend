package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.dto.ImgProductDTO;
import com.example.betmdtnhom3.dto.ProductDTO;
import com.example.betmdtnhom3.dto.ProductListDTO;
import com.example.betmdtnhom3.dto.SizeDTO;
import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.dto.request.UpdateProductRequest;
import com.example.betmdtnhom3.entity.*;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.ProductMapper;
import com.example.betmdtnhom3.responsitory.ImgProductReponsitory;
import com.example.betmdtnhom3.responsitory.ProductReponsitory;
import com.example.betmdtnhom3.responsitory.RateProductReponsitory;
import com.example.betmdtnhom3.responsitory.SizeReponsitory;
import com.example.betmdtnhom3.service.impl.FileServiceImpl;
import com.example.betmdtnhom3.service.impl.ProductServiceImpl;
import com.example.betmdtnhom3.utils.FileImgUtilsHelper;
import com.example.betmdtnhom3.utils.RateUtilsHelper;
import com.example.betmdtnhom3.utils.SizeUtilsHelper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceImpl {
    @Autowired
    ProductReponsitory productReponsitory;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    FileServiceImpl fileService;
    @Autowired
    SizeUtilsHelper sizeUtilsHelper;
    @Autowired
    SizeReponsitory sizeReponsitory;
    @Autowired
    ImgProductReponsitory imgProductReponsitory;
    @Autowired
    FileImgUtilsHelper fileImgUtilsHelper;
    @Autowired
    RateUtilsHelper rateUtilsHelper;
    @Autowired
    RateProductReponsitory rateProductReponsitory;

    @Override
    @Transactional
    public Boolean create(List<MultipartFile> files, CreateProductRequest createProductRequest) {
        boolean isSuccess = false;
        if (productReponsitory.existsById(createProductRequest.getId())) {
            throw new AppException(ErrorCode.PRODUCT_EXITED);
        }

        Product product = productMapper.toProductCreate(createProductRequest);

        try {
            productReponsitory.save(product);

            List<ImgProduct> imgProducts = new ArrayList<>();

            int indexImg = 0;
            for (MultipartFile file : files) {
                boolean isSaved = fileService.saveFile(file);
                if (!isSaved) {
                    throw new AppException(ErrorCode.FILE_UPLOAD_ERROR);
                }

                ImgProduct imgProduct = new ImgProduct();
                imgProduct.setProduct(product);
                imgProduct.setImg(file.getOriginalFilename());
                if (indexImg == 0) {
                    imgProduct.setIndexImg(0);
                    indexImg++;
                } else {
                    imgProduct.setIndexImg(1);
                }
                imgProducts.add(imgProduct);
            }

            imgProductReponsitory.saveAll(imgProducts);

            List<String> sizes = sizeUtilsHelper.parseSizes(createProductRequest.getSize());
            for (String size : sizes) {
                Size sizeEntity = new Size();
                sizeEntity.setSize(size);
                sizeEntity.setProduct(product);
                sizeReponsitory.save(sizeEntity);
            }

            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_CREATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    @Transactional
    public Boolean update(String idProduct, List<MultipartFile> files, UpdateProductRequest updateProductRequest) {
        boolean isSuccess = false;
        try {
            Product product = productReponsitory.findById(idProduct)
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            sizeUtilsHelper.updateSizes(product, updateProductRequest.getSize());
            fileImgUtilsHelper.updateProductImages(product, files);

            Category category = new Category();
            category.setId(updateProductRequest.getCategory());
            Brand brand = new Brand();
            brand.setId(updateProductRequest.getBrand());
            product.setBrand(brand);
            product.setCategory(category);
            product.setName(updateProductRequest.getName());
            product.setPrice(updateProductRequest.getPrice());
            product.setDescription(updateProductRequest.getDescription());
            product.setQuantity(updateProductRequest.getQuantity());

            productReponsitory.save(product);
            isSuccess = true;
        } catch (Exception e) {
            throw new AppException(ErrorCode.PRODUCT_UPDATE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public Boolean delete(String id) {
        boolean isSuccess = false;
        try {
            Product product = productReponsitory.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
            List<ImgProduct> imgProducts = imgProductReponsitory.findAllByProduct(product);
            for (ImgProduct img: imgProducts) {
                fileService.deleteFile(img.getImg());
            }
            imgProductReponsitory.deleteAll(imgProducts);
            productReponsitory.delete(product);
            isSuccess = true;
        } catch (Exception e){
            throw new AppException(ErrorCode.PRODUCT_DELETE_ERROR);
        }

        return isSuccess;
    }

    @Override
    public ProductDTO getById(String id) {
        Product products = productReponsitory.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductDTO productDTO = productMapper.toProductDTO(products);
        List<Size> sizesList = sizeReponsitory.findAllByProduct(products);

        if (sizesList.isEmpty()){
            productDTO.setSizeList(null);
        } else{
            List<String> sizeDTOList = new ArrayList<>();
            for (Size size:sizesList) {
                sizeDTOList.add(size.getSize());
            }
            productDTO.setSizeList(sizeDTOList);
        }

        List<ImgProduct> imgProductList = imgProductReponsitory.findAllByProduct(products);
        if (imgProductList.isEmpty()){
            productDTO.setImg(null);
        } else{
            List<ImgProductDTO> imgProductDTOS = new ArrayList<>();
            for (ImgProduct img:imgProductList) {
                ImgProductDTO imgProductDTO = new ImgProductDTO();
                imgProductDTO.setImg(img.getImg());
                imgProductDTO.setIndexImg(img.getIndexImg());
                imgProductDTOS.add(imgProductDTO);
            }
            productDTO.setImg(imgProductDTOS);
        }

        List<RateProduct> rateProductList = rateProductReponsitory.findAllByProduct(products);
        int totalRate = 0;
        for (RateProduct rateList:rateProductList) {
            totalRate += rateList.getRate();
        }
        Double rate =  (totalRate /(double) rateProductList.size());
        productDTO.setRate(rate);

        return productDTO;
    }

    @Override
    public List<ProductListDTO> getAllAdmin(String query, int select) {
        Pageable pageable = PageRequest.of(0, 12);
        Page<Product> productsList;
        switch (select) {
            case 0 -> productsList = productReponsitory.findByPartialIdProduct(query, pageable);
            case 1 -> productsList = productReponsitory.findByPartialIdProductAndCategory(query, 1, pageable);
            case 2 -> productsList = productReponsitory.findByPartialIdProductAndCategory(query, 2, pageable);
            case 3 -> productsList = productReponsitory.findByPartialIdProductAndCategory(query, 3, pageable);
            case 4 -> productsList = productReponsitory.findByPartialIdProductQuantityLessThan(query, pageable);
            case 5 -> productsList = productReponsitory.findByPartialIdProductOrderByPriceAsc(query, pageable);
            case 6 -> productsList = productReponsitory.findByPartialIdProductOrderByPriceDesc(query, pageable);
            default -> throw new IllegalArgumentException("Lỗi lựa chọn: " + select);
        }

        List<ProductListDTO> productDTOList = new ArrayList<>();
        for (Product product : productsList.getContent()) {
            ProductListDTO productDTO = productMapper.toProductListDTO(product);
            productDTO.setImg(product.getImgProducts().get(0).getImg());
            productDTO.setRate(rateUtilsHelper.getRate(product));
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    @Override
    public List<ProductListDTO> getIndex() {
        List<Product> randomProducts = productReponsitory.findRandomProducts();
        List<ProductListDTO> productDTOList = new ArrayList<>();
        for (Product product : randomProducts) {
            ProductListDTO productDTO = productMapper.toProductListDTO(product);
            productDTO.setImg(product.getImgProducts().get(0).getImg());
            productDTO.setRate(rateUtilsHelper.getRate(product));
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    @Override
    public PagenationDTO getProduct(int page, int filterSort, int filterPrice, String query) {
        Pageable pageable = PageRequest.of(page - 1, 12);
        PagenationDTO pagenationDTO = new PagenationDTO();
        Page<Product> productsPage = null;

        if (filterSort == 0){
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByPartialIdProductAndPriceBetween
                        (query, 0, 5000000, pageable);
                case 2 -> productReponsitory.findByPartialIdProductAndPriceBetween
                        (query, 5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByPartialIdProductAndPriceBetween
                        (query, 10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByPartialIdProductAndPriceBetween
                        (query, 20000000, 1000000000, pageable);
                default -> productReponsitory.findByPartialIdProduct(query, pageable);
            };
        } else if (filterSort == 1) {
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceAsc
                        (query, 0, 5000000, pageable);
                case 2 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceAsc
                        (query, 5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceAsc
                        (query ,10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceAsc
                        (query ,20000000, 1000000000, pageable);
                default -> productReponsitory.findByPartialIdProductOrderByPriceAsc(query, pageable);
            };
        } else {
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceDesc
                        (query,0, 5000000, pageable);
                case 2 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceDesc
                        (query, 5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceDesc
                        (query,10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByPartialIdProductAndPriceBetweenOrderByPriceDesc
                        (query,20000000, 1000000000, pageable);
                default -> productReponsitory.findByPartialIdProductOrderByPriceDesc(query, pageable);
            };
        }

        List<ProductListDTO> productDTOList = new ArrayList<>();
        for (Product products : productsPage) {
            ProductListDTO productDTO = productMapper.toProductListDTO(products);
            productDTO.setImg(products.getImgProducts().get(0).getImg());
            productDTO.setRate(rateUtilsHelper.getRate(products));
            productDTOList.add(productDTO);
        }
        pagenationDTO.setTotalPages(productsPage.getTotalPages());
        pagenationDTO.setObjectList(productDTOList);

        return pagenationDTO;
    }

    @Override
    public PagenationDTO getByCategory(int category, int page, int filterSort, int filterPrice) {
        Category categories = new Category();
        categories.setId(category);
        Pageable pageable = PageRequest.of(page - 1, 12);
        PagenationDTO pagenationDTO = new PagenationDTO();
        Page<Product> productsPage = null;

        if (filterSort == 0){
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByCategoryAndPriceBetween
                        (category, 0, 5000000, pageable);
                case 2 -> productReponsitory.findByCategoryAndPriceBetween
                        (category,5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByCategoryAndPriceBetween
                        (category,10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByCategoryAndPriceBetween
                        (category,20000000, 1000000000, pageable);
                default -> productReponsitory.findAllByCategory(categories, pageable);
            };
        } else if (filterSort == 1) {
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceAsc
                        (category, 0, 5000000, pageable);
                case 2 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceAsc
                        (category, 5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceAsc
                        (category, 10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceAsc
                        (category, 20000000, 1000000000, pageable);
                default -> productReponsitory.findByCategoryOrderByPriceAsc(category, pageable);
            };
        } else {
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceDesc
                        (category,0, 5000000, pageable);
                case 2 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceDesc
                        (category, 5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceDesc
                        (category,10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByCategoryAndPriceBetweenOrderByPriceDesc
                        (category,20000000, 1000000000, pageable);
                default -> productReponsitory.findByCategoryOrderByPriceDesc(category, pageable);
            };
        }

        List<ProductListDTO> productDTOList = new ArrayList<>();
        for (Product products : productsPage) {
            ProductListDTO productDTO = productMapper.toProductListDTO(products);
            productDTO.setImg(products.getImgProducts().get(0).getImg());
            productDTO.setRate(rateUtilsHelper.getRate(products));
            productDTOList.add(productDTO);
        }
        pagenationDTO.setTotalPages(productsPage.getTotalPages());
        pagenationDTO.setObjectList(productDTOList);

        return pagenationDTO;
    }

    @Override
    public PagenationDTO getByBrand(int brand, int page, int filterSort, int filterPrice) {
        Brand brandId = new Brand();
        brandId.setId(brand);
        Pageable pageable = PageRequest.of(page - 1, 12);
        PagenationDTO pagenationDTO = new PagenationDTO();
        Page<Product> productsPage = null;

        if (filterSort == 0){
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByBrandAndPriceBetween
                        (brand, 0, 5000000, pageable);
                case 2 -> productReponsitory.findByBrandAndPriceBetween
                        (brand,5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByBrandAndPriceBetween
                        (brand,10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByBrandAndPriceBetween
                        (brand,20000000, 1000000000, pageable);
                default -> productReponsitory.findAllByBrand(brandId, pageable);
            };
        } else if (filterSort == 1) {
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceAsc
                        (brand, 0, 5000000, pageable);
                case 2 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceAsc
                        (brand, 5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceAsc
                        (brand, 10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceAsc
                        (brand, 20000000, 1000000000, pageable);
                default -> productReponsitory.findByBrandOrderByPriceAsc(brand, pageable);
            };
        } else {
            productsPage = switch (filterPrice) {
                case 1 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceDesc
                        (brand,0, 5000000, pageable);
                case 2 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceDesc
                        (brand, 5000000, 10000000, pageable);
                case 3 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceDesc
                        (brand,10000000, 20000000, pageable);
                case 4 -> productReponsitory.findByBrandAndPriceBetweenOrderByPriceDesc
                        (brand,20000000, 1000000000, pageable);
                default -> productReponsitory.findByBrandOrderByPriceDesc(brand, pageable);
            };
        }

        List<ProductListDTO> productDTOList = new ArrayList<>();
        for (Product products : productsPage) {
            ProductListDTO productDTO = productMapper.toProductListDTO(products);
            productDTO.setImg(products.getImgProducts().get(0).getImg());
            productDTO.setRate(rateUtilsHelper.getRate(products));
            productDTOList.add(productDTO);
        }
        pagenationDTO.setTotalPages(productsPage.getTotalPages());
        pagenationDTO.setObjectList(productDTOList);

        return pagenationDTO;
    }
}

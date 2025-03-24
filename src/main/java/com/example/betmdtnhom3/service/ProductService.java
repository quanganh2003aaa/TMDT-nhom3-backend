package com.example.betmdtnhom3.service;

import com.example.betmdtnhom3.Enum.ErrorCode;
import com.example.betmdtnhom3.Enum.StatusProduct;
import com.example.betmdtnhom3.dto.ImgProductDTO;
import com.example.betmdtnhom3.dto.ProductDTO;
import com.example.betmdtnhom3.dto.ProductListDTO;
import com.example.betmdtnhom3.dto.request.CreateProductRequest;
import com.example.betmdtnhom3.dto.request.PagenationDTO;
import com.example.betmdtnhom3.dto.request.UpdateProductRequest;
import com.example.betmdtnhom3.entity.*;
import com.example.betmdtnhom3.exception.AppException;
import com.example.betmdtnhom3.mapper.BrandMapper;
import com.example.betmdtnhom3.mapper.CategoryMapper;
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
    BrandMapper brandMapper;
    @Autowired
    CategoryMapper categoryMapper;
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
        product.setStatusProduct(createProductRequest.getStatus()==0?StatusProduct.INACTIVE:StatusProduct.ACTIVE);

        try {
            productReponsitory.save(product);
            fileImgUtilsHelper.saveProductImages(files,product);

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
            product.setStatusProduct(updateProductRequest.getStatus()==0? StatusProduct.INACTIVE:StatusProduct.ACTIVE);

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
        productDTO.setBrand(brandMapper.toBrandDTO(products.getBrand()));
        productDTO.setCategory(categoryMapper.toCateDTO(products.getCategory()));
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
    public List<ProductListDTO> getAllAdmin(String query) {
        Pageable pageable = PageRequest.of(0, 12);
        Page<Product> productsList = productReponsitory.findByPartialIdProduct(query, pageable);
        List<ProductListDTO> productDTOList = new ArrayList<>();
        for (Product product : productsList.getContent()) {
            ProductListDTO productDTO = productMapper.toProductListDTO(product);
            productDTO.setImg(fileImgUtilsHelper.getFirstImage(product));
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
            productDTO.setImg(fileImgUtilsHelper.getFirstImage(product));
            productDTO.setRate(rateUtilsHelper.getRate(product));
            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    @Override
    public PagenationDTO getProduct(int page, int filterSort, int filterPrice, String query, int brandId, int categoryId) {
        Pageable pageable = PageRequest.of(page - 1, 12);
        PagenationDTO pagenationDTO = new PagenationDTO();

        int minPrice = 0, maxPrice = Integer.MAX_VALUE;
        switch (filterPrice) {
            case 1 -> maxPrice = 5_000_000;
            case 2 -> { minPrice = 5_000_000; maxPrice = 10_000_000; }
            case 3 -> { minPrice = 10_000_000; maxPrice = 20_000_000; }
            case 4 -> minPrice = 20_000_000;
        }

        Page<Product> productsPage;
        if (filterSort == 1) {
            productsPage = productReponsitory.findByFiltersOrderByPriceAsc(query, minPrice, maxPrice, brandId, categoryId, pageable);
        } else if (filterSort == 2) {
            productsPage = productReponsitory.findByFiltersOrderByPriceDesc(query, minPrice, maxPrice, brandId, categoryId, pageable);
        } else {
            productsPage = productReponsitory.findByFilters(query, minPrice, maxPrice, brandId, categoryId, pageable);
        }

        List<ProductListDTO> productDTOList = productsPage.stream()
                .map(product -> {
                    ProductListDTO productDTO = productMapper.toProductListDTO(product);
                    productDTO.setImg(fileImgUtilsHelper.getFirstImage(product));
                    productDTO.setRate(rateUtilsHelper.getRate(product));
                    return productDTO;
                })
                .collect(Collectors.toList());

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
            productDTO.setImg(fileImgUtilsHelper.getFirstImage(products));
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
            productDTO.setImg(fileImgUtilsHelper.getFirstImage(products));
            productDTO.setRate(rateUtilsHelper.getRate(products));
            productDTOList.add(productDTO);
        }
        pagenationDTO.setTotalPages(productsPage.getTotalPages());
        pagenationDTO.setObjectList(productDTOList);

        return pagenationDTO;
    }

    @Override
    public long countProducts() {
        return productReponsitory.count();
    }


}

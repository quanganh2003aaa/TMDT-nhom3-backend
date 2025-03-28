package com.example.betmdtnhom3.responsitory;

import com.example.betmdtnhom3.Enum.StatusProduct;
import com.example.betmdtnhom3.entity.Brand;
import com.example.betmdtnhom3.entity.Category;
import com.example.betmdtnhom3.entity.Product;
import com.example.betmdtnhom3.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReponsitory extends JpaRepository<Product, String> {
    Product findProductsById(String id);
    Optional<Product> findByIdAndStatusProduct(String id, StatusProduct statusProduct);
    @Query("SELECT p FROM products p " +
            "WHERE p.statusProduct = :statusProduct " +
            "AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> findByStatusAndKeyword(
            @Param("statusProduct") StatusProduct statusProduct,
            @Param("keyword") String keyword,
            Pageable pageable);

    @Query(value = "SELECT * FROM products WHERE category = 1 ORDER BY RAND() LIMIT 4", nativeQuery = true)
    List<Product> findRandomProducts();

    @Query("SELECT p FROM products p WHERE CAST(p.id AS string) LIKE %:partialId%")
    Page<Product> findByPartialIdProduct(@Param("partialId") String partialId, Pageable pageable);

    @Query("SELECT p FROM products p WHERE CAST(p.id AS string) LIKE %:partialId% AND p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPartialIdProductAndPriceBetween(@Param("partialId") String partialId, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

    @Query("SELECT p FROM products p WHERE CAST(p.id AS string) LIKE %:partialId% AND p.category.id = :category")
    Page<Product> findByPartialIdProductAndCategory(@Param("partialId") String partialId, @Param("category") int category, Pageable pageable);

    @Query("SELECT p FROM products p WHERE CAST(p.id AS string) LIKE %:partialId% ORDER BY p.price DESC")
    Page<Product> findByPartialIdProductOrderByPriceDesc(
            @Param("partialId") String partialId,
            Pageable pageable
    );

    @Query("SELECT p FROM products p WHERE CAST(p.id AS string) LIKE %:partialId% AND p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.price DESC")
    Page<Product> findByPartialIdProductAndPriceBetweenOrderByPriceDesc(
            @Param("partialId") String partialId,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    @Query("SELECT p FROM products p WHERE CAST(p.id AS string) LIKE %:partialId% ORDER BY p.price ASC")
    Page<Product> findByPartialIdProductOrderByPriceAsc(
            @Param("partialId") String partialId,
            Pageable pageable);

    @Query("SELECT p FROM products p WHERE CAST(p.id AS string) LIKE %:partialId% AND p.price BETWEEN :minPrice AND :maxPrice ORDER BY p.price ASC")
    Page<Product> findByPartialIdProductAndPriceBetweenOrderByPriceAsc(
            @Param("partialId") String partialId,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable);

    // Tìm theo brand và khoảng giá
    @Query("SELECT p FROM products p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.brand.id = :brand")
    Page<Product> findByBrandAndPriceBetween(
            @Param("brand") int brand,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    // Tìm theo category và khoảng giá
    @Query("SELECT p FROM products p WHERE p.price BETWEEN :minPrice AND :maxPrice AND p.category.id = :category")
    Page<Product> findByCategoryAndPriceBetween(
            @Param("category") int category,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    // Tìm theo brand, khoảng giá và sắp xếp giá giảm dần
    @Query("SELECT p FROM products p WHERE p.price BETWEEN :minPrice " +
            "AND :maxPrice AND p.brand.id = :brand ORDER BY p.price DESC")
    Page<Product> findByBrandAndPriceBetweenOrderByPriceDesc(
            @Param("brand") int brand,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    // Tìm theo category, khoảng giá và sắp xếp giá giảm dần
    @Query("SELECT p FROM products p WHERE p.price BETWEEN :minPrice " +
            "AND :maxPrice AND p.category.id = :category ORDER BY p.price DESC")
    Page<Product> findByCategoryAndPriceBetweenOrderByPriceDesc(
            @Param("category") int category,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    // Tìm theo brand và sắp xếp giá tăng dần
    @Query("SELECT p FROM products p WHERE p.brand.id = :brand ORDER BY p.price ASC")
    Page<Product> findByBrandOrderByPriceAsc(
            @Param("brand") int brand,
            Pageable pageable
    );

    // Tìm theo category và sắp xếp giá tăng dần
    @Query("SELECT p FROM products p WHERE p.category.id = :category ORDER BY p.price ASC")
    Page<Product> findByCategoryOrderByPriceAsc(
            @Param("category") int category,
            Pageable pageable
    );

    // Tìm theo brand và sắp xếp giá giảm dần
    @Query("SELECT p FROM products p WHERE p.brand.id = :brand ORDER BY p.price DESC")
    Page<Product> findByBrandOrderByPriceDesc(
            @Param("brand") int brand,
            Pageable pageable
    );

    // Tìm theo category và sắp xếp giá giảm dần
    @Query("SELECT p FROM products p WHERE p.category.id = :category ORDER BY p.price DESC")
    Page<Product> findByCategoryOrderByPriceDesc(
            @Param("category") int category,
            Pageable pageable
    );

    @Query("SELECT p FROM products p WHERE p.price BETWEEN :minPrice " +
            "AND :maxPrice AND p.brand.id = :brand ORDER BY p.price ASC")
    Page<Product> findByBrandAndPriceBetweenOrderByPriceAsc(
            @Param("brand") int brand,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    @Query("SELECT p FROM products p WHERE p.price BETWEEN :minPrice " +
            "AND :maxPrice AND p.category.id = :category ORDER BY p.price ASC")
    Page<Product> findByCategoryAndPriceBetweenOrderByPriceAsc(
            @Param("category") int category,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            Pageable pageable
    );

    Page<Product> findAllByCategory(Category category, Pageable pageable);
    Page<Product> findAllByBrand(Brand brand, Pageable pageable);
    @Query("SELECT p FROM products p JOIN sizes s ON p.id = s.product.id WHERE p.id = :id AND s.size = :size")
    Product findByIdAndSize(@Param("id") String id, @Param("size") String size);

    @Query("SELECT p FROM products p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR :query IS NULL) " +
            "AND (p.price BETWEEN :minPrice AND :maxPrice) " +
            "AND (:brandId = 0 OR p.brand.id = :brandId) " +
            "AND (:categoryId = 0 OR p.category.id = :categoryId)")
    Page<Product> findByFilters(@Param("query") String query,
                                @Param("minPrice") int minPrice,
                                @Param("maxPrice") int maxPrice,
                                @Param("brandId") int brandId,
                                @Param("categoryId") int categoryId,
                                Pageable pageable);

    @Query("SELECT p FROM products p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR :query IS NULL) " +
            "AND (p.price BETWEEN :minPrice AND :maxPrice) " +
            "AND (:brandId = 0 OR p.brand.id = :brandId) " +
            "AND (:categoryId = 0 OR p.category.id = :categoryId) " +
            "ORDER BY p.price ASC")
    Page<Product> findByFiltersOrderByPriceAsc(@Param("query") String query,
                                               @Param("minPrice") int minPrice,
                                               @Param("maxPrice") int maxPrice,
                                               @Param("brandId") int brandId,
                                               @Param("categoryId") int categoryId,
                                               Pageable pageable);

    @Query("SELECT p FROM products p WHERE " +
            "(LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR :query IS NULL) " +
            "AND (p.price BETWEEN :minPrice AND :maxPrice) " +
            "AND (:brandId = 0 OR p.brand.id = :brandId) " +
            "AND (:categoryId = 0 OR p.category.id = :categoryId) " +
            "ORDER BY p.price DESC")
    Page<Product> findByFiltersOrderByPriceDesc(@Param("query") String query,
                                                @Param("minPrice") int minPrice,
                                                @Param("maxPrice") int maxPrice,
                                                @Param("brandId") int brandId,
                                                @Param("categoryId") int categoryId,
                                                Pageable pageable);

}

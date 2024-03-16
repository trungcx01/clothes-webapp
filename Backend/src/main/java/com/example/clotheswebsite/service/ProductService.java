package com.example.clotheswebsite.service;

import com.example.clotheswebsite.dto.ProductDTO;
import com.example.clotheswebsite.entity.Product;
import com.example.clotheswebsite.entity.ProductSize;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> findTop8NewProduct();;
    List<Product> findTop8BestSellerProduct();;
    Page<Product> getAllProductsWithPagination(int page, int size);
    Product getProductById(long productId);
    Product addProduct(ProductDTO productDTO);
    Product updateProduct(ProductDTO productDTO, long productId);
    void deleteProduct(Long productId);
    List<Product> searchProduct(String keyword);

    ProductSize getProductSizeById(long productSizeId);

}

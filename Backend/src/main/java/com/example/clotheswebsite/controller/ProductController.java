package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.dto.ProductDTO;
import com.example.clotheswebsite.dto.ProductSizeDTO;
import com.example.clotheswebsite.entity.Product;
import com.example.clotheswebsite.entity.ProductSize;
import com.example.clotheswebsite.message.ResponseMessage;
import com.example.clotheswebsite.repository.SupplierRepository;
import com.example.clotheswebsite.service.ProductService;
import com.example.clotheswebsite.service.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SizeService sizeService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/top-new")
    public ResponseEntity<List<Product>> getTop8NewProduct(){
        List<Product> products = productService.findTop8NewProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/top-seller")
    public ResponseEntity<List<Product>> getTop8BestSellerProduct(){
        List<Product> products = productService.findTop8BestSellerProduct();
        return ResponseEntity.ok(products);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProductsWithPagination(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){
       Page<Product> pagination = productService.getAllProductsWithPagination(page, size);
       return ResponseEntity.ok(pagination);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long productId){
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO){
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long productId, @RequestBody ProductDTO updatedProduct){
        return ResponseEntity.ok(productService.updateProduct(updatedProduct, productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable("id") long productId){
        productService.deleteProduct(productId);
        ResponseMessage message = new ResponseMessage("Delete product with id " + productId + " successfully!");
        return ResponseEntity.ok(message);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam("keyword") String keyword){
        List<Product> products = productService.searchProduct(keyword);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/productSizes/{id}")
    public ResponseEntity<ProductSize> getProductSizeById(@PathVariable("id") long productSizeId){
        return ResponseEntity.ok(productService.getProductSizeById(productSizeId));
    }

}

package com.example.clotheswebsite.service.impl;

import com.example.clotheswebsite.dto.ProductDTO;
import com.example.clotheswebsite.dto.ProductSizeDTO;
import com.example.clotheswebsite.entity.*;
import com.example.clotheswebsite.repository.ProductRepository;
import com.example.clotheswebsite.repository.ProductSizeRepository;
import com.example.clotheswebsite.repository.SizeRepository;
import com.example.clotheswebsite.repository.SupplierRepository;
import com.example.clotheswebsite.service.CloudinaryService;
import com.example.clotheswebsite.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryService cloudinaryService;
    @Override
    public List<Product> getAllProducts() {
        List<Product> allProducts = productRepository.findAll();
        for (Product x : allProducts){
            caculateDiscountPrice(x);
        }
        return productRepository.saveAll(allProducts);
    }

    @Override
    public List<Product> findTop8NewProduct() {
        return productRepository.findTop8NewProduct();
    }

    @Override
    public List<Product> findTop8BestSellerProduct() {
        return productRepository.findTop8BestSellerProduct();
    }


    @Override
    @Transactional
    public Page<Product> getAllProductsWithPagination(int page, int size) {
        Page<Product> allProducts = productRepository.findAll(PageRequest.of(page, size));
        for (Product x : allProducts){
            caculateDiscountPrice(x);
        }
        return new PageImpl<>(productRepository.saveAll(allProducts.getContent()), PageRequest.of(page, size), allProducts.getTotalElements());
    }
    @Override
    public Product getProductById(long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Không tồn tại Product này!"));
        caculateDiscountPrice(product);
        return productRepository.save(product);
    }

    @Override
    public Product addProduct(ProductDTO productDTO) {
        Product newProduct = modelMapper.map(productDTO, Product.class);
        Supplier supplier = supplierRepository.findById(productDTO.getSupplierId()).orElseThrow(() -> new EntityNotFoundException("lỗi không tồn ta entity này"));
        newProduct.setSupplier(supplier);
        List<ProductSize> productSizes = new ArrayList<>();
        for (ProductSizeDTO productSizeDTO : productDTO.getProductSizes()){
            ProductSize productSize = modelMapper.map(productSizeDTO, ProductSize.class);
            Size size = sizeRepository.findById(productSizeDTO.getSizeId()).orElseThrow(() -> new EntityNotFoundException("lỗi không tồn ta entity này"));
            productSize.setSize(size);
//            productSize.setSoldQuantity(0);
            productSize.setOldPrice(productSizeDTO.getPrice());
            productSize.setProduct(newProduct);
            productSizes.add(productSize);
        }
        newProduct.setProductSizes(productSizes);
        Map imageData = cloudinaryService.upload(productDTO.getImage());
        newProduct.setImageUrl((String) imageData.get("url"));
        newProduct.setTotalSoldQuantity(productSizes.stream().mapToLong(ProductSize::getSoldQuantity).sum());
        newProduct.setTotalRemainingQuantity(productSizes.stream().mapToLong(ProductSize::getRemainingQuantity).sum());
        return productRepository.save(newProduct);
    }


    @Transactional
    @Override
    public Product updateProduct(ProductDTO updatedProductDTO, long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Supplier supplier = supplierRepository.findById(updatedProductDTO.getSupplierId()).orElseThrow(() -> new EntityNotFoundException("lỗi không tồn ta entity này"));
        product.setProductName(updatedProductDTO.getProductName());
        product.setCategory(updatedProductDTO.getCategory());
        product.setAgeFor(updatedProductDTO.getAgeFor());
        product.setGenderFor(updatedProductDTO.getGenderFor());
        Map imageData = cloudinaryService.upload(updatedProductDTO.getImage());
        product.setImageUrl((String) imageData.get("url"));
        product.setDescription(updatedProductDTO.getDescription());
        product.setSupplier(supplier);
        List<ProductSize> productSizes = new ArrayList<>();
        productSizeRepository.deleteAll(product.getProductSizes());
        for (ProductSizeDTO productSizeDTO : updatedProductDTO.getProductSizes()){
            ProductSize productSize = modelMapper.map(productSizeDTO, ProductSize.class);
            Size size = sizeRepository.findById(productSizeDTO.getSizeId()).orElseThrow(() -> new EntityNotFoundException("lỗi không tồn ta entity này"));
            productSize.setSize(size);
            productSize.setOldPrice(productSizeDTO.getPrice());
            productSize.setProduct(product);
            productSizes.add(productSize);
        }
        product.setProductSizes(productSizes);
        caculateDiscountPrice(product);
        product.setTotalRemainingQuantity(productSizes.stream().mapToLong(ProductSize::getRemainingQuantity).sum());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
//        productSizeRepository.deleteAll(productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found")).getProductSizes());
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(keyword);
        for (Product x : products){
            caculateDiscountPrice(x);
        }
        return productRepository.saveAll(products);
    }



    @Override
    public ProductSize getProductSizeById(long productSizeId) {
        return productSizeRepository.findById(productSizeId).orElseThrow(() -> new EntityNotFoundException("Không tồn tại ProductSize này!"));
    }


    @Override
    public List<Product> filterProducts(String categories, String stock, String priceRanges) {
        List<String> _categories = Arrays.asList(categories.split("\\s+"));
        List<Integer> _priceRanges = Arrays.asList(priceRanges.split("\\s+")).stream().map(p -> Integer.parseInt(p)).toList();
        return productRepository.filterProducts(_categories, stock, _priceRanges.get(0), _priceRanges.get(1),
                _priceRanges.get(2), _priceRanges.get(3), _priceRanges.get(4),  _priceRanges.get(5));
    }

    private void caculateDiscountPrice(Product product){
        List<ProductSize> productSizes = product.getProductSizes();
        for (Discount discount : product.getDiscounts()){
            if (isValidDiscount(discount)){
                for (ProductSize productSize : productSizes){
                    productSize.setPrice(Math.round(productSize.getOldPrice() * (1- discount.getReducePercent()/100)));
                }
            }
            else {
                for (ProductSize productSize : productSizes){
                    productSize.setPrice(productSize.getOldPrice());
                }
            }
        }
    }

    private boolean isValidDiscount(Discount discount){
        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(discount.getTimeStart()) && now.isBefore(discount.getTimeEnd()))
            return true;
        return false;
    }
}

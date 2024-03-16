package com.example.clotheswebsite.service.impl;

import com.example.clotheswebsite.dto.DiscountDTO;
import com.example.clotheswebsite.entity.Discount;
import com.example.clotheswebsite.entity.Product;
import com.example.clotheswebsite.repository.DiscountRepository;
import com.example.clotheswebsite.repository.ProductRepository;
import com.example.clotheswebsite.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscountServiceImpl implements DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public Discount getDiscountById(long discountId) {
        return discountRepository.findById(discountId).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Discount này!"));
    }

    @Override
    public Discount addDiscount(DiscountDTO discountDTO) {
        Discount discount = modelMapper.map(discountDTO, Discount.class);
        List<Product> products = new ArrayList<>();
        for (long productId : discountDTO.getProductIds()){
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Product này!"));
            product.getDiscounts().add(discount);
            products.add(product);
        }
        discount.setProducts(products);
        return discountRepository.save(discount);

    }

    @Override
    public Discount updateDiscount(DiscountDTO discountDTO, long discountId) {
        Discount existDiscount = getDiscountById(discountId);
        List<Product> products = new ArrayList<>();
        for (long productId : discountDTO.getProductIds()){
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Product này!"));
            product.getDiscounts().add(existDiscount);
            products.add(product);
        }
        existDiscount.setDiscountName(discountDTO.getDiscountName());
        existDiscount.setDescription(discountDTO.getDescription());
        existDiscount.setReducePercent(discountDTO.getReducePercent());
        existDiscount.setTimeStart(discountDTO.getTimeStart());
        existDiscount.setTimeEnd(discountDTO.getTimeEnd());
        existDiscount.setProducts(products);
        return discountRepository.save(existDiscount);
    }

    @Override
    public void deleteDiscount(long discountId) {
        discountRepository.deleteById(discountId);
    }
}

package com.example.clotheswebsite.service;

import com.example.clotheswebsite.dto.DiscountDTO;
import com.example.clotheswebsite.entity.Discount;

import java.util.List;

public interface DiscountService {
    List<Discount> getAllDiscounts();
    Discount getDiscountById(long discountId);
    Discount addDiscount(DiscountDTO discountDTO);
    Discount updateDiscount(DiscountDTO discountDTO, long discountId);
    void deleteDiscount(long discountId);
}

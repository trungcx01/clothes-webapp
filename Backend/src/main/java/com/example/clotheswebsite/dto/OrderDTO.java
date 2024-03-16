package com.example.clotheswebsite.dto;

import com.example.clotheswebsite.entity.enums.OrderStatus;
import com.example.clotheswebsite.entity.enums.PaymentMethod;
import com.example.clotheswebsite.entity.enums.ShippingMethod;
import lombok.Data;

import java.util.List;

import static com.example.clotheswebsite.entity.enums.OrderStatus.Processing;

@Data
public class OrderDTO {
    private String phoneNumber;
    private String shippingAddress;
    private ShippingMethod shippingMethod;
    private PaymentMethod paymentMethod;
    private OrderStatus orderStatus = Processing;
    private String note;
    private List<ItemDTO> items;
}

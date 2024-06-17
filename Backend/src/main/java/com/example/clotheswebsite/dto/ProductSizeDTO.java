package com.example.clotheswebsite.dto;

import lombok.Data;

@Data
public class ProductSizeDTO {
    private long sizeId;
    private long price;
    private long remainingQuantity;
    private long soldQuantity=0;
    private String description;
}

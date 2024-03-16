package com.example.clotheswebsite.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDTO {
    private String productName;
    private String category;
    private String ageFor;
    private String genderFor;
    private String imageUrl;
    private String description;
    private long supplierId;
    private List<ProductSizeDTO> productSizes;
}

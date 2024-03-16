package com.example.clotheswebsite.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DiscountDTO {
    private String discountName;
    private long reducePercent;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private String description;
    private List<Long> productIds = new ArrayList<>();
}

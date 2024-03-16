package com.example.clotheswebsite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id", nullable = false, updatable = false)
    private long discountId;

    @Column(name = "promotion_name", nullable = false)
    private String discountName;

    @Column(name = "reduce_percent", nullable = false)
    private double reducePercent;

    @Column(name = "time_start", nullable = false)
    private LocalDateTime timeStart;

    @Column(name = "time_end", nullable = false)
    private LocalDateTime timeEnd;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "discounts")
    @JsonIgnoreProperties("discounts")
    private List<Product> products;
}

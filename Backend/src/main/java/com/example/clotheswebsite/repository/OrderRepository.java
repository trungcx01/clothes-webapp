package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}

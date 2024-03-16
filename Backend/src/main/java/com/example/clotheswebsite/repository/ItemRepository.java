package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item,Long> {
}

package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.dto.DiscountDTO;
import com.example.clotheswebsite.entity.Discount;
import com.example.clotheswebsite.message.ResponseMessage;
import com.example.clotheswebsite.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/discounts")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<Discount>> getAllDiscounts(){
        return ResponseEntity.ok(discountService.getAllDiscounts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable("id") long discountId){
        return ResponseEntity.ok(discountService.getDiscountById(discountId));
    }

    @PostMapping
    public ResponseEntity<Discount> addDiscount(@RequestBody DiscountDTO discountDTO){
        return ResponseEntity.ok(discountService.addDiscount(discountDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Discount> updateDiscount(@RequestBody DiscountDTO discountDTO, @PathVariable("id") long discountId){
        return ResponseEntity.ok(discountService.updateDiscount(discountDTO, discountId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteDiscount(@PathVariable("id") long dicountId){
        discountService.deleteDiscount(dicountId);
        return ResponseEntity.ok(new ResponseMessage("Delete Discount with id: " + dicountId + " successfully!"));
    }
}

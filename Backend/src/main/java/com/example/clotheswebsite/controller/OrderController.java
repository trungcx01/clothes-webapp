package com.example.clotheswebsite.controller;

import com.example.clotheswebsite.dto.OrderDTO;
import com.example.clotheswebsite.entity.Order;
import com.example.clotheswebsite.entity.enums.OrderStatus;
import com.example.clotheswebsite.message.ResponseMessage;
import com.example.clotheswebsite.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") long orderId){
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping
    public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.addOrder(orderDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@RequestParam("orderStatus")OrderStatus orderStatus, @PathVariable("id") long orderId){
        return ResponseEntity.ok((orderService.updateOrder(orderStatus, orderId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteOrder(@PathVariable("id") long orderId){
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok(new ResponseMessage("Delete Order with id: " + orderId + " successfully!"));
    }
}

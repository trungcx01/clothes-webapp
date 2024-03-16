package com.example.clotheswebsite.service.impl;

import com.example.clotheswebsite.dto.ItemDTO;
import com.example.clotheswebsite.dto.OrderDTO;
import com.example.clotheswebsite.entity.Item;
import com.example.clotheswebsite.entity.Order;
import com.example.clotheswebsite.entity.ProductSize;
import com.example.clotheswebsite.entity.UserEntity;
import com.example.clotheswebsite.entity.enums.OrderStatus;
import com.example.clotheswebsite.repository.ItemRepository;
import com.example.clotheswebsite.repository.OrderRepository;
import com.example.clotheswebsite.repository.ProductSizeRepository;
import com.example.clotheswebsite.repository.UserRepository;
import com.example.clotheswebsite.service.OrderService;
import com.example.clotheswebsite.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Order này!"));
    }

    @Override
    public Order addOrder(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Không tồn tại User này!"));
        order.setUser(user);
        long totalPrice = 0;
        List<Item> listItems = new ArrayList<>();
        for (ItemDTO itemDTO : orderDTO.getItems()){
            Item item = modelMapper.map(itemDTO, Item.class);
            ProductSize productSize = productSizeRepository.findById(itemDTO.getProductSizeId()).orElseThrow(() -> new EntityNotFoundException("Không tồn tại ProductSize này!"));
            item.setOrder(order);
            item.setPrice(productSize.getPrice() * itemDTO.getQuantity());
            item.setProductSize(productSize);
            totalPrice += productSize.getPrice() * itemDTO.getQuantity();
            listItems.add(item);
        }
        order.setListItem(listItems);
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(OrderStatus orderStatus, long orderId) {
        Order existOrder = getOrderById(orderId);
        existOrder.setOrderStatus(orderStatus);
        return orderRepository.save(existOrder);
    }

    @Override
    public void deleteOrder(long orderId) {
        orderRepository.deleteById(orderId);
    }
}

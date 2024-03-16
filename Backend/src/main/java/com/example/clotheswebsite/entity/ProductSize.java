package com.example.clotheswebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products_sizes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSize implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_size_id", nullable = false, updatable = false)
    private long productSizeId;

    @Column(name = "old_price", nullable = false)
    private long oldPrice;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "remaining_quantity", nullable = false)
    private long remainingQuantity;

    @Column(name = "sold_quantity", nullable = false)
    private long soldQuantity;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties("productSizes")  // sẽ ignore thuộc tính productSizes trong đối tượng Product product
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @OneToMany(mappedBy = "productSize", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Item> items = new ArrayList<>();

    // FetchType.LAZY: Là chiến lược tải chậm, nghĩa là dữ liệu chỉ được tải khi bạn truy cập đến nó thông qua getter method. Đây là chiến lược mặc định cho các quan hệ @OneToMany và @ManyToMany. Sử dụng FetchType.LAZY giúp tiết kiệm tài nguyên bằng cách chỉ tải dữ liệu khi thực sự cần thiết, nhưng có thể gây ra lỗi LazyInitializationException nếu bạn truy cập đến dữ liệu sau khi session đã đóng
    //FetchType.EAGER: Là chiến lược tải ngay, nghĩa là dữ liệu liên quan sẽ được tải ngay lập tức cùng với entity chính. Đây là chiến lược mặc định cho các quan hệ @ManyToOne và @OneToOne. FetchType.EAGER có thể làm giảm hiệu suất do tải nhiều dữ liệu không cần thiết, đặc biệt khi có nhiều quan hệ hoặc dữ liệu lớn
}

package com.example.clotheswebsite.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sizes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Size implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id", nullable = false, updatable = false)
    private long sizeId;

    @Column(name = "size_name", nullable = false)
    private String sizeName;

    @Column(name = "size_symbol", nullable = false)
    private String sizeSymbol;

    @OneToMany(mappedBy = "size",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore  // bị bỏ đi khi tuần tự(do lặp lại)
    private List<ProductSize> productSizes = new ArrayList<>();

}

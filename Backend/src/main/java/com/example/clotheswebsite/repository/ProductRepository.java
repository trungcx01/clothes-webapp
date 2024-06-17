package com.example.clotheswebsite.repository;

import com.example.clotheswebsite.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByProductNameContainingIgnoreCase(String keyword);

    @Query(value = "SELECT * \n" +
            "FROM products p\n" +
            "ORDER BY p.total_sold_quantity DESC\n" +
            "LIMIT 8", nativeQuery = true)
    List<Product> findTop8BestSellerProduct();

    @Query(value = "SELECT * \n" +
            "FROM products p\n" +
            "ORDER BY p.product_id DESC\n" +
            "LIMIT 8", nativeQuery = true)
    List<Product> findTop8NewProduct();

    @Query(value = "SELECT p.* \n" +
            "FROM products p\n" +
            "JOIN products_sizes ps ON p.product_id = ps.product_id\n" +
            "WHERE p.category in :categories\n" +
            "AND \n" +
            "CASE\n" +
            "\tWHEN :stock ='IN' THEN p.total_remaining_quantity > 0\n" +
            "\tWHEN :stock = 'OUT' THEN p.total_remaining_quantity = 0\n" +
            "\tELSE TRUE \n" +
            "END\n" +
            "GROUP BY p.product_id\n" +
            "HAVING (\n" +
            "(CASE WHEN :A =20000 THEN MIN(ps.price) BETWEEN 0 AND 20000 END)\n" +
            "OR (CASE WHEN :B =50000 THEN MIN(ps.price) BETWEEN 20000 AND 50000 END)\n" +
            "OR (CASE WHEN :C =100000 THEN MIN(ps.price) BETWEEN 50000 AND 100000 END)\n" +
            "OR (CASE WHEN :D =200000 THEN MIN(ps.price) BETWEEN 100000 AND 200000 END)\n" +
            "OR (CASE WHEN :E =500000 THEN MIN(ps.price) BETWEEN 200000 AND 500000 END )\n" +
            "OR (CASE WHEN :F =500001 THEN MIN(ps.price) > 500000 END)\n" +
            ")\n" +
            "\n", nativeQuery = true)
    List<Product> filterProducts(@Param("categories") List<String> categories, @Param("stock") String stock,
                                           @Param("A") int A, @Param("B") int B, @Param("C") int C,
                                           @Param("D") int D, @Param("E") int E, @Param("F") int F);
}

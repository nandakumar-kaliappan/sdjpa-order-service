package com.knkweb.sdjpaorderservice.repository;


import com.knkweb.sdjpaorderservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByDescription(String description);
}

package com.redis_product_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.redis_product_app.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

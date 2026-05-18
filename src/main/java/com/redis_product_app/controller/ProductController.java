package com.redis_product_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.redis_product_app.dto.ProductRequest;
import com.redis_product_app.dto.ProductResponse;
import com.redis_product_app.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "CRUD operations for products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    public ProductResponse create(@Valid @RequestBody ProductRequest request) {
        return service.createProduct(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID (uses Redis cache)")
    public ProductResponse getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> getAll() {
        return service.getAllProducts();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product (updates cache)")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return service.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete product (evicts from cache)")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}

package com.redis_product_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.redis_product_app.dto.ProductRequest;
import com.redis_product_app.dto.ProductResponse;
import com.redis_product_app.entity.Product;
import com.redis_product_app.exception.ProductNotFoundException;
import com.redis_product_app.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    @Cacheable(value = "products", key = "#id")
public ProductResponse getProductById(Long id) {
    System.out.println("=== Cache Miss - Fetching from DATABASE for id: " + id);
    
    Product product = repository.findById(id)
            .orElseThrow(() -> {
                System.out.println("!!! Product not found with id: " + id);
                return new ProductNotFoundException("Product not found with id: " + id);
            });
    
    ProductResponse response = mapToResponse(product);
    System.out.println("=== Product found and returned: " + response.getId());
    return response;
}

    @CachePut(value = "products", key = "#result.id")
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        Product saved = repository.save(product);
        System.out.println("=== Product created and should be cached with id: " + saved.getId());
        return mapToResponse(saved);
    }

    @CachePut(value = "products", key = "#id")
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        Product updated = repository.save(product);
        return mapToResponse(updated);
    }

    @CacheEvict(value = "products", key = "#id")
    @Transactional
    public void deleteProduct(Long id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll().stream().map(this::mapToResponse).toList();
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}

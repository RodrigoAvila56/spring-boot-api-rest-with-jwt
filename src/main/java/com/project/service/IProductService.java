package com.project.service;

import com.project.models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IProductService {

    List<Product> findAll();
    Optional<Product> findById(long id);
    List<Product> findByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice);
    void save(Product product);
    void deleteById(Long id);
}

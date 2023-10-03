package com.example.testrisf.service.impl;

import com.example.testrisf.entities.Product;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculateTax(Product product);
}


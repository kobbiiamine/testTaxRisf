package com.example.testrisf.util;

import com.example.testrisf.entities.Product;

import java.math.BigDecimal;

public class ProductFactory {
    public static Product createProduct(int quantity, String name, BigDecimal price, boolean isImported, Boolean isExempt, ProductType productType ) {
        return new Product(quantity,name, price, isImported, isExempt,productType);
    }
}
package com.example.testrisf.service.impl;

import com.example.testrisf.entities.Product;
import com.example.testrisf.util.ProductType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("basicTaxCalculator")
public class BasicTaxCalculator implements TaxCalculator {
    private static final BigDecimal BASIC_TAX_RATE = new BigDecimal("0.10");

    @Override
    public BigDecimal calculateTax(Product product) {
        BigDecimal tax = product.getPrice().multiply(BASIC_TAX_RATE);
        return tax;
    }

}


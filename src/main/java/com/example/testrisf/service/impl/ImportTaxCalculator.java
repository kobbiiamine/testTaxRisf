package com.example.testrisf.service.impl;

import com.example.testrisf.entities.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component("importTaxCalculator")
public class ImportTaxCalculator implements TaxCalculator {
    private static final BigDecimal IMPORT_TAX_RATE = new BigDecimal("0.05");

    @Override
    public BigDecimal calculateTax(Product product) {
          BigDecimal tax = product.getPrice().multiply(IMPORT_TAX_RATE);
        return tax;
    }


}
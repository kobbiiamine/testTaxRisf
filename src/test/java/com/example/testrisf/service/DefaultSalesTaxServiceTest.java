package com.example.testrisf.service;

import com.example.testrisf.entities.Product;
import com.example.testrisf.entities.Receipt;

import com.example.testrisf.service.impl.DefaultSalesTaxService;
import com.example.testrisf.service.impl.TaxCalculator;
import com.example.testrisf.util.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class DefaultSalesTaxServiceTest {

    @Mock
    private TaxCalculator basicTaxCalculator;

    @Mock
    private TaxCalculator importTaxCalculator;

    private DefaultSalesTaxService taxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        taxService = new DefaultSalesTaxService(basicTaxCalculator, importTaxCalculator);
    }

    @Test
    void testGenerateReceiptForBasicProduct() {
        Product product = new Product(1, "book", BigDecimal.valueOf(12.49), false, false, ProductType.BOOK);
        when(basicTaxCalculator.calculateTax(product)).thenReturn(BigDecimal.valueOf(0.0));

        List<Product> products = Arrays.asList(product);
        Receipt receipt = taxService.generateReceipt(products);

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), receipt.getTotalSalesTax().setScale(2));
        assertEquals(BigDecimal.valueOf(12.49).setScale(2), receipt.getTotalCost().setScale(2));
    }

    @Test
    void testGenerateReceiptForImportedProduct() {
        Product product = new Product(1, "imported book", BigDecimal.valueOf(10.0), true, false, ProductType.OTHER);
        when(importTaxCalculator.calculateTax(product)).thenReturn(BigDecimal.valueOf(0.5));
        when(basicTaxCalculator.calculateTax(product)).thenReturn(BigDecimal.valueOf(0.0));

        List<Product> products = Arrays.asList(product);
        Receipt receipt = taxService.generateReceipt(products);

        assertEquals(BigDecimal.valueOf(0.50).setScale(2), receipt.getTotalSalesTax());
        assertEquals(BigDecimal.valueOf(10.5).setScale(2), receipt.getTotalCost());
    }

    @Test
    void testGenerateReceiptForBasicAndImportedProduct() {
        Product product1 = new Product(1, "book", BigDecimal.valueOf(12.49), false, true, ProductType.BOOK);
        Product product2 = new Product(1, "imported bottle of perfume", BigDecimal.valueOf(47.50), true, false, ProductType.OTHER);

        when(basicTaxCalculator.calculateTax(product1)).thenReturn(BigDecimal.valueOf(0.0));
        when(importTaxCalculator.calculateTax(product2)).thenReturn(BigDecimal.valueOf(2.375));
        when(basicTaxCalculator.calculateTax(product2)).thenReturn(BigDecimal.valueOf(4.75));

        List<Product> products = Arrays.asList(product1, product2);
        Receipt receipt = taxService.generateReceipt(products);

        assertEquals(BigDecimal.valueOf(7.15), receipt.getTotalSalesTax());
        assertEquals(BigDecimal.valueOf(67.14).setScale(2), receipt.getTotalCost().setScale(2));
    }

    @Test
    void testGenerateReceiptForMultipleProducts() {
        Product product1 = new Product(1, "book", BigDecimal.valueOf(12.49), false, true, ProductType.BOOK);
        Product product2 = new Product(2, "music CD", BigDecimal.valueOf(14.99), false, false, ProductType.OTHER);
        Product product3 = new Product(3, "imported box of chocolates", BigDecimal.valueOf(10.00), true, false, ProductType.FOOD);

        when(basicTaxCalculator.calculateTax(product1)).thenReturn(BigDecimal.ZERO);
        when(basicTaxCalculator.calculateTax(product2)).thenReturn(BigDecimal.ZERO);
        when(importTaxCalculator.calculateTax(product3)).thenReturn(BigDecimal.valueOf(0.5));

        List<Product> products = Arrays.asList(product1, product2, product3);
        Receipt receipt = taxService.generateReceipt(products);

        assertEquals(BigDecimal.valueOf(0.5).setScale(2), receipt.getTotalSalesTax().setScale(2)); // Allow a small delta for floating-point precision
        assertEquals(BigDecimal.valueOf(37.98), receipt.getTotalCost().setScale(2)); // Allow a small delta for floating-point precision
    }

    @Test
    void testGenerateReceiptForExemptProduct() {
        Product product = new Product(1, "packet of headache pills", BigDecimal.valueOf(9.75), false, true, ProductType.MEDICAL);
        when(basicTaxCalculator.calculateTax(product)).thenReturn(BigDecimal.ZERO);

        List<Product> products = Arrays.asList(product);
        Receipt receipt = taxService.generateReceipt(products);

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), receipt.getTotalSalesTax());
        assertEquals(BigDecimal.valueOf(9.75), receipt.getTotalCost());
    }
}
package com.example.testrisf.service;

import com.example.testrisf.entities.Product;
import com.example.testrisf.service.impl.BasicTaxCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BasicTaxCalculatorTest {

    @Mock
    private Product mockProduct;

    private BasicTaxCalculator basicTaxCalculator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        basicTaxCalculator = new BasicTaxCalculator();
    }

    @Test
    public void testCalculateTax_WhenProductIsExempt() {
        // Arrange
        when(mockProduct.isExempt()).thenReturn(true);

        // Configure the mockProduct to return a non-null price
        when(mockProduct.getPrice()).thenReturn(BigDecimal.valueOf(0.0));

        // Act
        BigDecimal tax = basicTaxCalculator.calculateTax(mockProduct);

        // Expected tax with 2 decimal places precision
        BigDecimal expectedTax = BigDecimal.ZERO;

        // Assert
        assertEquals(BigDecimal.valueOf(0), tax.compareTo(expectedTax));
    }

    @Test
    public void testCalculateTax_WhenProductIsNotExempt() {
        // Arrange
        when(mockProduct.isExempt()).thenReturn(false);
        when(mockProduct.getPrice()).thenReturn(BigDecimal.valueOf(100.0));

        // Act
        BigDecimal tax = basicTaxCalculator.calculateTax(mockProduct);

        // Set the expected value with the same scale as the actual value
        BigDecimal expectedTax = BigDecimal.valueOf(10.0).setScale(3, RoundingMode.HALF_UP);

        // Assert
        assertEquals(expectedTax, tax);
    }

    @Test
    public void testCalculateTax_RoundsToNearestFiveCents() {
        // Arrange
        when(mockProduct.isExempt()).thenReturn(false);
        when(mockProduct.getPrice()).thenReturn(BigDecimal.valueOf(15.0));

        // Act
        BigDecimal tax = basicTaxCalculator.calculateTax(mockProduct);

        // Set the expected value with the same scale as the actual value
        BigDecimal expectedTax = BigDecimal.valueOf(1.5).setScale(3, RoundingMode.HALF_UP);

        // Assert
        assertEquals(expectedTax, tax);
    }
}
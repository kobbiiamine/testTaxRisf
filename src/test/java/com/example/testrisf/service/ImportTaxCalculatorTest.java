package com.example.testrisf.service;

import com.example.testrisf.entities.Product;
import com.example.testrisf.service.impl.ImportTaxCalculator;
import com.example.testrisf.util.ProductType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImportTaxCalculatorTest {
    @Mock
    private Product mockProduct;

    private ImportTaxCalculator importTaxCalculator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        importTaxCalculator = new ImportTaxCalculator();
    }

    @Test
    public void testCalculateTax_WhenProductIsImported() {
        // Arrange
        when(mockProduct.isImported()).thenReturn(true);
        when(mockProduct.getPrice()).thenReturn(BigDecimal.valueOf(100.0));

        // Act
        BigDecimal tax = importTaxCalculator.calculateTax(mockProduct);

        // Assert
        assertEquals(BigDecimal.valueOf(5.0).setScale(2), tax.setScale(2)); // Ensure that tax is calculated correctly (5% of 100.0) with a scale of 2 decimal places
    }

    @Test
    public void testCalculateTax_WhenProductIsNotImported() {
        // Arrange
        when(mockProduct.isImported()).thenReturn(false);
        when(mockProduct.getType()).thenReturn(ProductType.OTHER);

        // Configure the mockProduct to return a non-null price
        when(mockProduct.getPrice()).thenReturn(BigDecimal.valueOf(100.0));

        // Act
        BigDecimal tax = importTaxCalculator.calculateTax(mockProduct);

        // Assert
        assertEquals(BigDecimal.valueOf(5.0).setScale(2), tax.setScale(2));
    }



}

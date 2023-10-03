package com.example.testrisf.service.impl;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxRounderTest {

    @Test
    public void testRoundToNearestFiveCents() {
        // Act
        BigDecimal roundedValue = TaxRounder.roundToNearestFiveCents(BigDecimal.valueOf(7.32));

        // Assert
        assertEquals(7.35, roundedValue);
    }
}

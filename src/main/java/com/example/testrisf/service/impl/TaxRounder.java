package com.example.testrisf.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TaxRounder {
    static BigDecimal roundToNearestFiveCents(BigDecimal value) {
        BigDecimal roundedValue = value.setScale(2, RoundingMode.HALF_UP); // Round to two decimal places
        BigDecimal fraction = roundedValue.subtract(roundedValue.setScale(0, RoundingMode.DOWN)); // Get the fractional part

        // Calculate the nearest 0.05 value
        BigDecimal nearestFiveCents = BigDecimal.valueOf(Math.ceil(fraction.doubleValue() / 0.05) * 0.05);

        // Add the nearest 0.05 value to the integer part
        return roundedValue.setScale(0, RoundingMode.DOWN).add(nearestFiveCents);

    }
}

package com.example.testrisf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ReceiptItem {
    private Product product;
    private BigDecimal tax;
    private BigDecimal totalPrice;
}

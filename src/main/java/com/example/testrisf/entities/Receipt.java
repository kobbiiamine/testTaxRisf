package com.example.testrisf.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class Receipt {
    private List<ReceiptItem> receiptItems;
    private BigDecimal totalSalesTax;
    private BigDecimal totalCost;
}

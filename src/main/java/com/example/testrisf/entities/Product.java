package com.example.testrisf.entities;

import com.example.testrisf.util.ProductType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private int quantity;
    private String name;
    private BigDecimal price;
    private boolean isImported;
    private boolean isExempt;

    private ProductType type;

}

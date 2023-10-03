package com.example.testrisf.service;

import com.example.testrisf.entities.Product;
import com.example.testrisf.entities.Receipt;
import java.util.List;


public interface SalesTaxCalculatorService {
    Receipt generateReceipt(List<Product> products);
}

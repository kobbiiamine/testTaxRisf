package com.example.testrisf.service.impl;

import com.example.testrisf.entities.Product;
import com.example.testrisf.entities.Receipt;
import com.example.testrisf.entities.ReceiptItem;
import com.example.testrisf.service.SalesTaxCalculatorService;
import com.example.testrisf.util.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultSalesTaxService implements SalesTaxCalculatorService {
    private final TaxCalculator basicTaxCalculator;
    private final TaxCalculator importTaxCalculator;

    @Autowired
    public DefaultSalesTaxService(
            @Qualifier("basicTaxCalculator") TaxCalculator basicTaxCalculator,
            @Qualifier("importTaxCalculator") TaxCalculator importTaxCalculator) {
        this.basicTaxCalculator = basicTaxCalculator;
        this.importTaxCalculator = importTaxCalculator;
    }

    @Override
    public Receipt generateReceipt(List<Product> products) {
        List<ReceiptItem> receiptItems = new ArrayList<>();

        for (Product product : products) {
            BigDecimal tax = calculateTax(product);
            BigDecimal totalPrice = product.getPrice().add(tax);
            receiptItems.add(new ReceiptItem(product, tax, totalPrice));
        }

        BigDecimal totalSalesTax = calculateTotalSalesTax(receiptItems);
        BigDecimal totalCost = calculateTotalCost(receiptItems);

        return new Receipt(receiptItems, totalSalesTax, totalCost);
    }

    private BigDecimal calculateTax(Product product) {
        BigDecimal tax = BigDecimal.ZERO;

        if (!isExceptionProduct(product)) {
            tax = tax.add(basicTaxCalculator.calculateTax(product));
        }


        if (product.isImported() && !product.isExempt()) {
            tax = tax.add(importTaxCalculator.calculateTax(product));
        }


        return TaxRounder.roundToNearestFiveCents(tax).setScale(2, RoundingMode.DOWN);
    }

    private boolean isExceptionProduct(Product product) {
        return product.getType() == ProductType.BOOK ||
                product.getType() == ProductType.FOOD ||
                product.getType() == ProductType.MEDICAL && product.isExempt();
    }

    private BigDecimal calculateTotalSalesTax(List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .map(ReceiptItem::getTax)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculateTotalCost(List<ReceiptItem> receiptItems) {
        return receiptItems.stream()
                .map(ReceiptItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
package com.example.testrisf.controller;

import com.example.testrisf.entities.Product;
import com.example.testrisf.entities.Receipt;
import com.example.testrisf.entities.ReceiptItem;
import com.example.testrisf.service.SalesTaxCalculatorService;
import com.example.testrisf.util.ProductFactory;
import com.example.testrisf.util.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SalesTaxController implements CommandLineRunner {

    private final SalesTaxCalculatorService calculatorService;

    @Autowired
    public SalesTaxController(SalesTaxCalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public void run(String... args) {
        List<Product> basket1 = new ArrayList<>();
        basket1.add(ProductFactory.createProduct(1, "book", BigDecimal.valueOf(12.49), false, true, ProductType.BOOK));
        basket1.add(ProductFactory.createProduct(1, "music CD", BigDecimal.valueOf(14.99), false, false, ProductType.OTHER));
        basket1.add(ProductFactory.createProduct(1, "chocolate bar", BigDecimal.valueOf(0.85), false, true, ProductType.FOOD));

        Receipt receipt1 = calculatorService.generateReceipt(basket1);
        printReceiptDetails(receipt1);

        List<Product> basket2 = new ArrayList<>();
        basket2.add(ProductFactory.createProduct(1, "imported box of chocolates", BigDecimal.valueOf(10.00), true, false, ProductType.FOOD));
        basket2.add(ProductFactory.createProduct(1, "imported bottle of perfume", BigDecimal.valueOf(47.50), true, false, ProductType.OTHER));

        Receipt receipt2 = calculatorService.generateReceipt(basket2);
        printReceiptDetails(receipt2);

        List<Product> basket3 = new ArrayList<>();
        basket3.add(ProductFactory.createProduct(1, "imported bottle of perfume", BigDecimal.valueOf(27.99), true, false, ProductType.OTHER));
        basket3.add(ProductFactory.createProduct(1, "bottle of perfume", BigDecimal.valueOf(18.99), false, true, ProductType.OTHER));
        basket3.add(ProductFactory.createProduct(1, "packet of headache pills", BigDecimal.valueOf(9.75), false, true, ProductType.MEDICAL));
        basket3.add(ProductFactory.createProduct(1, "box of imported chocolates", BigDecimal.valueOf(11.25), true, false, ProductType.FOOD));

        Receipt receipt3 = calculatorService.generateReceipt(basket3);
        printReceiptDetails(receipt3);


    }

    private static void printReceiptDetails(Receipt receipt) {
        System.out.println("Output:");
        for (ReceiptItem item : receipt.getReceiptItems()) {
            System.out.println(item.getProduct().getQuantity() + " " + item.getProduct().getName() + ": " + formatPrice(item.getTotalPrice()));
        }
        System.out.println("Sales" +
                " Taxes: " + formatPrice(receipt.getTotalSalesTax()) + " Total: " + formatPrice(receipt.getTotalCost()));
        System.out.println();
    }

    private static String formatPrice(BigDecimal price) {
        return price.toString();
    }
}
package javacore.salesandcustomeranalysis;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItem {
    private String productName;
    private int quantity;
    private double price;
    private Category category;
}

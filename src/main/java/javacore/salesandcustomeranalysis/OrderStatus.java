package javacore.salesandcustomeranalysis;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED
}

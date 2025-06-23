package javacore.salesandcustomeranalysis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderAnalyticsService {
    List<Order> orders;

    public OrderAnalyticsService(List<Order> orders) {
        this.orders = orders;
    }

    //List of unique cities where orders came from
    public Set<String> getUniqueOrderCities() {
        return orders.stream()
                .map(order -> order.getCustomer().getCity())
                .collect(Collectors.toSet());
    }

    //Total income for all completed orders
    public double calculateTotalIncomeForCompletedOrders() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    //The most popular product by sales
    public String getMostPopularProductBySales() {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName,
                        Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No products");
    }

    //Average check for successfully delivered orders
    public double calculateAverageDeliveryCheck() {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum())
                .average()
                .orElse(0.0);
    }

    //Customers who have more than 5 orders
    public List<Customer> getFrequentCustomers(int countOrders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > countOrders)
                .map(Map.Entry::getKey)
                .toList();
    }
}

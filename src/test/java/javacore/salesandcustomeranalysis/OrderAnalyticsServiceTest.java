package javacore.salesandcustomeranalysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderAnalyticsServiceTest {
    private OrderAnalyticsService service;
    OrderAnalyticsService emptyService;
    private List<Order> orders;
    private static final int COUNT_ORDERS = 2;

    @BeforeEach
    void setUp() {
        Customer customer1 = Customer.builder()
                .name("Alice")
                .city("New York")
                .build();
        Customer customer2 = Customer.builder()
                .name("Bob")
                .city("Los Angeles")
                .build();
        Customer customer3 = Customer.builder()
                .name("Bobina")
                .city("New York")
                .build();

        OrderItem item1 = OrderItem.builder()
                .productName("Laptop")
                .price(1000)
                .quantity(2)
                .build();
        OrderItem item2 = OrderItem.builder()
                .productName("Mouse")
                .price(20)
                .quantity(5)
                .build();
        OrderItem item3 = OrderItem.builder()
                .productName("Keyboard")
                .price(50)
                .quantity(3)
                .build();

        Order order1 = Order.builder()
                .customer(customer1)
                .status(OrderStatus.DELIVERED)
                .items(List.of(item1, item2))
                .build();
        Order order2 = Order.builder()
                .customer(customer2)
                .status(OrderStatus.CANCELLED)
                .items(List.of(item2, item3))
                .build();
        Order order3 = Order.builder()
                .customer(customer3)
                .status(OrderStatus.DELIVERED)
                .items(List.of(item1, item2, item3))
                .build();
        Order order4 = Order.builder()
                .customer(customer1)
                .status(OrderStatus.SHIPPED)
                .items(List.of(item1))
                .build();
        Order order5 = Order.builder()
                .customer(customer1)
                .status(OrderStatus.NEW)
                .items(List.of(item3))
                .build();

        orders = List.of(order1, order2, order3, order4, order5);
        service = new OrderAnalyticsService(orders);
        emptyService = new OrderAnalyticsService(List.of());
    }

    @Test
    void getUniqueOrderCities_shouldReturnTwoCities() {
        Set<String> cities = service.getUniqueOrderCities();

        assertEquals(2, cities.size());
        assertTrue(cities.contains("New York"));
        assertTrue(cities.contains("Los Angeles"));
    }

    @Test
    void getUniqueOrderCities_WithEmptyOrderList_ShouldReturnEmptySet() {
        assertTrue(emptyService.getUniqueOrderCities().isEmpty());
    }

    @Test
    void calculateTotalIncomeForCompletedOrders_shouldReturnCorrectValue() {
        double totalIncome = service.calculateTotalIncomeForCompletedOrders();

        double expectedIncome = (1000 * 2 + 20 * 5) + (1000 * 2 + 20 * 5 + 50 * 3);
        assertEquals(expectedIncome, totalIncome, 0.001);
    }

    @Test
    void getMostPopularProductBySales_returnsMouseAsMostPopular() {
        String mostPopular = service.getMostPopularProductBySales();

        assertEquals("Mouse", mostPopular);
    }

    @Test
    void getMostPopularProductBySales_WithEmptyOrderList_ShouldReturnDefaultMessage() {
        assertEquals("No products", emptyService.getMostPopularProductBySales());
    }

    @Test
    void calculateAverageDeliveryCheck_returnsCorrectAverage() {
        double average = service.calculateAverageDeliveryCheck();

        double totalSum = (1000 * 2 + 20 * 5) + (1000 * 2 + 20 * 5 + 50 * 3);
        double expectedAverage = totalSum / 2;
        assertEquals(expectedAverage, average, 0.001);
    }

    @Test
    void getFrequentCustomers_ShouldReturnCustomersWithMoreThan5Orders() {
        List<Customer> frequentCustomers = service.getFrequentCustomers(COUNT_ORDERS);

        assertEquals(1, frequentCustomers.size());
        assertEquals("Alice", frequentCustomers.getFirst().getName());
    }

    @Test
    void getFrequentCustomers_WithEmptyOrderList_ShouldReturnEmptyList() {
        assertTrue(emptyService.getFrequentCustomers(COUNT_ORDERS).isEmpty());
    }
}
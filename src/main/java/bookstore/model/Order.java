package bookstore.model;

import bookstore.datastructures.MyArrayList;
import java.util.Comparator;

/**
 * Represents a customer order in the bookstore system
 */
public class Order {
    private static int nextOrderId = 1;
    
    private final int orderId;
    private final String customerName;
    private final String shippingAddress;
    private final MyArrayList<Book> books;
    private final MyArrayList<Integer> quantities;
    private String status;
    
    /**
     * Comparator for sorting orders by customer name
     */
    public static final Comparator<Order> BY_CUSTOMER_NAME = Comparator.comparing(
        Order::getCustomerName, String.CASE_INSENSITIVE_ORDER);
    
    /**
     * Comparator for sorting orders by ID
     */
    public static final Comparator<Order> BY_ORDER_ID = Comparator.comparingInt(Order::getOrderId);
    
    /**
     * Constructor for Order
     * @param customerName Customer's name
     * @param shippingAddress Shipping address
     */
    public Order(String customerName, String shippingAddress) {
        this.orderId = nextOrderId++;
        this.customerName = customerName;
        this.shippingAddress = shippingAddress;
        this.books = new MyArrayList<>();
        this.quantities = new MyArrayList<>();
        this.status = "Pending";
    }
    
    /**
     * Add a book to the order
     * @param book Book to add
     * @param quantity Quantity to order
     */
    public void addBook(Book book, int quantity) {
        books.add(book);
        quantities.add(quantity);
    }
    
    /**
     * Calculate total price of the order
     * @return Total price
     */
    public double getTotalPrice() {
        double total = 0;
        for (int i = 0; i < books.size(); i++) {
            total += books.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }
    
    // Getters and setters
    public int getOrderId() {
        return orderId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public MyArrayList<Book> getBooks() {
        return books;
    }
    
    public MyArrayList<Integer> getQuantities() {
        return quantities;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order #").append(orderId)
          .append(" - Customer: ").append(customerName)
          .append(" - Status: ").append(status)
          .append("\nShipping Address: ").append(shippingAddress)
          .append("\nBooks:");
        
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            int quantity = quantities.get(i);
            sb.append("\n  - ").append(book.getTitle())
              .append(" (").append(quantity).append(" copies)")
              .append(" @ $").append(String.format("%.2f", book.getPrice()))
              .append(" each = $").append(String.format("%.2f", book.getPrice() * quantity));
        }
        
        sb.append("\nTotal: $").append(String.format("%.2f", getTotalPrice()));
        return sb.toString();
    }
} 
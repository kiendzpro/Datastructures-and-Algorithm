package bookstore.model;

import java.util.Comparator;

/**
 * Represents a book in the bookstore inventory
 */
public class Book implements Comparable<Book> {
    private String title;
    private String author;
    private String isbn;
    private double price;
    private int quantityInStock;
    
    /**
     * Comparator for sorting books by title
     */
    public static final Comparator<Book> BY_TITLE = (b1, b2) -> {
        // Check if either title starts with a number
        boolean b1StartsWithDigit = !b1.getTitle().isEmpty() && Character.isDigit(b1.getTitle().charAt(0));
        boolean b2StartsWithDigit = !b2.getTitle().isEmpty() && Character.isDigit(b2.getTitle().charAt(0));
        
        // If one starts with digit and the other doesn't, the digit one goes last
        if (b1StartsWithDigit && !b2StartsWithDigit) {
            return 1;
        }
        if (!b1StartsWithDigit && b2StartsWithDigit) {
            return -1;
        }
        
        // Otherwise, alphabetical order
        return b1.getTitle().compareToIgnoreCase(b2.getTitle());
    };
    
    /**
     * Comparator for sorting books by author
     */
    public static final Comparator<Book> BY_AUTHOR = Comparator.comparing(
        Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
    
    /**
     * Comparator for sorting books by price (low to high)
     */
    public static final Comparator<Book> BY_PRICE_ASC = Comparator.comparing(Book::getPrice);
    
    /**
     * Comparator for sorting books by price (high to low)
     */
    public static final Comparator<Book> BY_PRICE_DESC = (b1, b2) -> Double.compare(b2.getPrice(), b1.getPrice());
    
    /**
     * Comparator for sorting books by quantity in stock (low to high)
     */
    public static final Comparator<Book> BY_QUANTITY = Comparator.comparingInt(Book::getQuantityInStock);
    
    /**
     * Comparator for sorting books by quantity in stock (high to low)
     */
    public static final Comparator<Book> BY_QUANTITY_DESC = (b1, b2) -> 
        Integer.compare(b2.getQuantityInStock(), b1.getQuantityInStock());
    
    /**
     * Comparator for sorting books by ISBN
     */
    public static final Comparator<Book> BY_ISBN = Comparator.comparing(Book::getIsbn);
    
    /**
     * Constructor for Book
     * @param title Book title
     * @param author Book author
     * @param isbn Book ISBN
     * @param price Book price
     * @param quantityInStock Initial quantity in stock
     */
    public Book(String title, String author, String isbn, double price, int quantityInStock) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }
    
    // Getters and setters
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getQuantityInStock() {
        return quantityInStock;
    }
    
    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    
    /**
     * Implementation of Comparable interface
     * Default comparison by title
     * @param other Book to compare with
     * @return Comparison result
     */
    @Override
    public int compareTo(Book other) {
        return BY_TITLE.compare(this, other);
    }
    
    @Override
    public String toString() {
        return "Book{" +
               "title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", isbn='" + isbn + '\'' +
               ", price=" + price +
               ", quantityInStock=" + quantityInStock +
               '}';
    }
} 
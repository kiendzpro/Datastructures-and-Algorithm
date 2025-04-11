package bookstore.algorithms;

import bookstore.datastructures.MyArrayList;
import bookstore.model.Book;
import bookstore.model.Order;
import org.junit.Test;
import static org.junit.Assert.*;

public class
SearchingAlgorithmsTest {
    
    @Test
    public void testLinearSearch() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add("date");
        
        int index = SearchingAlgorithms.linearSearch(list, "cherry");
        assertEquals("Should find 'cherry' at index 2", 2, index);
        
        index = SearchingAlgorithms.linearSearch(list, "grape");
        assertEquals("Should return -1 for non-existent element", -1, index);
    }
    
    @Test
    public void testBinarySearch() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);
        list.add(50);
        
        int index = SearchingAlgorithms.binarySearch(list, 30);
        assertEquals("Should find 30 at index 2", 2, index);
        
        index = SearchingAlgorithms.binarySearch(list, 35);
        assertEquals("Should return -1 for non-existent element", -1, index);
    }
    
    @Test
    public void testJumpSearch() {
        MyArrayList<Integer> list = new MyArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i * 5); // 0, 5, 10, 15, ... 95
        }
        
        int index = SearchingAlgorithms.jumpSearch(list, 45);
        assertEquals("Should find 45 at index 9", 9, index);
        
        index = SearchingAlgorithms.jumpSearch(list, 42);
        assertEquals("Should return -1 for non-existent element", -1, index);
    }
    
    @Test
    public void testFindBookByIsbn() {
        MyArrayList<Book> books = new MyArrayList<>();
        books.add(new Book("Book 1", "Author 1", "ISBN1", 10.0, 5));
        books.add(new Book("Book 2", "Author 2", "ISBN2", 15.0, 10));
        books.add(new Book("Book 3", "Author 3", "ISBN3", 20.0, 15));
        
        int index = SearchingAlgorithms.findBookByIsbn(books, "ISBN2");
        assertEquals("Should find book with ISBN2 at index 1", 1, index);
        
        index = SearchingAlgorithms.findBookByIsbn(books, "ISBN4");
        assertEquals("Should return -1 for non-existent ISBN", -1, index);
    }
    
    @Test
    public void testFindBooksByAuthor() {
        MyArrayList<Book> books = new MyArrayList<>();
        books.add(new Book("Book 1", "Author 1", "ISBN1", 10.0, 5));
        books.add(new Book("Book 2", "Author 2", "ISBN2", 15.0, 10));
        books.add(new Book("Book 3", "Author 1", "ISBN3", 20.0, 15));
        
        MyArrayList<Book> result = SearchingAlgorithms.findBooksByAuthor(books, "Author 1");
        assertEquals("Should find 2 books by Author 1", 2, result.size());
        assertEquals("First result should be Book 1", "Book 1", result.get(0).getTitle());
        assertEquals("Second result should be Book 3", "Book 3", result.get(1).getTitle());
    }
    
    @Test
    public void testFindOrderById() {
        MyArrayList<Order> orders = new MyArrayList<>();
        orders.add(new Order("Customer 1", "Address 1"));
        orders.add(new Order("Customer 2", "Address 2"));
        orders.add(new Order("Customer 3", "Address 3"));
        
        // Since Order IDs are auto-incremented, we need to adjust the test accordingly
        int firstOrderId = orders.get(0).getOrderId();
        
        int index = SearchingAlgorithms.findOrderById(orders, firstOrderId);
        assertEquals("Should find first order at index 0", 0, index);
        
        index = SearchingAlgorithms.findOrderById(orders, firstOrderId + 10);
        assertEquals("Should return -1 for non-existent order ID", -1, index);
    }
} 
package bookstore.algorithms;

import bookstore.datastructures.MyArrayList;
import bookstore.model.Book;
import bookstore.model.Order;
import java.util.Comparator;

/**
 * Implementation of various searching algorithms.
 */
public class SearchingAlgorithms {
    
    // Private constructor to prevent instantiation of utility class
    private SearchingAlgorithms() {}
    
    /**
     * Linear Search algorithm
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     * @param list List to search in
     * @param target Target element to find
     * @return Index of target or -1 if not found
     */
    public static <T> int linearSearch(MyArrayList<T> list, T target) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null && list.get(i).equals(target)) {
                return i;
            }
        }
        return -1;  // Not found
    }
    
    /**
     * Linear Search with custom predicate
     * @param list List to search in
     * @param predicate Function to test elements
     * @return Index of matching element or -1 if not found
     */
    public static <T> int linearSearchPredicate(MyArrayList<T> list, SearchPredicate<T> predicate) {
        for (int i = 0; i < list.size(); i++) {
            if (predicate.test(list.get(i))) {
                return i;
            }
        }
        return -1;  // Not found
    }
    
    /**
     * Binary Search algorithm for sorted lists (using natural ordering)
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     * @param list Sorted list to search in
     * @param target Target element to find
     * @return Index of target or -1 if not found
     */
    public static <T extends Comparable<T>> int binarySearch(MyArrayList<T> list, T target) {
        return binarySearch(list, target, AlgorithmUtils.naturalOrder());
    }
    
    /**
     * Binary Search algorithm for sorted lists using custom comparator
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     * @param list Sorted list to search in
     * @param target Target element to find
     * @param comparator Comparator for element comparison
     * @return Index of target or -1 if not found
     */
    public static <T> int binarySearch(MyArrayList<T> list, T target, Comparator<T> comparator) {
        int left = 0;
        int right = list.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            int comparison = comparator.compare(list.get(mid), target);
            
            if (comparison == 0) {
                return mid;
            }
            
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return -1;  // Not found
    }
    
    /**
     * Jump Search algorithm for sorted lists
     * Time Complexity: O(√n)
     * Space Complexity: O(1)
     * @param list Sorted list to search in
     * @param target Target element to find
     * @return Index of target or -1 if not found
     */
    public static <T extends Comparable<T>> int jumpSearch(MyArrayList<T> list, T target) {
        return jumpSearch(list, target, AlgorithmUtils.naturalOrder());
    }
    
    /**
     * Jump Search algorithm for sorted lists using custom comparator
     * Time Complexity: O(√n)
     * Space Complexity: O(1)
     * @param list Sorted list to search in
     * @param target Target element to find
     * @param comparator Comparator for element comparison
     * @return Index of target or -1 if not found
     */
    public static <T> int jumpSearch(MyArrayList<T> list, T target, Comparator<T> comparator) {
        int n = list.size();
        if (n == 0) return -1;
        
        // Block size to be jumped
        int step = (int) Math.floor(Math.sqrt(n));
        
        // Finding the block where element may be present
        int prev = 0;
        while (prev < n && comparator.compare(list.get(Math.min(step, n) - 1), target) < 0) {
            prev = step;
            step += (int) Math.floor(Math.sqrt(n));
            if (prev >= n) return -1;
        }
        
        // Linear search within the block
        while (prev < Math.min(step, n)) {
            if (comparator.compare(list.get(prev), target) == 0) {
                return prev;
            }
            prev++;
        }
        
        return -1;  // Not found
    }
    
    /**
     * Interpolation Search for numeric values
     * Time Complexity: O(log log n) average case for uniform distribution, O(n) worst case
     * Space Complexity: O(1)
     * @param list Sorted list of numeric values
     * @param target Target value to find
     * @return Index of target or -1 if not found
     */
    public static int interpolationSearchByPrice(MyArrayList<Book> list, double targetPrice) {
        int low = 0;
        int high = list.size() - 1;
        
        // Search only if price is within the range
        while (low <= high && 
               targetPrice >= list.get(low).getPrice() && 
               targetPrice <= list.get(high).getPrice()) {
            
            // Estimate position using price ratio
            if (high == low) {
                if (list.get(low).getPrice() == targetPrice) {
                    return low;
                }
                return -1;
            }
            
            // Probing position with uniform distribution
            int pos = low + (int)(
                ((double)(high - low) / (list.get(high).getPrice() - list.get(low).getPrice())) * 
                (targetPrice - list.get(low).getPrice())
            );
            
            if (list.get(pos).getPrice() == targetPrice) {
                return pos;
            }
            
            if (list.get(pos).getPrice() < targetPrice) {
                low = pos + 1;
            } else {
                high = pos - 1;
            }
        }
        
        return -1;  // Not found
    }
    
    /**
     * Find books with price nearest to target price
     * @param list List of books
     * @param targetPrice Target price
     * @param tolerance Price tolerance range
     * @return List of books within price range
     */
    public static MyArrayList<Book> findBooksByPriceRange(MyArrayList<Book> list, double targetPrice, double tolerance) {
        MyArrayList<Book> result = new MyArrayList<>();
        
        for (int i = 0; i < list.size(); i++) {
            Book book = list.get(i);
            if (Math.abs(book.getPrice() - targetPrice) <= tolerance) {
                result.add(book);
            }
        }
        
        return result;
    }
    
    /**
     * Find book by ISBN
     * @param books List of books to search
     * @param isbn ISBN to find
     * @return Index of matching book or -1 if not found
     */
    public static int findBookByIsbn(MyArrayList<Book> books, String isbn) {
        return linearSearchPredicate(books, (book) -> book.getIsbn().equals(isbn));
    }
    
    /**
     * Find all books by author
     * @param books List of books to search
     * @param author Author name to find
     * @return New list containing all matching books
     */
    public static MyArrayList<Book> findBooksByAuthor(MyArrayList<Book> books, String author) {
        MyArrayList<Book> result = new MyArrayList<>();
        
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        
        return result;
    }
    
    /**
     * Find all books by title
     * @param books List of books to search
     * @param title Title or part of title to find
     * @return New list containing all matching books
     */
    public static MyArrayList<Book> findBooksByTitle(MyArrayList<Book> books, String title) {
        MyArrayList<Book> result = new MyArrayList<>();
        
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        
        return result;
    }
    
    /**
     * Find order by ID
     * @param orders List of orders to search
     * @param orderId Order ID to find
     * @return Index of matching order or -1 if not found
     */
    public static int findOrderById(MyArrayList<Order> orders, int orderId) {
        return linearSearchPredicate(orders, (order) -> order.getOrderId() == orderId);
    }
    
    /**
     * Find orders by customer name
     * @param orders List of orders to search
     * @param customerName Customer name to find
     * @return New list containing all matching orders
     */
    public static MyArrayList<Order> findOrdersByCustomerName(MyArrayList<Order> orders, String customerName) {
        MyArrayList<Order> result = new MyArrayList<>();
        
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getCustomerName().toLowerCase().contains(customerName.toLowerCase())) {
                result.add(order);
            }
        }
        
        return result;
    }
    
    /**
     * Functional interface for predicate testing
     */
    @FunctionalInterface
    public interface SearchPredicate<T> {
        boolean test(T value);
    }
} 
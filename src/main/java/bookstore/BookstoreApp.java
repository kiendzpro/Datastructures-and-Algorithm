package bookstore;

import java.util.Scanner;
import bookstore.datastructures.MyArrayList;
import bookstore.datastructures.MyQueue;
import bookstore.model.Book;
import bookstore.model.Order;
import bookstore.algorithms.SortingAlgorithms;
import bookstore.algorithms.SearchingAlgorithms;
import java.util.Comparator;
import java.util.regex.Pattern;
import bookstore.algorithms.AlgorithmUtils;
import bookstore.datastructures.MyStack;

public class BookstoreApp {
    private final MyArrayList<Book> inventory;
    private final MyQueue<Order> pendingOrders;
    private final MyArrayList<Order> processedOrders;
    private final MyStack<Book> browsingHistory;
    private final MyStack<String> searchHistory;
    private final Scanner scanner;
    
    // Regular expression for author validation - only letters, spaces, hyphens, apostrophes and periods
    private static final Pattern AUTHOR_PATTERN = Pattern.compile("^[a-zA-Z .'-]+$");
    
    public BookstoreApp() {
        inventory = new MyArrayList<>();
        pendingOrders = new MyQueue<>();
        processedOrders = new MyArrayList<>();
        browsingHistory = new MyStack<>();
        searchHistory = new MyStack<>();
        scanner = new Scanner(System.in);
        
        // Initialize with sample data
        initializeInventory();
        initializeSampleOrders();
        initializeSearchHistory();
    }
    
    private void initializeInventory() {
        // Original 20 books
        inventory.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", 12.99, 50));
        inventory.add(new Book("To Kill a Mockingbird", "Harper Lee", "9780060935467", 14.99, 75));
        inventory.add(new Book("1984", "George Orwell", "9780451524935", 11.99, 60));
        inventory.add(new Book("Pride and Prejudice", "Jane Austen", "9780141439518", 9.99, 40));
        inventory.add(new Book("The Catcher in the Rye", "J.D. Salinger", "9780316769488", 10.99, 55));
        inventory.add(new Book("The Hobbit", "J.R.R. Tolkien", "9780547928227", 15.99, 65));
        inventory.add(new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "9780590353427", 17.99, 90));
        inventory.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", "9780618640157", 22.99, 45));
        inventory.add(new Book("The Alchemist", "Paulo Coelho", "9780062315007", 13.99, 35));
        inventory.add(new Book("Brave New World", "Aldous Huxley", "9780060850524", 11.49, 55));
        inventory.add(new Book("The Hunger Games", "Suzanne Collins", "9780439023481", 14.49, 70));
        inventory.add(new Book("12 Rules for Life", "Jordan Peterson", "9780345816023", 18.99, 25));
        inventory.add(new Book("Fahrenheit 451", "Ray Bradbury", "9781451673319", 10.99, 40));
        inventory.add(new Book("The Da Vinci Code", "Dan Brown", "9780307474278", 12.99, 30));
        inventory.add(new Book("Animal Farm", "George Orwell", "9780451526342", 9.49, 50));
        inventory.add(new Book("The Shining", "Stephen King", "9780307743657", 15.49, 20));
        inventory.add(new Book("The Road", "Cormac McCarthy", "9780307387899", 13.49, 15));
        inventory.add(new Book("Gone Girl", "Gillian Flynn", "9780307588371", 11.99, 25));
        inventory.add(new Book("Moby Dick", "Herman Melville", "9781503280786", 8.99, 30));
        inventory.add(new Book("War and Peace", "Leo Tolstoy", "9781400079988", 19.99, 10));
    }
    
    /**
     * Initialize sample orders to demonstrate Queue
     */
    private void initializeSampleOrders() {
        // Create sample pending orders to demonstrate Queue
        Order order1 = new Order("John Smith", "123 Main St, Anytown, USA");
        order1.addBook(inventory.get(0), 2); // The Great Gatsby
        order1.addBook(inventory.get(2), 1); // 1984
        pendingOrders.enqueue(order1);
        
        Order order2 = new Order("Sarah Johnson", "456 Oak Ave, Springfield, USA");
        order2.addBook(inventory.get(6), 1); // Harry Potter
        order2.addBook(inventory.get(10), 3); // The Hunger Games
        pendingOrders.enqueue(order2);
        
        Order order3 = new Order("Michael Brown", "789 Pine Rd, Liberty, USA");
        order3.addBook(inventory.get(5), 2); // The Hobbit
        order3.addBook(inventory.get(7), 1); // The Lord of the Rings
        pendingOrders.enqueue(order3);
        
        // Create sample processed order
        Order processedOrder = new Order("Emily Davis", "321 Maple Dr, Lakeside, USA");
        processedOrder.addBook(inventory.get(3), 1); // Pride and Prejudice
        processedOrder.addBook(inventory.get(9), 2); // Brave New World
        processedOrder.setStatus("Processed");
        processedOrders.add(processedOrder);
    }
    
    /**
     * Initialize sample search history to demonstrate Stack
     */
    private void initializeSearchHistory() {
        // Add sample search terms with newest on top (LIFO)
        searchHistory.push("fantasy");
        searchHistory.push("Orwell");
        searchHistory.push("Harry Potter");
        searchHistory.push("bestseller");
        searchHistory.push("classic literature");
        
        // Add sample browsing history
        browsingHistory.push(inventory.get(2)); // 1984
        browsingHistory.push(inventory.get(6)); // Harry Potter
        browsingHistory.push(inventory.get(0)); // The Great Gatsby
    }
    
    public void run() {
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getUserChoice();
            
            switch (choice) {
                case 1:
                    displayInventory();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    processOrders();
                    break;
                case 4:
                    searchBooks();
                    break;
                case 5:
                    sortInventory();
                    break;
                case 6:
                    viewOrders();
                    break;
                case 7:
                    addNewBook();
                    break;
                case 8:
                    showSearchHistory();
                    break;
                case 9:
                    running = false;
                    System.out.println("Thank you for using the Online Bookstore System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            // Wait for user to press enter to continue
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        
        scanner.close();
    }
    
    private void displayMainMenu() {
        System.out.println("\n===== ONLINE BOOKSTORE SYSTEM =====");
        System.out.println("Pending Orders: " + pendingOrders.size());
        System.out.println("Processed Orders: " + processedOrders.size());
        System.out.println("Books in Inventory: " + inventory.size());
        System.out.println("Available Books: " + countAvailableBooks());
        System.out.println("------------------------------------");
        System.out.println("1. Display Book Inventory");
        System.out.println("2. Place New Order");
        System.out.println("3. Process Orders");
        System.out.println("4. Search Books");
        System.out.println("5. Sort Inventory");
        System.out.println("6. View Orders");
        System.out.println("7. Add New Book to Inventory");
        System.out.println("8. Search History");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;  // Invalid input
        }
    }
    
    private void displayInventory() {
        if (inventory.isEmpty()) {
            System.out.println("No books in inventory.");
            return;
        }
        
        System.out.println("\n--- Book Inventory ---");
        System.out.printf("%-5s %-30s %-20s %-15s %-10s %-10s\n", 
                         "No.", "Title", "Author", "ISBN", "Price", "In Stock");
        System.out.println("-".repeat(95));
        
        for (int i = 0; i < inventory.size(); i++) {
            Book book = inventory.get(i);
            System.out.printf("%-5d %-30s %-20s %-15s $%-9.2f %-10d\n", 
                             (i + 1), 
                             truncateString(book.getTitle(), 28),
                             truncateString(book.getAuthor(), 18),
                             book.getIsbn(),
                             book.getPrice(),
                             book.getQuantityInStock());
        }
        System.out.println("-".repeat(95));
        System.out.println("Total: " + inventory.size() + " books\n");
    }
    
    private void placeOrder() {
        System.out.println("\n===== PLACE NEW ORDER =====");
        System.out.print("Enter customer name (or 0 to return to main menu): ");
        String customerNameInput = scanner.nextLine();
        
        if (customerNameInput.equals("0")) {
            System.out.println("Order placement cancelled.");
            return;
        }
        
        System.out.print("Enter shipping address (or 0 to return to main menu): ");
        String shippingAddressInput = scanner.nextLine();
        
        if (shippingAddressInput.equals("0")) {
            System.out.println("Order placement cancelled.");
            return;
        }
        
        Order order = new Order(customerNameInput, shippingAddressInput);
        
        boolean addingBooks = true;
        while (addingBooks) {
            displayInventory();
            System.out.print("\nEnter book number to add (0 to finish order, -1 to cancel entire order): ");
            int bookNumber = getUserChoice();
            
            if (bookNumber == -1) {
                System.out.println("Order cancelled.");
                return;
            } else if (bookNumber == 0) {
                if (order.getBooks().size() == 0) {
                    System.out.println("Order cancelled - no books added.");
                    return;
                }
                addingBooks = false;
            } else if (bookNumber > 0 && bookNumber <= inventory.size()) {
                Book selectedBook = inventory.get(bookNumber - 1);
                
                if (selectedBook.getQuantityInStock() == 0) {
                    System.out.println("Sorry, this book is out of stock. Please select another.");
                    continue;
                }
                
                System.out.print("Enter quantity (0 to cancel adding this book): ");
                int quantity = getUserChoice();
                
                if (quantity == 0) {
                    System.out.println("Book not added to order.");
                    continue;
                }
                
                if (quantity < 0) {
                    System.out.println("Invalid quantity. Please enter a positive number.");
                    continue;
                }
                
                if (quantity > selectedBook.getQuantityInStock()) {
                    System.out.println("Not enough stock. Only " + selectedBook.getQuantityInStock() + " available.");
                    
                    System.out.print("Enter reduced quantity (0 to cancel): ");
                    quantity = getUserChoice();
                    
                    if (quantity == 0) {
                        System.out.println("Book not added to order.");
                        continue;
                    }
                    
                    if (quantity < 0 || quantity > selectedBook.getQuantityInStock()) {
                        System.out.println("Invalid quantity. Book not added to order.");
                        continue;
                    }
                }
                
                order.addBook(selectedBook, quantity);
                System.out.println(quantity + "x \"" + selectedBook.getTitle() + "\" added to order.");
            } else {
                System.out.println("Invalid book number. Please try again.");
            }
        }
        
        // Confirm the order details before placing it
        System.out.println("\n--- Order Summary ---");
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Shipping to: " + order.getShippingAddress());
        System.out.println("Items:");
        
        for (int i = 0; i < order.getBooks().size(); i++) {
            Book book = order.getBooks().get(i);
            int quantity = order.getQuantities().get(i);
            System.out.printf("- %s by %s (Quantity: %d, Price: $%.2f each)\n", 
                book.getTitle(), book.getAuthor(), quantity, book.getPrice());
        }
        
        System.out.printf("Total: $%.2f\n", order.getTotalPrice());
        
        System.out.print("\nConfirm order (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            pendingOrders.enqueue(order);
            System.out.println("Order #" + order.getOrderId() + " placed successfully and added to queue.");
            System.out.println("Current queue status: Your order is at position #" + pendingOrders.size() + " in the queue.");
            System.out.println("Orders are processed based on First-In-First-Out (FIFO) principle.");
            
            // Demonstrate queue operation by showing pending orders
            if (pendingOrders.size() > 1) {
                System.out.println("Next order to be processed: Order #" + pendingOrders.peek().getOrderId() + 
                                   " for customer " + pendingOrders.peek().getCustomerName());
            }
        } else {
            System.out.println("Order cancelled.");
        }
    }
    
    private void processOrders() {
        System.out.println("\n===== PROCESS ORDERS =====");
        
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders to process.");
            return;
        }
        
        System.out.println("Current queue status before processing:");
        System.out.println("Orders in queue: " + pendingOrders.size());
        System.out.println("Next order to be processed: Order #" + pendingOrders.peek().getOrderId() + 
                          " for customer " + pendingOrders.peek().getCustomerName());
        System.out.println("The first order that entered the queue will be processed first (FIFO).");
        
        System.out.print("\nProcess next order? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (!confirm.equals("y") && !confirm.equals("yes")) {
            System.out.println("Order processing cancelled.");
            return;
        }
        
        // Dequeue the order
        Order order = pendingOrders.dequeue();
        order.setStatus("Processed");
        processedOrders.add(order);
        
        System.out.println("\nOrder processed successfully:");
        System.out.println(order);
        
        // Show queue after processing
        if (!pendingOrders.isEmpty()) {
            System.out.println("\nCurrent queue status after processing:");
            System.out.println("Remaining orders in queue: " + pendingOrders.size());
            System.out.println("Next order to be processed: Order #" + pendingOrders.peek().getOrderId() + 
                              " for customer " + pendingOrders.peek().getCustomerName());
        } else {
            System.out.println("\nAll orders have been processed. Queue is now empty.");
        }
    }
    
    private void searchBooks() {
        System.out.println("\n===== SEARCH BOOKS =====");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by ISBN");
        System.out.println("4. Search by Price Range");
        System.out.println("5. Compare Search Algorithm Performance");
        System.out.println("6. Back to Main Menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        MyArrayList<Book> results = new MyArrayList<>();
        long startTime = 0;
        long endTime = 0;
        String searchMethod = "";
        
        switch (choice) {
            case "1":
                System.out.print("Enter title to search: ");
                String title = scanner.nextLine().trim();
                if (!title.isEmpty()) {
                    searchMethod = "Title Search";
                    // Save search term to history stack
                    searchHistory.push("Title: " + title);
                    System.out.println("\nAdding search term to history stack...");
                    System.out.println("PUSH operation: \"Title: " + title + "\" added to top of stack");
                    
                    startTime = System.currentTimeMillis();
                    results = SearchingAlgorithms.findBooksByTitle(inventory, title);
                    endTime = System.currentTimeMillis();
                }
                break;
                
            case "2":
                System.out.print("Enter author to search: ");
                String author = scanner.nextLine().trim();
                if (!author.isEmpty()) {
                    searchMethod = "Author Search";
                    // Save search term to history stack
                    searchHistory.push("Author: " + author);
                    System.out.println("\nAdding search term to history stack...");
                    System.out.println("PUSH operation: \"Author: " + author + "\" added to top of stack");
                    
                    startTime = System.currentTimeMillis();
                    results = SearchingAlgorithms.findBooksByAuthor(inventory, author);
                    endTime = System.currentTimeMillis();
                }
                break;
                
            case "3":
                System.out.print("Enter ISBN to search: ");
                String isbn = scanner.nextLine().trim();
                if (!isbn.isEmpty()) {
                    searchMethod = "ISBN Search (Linear)";
                    // Save search term to history stack
                    searchHistory.push("ISBN: " + isbn);
                    System.out.println("\nAdding search term to history stack...");
                    System.out.println("PUSH operation: \"ISBN: " + isbn + "\" added to top of stack");
                    
                    startTime = System.currentTimeMillis();
                    int index = SearchingAlgorithms.findBookByIsbn(inventory, isbn);
                    endTime = System.currentTimeMillis();
                    if (index != -1) {
                        results.add(inventory.get(index));
                    }
                }
                break;
                
            case "4":
                System.out.print("Enter target price: ");
                try {
                    double price = Double.parseDouble(scanner.nextLine());
                    System.out.print("Enter price tolerance range: ");
                    double tolerance = Double.parseDouble(scanner.nextLine());
                    if (price >= 0 && tolerance >= 0) {
                        searchMethod = "Price Range Search";
                        // Save search term to history stack
                        String priceSearch = "Price: $" + price + " ±$" + tolerance;
                        searchHistory.push(priceSearch);
                        System.out.println("\nAdding search term to history stack...");
                        System.out.println("PUSH operation: \"" + priceSearch + "\" added to top of stack");
                        
                        startTime = System.currentTimeMillis();
                        results = SearchingAlgorithms.findBooksByPriceRange(inventory, price, tolerance);
                        endTime = System.currentTimeMillis();
                    } else {
                        System.out.println("Price and tolerance must be non-negative.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format.");
                }
                break;
                
            case "5":
                compareSearchAlgorithms();
                return;
                
            case "6":
                return;
                
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        System.out.println("\nSearch Results (" + results.size() + " books found):");
        displayBookList(results);
        
        // Display algorithm execution time if search was performed
        if (endTime > startTime) {
            System.out.println("\nAlgorithm Performance:");
            System.out.println("Search Method: " + searchMethod);
            System.out.println("Execution Time: " + (endTime - startTime) + " milliseconds");
            System.out.println("Data Size: " + inventory.size() + " books");
        }
        
        // If search results found, allow viewing book details
        if (!results.isEmpty()) {
            System.out.print("\nWould you like to view details for a book? (Enter book number or 0 to return): ");
            int bookNumber = getUserChoice();
            
            if (bookNumber > 0 && bookNumber <= results.size()) {
                // Show stack before adding
                System.out.println("\nCurrent browsing history before adding this book:");
                System.out.println("Books in history: " + browsingHistory.size());
                if (!browsingHistory.isEmpty()) {
                    System.out.println("Most recently viewed book (top of stack): " + 
                                      browsingHistory.peek().getTitle() + " by " + browsingHistory.peek().getAuthor());
                } else {
                    System.out.println("Browsing history is currently empty.");
                }
                
                // Add the selected book to browsing history
                Book selectedBook = results.get(bookNumber - 1);
                System.out.println("\nPerforming Stack PUSH operation for book: " + 
                                  selectedBook.getTitle() + " by " + selectedBook.getAuthor());
                browsingHistory.push(selectedBook);
                
                // Display detailed information
                System.out.println("\n===== BOOK DETAILS =====");
                System.out.println("Title: " + selectedBook.getTitle());
                System.out.println("Author: " + selectedBook.getAuthor());
                System.out.println("ISBN: " + selectedBook.getIsbn());
                System.out.println("Price: $" + String.format("%.2f", selectedBook.getPrice()));
                System.out.println("Quantity in Stock: " + selectedBook.getQuantityInStock());
                System.out.println("-------------------------");
                
                // Show stack after adding
                System.out.println("\nStack operation: PUSH completed successfully!");
                System.out.println("Current browsing history after adding this book:");
                System.out.println("Books in history: " + browsingHistory.size());
                System.out.println("Most recently viewed book (top of stack): " + 
                                  browsingHistory.peek().getTitle() + " by " + browsingHistory.peek().getAuthor());
                System.out.println("This book is now at the top of your browsing history stack.");
            } else if (bookNumber != 0) {
                System.out.println("Invalid book number.");
            }
        }
    }
    
    /**
     * Compare the performance of different search algorithms
     */
    private void compareSearchAlgorithms() {
        System.out.println("\n===== SEARCH ALGORITHM COMPARISON =====");
        
        // Create a sorted copy of the inventory for binary and jump search
        MyArrayList<Book> sortedInventory = AlgorithmUtils.copyList(inventory);
        SortingAlgorithms.quickSort(sortedInventory, Book.BY_TITLE);
        
        // Get a random book from the inventory for search target
        int randomIndex = (int) (Math.random() * inventory.size());
        Book targetBook = inventory.get(randomIndex);
        
        System.out.println("Target Book for Search: " + targetBook.getTitle() + " by " + targetBook.getAuthor());
        System.out.println("Data Size: " + inventory.size() + " books");
        System.out.println("\nPerforming searches with different algorithms...");
        
        // Test linear search
        long startTime = System.currentTimeMillis();
        int linearResult = SearchingAlgorithms.linearSearch(inventory, targetBook);
        long linearTime = System.currentTimeMillis() - startTime;
        
        // Test binary search on sorted list
        startTime = System.currentTimeMillis();
        int binaryResult = SearchingAlgorithms.binarySearch(sortedInventory, targetBook);
        long binaryTime = System.currentTimeMillis() - startTime;
        
        // Test jump search on sorted list
        startTime = System.currentTimeMillis();
        int jumpResult = SearchingAlgorithms.jumpSearch(sortedInventory, targetBook);
        long jumpTime = System.currentTimeMillis() - startTime;
        
        // Display results
        System.out.println("\nSearch Results:");
        System.out.println("-".repeat(85));
        System.out.printf("| %-15s | %-15s | %-20s | %-25s |\n", 
                          "Algorithm", "Result Index", "Time (milliseconds)", "Time Complexity");
        System.out.println("-".repeat(85));
        
        System.out.printf("| %-15s | %-15d | %-20d | %-25s |\n", 
                          "Linear Search", linearResult, linearTime, "O(n)");
        
        System.out.printf("| %-15s | %-15d | %-20d | %-25s |\n", 
                          "Binary Search", binaryResult, binaryTime, "O(log n)");
        
        System.out.printf("| %-15s | %-15d | %-20d | %-25s |\n", 
                          "Jump Search", jumpResult, jumpTime, "O(√n)");
        
        System.out.println("-".repeat(85));
        
        // Analysis
        System.out.println("\nDetailed Analysis:");
        System.out.println("1. Linear Search:");
        System.out.println("   - Examines each element sequentially");
        System.out.println("   - Time Complexity: O(n) - scales linearly with data size");
        System.out.println("   - Works on unsorted data");
        System.out.println("   - Simple to implement but inefficient for large datasets");
        System.out.println("   - Best for small lists or when data isn't sorted");
        
        System.out.println("\n2. Binary Search:");
        System.out.println("   - Divides search interval in half repeatedly");
        System.out.println("   - Time Complexity: O(log n) - very efficient for large data");
        System.out.println("   - Requires sorted data");
        System.out.println("   - Much faster than linear search for large datasets");
        System.out.println("   - Best when data is already sorted or when search will be performed multiple times");
        
        System.out.println("\n3. Jump Search:");
        System.out.println("   - Jumps fixed steps ahead and then steps back linearly");
        System.out.println("   - Time Complexity: O(√n) - between linear and binary search");
        System.out.println("   - Requires sorted data");
        System.out.println("   - Better than linear search but not as good as binary search");
        System.out.println("   - Advantage over binary: Better for data stored on external storage");
        
        // Note about real-world behavior
        System.out.println("\nNote: For small datasets, time differences may not be significant.");
        System.out.println("In real applications with large datasets (millions of items), the theoretical time complexity becomes more important.");
        System.out.println("Binary search can be thousands of times faster than linear search for large datasets.");
    }
    
    private void sortInventory() {
        System.out.println("\n===== SORT INVENTORY =====");
        System.out.println("1. Sort by Title (A-Z)");
        System.out.println("2. Sort by Author (A-Z)");
        System.out.println("3. Sort by Price (Low-High)");
        System.out.println("4. Sort by Price (High-Low)");
        System.out.println("5. Sort by Quantity (Low-High)");
        System.out.println("6. Sort by Quantity (High-Low)");
        System.out.println("7. Compare Sorting Algorithm Performance");
        System.out.println("8. Back to Main Menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        String sortProperty = "";
        Comparator<Book> comparator = null;
        
        switch (choice) {
            case "1": // Sort by Title
                sortProperty = "Title";
                comparator = Book.BY_TITLE;
                break;
                
            case "2": // Sort by Author
                sortProperty = "Author";
                comparator = Book.BY_AUTHOR;
                break;
                
            case "3": // Sort by Price (Low-High)
                sortProperty = "Price (Low-High)";
                comparator = Book.BY_PRICE_ASC;
                break;
                
            case "4": // Sort by Price (High-Low)
                sortProperty = "Price (High-Low)";
                comparator = Book.BY_PRICE_DESC;
                break;
                
            case "5": // Sort by Quantity (Low-High)
                sortProperty = "Quantity (Low-High)";
                comparator = Book.BY_QUANTITY;
                break;
                
            case "6": // Sort by Quantity (High-Low)
                sortProperty = "Quantity (High-Low)";
                comparator = Book.BY_QUANTITY_DESC;
                break;
                
            case "7": // Compare sorting algorithms
                compareSortingAlgorithms();
                return;
                
            case "8": // Back to Main Menu
                return;
                
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        // Create a copy of the inventory for sorting
        MyArrayList<Book> sortedInventory = AlgorithmUtils.copyList(inventory);
        
        if (comparator != null) {
            System.out.println("\nChoose sorting algorithm:");
            
            // Hiển thị thuật toán phù hợp cho từng loại dữ liệu
            if (sortProperty.equals("Title") || sortProperty.equals("Author")) {
                System.out.println("1. Insertion Sort (Recommended for text)");
                System.out.println("2. Selection Sort");
                System.out.println("3. Quick Sort (Efficient for text)");
                System.out.println("4. Merge Sort (Stable sorting for text)");
            } else {
                System.out.println("1. Insertion Sort");
                System.out.println("2. Selection Sort");
                System.out.println("3. Quick Sort (Recommended for numbers)");
                System.out.println("4. Merge Sort");
                System.out.println("5. Heap Sort (Efficient for numeric data)");
            }
            
            System.out.print("Enter algorithm choice: ");
            String algoChoice = scanner.nextLine();
            
            long executionTime = applySortingAlgorithm(sortedInventory, comparator, algoChoice, sortProperty);
            
            // Display results
            System.out.println("\nSorted Inventory by " + sortProperty + ":");
            displayBookList(sortedInventory);
            
            // Display algorithm execution time
            System.out.println("\nAlgorithm Performance:");
            System.out.println("Sorting Property: " + sortProperty);
            System.out.println("Execution Time: " + executionTime + " milliseconds");
            System.out.println("Data Size: " + sortedInventory.size() + " books");
        }
    }
    
    private long applySortingAlgorithm(MyArrayList<Book> list, Comparator<Book> comparator, String algoChoice, String sortProperty) {
        String algorithmName = "";
        boolean isTextSorting = sortProperty.equals("Title") || sortProperty.equals("Author");
        
        // Validate algorithm choice for text fields
        if (isTextSorting && algoChoice.equals("5")) {
            System.out.println("Warning: Heap Sort is not recommended for text data. Using Quick Sort instead.");
            algoChoice = "3"; // Default to Quick Sort
        }
        
        long startTime = System.currentTimeMillis();
        
        switch (algoChoice) {
            case "1":
                algorithmName = "Insertion Sort";
                System.out.println("Using " + algorithmName + (isTextSorting ? " (good for text data)" : ""));
                SortingAlgorithms.insertionSort(list, comparator);
                break;
            case "2":
                algorithmName = "Selection Sort";
                System.out.println("Using " + algorithmName);
                SortingAlgorithms.selectionSort(list, comparator);
                break;
            case "3":
                algorithmName = "Quick Sort";
                System.out.println("Using " + algorithmName + (isTextSorting ? " (efficient for text data)" : " (efficient for numeric data)"));
                SortingAlgorithms.quickSort(list, comparator);
                break;
            case "4":
                algorithmName = "Merge Sort";
                System.out.println("Using " + algorithmName + (isTextSorting ? " (maintains order of equal elements)" : ""));
                SortingAlgorithms.mergeSort(list, comparator);
                break;
            case "5":
                if (!isTextSorting) {
                    algorithmName = "Heap Sort";
                    System.out.println("Using " + algorithmName + " (efficient for numeric data)");
                    SortingAlgorithms.heapSort(list, comparator);
                } else {
                    algorithmName = "Quick Sort (Default)";
                    System.out.println("Invalid algorithm choice for text. Using " + algorithmName);
                    SortingAlgorithms.quickSort(list, comparator);
                }
                break;
            default:
                algorithmName = "Quick Sort (Default)";
                System.out.println("Invalid algorithm choice. Using " + algorithmName);
                SortingAlgorithms.quickSort(list, comparator);
        }
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        System.out.println("Algorithm: " + algorithmName);
        System.out.println("Time Complexity: " + getTimeComplexity(algorithmName));
        System.out.println("Space Complexity: " + getSpaceComplexity(algorithmName));
        
        return executionTime;
    }
    
    /**
     * Get the time complexity of a sorting algorithm
     */
    private String getTimeComplexity(String algorithm) {
        switch (algorithm) {
            case "Insertion Sort":
                return "O(n²) worst/average case, O(n) best case for nearly sorted data";
            case "Selection Sort":
                return "O(n²) in all cases";
            case "Quick Sort":
                return "O(n log n) average case, O(n²) worst case";
            case "Merge Sort":
                return "O(n log n) in all cases";
            case "Heap Sort":
                return "O(n log n) in all cases";
            default:
                return "Unknown";
        }
    }
    
    /**
     * Get the space complexity of a sorting algorithm
     */
    private String getSpaceComplexity(String algorithm) {
        switch (algorithm) {
            case "Insertion Sort":
                return "O(1) - in-place sort";
            case "Selection Sort":
                return "O(1) - in-place sort";
            case "Quick Sort":
                return "O(log n) for recursion stack";
            case "Merge Sort":
                return "O(n) for auxiliary array";
            case "Heap Sort":
                return "O(1) - in-place sort";
            default:
                return "Unknown";
        }
    }
    
    /**
     * Compare the performance of different sorting algorithms
     */
    private void compareSortingAlgorithms() {
        System.out.println("\n===== SORTING ALGORITHM COMPARISON =====");
        System.out.println("Data Size: " + inventory.size() + " books");
        
        // Create copies of the inventory for different sorting algorithms
        MyArrayList<Book> listForInsertion = AlgorithmUtils.copyList(inventory);
        MyArrayList<Book> listForSelection = AlgorithmUtils.copyList(inventory);
        MyArrayList<Book> listForQuick = AlgorithmUtils.copyList(inventory);
        MyArrayList<Book> listForMerge = AlgorithmUtils.copyList(inventory);
        MyArrayList<Book> listForHeap = AlgorithmUtils.copyList(inventory);
        
        System.out.println("\nChoose data type to sort:");
        System.out.println("1. Text data (Title)");
        System.out.println("2. Numeric data (Price)");
        
        System.out.print("Enter your choice: ");
        String dataChoice = scanner.nextLine();
        
        Comparator<Book> comparator;
        String dataType;
        
        if (dataChoice.equals("1")) {
            comparator = Book.BY_TITLE;
            dataType = "Text (Book Title)";
            System.out.println("\nSorting by Book Title (text data)...");
        } else {
            comparator = Book.BY_PRICE_ASC;
            dataType = "Numeric (Book Price)";
            System.out.println("\nSorting by Book Price (numeric data)...");
        }
        
        // Test Insertion Sort
        long startTime = System.currentTimeMillis();
        SortingAlgorithms.insertionSort(listForInsertion, comparator);
        long insertionTime = System.currentTimeMillis() - startTime;
        
        // Test Selection Sort
        startTime = System.currentTimeMillis();
        SortingAlgorithms.selectionSort(listForSelection, comparator);
        long selectionTime = System.currentTimeMillis() - startTime;
        
        // Test Quick Sort
        startTime = System.currentTimeMillis();
        SortingAlgorithms.quickSort(listForQuick, comparator);
        long quickTime = System.currentTimeMillis() - startTime;
        
        // Test Merge Sort
        startTime = System.currentTimeMillis();
        SortingAlgorithms.mergeSort(listForMerge, comparator);
        long mergeTime = System.currentTimeMillis() - startTime;
        
        // Test Heap Sort - may not be ideal for text data but included for comparison
        startTime = System.currentTimeMillis();
        SortingAlgorithms.heapSort(listForHeap, comparator);
        long heapTime = System.currentTimeMillis() - startTime;
        
        // Display results in a table
        System.out.println("\nPerformance Results for " + dataType + " sorting:");
        System.out.println("-".repeat(105));
        System.out.printf("| %-15s | %-25s | %-30s | %-15s | %-6s |\n", 
                         "Algorithm", "Time (milliseconds)", "Time Complexity", "Space Complexity", "Stable");
        System.out.println("-".repeat(105));
        
        System.out.printf("| %-15s | %-25d | %-30s | %-15s | %-6s |\n", 
                         "Insertion Sort", insertionTime, 
                         "O(n²) avg, O(n) best", "O(1)",
                         "Yes");
        
        System.out.printf("| %-15s | %-25d | %-30s | %-15s | %-6s |\n", 
                         "Selection Sort", selectionTime, 
                         "O(n²) all cases", "O(1)",
                         "No");
        
        System.out.printf("| %-15s | %-25d | %-30s | %-15s | %-6s |\n", 
                         "Quick Sort", quickTime, 
                         "O(n log n) avg, O(n²) worst", "O(log n)",
                         "No");
        
        System.out.printf("| %-15s | %-25d | %-30s | %-15s | %-6s |\n", 
                         "Merge Sort", mergeTime, 
                         "O(n log n) all cases", "O(n)",
                         "Yes");
        
        System.out.printf("| %-15s | %-25d | %-30s | %-15s | %-6s |\n", 
                         "Heap Sort", heapTime, 
                         "O(n log n) all cases", "O(1)",
                         "No");
        
        System.out.println("-".repeat(105));
        
        // Verify correct sorting
        boolean allSame = true;
        for (int i = 0; i < listForInsertion.size(); i++) {
            if (!listForInsertion.get(i).equals(listForQuick.get(i))) {
                allSame = false;
                break;
            }
        }
        
        System.out.println("\nAll algorithms produced identical sorted results: " + 
                          (allSame ? "Yes" : "No"));
        
        // Analysis based on data type
        System.out.println("\nAnalysis for " + dataType + " sorting:");
        if (dataChoice.equals("1")) {
            // Text data analysis
            System.out.println("1. Insertion Sort: Excellent for small or nearly sorted text data. Stable sort preserves order of equal elements.");
            System.out.println("2. Selection Sort: Simple but inefficient for large text data.");
            System.out.println("3. Quick Sort: Fast in practice for text, but order of equal elements may change (unstable).");
            System.out.println("4. Merge Sort: Best choice for text when stability is important. Preserves original order of equal elements.");
            System.out.println("5. Heap Sort: Not commonly used for text sorting as it's unstable and less efficient for text comparison.");
        } else {
            // Numeric data analysis
            System.out.println("1. Insertion Sort: Good for small numeric datasets but inefficient for large ones.");
            System.out.println("2. Selection Sort: Simple but generally inefficient for large numeric data.");
            System.out.println("3. Quick Sort: Very efficient for numeric data in most cases.");
            System.out.println("4. Merge Sort: Reliable performance for all numeric data sizes.");
            System.out.println("5. Heap Sort: Excellent for numeric data with guaranteed O(n log n) performance.");
        }
        
        System.out.println("\nNote: For small datasets, time differences may not reflect theoretical complexities accurately.");
        System.out.println("In real applications with large datasets, the differences become more significant.");
        
        System.out.println("\nDefinitions:");
        System.out.println("- Stable sort: Preserves the relative order of equal elements");
        System.out.println("- In-place sort: Uses O(1) extra space");
        System.out.println("- Time complexity: How execution time grows with input size");
        System.out.println("- Space complexity: How memory usage grows with input size");
    }
    
    private void viewOrders() {
        System.out.println("\n===== VIEW ORDERS =====");
        System.out.println("1. View Pending Orders");
        System.out.println("2. View Processed Orders");
        System.out.println("3. Visualize Order Queue");
        System.out.println("4. Back to Main Menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                viewPendingOrders();
                break;
                
            case "2":
                viewProcessedOrders();
                break;
                
            case "3":
                demonstrateQueueVisualization();
                break;
                
            case "4":
                return;
                
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void viewPendingOrders() {
        System.out.println("\n--- PENDING ORDERS ---");
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders.");
        } else {
            System.out.println("Number of pending orders: " + pendingOrders.size());
            
            // View first pending order without removing it
            System.out.println("\nNext order to be processed:");
            System.out.println(pendingOrders.peek());
        }
    }
    
    private void viewProcessedOrders() {
        System.out.println("\n--- PROCESSED ORDERS ---");
        if (processedOrders.isEmpty()) {
            System.out.println("No processed orders.");
        } else {
            System.out.println("Number of processed orders: " + processedOrders.size());
            
            // Create a copy of the processed orders for sorting and display
            MyArrayList<Order> sortedOrders = AlgorithmUtils.copyList(processedOrders);
            SortingAlgorithms.quickSort(sortedOrders, Order.BY_ORDER_ID);
            
            for (int i = 0; i < sortedOrders.size(); i++) {
                System.out.println("\n--- ORDER " + (i + 1) + " ---");
                System.out.println(sortedOrders.get(i));
            }
        }
    }
    
    /**
     * Demonstrate queue visualization to show the FIFO nature of order processing
     */
    private void demonstrateQueueVisualization() {
        if (pendingOrders.isEmpty()) {
            System.out.println("No pending orders in the queue.");
            return;
        }
        
        System.out.println("\n===== QUEUE VISUALIZATION =====");
        System.out.println("Your pending orders as a FIFO Queue:");
        System.out.println("Total orders in queue: " + pendingOrders.size());
        
        // We need to dequeue all orders to visualize them, then re-enqueue them
        MyQueue<Order> tempQueue = new MyQueue<>();
        
        System.out.println("\n" + "-".repeat(60));
        System.out.println("| POS | ORDER ID | CUSTOMER NAME                 | TOTAL      |");
        System.out.println("-".repeat(60));
        
        int position = 1;
        while (!pendingOrders.isEmpty()) {
            Order order = pendingOrders.dequeue();
            
            // Display the current order
            System.out.printf("| %3d | #%-7d | %-30s | $%-9.2f |\n",
                             position++,
                             order.getOrderId(),
                             truncateString(order.getCustomerName(), 30),
                             order.getTotalPrice());
            
            // Save in temp queue
            tempQueue.enqueue(order);
        }
        
        System.out.println("-".repeat(60));
        System.out.println("FRONT OF QUEUE (First to be processed) at top");
        System.out.println("END OF QUEUE (Last to be processed) at bottom");
        
        // Restore the orders back to the original queue
        while (!tempQueue.isEmpty()) {
            pendingOrders.enqueue(tempQueue.dequeue());
        }
        
        System.out.println("\nQueue Operations:");
        System.out.println("- ENQUEUE: When a new order is placed, it's added to the end of the queue");
        System.out.println("- DEQUEUE: When processing orders, they're removed from the front of the queue");
        System.out.println("- PEEK: See the next order to be processed without removing it");
        System.out.println("- First order added is the first order processed (FIFO principle)");
    }
    
    private void addNewBook() {
        System.out.println("\n===== ADD NEW BOOK =====");
        
        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Title cannot be empty.");
            return;
        }
        
        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Author cannot be empty.");
            return;
        }
        
        if (!AUTHOR_PATTERN.matcher(author).matches()) {
            System.out.println("Author name contains invalid characters.");
            return;
        }
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("ISBN cannot be empty.");
            return;
        }
        
        // Check if ISBN already exists
        if (SearchingAlgorithms.findBookByIsbn(inventory, isbn) != -1) {
            System.out.println("A book with this ISBN already exists.");
            return;
        }
        
        System.out.print("Enter Price: ");
        double price;
        try {
            price = Double.parseDouble(scanner.nextLine());
            if (price < 0) {
                System.out.println("Price cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format.");
            return;
        }
        
        System.out.print("Enter Quantity in Stock: ");
        int quantity;
        try {
            quantity = Integer.parseInt(scanner.nextLine());
            if (quantity < 0) {
                System.out.println("Quantity cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity format.");
            return;
        }
        
        // Add new book to inventory
        Book newBook = new Book(title, author, isbn, price, quantity);
        inventory.add(newBook);
        
        System.out.println("\nBook added successfully:");
        MyArrayList<Book> result = new MyArrayList<>();
        result.add(newBook);
        displayBookList(result);
    }
    
    // Helper method to truncate long strings for display
    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
    
    // Helper method to display a list of books
    private void displayBookList(MyArrayList<Book> books) {
        if (books.size() == 0) {
            System.out.println("No books to display.");
            return;
        }
        
        System.out.printf("%-30s %-20s %-15s %-10s %-10s\n", 
                         "Title", "Author", "ISBN", "Price", "In Stock");
        System.out.println("-".repeat(90));
        
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.printf("%-30s %-20s %-15s $%-9.2f %-10d\n", 
                             truncateString(book.getTitle(), 28),
                             truncateString(book.getAuthor(), 18),
                             book.getIsbn(),
                             book.getPrice(),
                             book.getQuantityInStock());
        }
    }
    
    // Method to count available books (quantity > 0)
    private int countAvailableBooks() {
        int count = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getQuantityInStock() > 0) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Show search history and browsing history from stack
     * This method demonstrates the use of Stack data structure
     */
    private void showSearchHistory() {
        System.out.println("\n===== SEARCH HISTORY =====");
        
        System.out.println("1. View Search Term History");
        System.out.println("2. View Book Browsing History");
        System.out.println("3. Clear All History");
        System.out.println("4. Visualize History Stack");
        System.out.println("5. Back to Main Menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                viewSearchTermHistory();
                break;
                
            case "2":
                visualizeBookBrowsingStack();
                break;
                
            case "3":
                clearAllHistory();
                break;
                
            case "4":
                visualizeHistoryStack();
                break;
                
            case "5":
                return;
                
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    /**
     * View and navigate through search term history using stack operations
     * Demonstrates LIFO (Last-In-First-Out) behavior of stacks
     */
    private void viewSearchTermHistory() {
        if (searchHistory.isEmpty()) {
            System.out.println("Your search history is empty.");
            return;
        }
        
        System.out.println("\n===== SEARCH TERM HISTORY =====");
        System.out.println("Most recent searches (Total: " + searchHistory.size() + ")");
        System.out.println("Stack Structure: Last search is on top (LIFO - Last In, First Out)");
        System.out.println("Current top of stack: \"" + searchHistory.peek() + "\"");
        
        MyStack<String> tempStack = new MyStack<>();
        
        boolean viewingHistory = true;
        while (viewingHistory && !searchHistory.isEmpty()) {
            String currentTerm = searchHistory.peek();
            
            System.out.println("\nCurrently viewing search term: \"" + currentTerm + "\"");
            System.out.println("1. Pop this term and view next (Stack POP operation)");
            System.out.println("2. Find books matching this term");
            System.out.println("3. Return to menu");
            
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    // Pop the current term and store in temporary stack
                    System.out.println("\nPerforming Stack POP operation...");
                    System.out.println("Removing \"" + currentTerm + "\" from the top of the stack");
                    
                    tempStack.push(searchHistory.pop());
                    
                    if (searchHistory.isEmpty()) {
                        System.out.println("You've reached the bottom of your search history stack.");
                        System.out.println("Restoring stack to original state...");
                        
                        // Restore from temp stack
                        while (!tempStack.isEmpty()) {
                            searchHistory.push(tempStack.pop());
                        }
                        System.out.println("Stack restored with " + searchHistory.size() + " terms.");
                    } else {
                        System.out.println("Now viewing next search term: \"" + searchHistory.peek() + "\"");
                    }
                    break;
                    
                case "2":
                    // Execute the search again
                    rerunSearch(currentTerm);
                    break;
                    
                case "3":
                    // Return all terms to the original stack
                    if (!tempStack.isEmpty()) {
                        System.out.println("\nRestoring " + tempStack.size() + " terms to the history stack...");
                        while (!tempStack.isEmpty()) {
                            searchHistory.push(tempStack.pop());
                        }
                        System.out.println("Stack restored to original state.");
                    }
                    viewingHistory = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    /**
     * Re-execute a search based on a term from history
     */
    private void rerunSearch(String searchTerm) {
        System.out.println("\nRe-executing search: " + searchTerm);
        
        MyArrayList<Book> results = new MyArrayList<>();
        
        // Parse the search term to extract type and value
        if (searchTerm.startsWith("Title: ")) {
            String title = searchTerm.substring(7);
            results = SearchingAlgorithms.findBooksByTitle(inventory, title);
        } else if (searchTerm.startsWith("Author: ")) {
            String author = searchTerm.substring(8);
            results = SearchingAlgorithms.findBooksByAuthor(inventory, author);
        } else if (searchTerm.startsWith("ISBN: ")) {
            String isbn = searchTerm.substring(6);
            int index = SearchingAlgorithms.findBookByIsbn(inventory, isbn);
            if (index != -1) {
                results.add(inventory.get(index));
            }
        } else if (searchTerm.startsWith("Price: ")) {
            // Price search format: "Price: $X.XX ±$Y.YY"
            try {
                String priceStr = searchTerm.substring(searchTerm.indexOf("$") + 1, searchTerm.indexOf(" ±"));
                String toleranceStr = searchTerm.substring(searchTerm.lastIndexOf("$") + 1);
                
                double price = Double.parseDouble(priceStr);
                double tolerance = Double.parseDouble(toleranceStr);
                
                results = SearchingAlgorithms.findBooksByPriceRange(inventory, price, tolerance);
            } catch (Exception e) {
                System.out.println("Error parsing price search term");
            }
        }
        
        // Display results
        System.out.println("\nSearch Results (" + results.size() + " books found):");
        displayBookList(results);
    }
    
    /**
     * Clear all history (both search terms and browsing)
     */
    private void clearAllHistory() {
        System.out.println("\n===== CLEAR HISTORY =====");
        System.out.println("Current search history stack contains " + searchHistory.size() + " terms.");
        System.out.println("Current browsing history stack contains " + browsingHistory.size() + " books.");
        
        System.out.print("\nAre you sure you want to clear all history? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        
        if (confirm.equals("y") || confirm.equals("yes")) {
            int searchTerms = searchHistory.size();
            int browsingItems = browsingHistory.size();
            
            System.out.println("\nPerforming Stack CLEAR operations...");
            
            // Use the clear method from MyStack
            searchHistory.clear();
            browsingHistory.clear();
            
            System.out.println("Stack clear operation successful!");
            System.out.println("Removed " + searchTerms + " search terms and " + browsingItems + " browsed books.");
            System.out.println("Both stacks are now empty (size = 0).");
            System.out.println("The stack clear operation has O(1) time complexity.");
        } else {
            System.out.println("Operation cancelled.");
        }
    }
    
    /**
     * Visualize the structure of the history stacks
     */
    private void visualizeHistoryStack() {
        System.out.println("\n===== STACK VISUALIZATION =====");
        System.out.println("Choose which stack to visualize:");
        System.out.println("1. Search Term History Stack");
        System.out.println("2. Book Browsing History Stack");
        System.out.println("3. Back to Menu");
        
        System.out.print("Enter your choice: ");
        String choice = scanner.nextLine();
        
        switch (choice) {
            case "1":
                visualizeSearchTermStack();
                break;
            case "2":
                visualizeBookBrowsingStack();
                break;
            case "3":
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    /**
     * Visualize the search term history stack
     */
    private void visualizeSearchTermStack() {
        if (searchHistory.isEmpty()) {
            System.out.println("Search history stack is empty.");
            return;
        }
        
        System.out.println("\n===== SEARCH TERM STACK VISUALIZATION =====");
        System.out.println("Your search history as a LIFO Stack:");
        System.out.println("Total search terms: " + searchHistory.size());
        
        // Create temporary stack for visualization without losing original stack
        MyStack<String> tempStack = new MyStack<>();
        MyStack<String> displayStack = new MyStack<>();
        
        // Transfer all terms to temp stack (this will reverse the order)
        while (!searchHistory.isEmpty()) {
            tempStack.push(searchHistory.pop());
        }
        
        // Then transfer back to original stack and to display stack
        while (!tempStack.isEmpty()) {
            String term = tempStack.pop();
            searchHistory.push(term);
            displayStack.push(term);
        }
        
        // Now visualize the stack from displayStack (most recent at top)
        System.out.println("\n" + "-".repeat(70));
        System.out.println("| STACK TOP (Most Recent Search) |");
        System.out.println("-".repeat(70));
        
        int position = 1;
        while (!displayStack.isEmpty()) {
            String term = displayStack.pop();
            System.out.printf("| %2d | %-60s |\n", position++, term);
            System.out.println("-".repeat(70));
        }
        
        System.out.println("| STACK BOTTOM (Oldest Search) |");
        System.out.println("-".repeat(70));
        
        System.out.println("\nStack Operations:");
        System.out.println("- PUSH: When you perform a search, term is added to the top");
        System.out.println("- POP: When viewing history, terms are removed from the top");
        System.out.println("- PEEK: See the most recent search term without removing it");
        System.out.println("- All operations happen in O(1) time complexity");
    }
    
    /**
     * Visualize the book browsing history stack
     */
    private void visualizeBookBrowsingStack() {
        if (browsingHistory.isEmpty()) {
            System.out.println("Book browsing history stack is empty.");
            return;
        }
        
        System.out.println("\n===== BOOK BROWSING STACK VISUALIZATION =====");
        System.out.println("Your browsing history as a LIFO Stack:");
        System.out.println("Total books viewed: " + browsingHistory.size());
        
        // Create temporary stacks for visualization without losing original stack
        MyStack<Book> tempStack = new MyStack<>();
        MyStack<Book> displayStack = new MyStack<>();
        
        // First, transfer all books to temp stack (this will reverse the order)
        while (!browsingHistory.isEmpty()) {
            tempStack.push(browsingHistory.pop());
        }
        
        // Then transfer back to original stack and to display stack
        while (!tempStack.isEmpty()) {
            Book book = tempStack.pop();
            browsingHistory.push(book);
            displayStack.push(book);
        }
        
        // Now visualize the stack from displayStack (most recent at top)
        System.out.println("\n" + "-".repeat(60));
        System.out.println("STACK TOP (Most Recently Viewed Book)");
        System.out.println("-".repeat(60));
        
        int position = 1;
        while (!displayStack.isEmpty()) {
            Book book = displayStack.pop();
            System.out.printf("| %2d | %-30s by %-20s |\n", 
                             position++,
                             truncateString(book.getTitle(), 30),
                             truncateString(book.getAuthor(), 20));
            System.out.println("-".repeat(60));
        }
        
        System.out.println("STACK BOTTOM (First Viewed Book)");
        System.out.println("-".repeat(60));
    }
    
    public static void main(String[] args) {
        BookstoreApp app = new BookstoreApp();
        app.run();
    }
} 

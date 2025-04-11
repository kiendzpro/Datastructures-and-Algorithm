package bookstore.datastructures;

/**
 * Custom implementation of Queue data structure using linked nodes.
 * Follows FIFO (First-In-First-Out) principle.
 * @param <T> Type of elements stored in the queue
 */
public class MyQueue<T> {
    private Node<T> front;    // For removal (dequeue)
    private Node<T> rear;     // For addition (enqueue)
    private int size;         // Tracks number of elements
    
    /**
     * Node class for queue elements
     * @param <T> Type of data stored in node
     */
    private static class Node<T> {
        private T data;
        private Node<T> next;
        
        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }
    
    /**
     * Constructor for empty queue
     */
    public MyQueue() {
        front = null;
        rear = null;
        size = 0;
    }
    
    /**
     * Add an element to the end of the queue
     * @param item Element to add
     */
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);
        
        if (isEmpty()) {
            // If queue is empty, both front and rear point to new node
            front = newNode;
        } else {
            // Otherwise, add to rear
            rear.next = newNode;
        }
        
        rear = newNode;  // Update rear pointer
        size++;
    }
    
    /**
     * Remove and return the element at the front of queue
     * @return The front element
     * @throws IllegalStateException if queue is empty
     */
    public T dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        T item = front.data;
        front = front.next;
        
        // If after removal the queue becomes empty, update rear pointer
        if (front == null) {
            rear = null;
        }
        
        size--;
        return item;
    }
    
    /**
     * Return but don't remove the front element
     * @return The front element
     * @throws IllegalStateException if queue is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        
        return front.data;
    }
    
    /**
     * Check if queue is empty
     * @return true if queue contains no elements
     */
    public boolean isEmpty() {
        return front == null;
    }
    
    /**
     * Get the number of elements in the queue
     * @return Number of elements
     */
    public int size() {
        return size;
    }
    
    /**
     * Clear all elements from the queue
     */
    public void clear() {
        front = null;
        rear = null;
        size = 0;
    }
} 
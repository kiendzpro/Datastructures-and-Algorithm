package bookstore.datastructures;

/**
 * Custom implementation of Stack data structure using linked nodes.
 * Follows LIFO (Last-In-First-Out) principle.
 * @param <T> Type of elements stored in the stack
 */
public class MyStack<T> {
    private Node<T> top;   // Reference to the top node
    private int size;      // Tracks number of elements
    
    /**
     * Node class for stack elements
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
     * Constructor for empty stack
     */
    public MyStack() {
        top = null;
        size = 0;
    }
    
    /**
     * Add an element to the top of the stack
     * @param item Element to add
     */
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    /**
     * Remove and return the element at the top of stack
     * @return The top element
     * @throws IllegalStateException if stack is empty
     */
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }
    
    /**
     * Return but don't remove the top element
     * @return The top element
     * @throws IllegalStateException if stack is empty
     */
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        
        return top.data;
    }
    
    /**
     * Check if stack is empty
     * @return true if stack contains no elements
     */
    public boolean isEmpty() {
        return top == null;
    }
    
    /**
     * Get the number of elements in the stack
     * @return Number of elements
     */
    public int size() {
        return size;
    }
    
    /**
     * Clear all elements from the stack
     */
    public void clear() {
        top = null;
        size = 0;
    }
} 
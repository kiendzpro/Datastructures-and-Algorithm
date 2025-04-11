package bookstore.datastructures;

/**
 * Custom implementation of dynamic array data structure.
 * @param <T> Type of elements stored in the array list
 */
public class MyArrayList<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;
    
    /**
     * Constructor with default capacity of 10
     */
    public MyArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
        size = 0;
    }
    
    /**
     * Constructor with specified initial capacity
     * @param initialCapacity The initial capacity for the array
     */
    public MyArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Initial capacity cannot be negative: " + initialCapacity);
        }
        elements = new Object[initialCapacity];
        size = 0;
    }
    
    /**
     * Add an element to the end of the list
     * @param element Element to be added
     */
    public void add(T element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
    }
    
    /**
     * Add an element at a specific index
     * @param index Index where element should be added
     * @param element Element to be added
     */
    public void add(int index, T element) {
        validateIndexForAdd(index);
        ensureCapacity(size + 1);
        
        // Shift elements right to make space
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }
    
    /**
     * Get element at specified index
     * @param index The index of the element
     * @return Element at the specified index
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        validateIndexForAccess(index);
        return (T) elements[index];
    }
    
    /**
     * Set the element at specified index
     * @param index The index of the element to replace
     * @param element New element to store
     * @return Previous element at the index
     */
    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        validateIndexForAccess(index);
        T oldValue = (T) elements[index];
        elements[index] = element;
        return oldValue;
    }
    
    /**
     * Remove element at specified index
     * @param index The index of the element to remove
     * @return The removed element
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        validateIndexForAccess(index);
        T oldValue = (T) elements[index];
        
        // Shift elements left to fill the gap
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        
        // Clear reference to help GC
        elements[--size] = null;
        return oldValue;
    }
    
    /**
     * Clear all elements from the list
     */
    public void clear() {
        // Clear references to help GC
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }
    
    /**
     * Get current size of the list
     * @return Number of elements in the list
     */
    public int size() {
        return size;
    }
    
    /**
     * Check if the list is empty
     * @return true if list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Check if list contains the specified element
     * @param element Element to check for
     * @return true if the element is present
     */
    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }
    
    /**
     * Find the index of the first occurrence of an element
     * @param element Element to find
     * @return Index of element or -1 if not found
     */
    public int indexOf(T element) {
        for (int i = 0; i < size; i++) {
            if (element == null ? elements[i] == null : element.equals(elements[i])) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Ensure the array has enough capacity to add elements
     * @param minCapacity Minimum required capacity
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            // Grow by doubling - standard growth strategy for dynamic arrays
            int newCapacity = Math.max(elements.length * 2, minCapacity);
            Object[] newElements = new Object[newCapacity];
            System.arraycopy(elements, 0, newElements, 0, size);
            elements = newElements;
        }
    }
    
    /**
     * Validate index for add operations
     * @param index Index to validate
     */
    private void validateIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
    
    /**
     * Validate index for access/remove operations
     * @param index Index to validate
     */
    private void validateIndexForAccess(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
} 
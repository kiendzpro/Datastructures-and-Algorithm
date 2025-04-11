package bookstore.algorithms;

import bookstore.datastructures.MyArrayList;
import java.util.Comparator;

/**
 * Implementation of various sorting algorithms.
 */
public class SortingAlgorithms {
    
    // Private constructor to prevent instantiation of utility class
    private SortingAlgorithms() {}
    
    /**
     * Sort using Insertion Sort with natural ordering
     * @param list List to sort
     * @param <T> Type that implements Comparable
     */
    public static <T extends Comparable<T>> void insertionSort(MyArrayList<T> list) {
        insertionSort(list, AlgorithmUtils.naturalOrder());
    }
    
    /**
     * Sort using Insertion Sort with custom comparator
     * Time Complexity: O(n²) worst/average case, O(n) best case for nearly sorted data
     * Space Complexity: O(1)
     * @param list List to sort
     * @param comparator Comparator for element comparison
     * @param <T> Element type
     */
    public static <T> void insertionSort(MyArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        
        for (int i = 1; i < n; i++) {
            T key = list.get(i);
            int j = i - 1;
            
            // Shift elements greater than key to the right
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            
            list.set(j + 1, key);
        }
    }
    
    /**
     * Sort using Selection Sort with natural ordering
     * @param list List to sort
     * @param <T> Type that implements Comparable
     */
    public static <T extends Comparable<T>> void selectionSort(MyArrayList<T> list) {
        selectionSort(list, AlgorithmUtils.naturalOrder());
    }
    
    /**
     * Sort using Selection Sort with custom comparator
     * Time Complexity: O(n²) in all cases
     * Space Complexity: O(1)
     * @param list List to sort
     * @param comparator Comparator for element comparison
     * @param <T> Element type
     */
    public static <T> void selectionSort(MyArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            
            // Find minimum element in unsorted part
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            
            // Swap if minimum not at current position
            if (minIndex != i) {
                AlgorithmUtils.swap(list, i, minIndex);
            }
        }
    }
    
    /**
     * Sort using Quick Sort with natural ordering
     * @param list List to sort
     * @param <T> Type that implements Comparable
     */
    public static <T extends Comparable<T>> void quickSort(MyArrayList<T> list) {
        quickSort(list, AlgorithmUtils.naturalOrder());
    }
    
    /**
     * Sort using Quick Sort with custom comparator
     * Time Complexity: O(n log n) average case, O(n²) worst case
     * Space Complexity: O(log n) for recursion stack
     * @param list List to sort
     * @param comparator Comparator for element comparison
     * @param <T> Element type
     */
    public static <T> void quickSort(MyArrayList<T> list, Comparator<T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }
    
    /**
     * Recursive Quick Sort implementation
     */
    private static <T> void quickSort(MyArrayList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(list, low, high, comparator);
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }
    
    /**
     * Partition function for Quick Sort
     */
    private static <T> int partition(MyArrayList<T> list, int low, int high, Comparator<T> comparator) {
        // Use the high value as pivot
        T pivot = list.get(high);
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                AlgorithmUtils.swap(list, i, j);
            }
        }
        
        AlgorithmUtils.swap(list, i + 1, high);
        return i + 1;
    }
    
    /**
     * Sort using Merge Sort with natural ordering
     * @param list List to sort
     * @param <T> Type that implements Comparable
     */
    public static <T extends Comparable<T>> void mergeSort(MyArrayList<T> list) {
        mergeSort(list, AlgorithmUtils.naturalOrder());
    }
    
    /**
     * Sort using Merge Sort with custom comparator
     * Time Complexity: O(n log n) in all cases
     * Space Complexity: O(n) for temporary array
     * @param list List to sort
     * @param comparator Comparator for element comparison
     * @param <T> Element type
     */
    public static <T> void mergeSort(MyArrayList<T> list, Comparator<T> comparator) {
        Object[] temp = new Object[list.size()];
        mergeSort(list, temp, 0, list.size() - 1, comparator);
    }
    
    /**
     * Recursive Merge Sort implementation
     */
    private static <T> void mergeSort(MyArrayList<T> list, Object[] temp, int left, int right, Comparator<T> comparator) {
        if (left < right) {
            int middle = left + (right - left) / 2;
            
            mergeSort(list, temp, left, middle, comparator);
            mergeSort(list, temp, middle + 1, right, comparator);
            
            merge(list, temp, left, middle, right, comparator);
        }
    }
    
    /**
     * Merge function for Merge Sort
     */
    private static <T> void merge(MyArrayList<T> list, Object[] temp, int left, int middle, int right, Comparator<T> comparator) {
        // Copy data to temp arrays
        for (int i = left; i <= right; i++) {
            temp[i] = list.get(i);
        }
        
        int i = left;
        int j = middle + 1;
        int k = left;
        
        while (i <= middle && j <= right) {
            @SuppressWarnings("unchecked")
            T leftVal = (T) temp[i];
            @SuppressWarnings("unchecked")
            T rightVal = (T) temp[j];
            
            if (comparator.compare(leftVal, rightVal) <= 0) {
                list.set(k++, leftVal);
                i++;
            } else {
                list.set(k++, rightVal);
                j++;
            }
        }
        
        // Copy remaining elements
        while (i <= middle) {
            @SuppressWarnings("unchecked")
            T val = (T) temp[i++];
            list.set(k++, val);
        }
    }
    
    /**
     * Sort using Heap Sort with natural ordering
     * @param list List to sort
     * @param <T> Type that implements Comparable
     */
    public static <T extends Comparable<T>> void heapSort(MyArrayList<T> list) {
        heapSort(list, AlgorithmUtils.naturalOrder());
    }
    
    /**
     * Sort using Heap Sort with custom comparator
     * Time Complexity: O(n log n) in all cases
     * Space Complexity: O(1) - in-place sorting
     * @param list List to sort
     * @param comparator Comparator for element comparison
     * @param <T> Element type
     */
    public static <T> void heapSort(MyArrayList<T> list, Comparator<T> comparator) {
        int n = list.size();
        
        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(list, n, i, comparator);
        }
        
        // Extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            AlgorithmUtils.swap(list, 0, i);
            
            // Call max heapify on reduced heap
            heapify(list, i, 0, comparator);
        }
    }
    
    /**
     * Heapify function for Heap Sort
     */
    private static <T> void heapify(MyArrayList<T> list, int n, int i, Comparator<T> comparator) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        
        // Check if left child is larger than root
        if (left < n && comparator.compare(list.get(left), list.get(largest)) > 0) {
            largest = left;
        }
        
        // Check if right child is larger than largest so far
        if (right < n && comparator.compare(list.get(right), list.get(largest)) > 0) {
            largest = right;
        }
        
        // If largest is not root, swap and continue heapifying
        if (largest != i) {
            AlgorithmUtils.swap(list, i, largest);
            heapify(list, n, largest, comparator);
        }
    }
} 
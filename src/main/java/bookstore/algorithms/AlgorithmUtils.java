package bookstore.algorithms;

import bookstore.datastructures.MyArrayList;
import java.util.Comparator;

/**
 * Utility class with common algorithm functionality
 */
public class AlgorithmUtils {
    
    /**
     * Generic comparator that uses natural ordering
     * @param <T> Type that implements Comparable
     * @return Comparator instance
     */
    public static <T extends Comparable<T>> Comparator<T> naturalOrder() {
        return (a, b) -> a.compareTo(b);
    }
    
    /**
     * Generate a reversed comparator
     * @param comparator Original comparator
     * @return Reversed comparator
     */
    public static <T> Comparator<T> reversed(Comparator<T> comparator) {
        return (a, b) -> comparator.compare(b, a);
    }
    
    /**
     * Swap two elements in an array list
     * @param list List containing elements
     * @param i First index
     * @param j Second index
     */
    public static <T> void swap(MyArrayList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
    
    /**
     * Deep copy an array list
     * @param original Original list
     * @return Copy of the list
     */
    public static <T> MyArrayList<T> copyList(MyArrayList<T> original) {
        MyArrayList<T> copy = new MyArrayList<>(original.size());
        for (int i = 0; i < original.size(); i++) {
            copy.add(original.get(i));
        }
        return copy;
    }
} 
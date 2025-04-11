package bookstore.algorithms;

import bookstore.datastructures.MyArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;

public class SortingAlgorithmsTest {
    
    @Test
    public void testInsertionSort() {
        MyArrayList<Integer> list = createUnsortedIntegerList();
        SortingAlgorithms.insertionSort(list);
        assertSorted(list);
    }
    
    @Test
    public void testInsertionSortWithComparator() {
        MyArrayList<String> list = createUnsortedStringList();
        SortingAlgorithms.insertionSort(list, Comparator.naturalOrder());
        assertSorted(list, Comparator.naturalOrder());
    }
    
    @Test
    public void testSelectionSort() {
        MyArrayList<Integer> list = createUnsortedIntegerList();
        SortingAlgorithms.selectionSort(list);
        assertSorted(list);
    }
    
    @Test
    public void testQuickSort() {
        MyArrayList<Integer> list = createUnsortedIntegerList();
        SortingAlgorithms.quickSort(list);
        assertSorted(list);
    }
    
    @Test
    public void testMergeSort() {
        MyArrayList<Integer> list = createUnsortedIntegerList();
        SortingAlgorithms.mergeSort(list);
        assertSorted(list);
    }
    
    @Test
    public void testHeapSort() {
        MyArrayList<Integer> list = createUnsortedIntegerList();
        SortingAlgorithms.heapSort(list);
        assertSorted(list);
    }
    
    // Helper method to create an unsorted list of integers
    private MyArrayList<Integer> createUnsortedIntegerList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(5);
        list.add(2);
        list.add(8);
        list.add(1);
        list.add(9);
        list.add(3);
        list.add(7);
        list.add(4);
        list.add(6);
        return list;
    }
    
    // Helper method to create an unsorted list of strings
    private MyArrayList<String> createUnsortedStringList() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("banana");
        list.add("apple");
        list.add("grape");
        list.add("cherry");
        list.add("orange");
        list.add("kiwi");
        list.add("melon");
        return list;
    }
    
    // Helper method to check if a list is sorted
    private <T extends Comparable<T>> void assertSorted(MyArrayList<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            assertTrue(
                "Elements should be sorted. Element at " + i + " (" + list.get(i) + 
                ") should be <= element at " + (i+1) + " (" + list.get(i+1) + ")",
                list.get(i).compareTo(list.get(i+1)) <= 0);
        }
    }
    
    // Helper method to check if a list is sorted using a comparator
    private <T> void assertSorted(MyArrayList<T> list, Comparator<T> comparator) {
        for (int i = 0; i < list.size() - 1; i++) {
            assertTrue(
                "Elements should be sorted. Element at " + i + " (" + list.get(i) + 
                ") should be <= element at " + (i+1) + " (" + list.get(i+1) + ")",
                comparator.compare(list.get(i), list.get(i+1)) <= 0);
        }
    }
} 
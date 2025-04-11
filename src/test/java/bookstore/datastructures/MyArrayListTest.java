package bookstore.datastructures;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class MyArrayListTest {
    
    private MyArrayList<String> list;
    
    @Before
    public void setUp() {
        list = new MyArrayList<>();
    }
    
    @Test
    public void testAddAndGet() {
        list.add("One");
        assertEquals("First element should be 'One'", "One", list.get(0));
    }
    
    @Test
    public void testSet() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        
        list.set(1, 22);
        
        assertEquals("Element at index 1 should be updated", Integer.valueOf(22), list.get(1));
    }
    
    @Test
    public void testResize() {
        MyArrayList<Integer> list = new MyArrayList<>();
        
        for (int i = 0; i < 15; i++) {
            list.add(i);
        }
        
        assertEquals("Size should be 15", 15, list.size());
        assertEquals("Element at index 14 should be 14", Integer.valueOf(14), list.get(14));
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("One");
        
        list.get(1); // This should throw an exception
    }
    
    @Test(expected = IndexOutOfBoundsException.class)
    public void testSetOutOfBounds() {
        MyArrayList<String> list = new MyArrayList<>();
        list.add("One");
        
        list.set(1, "Two"); // This should throw an exception
    }
} 
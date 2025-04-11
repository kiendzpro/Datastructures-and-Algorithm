package bookstore.datastructures;

import org.junit.Test;
import static org.junit.Assert.*;

public class MyStackTest {
    
    @Test
    public void testPushAndPop() {
        MyStack<String> stack = new MyStack<>();
        
        stack.push("First");
        stack.push("Second");
        stack.push("Third");
        
        assertEquals("Size should be 3", 3, stack.size());
        assertEquals("Top element should be 'Third'", "Third", stack.peek());
        
        assertEquals("Popped element should be 'Third'", "Third", stack.pop());
        assertEquals("Size should be 2 after pop", 2, stack.size());
        assertEquals("New top element should be 'Second'", "Second", stack.peek());
        
        assertEquals("Popped element should be 'Second'", "Second", stack.pop());
        assertEquals("Popped element should be 'First'", "First", stack.pop());
        
        assertTrue("Stack should be empty after all elements are popped", stack.isEmpty());
    }
    
    @Test
    public void testIsEmpty() {
        MyStack<Integer> stack = new MyStack<>();
        
        assertTrue("New stack should be empty", stack.isEmpty());
        
        stack.push(1);
        assertFalse("Stack with element should not be empty", stack.isEmpty());
        
        stack.pop();
        assertTrue("Stack should be empty after pop", stack.isEmpty());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testPopEmpty() {
        MyStack<String> stack = new MyStack<>();
        
        stack.pop(); // This should throw an exception
    }
    
    @Test(expected = IllegalStateException.class)
    public void testPeekEmpty() {
        MyStack<String> stack = new MyStack<>();
        
        stack.peek(); // This should throw an exception
    }
} 
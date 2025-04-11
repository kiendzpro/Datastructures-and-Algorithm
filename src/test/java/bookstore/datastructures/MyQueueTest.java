package bookstore.datastructures;

import org.junit.Test;
import static org.junit.Assert.*;

public class MyQueueTest {
    
    @Test
    public void testEnqueueAndDequeue() {
        MyQueue<String> queue = new MyQueue<>();
        
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");
        
        assertEquals("Size should be 3", 3, queue.size());
        assertEquals("First element should be 'First'", "First", queue.peek());
        
        assertEquals("Dequeued element should be 'First'", "First", queue.dequeue());
        assertEquals("Size should be 2 after dequeue", 2, queue.size());
        assertEquals("New first element should be 'Second'", "Second", queue.peek());
        
        assertEquals("Dequeued element should be 'Second'", "Second", queue.dequeue());
        assertEquals("Dequeued element should be 'Third'", "Third", queue.dequeue());
        
        assertTrue("Queue should be empty after all elements are dequeued", queue.isEmpty());
    }
    
    @Test
    public void testIsEmpty() {
        MyQueue<Integer> queue = new MyQueue<>();
        
        assertTrue("New queue should be empty", queue.isEmpty());
        
        queue.enqueue(1);
        assertFalse("Queue with element should not be empty", queue.isEmpty());
        
        queue.dequeue();
        assertTrue("Queue should be empty after dequeue", queue.isEmpty());
    }
    
    @Test(expected = IllegalStateException.class)
    public void testDequeueEmpty() {
        MyQueue<String> queue = new MyQueue<>();
        
        queue.dequeue();
    }
    
    @Test(expected = IllegalStateException.class)
    public void testPeekEmpty() {
        MyQueue<String> queue = new MyQueue<>();
        
        queue.peek();
    }
} 
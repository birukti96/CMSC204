import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MyStackTest {
    public MyQueue<String> stringQ;
    public String a = "a", b = "b", c = "c", d = "d", e = "e", f = "f";
    public ArrayList<String> fill = new ArrayList<String>();

    // STUDENT: student tests will use the doubleQ
    public MyQueue<Double> doubleQ;
    public Double num1 = 1.1, num2 = 2.2, num3 = 3.3, num4 = 4.4;

    @BeforeEach
    public void setUp() throws Exception {
        stringQ = new MyQueue<String>(5);
        stringQ.enqueue(a);
        stringQ.enqueue(b);
        stringQ.enqueue(c);

        // STUDENT: setup for doubleQ
        doubleQ = new MyQueue<Double>(4);
        doubleQ.enqueue(num1);
        doubleQ.enqueue(num2);
        doubleQ.enqueue(num3);
    }

    @AfterEach
    public void tearDown() throws Exception {
        stringQ = null;
        doubleQ = null;
    }

    @Test
    public void testIsEmpty() throws QueueUnderflowException {
        assertEquals(false, stringQ.isEmpty());
        stringQ.dequeue();
        stringQ.dequeue();
        stringQ.dequeue();
        assertEquals(true, stringQ.isEmpty());
    }

    @Test
    public void testDequeue() {
        try {
            assertEquals(a, stringQ.dequeue());
            assertEquals(b, stringQ.dequeue());
            assertEquals(c, stringQ.dequeue());
            // Queue is empty, next statement should cause QueueUnderFlowException
            stringQ.dequeue();
            assertTrue("This should have caused a QueueUnderflowException", false);
        } catch (QueueUnderflowException e) {
            assertTrue("This should have caused a QueueUnderflowException", true);
        } catch (Exception e) {
            assertTrue("This should have caused a QueueUnderflowException", false);
        }
    }

    @Test
    public void testDequeueStudent() {
        // Test the dequeue operation for doubleQ
        try {
            assertEquals(num1, doubleQ.dequeue());
            assertEquals(num2, doubleQ.dequeue());
            assertEquals(num3, doubleQ.dequeue());
            // Queue is empty, should cause QueueUnderflowException
            doubleQ.dequeue();
            assertTrue("This should have caused a QueueUnderflowException", false);
        } catch (QueueUnderflowException e) {
            assertTrue("This should have caused a QueueUnderflowException", true);
        } catch (Exception e) {
            assertTrue("Unexpected exception type", false);
        }
    }

    @Test
    public void testSize() throws QueueOverflowException, QueueUnderflowException {
        assertEquals(3, stringQ.size());
        stringQ.enqueue(d);
        assertEquals(4, stringQ.size());
        stringQ.dequeue();
        stringQ.dequeue();
        assertEquals(2, stringQ.size());
    }

    @Test
    public void testEnqueue() {
        try {
            assertEquals(3, stringQ.size());
            assertEquals(true, stringQ.enqueue(d));
            assertEquals(4, stringQ.size());
            assertEquals(true, stringQ.enqueue(e));
            assertEquals(5, stringQ.size());
            // Queue is full, next statement should cause QueueOverFlowException
            stringQ.enqueue(f);
            assertTrue("This should have caused a QueueOverflowException", false);
        } catch (QueueOverflowException e) {
            assertTrue("This should have caused a QueueOverflowException", true);
        } catch (Exception e) {
            assertTrue("This should have caused a QueueOverflowException", false);
        }
    }

    @Test
    public void testEnqueueStudent() {
        // Test the enqueue operation for doubleQ
        try {
            assertEquals(3, doubleQ.size());
            assertEquals(true, doubleQ.enqueue(num4));
            assertEquals(4, doubleQ.size());
            // Queue is full, should cause QueueOverflowException
            doubleQ.enqueue(5.5);
            assertTrue("This should have caused a QueueOverflowException", false);
        } catch (QueueOverflowException e) {
            assertTrue("This should have caused a QueueOverflowException", true);
        } catch (Exception e) {
            assertTrue("Unexpected exception type", false);
        }
    }

    @Test
    public void testIsFull() throws QueueOverflowException {
        assertEquals(false, stringQ.isFull());
        stringQ.enqueue(d);
        stringQ.enqueue(e);
        assertEquals(true, stringQ.isFull());
    }

    @Test
    public void testToString() throws QueueOverflowException {
        assertEquals("abc", stringQ.toString());
        stringQ.enqueue(d);
        assertEquals("abcd", stringQ.toString());
        stringQ.enqueue(e);
        assertEquals("abcde", stringQ.toString());
    }

    @Test
    public void testToStringStudent() {
        // Test the toString method for doubleQ
        assertEquals("1.1 2.2 3.3", doubleQ.toString(" "));
        try {
			doubleQ.enqueue(num4);
		} catch (QueueOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        assertEquals("1.1 2.2 3.3 4.4", doubleQ.toString(" "));
    }

    @Test
    public void testToStringDelimiter() throws QueueOverflowException {
        assertEquals("a%b%c", stringQ.toString("%"));
        stringQ.enqueue(d);
        assertEquals("a&b&c&d", stringQ.toString("&"));
        stringQ.enqueue(e);
        assertEquals("a/b/c/d/e", stringQ.toString("/"));
    }

    @Test
    public void testFill() throws QueueUnderflowException {
        fill.add("apple");
        fill.add("banana");
        fill.add("carrot");
        // start with an empty queue
        stringQ = new MyQueue<String>(5);
        // fill with an ArrayList
        stringQ.fill(fill);
        assertEquals(3, stringQ.size());
        assertEquals("apple", stringQ.dequeue());
        assertEquals("banana", stringQ.dequeue());
        assertEquals("carrot", stringQ.dequeue());
    }

}

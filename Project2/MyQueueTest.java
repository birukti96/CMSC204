import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MyQueueTest {
    public MyStack<String> stringS;
    public String a = "a", b = "b", c = "c", d = "d", e = "e", f = "f";
    public ArrayList<String> fill = new ArrayList<String>();

    // STUDENT: student tests will use the doubleS
    public MyStack<Double> doubleS;
    public Double num1 = 1.1, num2 = 2.2, num3 = 3.3, num4 = 4.4;

    @BeforeEach
    public void setUp() throws Exception {
        stringS = new MyStack<String>(5);
        stringS.push(a);
        stringS.push(b);
        stringS.push(c);

        // STUDENT: setup for doubleS stack
        doubleS = new MyStack<Double>(4);
        doubleS.push(num1);
        doubleS.push(num2);
        doubleS.push(num3);
    }

    @AfterEach
    public void tearDown() throws Exception {
        stringS = null;
        doubleS = null;
    }

    @Test
    public void testIsEmpty() throws StackUnderflowException {
        assertEquals(false, stringS.isEmpty());
        stringS.pop();
        stringS.pop();
        stringS.pop();
        assertEquals(true, stringS.isEmpty());
    }

    @Test
    public void testIsFull() throws StackOverflowException {
        assertEquals(false, stringS.isFull());
        stringS.push(d);
        stringS.push(e);
        assertEquals(true, stringS.isFull());
    }

    @Test
    public void testPop() {
        try {
            assertEquals(c, stringS.pop());
            assertEquals(b, stringS.pop());
            assertEquals(a, stringS.pop());
            // Queue is empty, next statement should cause QueueUnderFlowException
            stringS.pop();
            assertTrue("This should have caused an StackUnderflowException", false);
        } catch (StackUnderflowException e) {
            assertTrue("This should have caused an StackUnderflowException", true);
        } catch (Exception e) {
            assertTrue("This should have caused an StackUnderflowException", false);
        }
    }

    @Test
    public void testPopStudent() {
        try {
            assertEquals(num3, doubleS.pop());
            assertEquals(num2, doubleS.pop());
            assertEquals(num1, doubleS.pop());
            doubleS.pop(); // This should cause an exception
            assertTrue("This should have caused a StackUnderflowException", false);
        } catch (StackUnderflowException e) {
            assertTrue("This should have caused a StackUnderflowException", true);
        } catch (Exception e) {
            assertTrue("Unexpected exception type", false);
        }
    }

    @Test
    public void testTop() throws StackUnderflowException, StackOverflowException {
        assertEquals(c, stringS.top());
        stringS.push(d);
        assertEquals(d, stringS.top());
        stringS.pop();
        stringS.pop();
        assertEquals(b, stringS.top());
    }

    @Test
    public void testSize() throws StackOverflowException, StackUnderflowException {
        assertEquals(3, stringS.size());
        stringS.push(d);
        assertEquals(4, stringS.size());
        stringS.pop();
        stringS.pop();
        assertEquals(2, stringS.size());
    }

    @Test
    public void testPush() {
        try {
            assertEquals(3, stringS.size());
            assertEquals(true, stringS.push(d));
            assertEquals(4, stringS.size());
            assertEquals(true, stringS.push(e));
            assertEquals(5, stringS.size());
            // Queue is full, next statement should cause QueueOverFlowException
            stringS.push(f);
            assertTrue("This should have caused an StackOverflowException", false);
        } catch (StackOverflowException e) {
            assertTrue("This should have caused an StackOverflowException", true);
        } catch (Exception e) {
            assertTrue("Unexpected exception type", false);
        }
    }

    @Test
    public void testPushStudent() {
        try {
            assertEquals(3, doubleS.size());
            assertEquals(true, doubleS.push(num4));
            assertEquals(4, doubleS.size());
            // Stack is full, next statement should cause StackOverflowException
            doubleS.push(5.5);
            assertTrue("This should have caused a StackOverflowException", false);
        } catch (StackOverflowException e) {
            assertTrue("This should have caused a StackOverflowException", true);
        } catch (Exception e) {
            assertTrue("Unexpected exception type", false);
        }
    }

    @Test
    public void testToString() throws StackOverflowException {
        assertEquals("abc", stringS.toString());
        stringS.push(d);
        assertEquals("abcd", stringS.toString());
        stringS.push(e);
        assertEquals("abcde", stringS.toString());
    }

    @Test
    public void testToStringStudent() throws StackOverflowException {
        assertEquals("1.1 2.2 3.3", doubleS.toString(" "));
        doubleS.push(num4);
        assertEquals("1.1 2.2 3.3 4.4", doubleS.toString(" "));
    }

    @Test
    public void testToStringDelimiter() throws StackOverflowException {
        assertEquals("a%b%c", stringS.toString("%"));
        stringS.push(d);
        assertEquals("a&b&c&d", stringS.toString("&"));
        stringS.push(e);
        assertEquals("a/b/c/d/e", stringS.toString("/"));
    }

    @Test
    public void testFill() throws StackUnderflowException {
        fill.add("apple");
        fill.add("banana");
        fill.add("carrot");
        // start with an empty queue
        stringS = new MyStack<String>(5);
        // fill with an ArrayList
        stringS.fill(fill);
        assertEquals(3, stringS.size());
        assertEquals("carrot", stringS.pop());
        assertEquals("banana", stringS.pop());
        assertEquals("apple", stringS.pop());
    }

}

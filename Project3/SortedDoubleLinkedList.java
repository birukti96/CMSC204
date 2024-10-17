/*
 * Class: CMSC204  CRN 21437
 * Instructor: Prof. Huseyin Aygun
 * Description:  
 * Due: 10/16/2024
 * Platform/compiler:
 * I pledge that I have completed the programming 
 * assignment independently. I have not copied the code 
 * from a student or any source. I have not given my code 
 * to any student.
   Print your Name here: Biruktawit Sibanie
*/

import java.util.Comparator;
import java.util.ListIterator;

/**
 * A sorted doubly linked list that maintains its elements in sorted order, 
 * based on the comparator provided at instantiation.
 * @param <T> Generic type parameter
 */
public class SortedDoubleLinkedList<T> extends BasicDoubleLinkedList<T> {

    private Comparator<T> comparator;

    /**
     * Constructs an empty list that will use the given comparator 
     * to determine the order of elements.
     * @param comparator Comparator used to compare list elements
     */
    public SortedDoubleLinkedList(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Adds the specified data in the appropriate sorted position in the list.
     * @param data Element to be added
     * @return A reference to this SortedDoubleLinkedList instance
     * @throws NullPointerException if the data is null
     */
    public SortedDoubleLinkedList<T> add(T data) {
        if (data == null) {
            throw new NullPointerException("Null elements cannot be added to the list.");
        }

        Link newLink = new Link(data);
        Link current = head;
        Link previous = null;

        // Traverse the list until the correct insertion point is found
        while (current != null && comparator.compare(current.data, data) < 0) {
            previous = current;
            current = current.next;
        }

        // Insertion at the front of the list if no previous link exists
        if (previous == null) {
            super.addToFront(data);
        } 
        // Insert at the end of the list if no next link exists
        else if (current == null) {
            super.addToEnd(data);
        } 
        // Insert between two nodes
        else {
            previous.next = newLink;
            newLink.previous = previous;
            newLink.next = current;
            current.previous = newLink;
            size++;
        }

        return this;
    }

    @Override
    /**
     * Adding elements directly to the end is not supported, 
     * as it would violate the list's sorted nature.
     * @throws UnsupportedOperationException whenever this operation is attempted
     */
    public SortedDoubleLinkedList<T> addToEnd(T data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Invalid operation for sorted list");
    }

    @Override
    /**
     * Adding elements directly to the front is not supported, 
     * as it would break the sorting of the list.
     * @throws UnsupportedOperationException whenever this operation is attempted
     */
    public SortedDoubleLinkedList<T> addToFront(T data) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Invalid operation for sorted list");
    }

    @Override
    /**
     * Returns an iterator starting at the first element of the list.
     * @return A ListIterator positioned at the head of the list
     */
    public ListIterator<T> iterator() {
        return super.iterator();
    }

    @Override
    /**
     * Removes the specified element from the list, if it exists, 
     * by using the provided comparator for equality checks.
     * @param targetData The element to be removed
     * @param comparator Comparator used for equality checking
     * @return A reference to this SortedDoubleLinkedList instance
     */
    public SortedDoubleLinkedList<T> remove(T targetData, Comparator<T> comparator) {
        super.remove(targetData, comparator);
        return this;
    }
}

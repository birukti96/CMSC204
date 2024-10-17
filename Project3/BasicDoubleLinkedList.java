/*
 * Class: CMSC204 CRN 21437
 * Instructor: Prof. Huseyin Aygun
 * Description: 
 * Due: 10/16/2024
 * Platform/compiler: 
 * I pledge that I have completed the programming
 * assignment independently. I have not copied the code
 * from a student or any source. I have not given my code
 * to any student.
   Print your Name here: Naol Gobena
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * BasicDoubleLinkedList class representing a doubly linked list
 * @param <T> Generic type parameter
 */
public class BasicDoubleLinkedList<T> {
    protected Link head;
    protected Link tail;
    protected int size = 0;

    /**
     * Constructor initializes an empty list
     */
    public BasicDoubleLinkedList() {
        head = null;
        tail = null;
    }

    /**
     * Retrieves the current size of the list
     * @return number of elements in the list
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the list contains no elements
     * @return true if the list is empty, otherwise false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Appends an element to the end of the list
     * @param data the data to be added
     * @return a reference to the current list
     */
    public BasicDoubleLinkedList<T> addToEnd(T data) {
        Link newLink = new Link(data);
        if (isEmpty()) {
            head = newLink;
        } else {
            tail.next = newLink;
            newLink.previous = tail;
        }
        tail = newLink;
        size++;
        return this;
    }

    /**
     * Prepends an element to the beginning of the list
     * @param data the data to be added
     * @return a reference to the current list
     */
    public BasicDoubleLinkedList<T> addToFront(T data) {
        Link newLink = new Link(data);
        if (isEmpty()) {
            tail = newLink;
        } else {
            newLink.next = head;
            head.previous = newLink;
        }
        head = newLink;
        size++;
        return this;
    }

    /**
     * Retrieves the first element in the list
     * @return the first element or null if the list is empty
     */
    public T getFirst() {
        return isEmpty() ? null : head.data;
    }

    /**
     * Retrieves the last element in the list
     * @return the last element or null if the list is empty
     */
    public T getLast() {
        return isEmpty() ? null : tail.data;
    }

    /**
     * Removes the first occurrence of the specified element
     * @param targetData data element to be removed
     * @param comparator comparator used for matching elements
     * @return the modified list
     */
    public BasicDoubleLinkedList<T> remove(T targetData, Comparator<T> comparator) {
        Link current = head;

        while (current != null) {
            if (comparator.compare(targetData, current.data) == 0) {
                if (current == head) {
                    head = current.next;
                    if (head != null) {
                        head.previous = null;
                    }
                } else if (current == tail) {
                    tail = current.previous;
                    if (tail != null) {
                        tail.next = null;
                    }
                } else {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                }
                size--;
                return this;
            }
            current = current.next;
        }

        return this; // Target data not found
    }

    /**
     * Removes and returns the first element from the list
     * @return the first element or null if the list is empty
     */
    public T retrieveFirstElement() {
        if (isEmpty()) {
            return null;
        }
        T data = getFirst();
        if (head == tail) {
            head = tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        size--;
        return data;
    }

    /**
     * Removes and returns the last element from the list
     * @return the last element or null if the list is empty
     */
    public T retrieveLastElement() {
        if (isEmpty()) {
            return null;
        }
        T data = getLast();
        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }
        size--;
        return data;
    }

    /**
     * Converts the list to an ArrayList containing all elements
     * @return an ArrayList with the elements in the list
     */
    public ArrayList<T> toArrayList() {
        ArrayList<T> arrayList = new ArrayList<>();
        ListIterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next());
        }
        return arrayList;
    }

    /**
     * Returns a new iterator for the list
     * @return a ListIterator for the current list
     */
    public ListIterator<T> iterator() {
        return new DoubleLinkedListIterator();
    }

    /**
     * Internal class representing a link in the doubly linked list
     */
    protected class Link {
        public Link previous;
        public Link next;
        public T data;

        /**
         * Constructs a new link with the provided data
         * @param data the data to be stored in the link
         */
        Link(T data) {
            this.data = data;
            this.previous = null;
            this.next = null;
        }
    }

    /**
     * Iterator class for the doubly linked list
     */
    protected class DoubleLinkedListIterator implements ListIterator<T> {
        private Link previousLink;
        private Link currentLink;

        /**
         * Initializes the iterator
         */
        DoubleLinkedListIterator() {
            previousLink = null;
            currentLink = head;
        }

        @Override
        public boolean hasNext() {
            return currentLink != null;
        }

        @Override
        public boolean hasPrevious() {
            return previousLink != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            previousLink = currentLink;
            currentLink = currentLink.next;
            return previousLink.data;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            currentLink = previousLink;
            previousLink = previousLink.previous;
            return currentLink.data;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(T e) {
            throw new UnsupportedOperationException();
        }
    }
}

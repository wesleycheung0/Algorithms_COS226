/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 * % custom checkstyle checks for Deque.java
*-----------------------------------------------------------
[WARN] Deque.java:78:20: Did you mean to construct a new 'Node' in 'removeLast()'? Or did you mean to create a reference to an existing 'Node' instead? [Design]
Checkstyle ends with 0 errors and 1 warning.
*
*
*
*
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last = null;
    private int size;

    private class Node {
        private Item item; // The item in the node
        private Node next; // pointer to next item
        private Node previous; // pointer to previous item
    }

    // constructor of an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        // Throw error if the item is null
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (first == null) {
            first = new Node();
            first.item = item;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        if (last == null && size == 0) {
            last = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        // Throw error if the item is null
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (last != null) {
            Node oldLast = last;
            Node newLast = new Node();
            newLast.item = item;
            oldLast.next = newLast;
            newLast.previous = oldLast;
            last = newLast;
        } else {
            last = new Node(); // Declare new when it is null
            last.item = item;
        }
        if (first == null && size == 0) {
            first = last;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        //Throw error if Node are null
        if (first == null) {
            throw new NoSuchElementException();
        }

        Item firstItem = first.item;
        if (first.next != null) {
            first = first.next;
        } else {
            first = null;
            if (size == 1) {
                last = null;
            }
        }
        size--;
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        //Throw error if Node are null
        if (last == null) {
            throw new NoSuchElementException();
        }

        Item lastItem = last.item;
        if (last.previous != null && size != 1) {
            last = last.previous;
            last.next = null;
        } else {
            last = null;
            if (size == 1) {
                first = null;
            }
        }
        size--;
        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;
//        private int sizeI = size;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> temp = new Deque<String>();
//        temp.addFirst("Apple");
//        temp.addLast("Pear");
//        System.out.println(temp.removeFirst() + temp.removeLast() + temp.size());
//        temp.addFirst("Apple");
//        temp.addLast("Pear");
//        System.out.println(temp.removeFirst() + temp.removeLast() + temp.size());

        temp.addFirst("Apple");
        temp.removeFirst();
        temp.addFirst("Pear");
        System.out.println(temp.removeLast());
        Deque<String> temp2 = new Deque<String>();

        temp2.addLast("Mochi");
        temp2.addFirst("Mochi2");
        System.out.println(temp2.removeFirst());
        System.out.println(temp2.removeFirst());
        temp2.addLast("Ice Cream");
        System.out.println(temp2.removeFirst());

        Deque<Integer> deque = new Deque<Integer>();
        deque.addLast(1);
        deque.addFirst(2);
        deque.addLast(3);
        deque.removeLast();
        deque.size();
        deque.removeFirst();
        deque.isEmpty();
        System.out.println(deque.removeLast());
        deque.addLast(9);
        deque.addLast(10);
        System.out.println(deque.removeFirst());

        Deque<Integer> dequeA = new Deque<Integer>();
        dequeA.addFirst(1);
        dequeA.removeFirst();
        dequeA.addLast(3);
        dequeA.removeLast();
        dequeA.addLast(9);

        for (Iterator<Integer> it = dequeA.iterator(); it.hasNext(); ) {
            Integer n = it.next();
            System.out.println(n);
        }


        //        temp.addLast("Pear");
//        temp.addLast("Blueberry");
//        temp.addLast("Strawberry");
//        temp.addLast("Blueberry");
//        temp.addLast("Pear");
//        temp.removeFirst();
//
//        for (Iterator<String> it = temp.iterator(); it.hasNext(); ) {
//            String n = it.next();
//            System.out.println(n);
//        }
    }
}

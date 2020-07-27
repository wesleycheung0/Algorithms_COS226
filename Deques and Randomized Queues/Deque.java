/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last = null;
    private int size;

    private class Node {
        private Item item; // The item in the node
        private Node next; // Reference to next item
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (last != null) {
            Node oldLast = last;
            Node newLast = new Node();
            newLast.item = item;
            oldLast.next = newLast;
            last = newLast;
        } else {
            last = new Node();// Declare new when it is null
            last.item = item;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item temp = first.item;
        first = first.next;
        size--;
        return temp;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item lastItem = last.item;
        last = new Node();
        size--;
        return lastItem;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (size == 0) {
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
        temp.addFirst("Apple");
        temp.addLast("Pear");
        System.out.println(temp.removeFirst() + temp.removeLast() + temp.size());
        temp.addFirst("Apple");
        temp.addLast("Pear");
        System.out.println(temp.removeFirst() + temp.removeLast() + temp.size());

    }
}

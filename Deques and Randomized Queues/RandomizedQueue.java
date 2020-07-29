/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 * 1. [unchecked] unchecked cast
 *[INFO] RandomizedQueue.java:85: Using a loop in this method might be a performance bug. [Performance]
 * Test 3 (bonus): check that maximum size of any or Deque or RandomizedQueue object
                created is equal to k
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Node first;

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
    }

    private class Node {
        Item item;
        Node next;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    // remove and return a random item
    // Not Constant time

    public Item dequeue() {

        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node target = first;
        Node previous = null;
        int randomNum = StdRandom.uniform(size);
        if (randomNum == 0) {
            Item item = first.item;
            first = first.next;
            size--;
            return item;
        } else {
            int count = 0;
            //Find the item to output
            while (count < randomNum) {
                previous = target;
                target = target.next;
                count++;
            }
            assert previous != null;
            previous.next = target.next;
            Item output = target.item;
            size--;
            return output;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {

        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node copy = first;
        // Throw if is empty
        int random_num = StdRandom.uniform(size);
        int count = 0;
        while (count < random_num) {
            copy = copy.next;
            count++;
        }
        return copy.item;
    }

    // return an independent iterator over items in random order

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private int current = 0;
        private Item[] items;

        public ListIterator() {
            items = (Item[]) new Object[size()];
            Node node = first;
            int i = 0;
            while (node != null) {
                items[i++] = node.item;
                node = node.next;
            }
            StdRandom.shuffle(items);
        }

        public boolean hasNext() {
            return current < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return items[current++];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> test = new RandomizedQueue<>();
        test.enqueue("Apple");
        test.enqueue("apple");
        test.enqueue("APple");
        test.enqueue("ApPle");
        test.enqueue("AppLe");
        test.enqueue("AppLE");
        test.enqueue("AppLE");

        System.out.println(test.sample() + test.size + test.dequeue() + test.size);
        System.out.println(test.sample() + test.size + test.dequeue() + test.size);
        System.out.println(test.sample() + test.size + test.dequeue() + test.size);
        System.out.println(test.sample() + test.size + test.dequeue() + test.size);
        System.out.println(test.sample() + test.size + test.dequeue() + test.size);

        int n = 9;
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        // while (!queue.isEmpty()) {
        //     System.out.print(queue.dequeue());
        // }
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }


    }


}

/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Partner Name:    Ada Lovelace
 *  Partner NetID:   alovelace
 *  Partner Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {

        if (a == null || key == null
                || comparator == null) {  //if any of the inputs are null throw exception
            throw new java.lang.NullPointerException();
        }
        if (a.length == 0) {
            return -1;
        }

        int m = a.length / 2;
        int lo = 0;
        int hi = a.length - 1;
        while (lo + 1 < hi) {
            m = (lo + hi) / 2;
            if (comparator.compare(key, a[m]) <= 0) {
                hi = m;
            }
            else {
                lo = m;
            }
        }
        if (comparator.compare(a[lo], key) == 0) {
            return lo;
        }
        if (comparator.compare(a[hi], key) == 0) {
            return hi;
        }
        return -1;

    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null
                || comparator == null) {  //if any of the inputs are null throw exception
            throw new java.lang.NullPointerException();
        }
        if (a.length == 0) {
            return -1;
        }
        int m = a.length / 2;
        int lo = 0;
        int hi = a.length - 1;
        while (lo + 1 < hi) {
            m = (lo + hi) / 2;
            if (comparator.compare(key, a[m]) >= 0) {
                lo = m;
            }
            else {
                hi = m;
            }
        }
        if (comparator.compare(a[lo], key) == 0) {
            return lo;
        }
        if (comparator.compare(a[hi], key) == 0) {
            return hi;
        }
        return -1;

    }

    // unit testing (required)
    public static void main(String[] args) {
        String[] a = { "A", "A", "C", "G", "G", "T" };
        int index = BinarySearchDeluxe.lastIndexOf(a, "G", String.CASE_INSENSITIVE_ORDER);
        System.out.println(index);
    }
}

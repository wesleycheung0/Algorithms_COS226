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

import edu.princeton.cs.algs4.MergeX;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class Term implements Comparable<Term> {
    // Initializes a term with the given query string and weight.
    private String query;
    private long weight;

    public Term(String query, long weight) {

        //Throw exception

        if (query == null) {
            throw new java.lang.NullPointerException("String cannot be null");
        }
        if (weight < 0) {
            throw new java.lang.IllegalArgumentException("weight < 0");
        }

        this.query = query;
        this.weight = weight;
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ByReverseWeightOrder();
    }

    public static class ByReverseWeightOrder implements Comparator<Term> {

        // public int compare(Term o1, Term o2) {
        //     return (int) (o1.weight - o2.weight);
        // }
        public int compare(Term term1, Term term2) {
            if (term1.weight > term2.weight) {//if descending
                return -1;
            } else if (term1.weight == term2.weight) {//if equal
                return 0;
            } else {
                return 1;//if ascending
            }
        }

    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new java.lang.IllegalArgumentException("Prefix >0");
        }
        return new ByPrefixOrder(r);
    }

    public static class ByPrefixOrder implements Comparator<Term> {
        private int r;

        public ByPrefixOrder(int r) {
            this.r = r;
        }

        public int compare(Term t1, Term t2) {
            int r1 = t1.query.length() < r ? t1.query.length() : r;
            int r2 = t2.query.length() < r ? t2.query.length() : r;

            return t1.query.substring(0, r1).compareTo(t2.query.substring(0, r2));
        }

    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (Long.toString(weight) + "	" + query);
    }

    // unit testing (required)
    public static void main(String[] args) throws FileNotFoundException {
        String filename = args[0];
        // Read files
        Scanner text = new Scanner(new File(filename));
        int numRecord = text.nextInt();
        text.nextLine();
        Term[] record = new Term[numRecord];
        int count = 0;
        while (text.hasNextLong()) {
            long weight = text.nextLong();
            String query = text.next();
            record[count] = new Term(query, weight);
            text.nextLine();
            count++;
        }

        MergeX.sort(record, byReverseWeightOrder());

        for (int j = 0; j < record.length; j++) {
            System.out.println(record[j].toString());
        }


        // Skip the first line
        // for loop store in Team data structure
        // sort
        // print result

    }

}

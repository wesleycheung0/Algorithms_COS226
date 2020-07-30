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

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Autocomplete {
    // Initializes the data structure from the given array of terms.
    private Term[] terms;

    public Autocomplete(Term[] terms) {
        MergeX.sort(terms);
        this.terms = terms;
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        Term[] subset;
        Term test = new Term(prefix, 0);
        int firstIndex = BinarySearchDeluxe
                .firstIndexOf(terms, test, Term.byPrefixOrder(prefix.length()));
        int lastIndex = BinarySearchDeluxe
                .lastIndexOf(terms, test, Term.byPrefixOrder(prefix.length()));
        int length = lastIndex - firstIndex + 1; // Length of the subset
        if (firstIndex >= 0 && lastIndex > 0 && lastIndex > firstIndex && length != 0) {
            subset = new Term[length];
            int count = 0;
            for (int i = firstIndex; i <= lastIndex; i++) { //Subset the terms
                subset[count] = terms[i];
                count++;
            }

            MergeX.sort(subset,
                    Term.byReverseWeightOrder()); // Sort the subset with descending order of weight
        } else {
            subset = new Term[0];
        }

        return subset;


    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        Term test = new Term(prefix, 0);
        int firstIndex = BinarySearchDeluxe
                .firstIndexOf(terms, test, Term.byPrefixOrder(prefix.length()));
        int lastIndex = BinarySearchDeluxe
                .lastIndexOf(terms, test, Term.byPrefixOrder(prefix.length()));

        if (lastIndex - firstIndex == 0) {
            return 0;
        }
        return lastIndex - firstIndex + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {


        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}

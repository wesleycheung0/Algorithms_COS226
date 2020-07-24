/* *****************************************************************************
 *  Name:    Wesley Cheung
 *  NetID:   wCheung
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private boolean[][] grid = { };
    private WeightedQuickUnionUF wqUnion;
    private WeightedQuickUnionUF wqUnionBack;
    private final int n;
    private final int bottom;
    private int numberOfopenSite;

    // Constructor
    public Percolation(int n) {
        this.n = n;
        bottom = (n * n) + 1; // The virtual bottom
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than zero");
        }
        else {
            grid = new boolean[n][n];
            // Mark all site as blocked
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    grid[i][j] = false;
                }
            }
            wqUnion = new WeightedQuickUnionUF((n * n) + 2);
            wqUnionBack = new WeightedQuickUnionUF((n * n) + 1);
        }
    }

    // Convert row and col to index in Quick Union
    private int xyToIndex(int i, int j) {
        // Attention: i and j are of range 1 ~ N, NOT 0 ~ N-1.
        // Throw IndexOutOfBoundsException if i or j is not valid
        if (i < 0 || i >= n)
            throw new IndexOutOfBoundsException("row i out of bound");
        if (j < 0 || j >= n)
            throw new IndexOutOfBoundsException("column j out of bound");

        return ((i * n + j) + 1);
    }

    // To validate row and col are in bound
    private void validate(int row, int col) {
        if (row >= n || col >= n || row < 0 || col < 0) {
            throw new IllegalArgumentException(
                    "Outside of range" + Integer.toString(row) + Integer.toString(col));
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // Edited coursera
        row = row - 1;
        col = col - 1;

        validate(row, col);

        if (!grid[row][col]) { // duplicate logical test
            grid[row][col] = true; // Mark the site as open
            numberOfopenSite++;
            openHelper(1, 0, row, col);
            openHelper(0, 1, row, col);
            openHelper(-1, 0, row, col);
            openHelper(0, -1, row, col);

            // If the site is on the first row, connect with the top virtual site
            if (row == 0) {
                wqUnion.union(0, xyToIndex(row, col));
                wqUnionBack.union(0, xyToIndex(row, col));
            }
            // If the site is on the last row, connect with the bottom virtual site
            if (row == (n - 1)) {
                wqUnion.union(xyToIndex(row, col), bottom);
                // wqUnionBack not connected to bottom
            }
        }

    }

    // Helper method for Opening method
    private void openHelper(int i, int j, int row, int col) {
        if (inIndex(row + i, col + j) && isOpen(row + i + 1, col + j + 1)) { // Edited
            wqUnion.union(xyToIndex(row + i, col + j), xyToIndex(row, col));
            wqUnionBack.union(xyToIndex(row + i, col + j), xyToIndex(row, col));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        // Edited coursera
        row = row - 1;
        col = col - 1;

        validate(row, col);
        return (grid[row][col]); // if the site is open or not
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        // Edited coursera
        row = row - 1;
        col = col - 1;

        validate(row, col);

        // if it connects to the top virtual node
        if (isOpen(row + 1, col + 1)) { // Edited coursera
            return wqUnionBack.find(0) == wqUnionBack.find(xyToIndex(row, col));
        }
        return false;
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        // int sum = 0;
        // // Not taking constant time
        // for (int i = 0; i < n; i++) {
        //     for (int j = 0; j < n; j++) {
        //         if (grid[i][j] == true) sum += 1;
        //     }
        // }
        return numberOfopenSite;
    }

    // does the system percolate?
    public boolean percolates() {
        // if the top virtual node connect with bottom node
        return wqUnion.find(0) == wqUnion.find(bottom);
    }

    // To check whether the row and col is inBound
    private boolean inIndex(int row, int col) {
        if (row < n && row >= 0) {
            if (col < n && col >= 0) {
                return true;
            }
        }
        return false;
    }


    // unit testing (required)
    public static void main(String[] args) {
        Percolation test = new Percolation(100);
        while (!test.percolates()) {
            // random select a site and open it
            int randomRow = StdRandom.uniform(100);
            int randomCol = StdRandom.uniform(100);
            if (!test.isOpen(randomRow, randomCol)) {
                test.open(randomRow, randomCol);
            }
        }
        double probability = (double) test.numberOfOpenSites() / (double) 10000;
        System.out.println((double) test.numberOfOpenSites());
        System.out.println(probability);
    }

}


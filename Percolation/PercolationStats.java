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

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final double[] probabilityArray;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        probabilityArray = new double[trials];
        int size = n * n;
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            while (!test.percolates()) {
                // random select a site and open it
                int randomRow = StdRandom.uniform(n) + 1; // Edited
                int randomCol = StdRandom.uniform(n) + 1; // Edited
                if (!test.isOpen(randomRow, randomCol)) {
                    test.open(randomRow, randomCol);
                }
            }
            double probability = (double) test.numberOfOpenSites() / (double) (size);
            probabilityArray[i] = probability;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double mean = StdStats.mean(probabilityArray);
        return (mean);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double sd = StdStats.stddev(probabilityArray);
        // System.out.println("stddev()" + sd);
        return (sd);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double sd = stddev();
        double mean = mean();
        // System.out.println("confidenceLow() = " + cL);
        return mean - (1.96 * sd / (Math.sqrt(probabilityArray.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double sd = stddev();
        double mean = mean();
        double cH = mean + (1.96 * sd / (Math.sqrt(probabilityArray.length)));
        // System.out.println("confidenceHigh() = " + cH);
        return cH;
    }

    // test client (see below)

    public static void main(String[] args) {

        // Scanner input = new Scanner(System.in);
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));

        double mean = test.mean();
        double sd = test.stddev();
        double cH = test.confidenceHi();
        double cL = test.confidenceLo();

        System.out.println("mean()          =" + mean);
        System.out.println("stddev          =" + sd);
        System.out.println("95% confidence Interval          =[" + cL + ", " + cH + "]");
    }
}


import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.RedBlackBST;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

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

public class PointST<Value> {
    private RedBlackBST<Point2D, Value> table;
    private int size;

    // construct an empty symbol table of points
    public PointST() {
        table = new RedBlackBST<Point2D, Value>();
        size = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points
    public int size() {
        return size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        if (p == null) {
            throw new NullPointerException();
        }
        if (!table.contains(p)) {
            table.put(p, val);
            size++;
        }
    }

    // value associated with point p
    public Value get(Point2D p) {
        return table.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return table.contains(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return table.keys();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> rangePoint = new Queue<Point2D>();
        for (Point2D point : table.keys()) {
            if (rect.contains(point)) {
                rangePoint.enqueue(point);
            }
        }
        return rangePoint;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        Point2D minDpoint = p;
        Double minDistance = 100.0;
        for (Point2D point : table.keys()) {
            if (point.distanceSquaredTo(p) < minDistance) {
                minDpoint = point;
                minDistance = point.distanceSquaredTo(p);
            }
        }
        return minDpoint;

    }

    // unit testing (required)
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner in = new Scanner(file);
        PointST<Double> point = new PointST<Double>();
        int count = 0;
        while (in.hasNextDouble()) {
            Point2D thePoint = new Point2D(in.nextDouble(), in.nextDouble());
            point.put(thePoint, (double) count);
            count++;
        }

        // System.out.println(point.contains(new Point2D(0.533381, 0.465703)));

        long start = 0;
        int runs = 10;
        Random rd = new Random();
        start = System.nanoTime();
        for (int i = 0; i < runs; i++) {
            double one = rd.nextDouble();
            double two = rd.nextDouble();
            point.nearest(new Point2D(one, two));
        }
        long time = System.nanoTime() - start;
        System.out.printf("Each find nearest took an average of %,d ns%n", time / runs);


    }

}

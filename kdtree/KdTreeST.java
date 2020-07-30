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

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class KdTreeST<Value> {

    private static final double XMIN = 0.0;
    private static final double YMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMAX = 1.0;
    private int size; // size of the KD tree
    private Node root; // Root Node of KD tree

    private static class Node {
        private Point2D p;
        private RectHV rect;
        private Node left;
        private Node right;

        public Node(Point2D p, RectHV r) {
            if (r == null) {
                r = new RectHV(0, 0, 1, 1);
            }
            this.rect = r;
            this.p = p;
            left = null;
            right = null;
        }

    }


    public KdTreeST() {
        root = null;
        size = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points
    public int size() {
        return size;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        root = put(root, p, XMIN, YMIN, XMAX, YMAX, 0);
    }

    public Node put(Node x, Point2D p, double xmin, double ymin, double xmax,
                    double ymax, int level) {
        if (x == null) {
            size++;
            RectHV r = new RectHV(xmin, ymin, xmax, ymax);
            return new Node(p, r);
        }
        int cmp = cmp(x.p, p, level);
        // If current node larger than the target point
        if (cmp > 0) {
            // If it is in even level
            if (level % 2 == 0) {
                level++;
                x.left = put(x.left, p, xmin, ymin, x.p.x(), ymax, level);
            }
            // If it is in odd level
            else {
                level++;
                x.left = put(x.left, p, xmin, ymin, xmax, x.p.y(), level);
            }
        }
        else {
            if (level % 2 == 0) {
                level++;
                x.right = put(x.right, p, x.p.x(), ymin, xmax, ymax, level);
            }
            else {
                level++;
                x.right = put(x.right, p, xmin, x.p.y(), xmax, ymax, level);
            }
        }
        return x;
    }

    // Custom compare function (a: current node, b: target node)
    private int cmp(Point2D a, Point2D b, int level) {
        if (level % 2 == 0) {
            int cmpResult = Double.compare(a.x(), b.x());
            return cmpResult;
        }
        else {
            int cmpResult = Double.compare(a.y(), b.y());
            return cmpResult;
        }
    }

    // value associated with point p

    public Value get(Point2D p) {
        return get(root, p, 0);
    }

    private Value get(Node n, Point2D p, int level) {
        Node x = root;
        int depth = 0;
        while (x != null) {
            int cmp = cmp(p, x.p, depth);
            if (cmp < 0) return get(x.left, p, depth++);
            else if (cmp > 0) return get(x.right, p, depth++);
            else
                return (Value) n.p;
        }
        return null;
    }


    public boolean contains(Point2D p) {
        return (contains(root, p, 0)) != null;
    }

    // does the symbol table contain point p?
    private Point2D contains(Node n, Point2D p, int level) {
        while (n != null) {
            int cmp = cmp(p, n.p, 0);
            if (cmp < 0) return contains(n.left, p, level++);
            else if (cmp > 0) return contains(n.right, p, level++);
            else
                return n.p;
        }
        return null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        Queue<Node> nextLevel = new Queue<Node>();
        Queue<Point2D> result = new Queue<Point2D>();
        result.enqueue(root.p);
        nextLevel.enqueue(root);
        while (!nextLevel.isEmpty()) {
            Node current = nextLevel.dequeue();
            if (current.left != null) {
                nextLevel.enqueue(current.left);
            }
            if (current.right != null) {
                nextLevel.enqueue(current.right);
            }
            if (current.left != null) {
                result.enqueue(current.left.p);
            }
            if (current.right != null) {
                result.enqueue(current.right.p);
            }
        }
        return result;

    }

    // all points that are inside the rectangle (or on the boundary)

    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();
        rangeAdd(root, rect, queue);
        return queue;
    }

    // all points that are inside the rectangle
    private void rangeAdd(Node d, RectHV rect, Queue<Point2D> queue) {
        if (d == null) {
            return;
        }
        if (rect.contains(d.p)) {
            queue.enqueue(d.p);
        }
        if (d.left != null && rect.intersects(d.left.rect))
            rangeAdd(d.left, rect, queue);
        if (d.right != null && rect.intersects(d.right.rect))
            rangeAdd(d.right, rect, queue);
    }

    public Point2D nearest(Point2D p) {
        if (isEmpty())
            return null;
        Boolean vert = true;
        Point2D min = nearest(root, p, root.p, vert);
        return min;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Node node, Point2D p, Point2D min, boolean vertical) {
        if (node == null) {
            return min;
        }
        if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(min))
            min = node.p;

        // Recursive Case
        if (vertical) {
            // Going to the right if the node is less than query point
            if (node.p.x() < p.x()) {
                min = nearest(node.right, p, min, !vertical);
                // Check if it is possible there is closer neightbor on the other side
                if (node.left != null && (min.distanceSquaredTo(p)
                        > node.left.rect.distanceSquaredTo(p))) {
                    min = nearest(node.left, p, min, !vertical);
                }
            }
            else {
                min = nearest(node.left, p, min, !vertical);
                if (node.right != null && (min.distanceSquaredTo(p)
                        > node.right.rect.distanceSquaredTo(p))) {
                    min = nearest(node.right, p, min, !vertical);
                }
            }
        }
        else {
            if (node.p.y() < p.y()) {
                min = nearest(node.right, p, min, !vertical);
                if (node.left != null
                        && (min.distanceSquaredTo(p)
                        > node.left.rect.distanceSquaredTo(p)))
                    min = nearest(node.left, p, min, !vertical);
            }
            else {
                min = nearest(node.left, p, min, !vertical);
                if (node.right != null
                        && (min.distanceSquaredTo(p)
                        > node.right.rect.distanceSquaredTo(p)))
                    min = nearest(node.right, p, min, !vertical);
            }
        }
        return min;


    }


    // unit testing (required)
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner in = new Scanner(file);
        KdTreeST<Double> point = new KdTreeST<Double>();
        int count = 0;
        while (in.hasNextDouble()) {
            Point2D thePoint = new Point2D(in.nextDouble(), in.nextDouble());
            point.put(thePoint, (double) count);
            count++;
        }


        System.out.println(point.nearest(new Point2D(0.4, 0.4)));
    }

    // long start = 0;
    // int runs = 10000;
    // start = System.nanoTime();
    // for (int i = 0; i < runs; i++) {
    //     double one = StdRandom.uniform();
    //     double two = StdRandom.uniform();
    //     point.nearest(new Point2D(one, two));
    // }
    // long time = System.nanoTime() - start;
    // System.out.printf("Each find nearest took an average of %,d ns%n", time / runs);

}


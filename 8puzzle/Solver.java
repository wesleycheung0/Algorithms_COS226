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

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Scanner;

public class Solver {
    private Board initial;
    private MinPQ<SearchNode> pq; // Open List
    private SearchNode goal;


    private class SearchNode {
        private int moves;
        private Board board;
        private SearchNode prev;

        public SearchNode(Board initial) {
            moves = 0;
            prev = null;
            board = initial;
        }
    }

    private class PriorityOrder implements Comparator<SearchNode> {
        public int compare(SearchNode a, SearchNode b) {
            int moveA = a.moves + a.board.manhattan();
            int moveB = b.moves + b.board.manhattan();
            if (moveA > moveB) return 1;
            if (moveA < moveB) return -1;
            else return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        //IllegalArgumentException if null
        // IllegalArgumentException if unsolvable
        PriorityOrder pOrder = new PriorityOrder();

        SearchNode initNode = new SearchNode(initial);
        pq = new MinPQ<SearchNode>(pOrder);
        pq.insert(initNode); // Insert the starting Nodes

        // In while loop store its neightbor board and priority
        while (true) {
            SearchNode current = pq.delMin();
            // if the current is not visted b4
            if (current.board.isGoal()) {
                goal = current;
                break;
            }
            for (Board neigh : current.board.neighbors()) {
                if (current.prev == null || !neigh.equals(current.prev.board)) {
                    SearchNode temp = new SearchNode(neigh);
                    temp.moves = current.moves + 1;
                    temp.prev = current;
                    pq.insert(temp);
                }
            }
        }
    }

    // min number of moves to solve initial board
    public int moves() {
        return goal.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        Stack<Board> solution = new Stack<Board>();
        solution.push(goal.board);
        SearchNode current = goal.prev;
        while (current != null) {
            solution.push(current.board);
            current = current.prev;
        }
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(args[0]);
        Scanner in = new Scanner(file);
        int n = in.nextInt();
        int[][] Tile = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Tile[i][j] = in.nextInt();
            }
        }
        Board tile = new Board(Tile);
        if (tile.isSolvable()) {
            Solver temp = new Solver(tile);
            System.out.println("THe Step = " + temp.moves());
            for (Board neigh : temp.solution()) {
                System.out.println(neigh);
            }
        }
    }

}

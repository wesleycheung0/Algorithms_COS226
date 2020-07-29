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

import edu.princeton.cs.algs4.Queue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Board {
    //    private String tiles_string;
    private final int[][] tiles;
    private int hamming;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = tiles;
        hamming = 0;
        manhattan = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] != (i * tiles.length + j + 1)
                        && tiles[i][j] != 0) {// If the tile is not in place and not blank
                    hamming++;
                    int remainder = tiles[i][j] % tiles.length;// The Columns of the right place
                    int devFloor = tiles[i][j]
                            / tiles.length;// ;// The row of the right place
                    manhattan += Math.abs(remainder - j - 1) + Math.abs(devFloor - i);
                    int temp = i + j;
                }
            }

        }
    }


    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(tiles.length));
        sb.append("\n");
        for (int[] tile : tiles) {
            for (int j = 0; j < tiles.length; j++) {
                sb.append(String.format("%2d ", tile[j]));
            }
            sb.append("\n");
        }
        String result = sb.toString();
        return result;
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col) {
        return tiles[row][col];
    }

    // board size n
    public int size() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        int count = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (this.tileAt(i, j) != ((Board) y).tileAt(i, j)) {
                    count++;
                }
            }
        }
        return count == 0;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> list = new Queue<Board>();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) {
                    swapBoard(list, copy(tiles), i, j, i + 1, j);
                    swapBoard(list, copy(tiles), i, j, i - 1, j);
                    swapBoard(list, copy(tiles), i, j, i, j + 1);
                    swapBoard(list, copy(tiles), i, j, i, j - 1);
                }
            }
        }
        return list;
    }

    private int[][] copy(int[][] toCopy) {
        int[][] copy = new int[toCopy.length][toCopy.length];
        for (int i = 0; i < toCopy.length; i++) {
            for (int j = 0; j < toCopy.length; j++) {
                copy[i][j] = toCopy[i][j];
            }
        }
        return copy;
    }

    private void swapBoard(Queue<Board> List, int[][] list, int i, int j, int x, int y) {
        if (x >= 0 && y >= 0 && x < tiles.length && y < tiles.length) {
            list[i][j] = list[x][y];
            list[x][y] = 0;
            Board result = new Board(list);
            List.enqueue(result);
        }
    }

    // is this board solvable?
    public boolean isSolvable() {
        int[] flatTile = new int[tiles.length * tiles.length];
        int blankRow = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (tiles[i][j] == 0) blankRow = i;
                flatTile[i * tiles.length + j] = tiles[i][j];
            }
        }

        int inversion = 0;
        for (int i = 0; i < flatTile.length; i++) {
            for (int j = i + 1; j < flatTile.length; j++) {
                if (flatTile[i] > flatTile[j] && flatTile[i] != 0 && flatTile[j] != 0) {
                    inversion++;
                }
            }
        }
        // System.out.println(inversion);
        if (tiles.length % 2 == 1) {
            return tiles.length % 2 != inversion % 2;
        } else {
            return tiles.length % 2 != (inversion + blankRow) % 2;
        }

    }


    // unit testing (required)
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(args[0]);
        Scanner in = new Scanner(file, "UTF-8");
        int n = in.nextInt();
        int[][] Tile = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Tile[i][j] = in.nextInt();
            }
        }
        Board tile = new Board(Tile);
        // System.out.println(tile.manhattan());
        // System.out.println(tile.isGoal());
        System.out.println(tile.isSolvable());
        //System.out.println(tile.toString());
        for (Board neighbor : tile.neighbors()) {
            System.out.println(neighbor.toString());
        }

    }
}



/*****************************************************
 * Travis Schnider
 * Stephen Max Reiter
 * 2420
 * Darrin Hunter
 * March 15th 2018
 * Board.java
 *
 * @author travisschnider
 ******************************************************
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Solver {

    private final Board board = null;

    /*
     *  Each board is placed in a node and put into a linked list.
     */
    private final class Node {
        public Board board;
        public int moves;
        public int priority;
        public Node previous;
    }


    Node first = new Node();

    /*
     *  The constructor is passed a board. It will then solve it.
     *  By replacing "manhattan" with "hamming" or vise-versa the search method can be toggled.
     */
    public Solver(Board initial) {

        try {

            if (initial.isSolvable() || !initial.equals(null)) {

                first.board = initial;
                first.moves = 0;
                first.priority = first.moves + first.board.manhattan();
                first.previous = null;

                MinPQ<Solver.Node> mpq = new MinPQ<Node>(new BoardComparator().priorityCompare());
                while (first.board.isGoal() != true) {
                    for (Board m : first.board.neighbors()) {
                        Node n = new Node();
                        n.board = m;
                        n.moves = first.moves + 1;
                        n.priority = n.moves + n.board.manhattan();
                        n.previous = first;
                        mpq.insert(n);
                    }

                // TODO delete this
                    System.out.println("Neighbors: ");
                    System.out.print(first.board.neighbors());
                MinPQ<Solver.Node> foo = mpq;
                    System.out.println("Contents of pq: ");
                    while(foo.isEmpty() != true){
                        System.out.print(foo.delMin().board.toString());
                    }

                    Node oldFirst = first;
                    first = mpq.delMin();


                }
            }
        } catch (IllegalArgumentException err1) {
            throw new IllegalArgumentException("This board is not solvable.");
        } catch (NullPointerException err1) {
            throw new NullPointerException("The board passed to the Solver method cannot be null.");
        }
    }

    /*
     *  Returns the number of moves from the head of the linked list.
     *  This will be that last link in the linked list so it's the total number of moves taken to solve
     *  the puzzle.
     */
    public int moves() {
        return first.moves;
    }

    /*
     *  Returns a linked list representing each step to solve the puzzle.
     */
    public Iterable<Board> solution() {
        LinkedList<Board> solutions = new LinkedList<>();
        Node node = first;

        while (node.previous != null) {
            solutions.add(node.board);
            node = node.previous;
        }

        return solutions;
    }

    /*********************************************************
     *  Board Comparator
     *
     *  Used in the Priority Queue to sort based on the selected method
     *  (Manhattan or Hamming) and the number of moves taken to reach the
     *  current state of the puzzle.
     *********************************************************/
    class BoardComparator implements Comparator<Solver.Node> {

        public Comparator<Solver.Node> priorityCompare() {
            return new Comparator<Solver.Node>() {
                @Override
                public int compare(Solver.Node n1, Solver.Node n2) {
                    return n1.priority - n2.priority;
                }
            };
        }

        @Override
        public int compare(Solver.Node o1, Solver.Node o2) {
            return 0;
        }
    }

    /*
     *  Client to run the program from command line
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // check if puzzle is solvable; if so, solve it and output solution
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        // if not, report unsolvable
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
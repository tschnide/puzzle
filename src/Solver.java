import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.awt.print.Book;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Solver {

    private final Board board = null;

   private final class Node {
        public Board board;
        public int moves;
        public int priority;
        public Node previous;
    }


    // This is out here so the moves method has access
    Node first = new Node();

    public Solver(Board initial) {

        try{

        if (initial.isSolvable() || !initial.equals(null)) {

            first.board = initial;
            first.moves = 0;
            first.priority = first.moves + first.board.hamming();
            first.previous = null;

            MinPQ<Solver.Node> mpq = new MinPQ<Node>(new BoardComparator().priorityCompare());
            while (first.board.hamming() != 0) {

                for (Board m : first.board.neighbors()) {
                    Node n = new Node();
                    n.board = m;
                    n.moves = first.moves + 1;
                    n.priority = n.moves + n.board.hamming();
                    n.previous = first;
                    mpq.insert(n);
                }

                Node oldFirst = first;
                first = mpq.delMin();


            }
        }
        } catch (IllegalArgumentException err1){
            throw new IllegalArgumentException("This board is not solvable.");
        } catch (NullPointerException err1){
            throw new NullPointerException("The board passed to the Solver method cannot be null.");
        }
    }

    public int moves(){
        return first.moves;
    }

    public Iterable<Board> solution(){
        LinkedList<Board> solutions = new LinkedList<>();
        Node node = first;

        while(node.previous != null){
            solutions.add(node.board);
            node = node.previous;
        }

        return solutions;
    }

//    public static int[][] makeTwoDArray(int y, int x) {
//        int[][] twoDArray = new int[y][x];
//        int incrementation = 1;
//
//        for (int row = 0; row < y; row++) {
//            for (int column = 0; column < x; column++) {
//                twoDArray[row][column] = incrementation;
//                incrementation++;
//            }
//        }
//        return twoDArray;
//    }

    class BoardComparator implements Comparator<Solver.Node>{

        public Comparator<Solver.Node> priorityCompare(){
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

    public static void main(String[] args) {
//        int[][] arr = makeTwoDArray(3, 3);
//        int[][] arr = {{1, 2, 3}, {4 ,5, 0}, {7, 8, 6}};
//        board b = new board(arr);
//        Board b = null;
//        Solver s = new Solver(b);
//        System.out.println("the number of moves was: " + s.moves());
//        System.out.println("the solutions are ");
//
//        for(Board x : s.solution()){
//            System.out.println(x);
//        }
//
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


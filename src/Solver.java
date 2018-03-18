import edu.princeton.cs.algs4.MinPQ;

import java.awt.print.Book;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Solver {

    private Board board;
    public class Node {
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

            MinPQ mpq = new MinPQ(BoardComparator.priorityCompare());

            while (first.board.hamming() != 0) {

                for (Board m : first.board.neighbors()) {
//                    System.out.println("board being inserted is ");
//                    System.out.println("the hamming distance is " + m.hamming());
//                    for (int row = 0; row < m.blocks.length; row++) {
//                        for (int column = 0; column < m.blocks.length; column++) {
//                            System.out.print(m.blocks[row][column]);
//                        }
//                        System.out.println();
//                    }
                    Node n = new Node();
                    n.board = m;
                    n.moves = first.moves + 1;
                    n.priority = n.moves + n.board.hamming();
                    n.previous = first;
                    mpq.insert(n);
                }

                Node oldFirst = first;
                first = (Solver.Node) mpq.delMin();
//            System.out.println("first " + first.previous);
//            System.out.println("oldfirst " + oldFirst.previous);


            }
//            System.out.println("done");
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

    public static int[][] makeTwoDArray(int y, int x) {
        int[][] twoDArray = new int[y][x];
        int incrementation = 1;

        for (int row = 0; row < y; row++) {
            for (int column = 0; column < x; column++) {
                twoDArray[row][column] = incrementation;
//                System.out.print(twoDArray[row][column]);
                incrementation++;
            }
//            System.out.println();
        }

        twoDArray[2][0] = 0;
        twoDArray[2][1] = 7;
        twoDArray[2][2] = 8;

        return twoDArray;
    }

    public static void main(String[] args) {
//        int[][] arr = makeTwoDArray(3, 3);
        int[][] arr = {{1, 2, 3}, {4 ,5 ,6}, {0, 7, 8}};
        Board b = new Board(arr);
//        Board b = null;
        Solver s = new Solver(b);
        System.out.println("the number of moves was: " + s.moves());
        System.out.println("the solutions are ");

        for(Board x : s.solution()){
            System.out.println(x);
        }

    }

}

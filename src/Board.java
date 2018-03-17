import java.util.Iterator;

public class Board {
    private int[][] board;

    // For now I'm just using a single dimensional array, converting later will be easy.
    public Board(int[][] board) {
        this.board = board;
    }

    public int size() {
        int y = board.length;
        int x = board[0].length;
        return x * y;
    }

    public int hamming() {
        int hamming = 0;
        int position = 1;
        int y = board.length;
        int x = board[0].length;

        for (int row = 0; row < y; row++) {
            for (int column = 0; column < x; column++) {
                if(position != board[row][column]){
                    hamming++;
                }
            }
        }

        return hamming;
    }

    public int manhattan(){
        // There may be a better way...
        // distances
        // find row difference
        // find column difference
        // add them up
        // repeat for each tile
        int manhattan = 0;

        for (int row = 0; row < board.length; row++) {
            for (int column = 0; column < board.length; column++) {
                
            }
        }

        return manhattan;
    }

//
//    public boolean isgoal(){
//
//    }
//
//    public boolean issolveable() {
//
//    }
//
//    public boolean equals(object y){
//
//    }
//
//    public iterable<board> neighbors(){
//
//    }
//
//    public string tostring(){
//
//    }

    public static int[][] makeOrderedArray(int y, int x) {
        int[][] orderedArray = new int[y][x];
        int count = 1;

        for (int row = 0; row < y; row++) {
            for (int column = 0; column < x; column++) {
                orderedArray[row][column] = count++;
            }
        }

        return orderedArray;
    }

    public static void main(String[] args) {
        int y = 3;
        int x = 3;

        Board b1 = new Board(makeOrderedArray(y, x));
        assert b1.size() == y * x;

        Board b2 = new Board(makeOrderedArray(y, x));
        assert b2.hamming() == 0;

    }
}


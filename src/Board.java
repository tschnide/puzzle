import java.util.Iterator;

public class Board {
    private int[] board;

    // For now I'm just using a single dimensional array, converting later will be easy.
    public Board(int[] board) {
        this.board = board;
    }

    public int size() {
        return board.length;
    }

    public int hamming() {
        int hamming = 0;

        for (int index = 0; index < board.length; index++) {
            if (board[index] != index) {                           // If the index equals the value stored in it it is in order.
                hamming++;
            }
        }

        // Still working on the number of state changes that came before
        return hamming;
    }
//
//    public int manhattan(){
//
//    }
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

    public static int[] makeOrderedArray(int size) {

        int[] orderedArray = new int[size];
        // Begin at one
        for (int n = 1; n < size; n++) {
            orderedArray[n] = n;
        }

        return orderedArray;
    }

    public static void main(String[] args) {
        int size = 9;

        Board b1 = new Board(makeOrderedArray(size));
        assert b1.size() == size;

        Board b2 = new Board(makeOrderedArray(size));
        assert b2.hamming() == 0;

    }
}


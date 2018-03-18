/*****************************************************
 * Stephen Max Reiter
 * Travis Schnider
 * 2420
 * Darrin Hunter
 * March 15th 2018
 * Board.java
 *
 * @author smaxreiter
 ******************************************************
 */

        import java.util.LinkedList;


public class Board {
    private static final int SPACE = 0; // represents open space
    private final int[][] blocks;

    /*
     * Board constructor construct a board from an N-by-N array of blocks (where
     * blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        this.blocks = clone(blocks);

    }

    private int[][] clone(int[][] blocks) {
        int n = blocks.length;
        int[][] copy = new int[n][n];
        for (int row = 0; row < n; row++)
            for (int col = 0; col < n; col++)
                copy[row][col] = blocks[row][col];

        return copy;
    }

    /*
     * board size N
     */
    public int size() {
        return blocks.length;
    }

    /*
     * returns position of a single tile
     */
    private int tile(int row, int col) {
        return blocks[row][col];
    }

    /*
     * returns 'row'/i position of a tile
     */
    private int row(int tile) {
        return (tile - 1) / size();
    }

    /*
     * returns 'col'/j position of a tile
     */
    private int col(int tile) {
        return (tile - 1) % size();
    }

    /*
     * checks whether block is the empty block on board
     */
    private boolean Empty(int tile) {
        return tile == SPACE;
    }

    /*
     * checks if block is in the correct place on board
     */
    private boolean isInPlace(int row, int col) {
        int block = tile(row, col);

        return !Empty(block) && block != tileGoal(row, col);
    }

    /*
     * goal for a single tile
     */
    private int tileGoal(int row, int col) {
        return row * size() + col + 1;
    }

    /*
     * Tracks number of moves
     */
    private int countMoves(int row, int col) {
        int block = tile(row, col);

        return (Empty(block)) ? 0 : Math.abs(row - row(block)) + Math.abs(col - col(block));
    }

    /*
     * keeps track of empty block
     */
    private int[] emptyTile() {
        for (int row = 0; row < blocks.length; row++)
            for (int col = 0; col < blocks.length; col++)
                if (Empty(tile(row, col))) {
                    int[] location = new int[2];
                    location[0] = row;
                    location[1] = col;

                    return location;
                }
        throw new RuntimeException();
    }

    /*
     * number of blocks out of place
     */
    public int hamming() {
        int count = 0;
        for (int row = 0; row < blocks.length; row++) { // Checks rows
            for (int col = 0; col < blocks.length; col++) { // Checks columns
                if (isInPlace(row, col)) {
                    count++; // adds to 'count' for out of place blocks
                }
            }
        }
        return count; // returns number of out of place blocks
    }

    /*
     * Sum of Manhattan distances between blocks and goal, sum of
     * hamming(slides) and board(neighbors) it takes to get to neighboring board.
     */
    public int manhattan() {
        int sum = 0; // Priority count
        for (int row = 0; row < blocks.length; row++) { // Check rows
            for (int col = 0; col < blocks.length; col++) { // Check columns
                sum += countMoves(row, col); // Adds moves(slides) and valid neighboring
                // boards
            }
        }
        return sum;
    }

    /*
     * TODO
     */
    public boolean isGoal() {
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks.length; col++) {
                if (isInPlace(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * TODO
     */
    public boolean isSolvable() {
        Board board = new Board(blocks);

        int inversion = board.hamming() % 2;
        if (inversion == 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * TODO
     */
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null || !(y instanceof Board) || ((Board) y).blocks.length != blocks.length) {
            return false;
        }

        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks.length; col++) {
                if (((Board) y).blocks[row][col] != tile(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        if (size() < 2) {
            return null;
        }
        LinkedList<Board> boards = new LinkedList<Board>();

        int[] location = emptyTile();
        int row = location[0];
        int col = location[1];

        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < size(); j++) {
                if (blocks[i][j] == 0) {
                    if (col > 0) {
                        boards.add(getNeighbor(row, col, row, col - 1));// swap
                        // upper
                        // tile
                    }
                    if (col < size() - 1) {
                        boards.add(getNeighbor(row, col, row, col + 1));// swap
                        // lower
                        // tile
                    }
                    if (row > 0) {
                        boards.add(getNeighbor(row, col, row - 1, col));// swap
                        // left
                        // tile
                    }
                    if (row < size() - 1) {
                        boards.add(getNeighbor(row, col, row + 1, col));// swap
                        // right
                        // tile
                    }
                    return boards;
                }
            }
        }
        return null;
    }

    /*
     * construct a new neighboring board/return.
     */
    private Board getNeighbor(int row1, int col1, int row2, int col2) {
        Board newBoard = new Board(blocks);
        newBoard  = new Board(newBoard.exch(row1, col1, row2, col2));
        return newBoard;
    }

    /*
     * TODO
     */
    public Board twin() {
        for (int row = 0; row < blocks.length; row++) {
            for (int col = 0; col < blocks.length - 1; col++) {
                if (!Empty(tile(row, col)) && !Empty(tile(row, col + 1))) {
                    return new Board(exch(row, col, row, col + 1));
                }
            }
        }
        throw new RuntimeException();
    }

    /*
     * TODO
     */
    private int[][] exch(int row1, int col1, int row2, int col2) {
        int[][] copy = clone(blocks);
        int tmp = copy[row1][col1];
        copy[row1][col1] = copy[row2][col2];
        copy[row2][col2] = tmp;

        return copy;
    }

    /*
     * string representation of this board
     * (in the output format specified below)
     *
     */
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append(size() + "\n");
        for (int row = 0; row < size(); row++) {
            for (int col = 0; col < size(); col++) {
                str.append(String.format("%2d ", tile(row, col)));
            }
            str.append("\n");
        }

        return str.toString();
    }

    public static int[][] makeTwoDArray(int y, int x) {
        int[][] twoDArray = new int[y][x];
        int incrementation = 0;

        for (int row = 0; row < y; row++) {
            for (int column = 0; column < x; column++) {
                twoDArray[row][column] = incrementation;
                System.out.print(twoDArray[row][column]);
                incrementation++;
            }
            System.out.println();
        }

        return twoDArray;
    }

    public static void main(String[] args) {

        Board b1 = new Board(makeTwoDArray(3,3));
        // Size test
        assert b1.size() == 9 : "Problem with size";
        // Tile test
        assert b1.tile(0,0) == 0 : "Problem with tile";

        System.out.println("hamming " + b1.hamming());
    }

}



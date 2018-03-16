class BoardTest extends GroovyTestCase {
// This isn't currently working
    public static int[] makeBoardArray(int size){
        int[] boardArray = new int[size];

        for(int n = 0; n < size; n++){
            boardArray[n] = n;
        }

        return boardArray;
    }

    void testSize() {
        Board board = new BoardTest(makeBoardArray(9));
        assert board.size() == 9;
    }

    void testHamming() {
        Board board = new BoardTest(makeBoardArray(9));
        assert board.hamming() == 0;
    }
}

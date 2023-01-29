//Name: David Fantin
//ID: 1525813
//Lab Section: 2000-03
//Lecture Section: 2003-02
//Date: 3/7/19
//Assignment: Project 2

/*
 * NOTE: I completely scraped my old code and started from scratch because I could not make sense of it anymore.
 */
class NQueens {
	/** squares per row or column */
	private int BOARD_SIZE;
	/** indicate an empty square */
	public static final int EMPTY = 0;
	/** indicate square contains a queen */
	public static final int QUEEN = -1;
	/**
	 * chess board: each entry <code>board[i][j]</code> of the board can take
	 * the following values:
	 * <li>QUEEN = -1 : indicating the presence of a queen
	 * <li>EMPTY = 0 : indicating an empty cell
	 * <li><code>i>0</code> : where <code>i</code> number of queens that can
	 * attack the cell <code>(i,j)</code>
	 */
	public int board[][];
	/** current number of backtracks */
	private int backTracks;
	/**
	 * number of locations that have been checked to be under attack at some
	 * point in the search process
	 */
	private int isAttackedCalls;
	/** current number of placed queens */
	private int numPlacedQueens;

	/** creates an empty square board */
	public NQueens(int size) {
		BOARD_SIZE = size;
		board = new int[BOARD_SIZE][BOARD_SIZE];
		backTracks = 0;
		isAttackedCalls = 0;
		numPlacedQueens = 0;
	} // end constructor

	/**
	 * Places queens by calling PlaceQueens with the first column.
	 * 
	 * @return If a solution is found, each column of the board contains one
	 *         queen and method returns true; otherwise, returns false (no
	 *         solution exists for a queen anywhere in column specified).
	 */
	public boolean placeQueens() {
		return placeQueens(0);
	}

	/**
	 * Places queens in the specified column of the board and recursvily place
	 * the queens on the successive columns when possible
	 * 
	 * @param column
	 *            where the new queen is added. It is assumed that queens are
	 *            correctly placed in columns <code>1</code> through
	 *            <code>column-1</code>
	 * @return If a solution is found, each column of the board contains one
	 *         queen and method returns true; otherwise, returns false (no
	 *         solution exists for a queen anywhere in column specified).
	 */
	private boolean placeQueens(int column) {
		if (column == BOARD_SIZE) {
			return true; // base case
		} else {
			boolean queenPlaced = false;
			int row = 0; // number of square in column
			while (!queenPlaced && (row < BOARD_SIZE)) {
				// if square can be attacked
				if (isAttacked(row, column)) {
					++row; // consider next row in column
				} // end if
				else { // place queen and consider next column
					setQueenAndMarks(row, column);
					queenPlaced = placeQueens(column + 1);
					// if no queen can be placed in the next column,
					if (!queenPlaced) {
						// backtrack: remove queen placed earlier
						// and try next row in column
						removeQueenAndMarks(row, column);
						++row;
						++backTracks;
					} // end if
				} // end else
			} // end while
			return queenPlaced;
		} // end else
	} // end placeQueens

	/**
	 * Sets a queen at the location indicated by the specified row and column
	 * and marks the columns attacked by this queen and no other queen placed in
	 * prior columns. Should call markBoard with appropriate arguments
	 * 
	 * @param row
	 *            where the new queen is located
	 * @param column
	 *            where the new queen is located
	 */
	private void setQueenAndMarks(int row, int column) {
		markForward(row, column, BOARD_SIZE - column - 1, EMPTY, column + 1);
		markBoard(row, column, EMPTY, QUEEN);
		numPlacedQueens++;
	} // end setQueen

	/**
	 * Removes a queen at the location indicated by row and column, and marks in
	 * the following columns to denote attack by this queen.
	 * <li>Precondition: A queen was placed in this position and it is being
	 * removed as part of the backtracking process.
	 * <li>Postcondition: Sets the square on the board in a given row and column
	 * to EMPTY. Also unmark all board positions attacked by this queen and not
	 * by any previous queens. Should call markBoard with appropriate arguments
	 * 
	 * @param row
	 *            where the queen to be removed is located
	 * @param column
	 *            where the queen to be removed is located
	 */
	private void removeQueenAndMarks(int row, int column) {
		markForward(row, column, BOARD_SIZE - column - 1, column + 1, EMPTY);
		markBoard(row, column, QUEEN, EMPTY);
	} // end removeQueen

	private void markBoard(int row, int column, int oldMarker, int newMarker) {
		markForward(row, column, 1, oldMarker, newMarker);
	}

	/**
	 * Marks the column number <code>col+distance</code> to denote the location
	 * that can be attacked by the queen being placed.
	 * <li>Precondition: A queen was placed in the position (row, col) and the
	 * (col+distance)th column is being marked as part of the lookahead process.
	 * <li>Postcondition: Marks up to three squares on the board in column
	 * (col+distance). These squares are the one on the same row and the ones on
	 * the diagonals emanating from the queen placed at (row, col). Marks only
	 * those squares not marked by a previously placed queen.
	 * 
	 * @param row
	 *            where the new queen is located
	 * @param col
	 *            where the new queen is located
	 * @param distance
	 *            identifies the state of the markup process: the
	 *            <code>col+distance</code> column will be marked up
	 * @param oldMarker
	 *            identifies the old marker
	 * @param newMarker
	 *            identifies the new marker
	 */
	private void markForward(int row, int col, int distance, int oldMarker, int newMarker) {
		if (distance == 0) {
			board[row][col] = newMarker;
		} else if ((col + distance) < BOARD_SIZE) {
			// Marks the row the queen is on
			if (board[row][col + distance] == oldMarker) {
				board[row][col + distance] = newMarker;
			}
			// Marks the diagonal the queen is on
			if (row + distance < BOARD_SIZE && board[row + distance][col + distance] == oldMarker) {
				board[row + distance][col + distance] = newMarker;
			}
			// Marks the diagonal the queen is on
			if (row - distance >= 0 && board[row - distance][col + distance] == oldMarker) {
				board[row - distance][col + distance] = newMarker;
			}
			markForward(row, col, distance - 1, oldMarker, newMarker);
		}
	}

	/**
	 * Determines whether the square on the board at a given row and column is
	 * under attack by any queens in the columns 1 through column-1.
	 * <li>Precondition: Each column between 1 and column-1 has a queen placed
	 * in a square at a specific row. None of these queens can be attacked by
	 * any other queen.
	 * 
	 * @param row
	 *            of the considered square of the board
	 * @param column
	 *            of the considered square of the board
	 * @return If the designated square is under attack, returns true;
	 *         otherwise, returns false.
	 */
	private boolean isAttacked(int row, int column) {
		isAttackedCalls++;
		if (board[row][column] != EMPTY)
			return true;
		else
			return false;
	} // end isAttacked

	public String getStatsInHTML() {
		return "Statistics for NQueens on a " + BOARD_SIZE + " x " + BOARD_SIZE + " chess board <br>"
				+ "Number of Back Tracks: " + backTracks + "<br>" + "Number of isAttacked() calls : " + isAttackedCalls
				+ "<br>" + "Number of times Queens were placed: " + numPlacedQueens + "<br>";
	}
} // end NQueens
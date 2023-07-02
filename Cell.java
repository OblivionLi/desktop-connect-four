package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

public class Cell extends JButton implements ActionListener {
    public static boolean isXLabel = true;
    private static Cell[][] board;
    private final int row;
    private final int column;
    private static final Color BASELINE_COLOR = Color.LIGHT_GRAY;
    private static final Color WINNING_COLOR = new Color(192, 192, 192);
    public static boolean isGameOver = false;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        setFocusPainted(false);
        setBackground(BASELINE_COLOR);
        setText(" ");
        setName("Button" + getLabelForRowAndColumn(row, column));
        addActionListener(this);
    }

    private String getLabelForRowAndColumn(int row, int column) {
        char columnLabel = (char) ('A' + column);
        int rowLabel = 6 - row;
        return String.valueOf(columnLabel) + rowLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isGameOver) {
            return;
        }

        int emptyRow = getEmptyRowInColumn(column);
        if (emptyRow != -1) {
            board[emptyRow][column].setText(isXLabel ? "X" : "O");
            isXLabel = !isXLabel;
            checkForWin(emptyRow, column);
        }
    }

    private int getEmptyRowInColumn(int column) {
        for (int row = 5; row >= 0; row--) {
            if (board[row][column].getText().equals(" ")) {
                return row;
            }
        }
        return -1;
    }

    private void checkForWin(int row, int column) {
        String label = board[row][column].getText();

        if (checkVerticalWin(row, column, label) ||
                checkHorizontalWin(row, column, label) ||
                checkDiagonalWin(row, column, label))
        {
            markWinningCells(row, column);
        }
    }

    private boolean checkVerticalWin(int row, int column, String label) {
        int count = 1;
        int r = row - 1;
        while (r >= 0 && board[r][column].getText().equals(label)) {
            count++;
            r--;
        }
        r = row + 1;
        while (r < 6 && board[r][column].getText().equals(label)) {
            count++;
            r++;
        }

        return count >= 4;
    }

    private boolean checkHorizontalWin(int row, int column, String label) {
        int count = 1;
        int c = column - 1;
        while (c >= 0 && board[row][c].getText().equals(label)) {
            count++;
            c--;
        }

        c = column + 1;
        while (c < 7 && board[row][c].getText().equals(label)) {
            count++;
            c++;
        }
        return count >= 4;
    }

    private boolean checkDiagonalWin(int row, int column, String label) {
        int count = 1;

        int r = row - 1;
        int c = column - 1;
        while (r >= 0 && c >= 0 && board[r][c].getText().equals(label)) {
            count++;
            r--;
            c--;
        }

        r = row + 1;
        c = column + 1;
        while (r < 6 && c < 7 && board[r][c].getText().equals(label)) {
            count++;
            r++;
            c++;
        }
        if (count >= 4) {
            return true;
        }

        count = 1;
        r = row - 1;
        c = column + 1;
        while (r >= 0 && c < 7 && board[r][c].getText().equals(label)) {
            count++;
            r--;
            c++;
        }

        r = row + 1;
        c = column - 1;
        while (r < 6 && c >= 0 && board[r][c].getText().equals(label)) {
            count++;
            r++;
            c--;
        }
        return count >= 4;
    }

    private void markWinningCells(int row, int column) {
        if (this.fillCellsVerticallyIfWon(row, column)) {
            return;
        }

        if (this.fillCellsHorizontallyIfWon(row, column)) {
            return;
        }

        if (this.fillCellsDiagonallyBottomLeftToTopRightIfWon(row, column)) {
            return;
        }

        this.fillCellsDiagonallyTopLeftToBottomRightIfWon(row, column);
    }

    private boolean fillCellsVerticallyIfWon(int row, int column) {
        String label = board[row][column].getText();

        int count = 0;
        int r = row;
        Set<Integer> cellsIndexToFill = new HashSet<>();

        while (r < 6) {
            if (board[r][column].getText().equals(label)) {
                cellsIndexToFill.add(r);
                count++;
            }

            if (cellsIndexToFill.size() == 4) {
                break;
            }

            r++;
        }

        r = row;
        while (r > 0) {
            if (board[r][column].getText().equals(label)) {
                cellsIndexToFill.add(r);
                count++;
            }

            if (cellsIndexToFill.size() == 4) {
                break;
            }

            r--;
        }

        if (count >= 4) {
            for (int j : cellsIndexToFill) {
                board[j][column].setBackground(WINNING_COLOR);
            }
            isGameOver = true;
            return true;
        }

        return false;
    }

    private boolean fillCellsHorizontallyIfWon(int row, int column) {
        String label = board[row][column].getText();

        int count = 0;
        int c = column;
        Set<Integer> cellsIndexToFill = new HashSet<>();

        while (c < 7) {
            if (board[row][c].getText().equals(label)) {
                cellsIndexToFill.add(c);
                count++;
            }

            if (cellsIndexToFill.size() == 4) {
                break;
            }

            c++;
        }

        c = column;
        while (c >= 0) {
            if (board[row][c].getText().equals(label)) {
                cellsIndexToFill.add(c);
                count++;
            }

            if (cellsIndexToFill.size() == 4) {
                break;
            }

            c--;
        }

        if (count >= 4) {
            for (int j : cellsIndexToFill) {
                board[row][j].setBackground(WINNING_COLOR);
            }
            isGameOver = true;
            return true;
        }

        return false;
    }

    private boolean fillCellsDiagonallyBottomLeftToTopRightIfWon(int row, int column) {
        String label = board[row][column].getText();

        int r = row;
        int c = column;
        int count = 0;

        // Check the bottom-left direction
        while (r < 6 && c >= 0 && board[r][c].getText().equals(label)) {
            count++;
            r++;
            c--;
        }

        r = row - 1;
        c = column + 1;

        // Check the top-right direction
        while (r >= 0 && c < 7 && board[r][c].getText().equals(label)) {
            count++;
            r--;
            c++;
        }

        if (count >= 4) {
            // Mark the winning cells
            r = row;
            c = column;
            while (r < 6 && c >= 0 && board[r][c].getText().equals(label)) {
                board[r][c].setBackground(WINNING_COLOR);
                r++;
                c--;
            }

            r = row - 1;
            c = column + 1;
            while (r >= 0 && c < 7 && board[r][c].getText().equals(label)) {
                board[r][c].setBackground(WINNING_COLOR);
                r--;
                c++;
            }

            isGameOver = true;
            return true;
        }

        return false;
    }

    private boolean fillCellsDiagonallyTopLeftToBottomRightIfWon(int row, int column) {
        String label = board[row][column].getText();

        int r = row;
        int c = column;
        int count = 0;

        // Check the top-left direction
        while (r >= 0 && c >= 0 && board[r][c].getText().equals(label)) {
            count++;
            r--;
            c--;
        }

        r = row + 1;
        c = column + 1;

        // Check the bottom-right direction
        while (r < this.row && c < this.column && board[r][c].getText().equals(label)) {
            count++;
            r++;
            c++;
        }

        if (count >= 4) {
            // Mark the winning cells
            r = row;
            c = column;
            while (r >= 0 && c >= 0 && board[r][c].getText().equals(label)) {
                board[r][c].setBackground(WINNING_COLOR);
                r--;
                c--;
            }

            r = row + 1;
            c = column + 1;
            while (r < this.row && c < this.column && board[r][c].getText().equals(label)) {
                board[r][c].setBackground(WINNING_COLOR);
                r++;
                c++;
            }

            isGameOver = true;
            return true;
        }

        return false;
    }

    public static void setBoard(Cell[][] board) {
        Cell.board = board;
    }
}

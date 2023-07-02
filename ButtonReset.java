package four;

import javax.swing.*;
import java.awt.*;

public class ButtonReset extends JButton {
    private final int rows;
    private final int columns;
    private final Cell[][] board;
    private final Color baselineColor = Color.LIGHT_GRAY;

    public ButtonReset(int rows, int columns, Cell[][] board) {
        this.rows = rows;
        this.columns = columns;
        this.board = board;

        setText("Reset");
        setName("ButtonReset");
        setEnabled(true);
        addActionListener(e -> resetBoard());
    }

    private void resetBoard() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Cell cell = board[row][column];
                cell.setBackground(baselineColor);
                cell.setText(" ");
                cell.setEnabled(true);
                Cell.isGameOver = false;
            }
        }

        Cell.isXLabel = true;
    }
}

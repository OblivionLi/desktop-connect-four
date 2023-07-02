package four;

import javax.swing.*;
import java.awt.*;

public class ConnectFour extends JFrame {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;

    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setTitle("Connect Four");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel gridPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(ROWS, COLUMNS);
        gridPanel.setLayout(gridLayout);

        Cell[][] board = new Cell[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Cell cell = new Cell(row, column);
                board[row][column] = cell;
                gridPanel.add(cell);
            }
        }

        Cell.setBoard(board);

        JPanel buttonPanel = new JPanel();
        ButtonReset resetButton = new ButtonReset(ROWS, COLUMNS, board);
        buttonPanel.add(resetButton);

        mainPanel.add(gridPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConnectFour::new);
    }
}

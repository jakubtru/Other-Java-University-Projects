package tictactoe;

import javax.swing.*;
import java.util.ArrayList;

public class TicTacToe extends JFrame {
    private int turn = 0;
    private final ArrayList<JButton> buttons = new ArrayList<>();
    JLabel status;
    private boolean xWin;
    private boolean yWin;
    private boolean gameEnded = false;

    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(463, 527);
        setVisible(true);
        setLayout(null);
        initComponents();

    }

    private void initComponents() {
        generateButtons();
    }

    private void generateButtons() {
        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(5, 455, 100, 30);
        add(resetButton);
        resetButton.setFocusPainted(false);
        resetButton.setName("ButtonReset");
        resetButton.addActionListener(actionEvent -> {
            turn = 0;
            xWin = false;
            yWin = false;
            gameEnded = false;
            for (var button : buttons) {
                button.setText(" ");
            }
            status.setText("Game is not started");
        });

        status = new JLabel();
        status.setBounds(300, 455, 150, 30);
        status.setText("Game is not started");
        status.setName("LabelStatus");

        add(status);

        String[] names = {"A3", "B3", "C3", "A2", "B2", "C2", "A1", "B1", "C1"};
        int j = -1;
        for (int i = 0; i < 9; i++) {
            JButton button = new JButton();
            int x = 150 * (i % 3);
            if (i % 3 == 0) {
                j++;
            }
            int y = 150 * j;
            button.setBounds(x, y, 150, 150);
            add(button);
            button.setFocusPainted(false);
            button.setName("Button" + names[i]);
            button.setText(" ");
            button.addActionListener(actionEvent -> {
                if (!gameEnded && !button.getText().matches("[XO]")) {
                    if (turn % 2 == 1) {
                        button.setText("O");
                    } else {
                        button.setText("X");
                    }
                    turn++;
                    checkResult();
                    checkStatus();
                }
            });
            buttons.add(button);
        }
    }

    private void checkResult() {
        String s = turn % 2 == 0 ? "O" : "X";
        boolean win = false;
        for (int i = 0; i < 9; i += 3) {
            if (buttons.get(i).getText().equals(s) && buttons.get(i + 1).getText().equals(s) && buttons.get(i + 2).getText().equals(s)) {
                win = true;
                break;
            }
        }
        if (!win) {
            for (int i = 0; i < 3; i += 1) {
                if (buttons.get(i).getText().equals(s) && buttons.get(i + 3).getText().equals(s) && buttons.get(i + 6).getText().equals(s)) {
                    win = true;
                    break;
                }
            }
        }
        if (!win && buttons.get(0).getText().equals(s) && buttons.get(4).getText().equals(s) && buttons.get(8).getText().equals(s)) {
            win = true;
        }
        if (!win && buttons.get(2).getText().equals(s) && buttons.get(4).getText().equals(s) && buttons.get(6).getText().equals(s)) {
            win = true;
        }
        if (win) {
            if (s.equals("X")) {
                xWin = true;
            } else {
                yWin = true;
            }
        }
        checkStatus();
    }

    private void checkStatus() {
        if (xWin) {
            status.setText("X wins");
            gameEnded = true;
        } else if (yWin) {
            status.setText("O wins");
            gameEnded = true;
        } else if (turn > 0 && turn < 9) {
            status.setText("Game in progress");
        } else if (turn >= 9) {
            status.setText("Draw");
        }
    }
}
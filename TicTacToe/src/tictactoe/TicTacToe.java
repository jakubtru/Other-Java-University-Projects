package tictactoe;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class TicTacToe extends JFrame {
    private int turn = 0;
    private final ArrayList<JButton> buttons = new ArrayList<>();
    JLabel status;
    private boolean xWin;
    private boolean yWin;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private JButton ButtonStartReset;
    private JButton ButtonPlayer1;
    private JButton ButtonPlayer2;
    private String firstPlayer = "Human";
    private String secondPlayer = "Human";


    public TicTacToe() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setSize(463, 600);
        setVisible(true);
        setLayout(null);
        initComponents();
    }

    private void initComponents() {
        generateButtons();
    }

    private void generateButtons() {
        JMenuBar menuBar = new JMenuBar();
        JMenu MenuGame = new JMenu("Game");
        MenuGame.setName("MenuGame");
        MenuGame.setMnemonic(KeyEvent.VK_F);

        JMenuItem MenuHumanHuman = new JMenuItem("Human vs Human");
        JMenuItem MenuHumanRobot = new JMenuItem("Human vs Robot");
        JMenuItem MenuRobotHuman = new JMenuItem("Robot vs Human");
        JMenuItem MenuRobotRobot = new JMenuItem("Robot vs Robot");
        JMenuItem MenuExit = new JMenuItem("Exit");

        MenuHumanHuman.addActionListener(actionEvent -> {
            setFirstPlayer("Human");
            setSecondPlayer("Human");
            gameStarted = false;
            ButtonStartReset.doClick();
        });
        MenuHumanRobot.addActionListener(actionEvent -> {
            setFirstPlayer("Human");
            setSecondPlayer("Robot");
            gameStarted = false;
            ButtonStartReset.doClick();
        });
        MenuRobotHuman.addActionListener(actionEvent -> {
            setFirstPlayer("Robot");
            setSecondPlayer("Human");
            gameStarted = false;
            ButtonStartReset.doClick();
        });
        MenuRobotRobot.addActionListener(actionEvent -> {
            setFirstPlayer("Robot");
            setSecondPlayer("Robot");
            gameStarted = false;
            ButtonStartReset.doClick();
        });
        MenuExit.addActionListener(event -> System.exit(0));


        MenuGame.add(MenuHumanHuman);
        MenuGame.add(MenuHumanRobot);
        MenuGame.add(MenuRobotHuman);
        MenuGame.add(MenuRobotRobot);
        MenuGame.add(MenuExit);

        menuBar.add(MenuGame);
        setJMenuBar(menuBar);
        setVisible(true);

        ButtonStartReset = new JButton("Start");
        ButtonStartReset.setBounds(150, 10, 150, 30);
        add(ButtonStartReset);
        ButtonStartReset.setFocusPainted(false);
        ButtonStartReset.setName("ButtonStartReset");
        ButtonStartReset.addActionListener(actionEvent -> {
            if (!gameStarted) {
                gameStarted = true;
                ButtonStartReset.setText("Reset");
                buttons.stream().forEach(b -> b.setEnabled(true));
                status.setText("The turn of " + firstPlayer + " Player (X)");
                ButtonPlayer1.setEnabled(false);
                ButtonPlayer2.setEnabled(false);

            } else {
                gameStarted = false;
                ButtonStartReset.setText("Start");
                //buttons.stream().forEach(b -> b.setEnabled(false));
                status.setText("Game is not started");
                ButtonPlayer1.setEnabled(true);
                ButtonPlayer2.setEnabled(true);
            }
            turn = 0;
            xWin = false;
            yWin = false;
            gameEnded = false;
            for (var button : buttons) {
                button.setText(" ");
            }

            try {
                if (gameStarted && firstPlayer.equals("Robot") && secondPlayer.equals("Robot")) {
                    while (!gameEnded) {
                        TimerTask task = new ComputerMove();
                        task.run();
                    }
                } else if (gameStarted && firstPlayer.equals("Robot")) {
                    computerMove();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        ButtonPlayer1 = new JButton("Human");
        ButtonPlayer1.setBounds(0, 10, 150, 30);
        add(ButtonPlayer1);
        ButtonPlayer1.setFocusPainted(false);
        ButtonPlayer1.setName("ButtonPlayer1");
        ButtonPlayer1.addActionListener(actionEvent -> {
            if (!gameStarted) {
                if (ButtonPlayer1.getText().equals("Human")) {
                    setFirstPlayer("Robot");
                } else {
                    setFirstPlayer("Human");
                }
            }
        });

        ButtonPlayer2 = new JButton("Human");
        ButtonPlayer2.setBounds(300, 10, 150, 30);
        add(ButtonPlayer2);
        ButtonPlayer2.setFocusPainted(false);
        ButtonPlayer2.setName("ButtonPlayer2");
        ButtonPlayer2.addActionListener(actionEvent -> {
            if (!gameStarted) {
                if (ButtonPlayer2.getText().equals("Human")) {
                    setSecondPlayer("Robot");
                } else {
                    setSecondPlayer("Human");
                }
            }
        });

        status = new JLabel();
        status.setBounds(15, 500, 300, 30);
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
            int y = 150 * j + 45;
            button.setBounds(x, y, 150, 150);
            add(button);
            button.setFocusPainted(false);
            button.setName("Button" + names[i]);
            button.setText(" ");
            button.addActionListener(actionEvent -> {
                if (!gameEnded && !button.getText().matches("[XO]")) {
                    if (turn % 2 == 1) {
                        button.setText("O");
                        status.setText("The turn of " + firstPlayer + " Player (X)");
                    } else {
                        button.setText("X");
                        status.setText("The turn of " + secondPlayer + " Player (O)");
                    }
                    turn++;
                    checkResult();
                    checkStatus();
                }
                if (!gameEnded && (firstPlayer.equals("Robot") || secondPlayer.equals("Robot"))) {
                    try {
                        computerMove();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            button.setEnabled(false);
            buttons.add(button);
        }
    }

    private void setFirstPlayer(String s) {
        ButtonPlayer1.setText(s);
        firstPlayer = s;
    }

    private void setSecondPlayer(String s) {
        ButtonPlayer2.setText(s);
        secondPlayer = s;
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
            status.setText("The " + firstPlayer +" Player (X) wins");
            gameEnded = true;
        } else if (yWin) {
            status.setText("The " + secondPlayer + " Player (O) wins");
            gameEnded = true;
        } else if (turn > 0 && turn < 9) {
            ButtonStartReset.setText("Reset");
        } else if (turn >= 9) {
            status.setText("Draw");
        }
    }

    private void computerMove() throws InterruptedException {
        Timer timer = new Timer();
        TimerTask task = new ComputerMove();
        LocalDateTime timeToExecute = LocalDateTime.now().plusSeconds(1);
        Date delay = Date.from(timeToExecute.atZone(ZoneId.systemDefault()).toInstant());
        timer.schedule(task, delay);
    }

    class ComputerMove extends TimerTask {
        public void run() {
            Optional<JButton> button = buttons.stream().filter(b -> b.getText().equals(" ")).findFirst();
            if (button.isEmpty()) {
                return;
            }
            if (turn % 2 == 1) {
                button.get().setText("O");
                status.setText("The turn of " + firstPlayer + " Player (X)");

            } else {
                button.get().setText("X");
                status.setText("The turn of " + secondPlayer + " Player (O)");
            }
            turn++;
            checkResult();
            checkStatus();
        }
    }
}

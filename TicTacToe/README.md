# Desktop TicTacToe

The Desktop TicTacToe is a simple Java application that allows users to play the classic game of Tic Tac Toe on their computer. The game provides a graphical user interface (GUI) implemented using Swing components.

## Features
- Players can choose between various game modes, including Human vs Human, Human vs Robot, Robot vs Human, and Robot vs Robot.
- The game interface displays a 3x3 grid of buttons representing the Tic Tac Toe board.
- Players can make moves by clicking on the buttons, and the game keeps track of the turn.
- The status label indicates whose turn it is and displays the outcome of the game (win, draw, etc.).
- When playing against a robot, the computer automatically makes its move after a 1-second delay using a Timer.

## Usage
1. Run the application and the game window will appear.
2. From the "Game" menu, select the desired game mode (Human vs Human, Human vs Robot, etc.).
3. Click the "Start" button to begin the game.
4. Players can take turns making moves by clicking on the available buttons.
5. The status label will update to show the current player's turn and the game outcome.
6. To start a new game, click the "Reset" button.
7. The application also allows customization of the player types by clicking the "Human" buttons next to each player's name.

## Dependencies
The application does not have any external dependencies and can be run using Java SE.

## Implementation Details
The application is implemented using Java's Swing framework. It utilizes `JFrame` for the main window and `JButton` for the game buttons. The game logic is handled by tracking the state of the game, including the turn count, players' moves, and win conditions. The `Timer` class is used to introduce a delay for the robot's move.

The game interface is generated dynamically based on the chosen game mode. The menu bar provides options for selecting the game mode, and the buttons are arranged in a 3x3 grid.

Overall, the Desktop TicTacToe application offers a convenient and interactive way to enjoy the classic Tic Tac Toe game on your computer.

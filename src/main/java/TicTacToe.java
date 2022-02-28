import lombok.Cleanup;
import lombok.val;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TicTacToe {

    private final List<String> possiblePositions = Arrays.asList(
            "00",
            "01",
            "02",
            "10",
            "11",
            "12",
            "20",
            "21",
            "22"
    );

    private final char[][] board;

    private final int rows = 3;
    private final int columns = 3;

    private boolean gameHasEnded = false;

    private String actualTurn = "Player 1";

    public TicTacToe() {

        board = new char[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                board[i][j] = '-';
            }
        }
    }

    public TicTacToe start() {
        @Cleanup Scanner input = new Scanner(System.in);
        do {
            System.out.println("Vez de: " + actualTurn + " (" + getSymbolByTurn() + ")");
            System.out.println("Digite a posição:");
            printBoard();
            String position = getRightPositionInput(input);

            putSymbolInPosition(position);

            if (checkIfHasWinner()) {
                gameHasEnded = true;
                System.out.println("O " + actualTurn + " (" + getSymbolByTurn() + ") ganhou o jogo!!!");
            } else if (checkIfBoardIsFull()) {
                gameHasEnded = true;
                System.out.println("O jogo deu velha!");
            }

            if (!gameHasEnded)
                switchTurn();
        } while (!gameHasEnded);

        return this;
    }

    private void printBoard() {
        int totalCount = 0;

        System.out.println(" 0  1  2");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                totalCount++;

                System.out.print(j == 0 ? (j + i) + " " : "");
                System.out.print(board[i][j] + " ");

                if (totalCount % 3 == 0)
                    System.out.println();
            }
        }

        System.out.println();
    }

    private String getRightPositionInput(Scanner input) {
        String positionToVerify = input.nextLine();

        if (!possiblePositions.contains(positionToVerify)) {
            System.out.println("Digite uma opção válida.");
            positionToVerify = getRightPositionInput(input);
        }

        val positionSplit = positionToVerify.split("");

        val row = Integer.parseInt(positionSplit[0]);
        val column = Integer.parseInt(positionSplit[1]);

        if (board[row][column] != '-') {
            System.out.println("Essa posição já está ocupada. Digite outra!");
            positionToVerify = getRightPositionInput(input);
        }

        return positionToVerify;
    }

    private void putSymbolInPosition(String position) {
        val positionSplit = position.split("");
        val row = Integer.parseInt(positionSplit[0]);
        val column = Integer.parseInt(positionSplit[1]);

        board[row][column] = getSymbolByTurn();
    }

    private boolean checkIfBoardIsFull() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board[i][j] == '-')
                    return false;
            }
        }

        return true;
    }

    private boolean checkIfHasWinner() {
        // Check row 1
        if (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != '-')
            return true;


        // Check row 2
        if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != '-')
            return true;


        // Check row 3
        if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != '-')
            return true;


        // Check column 1
        if (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != '-')
            return true;


        // Check column 2
        if (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != '-') {
            return true;
        }

        // Check column 3
        if (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != '-') {
            gameHasEnded = true;
            return true;
        }

        // Check diagonal 1
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') {
            return true;
        }

        // Check diagonal 2
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != '-') {
            return true;
        }

        return false;
    }

    private void switchTurn() {
        if (actualTurn.equals("Player 1"))
            actualTurn = "Player 2";
        else
            actualTurn = "Player 1";
    }

    private char getSymbolByTurn() {
        if (actualTurn.equals("Player 1"))
            return 'X';
        else
            return 'O';
    }

}

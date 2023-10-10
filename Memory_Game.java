import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Memory_Game {
    public static void main(String[] args) {
        Scanner user = new Scanner(System.in);
        System.out.println("Welcome to the game!");

        int[] key = setupBoard();
        int[] game = new int[key.length];
        int attempts = (game.length / 2) + 2;

        displayMainMenu(attempts);
        int choice = user.nextInt();
        int runStatus = 5;
        switch (choice) {
            case 1:

                for (int i = attempts; i >= 1; i--) {
                    System.out.println(blueBackground + "Number of attempts left: " + i + colorReset);
                    runStatus = runGame(game, key);
                    if (runStatus == 3) {
                        System.out.println(greenBackground + "You have won!" + colorReset);

                        break;
                    }
                }
                if (runStatus == 0) {
                    System.out.println(redBackground + "You lost. Game over" + colorReset);
                }

                break;

            case 2:
                while (runStatus != 3) {
                    runStatus = runGame(game, key);
                    if (runStatus == 3) {
                        System.out.println(greenBackground + "You have won!" + colorReset);

                        break;
                    }
                }
                break;

            case 3:
                System.out.println("Exiting game...");

                break;
            default:
                System.out.print("Input a valid number option: ");
                choice = user.nextInt();
                break;
        }
    }

    private static int[] setupBoard() {
        int[] key;

        Scanner user = new Scanner(System.in);
        System.out.print("Enter the size of your board: ");
        int sizeOfBoard = user.nextInt();

        while (!(sizeOfBoard > 2 && sizeOfBoard % 2 == 0)) {
            System.out.print("Enter the size of your board (even number): ");
            sizeOfBoard = user.nextInt();
        }
        key = new int[sizeOfBoard];

        placePairs(key);
        return key;
    }

    private static void displayMainMenu(int attempts) {
        System.out.println("Do you wish to play for: ");
        System.out.println("1. " + attempts + " attempts");
        System.out.println("2. Unlimited attempts.");
        System.out.println("3. Exit game.");
    }

    private static int runGame(int[] playingBoard, int[] board) {
        Scanner user = new Scanner(System.in);

        int cell1;
        int cell2;
        int boardLength = playingBoard.length;

        loading();
        System.out.println();

        displayBoard(playingBoard);

        System.out.println();

        // Cell #1
        System.out.print("Choose a cell number: ");
        cell1 = user.nextInt();

        // Check if the cell is within bounds.
        boolean range = isWithinBounds(boardLength, cell1);

        while (range == false) {
            System.out.print("Input a valid number: ");
            cell1 = user.nextInt();
            range = isWithinBounds(boardLength, cell1);
        }

        // Check if the cell has been picked already or cleared.
        boolean pickedOrCleared = isCellSelected(playingBoard, cell1);

        while (pickedOrCleared == true) {
            System.out.println("The cell has already been selected");
            System.out.print("Choose a valid cell number: ");
            cell1 = user.nextInt();
            System.out.println();
            pickedOrCleared = isCellSelected(playingBoard, cell1);
        }

        clearCell(playingBoard, cell1, board[cell1]); // Change the value of the cell.

        loading();
        System.out.println();

        displayBoard(playingBoard);

        System.out.println();

        System.out.println("You have discovered: " + playingBoard[cell1]);
        System.out.println("Where is the matching pair? ");

        // Cell #2
        System.out.print("Choose a cell number: ");
        cell2 = user.nextInt();

        // Check if the cell is within bounds.
        range = isWithinBounds(boardLength, cell2);

        while (range == false) {
            System.out.print("Input a valid number: ");
            cell2 = user.nextInt();
            range = isWithinBounds(boardLength, cell2);
        }

        // Check if the cell has been picked already or cleared.
        pickedOrCleared = isCellSelected(playingBoard, cell2);

        while (pickedOrCleared == true) {
            System.out.println("The cell has already been selected");
            System.out.print("Choose a valid cell number: ");
            cell2 = user.nextInt();
            pickedOrCleared = isCellSelected(playingBoard, cell2);
        }

        loading();
        System.out.println();

        clearCell(playingBoard, cell2, board[cell2]); // Change the value of the cell.
        displayBoard(playingBoard);

        // Check if both cells have the same value.
        if (playingBoard[cell1] == playingBoard[cell2]) {
            System.out.println();
            System.out.println(green + "Found a pair!" + colorReset);

        } else {
            clearCell(playingBoard, cell1, 0);
            clearCell(playingBoard, cell2, 0);
            System.out.println();
            System.out.println(red + "No matching pair." + colorReset);

        }

        // See if the board is cleared already.
        boolean status = isBoardCleared(playingBoard);
        if (status == true) {
            return 3;

        } else {
            return 0;
        }
    }

    private static void displayBoard(int[] board) {

        String board_print = "";

        for (int i = 0; i < board.length; i++) {
            board_print += i + "   ";
        }

        System.out.println(board_print);

        for (int i = 0; i < board_print.length() - 3; i++) {
            System.out.print("-");
        }

        System.out.println("-");

        for (int i = 0; i < board.length; i++) {
            if (board[i] != 0) {
                System.out.print(green + board[i] + "   " + colorReset);
            } else {
                if (i >= 10) { // If the number of cell has two digits.
                    System.out.print(cyan + "?    " + colorReset);
                } else {
                    System.out.print(cyan + "?   " + colorReset);
                }
            }
        }
        System.out.println();
    }

    private static boolean isWithinBounds(int boardLength, int cell) {
        if (cell >= 0 && cell < boardLength) {
            return true;

        } else {
            return false;
        }
    }

    private static boolean isCellSelected(int[] board, int cell) {
        boolean pickedOrCleared = false;
        if (board[cell] != 0) {
            pickedOrCleared = true;
        }
        return pickedOrCleared;
    }

    private static void clearCell(int[] board, int cell, int value) {
        board[cell] = value;
    }

    private static boolean isBoardCleared(int[] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private static void placePairs(int[] board) {
        Random rand = new Random();

        for (int i = 0; i < board.length; i++)
            board[i] = i / 2 + 1;

        for (int i = 0; i < board.length - 1; i++) {
            int j = rand.nextInt(board.length - i);
            int temp = board[i];
            board[i] = board[j];
            board[j] = temp;
        }
    }

    // Colors - https://www.w3schools.blog/ansi-colors-java
    public static final String colorReset = "\u001B[0m";
    public static final String green = "\u001B[32m";
    public static final String red = "\u001B[31m";
    public static final String cyan = "\033[0;36m";
    public static final String blueBackground = "\033[44m";
    public static final String redBackground = "\033[0;101m";
    public static final String greenBackground = "\033[42m";

    // Loading message:
    // https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html
    // https://www.geeksforgeeks.org/timeunit-class-in-java-with-examples/
    // https://www.baeldung.com/java-delay-code-execution
    public static void loading() {
        System.out.print("Loading");
        for (int i = 0; i < 3; i++) {
            System.out.print(".");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

import java.util.Scanner;

public class SeaBattle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the name of Player 1: ");
        String player1Name = scanner.nextLine();

        System.out.print("Enter the name of Player 2: ");
        String player2Name = scanner.nextLine();

        char[][] player1Board = initializeBoard();
        char[][] player2Board = initializeBoard();
        char[][] player1Hits = initializeBoard();
        char[][] player2Hits = initializeBoard();

        // Place ships for each player
        placeShips(player1Board);
        placeShips(player2Board);

        // Play the game
        while (!isGameOver(player1Board) && !isGameOver(player2Board)) {
            clearScreen();

            System.out.println(player1Name + "'s turn:");
            takeTurn(player1Name, player1Board, player1Hits, player2Board);

            if (isGameOver(player1Board)) {
                break;
            }

            clearScreen();

            System.out.println(player2Name + "'s turn:");
            takeTurn(player2Name, player2Board, player2Hits, player1Board);

            if (isGameOver(player2Board)) {
                break;
            }
        }

        // Display the winner
        clearScreen();
        if (isGameOver(player1Board)) {
            System.out.println(player2Name + " wins! All " + player1Name + "'s ships are sunk.");
        } else {
            System.out.println(player1Name + " wins! All " + player2Name + "'s ships are sunk.");
        }
    }

    private static char[][] initializeBoard() {
        return new char[7][7]; // Initialize a 7x7 board
    }

    private static void placeShips(char[][] board) {

        // Simplified placement of ships
        // In a real game, you would ask the player for ship coordinates

        placeShip(board, 3, "A2", "C2", "E2"); // One ship of size 3
        placeShip(board, 2, "A4", "C4"); // Two ships of size 2
        placeShip(board, 1, "A6"); // Four ships of size 1

        // Print the board after placing ships
        printBoard(board, true);
    }

    private static void takeTurn(String playerName, char[][] currentPlayerBoard, char[][] currentPlayerHits,
                                 char[][] opponentBoard) {
        Scanner scanner = new Scanner(System.in);

        // Print the current player's board, hits, and misses
        printBoard(currentPlayerBoard, true);

        System.out.print(playerName + ", enter the coordinates (e.g., A2): ");
        String coordinates = scanner.next().toUpperCase();

        // Check if the input is valid
        if (!isValidInput(coordinates)) {
            System.out.println("Invalid coordinates. Please enter again.");
            takeTurn(playerName, currentPlayerBoard, currentPlayerHits, opponentBoard);
            return;
        }


        int row = coordinates.charAt(0) - 'A';
        int col = coordinates.charAt(1) - '1';

        // Check the result of the shot
        if (opponentBoard[row][col] == 'S') {
            System.out.println("Hit!");
            currentPlayerHits[row][col] = 'X'; // 'X' represents a hit
            currentPlayerBoard[row][col] = 'X'; // Update the current player's board
        } else {
            System.out.println("Miss!");
            currentPlayerHits[row][col] = 'O'; // 'O' represents a miss
        }

        // Print the updated board
        printBoard(currentPlayerBoard, true);
    }

    private static boolean isValidInput(String input) {
        // Check if the input has valid format
        return input.length() == 2 && input.charAt(0) >= 'A' && input.charAt(0) <= 'G'
                && input.charAt(1) >= '1' && input.charAt(1) <= '7';
    }

    private static void printBoard(char[][] board, boolean showShips) {
        System.out.println("  1 2 3 4 5 6 7");
        System.out.println(" +-------------");

        char rowLabel = 'A';

        for (char[] row : board) {
            System.out.print(rowLabel + "| ");
            for (char cell : row) {
                if (cell == 'S' && !showShips) {
                    System.out.print(". ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
            rowLabel++;
        }
        System.out.println();
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void placeShip(char[][] board, int size, String... coordinates) {
        for (String coordinate : coordinates) {
            int row = coordinate.charAt(0) - 'A';
            int col = coordinate.charAt(1) - '1';
            board[row][col] = 'S'; // 'S' represents a ship
        }
    }

    private static boolean isGameOver(char[][] board) {
        // Check if all ships are sunk
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == 'S') {
                    return false;
                }
            }
        }
        return true;
    }
}

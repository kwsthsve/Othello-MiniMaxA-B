import java.util.concurrent.TimeUnit;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Othello {

    public static void main(String[] args) throws InterruptedException {

        Player BlackPlayer,WhitePlayer;

        int depth = -1;

        while (depth <= 0) {

            Scanner input = new Scanner(System.in);
            System.out.print("Enter maximum depth for MiniMax Algorithm: ");

            try {
                depth = input.nextInt();
                if (depth <= 0){
                    System.out.println("Input only positive integers! Try again...");
                }
            } catch (InputMismatchException e) {
                System.out.println("This is not a valid input! Try again...");
            }
        }

        int answer = -1;

        while ( (answer != 1) && (answer != 2) && (answer != 0) ){

            Scanner input = new Scanner(System.in);
            System.out.print("Choose your color: \n1.Black   2.White   0.PC vs PC\n");

            try {
                answer = input.nextInt();
                if ( (answer != 1) && (answer != 2) && (answer != 0) ){
                    System.out.println("Choose 1,2 or 0 to continue! Try again...");
                }
            } catch (InputMismatchException e) {
                System.out.println("This is not an integer! Try again...");
            }
        }

        BlackPlayer = new Player(depth, Board.BLACK);
        WhitePlayer = new Player(depth, Board.WHITE);

        Board GameBoard = new Board();

        System.out.println("\n\t\tHELPER: Input move like ---> [1-8] space [1-8] <---\n\t\t ex. 2 5 (second row,fifth column)\n");
        System.out.print("Read HELPER! The game begins in 3 seconds...\n\n");
        TimeUnit.SECONDS.sleep(3);

        GameBoard.printBoard();


        /* GAME ON */

        while ( !GameBoard.isGameOver() ){

            System.out.println();
            switch (GameBoard.getLastPlayer()){

                case Board.BLACK:
                    System.out.println("\t\t\t\t\sWHITE'S TURN\n");
                    Move WhiteMove;
                    if (answer == 2) {
                        int[] playerMove = GameBoard.playerMove(WhitePlayer);
                        if (playerMove[0] == -1){
                            GameBoard.setLastPlayer(WhitePlayer.getPlayerColor());
                            break;
                        }else {
                            WhiteMove = new Move(playerMove[0],playerMove[1]);
                        }
                    }else {
                        WhiteMove = WhitePlayer.MiniMax(GameBoard);
                    }

                    try {
                        GameBoard.makeMove(WhiteMove.getRow(), WhiteMove.getCol(), Board.WHITE);
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("White player has no valid moves...\n");
                        GameBoard.setLastPlayer(WhitePlayer.getPlayerColor());
                    }
                    break;

                case Board.WHITE:
                    System.out.println("\t\t\t\t\sBLACK'S TURN\n");
                    Move BlackMove;
                    if (answer == 1) {
                        int[] playerMove = GameBoard.playerMove(BlackPlayer);
                        if (playerMove[0] == -1){
                            GameBoard.setLastPlayer(BlackPlayer.getPlayerColor());
                            break;
                        }else {
                            BlackMove = new Move(playerMove[0],playerMove[1]);
                        }
                    }else {
                        BlackMove = BlackPlayer.MiniMax(GameBoard);
                    }

                    try {
                        GameBoard.makeMove(BlackMove.getRow(), BlackMove.getCol(), Board.BLACK);
                    }catch (IndexOutOfBoundsException e){
                        System.out.println("Black player has no valid moves...\n");
                        GameBoard.setLastPlayer(BlackPlayer.getPlayerColor());
                    }
                    break;

                default:
                    break;
            }

            GameBoard.printBoard();
        }
    }

}

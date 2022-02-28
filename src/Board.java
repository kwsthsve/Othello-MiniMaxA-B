import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Math.abs;


public class Board {

    public static final int WHITE = -2;
    public static final int BLACK = 2;
    public static final int EMPTY = 0;

    private int[][] othelloBoard;
    private int lastPlayer;

    private Move parentMove;



    /* Board constructor */

    Board(){
        this.parentMove = new Move();
        this.lastPlayer = WHITE;
        this.othelloBoard = new int[8][8];

        for (int row = 0; row < this.othelloBoard.length; row++){
            for (int col = 0; col < this.othelloBoard.length; col++){
                this.othelloBoard[row][col] = EMPTY;
            }
        }

        /* Initialize the starting tiles */

        this.othelloBoard[othelloBoard.length/2-1][othelloBoard.length/2-1] = WHITE;
        this.othelloBoard[(othelloBoard.length/2)][othelloBoard.length/2-1] = BLACK;
        this.othelloBoard[(othelloBoard.length/2)][(othelloBoard.length/2)] = WHITE;
        this.othelloBoard[othelloBoard.length/2-1][(othelloBoard.length/2)] = BLACK;
    }

    /* Board copy constructor */

    Board(Board newBoard){
        this.parentMove = newBoard.parentMove;;
        this.lastPlayer = newBoard.lastPlayer;;
        this.othelloBoard = new int[8][8];

        for (int row = 0; row < this.othelloBoard.length; row++){
            for (int col = 0; col < this.othelloBoard.length; col++){
                this.othelloBoard[row][col] = newBoard.othelloBoard[row][col];
            }
        }
    }


    /* Capture tiles on the board */

    void makeMove(int row, int col, int player){

        this.othelloBoard[row][col] = player;

        if (isHorizontalLeftSandwich(row,col,player)){
            int colProxy = col-1;
            while (this.othelloBoard[row][colProxy] == lastPlayer){
                this.othelloBoard[row][colProxy] = player;
                colProxy--;
            }
        }

        if (isHorizontalRightSandwich(row,col,player)){
            int colProxy = col+1;
            while (this.othelloBoard[row][colProxy] == lastPlayer){
                this.othelloBoard[row][colProxy] = player;
                colProxy++;
            }
        }

        if (isVerticalUpperSandwich(row,col,player)){
            int rowProxy = row-1;
            while (this.othelloBoard[rowProxy][col] == lastPlayer){
                this.othelloBoard[rowProxy][col] = player;
                rowProxy--;
            }
        }

        if (isVerticalLowerSandwich(row,col,player)){
            int rowProxy = row+1;
            while (this.othelloBoard[rowProxy][col] == lastPlayer){
                this.othelloBoard[rowProxy][col] = player;
                rowProxy++;
            }
        }

        if (isDiagonalLeftUpperSandwich(row,col,player)){
            int rowProxy = row-1;
            int colProxy = col-1;
            while (this.othelloBoard[rowProxy][colProxy] == lastPlayer){
                this.othelloBoard[rowProxy][colProxy] = player;
                rowProxy--;
                colProxy--;
            }
        }

        if (isDiagonalRightUpperSandwich(row,col,player)){
            int rowProxy = row-1;
            int colProxy = col+1;
            while (this.othelloBoard[rowProxy][colProxy] == lastPlayer){
                this.othelloBoard[rowProxy][colProxy] = player;
                rowProxy--;
                colProxy++;
            }
        }

        if (isDiagonalLeftLowerSandwich(row,col,player)){
            int rowProxy = row+1;
            int colProxy = col-1;
            while (this.othelloBoard[rowProxy][colProxy] == lastPlayer){
                this.othelloBoard[rowProxy][colProxy] = player;
                rowProxy++;
                colProxy--;
            }
        }

        if (isDiagonalRightLowerSandwich(row,col,player)){
            int rowProxy = row+1;
            int colProxy = col+1;
            while (this.othelloBoard[rowProxy][colProxy] == lastPlayer){
                this.othelloBoard[rowProxy][colProxy] = player;
                rowProxy++;
                colProxy++;
            }
        }

        this.parentMove = new Move(row, col);
        this.lastPlayer = player;
    }


    /* Move for human player */

    int[] playerMove(Player player){

        int validMovesCount = 0;

        for (int row = 0; row < this.othelloBoard.length; row++){
            for (int col = 0; col < this.othelloBoard.length; col++){
                if (validMove(row,col,player.getPlayerColor())){
                    validMovesCount += 1;
                }
            }
        }

        if (validMovesCount == 0){

            System.out.println("There are no valid moves! You lose your turn :(");
            return new int[]{-1};

        }else {

            int row = -1;
            int col = -1;

            while ( !validMove(row,col,player.getPlayerColor()) ){

                Scanner input = new Scanner(System.in);
                System.out.print("Input the coordinates of your move: ");

                try {
                    String[] move = input.nextLine().split(" ");
                    if (move.length > 2){
                        move = new String[]{"f"};
                    }
                    row = Integer.parseInt(move[0])-1;
                    col = Integer.parseInt(move[1])-1;
                } catch (Exception e) {
                    System.out.println("Enter one integer for ROW and one integer for COLUMN!");
                }

                if ( !validMove(row,col,player.getPlayerColor()) ){
                    System.out.println("Invalid move! Try again...\n");
                }

            }

            return new int[]{row, col};
        }
    }


    /* Check for sandwich type */

    boolean isHorizontalLeftSandwich(int row, int col, int player){

        boolean itIs = false;

        if (col == 0){
            return itIs;
        }

        if ( (this.othelloBoard[row][col-1] == EMPTY) || (this.othelloBoard[row][col-1] == player) ){
            return itIs;
        }else {
            for (int colProxy = col-1; colProxy > -1; colProxy--){
                if (this.othelloBoard[row][colProxy] == EMPTY){
                    break;
                }
                if (this.othelloBoard[row][colProxy] == player){
                    itIs = true;
                    break;
                }
            }
            return itIs;
        }
    }

    boolean isHorizontalRightSandwich(int row, int col, int player){

        boolean itIs = false;

        if (col == this.othelloBoard.length-1){
            return itIs;
        }

        if ( (this.othelloBoard[row][col+1] == EMPTY) || (this.othelloBoard[row][col+1] == player) ){
            return itIs;
        }else {
            for (int colProxy = col+1; colProxy < this.othelloBoard.length; colProxy++){
                if (this.othelloBoard[row][colProxy] == EMPTY){
                    break;
                }
                if (this.othelloBoard[row][colProxy] == player){
                    itIs = true;
                    break;
                }
            }
            return itIs;
        }
    }

    boolean isVerticalUpperSandwich(int row, int col, int player){

        boolean itIs = false;

        if (row == 0){
            return itIs;
        }

        if ( (this.othelloBoard[row-1][col] == EMPTY) || (this.othelloBoard[row-1][col] == player) ){
            return itIs;
        }else {
            for (int rowProxy = row-1; rowProxy > -1; rowProxy--){
                if (this.othelloBoard[rowProxy][col] == EMPTY){
                    break;
                }
                if (this.othelloBoard[rowProxy][col] == player){
                    itIs = true;
                    break;
                }
            }
            return itIs;
        }
    }

    boolean isVerticalLowerSandwich(int row, int col, int player){

        boolean itIs = false;

        if (row == this.othelloBoard.length-1){
            return itIs;
        }

        if ( (this.othelloBoard[row+1][col] == EMPTY) || (this.othelloBoard[row+1][col] == player) ){
            return itIs;
        }else {
            for (int rowProxy = row+1; rowProxy < this.othelloBoard.length; rowProxy++){
                if (this.othelloBoard[rowProxy][col] == EMPTY){
                    break;
                }
                if (this.othelloBoard[rowProxy][col] == player){
                    itIs = true;
                    break;
                }
            }
            return itIs;
        }
    }

    boolean isDiagonalLeftUpperSandwich(int row, int col, int player){

        boolean itIs = false;

        if ( (row == 0) || (col == 0) ){
            return itIs;
        }

        if ( (this.othelloBoard[row-1][col-1] == EMPTY) || (this.othelloBoard[row-1][col-1] == player) ){
            return itIs;
        }else {
            int rowProxy = row-1;
            int colProxy = col-1;
            while ( ((rowProxy > -1) && (colProxy > -1)) && (this.othelloBoard[rowProxy][colProxy] != EMPTY) ){
                if (this.othelloBoard[rowProxy][colProxy] == player){
                    itIs = true;
                    break;
                }
                rowProxy--;
                colProxy--;
            }
            return itIs;
        }
    }

    boolean isDiagonalRightUpperSandwich(int row, int col, int player){

        boolean itIs = false;

        if ( (row == 0) || (col == this.othelloBoard.length-1) ){
            return itIs;
        }

        if ( (this.othelloBoard[row-1][col+1] == EMPTY) || (this.othelloBoard[row-1][col+1] == player) ){
            return itIs;
        }else {
            int rowProxy = row-1;
            int colProxy = col+1;
            while ( ((rowProxy > -1) && (colProxy < this.othelloBoard.length)) && (this.othelloBoard[rowProxy][colProxy] != EMPTY) ){
                if (this.othelloBoard[rowProxy][colProxy] == player){
                    itIs = true;
                    break;
                }
                rowProxy--;
                colProxy++;
            }
            return itIs;
        }
    }

    boolean isDiagonalLeftLowerSandwich(int row, int col, int player){

        boolean itIs = false;

        if ( (row == this.othelloBoard.length-1) || (col == 0) ){
            return itIs;
        }

        if ( (this.othelloBoard[row+1][col-1] == EMPTY) || (this.othelloBoard[row+1][col-1] == player) ){
            return itIs;
        }else {
            int rowProxy = row+1;
            int colProxy = col-1;
            while ( ((rowProxy < this.othelloBoard.length) && (colProxy > -1)) && (this.othelloBoard[rowProxy][colProxy] != EMPTY) ){
                if (this.othelloBoard[rowProxy][colProxy] == player){
                    itIs = true;
                    break;
                }
                rowProxy++;
                colProxy--;
            }
            return itIs;
        }
    }

    boolean isDiagonalRightLowerSandwich(int row, int col, int player){

        boolean itIs = false;

        if ( (row == this.othelloBoard.length-1) || (col == this.othelloBoard.length-1) ){
            return itIs;
        }

        if ( (this.othelloBoard[row+1][col+1] == EMPTY) || (this.othelloBoard[row+1][col+1] == player) ){
            return itIs;
        }else {
            int rowProxy = row+1;
            int colProxy = col+1;
            while ( ((rowProxy < this.othelloBoard.length) && (colProxy < this.othelloBoard.length)) && (this.othelloBoard[rowProxy][colProxy] != EMPTY) ){
                if (this.othelloBoard[rowProxy][colProxy] == player){
                    itIs = true;
                    break;
                }
                rowProxy++;
                colProxy++;
            }
            return itIs;
        }
    }


    /*  In order to be valid the chosen tile must be neighbours with an opponent's tile and accomplish a sandwich */

    boolean validMove(int row, int col, int player){

        if ( (row > 7) || (col > 7) || (row < 0) || (col < 0) ) return false;

        if (this.othelloBoard[row][col] != EMPTY) return false;

        return isSandwich(row, col, player);

    }


    /* Check if a sandwich is accomplished */

    boolean isSandwich(int row, int col, int player){

        return ( (isHorizontalLeftSandwich(row, col, player) ||
                (isHorizontalRightSandwich(row, col, player)) ||
                (isVerticalUpperSandwich(row, col, player)) ||
                (isVerticalLowerSandwich(row, col, player)) ||
                (isDiagonalLeftUpperSandwich(row, col, player)) ||
                (isDiagonalRightUpperSandwich(row, col, player)) ||
                (isDiagonalLeftLowerSandwich(row, col, player)) ||
                (isDiagonalRightLowerSandwich(row, col, player))) );
    }


    /* Check if tile is exterior(or interior) */

    boolean isExterior(int row, int col){

        if ( (row == 0) || (row == 7) || (col == 0) || (col == 7) ){
            return true;
        }else {
            return ((this.othelloBoard[row + 1][col] == EMPTY) ||
                    (this.othelloBoard[row + 1][col + 1] == EMPTY) ||
                    (this.othelloBoard[row][col + 1] == EMPTY) ||
                    (this.othelloBoard[row - 1][col] == EMPTY) ||
                    (this.othelloBoard[row][col - 1] == EMPTY) ||
                    (this.othelloBoard[row - 1][col - 1] == EMPTY) ||
                    (this.othelloBoard[row + 1][col - 1] == EMPTY) ||
                    (this.othelloBoard[row - 1][col + 1] == EMPTY));
        }
    }


    /* Generate the state's tree */

    ArrayList<Board> getChildren(int player){

        ArrayList<Board> children = new ArrayList<>();

        for (int row = 0; row < this.othelloBoard.length; row++) {
            for (int col = 0; col < this.othelloBoard.length; col++) {
                if (this.validMove(row, col, player)) {
                    Board child = new Board(this);
                    child.makeMove(row, col, player);
                    children.add(child);
                }
            }
        }

        return children;
    }


    /* The game ends when there are no more valid moves */

    boolean isGameOver(){

        int ValidMovesCount = 0;

        for (int row = 0; row < this.othelloBoard.length; row++) {
            for (int col = 0; col < this.othelloBoard.length; col++) {
                if (this.othelloBoard[row][col] == EMPTY){
                    if ( (validMove(row,col,BLACK)) || (validMove(row,col,WHITE)) ){
                        ValidMovesCount += 1;
                    }
                }
            }
        }

        return (ValidMovesCount == 0);

    }


    /* Board class getters and setters */

    int[][] getOthelloBoard(){
        return this.othelloBoard;
    }

    Move getParentMove(){
        return this.parentMove;
    }

    int getLastPlayer(){
        return this.lastPlayer;
    }

    void setParentMove(Move parentMove){
        this.parentMove.setRow(parentMove.getRow());
        this.parentMove.setCol(parentMove.getCol());
        this.parentMove.setMoveValue(parentMove.getMoveValue());
    }

    void setLastPlayer(int lastPlayer){
        this.lastPlayer = lastPlayer;
    }

    void setOthelloBoard(int[][] othelloBoard){
        for (int row = 0; row < this.othelloBoard.length; row++){
            for (int col = 0; col < this.othelloBoard.length; col++){
                this.othelloBoard[row][col] = othelloBoard[row][col];
            }
        }
    }


    /* Heuristic function (Move evaluation)
    *  Maximize interior tiles and minimize exterior tiles
    *  Capture corners and edges
    *  No move around a corner until corner captured
    *  Capture stable tiles (impossible to lose after captured)
    */

    double Evaluation(){

        double BlackScore = 0;
        double WhiteScore = 0;

        int exteriorCounterBlack = 0;
        int interiorCounterBlack = 0;
        int exteriorCounterWhite = 0;
        int interiorCounterWhite = 0;

        for (int row = 0; row < this.othelloBoard.length; row++){
            for (int col = 0; col < this.othelloBoard.length; col++){
                if (this.othelloBoard[row][col] != EMPTY){
                    if (this.othelloBoard[row][col] == BLACK) {
                        BlackScore += tileWeight(row, col) * BLACK;
                        if (isExterior(row,col)){
                            exteriorCounterBlack++;
                        }else {
                            interiorCounterBlack++;
                        }
                    }else {
                        WhiteScore += tileWeight(row,col) * WHITE;
                        if (isExterior(row,col)){
                            exteriorCounterWhite++;
                        }else {
                            interiorCounterWhite++;
                        }
                    }
                }
            }
        }

        if ( (exteriorCounterBlack - exteriorCounterWhite) < 0 ){
            BlackScore += 100;
        }else {
            WhiteScore -= 100;
        }

        if ( (interiorCounterBlack - interiorCounterWhite) > 0 ){
            BlackScore += 200;
        }else {
            WhiteScore -= 200;
        }

        return BlackScore - abs(WhiteScore);

    }


    /* The weighting function for each tile */

    private double tileWeight(int row, int col){

        double[][] weights = new double[8][8];

        weights[0][0] = 160.1;
        weights[1][0] = -40.1;
        weights[2][0] = 10.3;
        weights[3][0] = 6.6;

        weights[0][1] = -30;
        weights[1][1] = -10.8;
        weights[2][1] = -0.4;
        weights[3][1] = -2.2;

        weights[0][2] = 9.99;
        weights[1][2] = -0.8;
        weights[2][2] = 5.5;
        weights[3][2] = -0.4;

        weights[0][3] = 4.4;
        weights[1][3] = -3.3;
        weights[2][3] = 0.7;
        weights[3][3] = -0.1;

        for (int rowProxy = 0; rowProxy < weights.length/2; rowProxy++){
            for (int colProxy = 0; colProxy < weights.length/2; colProxy++){
                weights[rowProxy][weights.length-1-colProxy] = weights[rowProxy][colProxy];
                weights[weights.length-1-rowProxy][colProxy] = weights[rowProxy][colProxy];
                weights[weights.length-1-rowProxy][weights.length-1-colProxy] = weights[rowProxy][colProxy];
            }
        }

        return weights[row][col];
    }


    /* Board printer */

    void printBoard(){

        int BlackCount = 0;
        int WhiteCount = 0;

        System.out.println("\t\s*************-OTHELLO_AI-*************\n");

        System.out.print("\t\t      ");
        for (int i = 0; i < 8; i++){
            System.out.print((i+1) +"\s\s");
        }
        System.out.print("\n");

        System.out.print("\t\t    --");
        for (int i = 0; i < 8; i++){
            System.out.print("---");
        }
        System.out.print("\n");

        for (int row = 0; row < 8; row++){
            System.out.print("\t\t" + (row+1) + "  | ");
            for (int col = 0; col < 8; col++){

                switch (this.othelloBoard[row][col]){
                    case BLACK -> {
                        System.out.print(" B ");
                        BlackCount += 1;
                    }
                    case WHITE -> {
                        System.out.print(" W ");
                        WhiteCount += 1;
                    }
                    default -> System.out.print(" - ");
                }
            }
            System.out.println(" |");
        }

        System.out.print("\t\t    --");
        for (int i = 0; i < 8; i++){
            System.out.print("---");
        }

        System.out.println("\n\n" + "\t\s**************************************");

        System.out.println("\t\tBlack tiles: " + BlackCount + "    " + "White tiles: " + WhiteCount);

    }
}

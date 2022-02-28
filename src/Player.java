import java.util.ArrayList;


public class Player {

    private int maxDepth;
    private int playerColor;

    private double a = Double.MIN_VALUE;
    private double b = Double.MAX_VALUE;


    /* Player class constructors */

    Player(int color){
        this.playerColor = color;
        this.maxDepth = -1;
    }

    Player(int depth, int color){
        this.maxDepth = depth;
        this.playerColor = color;
    }


    /* Player class getters and setters */

    int getPlayerColor(){
        return this.playerColor;
    }

    void setPlayerColor(int color){
        this.playerColor = color;
    }


    /* The MiniMax algorithm */

    Move MiniMax(Board othelloBoard) {

        if (this.playerColor == Board.BLACK) {
            return max(new Board(othelloBoard), this.a, this.b, 0);
        }
        else {
            return min(new Board(othelloBoard), this.a, this.b, 0);
        }
    }


    /* Find the minimum value for MiniMax algorithm with A-B pruning */

    private Move min(Board othelloBoard, double a, double b, int depth) {

        if ( (othelloBoard.isGameOver()) || (depth == this.maxDepth) ) {
            return new Move(othelloBoard.getParentMove().getRow(), othelloBoard.getParentMove().getCol(), othelloBoard.Evaluation());
        }

        ArrayList<Board> children = othelloBoard.getChildren(Board.WHITE);
        Move BetaMove = new Move(Double.MAX_VALUE);

        for (Board child: children) {

            Move move = max(child, a, b, depth + 1);

            if (move.getMoveValue() <= BetaMove.getMoveValue()) {
                BetaMove.setRow(child.getParentMove().getRow());
                BetaMove.setCol(child.getParentMove().getCol());
                BetaMove.setMoveValue(move.getMoveValue());
            }

            /* Pruning... */
            if ( a >= BetaMove.getMoveValue() ){
                break;
            }

            b = Double.min(b, BetaMove.getMoveValue());

        }

        return BetaMove;
    }


    /* Find the maximum value for MiniMax algorithm with A-B pruning */

    private Move max(Board othelloBoard, double a, double b, int depth) {

        if ( (othelloBoard.isGameOver()) || (depth == this.maxDepth) ) {
            return new Move(othelloBoard.getParentMove().getRow(), othelloBoard.getParentMove().getCol(), othelloBoard.Evaluation());
        }

        ArrayList<Board> children = othelloBoard.getChildren(Board.BLACK);
        Move AlphaMove = new Move(Double.MIN_VALUE);

        for (Board child: children) {

            Move move = min(child, a, b, depth + 1);

            if (move.getMoveValue() >= AlphaMove.getMoveValue()) {
                AlphaMove.setRow(child.getParentMove().getRow());
                AlphaMove.setCol(child.getParentMove().getCol());
                AlphaMove.setMoveValue(move.getMoveValue());
            }

            /* Pruning... */
            if ( AlphaMove.getMoveValue() >= b ){
                break;
            }

            a = Double.max(a, AlphaMove.getMoveValue());

        }

        return AlphaMove;
    }
}

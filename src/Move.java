public class Move {

    private int row;
    private int col;
    private double moveValue;


    /* Move class constructors */

    Move(){
        this.row = -1;
        this.col = -1;
        this.moveValue = 0;
    }

    Move(int row, int col){
        this.row = row;
        this.col = col;
        this.moveValue = 0;
    }

    Move(double value){
        this.row = -1;
        this.col = -1;
        this.moveValue = value;
    }

    Move(int row, int col, double value){
        this.row = row;
        this.col = col;
        this.moveValue = value;
    }


    /* Move class getters and setters */

    public double getMoveValue() {
        return moveValue;
    }

    public void setMoveValue(double moveValue) {
        this.moveValue = moveValue;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }
}

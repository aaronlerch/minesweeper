public class Square {
    private int _x;
    private int _y;

    private boolean _covered;
    private boolean _hasMine;
    private boolean _flagged;
    private int _countAdjacentMines;

    public Square(int x, int y) {
        _x = x;
        _y = y;

        _covered = true;
        _hasMine = false;
        _flagged = false;
        _countAdjacentMines = 0;
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    public boolean getHasMine() {
        return _hasMine;
    }

    public void setHasMine(boolean hasMine) {
        _hasMine = hasMine;
    }

    public boolean getFlagged() {
        return _flagged;
    }

    public void toggleFlag() {
        _flagged = !_flagged;
    }

    public boolean getCovered() {
        return _covered;
    }

    public void setCovered(boolean covered) {
        _covered = covered;
    }

    public int getCountAdjacentMines() {
        return _countAdjacentMines;
    }

    public void setCountAdjacentMines(int count) {
        _countAdjacentMines = count;
    }
}

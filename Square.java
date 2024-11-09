public class Square {
    private boolean _initialized;
    private boolean _covered;
    private boolean _hasMine;
    private boolean _flagged;
    private int _countAdjacentMines;

    public Square() {
        _initialized = false;
        _covered = true;
        _hasMine = false;
        _flagged = false;
        _countAdjacentMines = 0;
    }

    public void initialize(boolean hasMine) {
        if (_initialized) {
            // Only allow initializing once (?)
            return;
        }

        _hasMine = hasMine;
        _initialized = true;
    }

    public boolean getInitialized() {
        return _initialized;
    }

    public boolean getHasMine() {
        return _hasMine;
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

import java.util.ArrayList;

public class Square {
    private int _x;
    private int _y;

    private boolean _visible;
    private boolean _hasMine;
    private boolean _flagged;
    private int _countAdjacentMines;

    private ArrayList<SquareChangedCallback> _callbacks;

    public Square(int x, int y) {
        _x = x;
        _y = y;

        _visible = false;
        _hasMine = false;
        _flagged = false;
        _countAdjacentMines = 0;

        _callbacks = new ArrayList<SquareChangedCallback>();
    }

    public void addSquareChangedCallback(SquareChangedCallback callback)
    {
        _callbacks.add(callback);
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
        if (_hasMine != hasMine) {
            _hasMine = hasMine;
            fireChangedCallbacks();
        }
    }

    public boolean getFlagged() {
        return _flagged;
    }

    public void toggleFlag() {
        _flagged = !_flagged;
        fireChangedCallbacks();
    }

    public boolean getVisible() {
        return _visible;
    }

    public void setVisible(boolean visible) {
        if (_visible != visible) {
            _visible = visible;
            fireChangedCallbacks();
        }
    }

    public int getCountAdjacentMines() {
        return _countAdjacentMines;
    }

    public void setCountAdjacentMines(int count) {
        if (_countAdjacentMines != count) {
            _countAdjacentMines = count;
            fireChangedCallbacks();
        }
    }

    private void fireChangedCallbacks() {
        _callbacks.forEach((callback) -> callback.changed(this) );
    }
}

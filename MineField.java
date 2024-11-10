import java.util.*;

public class MineField implements SquareChangedCallback {
    private final int _initialMineRadius = 3;//2; // the radius to keep mines away when generating a new minefield
    private final double _mineChance = 20; // percent
    private final int[][] _adjacentLookups = new int[][] {
        { -1, -1 },
        { -1, 0 },
        { -1, 1 },
        { 0, -1 },
        { 0, 1 },
        { 1, -1 },
        { 1, 0 },
        { 1, 1 }
    };

    private Square[][] _field;
    private int _width;
    private int _height;
    private boolean _initialized;

    public MineField(int width, int height) {
        _width = width;
        _height = height;

        _initialized = false;
        _field = new Square[height][width];

        for (var row = 0; row < _height; row++) {
            for (var col = 0; col < _width; col++) {
                var square = new Square(col, row);
                _field[row][col] = square;
                square.addSquareChangedCallback(this);
            }
        }
    }

    public int getWidth() {
        return _width;
    }

    public int getHeight() {
        return _height;
    }

    public Square getSquare(int x, int y) {
        if (x < 0 || y < 0 || x >= _width || y >= _height) {
            return null;
        }

        return _field[y][x];
    }

    public Square[][] getSquares() {
        return _field;
    }

    public void setMines(int initialX, int initialY) {
        // Algo:
        // 1. Set all mines according to our chances of occurring
        //    1.1 Do not allow mines in some radius around initialX, initialY
        // 2. Set each square with the number of adjacent mines

        // Extra safety!
        if (_initialized) {
            return;
        }

        var rand = new Random();

        var lowerBoundX = initialX - _initialMineRadius;
        var upperBoundX = initialX + _initialMineRadius;
        var lowerBoundY = initialY - _initialMineRadius;
        var upperBoundY = initialY + _initialMineRadius;

        forEachSquare( (square, x, y) -> {
            var hasMine = rand.nextInt(100) < _mineChance;

            if (x >= lowerBoundX && x <= upperBoundX && y >= lowerBoundY && y <= upperBoundY) {
                // Ensure no mines in these squares, they are in the radius of initial exposure
                hasMine = false;
            }

            square.setHasMine(hasMine);
        });

        forEachSquare( (square, x, y) -> {
            var list = getAdjacentSquares(square);
            var count = list.stream().mapToInt((neighbor) -> neighbor.getHasMine() ? 1 : 0).sum();
            square.setCountAdjacentMines(count);
        });

        _initialized = true;
    }

    public void flag(Square square) {
        square.toggleFlag();
    }

    // Uncover a square and return true if it's a mine -- 💥 BOOM 💥
    public boolean uncover(Square square) {
        var x = square.getX();
        var y = square.getY();

        if (!_initialized) {
            setMines(x, y);
        }

        square.setVisible(true);
        var hasMine = square.getHasMine();

        if (!hasMine && square.getCountAdjacentMines() == 0) {
            uncoverAdjacent(square);
        }

        return hasMine;
    }

    private void uncoverAdjacent(Square square) {
        getAdjacentSquares(square).stream().filter((neighbor) -> !neighbor.getVisible()).forEach((neighbor) -> {
            neighbor.setVisible(true);
            if (neighbor.getCountAdjacentMines() == 0) {
                uncoverAdjacent(neighbor);
            }
        });
    }

    // This is used when the game is over (e.g. a mine was uncovered)
    public void uncoverAll() {
        forEachSquare( (square, x, y) -> square.setVisible(true) );
    }

    private ArrayList<Square> getAdjacentSquares(Square center) {
        var list = new ArrayList<Square>();

        for (var i = 0; i < _adjacentLookups.length; i++) {
            var transform = _adjacentLookups[i];
            var neighbor = getSquare(center.getX() + transform[1], center.getY() + transform[0]);
            if (neighbor != null) {
                list.add(neighbor);
            }
        }

        return list;
    }

    interface OnAllFunction {
        void run(Square square, int x, int y);
    }
    
    private void forEachSquare(OnAllFunction func) {
        for (var row = 0; row < _height; row++) {
            for (var col = 0; col < _width; col++) {
                func.run(_field[row][col], col, row);
            }
        }
    }

    @Override
    public void changed(Square square) {
        // If the changed square is a mine and is now shown, then our game is over!
        if (square.getHasMine() && square.getVisible()) {
            // game over
            uncoverAll();
        }
    }
}

import java.util.*;

public class MineField implements SquareChangedCallback {
    private final int _initialMineRadius = 3;//2; // the radius to keep mines away when generating a new minefield
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

    private int _mineChance; // value is percent (e.g. 20 == 20%)
    private Square[][] _field;
    private ArrayList<Square> _allSquares;
    private int _width;
    private int _height;
    private boolean _initialized;

    public MineField(GameSettings settings) {
        _width = settings.getGridWidth();
        _height = settings.getGridHeight();
        _mineChance = settings.getMineChance();

        _initialized = false;
        _field = new Square[_height][_width];
        _allSquares = new ArrayList<Square>();

        for (var row = 0; row < _height; row++) {
            for (var col = 0; col < _width; col++) {
                var square = new Square(col, row);
                _field[row][col] = square;
                _allSquares.add(square);
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

        _allSquares.forEach((square) -> {
            var hasMine = rand.nextInt(100) < _mineChance;
            var x = square.getX();
            var y = square.getY();

            if (x >= lowerBoundX && x <= upperBoundX && y >= lowerBoundY && y <= upperBoundY) {
                // Ensure no mines in these squares, they are in the radius of initial exposure
                hasMine = false;
            }

            square.setHasMine(hasMine);
        });

        _allSquares.forEach((square) -> {
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
        _allSquares.forEach((square) -> square.setVisible(true) );
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

    private void checkGameWon() {
        var hidden = _allSquares.stream().filter((square) -> !square.getVisible()).count();
        var hiddenMines = _allSquares.stream().filter((square) -> !square.getVisible() && square.getHasMine()).count();

        if (hidden > 0 && hidden == hiddenMines) {
            // Things are still hidden (e.g. game board isn't exposed) and everything hidden is a mine!
            System.out.println("YOU WON THE GAME!!!");
        }
    }

    @Override
    public void changed(Square square) {
        // If the changed square is a mine and is now shown, then our game is over!
        if (square.getHasMine() && square.getVisible()) {
            // game over
            uncoverAll();
        } else {
            checkGameWon();
        }
    }
}

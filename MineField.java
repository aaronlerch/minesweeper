import java.util.Random;

public class MineField {
    private final int _initialMineRadius = 2;
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

    public MineField(int width, int height) {
        _width = width;
        _height = height;

        _field = new Square[height][width];

        for (var row = 0; row < _height; row++) {
            for (var col = 0; col < _width; col++) {
                _field[row][col] = new Square();
            }
        }
    }

    public Square getSquare(int x, int y) {
        if (x < 0 || y < 0 || x >= _width || y >= _height) {
            return null;
        }

        return _field[y][x];
    }

    public void setMines(int initialX, int initialY) {
        // Algo:
        // 1. Set all mines according to our chances of occurring
        //    1.1 Do not allow mines in some radius around initialX, initialY
        // 2. Set each square with the number of adjacent mines

        var rand = new Random();

        var lowerBoundX = initialX - _initialMineRadius;
        var upperBoundX = initialX + _initialMineRadius;
        var lowerBoundY = initialY - _initialMineRadius;
        var upperBoundY = initialY + _initialMineRadius;

        forEachSquare( (square, x, y) -> {
            boolean hasMine = rand.nextInt(100) < _mineChance;

            if (x >= lowerBoundX && x <= upperBoundX && y >= lowerBoundY && y <= upperBoundY) {
                // Ensure no mines in these squares, they are in the radius of initial exposure
                hasMine = false;
            }

            square.initialize(hasMine);
        });

        forEachSquare( (square, x, y) -> {
            int count = 0;
            
            for (var i = 0; i < _adjacentLookups.length; i++) {
                var transform = _adjacentLookups[i];
                var neighbor = getSquare(x + transform[1], y + transform[0]);
                if (neighbor != null && neighbor.getHasMine()) {
                    count++;
                }
            }

            square.setCountAdjacentMines(count);
        });
    }

    public void flag(int x, int y) {
        var square = getSquare(x, y);
        if (square == null) return;

        square.toggleFlag();
    }

    public void uncover(int x, int y) {
        var square = getSquare(x, y);
        if (square == null) return;

        square.setCovered(false);
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
    public String toString() {
        var sb = new StringBuilder();
        for (var row = 0; row < _height; row++) {
            for (var col = 0; col < _width; col++) {
                var square = _field[row][col];
                if (!square.getInitialized()) {
                    sb.append("-");
                } else if (square.getHasMine()) {
                    sb.append("X");
                } else {
                    sb.append(square.getCountAdjacentMines());
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    };
}

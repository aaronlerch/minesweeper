
public class GameSettings {
    private GameMode _gameMode;
    private int _gridWidth;
    private int _gridHeight;
    private int _mineChance;

    public GameSettings(GameMode gameMode) {
        // Set defaults
        _gameMode = gameMode;
        _gridWidth = 20;
        _gridHeight = 20;
        _mineChance = 20;

        _gridWidth = _gridHeight = switch (_gameMode) {
            case GameMode.EASY -> 10;
            case GameMode.REGULAR -> 20;
            case GameMode.HARD -> 30;
            default -> 20;
        };

        _mineChance = switch (_gameMode) {
            case GameMode.EASY -> 10;
            case GameMode.REGULAR -> 20;
            case GameMode.HARD -> 30;
            default -> 20;
        };
    }

    public GameMode getGameMode() {
        return _gameMode;
    }

    public int getGridWidth() {
        return _gridWidth;
    }

    public int getGridHeight() {
        return _gridHeight;
    }

    public int getMineChance() {
        return _mineChance;
    }

    public GameSettings setGameMode(GameMode gameMode) {
        _gameMode = gameMode;
        return this;
    }

    public GameSettings setGridSize(int gridSize) {
        _gridWidth = _gridHeight = gridSize;
        return this;
    }

    public GameSettings setGridHeight(int gridHeight) {
        _gridHeight = gridHeight;
        return this;
    }

    public GameSettings setGridWidth(int gridWidth) {
        _gridWidth = gridWidth;
        return this;
    }

    public GameSettings setMineChance(int mineChance) {
        _mineChance = mineChance;
        return this;
    }
}
import java.awt.event.*;
import javax.swing.*;

public class GameWindow extends JFrame {
    private MineFieldView _currentGame;

    public GameWindow(String title) {
        super(title);

        _currentGame = null;
    }

    public void startDefaultGame() {
        startGame(GameMode.REGULAR);
    }

    public void startGame(GameMode gameMode) {
        // First, remove any existing component
        if (_currentGame != null) {
            remove(_currentGame);
        }

        var settings = new GameSettings(gameMode);
        var mf = new MineField(settings);
        _currentGame = new MineFieldView(mf);

        add(_currentGame);
        pack();

        _currentGame.requestFocusInWindow();
    }

    public void setUpMenus() {
        var menu = new JMenuBar();

        var file = new JMenu("File");
        var newGame = new JMenu("New Game");

        var easyGame = new JMenuItem("Easy");
        easyGame.addActionListener(e -> startGame(GameMode.EASY));

        var regularGame = new JMenuItem("Regular");
        regularGame.addActionListener(e -> startGame(GameMode.REGULAR));

        var hardGame = new JMenuItem("Hard");
        hardGame.addActionListener(e -> startGame(GameMode.HARD));

        var exit = new JMenuItem(new AbstractAction("Exit") {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        menu.add(file);
            file.add(newGame);
                newGame.add(easyGame);
                newGame.add(regularGame);
                newGame.add(hardGame);
            file.add(exit);

        setJMenuBar(menu);
    }
}

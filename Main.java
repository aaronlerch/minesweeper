import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        var window = new GameWindow("MINE SWEEPER");
        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        window.setUpMenus();
        window.startDefaultGame();
    }
}
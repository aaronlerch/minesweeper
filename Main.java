import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        var window = new JFrame("MINESWEEPER");
        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var width = 20;
        var height = 20;
        var mf = new MineField(width, height);
        var view = new MineFieldView(mf);

        window.add(view);
        window.pack();

        window.setVisible(true);
    }
}
import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        var window = new JFrame("MINESWEEPER");
        window.setSize(600, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var width = 20;
        var height = 20;

        var mf = new MineField(width, height);
        var view = new MineFieldView(mf);
        view.addSquareViews();
        
        window.getContentPane().add(view);
        window.pack();

        window.setVisible(true);
        // mf.setMines(5, 5);
    }
}
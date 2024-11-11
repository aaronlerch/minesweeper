import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        var window = new JFrame("MINESWEEPER");
        window.setSize(800, 800);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var gridSize = 30;
        // var width = 10;
        // var height = 10;
        var mf = new MineField(gridSize, gridSize);
        var view = new MineFieldView(mf);

        // var menu = new JMenuBar();
        // var file = new JMenu("File");
        // var exit = new JMenuItem()
        // menu.add(exit);


        window.add(view);
        //window.setJMenuBar(menu);
        window.pack();

        window.setVisible(true);
        view.requestFocusInWindow();
    }
}
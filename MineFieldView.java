import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MineFieldView extends JPanel implements KeyListener {
    private MineField _mineField;

    public MineFieldView(MineField mineField) {
        super(new GridLayout(mineField.getHeight(), mineField.getWidth()));
        
        _mineField = mineField;

        var squares = _mineField.getSquares();
        for (var y = 0; y < _mineField.getHeight(); y++) {
            for (var x = 0; x < _mineField.getWidth(); x++) {
                var squareView = new SquareView(_mineField, squares[x][y]);
                squareView.setPreferredSize(new Dimension(30, 30));
                squareView.setFocusable(false);

                add(squareView);
            }
        }

        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        var pt = getMousePosition();
        if (pt == null) {
            return;
        }

        if (e.getKeyChar() == 'f' || e.getKeyChar() == 'F') {
            // Find the square under the mouse and flag it
            var squareView = (SquareView)getComponentAt(pt);
            squareView.flag();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Find the square under the mouse and uncover it
            var squareView = (SquareView)getComponentAt(pt);
            squareView.uncover();
        }
    }
}

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MineFieldView extends JPanel {
    private MineField _mineField;

    public MineFieldView(MineField mineField) {
        super(new GridLayout(mineField.getHeight(), mineField.getWidth()));
        
        _mineField = mineField;

        var squares = _mineField.getSquares();
        for (var y = 0; y < _mineField.getHeight(); y++) {
            for (var x = 0; x < _mineField.getWidth(); x++) {
                var squareView = new SquareView(_mineField, squares[x][y]);
                squareView.setPreferredSize(new Dimension(30, 30));

                add(squareView);
            }
        }
    }
}

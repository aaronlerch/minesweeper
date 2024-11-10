import java.awt.*;
import javax.swing.*;

public class MineFieldView extends JPanel {
    private MineField _mineField;

    public MineFieldView(MineField mineField) {
        super(new GridLayout(mineField.getHeight(), mineField.getWidth()));
        
        _mineField = mineField;
    }

    public void addSquareViews()
    {
        var squares = _mineField.getSquares();
        for (var r = 0; r < squares.length; r++) {
            var row = squares[r];
            for (var c = 0; c < row.length; c++) {
                var squareView = new SquareView(_mineField, row[c]);
                squareView.setPreferredSize(new Dimension(30, 30));

                add(squareView);
            }
        }
    }
}

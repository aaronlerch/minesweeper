import java.awt.*;
import javax.swing.*;

public class SquareView extends JPanel {
    private Square _square;

    public SquareView(Square square) {
        _square = square;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var color = g.getColor();
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
    }
}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SquareView extends JPanel implements MouseListener {
    private final double _interiorScale = 0.6;
    
    private MineField _mineField;
    private Square _square;

    public SquareView(MineField mineField, Square square) {
        _mineField = mineField;
        _square = square;
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        var button = e.getButton();

        if (button == MouseEvent.BUTTON1) {
            _mineField.uncover(_square);
        }
        else if (button == MouseEvent.BUTTON3) {
            _mineField.flag(_square);
        }

        var parent = getParent();
        if (parent != null) {
            parent.invalidate();
            parent.validate();
            parent.repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (_square.getCovered()) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var color = g.getColor();
        g.setColor(Color.BLACK);

        var scaledWidth = (int)(getWidth() * _interiorScale);
        var scaledHeight = (int)(getHeight() * _interiorScale);

        var scaledX = (getWidth() - scaledWidth) / 2;
        var scaledY = (getHeight() - scaledHeight) / 2;
        var scaledRight = scaledX + scaledWidth;
        var scaledBottom = scaledY + scaledHeight;

        if (_square.getCovered()) {
            var preBGColor = g.getColor();
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(preBGColor);

            if (_square.getFlagged()) {
                g.fillOval(scaledX, scaledY, scaledWidth, scaledHeight);
            }
        }
        else {
            // Draw the number, or a mine!
            int count = _square.getCountAdjacentMines();

            if (_square.getHasMine()) {
                g.drawLine(scaledX, scaledY, scaledRight, scaledBottom);
                g.drawLine(scaledX, scaledBottom, scaledRight, scaledY);
            } else if (count > 0) {
                var f = g.getFont();
                var bigFont = new Font(f.getName(), f.getStyle(), scaledWidth);
                g.setFont(bigFont);
                g.drawString(Integer.toString(_square.getCountAdjacentMines()), scaledX, scaledBottom);
                g.setFont(f);
            }
        }

        g.drawRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
    }
}

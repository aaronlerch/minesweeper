import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class SquareView extends JComponent implements MouseListener, SquareChangedCallback {
    private final double _interiorScale = 0.6;
    private final Color ONE_BLUE = new Color(0, 00, 255);
    private final Color TWO_GREEN = new Color(0, 66, 0);
    private final Color THREE_RED = new Color(255, 0, 0);
    private final Color FOUR_DARK_BLUE = new Color(0, 0, 99);
    
    private MineField _mineField;
    private Square _square;

    public SquareView(MineField mineField, Square square) {
        _mineField = mineField;
        _square = square;

        square.addSquareChangedCallback(this);
        addMouseListener(this);
    }

    public void uncover() {
        _mineField.uncover(_square);
    }

    public void flag() {
        _mineField.flag(_square);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        var button = e.getButton();

        if (button == MouseEvent.BUTTON1) {
            uncover();
        }
        else if (button == MouseEvent.BUTTON3) {
            flag();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!_square.getVisible()) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void changed(Square square) {
        // Something about the square changed, repaint
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        var color = g.getColor();

        // Draw background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());

        var scaledWidth = (int)(getWidth() * _interiorScale);
        var scaledHeight = (int)(getHeight() * _interiorScale);

        var scaledX = (getWidth() - scaledWidth) / 2;
        var scaledY = (getHeight() - scaledHeight) / 2;
        var scaledRight = scaledX + scaledWidth;
        var scaledBottom = scaledY + scaledHeight;

        if (!_square.getVisible()) {
            g.setColor(Color.GRAY);
            g.fillRect(0, 0, getWidth(), getHeight());

            if (_square.getFlagged()) {
                // Draw the flag
                g.setColor(Color.RED);
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
                g.setColor(getColorForNumber(count));
                g.drawString(Integer.toString(_square.getCountAdjacentMines()), scaledX, scaledBottom);
                g.setFont(f);
            }
        }

        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        g.setColor(color);
    }

    private Color getColorForNumber(int num) {
        return switch (num) {
            case 1 -> ONE_BLUE;
            case 2 -> TWO_GREEN;
            case 3 -> THREE_RED;
            case 4 -> FOUR_DARK_BLUE;
            default -> Color.BLACK;
        };
    }
}

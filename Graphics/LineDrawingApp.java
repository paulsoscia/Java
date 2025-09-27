import javax.swing.*;
import java.awt.*;

public class LineDrawingApp extends JFrame {

    public LineDrawingApp() {
        setTitle("Line Drawing Example");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        add(new DrawingPanel()); // Add the custom drawing panel
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LineDrawingApp().setVisible(true);
        });
    }
}

class DrawingPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass method to ensure proper rendering

        // Set the drawing color (optional)
        g.setColor(Color.BLUE);

        // Draw a line from (0, 0) to (500, 500)
        g.drawLine(0, 0, 500, 500);


    }
}

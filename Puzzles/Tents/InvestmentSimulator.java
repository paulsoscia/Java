import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InvestmentSimulator extends JFrame {
    
    private JTextField initialField;
    private JTextField rateField;
    private JTextField yearsField;
    private GraphPanel graphPanel;

    public InvestmentSimulator() {
        // Set up the main window frame
        setTitle("Investment Growth Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Configuration Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        inputPanel.setBackground(new Color(240, 244, 248));

        inputPanel.add(new JLabel("Initial Deposit ($):"));
        initialField = new JTextField("10000", 8);
        inputPanel.add(initialField);

        inputPanel.add(new JLabel("Annual Return Rate (%):"));
        rateField = new JTextField("7.0", 5);
        inputPanel.add(rateField);

        inputPanel.add(new JLabel("Time Horizon (Years):"));
        yearsField = new JTextField("10", 4);
        inputPanel.add(yearsField);

        JButton calculateButton = new JButton("Simulate & Graph");
        calculateButton.setBackground(new Color(40, 167, 69));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFocusPainted(false);
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        // Center Graph Panel
        graphPanel = new GraphPanel();
        add(graphPanel, BorderLayout.CENTER);

        // Action Listener for the calculation button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(String e) {
                try {
                    double initial = Double.parseDouble(initialField.getText());
                    double rate = Double.parseDouble(rateField.getText()) / 100.0;
                    int years = Integer.parseInt(yearsField.getText());

                    if (initial < 0 || rate < -0.99 || years <= 0) {
                        JOptionPane.showMessageDialog(InvestmentSimulator.this, 
                            "Please enter valid positive values (Years must be greater than 0).", 
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Generate simulation data
                    List<Double> dataPoints = new ArrayList<>();
                    double currentBalance = initial;
                    dataPoints.add(currentBalance);

                    for (int i = 1; i <= years; i++) {
                        currentBalance += currentBalance * rate;
                        dataPoints.add(currentBalance);
                    }

                    // Send data to the graphics panel to trigger repaint
                    graphPanel.setPoints(dataPoints);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(InvestmentSimulator.this, 
                        "Please fill in all fields with numeric values.", 
                        "Invalid Format", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Trigger initial run
        calculateButton.doClick();
    }

    // Custom Swing Panel dedicated to drawing the graphics
    private static class GraphPanel extends JPanel {
        private List<Double> dataPoints = new ArrayList<>();

        public void setPoints(List<Double> dataPoints) {
            this.dataPoints = dataPoints;
            repaint(); // Instructs the window toolkit to call paintComponent
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Cast to Graphics2D for smoother rendering and line widths
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int padding = 60;

            // Draw Background Canvas
            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);

            if (dataPoints == null || dataPoints.isEmpty()) return;

            // Determine data scaling boundaries
            double maxAmount = 0;
            for (double val : dataPoints) {
                if (val > maxAmount) maxAmount = val;
            }

            int numPoints = dataPoints.size();
            double graphWidth = width - (2 * padding);
            double graphHeight = height - (2 * padding);

            // Draw Grid Lines and Axes Labels
            g2.setColor(new Color(220, 225, 230));
            for (int i = 0; i <= 4; i++) {
                // Horizontal grid lines
                int y = (int) (height - padding - (i * (graphHeight / 4)));
                g2.drawLine(padding, y, width - padding, y);

                // Y-Axis currency labels
                double labelVal = (maxAmount / 4) * i;
                g2.setColor(Color.DARK_GRAY);
                g2.drawString(String.format("$%,.0f", labelVal), 10, y + 5);
                g2.setColor(new Color(220, 225, 230));
            }

            // Draw Solid Origin Axes
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2f));
            g2.drawLine(padding, height - padding, padding, padding); // Y Axis
            g2.drawLine(padding, height - padding, width - padding, height - padding); // X Axis

            // Map and Draw the Growth Data Line Curve
            g2.setColor(new Color(0, 123, 255)); // Theme blue
            g2.setStroke(new BasicStroke(3f));

            int[] xPoints = new int[numPoints];
            int[] yPoints = new int[numPoints];

            for (int i = 0; i < numPoints; i++) {
                xPoints[i] = (int) (padding + (i * (graphWidth / (numPoints - 1))));
                yPoints[i] = (int) (height - padding - ((dataPoints.get(i) / maxAmount) * graphHeight));
                
                // Draw small trend node dots
                g2.fillOval(xPoints[i] - 4, yPoints[i] - 4, 8, 8);

                // X-Axis Year markers (skip labels if crowded)
                if (numPoints <= 15 || i % (numPoints / 10 + 1) == 0 || i == numPoints - 1) {
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString("Yr " + i, xPoints[i] - 12, height - padding + 20);
                    g2.setColor(new Color(0, 123, 255));
                }
            }

            // Draw the structural line connecting the plotted nodes
            g2.drawPolyline(xPoints, yPoints, numPoints);
            
            // Draw current final value text display banner
            g2.setColor(new Color(33, 37, 41));
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            String finalString = String.format("Ending Balance: $%,.2f", dataPoints.get(dataPoints.size() - 1));
            g2.drawString(finalString, padding + 10, padding - 15);
        }
    }

    public static void main(String[] args) {
        // Run GUI thread safely inside the Swing Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InvestmentSimulator().setVisible(true);
            }
        });
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CircusSevenSolver extends JPanel {

    // Define the 7 original pieces based on their sequential side colors clockwise:
    // R=Red, W=White, O=Orange, B=Blue, G=Green, Y=Yellow
    private static final String[] PIECE_STRINGS = {
        "RWOBGY", // Piece 1
        "RBOYGW", // Piece 2
        "RBYWOG", // Piece 3
        "RYWBGO", // Piece 4
        "RYBWGO", // Piece 5
        "RYGOWB", // Piece 6
        "RYGBOW"  // Piece 7
    };

    // Store the solved state: array indices correspond to puzzle positions 0 (Center) to 6.
    private static Piece[] solutionLayout = new Piece[7];
    private static boolean solved = false;

    public CircusSevenSolver() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.DARK_GRAY);
    }

    public static void main(String[] args) {
        // Initialize the pieces list
        List<Piece> pieces = new ArrayList<>();
        for (String s : PIECE_STRINGS) {
            pieces.add(new Piece(s));
        }

        // Run the brute-force backtracking algorithm
        boolean[] used = new boolean[7];
        if (backtrack(0, pieces, used)) {
            solved = true;
            System.out.println("Solution found!");
        } else {
            System.out.println("No solution found.");
        }

        // Launch the graphical window
        JFrame frame = new JFrame("Circus Seven Puzzle Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CircusSevenSolver());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Brute-force backtracking algorithm to place and rotate each piece.
     */
    private static boolean backtrack(int position, List<Piece> pieces, boolean[] used) {
        if (position == 7) {
            return checkValidSolution();
        }

        for (int i = 0; i < pieces.size(); i++) {
            if (!used[i]) {
                used[i] = true;
                Piece originalPiece = pieces.get(i);

                // Try all 6 possible rotations for this piece at the current position
                for (int rotation = 0; rotation < 6; rotation++) {
                    Piece rotatedPiece = originalPiece.getRotated(rotation);
                    solutionLayout[position] = rotatedPiece;

                    // Prune early if current placement violates partial constraints
                    if (isPartialValid(position)) {
                        if (backtrack(position + 1, pieces, used)) {
                            return true;
                        }
                    }
                }
                used[i] = false;
                solutionLayout[position] = null;
            }
        }
        return false;
    }

    /**
     * Checks constraints incrementally as pieces are placed to optimize brute-force.
     */
    private static boolean isPartialValid(int pos) {
        if (pos == 0) return true; // Center piece can be anything

        Piece center = solutionLayout[0];
        Piece current = solutionLayout[pos];

        // 1. Check matching edge with the Center Piece
        // Side indices: 0=Top, 1=Top-Right, 2=Bottom-Right, 3=Bottom, 4=Bottom-Left, 5=Top-Left
        int centerSide = pos - 1;
        int currentSide = (centerSide + 3) % 6; // Opposite edge faces the center
        if (center.colors[centerSide] != current.colors[currentSide]) {
            return false;
        }

        // 2. Check matching edge with the adjacent outer neighbor
        if (pos > 1) {
            Piece neighbor = solutionLayout[pos - 1];
            int currentToNeighborSide = (centerSide + 4) % 6; 
            int neighborToCurrentSide = (centerSide + 1) % 6;
            if (current.colors[currentToNeighborSide] != neighbor.colors[neighborToCurrentSide]) {
                return false;
            }
        }

        // 3. For the last piece (Position 6), also check its connection closure back to Position 1
        if (pos == 6) {
            Piece firstOuter = solutionLayout[1];
            if (current.colors[5] != firstOuter.colors[2]) {
                return false;
            }
        }

        return true;
    }

    private static boolean checkValidSolution() {
        for (int i = 1; i <= 6; i++) {
            if (!isPartialValid(i)) return false;
        }
        return true;
    }

    /**
     * Render the visual representation of the solved board.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!solved) {
            g.setColor(Color.WHITE);
            g.drawString("Solving or no solution found...", 50, 50);
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 80; // Size of each individual hexagon

        // Coordinates for layout placement of 7 hexagons
        // Pos 0: Center, Pos 1: Top, Pos 2: Top-Right, Pos 3: Bottom-Right, etc.
        int[][] positions = new int[7][2];
        positions[0] = new int[]{centerX, centerY};

        double distanceBetweenCenters = radius * Math.sqrt(3);
        for (int i = 1; i <= 6; i++) {
            // Angle offsetting starting from Top (-90 degrees or -PI/2) clockwise
            double angle = -Math.PI / 2 + (i - 1) * (Math.PI / 3);
            positions[i][0] = (int) (centerX + distanceBetweenCenters * Math.cos(angle));
            positions[i][1] = (int) (centerY + distanceBetweenCenters * Math.sin(angle));
        }

        // Draw each hexagon piece
        for (int i = 0; i < 7; i++) {
            drawHexagonPiece(g2d, positions[i][0], positions[i][1], radius, solutionLayout[i]);
        }
    }

    private void drawHexagonPiece(Graphics2D g2d, int cx, int cy, int radius, Piece piece) {
        // Draw the 6 colored triangles inside each hexagon
        for (int side = 0; side < 6; side++) {
            double angle1 = -Math.PI / 2 + side * (Math.PI / 3);
            double angle2 = angle1 + (Math.PI / 3);

            int x1 = (int) (cx + radius * Math.cos(angle1));
            int y1 = (int) (cy + radius * Math.sin(angle1));
            int x2 = (int) (cx + radius * Math.cos(angle2));
            int y2 = (int) (cy + radius * Math.sin(angle2));

            int[] xPoints = {cx, x1, x2};
            int[] yPoints = {cy, y1, y2};

            g2d.setColor(getAwtColor(piece.colors[side]));
            g2d.fillPolygon(xPoints, yPoints, 3);

            // Draw border outlines for wedges
            g2d.setColor(Color.BLACK);
            g2d.drawPolygon(xPoints, yPoints, 3);
        }

        // Draw outer ring boundary
        Polygon hex = new Polygon();
        for (int side = 0; side < 6; side++) {
            double angle = -Math.PI / 2 + side * (Math.PI / 3);
            hex.addPoint((int) (cx + radius * Math.cos(angle)), (int) (cy + radius * Math.sin(angle)));
        }
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawPolygon(hex);
    }

    private Color getAwtColor(char c) {
        switch (c) {
            case 'R': return Color.RED;
            case 'W': return Color.WHITE;
            case 'O': return new Color(255, 140, 0); // Orange
            case 'B': return new Color(30, 144, 255); // Blue
            case 'G': return new Color(34, 139, 34); // Green
            case 'Y': return Color.YELLOW;
            default: return Color.BLACK;
        }
    }

    /**
     * Inner class representing an immutable hexagon configuration layout.
     */
    private static class Piece {
        char[] colors; // Array of 6 characters mapping sides clockwise

        public Piece(String s) {
            this.colors = s.toCharArray();
        }

        public Piece(char[] colors) {
            this.colors = colors;
        }

        // Generates a new piece layout shifted by a specific rotation step
        public Piece getRotated(int steps) {
            char[] rotated = new char[6];
            for (int i = 0; i < 6; i++) {
                rotated[(i + steps) % 6] = this.colors[i];
            }
            return new Piece(rotated);
        }
    }
}

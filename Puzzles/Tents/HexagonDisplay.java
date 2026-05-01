import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.RenderingHints;

// https://docs.google.com/spreadsheets/d/1h_6Tf4JD9szT1_ky2YI1RWazNDuB7JPnzXAZQNc-dLo/edit?usp=sharing
/*

	0	1	2	3	4	5					Piece
  |______________________
0 | 5	1	4	0	3	2	YELLOW,GREEN,WHITE,BLUE,RED,ORANGE	{YELLOW, GREEN,  WHITE,  BLUE,   RED,    ORANGE},  0  a	YGWBRO
1 | 0	5	4	2	3	1	BLUE,YELLOW,WHITE,ORANGE,RED,GREEN	{BLUE,   YELLOW, WHITE,  ORANGE, RED,    GREEN },  1  b	BYWORG
2 | 2	4	5	0	1	3	ORANGE,WHITE,YELLOW,BLUE,GREEN,RED	{ORANGE, WHITE,  YELLOW, BLUE,   GREEN,  RED   },  2  c	OWYBGR
3 | 0	3	2	1	5	4	BLUE,RED,ORANGE,GREEN,YELLOW,WHITE	{BLUE,   RED,    ORANGE, GREEN,  YELLOW, WHITE },  3  d	BROGYW
4 | 1	5	3	2	4	0	GREEN,YELLOW,RED,ORANGE,WHITE,BLUE	{GREEN,  YELLOW, RED,    ORANGE, WHITE,  BLUE  },  4  e	GYROWB
5 | 0	2	4	1	3	5	BLUE,ORANGE,WHITE,GREEN,RED,YELLOW	{BLUE,   ORANGE, WHITE,  GREEN,  RED,    YELLOW},  5  f	BOWGRY
6 | 1	0	4	3	5	2	GREEN,BLUE,WHITE,RED,YELLOW,ORANGE	{GREEN,  BLUE,   WHITE,  RED,    YELLOW, ORANGE}   6  g	GBWRYO

*/

public class HexagonDisplay extends JPanel {

    private static final Color YELLOW  = new Color(255, 220, 0);
    private static final Color GREEN   = new Color(50, 200, 80);
    private static final Color WHITE   = new Color(255, 255, 255);
    private static final Color BLUE    = new Color(30, 130, 255);
    private static final Color RED     = new Color(230, 40, 40);
    private static final Color ORANGE  = new Color(255, 140, 0);
	
    private static final int iYELLOW  = 0;
    private static final int iGREEN   = 1;
    private static final int iWHITE   = 2;
    private static final int iBLUE    = 3;
    private static final int iRED     = 4;
    private static final int iORANGE  = 5;

    enum HexColor {
        YELLOW(255, 220, 0),
        GREEN(50, 200, 80),
        WHITE(255, 255, 255),
        BLUE(30, 130, 255),
        RED(230, 40, 40),
        ORANGE(255, 140, 0);

        final Color awt;
        HexColor(int r, int g, int b) { this.awt = new Color(r, g, b); }
    }

     private static final HexColor[][] COLOR_ORDERS2 = {
        {HexColor.YELLOW, HexColor.GREEN,  HexColor.WHITE,  HexColor.BLUE,   HexColor.RED,    HexColor.ORANGE},
        {HexColor.GREEN,  HexColor.BLUE,   HexColor.ORANGE, HexColor.YELLOW, HexColor.WHITE,  HexColor.RED   },
        {HexColor.WHITE,  HexColor.ORANGE, HexColor.GREEN,  HexColor.RED,    HexColor.YELLOW, HexColor.BLUE  },
        {HexColor.BLUE,   HexColor.YELLOW, HexColor.RED,    HexColor.GREEN,  HexColor.ORANGE, HexColor.WHITE },
        {HexColor.RED,    HexColor.WHITE,  HexColor.YELLOW, HexColor.ORANGE, HexColor.BLUE,   HexColor.GREEN },
        {HexColor.ORANGE, HexColor.RED,    HexColor.BLUE,   HexColor.WHITE,  HexColor.GREEN,  HexColor.YELLOW},
        {HexColor.YELLOW, HexColor.BLUE,   HexColor.GREEN,  HexColor.RED,    HexColor.WHITE,  HexColor.ORANGE},
    };

private static final int[][] COLOR_ORDERS = {
    {iYELLOW, iGREEN,  iWHITE,  iBLUE,   iRED,    iORANGE}, /* 0 */
	{iBLUE,   iYELLOW, iWHITE,  iORANGE, iRED,    iGREEN }, /* 1 */
	{iORANGE, iWHITE,  iYELLOW, iBLUE,   iGREEN,  iRED   }, /* 2 */
	{iBLUE,   iRED,    iORANGE, iGREEN,  iYELLOW, iWHITE }, /* 3 */
	{iGREEN,  iYELLOW, iRED,    iORANGE, iWHITE,  iBLUE  }, /* 4 */
	{iBLUE,   iORANGE, iWHITE,  iGREEN,  iRED,    iYELLOW}, /* 5 */
    {iGREEN,  iBLUE,   iWHITE,  iRED,    iYELLOW, iORANGE}  /* 6 */
};

    // 6 colors for the 6 sides of each hexagon
    private static final Color[] PALETTE = {
        HexColor.YELLOW,   // yellow 0
        GREEN,             // green  1
        HexColor.WHITE,    // white  2
        BLUE,              // blue   3
        HexColor.RED,      // red    4
        new Color(255, 140, 0)    // orange 5
    };

    private static final int NUM_HEXAGONS = 7;
    private static final int HEX_RADIUS   = 75;  // circumradius
    private static final int SIDE_WIDTH   = 14;  // thickness of colored edge band

    // rotation angles (degrees) per hexagon
    private final double[] angles = new double[NUM_HEXAGONS];

    // centres computed in paintComponent (layout depends on panel size)
    private final Point[] centres = new Point[NUM_HEXAGONS];

    // ------------------------------------------------------------------ //

    public HexagonDisplay() {
        setBackground(new Color(18, 18, 28));
        setPreferredSize(new Dimension(900, 340));

        addMouseListener(new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e) {
                for (int i = 0; i < NUM_HEXAGONS; i++) {
                    if (centres[i] != null && hexContains(centres[i], e.getPoint())) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            angles[i] += 60;
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            angles[i] -= 60;
                        }
                        repaint();
                        return;
                    }
                }
            }
        });
    }

    // ------------------------------------------------------------------ //

    /** Flat-top hexagon: vertex k is at angle (60*k) degrees from centre. */
    private Polygon makeHexagon(int cx, int cy, int r, double rotDeg) {
        int[] xs = new int[6];
        int[] ys = new int[6];
        for (int k = 0; k < 6; k++) {
            double angle = Math.toRadians(60.0 * k + rotDeg);
            xs[k] = cx + (int) Math.round(r * Math.cos(angle));
            ys[k] = cy + (int) Math.round(r * Math.sin(angle));
        }
        return new Polygon(xs, ys, 6);
    }

    /** True if point p lies inside the flat-top hexagon centred at c. */
    private boolean hexContains(Point c, Point p) {
        Polygon hex = makeHexagon(c.x, c.y, HEX_RADIUS, 0);
        return hex.contains(p);
    }

    // ------------------------------------------------------------------ //

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        computeCentres();

        for (int i = 0; i < NUM_HEXAGONS; i++) {
            drawHexagon(g2, i);
        }

        // Legend
        drawLegend(g2);

        g2.dispose();
    }

    /**
     * Layout:
     *   col 0        col 1   col 2   col 3        col 4
     *   hex 0                hex 2   hex 3                hex 6
     *                hex 1                hex 4
     *   hex 5 (left outer)            hex 6 is right outer
     *
     * Simpler reading of "3 in the middle, 2 on each side":
     *   Left pair  (hexagons 0,1) — staggered column
     *   Middle trio (hexagons 2,3,4) — three in a row
     *   Right pair  (hexagons 5,6) — staggered column
     */
    private void computeCentres() {
        int w  = getWidth();
        int h  = getHeight();
        int cy = h / 2;

        // horizontal spacing between column centres
        int colGap = (int)(HEX_RADIUS * 1.95);
        // vertical offset for staggered outer pairs
        int vOff   = (int)(HEX_RADIUS * 1.10);

        // total width of 7 columns (5 logical col positions spread over 7 hexagons)
        // positions: left-top, left-bot, mid-left, mid-centre, mid-right, right-top, right-bot
        int totalW = colGap * 4; // 5 positions → 4 gaps
        int startX = (w - totalW) / 2;

        int x0 = startX;               // left column
        int x1 = startX + colGap;      // left-inner (stagger)
        int x2 = startX + colGap * 2;  // mid-left
        int x3 = startX + colGap * 3;  // mid-right
        int x4 = startX + colGap * 4;  // right column

        // Layout:
        //  0 = left outer top,   1 = left outer bot
        //  2 = middle left,      3 = middle centre,  4 = middle right
        //  5 = right outer top,  6 = right outer bot
        centres[0] = new Point(x1, cy - vOff);
        centres[1] = new Point(x1, cy + vOff);

        centres[2] = new Point((x2 + x3) / 2, cy - (2* vOff));
        centres[3] = new Point((x2 + x3) / 2, cy);  // centre of middle trio
        centres[4] = new Point((x2 + x3) / 2, cy + (2* vOff));

        centres[5] = new Point(x3 + colGap, cy - vOff);
        //centres[6] = new Point(x4, cy + vOff);
        centres[6] = new Point(x3 + colGap, cy + vOff);
    }

    private void drawHexagon(Graphics2D g2, int idx) {
        Point c   = centres[idx];
        double rot = angles[idx];
        int[] order = COLOR_ORDERS[idx];

        // ---- filled inner hexagon ----
        Polygon inner = makeHexagon(c.x, c.y, HEX_RADIUS - SIDE_WIDTH, rot);
        g2.setColor(new Color(25, 25, 40));
        g2.fillPolygon(inner);

        // ---- coloured sides ----
        // Each "side" is the trapezoid between outer edge k→k+1 and the
        // corresponding inner edge.
        int outerR = HEX_RADIUS;
        int innerR = HEX_RADIUS - SIDE_WIDTH;

        for (int k = 0; k < 6; k++) {
            double a1 = Math.toRadians(60.0 * k       + rot);
            double a2 = Math.toRadians(60.0 * (k + 1) + rot);

            int ox1 = c.x + (int) Math.round(outerR * Math.cos(a1));
            int oy1 = c.y + (int) Math.round(outerR * Math.sin(a1));
            int ox2 = c.x + (int) Math.round(outerR * Math.cos(a2));
            int oy2 = c.y + (int) Math.round(outerR * Math.sin(a2));

            int ix1 = c.x + (int) Math.round(innerR * Math.cos(a1));
            int iy1 = c.y + (int) Math.round(innerR * Math.sin(a1));
            int ix2 = c.x + (int) Math.round(innerR * Math.cos(a2));
            int iy2 = c.y + (int) Math.round(innerR * Math.sin(a2));

            int[] xs = {ox1, ox2, ix2, ix1};
            int[] ys = {oy1, oy2, iy2, iy1};
            Polygon side = new Polygon(xs, ys, 4);

            Color base = PALETTE[order[k]];
            g2.setColor(base);
            g2.fillPolygon(side);

            // subtle dark edge between sides
            g2.setColor(new Color(18, 18, 28, 180));
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawPolygon(side);
        }

        // ---- outer border ----
        Polygon outer = makeHexagon(c.x, c.y, outerR, rot);
        g2.setColor(new Color(255, 255, 255, 40));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawPolygon(outer);

        // ---- index label in centre ----
        g2.setColor(new Color(180, 180, 200));
        g2.setFont(new Font("Monospaced", Font.BOLD, 13));
        String label = String.valueOf(idx + 1);
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(label,
                c.x - fm.stringWidth(label) / 2,
                c.y + fm.getAscent() / 2 - 2);
    }

    private void drawLegend(Graphics2D g2) {
        String[] names = {"Yellow", "Green", "White", "Blue", "Red", "Orange"};
        int swatchSize = 14;
        int gap = 8;
        int startX = 20;
        int y = getHeight() - 28;

        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));

        for (int i = 0; i < PALETTE.length; i++) {
            int x = startX + i * 110;
            g2.setColor(PALETTE[i]);
            g2.fillRoundRect(x, y, swatchSize, swatchSize, 3, 3);
            g2.setColor(new Color(200, 200, 220));
            g2.drawString(names[i], x + swatchSize + gap, y + 11);
        }

        // instructions
        g2.setColor(new Color(120, 120, 150));
        g2.setFont(new Font("Monospaced", Font.ITALIC, 11));
        g2.drawString("Left-click → rotate +60°   Right-click → rotate −60°",
                getWidth() / 2 - 175, getHeight() - 10);
    }

    // ------------------------------------------------------------------ //

    public static void main(String[] args) {
		for (int i = 0; i < COLOR_ORDERS2.length; i++) {
		            System.out.print("Hexagon " + (i + 0) + " colors: ");
		            for (HexColor color : COLOR_ORDERS2[i]) {
		                System.out.print(color.name() + " ");
		            }
		            System.out.println();
        }
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hexagon Display");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            HexagonDisplay panel = new HexagonDisplay();
            frame.add(panel);

            // title bar styling
            frame.getRootPane().putClientProperty("apple.awt.brushMetalLook", true);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setVisible(true);
        });
    }
}

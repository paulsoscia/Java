import java.awt.*;
import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.Random;

class MyCanvas extends JComponent {


	public static int BLUE 		= 0;
	public static int GREEN 	= 1;
	public static int ORANGE 	= 2;
	public static int RED 		= 3;
	public static int WHITE 	= 4;
	public static int YELLOW 	= 5;

	public 	String str2 = "0123456";
		//        **
		// 01 234 56 ( piece )
		// 23 401 56 ( location )

	public static int[][] tentArray = new int[][] {
					//	 Side 0  Side 1	 Side 2  Side 3  Side 4  Side 5				6 sides
						{YELLOW, GREEN,  WHITE,  BLUE,   RED,    ORANGE	}, 		/* Piece 0 with 6 sides */
						{BLUE,   YELLOW, WHITE,  ORANGE, RED,    GREEN	},  	/* Piece 1 with 6 sides */
						{ORANGE, WHITE,  YELLOW, BLUE,   GREEN,  RED	},    	/* Piece 2 with 6 sides */
						{BLUE,   RED,    ORANGE, GREEN,  YELLOW, WHITE	},  	/* Piece 3 with 6 sides */
						{GREEN,  YELLOW, RED,    ORANGE, WHITE,  BLUE	},   	/* Piece 4 with 6 sides */
						{BLUE,   ORANGE, WHITE,  GREEN,  RED,    YELLOW	},		/* Piece 5 with 6 sides */
						{GREEN,  BLUE,   WHITE,  RED,    YELLOW, ORANGE	}		/* Piece 6 with 6 sides */
					};

	public String getCharAtIndex(String str, int index) {
		if (str == null || index < 0 || index >= str.length()) {
			return "";
		}
		return String.valueOf(str.charAt(index));
	}

	public Color displayColor(int iVertex, int iPieceNumber, int iWedgeNumber, boolean IgnoreLocalColor)
	{
		/* Six colors */
		int iLocalColor ;
		if (IgnoreLocalColor)
			iLocalColor = tentArray[iPieceNumber][iVertex];
		else
			//				mapColor(int iVertex, int iPieceNumber, int iWedgeNumber)
			iLocalColor = 	mapColor(iVertex, iPieceNumber ,0);

		if (iLocalColor == BLUE ) {
			return (new Color(51, 204, 255) );
		}
		if (iLocalColor == GREEN ) {
			return (new Color(0, 255, 0) );
		}
		if (iLocalColor == ORANGE ) {
			/*	Orange //(255, 102, 0) ) */
			return (new Color(255, 153, 0) );
		}
		if (iLocalColor == RED ) {
			return (new Color(255, 0, 0) );
		}
		if (iLocalColor == WHITE ) {
			return (new Color(255, 255, 255) );
		}
		if (iLocalColor == YELLOW ) {
			return (new Color(255, 247, 0) );
		}

		return (new Color(0, 0, 0) ); /* BLACK ; should never happen */
	}

	public int decodeReMapPiece(int iPieceNumber)
	{
		// 01 234 56 ( piece)
		// 23 401 56 ( location )
		int iLocalPieceNumber = iPieceNumber;

		if (iPieceNumber ==0) {
			iLocalPieceNumber =2;
		}
		if (iPieceNumber ==1) {
			iLocalPieceNumber =3;
		}
		if (iPieceNumber ==2) {
			iLocalPieceNumber =4;
		}
		if (iPieceNumber ==3) {
			iLocalPieceNumber =0;
		}
		if (iPieceNumber ==4) {
			iLocalPieceNumber =1;
		}
		if (iPieceNumber ==5) {
			iLocalPieceNumber =5;
		}
		if (iPieceNumber ==6) {
			iLocalPieceNumber =6;
		}
		return(iPieceNumber);
		//return(iLocalPieceNumber) ;
	}

	public int mapColor(int iVertex, int iPieceNumber, int iWedgeNumber )
	{
		//if (iColorMethod == 6) { // Color wedges all Hexagons differnt color order (using arrays/RealPieces) with zero position (rotate) ; ordered Left, Middle, then last Right

		return( tentArray[iPieceNumber][iVertex] ) ;
		//return( tentArray[decodeReMapPiece(iPieceNumber)][iVertex] ) ;
		//return( tentArray[decodeReMapPiece(iPieceNumber)][((iVertex+1)%6)] ) ;

	}

    public void paint(Graphics g)
    {

	// draw a Polygon
	//int [ ] x = {20,  35,  50,  65, 80, 95};
	//int [ ] y = {60, 105, 105, 110, 95, 95};

	super.paintComponent(g);

	Polygon polyHexagon 		= new Polygon();
	int iNumberSides = 6;

	Polygon polyTriangle  		= new Polygon();
	Polygon polyNextTriangle  	= new Polygon();

	// three in the middle

	int offSetX = 10;
	int offSetY = 300;

//   Create a key
//      BEGIN
        g.setFont(new Font("SansSerif", Font.PLAIN, 14));

        int startXText = 30;
        int startXSquare = 100;
        int startY = 75;
        int spacing = 35;
        int size = 20;
		int yLocation = 0;
		int iRotationToLeft = 0;
		String sMessage = "";
        spacing = 22;

		for (int piece = 0 ; piece < 7; piece++) {
		yLocation = offSetY+startY + (piece * spacing) + 40 ;
		g.drawString("Piece #"+piece, startXText + offSetX -25, yLocation + 13 );
		sMessage = "";
		for (int i = 0; i < 6; i++) {
            		// Draw text to the left
            		g.setColor(Color.BLACK);
			sMessage = "";
			// Draw small square to the right of text
			// Rotate 0 Times To The Left
			//g.setColor( displayColor(i, piece, 0, true) );
			if (piece == 0)
				// Rotate 1 Times To The Left
				iRotationToLeft = 1;
			if (piece == 1)
				// Rotate 1 Times To The Left
				iRotationToLeft = 5;
			if (piece == 2)
				// Rotate 1 Times To The Left
				iRotationToLeft = 4;
			if (piece == 3)
				// Rotate 1 Times To The Left
				iRotationToLeft = 3;
			if (piece == 4)
				// Rotate 1 Times To The Left
				iRotationToLeft = 0;
				sMessage = "{YELLOW, GREEN,  WHITE,  BLUE,   RED,    ORANGE	}";
			if (piece == 5)
				// Rotate 1 Times To The Left
				iRotationToLeft = 3;
			if (piece == 6)
				// Rotate 1 Times To The Left
				iRotationToLeft = 0;

			g.setColor( displayColor( (i+iRotationToLeft)%6, piece, 0, true) );
			g.fillRect(startXSquare+offSetX + (i * spacing) - 12, yLocation , size, size);

			// Optional outline for visibility on white background (e.g. yellow)
			g.setColor(Color.BLACK);
			g.drawRect(startXSquare+offSetX + (i * spacing) - 12, yLocation, size, size);
        	}
        if (piece == 0 )
        	sMessage = "{YELLOW, GREEN,  WHITE,  BLUE,   RED,    ORANGE	}";
        if (piece == 1 )
        	sMessage = "{BLUE,   YELLOW, WHITE,  ORANGE, RED,    GREEN	}";
        if (piece == 2 )
        	sMessage = "{ORANGE, WHITE,  YELLOW, BLUE,   GREEN,  RED	}";
        if (piece == 3 )
        	sMessage = "{BLUE,   RED,    ORANGE, GREEN,  YELLOW, WHITE	}";
        if (piece == 4 )
        	sMessage = "{GREEN,  YELLOW, RED,    ORANGE, WHITE,  BLUE	}";
        if (piece == 5 )
        	sMessage = "{BLUE,   ORANGE, WHITE,  GREEN,  RED,    YELLOW	}";
        if (piece == 6 )
        	sMessage = "{GREEN,  BLUE,   WHITE,  RED,    YELLOW, ORANGE	}";

		g.drawString("Position #" + piece + ", Rotation=" + iRotationToLeft + " (Left) " + sMessage, startXSquare+offSetX + (6 * spacing) - 12, yLocation + 13 );
	}

	//   Create a key
	//      END
	//

//
//					*
//				*		*
//					*
//				*		*
//					*
//
	boolean bDisplayLocation0 = true; 	// Left two (top)
	boolean bDisplayLocation1 = true;  	// Left two (bottom)

	boolean bDisplayLocation2 = true;  	// Middle Three (top)
	boolean bDisplayLocation3 = true; 	// Middle Three (middle)
	boolean bDisplayLocation4 = true;  	// Middle Three (bottom)

	boolean bDisplayLocation5 = true; 	// Right two (top)
	boolean bDisplayLocation6 = true; 	// Right two (bottom)

	int iPiece = 0;

	//bDisplayLocation2 = false;  // Middle Three (top)
	//bDisplayLocation3 = false; 	// Middle Three (middle)
	//bDisplayLocation4 = false;  // Middle Three (bottom)

	// 3 Middle Rows ( only the 3 middle)
	//if ( bDisplayLocation2 || bDisplayLocation3 || bDisplayLocation4 ) {
		for (int yOffSet=100; yOffSet < 301 ;yOffSet=yOffSet+100) {
			int xCoordinate = 0;
			int yCoordinate = 0;
			int xPreviousCoordinate = 0;
			int yPreviousCoordinate = 0;
			int xTheFirstCoordinate = 0;
			int yTheFirstCoordinate = 0;

			polyHexagon = new Polygon();
			polyTriangle = new Polygon();
			int xMiddleHexagon =300;
			int yMiddleHexagon =yOffSet;

			// each polygon has 6 sides
			for (int iVertex = 0; iVertex < iNumberSides; iVertex++) {  // vertex/corners each polygon has 6 sides
				if (iVertex>0) {
					xPreviousCoordinate = xCoordinate ;
					yPreviousCoordinate = yCoordinate ;
				}
				xCoordinate = (int) (xMiddleHexagon   	+ 50 * Math.cos(iVertex * 2 * Math.PI / iNumberSides));
				yCoordinate = (int) (yOffSet   		+ 50 * Math.sin(iVertex * 2 * Math.PI / iNumberSides));
				if (iVertex==0 ) {
					xTheFirstCoordinate = xCoordinate;
					yTheFirstCoordinate = yCoordinate;
				}

				// Always adds the point
				polyHexagon.addPoint(	xCoordinate, yCoordinate);
				polyTriangle.addPoint(	xCoordinate, yCoordinate);

				if ( iVertex>=1  ) {
					if ( (iVertex==1) || (iVertex==3) || (iVertex==5) ) {   //Odds
						polyTriangle.addPoint(xMiddleHexagon, yOffSet);
						if ( (bDisplayLocation2 && iPiece==0) || (bDisplayLocation3  && iPiece==1) || (bDisplayLocation4  && iPiece==2) )
						{
							g.setColor( displayColor(iVertex,iPiece,0,false));
							g.drawPolygon(polyTriangle);
							g.fillPolygon(polyTriangle);
						}
						polyTriangle = new Polygon();
					}
					if ( (iVertex==2) || (iVertex ==4) || (iVertex==5) ) {	// Next Triangles (Evens)
							polyNextTriangle.addPoint( xCoordinate, 	yCoordinate);
							polyNextTriangle.addPoint( xMiddleHexagon, 	yOffSet);
							if ( (iVertex==2) || (iVertex ==4) ) {
								g.setColor( displayColor(iVertex,iPiece,0,false));
								polyNextTriangle.addPoint( xPreviousCoordinate, yPreviousCoordinate);
							}
							else {		//5  NextTriangle
								g.setColor( displayColor(0,iPiece,0,false));
								polyNextTriangle.addPoint( xTheFirstCoordinate, yTheFirstCoordinate);
							}
							if ( (bDisplayLocation2 && iPiece==0) || (bDisplayLocation3  && iPiece==1) || (bDisplayLocation4  && iPiece==2) ) {
								g.drawPolygon(polyNextTriangle);
								g.fillPolygon(polyNextTriangle);
							}
							polyNextTriangle = new Polygon();
					}
				} 	//		if ( iVertex>=1  ) {
			}		//		for (int iVertex = 0; iVertex < iNumberSides; iVertex++) {  // vertex/corners each polygon has 6 sides
			if (bDisplayLocation2 || bDisplayLocation3 || bDisplayLocation4 ) {
					g.drawPolygon(polyHexagon); // hexagon
			}
			iPiece++;
		}			//		// 3 Middle Rows
	//}


	//bDisplayLocation0 = false; 		// Left two (top)
	//bDisplayLocation1 = false; 		// Left two (bottom)
	//bDisplayLocation5 = false; 		// Right two (top)
	//bDisplayLocation6 = false; 		// Right two (bottom)

	// two on each side (total 4) ; two on left and
	//								two on the right
	//if ( bDisplayLocation0 || bDisplayLocation1 || bDisplayLocation5 || bDisplayLocation6 ) {
		for (int xOffSet=215; xOffSet < 386 ;xOffSet=xOffSet+170) {  		// x Middle/center of the Polygon/Hexagon
			for (int yOffSet=150; yOffSet < 251 ;yOffSet=yOffSet+100) {	// y Middle/center of the Polygon/Hexagon
				polyHexagon = new Polygon();

				int xCoordinate = 0;
				int yCoordinate = 0;
				int xPreviousCoordinate = 0;
				int yPreviousCoordinate = 0;
				int xTheFirstCoordinate = 0;
				int yTheFirstCoordinate = 0;
				polyTriangle = new Polygon();
				int xMiddleHexagon = xOffSet;
				int yMiddleHexagon = yOffSet;

				for (int iVertex = 0; iVertex < iNumberSides; iVertex++) {  // vertex/corners
					if (iVertex > 0) {
						xPreviousCoordinate = xCoordinate ;
						yPreviousCoordinate = yCoordinate ;
					}
					xCoordinate = (int) (xMiddleHexagon   + 50 * Math.cos(iVertex * 2 * Math.PI / iNumberSides));
					yCoordinate = (int) (yMiddleHexagon   + 50 * Math.sin(iVertex * 2 * Math.PI / iNumberSides));

					if (iVertex == 0 ) {
						xTheFirstCoordinate = xCoordinate;
						yTheFirstCoordinate = yCoordinate;
					}
					// Always adds the point
					polyHexagon.addPoint(	xCoordinate, yCoordinate);
					polyTriangle.addPoint(	xCoordinate, yCoordinate);

					if ( iVertex>=1  ) {
						if ( (iVertex==1) || (iVertex==3) || (iVertex==5) ) {   //Odds
							polyTriangle.addPoint(xMiddleHexagon, yOffSet);
							g.setColor( displayColor(iVertex,iPiece,0,false));
							if ( (bDisplayLocation0 && iPiece==3) || (bDisplayLocation1  && iPiece==4) || (bDisplayLocation5  && iPiece==5) || (bDisplayLocation6  && iPiece==6) ) {
								g.drawPolygon(polyTriangle);
								g.fillPolygon(polyTriangle);
							}
							polyTriangle = new Polygon();
						}
					if ( (iVertex==2) || (iVertex ==4) || (iVertex==5) ) {	// Next Triangles (Evens)
							polyNextTriangle.addPoint( xCoordinate, 	yCoordinate);
							polyNextTriangle.addPoint( xMiddleHexagon, 	yOffSet);
							if ( (iVertex==2) || (iVertex ==4) ) {
								g.setColor( displayColor(iVertex,iPiece,0,false));
								polyNextTriangle.addPoint( xPreviousCoordinate, yPreviousCoordinate);
							}
							else {		//5  NextTriangle
								g.setColor( displayColor(0,iPiece,0,false));
								polyNextTriangle.addPoint( xTheFirstCoordinate, yTheFirstCoordinate);
							}
							if ( (bDisplayLocation0 && iPiece==3) || (bDisplayLocation1  && iPiece==4) || (bDisplayLocation5  && iPiece==5) || (bDisplayLocation6  && iPiece==6) ) {
								g.drawPolygon(polyNextTriangle);
								g.fillPolygon(polyNextTriangle);
							}
							polyNextTriangle = new Polygon();

					}

				}			}
				//if ( (bDisplayLocation2 && iPiece==0) || (bDisplayLocation3  && iPiece==1) || (bDisplayLocation4  && iPiece==2) )
				if ( (bDisplayLocation0 && iPiece==3) || (bDisplayLocation1  && iPiece==4) || (bDisplayLocation5  && iPiece==5) || (bDisplayLocation6  && iPiece==6) )
					g.drawPolygon(polyHexagon); // hexagon
				iPiece++;
			}
		}

		}
	//}
}

public class TentsClean {

		public static String getCharAtIndex(String str, int index) {
			if (str == null || index < 0 || index >= str.length()) {
				return "";
			}
			return String.valueOf(str.charAt(index));
		}

	    public static void main(String[] a)
	    {

		String str = "0123456";
		// 0123456 ( piece)
		// 2340156 ( location
		// 23 401 56 ( location )
		System.out.println("" + getCharAtIndex(str,0) + " => " + getCharAtIndex(str,2));
		System.out.println("" + getCharAtIndex(str,1) + " => " + getCharAtIndex(str,3));

		System.out.println("" + getCharAtIndex(str,2) + " => " + getCharAtIndex(str,4));
		System.out.println("" + getCharAtIndex(str,3) + " => " + getCharAtIndex(str,0));
		System.out.println("" + getCharAtIndex(str,4) + " => " + getCharAtIndex(str,1));

		System.out.println("" + getCharAtIndex(str,5) + " => " + getCharAtIndex(str,5));
		System.out.println("" + getCharAtIndex(str,6) + " => " + getCharAtIndex(str,6));

        // int iTemp = tentArray[0][0];
		// creating object of JFrame(Window popup)
		JFrame window = new JFrame();

		// setting closing operation
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting size of the pop window
		//window.setBounds(30, 30, 200, 200);
		window.setBounds(0, 0, 1000, 1000);

		// setting canvas for draw
		window.getContentPane().add(new MyCanvas());

		// set visibility
		window.setVisible(true);
	    }

}

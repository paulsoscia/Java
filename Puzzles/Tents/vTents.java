// Java program to draw a line in Applet
  
import java.awt.*;
import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.Random;
  
class MyCanvas extends JComponent {

//	public static int lCount	= 0;

	public static int BLUE 		= 0;
	public static int GREEN 	= 1;
	public static int ORANGE 	= 2;
	public static int RED 		= 3;
	public static int WHITE 	= 4;
	public static int YELLOW 	= 5;
        
        public 	String str2 = "0123456";

	public static int[][] tentArray = new int[][] { 
						{YELLOW, GREEN,  WHITE,  BLUE,   RED,    ORANGE}, /* 0 */ 
						{BLUE,   YELLOW, WHITE,  ORANGE, RED,    GREEN},  /* 1 */
						{ORANGE, WHITE,  YELLOW, BLUE,   GREEN,  RED},    /* 2 */
						{BLUE,   RED,    ORANGE, GREEN,  YELLOW, WHITE},  /* 3 */
						{GREEN,  YELLOW, RED,    ORANGE, WHITE,  BLUE},   /* 4 */
						{BLUE,   ORANGE, WHITE,  GREEN,  RED,    YELLOW}, /* 5 */
						{GREEN,  BLUE,   WHITE,  RED,    YELLOW, ORANGE}  /* 6 */
					};
    /**
    * permutation function
    * @param str string to calculate permutation for
    * @param l starting index
    * @param r end index
    */

	public Color displayColor(int iVertex, int iPieceNumber, int iWedgeNumber) 
	{
	
		int iLocalColor = mapColor(iVertex, iPieceNumber ,0, 6);

		if (iLocalColor == 0 ) {  	/*	BLUE*/
			return (new Color(51, 204, 255) );
		}
		if (iLocalColor == 1 ) {  	/*	GREEN*/;;;;;;;;;;
			return (new Color(0, 255, 0) );
		}
		if (iLocalColor == 2 ) {  	/*	Orange //(255, 102, 0) ) */
			return (new Color(255, 153, 0) );
		}
		if (iLocalColor == 3 ) {  	/*	Red*/
			return (new Color(255, 0, 0) );
		}
		if (iLocalColor == 4 ) {  	/*	WHITE*/
			return (new Color(255, 255, 255) );
		}
		if (iLocalColor == 5 ) {  	/*	Yellow  +1 Mod 6*/
			return (new Color(255, 247, 0) );
		}

                return (new Color(0, 0, 0) ); 
	}

	public int decodeReMapPiece(int iPieceNumber) 
	{
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
		return(iLocalPieceNumber) ;
	}

	public int mapColor(int iVertex, int iPieceNumber, int iWedgeNumber, int iColorMethod) 
	{

		if (iColorMethod == 0) { //One Color all Hexagons
			return(0);
		}
		if (iColorMethod == 1) { //Each Hexagon One Color 
			return (iPieceNumber);
		}
		if (iColorMethod == 2) { // Color wedges all Hexagons same color order
			return(iVertex);
		}
		if (iColorMethod == 3) { // Color wedges all Hexagons same color order but in the zero position (rotate) 
			return( ((iVertex+1)%6) );
		}
		if (iColorMethod == 4) { // Color wedges all Hexagons differnt color order (using arrays/RealPieces) ; ordered Middle, Left, then last Right
			return( tentArray[iPieceNumber][iVertex] ) ;
		}
		if (iColorMethod == 5) { // Color wedges all Hexagons differnt color order (using arrays/RealPieces) with zero position (roate) ; ordered Middle, Left, then last Right
			return( tentArray[iPieceNumber][(iVertex+1)%6] ) ;
		}
		if (iColorMethod == 6) { // Color wedges all Hexagons differnt color order (using arrays/RealPieces) with zero position (roate) ; ordered Left, Middle, then last Right 
			return( tentArray[decodeReMapPiece(iPieceNumber)][((iVertex+1)%6)] ) ;
		}
		
		return (iVertex);
	}

	public void test(String str) 
	{
		System.out.println(str);
	}

	    public String swap(String a, int i, int j)
	    {
		char temp;
		char[] charArray = a.toCharArray();

		temp = charArray[i] ;
		charArray[i] = charArray[j];
		charArray[j] = temp;

		return String.valueOf(charArray);
	    }

	    public void permute(String str, int l, int r, int lCount)
	    {
		if (l == r){
		    	//System.out.println(str);
			test(str);
			//Total Count =5040 [recursive] 
		    	lCount++;
		}
		else
		{
		    for (int i = l; i <= r; i++)
		    {
		        str = swap(str,l,i);
		        permute(str, l+1, r, lCount);
		        str = swap(str,l,i);
		    }
		}
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
	int iPiece = 0;
	for (int yOffSet=100; yOffSet < 301 ;yOffSet=yOffSet+100) {
		int xCoordinate = 0; 
		int yCoordinate = 0;
		int xPreviousCoordinate = 0; 
		int yPreviousCoordinate = 0;
		int xTheFirstCoordinate = 0; 
		int yTheFirstCoordinate = 0;
		Random rn = new Random();
  
		polyHexagon = new Polygon();
		polyTriangle = new Polygon();
                int xMiddleHexagon =300;
                int yMiddleHexagon =yOffSet;
       		for (int iVertex = 0; iVertex < iNumberSides; iVertex++) {  // vertex/corners
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
					g.setColor( displayColor(iVertex,iPiece,0));
					g.drawPolygon(polyTriangle);
					g.fillPolygon(polyTriangle);
					polyTriangle = new Polygon();
				}
				if ( (iVertex==2) || (iVertex ==4) || (iVertex==5) ) {	// Next Triangles (Evens)  	
						polyNextTriangle.addPoint( xCoordinate, 	yCoordinate);
						polyNextTriangle.addPoint( xMiddleHexagon, 	yOffSet);
						if ( (iVertex==2) || (iVertex ==4) ) {
							g.setColor( displayColor(iVertex,iPiece,0));							
							polyNextTriangle.addPoint( xPreviousCoordinate, yPreviousCoordinate);
						}
						else {		//5  NextTriangle
							g.setColor( displayColor(0,iPiece,0));
							polyNextTriangle.addPoint( xTheFirstCoordinate, yTheFirstCoordinate);
						}
						g.drawPolygon(polyNextTriangle);
						g.fillPolygon(polyNextTriangle);
						polyNextTriangle = new Polygon();

				}

			}
		}
       		g.drawPolygon(polyHexagon); // hexagon
		iPiece++;
	}
	
	// two on each side (total 4) ; two on left and two on the righ 
	for (int xOffSet=215; xOffSet < 386 ;xOffSet=xOffSet+170) {  		// x Middle/center of the Polygon/Hexagon
		for (int yOffSet=150; yOffSet < 251 ;yOffSet=yOffSet+100) {	// y Middle/center of the Polygon/Hexagon
			polyHexagon = new Polygon();

				int xCoordinate = 0; 
				int yCoordinate = 0;
				int xPreviousCoordinate = 0; 
				int yPreviousCoordinate = 0;
				int xTheFirstCoordinate = 0; 
				int yTheFirstCoordinate = 0;
				Random rn = new Random();
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
					g.setColor( displayColor(iVertex,iPiece,0));
					g.drawPolygon(polyTriangle);
					g.fillPolygon(polyTriangle);
					polyTriangle = new Polygon();
				}
				if ( (iVertex==2) || (iVertex ==4) || (iVertex==5) ) {	// Next Triangles (Evens)  
						polyNextTriangle.addPoint( xCoordinate, 	yCoordinate);
						polyNextTriangle.addPoint( xMiddleHexagon, 	yOffSet);
						if ( (iVertex==2) || (iVertex ==4) ) {		
							g.setColor( displayColor(iVertex,iPiece,0));					
							polyNextTriangle.addPoint( xPreviousCoordinate, yPreviousCoordinate);
						}
						else {		//5  NextTriangle
							g.setColor( displayColor(0,iPiece,0));
							polyNextTriangle.addPoint( xTheFirstCoordinate, yTheFirstCoordinate);
						}
						g.drawPolygon(polyNextTriangle);
						g.fillPolygon(polyNextTriangle);
						polyNextTriangle = new Polygon();

				}

			}			}
		       	g.drawPolygon(polyHexagon); // hexagon
			iPiece++;
		}
	}

    }
}
  
public class vTents {

	    public static void main(String[] a)
	    {

			/* recursive method START */
			int lCount	= 0;
			long lBegin;
			long lEnd;
			String str = "0123456";
			lBegin = System.currentTimeMillis();
				lCount = 0;
				int n = str.length();
				MyCanvas permutation = new MyCanvas();
				permutation.permute(str, 0, n-1, lCount);
			lEnd = System.currentTimeMillis();

			long dt = lEnd - lBegin;
			System.out.println("Total time =" + (lEnd - lBegin) + " [recursive] " );
			System.out.println("Total Count =" + lCount + " [recursive] ");
	  
                //int iTemp = tentArray[0][0];
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

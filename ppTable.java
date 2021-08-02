package ppPackage;

	import static ppPackage.ppSimParams.*;

	import java.awt.Color;

	import acm.graphics.GLine;
import acm.program.GraphicsProgram;

	public class ppTable {
		
		/**Instance variables
		 */
			private static  ppSimPaddle dispRef;
		
		public ppTable(ppSimPaddle dispRef) {
			ppTable.dispRef = dispRef;
			
			/**GLine, graphics method to draw lines for the graph
			 *
			 */
		
		GLine Blue = new GLine(XLWALL*SCALE,0,XLWALL*SCALE,scrHEIGHT);// add the first blue X line
		Blue.setColor(Color.blue);
		
		GLine Black = new GLine(0,scrHEIGHT,scrWIDTH+OFFSET,scrHEIGHT); // add the first black Y line
		ppTable.dispRef.add(Blue);
		ppTable.dispRef.add(Black);
		
		}
		
		/** 
		 * Converts world coordinates to screen coordinates
		 * @param X
		 * @return The value of X in screen coordinates (pixels)
		 *  @param Y
		 * @return The value of Y in screen coordinates =  scrHEIGHT - Y*SCALE
		 */
		
		public double toScrX(double X) {
			return X*SCALE;
		}
		
		public double toScrY(double Y) {
			return scrHEIGHT - Y*SCALE;
		}
		
		
		public double ScrtoX(double X) {
			return (X - bSize)/SCALE;  // x position the ball on the screen 
		}
		
		public double ScrtoY(double Y) {
			return (scrHEIGHT - (Y + bSize))/SCALE; // y position the ball on the screen
		}
		
		/**
		 * Exports the methods in order to use it in other classes
		 * @return dispRef
		 */
		public ppSimPaddle getDisplay() {
			return dispRef;
		}

	}


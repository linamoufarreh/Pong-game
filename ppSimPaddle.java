/** @author Lina Moufarreh
 * This program is an Event-driven program, here using the MouseEvent to detect the position of the cursor and move a paddle that will hit 
 * the ball like in a ping-pong game
 */

package ppPackage;
import static ppPackage.ppSimParams.*;

import acm.program.*;
import acm.util.RandomGenerator;

import java.awt.Color;
import java.awt.event.*;


public class ppSimPaddle extends GraphicsProgram{
	 ppPaddle myPaddle;
	 ppBall myBall;
	 ppTable myTable;
	 
	 public static void main(String[] args) {
		 new ppSimPaddle().start(args);
		  }
	 
		  public void init() {
			  
		 myTable = new ppTable(this);
		 
		 myPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,myTable);
			  
		 this.resize(scrWIDTH+OFFSET,scrHEIGHT+OFFSET);
		 addMouseListeners();
			
			RandomGenerator rgen = RandomGenerator.getInstance();  // creates an instance of rgen 
			rgen.setSeed(RSEED); //setSeed is used to replicate the same result found in the assignment
			
			/** @param iColor is the color chosen randomly by the random generator
			 * @param iYinit is the initial Y position of the ball generated randomly
			 * @param iVel is the velocity of the ball generated randomly
			 * @param iTheta is the initial projection angle of the ball, generated randomly
			 */
		

				Color iColor = Color.RED;
				double iYinit = rgen.nextDouble(YinitMIN,YinitMAX);
				double iLoss = rgen.nextDouble(EMIN,EMAX);
				double iVel = rgen.nextDouble(VoMIN,VoMAX);
				double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX);	
				
				 myBall = new ppBall(XINIT,iYinit,iVel,iTheta,iColor,iLoss,myTable,myPaddle);
				 pause(1000);
				 myBall.start();
				 myPaddle.start();

		 
		 
		
		  }
		 /**
		 * Mouse Handler - a moved event moves the paddle up and down in Y
		 */
		  public void mouseMoved(MouseEvent e) {
		 myPaddle.setY(myTable.ScrtoY((double)e.getY()));
		  }
		 }




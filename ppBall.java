package ppPackage;
import static ppPackage.ppSimParams.*;
import static ppPackage.ppPaddle.*;

import java.awt.Color;

import acm.graphics.*;


public class ppBall extends Thread {



	//Instance variables 
		private double Xinit;		// Initial position of ball - X
		private double Yinit;		// Initial position of ball - Y 
		private double Vo; 			// Initial velocity (Magnitude)
		private double theta;
		private double loss; 
		@SuppressWarnings("unused")
		private Color color; 
		private ppTable table;
		GOval myBall;
		private ppPaddle myPaddle;
		
		/**
		* The constructor for the ppBall class copies parameters to instance variables, creates an
		* instance of a GOval to represent the ping-pong ball, and adds it to the display. *
		* @param Xinit - starting position of the ball X (meters)
		* @param Yinit - starting position of the ball Y (meters)
		* @param Vo - initial velocity (meters/second)
		* @param theta - initial angle to the horizontal (degrees)
		* @param color - ball color (Color)
		* @param loss - loss on collision ([0,1])
		* @param myTable 
		* @param table - a reference to the ppTable class used to manage the display */
		
		public ppBall (double Xinit, double Yinit, double Vo, double  theta, Color color, double loss, ppTable myTable, ppPaddle myPaddle) {
			
			this.Xinit = Xinit;
			this.Yinit = Yinit;
			this.Vo = Vo;
			this.loss = loss;
			this.table = myTable;
			this.myPaddle =myPaddle;
			this.theta = theta;
			this.color = color;
			
			myBall = new GOval (this.Xinit*SCALE, this.Yinit*SCALE, 2*bSize*SCALE, 2*bSize*SCALE);
			myBall.setFilled(true);
			myBall.setColor(color);
			this.table.getDisplay().add(myBall);
			
			}
			

		public void run () {
			
		
			//Initialize simulation parameters
			boolean hasEnergy;
			
			double time = 0;							//time
			double Vox = Vo*Math.cos(theta*Pi/180);		//initial velocity
			double Voy = Vo*Math.sin(theta*Pi/180);
			double Vt = bMass*g/(4*Pi*bSize*bSize*k);	//Terminal velocity
			double KEx = ETHR;							//Kinetic energy in x direction
			double KEy = ETHR;
			
			double Xo, X, Vx;							// X position and velocity variable
			double Yo, Y, Vy;
			double PE=0;

			
			Xo = Xinit;									//Initial X offset
			Yo = Yinit;
	
			hasEnergy = true;
			
			//Main simulation loop
			
			while (hasEnergy) {
				X = Vox*Vt/g*(1-Math.exp(-g*time/Vt));	//Update parameters
				Y = Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time;
				Vx = Vox*Math.exp(-g*time/Vt);
				Vy = (Voy+Vt)*Math.exp(-g*time/Vt)-Vt;
				
				
			//Collisions with the floor
				
				if ((Vy<0) && (Yo+Y <= bSize)) {
					KEx = (double)0.5*bMass*Vx*Vx*(1-loss);
					KEy = (double)0.5*bMass*Vy*Vy*(1-loss);
					PE=0;
					
					Vox = Math.sqrt(2*KEx/bMass);
					Voy = Math.sqrt(2*KEy/bMass);
					
					if (Vx < 0) {
						Vox = -Vox;
					}
					
					time = 0;								// Time is reset after every collision
					Xo += X;								// need to accumulate distance between collisions
					Yo = bSize;								// the absolute position of the ball on the ground
					X = 0;									// (X,Y) is the instantaneous position along an arc
					Y = 0;									// absolute position is (X0 + X, Y0 + Y)
				}
				
	
				
			//Collision with left wall
				
				if ((Vx < 0) && (Xo+X <= (XLWALL + bSize))) {
					KEx = (double)0.5*bMass*Vx*Vx*(1-loss);
					KEy = (double)0.5*bMass*Vy*Vy*(1-loss);
					PE = (double)bMass*g*(Y+Yo);
					
					Vox = Math.sqrt(2*KEx/bMass); 	
					Voy = Math.sqrt(2*KEy/bMass);
					
					if (Vy < 0) {
						Voy = -Voy;
					}
					
					time = 0;
					Xo = XLWALL + bSize;
					Yo += Y;
					X = 0;
					Y = 0;	

				}
				
			//Collision with the Paddle
				
				if (Vx > 0 && (Xo+X > myPaddle.getX()-bSize-ppPaddleW/2)) {
					if (myPaddle.contact(Xo+X, Yo+Y)) {
						
					
					KEx=(double) 0.5*bMass*Vx*Vx*(1-loss);
					KEy=(double) 0.5*bMass*Vy*Vy*(1-loss);
					PE=(double) bMass*g*Y;
					
					Vox=-Math.sqrt(2*KEx/bMass)*ppPaddleXgain;
					Voy=Voy*ppPaddleYgain*myPaddle.getVy();
					
					if(Vy<0) {
						Voy = -Voy;
					}
					
					time=0;   // reset time 
					Xo= myPaddle.getX()-bSize -ppPaddleW/2;  // position on the right wall
					Yo+=Y;  // accumulate distance
					X=0; 
					Y=0;
					
					}
					else {
						hasEnergy = false;
					}
				
				}	
				
				

		
				myBall.setLocation (table.toScrX(Xo+X-bSize), table.toScrY(Yo+Y+bSize));
				
				GOval tracer = new GOval (table.toScrX(Xo+X-bSize)+bSize*SCALE, table.toScrY(Yo+Y+bSize)+bSize*SCALE, PD,PD);
				tracer.setFilled(true);
				tracer.setFillColor(Color.gray);
				this.table.getDisplay().add(tracer);
				
				if ((KEx + KEy + PE) < ETHR) {
					break;
				}
				
				if (true) {
					System.out.printf(" t: %.2f X: %.2f Y:%.2f Vx:%.2f Vy:%.2f\n", time, Xo+X, Y, Vx, Vy);	
				}
				
				time += TICK;
				table.getDisplay().pause (TICK*5000);
			
		}
}
	
		public GObject getBall () {
			return myBall;
		}
			
		}
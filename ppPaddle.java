package ppPackage;

import static ppPackage.ppSimParams.*;
import acm.graphics.*;
import java.awt.*;

public class ppPaddle extends Thread{

	private double X , Y;
	private double lastX = X;
	private double lastY = Y;
	private double Vx , Vy;
	private ppTable myTable;
	GRect myPaddle;
	
public ppPaddle(double X, double Y, ppTable myTable) {
	this.X = X ;
	this.Y = Y;
	this.myTable = myTable;
	
	myPaddle = new GRect((X-ppPaddleW/2)*SCALE,scrHEIGHT -(Y-ppPaddleH/2),ppPaddleW*SCALE,ppPaddleH*SCALE);
	myPaddle.setColor(Color.BLACK);
	myPaddle.setFilled(true);
	myTable.getDisplay().add(myPaddle);
}
	
	
	public void run() {
		while (true) {
		Vx=(X-lastX)/TICK;
		Vy=(Y-lastY)/TICK;
		lastX=X;
		lastY=Y;
		myTable.getDisplay().pause(TICK*TIMESCALE); // Time to mS
		}
	
	}
	public double getVx(){			//Returns the velocity of the paddle in the X direction (which will be 0 here as X is fixed).
		return Vx;
	}
	
	public double getVy() {			//Returns the velocity of the paddle in the Y direction
		return Vy;
	} 
	public void setX(double X){	 // Sets the X position of the paddle.
		this.X = X;
		myPaddle.setLocation(myTable.toScrX(X),myTable.toScrY(Y));
		
	}
	public void setY(double Y) {	 //Sets the Y position of the paddle.
		this.Y = Y;
		myPaddle.setLocation(myTable.toScrX(X),myTable.toScrY(Y));
		
	}
	public double getX() {			//Returns the X position of the paddle.
		return X;
	} 
	public double getY() {			 //Returns the Y position of the paddle.
		return Y;
	}
	public double getSgnVy() { 		//Returns the sign of the Y velocity of the paddle.
		if (Vy < 0) {
			return -1;
		} else {
			return  1;
		}
	}
	public boolean contact (double Sx, double Sy) {		//Returns true if //a surface at position (Sx,Sy) is deemed to be in contact with the paddle
	if ((Sy >= Y - (ppPaddleH/2)) && (Sy<= Y +( ppPaddleH/2))) {
		return true;
		
	} else {
		return false ;
	
	}
}
}
	
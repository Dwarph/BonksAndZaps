package uk.ac.aber.dcs.pit.gridWorld;

/**
 * Stores two integers, denoting something's position
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
import java.util.ArrayList;

public class Position {
	private int xCoord, yCoord;
	
	public Position(int xCoord, int yCoord){
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	
	/**
	 * @return the xCoord
	 */
	public int getXCoord() {
		return xCoord;
	}

	/**
	 * @param xCoord the xCoord to set
	 */
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @return the yCoord
	 */
	public int getYCoord() {
		return yCoord;
	}

	/**
	 * @param yCoord the yCoord to set
	 */
	public void setYCoord(int yCoord) {
		this.yCoord = yCoord;
	}
	
	
}

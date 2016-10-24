package uk.ac.aber.dcs.pit.gridWorld;

/**
 * Holds the necessary information for a room
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
import java.util.ArrayList;

import uk.ac.aber.dcs.pit.beings.Bonk;
import uk.ac.aber.dcs.pit.beings.SuperBeing;
import uk.ac.aber.dcs.pit.beings.Zap;

public class Room {

	private ArrayList<Bonk> bonks;
	private ArrayList<Zap> zaps;
	private Position position;
	private int numberOfPrintedBonks, noOfBonksInRoom, noOfZapsInRoom;

	/**
	 * Constructor to initialise variables
	 * 
	 * @param position
	 */
	public Room(Position position) {
		this();
		this.position = position;
		numberOfPrintedBonks = 0;
		noOfBonksInRoom = 0;
		noOfZapsInRoom = 0;
	}

	/**
	 * Initialise arraylists
	 */
	public Room() {
		bonks = new ArrayList<>();
		zaps = new ArrayList<>();
	}

	/**
	 * Returns the arraylist of bonks
	 * 
	 * @return the bonks
	 */
	public ArrayList<Bonk> getBonks() {
		return bonks;
	}

	/**
	 * Gives the room an arrayList of bonks
	 * 
	 * @param bonks
	 */
	public void setBonks(ArrayList<Bonk> bonks) {
		this.bonks = bonks;
	}

	/**
	 * Returns the arrayList of zaps
	 * 
	 * @return zaps
	 */
	public ArrayList<Zap> getZaps() {
		return zaps;
	}

	/**
	 * Gives the room an arraylist of zaps
	 * 
	 * @param zaps
	 */
	public void setZaps(ArrayList<Zap> zaps) {
		this.zaps = zaps;
	}

	/**
	 * Returns the position of the room
	 * 
	 * @return position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * Sets the position of the room
	 * 
	 * @param position
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * Adds a being to the room
	 * 
	 * @param bonk
	 */
	public void addBeing(SuperBeing zonk) {

		if (zonk instanceof Bonk) {
			Bonk bonk = (Bonk) zonk;
			bonk.setRoom(this);
			bonks.add(bonk);
		} else if (zonk instanceof Zap) {
			Zap zap = (Zap) zonk;
			zap.setRoom(this);
			zaps.add(zap);
		}

	}

	/**
	 * Removes a being from the room
	 * 
	 * @param zonk
	 */
	public void removeBeing(SuperBeing zonk) {

		if (zonk instanceof Bonk) {
			Bonk bonk = (Bonk) zonk;
			int size = bonks.size();
			int i = 0;
			do {
				if (bonks.get(i).equals(bonk)) {
					bonks.remove(i);
					size--;
				} else {
					i++;
				}
			} while (i < size);

		} else if (zonk instanceof Zap) {
			Zap zap = (Zap) zonk;
			for (int i = 0; i < zaps.size(); i++) {
				if (zaps.get(i).equals(zap)) {
					zaps.remove(i);
				}
			}
		}

	}
	
	/**
	 * hold variables that are useful for PrintGridWorld
	 * @param resetVariables
	 * @param decrementNoOfBonksInRoom
	 * @param decrementNoOfZapsInRoom
	 * @param incrementNoOfPrintedBonks
	 * @return
	 */
	public int[] printGridHelp(boolean resetVariables, boolean decrementNoOfBonksInRoom,
			boolean decrementNoOfZapsInRoom, boolean incrementNoOfPrintedBonks) {
		if (resetVariables) {
			numberOfPrintedBonks = 0;
			noOfBonksInRoom = bonks.size();
			noOfZapsInRoom = zaps.size();
		}
		if (decrementNoOfBonksInRoom) {
			noOfBonksInRoom--;
		}
		if (decrementNoOfZapsInRoom) {
			noOfZapsInRoom--;
		}
		if (incrementNoOfPrintedBonks) {
			numberOfPrintedBonks++;
		}

		int[] gridInfo = { numberOfPrintedBonks, noOfBonksInRoom, noOfZapsInRoom };
		return gridInfo;
	}
	
	/**
	 * toString method
	 * @return finalString
	 */
	public String toString() {
		String finalString = "";
		for (int i = 0; i < bonks.size(); i++) {
			if (bonks.get(i).isAlive()) {
				finalString += bonks.get(i).getName() + ", ";
			}
		}
		for (int i = 0; i < zaps.size(); i++) {
			finalString += zaps.get(i).getName() + ",";
		}

		return finalString;

	}
}

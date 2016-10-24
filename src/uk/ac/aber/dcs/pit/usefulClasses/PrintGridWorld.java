package uk.ac.aber.dcs.pit.usefulClasses;
import uk.ac.aber.dcs.pit.gridWorld.Room;

/**
 * Prints the GridWorld
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
public class PrintGridWorld {
	private int noOfColumns, noOfRows;
	private Room[][] arrayOfRooms;
	final int ROOM_HEIGHT = 4;
	
	/**
	 * Constructor. Initialises variables
	 * @param noOfColumns
	 * @param noOfRows
	 * @param arrayOfRooms
	 */
	public PrintGridWorld(int noOfColumns, int noOfRows, Room[][] arrayOfRooms) {
		this.noOfColumns = noOfColumns;
		this.noOfRows = noOfRows;
		this.arrayOfRooms = arrayOfRooms;

	}
	
	/**
	 * This prints the world in the right order
	 */
	public void printWorld() {
		for (int row = 0; row < noOfRows; row++) {
			printHorizontalLine();
			for (int height = 0; height < ROOM_HEIGHT; height++) {
				for (int col = 0; col < noOfColumns; col++) {
					printVerticalLine(row, col, height);
					
				}
				System.out.print("|");
				System.out.println();
			}
		}
		printHorizontalLine();
	}
	
	/**
	 * Prints a horizontal line
	 */
	public void printHorizontalLine() {
		for (int i = 0; i < noOfColumns; i++) {
			System.out.print("---------");
		}
		System.out.println("-");
	}
	
	/**
	 * Prints one vertical line
	 * @param row
	 * @param col
	 * @param height
	 */
	public void printVerticalLine(int row, int col, int height) {
		int noOfBonksInRoom, noOfZapsInRoom, numberOfPrintedBonks = 0;
		int[] gridInfo;

		// arrayOfRooms[row][col].printGridHelp(boolean, boolean, boolean, boolean);
		// boolean 0: Reset Variables
		// boolean 1: Decrement noOfBonksInRoom
		// boolean 2: Decrement noOfZapsInRoom
		// boolean 3: Increment noOfPrintedBonks

		switch (height) {

		// if we're at the first row in a cell
		case 0:
			gridInfo = arrayOfRooms[row][col].printGridHelp(true, false, false, false); 
			// reset the variables needed
			noOfZapsInRoom = gridInfo[2];
			noOfBonksInRoom = gridInfo[1];
			numberOfPrintedBonks = gridInfo[0];
			// if there is 1+ zap(s), print the first one
			if (noOfZapsInRoom != 0) { 
				System.out.print("|" + makeStringEightChars(arrayOfRooms[row][col].getZaps().get(0).getName()));
				arrayOfRooms[row][col].printGridHelp(true, false, false, false);
				// else print the first bonk
			} else if (noOfBonksInRoom > 0) { 
				printBonk(row, col, numberOfPrintedBonks);
			//	Otherwise, just print a blank row
			} else {
				System.out.print("|" + makeStringEightChars(" "));
			}
			break;
		case 1:
		case 2:
			//get the appropriate variables
			gridInfo = arrayOfRooms[row][col].printGridHelp(false, false, true, false);
			noOfZapsInRoom = gridInfo[2];
			noOfBonksInRoom = gridInfo[1];
			numberOfPrintedBonks = gridInfo[0];
			//if there are bonks in the room, then print the first one that hasn't been printed yet
			if (noOfBonksInRoom != 0) {
				printBonk(row, col, numberOfPrintedBonks);
			//otherwise, print a blank line
			} else {
				System.out.print("|" + makeStringEightChars(" "));
			}
			break;
		case 3:
			String extraBeings;
			gridInfo = arrayOfRooms[row][col].printGridHelp(false, false, true, false);
			noOfZapsInRoom = gridInfo[2];
			noOfBonksInRoom = gridInfo[1];
			numberOfPrintedBonks = gridInfo[0];
			int noOfBeingsInRoom = 0;
			//if there are more bonks in the room, get the amount
			if (noOfBonksInRoom > 0) {
				noOfBeingsInRoom += noOfBonksInRoom;
			}
			//if there are more zaps in the room, get the amount
			if (noOfZapsInRoom > 0) {
				noOfBeingsInRoom += noOfZapsInRoom;
			}
			//if the amount of beings in the room that haven't been printed is greater than 1
			//print them in the format +x, where x is the amount of beings
			//Otherwise, just print another blank row
			if (noOfBeingsInRoom < 1) {
				System.out.print("|" + makeStringEightChars(" "));
			} else {
				extraBeings = "+" + noOfBeingsInRoom;
				System.out.print("|" + makeStringEightChars((extraBeings)));
			}
			break;
		}

		
	}
	
	/**
	 * Prints a bonk
	 * @param row
	 * @param col
	 * @param numberOfPrintedBonks
	 */
	public void printBonk(int row, int col, int numberOfPrintedBonks) {

		String name = "";
		boolean foundName = false;
		int[] gridInfo;
		// whilst we haven't found an alive bonk name
		while (!foundName) {
			// get the variables needed
			gridInfo = arrayOfRooms[row][col].printGridHelp(false, false, false, false);
			numberOfPrintedBonks = gridInfo[0];
			// if we've looped through all bonks and there are none left alive,
			// print a blank line
			if (numberOfPrintedBonks == arrayOfRooms[row][col].getBonks().size()) {
				name = "";
				break;
			}
			// if the next bonk is alive
			if (arrayOfRooms[row][col].getBonks().get(numberOfPrintedBonks).isAlive()) {
				// get its name
				name = arrayOfRooms[row][col].getBonks().get(numberOfPrintedBonks).getName();
				foundName = true;
				// decrement the number of possible bonks left to print
				arrayOfRooms[row][col].printGridHelp(false, true, false, true);
			} else {
				// else
				arrayOfRooms[row][col].printGridHelp(false, true, false, true);
			}
		}
		
		System.out.print("|" + makeStringEightChars(name));
		
	}
	
	/**
	 * Pads a string with spaces if <8 chars long
	 * @param s
	 * @return an 8 char long string
	 */
	public String makeStringEightChars(String s) {
		int amountOfSpace = 0;
		if (s.length() > 8) {
			s = " " + s.substring(0, 6);
		} else {
			amountOfSpace = 7 - s.length();
			s = " " + s;
			for (int i = 0; i < amountOfSpace; i++) {
				s += " ";
			}
		}
		return s;
	}
}

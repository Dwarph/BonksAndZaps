package uk.ac.aber.dcs.pit.gridWorld;

/**
 * Stores all the information about the GridWorld
 * Contains useful methods relating to the GridWorld
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import uk.ac.aber.dcs.pit.beings.Bonk;
import uk.ac.aber.dcs.pit.beings.SuperBeing;
import uk.ac.aber.dcs.pit.beings.Zap;
import uk.ac.aber.dcs.pit.usefulClasses.GenerateZapBackStory;
import uk.ac.aber.dcs.pit.usefulClasses.PrintGridWorld;

public class GridWorld {

	private int noOfInitialBonks, noOfInitialZaps, noOfRows, noOfColumns, noOfDeadBonks, noOfCycles, noOfZapsAllTime;
	private Room[][] arrayOfRooms;
	private ArrayList<Zap> zaps;
	private ArrayList<Bonk> bonks;
	final private int SLEEP_TIME = 1000;
	final String ZAPS_FILE_NAME = "Zaps.txt";

	/**
	 * Constructor to initialise variables
	 * 
	 * @param noOfBonks
	 * @param noOfZaps
	 * @param noOfRows
	 * @param noOfColumns
	 * @param noOfCycles
	 */
	public GridWorld(int noOfBonks, int noOfZaps, int noOfRows, int noOfColumns, int noOfCycles) {
		this();
		arrayOfRooms = new Room[noOfRows][noOfColumns];
		this.noOfRows = noOfRows;
		this.noOfColumns = noOfColumns;
		this.noOfCycles = noOfCycles;
		noOfInitialBonks = noOfBonks;
		noOfInitialZaps = noOfZaps;
		noOfZapsAllTime = 0;
		noOfDeadBonks = 0;
		
		if (noOfRows != 0 && noOfColumns != 0) {
		createInitialWorld();
		}else{
			System.out.println("Having 0 rows or columns is impossible as the GridWorld is 2D.");
		}
	}

	/**
	 * Constructor that initialises arrayLists
	 */
	public GridWorld() {
		zaps = new ArrayList<>();
		bonks = new ArrayList<>();

	}

	/**
	 * Returns the number of bonks
	 * 
	 * @return Number Of Bonks
	 */
	public int getNumberOfBonks() {
		return bonks.size();
	}

	/**
	 * Returns the number of dead bonks
	 * 
	 * @return Number of Dead Bonks
	 */
	public int getNoOfDeadBonks() {
		return noOfDeadBonks;
	}

	/**
	 * Returns the number of rows
	 * 
	 * @return int noOfRows
	 */
	public int getNoOfRows() {
		return noOfRows;
	}

	/**
	 * Returns the number of columns
	 * 
	 * @return int noOfColumns
	 */
	public int getNoOfColumns() {
		return noOfColumns;
	}

	/**
	 * Returns the array of rooms
	 * 
	 * @return Room[][] arrayOfrooms
	 */
	public Room[][] getArrayOfRooms() {
		return arrayOfRooms;
	}

	/**
	 * Adds a passed number to the amount of dead bonks
	 * 
	 * @param amountOfBonkCorpses
	 */
	public void addToNoOfDeadBonks(int amountOfBonkCorpses) {
		noOfDeadBonks = noOfDeadBonks + amountOfBonkCorpses;
	}

	/**
	 * Gets a random integer in the range start to end-1
	 * 
	 * @param start
	 * @param end
	 * @return random int
	 */
	public int getRandomIntInRange(int start, int end) {
		return ThreadLocalRandom.current().nextInt(start, end);
	}

	/**
	 * Moves a being passed to it to a room next to it
	 * 
	 * @param being
	 */
	public void moveBeing(SuperBeing zonk) {
		int randRow, randCol;
		// The first half of this gets the appropriate room to move it to
		// If the GridWorld is not 1x1, we need this
		if (noOfRows != 1 && noOfColumns != 1) {
			// get the being's location
			Position pos = zonk.getLocation();

			// if we're at row 0, we can only go down
			if (pos.getXCoord() == 0) {
				randRow = getRandomIntInRange(0, 2);
				// if we're at the bottom of the grid, we can only go up
			} else if (pos.getXCoord() == noOfRows - 1) {
				randRow = getRandomIntInRange(noOfRows - 2, noOfRows);
				// Otherwise, we can go up and down
			} else {
				randRow = getRandomIntInRange(zonk.getLocation().getXCoord() - 1, zonk.getLocation().getXCoord() + 2);
			}

			// if we're at column 0, we can only go right
			if (pos.getYCoord() == 0) {
				randCol = getRandomIntInRange(0, 2);
				// if we're at the right hand side of the grid, we can only go
				// left
			} else if (pos.getYCoord() == noOfColumns - 1) {
				randCol = getRandomIntInRange(noOfColumns - 2, noOfColumns);
				// otherwise, we can go left and right
			} else {
				randCol = getRandomIntInRange(zonk.getLocation().getYCoord() - 1, zonk.getLocation().getYCoord() + 2);
			}

			// if the being is a bonk, move the bonk
			if (zonk instanceof Bonk) {
				Bonk bonk = (Bonk) zonk;
				arrayOfRooms[bonk.getLocation().getXCoord()][bonk.getLocation().getYCoord()].removeBeing(bonk);
				arrayOfRooms[randRow][randCol].addBeing(bonk);
				// if the being is a zap, move the zap
			} else if (zonk instanceof Zap) {
				Zap zap = (Zap) zonk;
				arrayOfRooms[zap.getLocation().getXCoord()][zap.getLocation().getYCoord()].removeBeing(zap);
				arrayOfRooms[randRow][randCol].addBeing(zap);
			}
		}
	}

	/**
	 * Adds being to gridWorld
	 * 
	 * @param zonk
	 */
	public void addBeing(SuperBeing zonk) {
		if (zonk instanceof Bonk) {
			Bonk b = (Bonk) zonk;
			bonks.add(b);
		} else {
			Zap z = (Zap) zonk;
			zaps.add(z);
		}
	}

	/**
	 * Removes being from gridWorld
	 * 
	 * @param zonk
	 */
	public void removeBeing(SuperBeing zonk) {
		if (zonk instanceof Bonk) {
			Bonk b = (Bonk) zonk;
			bonks.remove(bonks.indexOf(b));

		} else {
			Zap z = (Zap) zonk;
			zaps.remove(zaps.indexOf(z));
		}
	}

	/**
	 * Removes a zap from the gridWorld
	 * 
	 * @param zapName
	 * @return boolean
	 */
	public boolean fireZap(String zapName) {
		GenerateZapBackStory zapBackStory;
		for (Zap z : zaps) {
			// if the zap exists, print out its backstory, remove it, then save.
			if (z.getName().equals(zapName)) {
				zapBackStory = new GenerateZapBackStory(zapName, this);
				zapBackStory.startStory();
				removeBeing(z);
				saveZaps();
				return true;
			}
		}
		// if it doesn't exist, return false
		return false;

	}

	/**
	 * Saves the zaps
	 */
	private void saveZaps() {
		try (PrintWriter save = new PrintWriter(new FileWriter(ZAPS_FILE_NAME))) {
			// Save in the format in number of zaps of all time, followed by
			// ZAPNAME:AMOUNTOFBONKSKILLED
			save.println(noOfZapsAllTime);
			for (Zap zap : zaps) {
				save.println(zap.getName() + ":" + zap.getNoOfBonksKilled());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Loads the zaps into the gridWorld
	 * 
	 * @throws FileNotFoundException
	 */
	private void loadZaps() throws FileNotFoundException {
		Zap z;
		int tempRow, tempCol, counter = 0, noOfActiveZaps = 0;
		try (Scanner load = new Scanner(new FileReader(ZAPS_FILE_NAME))) {
			load.useDelimiter(":|\r?\n|\r");
			noOfZapsAllTime = load.nextInt();
			while (load.hasNext()) {
				// loads the zaps into the grid world, randomly placing them
				tempRow = getRandomIntInRange(0, noOfRows);
				tempCol = getRandomIntInRange(0, noOfColumns);
				z = new Zap(load.next(), this, Integer.parseInt(load.next()));
				zaps.add(z);
				if (counter < noOfInitialZaps) {
					arrayOfRooms[tempRow][tempCol].addBeing(z);
					counter++;
				}
			}
			load.close();
		} catch (InputMismatchException ime) {
			System.out.println("Tried to load non-int");
		} catch (NoSuchElementException nsee) {
			System.out.println(
					"No Zaps file found, or empty file (NoSuchElementException).\n One shall be made and/or populated at the end of this GridWorld.");
		}

		for (Zap zap : zaps) {
			if (zap.getRoom() != null) {
				noOfActiveZaps++;
			}
		}

		// if we still need more zaps, make them
		if (noOfActiveZaps < noOfInitialZaps) {
			for (int i = 0; i < noOfInitialZaps - noOfActiveZaps; i++) {
				tempRow = getRandomIntInRange(0, noOfRows);
				tempCol = getRandomIntInRange(0, noOfColumns);
				noOfZapsAllTime++;
				z = new Zap("Z" + noOfZapsAllTime, this, 0);
				zaps.add(z);
				arrayOfRooms[tempRow][tempCol].addBeing(z);
			}
		}

	}

	/**
	 * Sleep for a number of milliSeconds
	 * 
	 * @param milliseconds
	 * @throws InterruptedException
	 */
	private void sleep(int milliseconds) throws InterruptedException {
		Thread.sleep(milliseconds);
	}

	/**
	 * performs a cycle of gridWorld
	 */
	private void cycle() {

		int sizeOfBeings = 0;
		for (Zap z : zaps) {
			if (z.getRoom() != null) {
				sizeOfBeings++;
			}
		}
		// make every zap act
		for (int i = 0; i < sizeOfBeings; i++) {
			zaps.get(i).act();
		}

		// make every bonk act
		sizeOfBeings = bonks.size();
		for (int i = 0; i < sizeOfBeings; i++) {
			bonks.get(i).act();
		}

	}

	/**
	 * Creates the initial world Populates it with Zaps and Bonks
	 */
	private void createInitialWorld() {
		int tempRow, tempCol;
		Bonk b;

		for (int i = 0; i < noOfRows; i++) {
			for (int j = 0; j < noOfColumns; j++) {
				arrayOfRooms[i][j] = new Room(new Position(i, j));
			}
		}

		for (int i = 0; i < noOfInitialBonks; i++) {
			tempRow = getRandomIntInRange(0, noOfRows);
			tempCol = getRandomIntInRange(0, noOfColumns);
			b = new Bonk("B" + Integer.toHexString(i) + "A", 0, this);
			bonks.add(b);
			arrayOfRooms[tempRow][tempCol].addBeing(b);
		}

		try {
			loadZaps();
		} catch (FileNotFoundException e) {
			// if this is the first time, just make the zaps
			Zap z;
			for (int i = 0; i < noOfInitialZaps; i++) {
				tempRow = getRandomIntInRange(0, noOfRows);
				tempCol = getRandomIntInRange(0, noOfColumns);
				z = new Zap("Z" + i, this, 0);
				zaps.add(z);
				noOfZapsAllTime++;
				arrayOfRooms[tempRow][tempCol].addBeing(z);
			}
		}

	}

	/**
	 * Plays the game
	 * 
	 * @throws InterruptedException
	 */
	public void playGame() throws InterruptedException {
		if (noOfRows != 0 && noOfColumns != 0) {
			this.printWorld(0);
			sleep(1000);
			int j = 0;
			while (j < noOfCycles && noOfDeadBonks < bonks.size()) {
				this.cycle();
				this.printWorld(j + 1);
				sleep(SLEEP_TIME);
				j++;
			}
			printStats();
			saveZaps();
		}
	}

	/**
	 * Prints stats about the bonks and zaps
	 */
	private void printStats() {
		System.out.println("Number of Initial Bonks: " + noOfInitialBonks);
		System.out.println("Number of Bonks Left Alive: " + (bonks.size() - noOfDeadBonks));
		System.out.println("Number of Dead Bonks: " + noOfDeadBonks + "\n");
		for (Zap zap : zaps) {
			if (zap.getRoom() != null) {
				if (zap.getPercentageOfBonksKilled() == 101) {
					System.out.println("Zap: " + zap.getName() + " did not meet any bonks.");
				} else {

					System.out.print("Zap: " + zap.getName() + " killed");
					System.out.printf(" %.2f", zap.getPercentageOfBonksKilled());
					System.out.println("% of Bonks that it encountered" + " (" + zap.getNoOfBonksKilledThisGame() + "/"
							+ zap.getNoOfBonksMetThisGame() + ")");
				}
			}
		}
	}

	/**
	 * Prints the world
	 * 
	 * @param cycleNo
	 */
	private void printWorld(int cycleNo) {

		System.out.println("Cycle No. " + cycleNo);
		PrintGridWorld displayGrid = new PrintGridWorld(noOfColumns, noOfRows, arrayOfRooms);
		displayGrid.printWorld();
		System.out.println("");

	}

}

package uk.ac.aber.dcs.pit.gridWorld;

import java.util.InputMismatchException;
/**
 * Main class to control the GridWorld
 * 
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
import java.util.Scanner;

public class GameDriver {
	private Scanner in;

	/**
	 * Constructor. Initialises the scanner
	 */
	public GameDriver() {
		in = new Scanner(System.in);
	}

	/**
	 * Runs the GridWorld. This method is necessary if I want to add more
	 * methods when running
	 * 
	 * @throws InterruptedException
	 */
	public void run() throws InterruptedException {
		runMenu();

	}

	/**
	 * Runs the menu and acts upon the user's input
	 * 
	 * @throws InterruptedException
	 */
	private void runMenu() throws InterruptedException {
		String userInput = "";

		while (!(userInput.equals("Q"))) { // whilst the user does not quit
			printMenu(); // print the menu and get the user's choice
			userInput = getUserInput();
			userInput = userInput.toUpperCase();

			switch (userInput) {
			case "1": // if the user just wants a standard game, do this
				GridWorld gridWorld = new GridWorld(20, 5, 20, 20, 20);
				gridWorld.playGame();

				fireZaps(gridWorld);

				break;
			case "2": // if the user wants a custom game, do this
				playCustomGame();
				break;
			case "Q": // if the user wants to quit, quit
				break;
			default: // otherwise display error message
				System.out.println("Invalid Input: " + userInput);
				break;
			}
		}
	}

	/**
	 * Gets user attributes for a custom game and then runs the game
	 * 
	 * @throws InterruptedException
	 */
	private void playCustomGame() throws InterruptedException {
		GridWorld gridWorld;
		// int noOfBonks, noOfZaps, noOfRows, noOfCols, noOfCycles;
		System.out.println("Please input the Grid World attributes seperated by spaces in the order: "
				+ "\n Number Of Bonks, Number Of Zaps, Number of rows, Number Of Columns, Number of Cycles");
		// start a new gridWorld with user's attributes
		try{
		gridWorld = new GridWorld(in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt());
		in.nextLine();// consume the \n char so that we can successfully get
						// another user input
		gridWorld.playGame();
		if (gridWorld.getNoOfColumns() != 0 && gridWorld.getNoOfRows() != 0) {
			fireZaps(gridWorld);
		}
		}catch (InputMismatchException ime){
			
			System.out.println("Invalid Input");
			in.nextLine();
		}
		

	}

	/**
	 * Prints the menu
	 */
	private void printMenu() {
		System.out
				.println("--------MENU--------" + "\n 1. Play Standard Game" + "\n 2. Play Custom Game" + "\n q. Quit");
	}

	/**
	 * Gets the name of the Zap the user wants to fire and gives it to the
	 * fireZap method in GridWorld
	 * 
	 * @param gridworld
	 */
	private void fireZaps(GridWorld gridworld) {
		// ask the user if they want to fire any zaps
		System.out.println("Would you like to fire any of your zaps? (Y/N)");
		String userInput = getUserInput().toUpperCase();
		switch (userInput) {
		case "Y":
			System.out.println("Which zap would you like to fire? (Please enter the zap's name)");
			// if they do, get the zap's name and then fire it
			if (gridworld.fireZap(getUserInput().toUpperCase()) != true) {
				System.out.println("That Zap does not exist.");
			}
		case "N":
			break;
		default:
			System.out.println("Invalid input. Please enter Y or N");
			break;
		}

	}

	/**
	 * Gets a user's input and returns it as a string
	 * 
	 * @return String userInput
	 */
	public String getUserInput() {
		String userInput;
		try {
			userInput = in.nextLine();
		} catch (IllegalArgumentException iae) {
			System.out.println("Illegal argument entered");
			return "";
		}
		return userInput;
	}

	/**
	 * Is called when the program is run
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		GameDriver gameDriver = new GameDriver();
		gameDriver.run();
	}

}

package uk.ac.aber.dcs.pit.beings;
import uk.ac.aber.dcs.pit.gridWorld.GridWorld;

/**
 * Holds all information about a Zap
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
public class Zap extends SuperBeing {

	private final int MAX_CHANCE = 10;

	private int noOfBonksKilled;
	private int mentalState; // can be between 0 and 10, where 0 is completely
								// sane and 10 is insane
	private int noOfBonksMetThisGame;
	private int noOfBonksKilledThisGame;
	
	/**
	 * Constructor. Initialises variables
	 * @param name
	 * @param gridWorld
	 * @param noOfBonksKilled
	 */
	public Zap(String name, GridWorld gridWorld, int noOfBonksKilled) {
		super(name, gridWorld);
		this.noOfBonksKilled = noOfBonksKilled;
		noOfBonksMetThisGame = 0;
		noOfBonksKilledThisGame = 0;
		this.mentalState = 0;
	}
	
	/**
	 * Returns the number of bonks met this game
	 * @return noOfBonksMetThisGame
	 */
	public int getNoOfBonksMetThisGame(){
		return noOfBonksMetThisGame;
	}
	
	/**
	 * Returns number of bonks killed this game
	 * @return noOfBonksKilledThisGame
	 */
	public int getNoOfBonksKilledThisGame(){
		return noOfBonksKilledThisGame;
	}
	
	/**
	 * returns number of bonks killed all time
	 * @return noOfBonksKilled
	 */
	public int getNoOfBonksKilled(){
		return noOfBonksKilled;
	}
	
	/**
	 * Works out and returns the percentage of bonks killed this game
	 * @return percentage of bonks killed this game
	 */
	public double getPercentageOfBonksKilled() {
		if (noOfBonksMetThisGame == 0) {
			return 101;
		} else {
			double deadBonks, bonksMet;
			deadBonks = (double) noOfBonksKilledThisGame;
			bonksMet = (double) noOfBonksMetThisGame;
			return (deadBonks / bonksMet) * 100;
		}
	}

	/**
	 * Measure's the zap's mental health
	 */
	private void measureMentalHealth() {

		if (noOfBonksKilled < 20) {
			mentalState = 0;
		} else if (isBetween(20, noOfBonksKilled, 39)) {
			mentalState = 1;

		} else if (isBetween(40, noOfBonksKilled, 59)) {
			mentalState = 2;

		} else if (isBetween(60, noOfBonksKilled, 79)) {
			mentalState = 3;

		} else if (isBetween(80, noOfBonksKilled, 99)) {
			mentalState = 4;

		} else if (isBetween(100, noOfBonksKilled, 149)) {
			mentalState = 5;

		} else if (isBetween(150, noOfBonksKilled, 199)) {
			mentalState = 6;

		} else if (isBetween(200, noOfBonksKilled, 299)) {
			mentalState = 7;

		} else if (isBetween(300, noOfBonksKilled, 499)) {
			mentalState = 8;

		} else if (isBetween(500, noOfBonksKilled, 999)) {
			mentalState = 9;

		} else if (noOfBonksKilled > 999) {
			mentalState = 10;

		}

	}
	
	/**
	 * Kills a bonk. Is affected by mental health.
	 * The higher the mental health, the less the Zap wants to kill
	 */
	private void killBonks() {
		int chance;
	
		for (Bonk bonk: this.getRoom().getBonks()) {
			if (bonk.isAlive()) {
				noOfBonksMetThisGame++;
				chance = this.getGridWorld().getRandomIntInRange(0, 11);
				measureMentalHealth();
				if (mentalState <= chance && chance <= MAX_CHANCE) {
					bonk.setAlive(false);
					this.getGridWorld().addToNoOfDeadBonks(1);
					noOfBonksKilled++;
					noOfBonksKilledThisGame++;
	
				}
			}
		}
	}
	
	/**
	 * Checks to see if a number is between two others
	 * @param lower
	 * @param middle
	 * @param upper
	 * @return
	 */
	private boolean isBetween(int lower, int middle, int upper) {
		return lower <= middle && middle <= upper;
	}
	
	/**
	 * Makes the Zap kill then move
	 */
	@Override
	public void act() {
		if (!(this.getRoom().getBonks().isEmpty())) {
			killBonks();
		}
		this.move();
	}
}

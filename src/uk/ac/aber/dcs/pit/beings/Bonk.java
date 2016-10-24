package uk.ac.aber.dcs.pit.beings;

/**
 * Holds the information for a Bonk
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
import java.util.concurrent.ThreadLocalRandom;

import uk.ac.aber.dcs.pit.gridWorld.GridWorld;
import uk.ac.aber.dcs.pit.gridWorld.Room;

public class Bonk extends SuperBeing {

	private boolean alive, female, adult, reproduced;
	private int noOfCycles;
	
	/**
	 * Constructor. Initialises values
	 * @param name
	 * @param noOfCycles
	 * @param gridWorld
	 */
	public Bonk(String name, int noOfCycles, GridWorld gridWorld) {
		super(name, gridWorld);
		alive = true;
		female = generateGender();
		adult = false;
		reproduced = false;
		this.noOfCycles = noOfCycles;
		if (noOfCycles > 0) {
			adult = true;
		}
	}

	/**
	 * Gets whether the Bonk is alive or not
	 * 
	 * @return alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Sets whether the Bonk is alive or not
	 * 
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
		if (alive = false) {
			// replaces a with D
			this.setName(this.getName().substring(0, this.getName().length() - 1) + "D"); 
		}
	}

	/**
	 * Gets the gender of the Bonk. In this case True is female and False is
	 * male
	 * 
	 * @return female
	 */
	public boolean isFemale() {
		return female;
	}
	
	/**
	 * Returns adult
	 * @return Boolean adult
	 */
	public boolean isAdult() {
		return adult;
	}
	
	/**
	 * Sets whether the Bonk has reproduced or not
	 * @param reproduced
	 */
	public void setReproduced(boolean reproduced) {
		this.reproduced = reproduced;
	}
	
	/**
	 * Returns reproduced
	 * @return Boolean reproduced
	 */
	public boolean hasReproduced() {
		return reproduced;
	}


	/**
	 * Sets the number of cycles this Bonk has been alive for If it is 1 cycle
	 * or greater, the Bonk becomes an adult This requires updating the adult
	 * boolean
	 * 
	 * @param noOfCycles
	 */
	public void incrementNoOfCycles(int increment) {
		this.noOfCycles = noOfCycles + increment;
		if (this.noOfCycles > 0) {
			// whilst we could bypass the adult boolean by checking if
			// noOfCycles>0,
			// it feels better and more readable to have the boolean in there.
			adult = true;
		}
	}

	/**
	 * Gets the amount of cycles this Bonk has been alive for
	 * 
	 * @return noOfCycles
	 */
	public int getNoOfCycles() {
		return noOfCycles;
	}
	
	/**
	 * Lets the bonk reproduce with another bonk, once per cycle
	 */
	private void bonkReproduction() {
		Room room = this.getRoom();

		for (Bonk bonk: room.getBonks()) {
			/* If another bonk is
			 * of the opposite gender
			 * Has not had sex that cycle
			 * Is an adult
			 * Is Alive
			 * Then, have reproduce with it
			 */
			if (bonk.isFemale() != this.isFemale() && bonk.reproduced != true
					&& bonk.isAlive() != false && bonk.isAdult()) {
				Bonk baby = new Bonk(generateChildName(), 0, this.getGridWorld());
				room.addBeing(baby);
				this.getGridWorld().addBeing(baby);
				bonk.setReproduced(true);
				reproduced = true;
				return;
			}
		}
	}

	/**
	 * Randomly chooses assigns either a male or female gender to the Bonk
	 * 
	 * @return true || false
	 */
	private boolean generateGender() {
		if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
			return true;
		} else {
			return false;
		}
	
	}
	
	/**
	 * Creates a childname by getting the total amount of bonks,
	 * adding one and then converting to hex
	 * @return Baby Bonk Name
	 */
	private String generateChildName() {
		return "B" + Integer.toHexString(this.getGridWorld().getNumberOfBonks()) + "A";
	}
	
	/**
	 * Tells the the bonk the order of which to act
	 */
	@Override
	public void act() {
		if (alive) {
	
			if (reproduced) {
				reproduced = !reproduced;
			}
			if (isAdult()) {
				bonkReproduction();
			}
			incrementNoOfCycles(1);
			this.move();
		}
	
	}

}

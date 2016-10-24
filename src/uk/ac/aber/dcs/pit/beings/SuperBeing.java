package uk.ac.aber.dcs.pit.beings;
import uk.ac.aber.dcs.pit.gridWorld.GridWorld;
import uk.ac.aber.dcs.pit.gridWorld.Position;
import uk.ac.aber.dcs.pit.gridWorld.Room;
import uk.ac.aber.dcs.pit.usefulClasses.CannotActException;

/**
 * Holds info for generic being (of which Bonk and Zap inherit)
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */

public class SuperBeing implements Being {
	private String name;
	private Room room;
	private GridWorld gridWorld;
	
	/**
	 * Constructor. Initialises variables
	 * @param name
	 * @param gridWorld
	 */
	public SuperBeing(String name, GridWorld gridWorld){
		this.name = name;
		this.gridWorld = gridWorld;
	}
	
	/**
	 * Returns the name of the being
	 * @return name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the being
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the position of the Being
	 * @return position
	 */
	@Override
	public Position getLocation() {
		return room.getPosition();
	}
	
	/**
	 * Simply here to adhere to the interface
	 * @param location
	 */
	@Override
	public void setLocation(Position location) {
	//	room.setPosition(location);
	}
	
	/**
	 * Returns the room
	 * @return room
	 */
	public Room getRoom(){
		return room;
	}
	
	/**
	 * Sets the room
	 * @param room
	 */
	public void setRoom(Room room){
		this.room = room;
	}
	
	/**
	 * Returns the grid world
	 * @return gridWorld
	 */
	public GridWorld getGridWorld(){
		return gridWorld;
	}
	
	/**
	 * Moves the being
	 */
	public void move(){
		gridWorld.moveBeing(this);	
	}
	
	/**
	 * Just here to adhere to the interface
	 */
	@Override
	public void act() throws CannotActException {
		
	}

}

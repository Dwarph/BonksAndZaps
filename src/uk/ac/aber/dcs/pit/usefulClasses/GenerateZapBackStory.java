package uk.ac.aber.dcs.pit.usefulClasses;
/**
 * Generates a randomised backstory for a fired Zap
 * In its own class in order to keep code neat
 * @author Pip Turner
 * @version 1.0 (5th May, 2016)
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import uk.ac.aber.dcs.pit.gridWorld.GridWorld;

public class GenerateZapBackStory {
	private String zapName;
	private GridWorld gridWorld;
	private final String HAPPY_STORY_FILENAME = "happyStory.txt", SAD_STORY_FILENAME = "sadStory.txt",
			ANGRY_STORY_FILENAME = "angryStory.txt";
	private ArrayList<String> happyEmotions, sadEmotions, angryEmotions, happyPart1, happyPart2, happyPart3, sadPart1,
			sadPart2, sadPart3, angryPart1, angryPart2, angryPart3;
	
	/**
	 * Constructor, initialises variables
	 * @param zapName
	 * @param gridWorld
	 */
	public GenerateZapBackStory(String zapName, GridWorld gridWorld) {
		this();
		this.zapName = zapName;
		this.gridWorld = gridWorld;
	}
	
	/**
	 * Constructor to initialise all the arrayLists
	 */
	private GenerateZapBackStory() {
		happyEmotions = new ArrayList<>();
		sadEmotions = new ArrayList<>();
		angryEmotions = new ArrayList<>();
		happyPart1 = new ArrayList<>();
		happyPart2 = new ArrayList<>();
		happyPart3 = new ArrayList<>();
		sadPart1 = new ArrayList<>();
		sadPart2 = new ArrayList<>();
		sadPart3 = new ArrayList<>();
		angryPart1 = new ArrayList<>();
		angryPart2 = new ArrayList<>();
		angryPart3 = new ArrayList<>();
	}
	
	/**
	 * Populates the appropriate arraylists, using the appropriate files
	 */
	private void populateArrayLists() {

		try {
			int part = 0;
			String nextString;
			Scanner load = new Scanner(new FileReader(HAPPY_STORY_FILENAME));
			load.useDelimiter(":|\r?|\r");

			while (load.hasNext()) {
				switch (part) {
				case 0:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					if (nextString.equals("PART-01")) {
						part = 1;
					} else {
						happyEmotions.add(nextString);
					}
					break;
				case 1:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					if (nextString.equals("PART-02")) {
						part = 2;
					} else {
						happyPart1.add(nextString);
					}
					break;
				case 2:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					if (nextString.equals("PART-03")) {
						part = 3;
					} else {
						happyPart2.add(nextString);
					}
					break;
				case 3:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					happyPart3.add(nextString);
					break;
				}

			}
			
			part = 0;
			load = new Scanner(new FileReader(SAD_STORY_FILENAME));
			while (load.hasNext()) {
				switch (part) {
				case 0:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					if (nextString.equals("PART-01")) {
						part = 1;
					} else {
						sadEmotions.add(nextString);
					}
					break;
				case 1:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					if (nextString.equals("PART-02")) {
						part = 2;
					} else {
						sadPart1.add(nextString);
					}
					break;
				case 2:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					if (nextString.equals("PART-03")) {
						part = 3;
					} else {
					
						sadPart2.add(nextString);
					}
					break;
				case 3:
					nextString = load.nextLine();
					nextString = nextString.replaceAll("NEWLINE", "\n");
					sadPart3.add(nextString);
					break;
				}
			}
				part = 0;
				load = new Scanner(new FileReader(ANGRY_STORY_FILENAME));
				while (load.hasNext()) {
					switch (part) {
					case 0:
						nextString = load.nextLine();
						nextString = nextString.replaceAll("NEWLINE", "\n");
						if (nextString.equals("PART-01")) {
							part = 1;
						} else {
							angryEmotions.add(nextString);
						}
						break;
					case 1:
						nextString = load.nextLine();
						nextString = nextString.replaceAll("NEWLINE", "\n");
						if (nextString.equals("PART-02")) {
							part = 2;
						} else {
							angryPart1.add(nextString);
						}
						break;
					case 2:
						nextString = load.nextLine();
						nextString = nextString.replaceAll("NEWLINE", "\n");
						if (nextString.equals("PART-03")) {
							part = 3;
						} else {
							angryPart2.add(nextString);
						}
						break;
					case 3:
						nextString = load.nextLine();
						nextString = nextString.replaceAll("NEWLINE", "\n");
						angryPart3.add(nextString);
						break;
					}

				}
			

			load.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found. Have you deleted or moved it?");
		}
	}
	
	/**
	 * Starts the story and randomises whether it will be
	 * Happy
	 * Sad
	 * Angry
	 */
	public void startStory() {
		populateArrayLists();
		System.out.println(zapName);
		int rand;
		rand = gridWorld.getRandomIntInRange(1, 4);
		System.out.print(zapName + " was ");
		switch (rand) {
		case 1:
			printStory(1);
			break;
		case 2:
			printStory(2);
			break;
		case 3:
			printStory(3);
			break;
		}
	}
	
	/**
	 * Prints the story
	 * @param emotion
	 */
	private void printStory(int emotion) {
		System.out.println(getRandomEmotion(emotion) + ".\n" + getRandomPart1(emotion) + "\n" + getRandomPart2(emotion)
				+ "\n" + getRandomPart3(emotion));
	}
	
	/**
	 * Gets a random emotion, depending on the number given to it
	 * 1 = Happy
	 * 2 = Sad
	 * 3 = Angry
	 * @param emotion
	 * @return emotionString
	 */
	private String getRandomEmotion(int emotion) {

		switch (emotion) {
		case 1:
			return happyEmotions.get(gridWorld.getRandomIntInRange(0, happyEmotions.size() + 1));
		case 2:
			return sadEmotions.get(gridWorld.getRandomIntInRange(0, sadEmotions.size() + 1));
		case 3:
			return angryEmotions.get(gridWorld.getRandomIntInRange(0, angryEmotions.size() + 1));
		default:
			return "no emotion";

		}
	}
	
	/**
	 * Gets a random part1, depending on the number given to it
	 * 1 = Happy
	 * 2 = Sad
	 * 3 = Angry
	 * @param emotion
	 * @return String part1
	 */
	private String getRandomPart1(int emotion) {

		switch (emotion) {
		case 1:
			return happyPart1.get(gridWorld.getRandomIntInRange(0, happyPart1.size()));
		case 2:
			return sadPart1.get(gridWorld.getRandomIntInRange(0, sadPart1.size()));
		case 3:
			return angryPart1.get(gridWorld.getRandomIntInRange(0, angryPart1.size()));
		default:
			return "no emotion";

		}
	}

	/**
	 * Gets a random part2, depending on the number given to it
	 * 1 = Happy
	 * 2 = Sad
	 * 3 = Angry
	 * @param emotion
	 * @return String part2
	 */
	private String getRandomPart2(int emotion) {

		switch (emotion) {
		case 1:
			return happyPart2.get(gridWorld.getRandomIntInRange(0, happyPart2.size()));
		case 2:
			return sadPart2.get(gridWorld.getRandomIntInRange(0, sadPart2.size()));
		case 3:
			return angryPart2.get(gridWorld.getRandomIntInRange(0, angryPart2.size()));
		default:
			return "no emotion";

		}
	}

	/**
	 * Gets a random part3, depending on the number given to it
	 * 1 = Happy
	 * 2 = Sad
	 * 3 = Angry
	 * @param emotion
	 * @return String part3
	 */
	private String getRandomPart3(int emotion) {

		switch (emotion) {
		case 1:
			return happyPart3.get(gridWorld.getRandomIntInRange(0, happyPart3.size()));
		case 2:
			return sadPart3.get(gridWorld.getRandomIntInRange(0, sadPart3.size()));
		case 3:
			return angryPart3.get(gridWorld.getRandomIntInRange(0, angryPart3.size()));
		default:
			return "no emotion";

		}
	}

}

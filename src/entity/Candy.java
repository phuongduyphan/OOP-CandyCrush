package entity;

import java.util.Random;

/**
 * @getRandCandy()
 * Return a random generated Candy object.
 *
 */
public class Candy { //stores type, colour, image of candy
	static private int numberOfType = 0;
	static private int numberOfColour = 0;
	private int type, colour;
	//Img image?

	public Candy(int type, int colour) {
		this.type = type;
		this.colour = colour;
	}

	public static int getNumberOfType() {
		return numberOfType;
	}

	public static void setNumberOfType(int numberOfType) {
		Candy.numberOfType = numberOfType;
	}
	
	public static Candy getRandCandy() {
		if(Candy.getNumberOfColour() == 0 || Candy.getNumberOfType() == 0) {
			System.err.println("ERROR MESSAGE HERE");
			return null;
		}
		Random rand = new Random();
		Candy candy = new Candy(rand.nextInt(Candy.getNumberOfType()), rand.nextInt(Candy.getNumberOfColour()));
		return candy;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}

	public static int getNumberOfColour() {
		return numberOfColour;
	}

	public static void setNumberOfColour(int numberOfColour) {
		Candy.numberOfColour = numberOfColour;
	}
	
	
	
}

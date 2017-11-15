import java.util.Random;

/**
 * @getRandCandy()
 * Return a random generated Candy object.
 *
 */
public class Candy {
	static private int numberOfType = GameBoard.getNumberofcandytype();
	static private Random rand = new Random();
	private int type;

	public Candy(int type) {
		this.type = type;
	}
	
	public static int getRandCandy() {
		if(numberOfType == 0) {
			System.err.println("(!)numberOfType in Candy class == 0");
			return -1;
		}
		//Candy candy = new Candy(rand.nextInt(numberOfType));
		return rand.nextInt(numberOfType);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	

	public static int getNumberOfType() {
		return numberOfType;
	}

	public static void setNumberOfType(int numberOfType) {
		Candy.numberOfType = numberOfType;
	}
	
}

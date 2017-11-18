import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Supplier;

import javafx.scene.Group;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Candy {
	private static final String[] colorImageDirectory = new String[] { "red.png", "blue.png",
			"yellow.png", "cyan.png", "purple.png", "pink.png", "green.png",
			"Chau.JPG", "Duy.PNG", "Quang.JPG", "red.jpg" };
	private static int numberOfCandyColor = Main.getNumberofcandycolor();
	private static Integer maxProba;
	private static ArrayList<Image> colorImgList = new ArrayList<Image>();
	private static TreeMap<Integer, Supplier<Candy>> candyTypeProbabilityList;

	private int color;

	public Candy() {
	}

	public Candy(int color) {
		this();
		this.color = color;
	}

	/**
	 * Add new candy type and control their probabilities here Probability of
	 * occurrence of a type of candy is calculate by the formula P(c) = V(c) / sumP
	 * P(c): Probability of occurrence of candy type c V(c): Probability value
	 * defined for candy type c sumP: Summation of all probability value in the list
	 */
	public static void init() {
		maxProba = 0;
		candyTypeProbabilityList = new TreeMap<Integer, Supplier<Candy>>();
		candyTypeProbabilityList.put((maxProba += 100), CandyNormal::new);
		candyTypeProbabilityList.put((maxProba += 3), Candy3x3Bomb::new);
		candyTypeProbabilityList.put((maxProba += 3), CandyVerticalBomb::new);

		assert (candyTypeProbabilityList.size() >= Main.getNumberofcandycolor());
		assert (colorImageDirectory.length >= Main.getNumberofcandycolor());

		for (String dir : colorImageDirectory)
			colorImgList.add(new Image(dir));
		System.out.println(maxProba);
	}

	/**
	 * Generate a random candy type with color defined
	 * 
	 * @param color
	 *            Desired color
	 * 
	 * @return A Candy object with random type with out color defined
	 */
	public static Candy getRandCandyType(int color) {
		assert (candyTypeProbabilityList.size() != 0);
		Candy candy = candyTypeProbabilityList.ceilingEntry(new Random().nextInt(maxProba)).getValue().get();
		candy.color = color;
		return candy;
	}

	/**
	 * Generate a candy with random type and random color
	 * 
	 * @return a random candy
	 */
	public static Candy getRandCandy() {
		assert (candyTypeProbabilityList.size() != 0);
		int type = new Random().nextInt(maxProba);
		System.out.println(type + " " + candyTypeProbabilityList.ceilingEntry(type).getValue().getClass());
		Candy candy = candyTypeProbabilityList.ceilingEntry(type).getValue().get();
		candy.color = new Random().nextInt(numberOfCandyColor);
		return candy;
	}
	
	public Group getImage() {
		ImageView colorImgV = new ImageView(colorImgList.get(color));
		ImageView typeImgV = new ImageView(this.getTypeImage());
		colorImgV.setFitHeight(Main.getGameBoard().getCellHeight());
		colorImgV.setFitWidth(Main.getGameBoard().getCellWidth());
		typeImgV.setFitHeight(Main.getGameBoard().getCellHeight());
		typeImgV.setFitWidth(Main.getGameBoard().getCellWidth());
		typeImgV.setBlendMode(BlendMode.SRC_OVER);
		return new Group(colorImgV, typeImgV);
	}

	/**
	 * Check if the candy at given coordinate is to be exploded under the effect of
	 * this current type of candy
	 * 
	 * @param coor
	 *            The coordinate of the candy want to check
	 * @return If the candy at the given coordinate is to be exploded
	 */
	public abstract ArrayList<Coordinate> specialExplode(Coordinate curCoor);
	
	public abstract Image getTypeImage();

	public int getColor() {
		return color;
	}
}

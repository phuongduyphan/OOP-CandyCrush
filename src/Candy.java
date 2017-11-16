import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;
import java.util.function.Supplier;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Candy {
	private static final String[] colorImageDirectory = new String[] { "Images/red.png", "Images/blue.png",
			"Images/yellow.png", "Images/cyan.png", "Images/purple.png", "Images/pink.png", "Images/green.png",
			"Images/Chau.JPG", "Images/Duy.PNG", "Images/Quang.JPG", "Images/red.jpg" };
	
	private static TreeMap<Integer, Supplier<Candy>> candyTypeProbabilityList;
	private static Integer maxProba;
	private static Random rand = new Random();
	private static ArrayList<Image> colorImgList = new ArrayList<Image>();
	
	//ABSTRACT FIELD	
	private static Image effectImage; 
	
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
	public void init() {
		maxProba = 0;
		candyTypeProbabilityList.put((maxProba += 100), CandyNormal::new);
		candyTypeProbabilityList.put((maxProba += 5), CandyVerticalBomb::new);	

		assert(candyTypeProbabilityList.size() >= Main.getNumberofcandytype());
		assert(colorImageDirectory.length >= Main.getNumberofcandytype());
		
		for (int i = 0; i < Main.getNumberofcandytype(); ++i)
			colorImgList.add(new Image(colorImageDirectory[i]));

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
		Candy candy = candyTypeProbabilityList.ceilingEntry(rand.nextInt(maxProba)).getValue().get();
		candy.setColor(color);
		return candy;
	}
	
	public static Image getColorImg(int index) {
		return colorImgList.get(index);
	}

	/**
	 * Check if the candy at given coordinate is to be exploded under the effect of
	 * this current type of candy
	 * 
	 * @param coor
	 *            The coordinate of the candy want to check
	 * @return If the candy at the given coordinate is to be exploded
	 */
	public abstract boolean specialExplode(Coordinate thisCoor, Coordinate checkCoor);
	

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public static void setEffectImage(Image effectImage) {
		Candy.effectImage = effectImage;
	}

}

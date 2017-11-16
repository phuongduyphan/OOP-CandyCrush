import javafx.scene.image.Image;

public class CandyVerticalBomb extends Candy{

	private static final String effectOverlayDirectory = "Images/overlayVerticalBomB.png";
	
	public CandyVerticalBomb() {
		super();
		setEffectImage(new Image(effectOverlayDirectory));
	}
	public CandyVerticalBomb(int color) {
		super(color);
		setEffectImage(new Image(effectOverlayDirectory));
	}
	@Override
	public boolean specialExplode(Coordinate thisCoor, Coordinate checkCoor) {
		if(thisCoor.getColumn() == checkCoor.getColumn()) return true;
		return false;
	}
}

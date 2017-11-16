import javafx.scene.image.Image;

public class CandyNormal extends Candy{

	private static final String effectOverlayDirectory = "Images/overlayNormal.png";
	
	public CandyNormal() {
		super();
		setEffectImage(new Image(effectOverlayDirectory));
	}
	public CandyNormal(int color) {
		super(color);
		setEffectImage(new Image(effectOverlayDirectory));
	}
	@Override
	public boolean specialExplode(Coordinate thisCoor, Coordinate checkCoor) {
		return false;
	}
}

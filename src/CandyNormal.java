import java.util.ArrayList;

import javafx.scene.image.Image;

public class CandyNormal extends Candy {

	private static final String typeImageDir = "overlayNormal.png";
	private static Image typeImage = new Image(typeImageDir);

	public CandyNormal() {
		super();
	}

	public CandyNormal(int color) {
		super(color);
	}

	@Override
	public Image getTypeImage() {
		return typeImage;
	}

	@Override
	public ArrayList<Coordinate> specialExplode(Coordinate curCoor) {
		return new ArrayList<Coordinate>();
	}
}

import java.util.ArrayList;

public class Hammer extends Booster {

	public Hammer(Coordinate coor) {
		super(coor);
	}

	@Override
	public void act() {
		Main.getBoard().crush(coor);
		Main.getBoard().updateBoard(new ArrayList<Coordinate>());
	}

}

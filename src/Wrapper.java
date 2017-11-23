import java.util.ArrayList;

public class Wrapper extends Booster {

	public Wrapper(Coordinate coor) {
		super(coor);
	}

	@Override
	public void act() {
		int color = Main.getBoard().getCandy(coor).getColor();
		Main.getBoard().setCandy(coor, new Candy3x3Bomb(color));
		Main.getBoard().updateBoard(new ArrayList<Coordinate>());
		//or Main.getGameBoard().updateBoard(); ???
	}

}

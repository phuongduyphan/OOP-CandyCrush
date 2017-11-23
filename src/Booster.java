
public abstract class Booster {
	protected Coordinate coor;

	public Booster(Coordinate coor) {
		this.coor = coor;
	}

	public abstract void act();
}

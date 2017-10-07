import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

/**
 * INSERT DESCRIPTION FOR PUBLIC METHOD HERE
 *
 */
public class Drawer {
	

	private static final int cellWidth = 40;
	private static final int cellHeight = 40;
	private static final int numberOfColumn = 7;
	private static final int numberOfRow = 7;
	
	Pane GameBoard = new Pane();
	
	public Drawer() {
		
	}
	
	public void newFall(ArrayList<Coordinate> coorList, ArrayList<Integer> typeList) { // doi mau tu 0 thanh type
		
	}
	
	public void swap(Coordinate coor1, Coordinate coor2) {
		
	}
	
	public void crush(Coordinate coor) { //doi mau ve 0
		
	}
	
	public void move(ArrayList<Pair<Coordinate, Coordinate>> coorList ) {
		
	}
	
	private void swapColor()
	
	private Coordinate transPixel(Coordinate coordinate) {
		// ROW ~ Y
		// COLUMN ~ X
		int x = coordinate.getColumn().intValue();
		int y = coordinate.getRow().intValue();
		
		x = x * cellWidth;
		y = y * cellHeight;		
		return coordinate = new Coordinate(y ,x);
	}
}

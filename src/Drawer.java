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

/**
 * INSERT DESCRIPTION FOR PUBLIC METHOD HERE
 *
 */
public class Drawer {
	public Scene scene;
	public Drawer() {
		
	}
	
	public boolean swap(Coordinate coor1, Coordinate coor2) {
		ArrayList<Coordinate> oldCoor = new ArrayList<Coordinate>();
		ArrayList<Coordinate> newCoor = new ArrayList<Coordinate>();
		oldCoor.add(coor1);
		oldCoor.add(coor2);
		newCoor.add(coor2);
		newCoor.add(coor1);
		return move(oldCoor, newCoor);
		
	}
	
	public boolean swap(ArrayList<Coordinate> oldCoorList, ArrayList<Coordinate> newCoorList) {
		return move(oldCoorList, newCoorList);
	}
	
	public boolean crush(ArrayList<Coordinate> coorList) {
		//TODO
		//draw exploding animation at given coors
		//at the end the given coors are blank
	}
	
	public boolean move(ArrayList<Coordinate> oldCoorList, ArrayList<Coordinate> newCoorList) {
		//TODO
		//move from old coor to new coor
	}
	
	public boolean newFall(ArrayList<Coordinate> newCoorList, ArrayList<int> candyList) {
	//Candies in list must be in the same col
	//Order in list must be from bottom to top
	}
	
	/*public boolean select(Coordinate coordinate) {
		//highlight given cell and adjacent cell
	}
	
	public boolean deselect(Coordinate coordinate) {
		//deselect all current selected cell
	}*/
}

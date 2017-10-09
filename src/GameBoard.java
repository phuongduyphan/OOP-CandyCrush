import java.util.ArrayList;
import java.util.Random;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 * INSERT DESCRIPTION FOR PUBLIC METHOD HERE
 *
 */
public class GameBoard {
	// Editable properties
	private static final int windowWidth = 320;
	private static final int numberOfColumn = 10;
	private static final int numberOfRow = 10;
	private static final String emptyCellDirectory = "Images/0.png";
	private static final String[] imageDirectory = new String[] { 	"Images/red.png",
																	"Images/blue.png",
																	"Images/yellow.png",
																	"Images/cyan.png",
																	"Images/purple.png",
																	"Images/pink.png",
																	"Images/green.png",
																	"Images/Chau.JPG",
																	"Images/Duy.PNG",
																	"Images/Quang.JPG",
																	"Images/red.jpg"};

	// Automatic properties
	private static final int cellWidth = windowWidth / numberOfColumn;
	//private static final int cellHeight = cellWidth;
	//	private static final int windowHeight = numberOfRow * cellHeight;
	private static Random random = new Random();
	private static GridPane gameBoardPane;
	private static FXMLLoader gameBoardLoader;
	private static ArrayList<ImageView> imgGrid = new ArrayList<ImageView>();
	private static ArrayList<Image> imgList = new ArrayList<Image>();
	private static Image emptyCell = new Image(emptyCellDirectory);
	// private static GameBoardController gameBoardController;

	public GameBoard() throws Exception {
		// Load FXML
		gameBoardLoader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
		gameBoardPane = gameBoardLoader.load();

		// Load images
		for (int i = 0; i < imageDirectory.length; ++i) imgList.add(new Image(imageDirectory[i]));
		
		//Generate board
		for (int i = 0; i < numberOfColumn; ++i) {
			for (int j = 0; j < numberOfRow; ++j) {
				imgGrid.add(new ImageView(imgList.get(random.nextInt(imgList.size()))));
				imgGrid.get(imgGrid.size()-1).setFitWidth(cellWidth);
				imgGrid.get(imgGrid.size()-1).setPreserveRatio(true);
				gameBoardPane.add(imgGrid.get(imgGrid.size()-1), i, j);
			}
			gameBoardPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
		}
	}

	public void newFall(ArrayList<Coordinate> coorList, ArrayList<Integer> typeList) { // doi mau tu 0 thanh type
		for(int i = 0; i < coorList.size(); ++i) {
			imgGrid.get(toIdex(coorList.get(i))).setImage(new Image(imageDirectory[typeList.get(i)]));
		}
	}

	public void swap(Coordinate coor1, Coordinate coor2) {
		Image i1 = imgGrid.get(toIdex(coor1)).getImage();
		Image i2 = imgGrid.get(toIdex(coor2)).getImage();
		
		imgGrid.get(toIdex(coor1)).setImage(i2);
		imgGrid.get(toIdex(coor2)).setImage(i1);
	}

	public void crush(Coordinate coor) { // doi mau ve 0
		imgGrid.get(toIdex(coor)).setImage(emptyCell);
	}

	public void move(ArrayList<Pair<Coordinate, Coordinate>> coorList) {
		for(Pair<Coordinate, Coordinate> pair : coorList) {
			Coordinate before = pair.getKey();
			Coordinate after = pair.getValue();
			swap(before, after);
		}
	}

	private int toIdex(Coordinate coordinate) {
		int row = coordinate.getKey().intValue();
		int col = coordinate.getValue().intValue();

		return row * numberOfColumn + col;
	}

	public GridPane getGameBoardPane() {
		return gameBoardPane;
	}
	
	public void getERekt() {
		for(ImageView img : imgGrid) {
			img.setImage(imgList.get(random.nextInt(imgList.size())));
		}
	}
}

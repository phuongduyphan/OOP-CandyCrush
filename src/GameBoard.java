import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import javafx.util.Pair;
public class GameBoard {
	/** Editable properties */
	private static final String emptyCellDirectory = "Images/0.png";
	private static final String[] imageDirectory = new String[] { "Images/red.png", "Images/blue.png",
			"Images/yellow.png", "Images/cyan.png", "Images/purple.png", "Images/pink.png", "Images/green.png",
			"Images/Chau.JPG", "Images/Duy.PNG", "Images/Quang.JPG", "Images/red.jpg" };

	/** Variables */
	private int windowWidth;
	private int numberOfColumn;
	private int numberOfRow;
	private int cellWidth;
	private int cellHeight;
	private int numberOfCandyType = imageDirectory.length;
	private GridPane gameBoardPane;
	private static Random random = new Random();
	private static ArrayList<ImageView> imgGrid = new ArrayList<ImageView>();
	private static ArrayList<Image> imgList = new ArrayList<Image>();
	private static Image emptyCell = new Image(emptyCellDirectory);
	private static CellSelectionHandler cellSelectionHandler;
	private static Board board;

	public GameBoard() throws Exception {
		// Init var
		windowWidth = Main.getWindowwidth();
		numberOfColumn = Main.getNumberofcolumn();
		numberOfRow = Main.getNumberofrow();
		numberOfCandyType = imageDirectory.length;
		cellWidth = windowWidth / numberOfColumn;
		cellHeight = cellWidth;
		cellSelectionHandler = new CellSelectionHandler();

		// Load FXML
		FXMLLoader gameBoardLoader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
		gameBoardPane = gameBoardLoader.load();

		// Load images
		for (int i = 0; i < imageDirectory.length; ++i)
			imgList.add(new Image(imageDirectory[i]));
		
		// Init board
		board = new Board();
		board.setSize(numberOfRow, numberOfColumn);
		board.setNumType(getNumberOfCandyType());
		board.generateBoard();
		board.isValid();

		// Generate board
		for (int i = 0; i < numberOfRow; ++i) {
			for (int j = 0; j < numberOfColumn; ++j) {
				imgGrid.add(new ImageView(imgList.get(board.getGridAt(i, j))));
				imgGrid.get(imgGrid.size() - 1).setFitWidth(cellWidth);
				imgGrid.get(imgGrid.size() - 1).setPreserveRatio(true);
				imgGrid.get(imgGrid.size() - 1).addEventHandler(MouseEvent.MOUSE_CLICKED,
						new EventHandler<MouseEvent>() {
							private int idx = imgGrid.size() - 1;

							@Override
							public void handle(MouseEvent event) {
								click(idx);
							}
						});
				gameBoardPane.add(imgGrid.get(imgGrid.size() - 1), j, i);
			}
			gameBoardPane.getRowConstraints().add(new RowConstraints(cellHeight));
			gameBoardPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
		}
	}

	/**
	 * Is called whenever a cell is clicked
	 * Handling checking neighbor-ness by cellSelectionHandler 
	 */
	private void click(int idx) {
		int row = toRow(idx);
		int col = toCol(idx);
		System.out.println("CLICKED AT " + row + " " + col);
		ArrayList<Coordinate> selected = cellSelectionHandler.select(new Coordinate(row, col));
		if(!selected.isEmpty()) {
			board.swapCandies(selected.get(0), selected.get(1));
		}
	}

	/**
	 * Change candies from list of Coordinate according to list of type.
	 * 
	 * @param coorList
	 *            List of to-be-changed candies' coordinates
	 * @param typeList
	 *            List of type for candies to be changed into
	 */
	public void newFall(ArrayList<Coordinate> coorList, ArrayList<Integer> typeList) { // doi mau tu 0 thanh type
		for (int i = 0; i < coorList.size(); ++i) {
			// imgGrid.get(toIdex(coorList.get(i))).setImage(new
			// Image(imageDirectory[typeList.get(i)]));
			flip(imgGrid.get(i), imgList.get(typeList.get(i)));
		}
	}

	/**
	 * Swap the type of two candies at two coordinates
	 * 
	 * @param coor1
	 *            First coordinate
	 * @param coor2
	 *            Second coordinate
	 */
	public void swap(Coordinate coor1, Coordinate coor2) {
		Image i1 = imgGrid.get(toIdex(coor1)).getImage();
		Image i2 = imgGrid.get(toIdex(coor2)).getImage();

		imgGrid.get(toIdex(coor1)).setImage(i2);
		imgGrid.get(toIdex(coor2)).setImage(i1);
	}

	/**
	 * Swap a list of coordinates pairs
	 * 
	 * @param coorList
	 *            List of coordinates pairs
	 */
	public void swap(ArrayList<Pair<Coordinate, Coordinate>> coorList) {
		for (Pair<Coordinate, Coordinate> pair : coorList) {
			Coordinate before = pair.getKey();
			Coordinate after = pair.getValue();
			swap(before, after);
		}
	}

	/**
	 * Change type of a candy at a coordinate to 0 (empty cell)
	 * 
	 * @param coor
	 *            the desired coordinate
	 */
	public void crush(Coordinate coor) { // doi mau ve 0
		// imgGrid.get(toIdex(coor)).setImage(emptyCell);
		flip(imgGrid.get(toIdex(coor)), emptyCell);
	}

	/**
	 * Change type of candies at coordinates to 0 (empty cell)
	 * 
	 * @param coorList
	 */
	public void crush(ArrayList<Coordinate> coorList) {
		for (Coordinate coor : coorList) {
			crush(coor);
		}
	}

	/**
	 * Calculate 1D index from coordinate
	 * 
	 * @param coordinate
	 * @return index in 1D manner
	 */
	private int toIdex(Coordinate coordinate) {
		int row = coordinate.getKey().intValue();
		int col = coordinate.getValue().intValue();
		return row * numberOfColumn + col;
	}

	/**
	 * Calculate coordinate from 1D index
	 */
	private int toRow(int idx) {
		return idx / numberOfColumn;
	}

	private int toCol(int idx) {
		return idx % numberOfColumn;
	}

	/**
	 * Transition
	 */
	private void flip(ImageView imgView, Image img) {
		ScaleTransition hideFront = new ScaleTransition(Duration.millis(50), imgView);
		hideFront.setFromX(1);
		hideFront.setToX(0);
		hideFront.setInterpolator(Interpolator.EASE_IN);

		hideFront.setOnFinished(e -> {
			imgView.setImage(img);
		});

		ScaleTransition showBack = new ScaleTransition(Duration.millis(50), imgView);
		showBack.setFromX(0);
		showBack.setToX(1);
		showBack.setInterpolator(Interpolator.EASE_OUT);

		SequentialTransition sequence = new SequentialTransition(hideFront, showBack);
		sequence.play();

	}

	public GridPane getGameBoardPane() {
		return gameBoardPane;
	}

	/**
	 * Lolz
	 */
	public void getERekt() {
		for (ImageView img : imgGrid) {
			flip(img, imgList.get(random.nextInt(numberOfCandyType)));
			// img.setImage(imgList.get(random.nextInt(imgList.size())));
		}
	}

	public int getNumberOfCandyType() {
		return numberOfCandyType;
	}
}

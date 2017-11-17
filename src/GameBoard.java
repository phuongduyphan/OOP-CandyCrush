import java.util.ArrayList;
import java.util.ListIterator;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
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
	private static final int flipTransitionDuration = 200;
	private static final String emptyCellDirectory = "Images/0.png";
	private static final String[] imageDirectory = new String[] { emptyCellDirectory, "Images/red.png", "Images/blue.png",
			"Images/yellow.png", "Images/cyan.png", "Images/purple.png", "Images/pink.png", "Images/green.png",
			"Images/Chau.JPG", "Images/Duy.PNG", "Images/Quang.JPG", "Images/red.jpg" };

	private static ArrayList<ImageView> imgGrid = new ArrayList<ImageView>();
	private static ArrayList<Image> imgList = new ArrayList<Image>();
	private static Image emptyCell = new Image(emptyCellDirectory);
	private static CellSelectionHandler cellSelectionHandler;
	private static SequentialTransition sequence;
	private static int[][] grid;
	/** Variables */
	private int windowWidth;
	private int numberOfColumn;
	private int numberOfRow;
	private int cellWidth;
	private int cellHeight;
	private GridPane gameBoardPane;

	public GameBoard() throws Exception {
		// Init var
		windowWidth = Main.getWindowwidth();
		numberOfColumn = Main.getNumberofcolumn();
		numberOfRow = Main.getNumberofrow();
		cellWidth = windowWidth / numberOfColumn;
		cellHeight = cellWidth;
		cellSelectionHandler = new CellSelectionHandler();
		sequence = new SequentialTransition();

		// Load FXML
		FXMLLoader gameBoardLoader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
		gameBoardPane = gameBoardLoader.load();
		gameBoardPane.setPrefWidth(Main.getWindowwidth());

		// Load images
		for (int i = 0; i < imageDirectory.length; ++i)
			imgList.add(new Image(imageDirectory[i]));

		// Init board
		for (int i = 0; i < numberOfRow; ++i) {
			for (int j = 0; j < numberOfColumn; ++j) {
				imgGrid.add(new ImageView(emptyCell)); // Init to empty cell
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
		grid = new int[numberOfRow][numberOfColumn];
	}

	/**
	 * Change type of a list of candies at coordinates to 0 (empty cell)
	 * Add the transitions by a parallel manner into sequence
	 * 
	 * @param coorList
	 *            the list of desired coordinates
	 */
	public void crush(ArrayList<Coordinate> coorList) {
		ParallelTransition transition = new ParallelTransition();
		for (Coordinate coor : coorList) {
			transition.getChildren().add(getFlipAnimation(imgGrid.get(toIndex(coor)), emptyCell));
		}
		sequence.getChildren().add(transition);
		System.out.println("Crush " + transition);
	}
	
	/**
	 * Change type of a candy at a coordinate to 0 (empty cell)
	 * 
	 * @param coor
	 *            the desired coordinate
	 */
	public void crush(Coordinate coor) {
		Animation transition = getFlipAnimation(imgGrid.get(toIndex(coor)), emptyCell);
		sequence.getChildren().add(transition);
		System.out.println("Crush " + transition);
	}

	/**
	 * Swap animation twice, back to the original type
	 * Add the two transitions in a parallel manner into sequence
	 * @param coor1
	 * @param coor2
	 */
	public void flipFlop(Coordinate coor1, Coordinate coor2) {
		ParallelTransition transition = new ParallelTransition();
		Image i1 = imgGrid.get(toIndex(coor1)).getImage();
		Image i2 = imgGrid.get(toIndex(coor2)).getImage();

		transition.getChildren().add(getFlipFlopAnimation(imgGrid.get(toIndex(coor1)), (i2)));
		transition.getChildren().add(getFlipFlopAnimation(imgGrid.get(toIndex(coor2)), (i1)));
		sequence.getChildren().add(transition);
		System.out.println("Flipflop " + transition);
		System.out.println("SwApSwAp: " + coor1 + " <-> " + coor2);
	}

	public GridPane getGameBoardPane() {
		return gameBoardPane;
	}

	/**
	 * Play then reset the sequence
	 */
	 public void play() {
		 ListIterator<Animation> list = sequence.getChildren().listIterator();
		 System.out.println("play:");
		 while(list.hasNext()) System.out.println(list.next());
		 System.out.println();
		 sequence.play();
		 sequence.getChildren().clear();
	 }

	/**
	 * Set candies from list of Coordinate according to list of type.
	 * Add the transition in parallel manner into sequence
	 * 
	 * @param coorList
	 *            List of to-be-changed candies' coordinates
	 * @param typeList
	 *            List of type for candies to be changed into
	 */
	public void setCandy(ArrayList<Coordinate> coorList, ArrayList<Integer> typeList) {
		ParallelTransition transition = new ParallelTransition();
		for (int i = 0; i < coorList.size(); ++i) {
			transition.getChildren().add(getFlipAnimation(imgGrid.get(toIndex(coorList.get(i))), imgList.get(typeList.get(i))));
		}
		System.out.println("Set candy " + transition);
		sequence.getChildren().add(transition);
	}
	
	/**
	 * Set a candy's type
	 * Add the transition into sequence
	 * 
	 * @param coor
	 *            Coordinate of the to-be-changed candy
	 * @param type
	 *            The type for the candy to be changed into
	 */
	public void setCandy(Coordinate coor, Integer type) {
		Animation transition = getFlipAnimation(imgGrid.get(toIndex(coor)), imgList.get(type));
		sequence.getChildren().add(transition);
		System.out.println("Set candy " + transition);
	}

	/**
	 * Swap a list of coordinates pairs
	 * Add the transitions by a parallel manner into sequence
	 * 
	 * @param coorList
	 *            List of coordinates pairs
	 */
	public void swap(ArrayList<Pair<Coordinate, Coordinate>> coorList) {
		ParallelTransition transition = new ParallelTransition();
		for (Pair<Coordinate, Coordinate> pair : coorList) {
			Coordinate coor1 = pair.getKey();
			Coordinate coor2 = pair.getValue();
			transition.getChildren().add(getSwapAnimation(coor1, coor2));
		}
		System.out.println("Swap " + transition);
		sequence.getChildren().add(transition);
	}

	/**
	 * Swap the type of two candies at two coordinates
	 * Add the transition into sequence
	 * 
	 * @param coor1
	 *            First coordinate
	 * @param coor2
	 *            Second coordinate
	 */
	public void swap(Coordinate coor1, Coordinate coor2) {
		Animation transition = getSwapAnimation(coor1, coor2);
		sequence.getChildren().add(transition);
		System.out.println("Swap " + transition);
		System.out.println("SWAP: " + coor1 + " <-> " + coor2);
	}
	
	public void pause() {
		sequence.getChildren().add(new PauseTransition(new Duration(flipTransitionDuration)));
	}

	/**
	 * Is called whenever a cell is clicked Handling checking neighbor-ness by
	 * cellSelectionHandler
	 */
	private void click(int idx) {
		int row = toRow(idx);
		int col = toCol(idx);
		System.out.println("CLICKED AT " + row + " " + col);
		ArrayList<Coordinate> selected = cellSelectionHandler.select(new Coordinate(row, col));
		if (selected != null) {
			Main.getBoard().swapCandies(selected.get(0), selected.get(1));
		}
	}

	/**
	 * Fundamental Transition
	 */
	private Animation getFlipAnimation(ImageView imgView, Image img) {
		ScaleTransition hideFront = new ScaleTransition(Duration.millis(flipTransitionDuration), imgView);
		hideFront.setFromX(1);
		hideFront.setToX(0);
		hideFront.setInterpolator(Interpolator.EASE_IN);

		hideFront.setOnFinished(e -> {
			imgView.setImage(img);
		});

		ScaleTransition showBack = new ScaleTransition(Duration.millis(flipTransitionDuration), imgView);
		showBack.setFromX(0);
		showBack.setToX(1);
		showBack.setInterpolator(Interpolator.EASE_OUT);

		return new SequentialTransition(hideFront, showBack);
	}

	/**
	 * Flip a cell to a new type and then flip back.
	 * Used when two cell is non-swap-able
	 */
	private Animation getFlipFlopAnimation(ImageView imgView, Image img) {
		Image old = imgView.getImage();
		
		ScaleTransition hideFront = new ScaleTransition(Duration.millis(flipTransitionDuration), imgView);
		hideFront.setFromX(1);
		hideFront.setToX(0);
		hideFront.setInterpolator(Interpolator.EASE_IN);

		hideFront.setOnFinished(e -> {
			imgView.setImage(img);
		});

		ScaleTransition showBack = new ScaleTransition(Duration.millis(flipTransitionDuration), imgView);
		showBack.setFromX(0);
		showBack.setToX(1);
		showBack.setInterpolator(Interpolator.EASE_BOTH);
		
		ScaleTransition hideBack = new ScaleTransition(Duration.millis(flipTransitionDuration), imgView);
		hideBack.setFromX(1);
		hideBack.setToX(0);
		hideBack.setInterpolator(Interpolator.EASE_BOTH);
		
		hideBack.setOnFinished(e -> {
			imgView.setImage(old);
		});
		
		ScaleTransition showFront = new ScaleTransition(Duration.millis(flipTransitionDuration), imgView);
		showFront.setFromX(0);
		showFront.setToX(1);
		showFront.setInterpolator(Interpolator.EASE_OUT);

		return new SequentialTransition(hideFront, showBack, hideBack, showFront);
	}

	private Animation getSwapAnimation(Coordinate coor1, Coordinate coor2) {
		ParallelTransition transition = new ParallelTransition();
		Image i1 = imgGrid.get(toIndex(coor1)).getImage();
		Image i2 = imgGrid.get(toIndex(coor2)).getImage();

		transition.getChildren().add(getFlipAnimation(imgGrid.get(toIndex(coor1)), (i2)));
		transition.getChildren().add(getFlipAnimation(imgGrid.get(toIndex(coor2)), (i1)));
		return transition;
	}
	
	private int toCol(int idx) {
		return idx % numberOfColumn;
	}
	
	/**
	 * Calculate 1D index from coordinate
	 * 
	 * @param coordinate
	 * @return index in 1D manner
	 */
	private int toIndex(Coordinate coordinate) {
		int row = coordinate.getRow();
		int col = coordinate.getColumn();
		return row * numberOfColumn + col;
	}

	/**
	 * Calculate coordinate from 1D index
	 */
	private int toRow(int idx) {
		return idx / numberOfColumn;
	}
	
	public void updateBoard() {
		ParallelTransition transition = new ParallelTransition();
		for(int i = numberOfRow-1; i >= 0; --i) {
			for(int j = numberOfColumn-1; j >= 0; --j) {
				if(grid[i][j] != Main.getBoard().getGrid()[i][j]) {
					grid[i][j] = Main.getBoard().getGrid()[i][j];
					ImageView imgView = imgGrid.get(toIndex(new Coordinate(i, j)));
					Image img = imgList.get(grid[i][j]);
					Animation animation = getFlipAnimation(imgView, img);
					transition.getChildren().add(animation);
				}
			}
		}
		sequence.getChildren().add(transition);
	}
}

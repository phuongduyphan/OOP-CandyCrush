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
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameBoard {
	/** Editable properties */
	private static final int flipTransitionDuration = 100;
	private static final String emptyCellDirectory = "Images/0.png";
	private static final String[] imageDirectory = new String[] { emptyCellDirectory, "Images/red.png",
			"Images/blue.png", "Images/yellow.png", "Images/cyan.png", "Images/purple.png", "Images/pink.png",
			"Images/green.png", "Images/Chau.JPG", "Images/Duy.PNG", "Images/Quang.JPG", "Images/red.jpg" };

	/** Variables */
	private static ArrayList<ImageView> imgGrid = new ArrayList<ImageView>();
	private static ArrayList<Image> imgList = new ArrayList<Image>();
	private static Image emptyCell = new Image(emptyCellDirectory);
	private int[][] grid;
	private int windowWidth;
	private int numberOfColumn;
	private int numberOfRow;
	private int cellWidth;
	private int cellHeight;
	private GridPane gameBoardPane;
	private Coordinate currentSelected;
	private SequentialTransition sequence;
	private CellSelectionHandler cellSelectionHandler;

	public GameBoard() throws Exception {
		// Init var
		windowWidth = Main.getWindowwidth();
		numberOfColumn = Main.getNumberofcolumn();
		numberOfRow = Main.getNumberofrow();
		cellWidth = windowWidth / numberOfColumn;
		cellHeight = cellWidth;
		cellSelectionHandler = new CellSelectionHandler();
		sequence = new SequentialTransition();
		currentSelected = null;

		// Load FXML
		FXMLLoader gameBoardLoader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
		gameBoardPane = gameBoardLoader.load();
		gameBoardPane.setPrefWidth(Main.getWindowwidth());

		// Load images
		assert(imageDirectory.length > Main.getNumberofcandytype());
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

	public GridPane getGameBoardPane() {
		return gameBoardPane;
	}

	public void pause() {
		sequence.getChildren().add(new PauseTransition(new Duration(flipTransitionDuration)));
	}

	/**
	 * Play then reset the sequence
	 */
	public void play() {
		ListIterator<Animation> list = sequence.getChildren().listIterator();
		System.out.println("play:");
		while (list.hasNext())
			System.out.println(list.next());
		System.out.println();
		sequence.play();
		sequence.getChildren().clear();
	}

	public void updateBoard() {
		ParallelTransition transition = new ParallelTransition();
		for (int i = numberOfRow - 1; i >= 0; --i) {
			for (int j = numberOfColumn - 1; j >= 0; --j) {
				if (grid[i][j] != Main.getBoard().getGrid()[i][j]) {
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

	/**
	 * Is called whenever a cell is clicked Handling checking neighbor-ness by
	 * cellSelectionHandler
	 */
	private void click(int idx) {
		if(sequence.getStatus() == Animation.Status.RUNNING) return;
		int row = toRow(idx);
		int col = toCol(idx);
		System.out.println("CLICKED AT " + row + " " + col);
		ArrayList<Coordinate> selected = cellSelectionHandler.select(new Coordinate(row, col));
		if (selected != null) {
			assert (selected.size() <= 2);
			for (Coordinate coor : selected)
				imgGrid.get(toIndex(coor)).setEffect(new InnerShadow(BlurType.ONE_PASS_BOX,
						Color.rgb(88, 237, 235, 0.5), cellWidth * 0.25, cellWidth * 0.1, 0, 0));
			if (selected.size() == 2) {
				currentSelected = null;
				for (Coordinate coor : selected)
					imgGrid.get(toIndex(coor)).setEffect(null);
				Main.getBoard().swapCandies(selected.get(0), selected.get(1));
			} else
				currentSelected = selected.get(0);
		} else {
			assert (currentSelected != null);
			imgGrid.get(toIndex(currentSelected)).setEffect(null);
			currentSelected = null;
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
}

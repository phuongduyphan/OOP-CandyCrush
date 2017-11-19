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
import javafx.scene.Group;
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
	private static final String emptyCellDirectory = "0.png";

	/** Variables */
	private static Group[][] imgGrid;
	private static Image emptyCell = new Image(emptyCellDirectory);
	private Candy[][] grid;
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

		// Init board
		imgGrid = new Group[numberOfRow][numberOfColumn];
		for (int i = 0; i < numberOfRow; ++i) {
			final int ii = i;
			gameBoardPane.getRowConstraints().add(new RowConstraints(cellHeight));
			gameBoardPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
			for (int j = 0; j < numberOfColumn; ++j) {
				final int jj = j;
				ImageView imgV = new ImageView(emptyCell);
				imgV.setPreserveRatio(true);
				imgGrid[i][j] = new Group(imgV); // Init to empty cell
				imgGrid[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					private int row = ii;
					private int col = jj;

					@Override
					public void handle(MouseEvent event) {
						click(row, col);
					}
				});
				gameBoardPane.add(imgGrid[i][j], j, i);
			}
		}
		grid = new Candy[numberOfRow][numberOfColumn];
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
//		ListIterator<Animation> list = sequence.getChildren().listIterator();
		System.out.println("play:");
//		while (list.hasNext())
//			System.out.println(list.next());
//		System.out.println();
		sequence.play();
		sequence.getChildren().clear();
	}

	public void updateBoard() {
		ParallelTransition transition = new ParallelTransition();
		Group img;
		for (int i = numberOfRow - 1; i >= 0; --i) {
			for (int j = numberOfColumn - 1; j >= 0; --j) {
				if (grid[i][j] != Main.getBoard().getGrid()[i][j]) {
					grid[i][j] = Main.getBoard().getGrid()[i][j];
					Group imgView = imgGrid[i][j];
					if(grid[i][j] == null)
						img = new Group(new ImageView(emptyCell));
					else 
						img = grid[i][j].getImage();
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
	private void click(int row, int col) {
		if (sequence.getStatus() == Animation.Status.RUNNING)
			return;
		System.out.println("CLICKED AT " + row + " " + col);
		ArrayList<Coordinate> selected = cellSelectionHandler.select(new Coordinate(row, col));
		if (selected != null) {
			assert (selected.size() <= 2);
			for (Coordinate coor : selected)
				imgGrid[coor.getRow()][coor.getColumn()].setEffect(new InnerShadow(BlurType.ONE_PASS_BOX,
						Color.rgb(88, 237, 235, 0.5), cellWidth * 0.25, cellWidth * 0.1, 0, 0));
			if (selected.size() == 2) {
				currentSelected = null;
				for (Coordinate coor : selected)
					imgGrid[coor.getRow()][coor.getColumn()].setEffect(null);
				Main.getBoard().swapCandies(selected.get(0), selected.get(1));
			} else
				currentSelected = selected.get(0);
		} else {
			assert (currentSelected != null);
			imgGrid[currentSelected.getRow()][currentSelected.getColumn()].setEffect(null);
			currentSelected = null;
		}
	}

	/**
	 * Fundamental Transition
	 */
	private Animation getFlipAnimation(Group group, Group neww) {
		ScaleTransition hideFront = new ScaleTransition(Duration.millis(flipTransitionDuration), group);
		hideFront.setFromX(1);
		hideFront.setToX(0);
		hideFront.setInterpolator(Interpolator.EASE_IN);

		hideFront.setOnFinished(e -> {
			group.getChildren().setAll(neww.getChildren());
		});

		ScaleTransition showBack = new ScaleTransition(Duration.millis(flipTransitionDuration), group);
		showBack.setFromX(0);
		showBack.setToX(1);
		showBack.setInterpolator(Interpolator.EASE_OUT);

		return new SequentialTransition(hideFront, showBack);
	}

	public int getCellWidth() {
		return cellWidth;
	}

	public int getCellHeight() {
		return cellHeight;
	}
}

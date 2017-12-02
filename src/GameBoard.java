import java.util.ArrayList;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;

/**
 * The GameBoard class will handle events of the game board.
 */
public class GameBoard {
	/** Editable properties */
	private static final int flipTransitionDuration = 120;
	private static final int pauseDuration = 50;
	private static final String emptyCellDirectory = "empty.png";
	private static final String cellSelectorDirectory = "cellSelector.png";

	/** Variables */
	private static Group[][] imgGrid;
	private static Image emptyCell = new Image(emptyCellDirectory);
	private static Image cellSelector = new Image(cellSelectorDirectory);
	private Candy[][] grid;
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
		numberOfColumn = Main.getNumberofcolumn();
		numberOfRow = Main.getNumberofrow();
		cellWidth = (562 * 80 / 100) / numberOfColumn;
		cellHeight = cellWidth;
		cellSelectionHandler = new CellSelectionHandler();
		sequence = new SequentialTransition();
		currentSelected = null;

		// Load FXML
		FXMLLoader gameBoardLoader = new FXMLLoader(getClass().getResource("GameBoard.fxml"));
		gameBoardPane = gameBoardLoader.load();
		gameBoardPane.setPrefWidth(562);
		gameBoardPane.setPrefHeight(750);

		// Init board
		gameBoardPane.setAlignment(Pos.CENTER);
		gameBoardPane.setHgap(cellWidth * 0.2);
		gameBoardPane.setVgap(gameBoardPane.getHgap());
		gameBoardPane.setBackground(
				new Background(new BackgroundImage(new Image("background.png"), null, null, null, null)));
		imgGrid = new Group[numberOfRow][numberOfColumn];
		for (int j = 0; j < numberOfColumn; ++j)
			gameBoardPane.getColumnConstraints().add(new ColumnConstraints(cellWidth));
		for (int i = 0; i < numberOfRow; ++i) {
			final int ii = i;
			gameBoardPane.getRowConstraints().add(new RowConstraints(cellHeight));
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
			ImageView cellSelectorImgV = new ImageView(cellSelector);
			cellSelectorImgV.setFitHeight(cellHeight);
			cellSelectorImgV.setFitWidth(cellWidth);
			for (Coordinate coor : selected)
				imgGrid[coor.getRow()][coor.getColumn()].getChildren().add(cellSelectorImgV);
			if (selected.size() == 2) {
				currentSelected = null;
				for (Coordinate coor : selected)
					imgGrid[coor.getRow()][coor.getColumn()].getChildren()
							.remove(imgGrid[coor.getRow()][coor.getColumn()].getChildren().size() - 1);
				Main.getBoard().swapCandies(selected.get(0), selected.get(1));
			} else
				currentSelected = selected.get(0);
		} else {
			assert (currentSelected != null);
			imgGrid[currentSelected.getRow()][currentSelected.getColumn()].getChildren()
					.remove(imgGrid[currentSelected.getRow()][currentSelected.getColumn()].getChildren().size() - 1);
			currentSelected = null;
		}
	}

	public int getCellHeight() {
		return cellHeight;
	}

	public int getCellWidth() {
		return cellWidth;
	}

	/**
	 * Fundamental Transition
	 */
	private Animation getFlipAnimation(Group group, Group neww, boolean isShrink) {
		ScaleTransition hideFront = new ScaleTransition(Duration.millis(flipTransitionDuration), group);
		hideFront.setFromX(1);
		hideFront.setToX(0);
		if (isShrink) {
			hideFront.setFromY(1);
			hideFront.setToY(0);
		}
		hideFront.setInterpolator(Interpolator.EASE_IN);

		hideFront.setOnFinished(e -> {
			group.getChildren().setAll(neww.getChildren());
		});

		ScaleTransition showBack = new ScaleTransition(Duration.millis(flipTransitionDuration), group);
		showBack.setFromX(0);
		showBack.setToX(1);
		if (isShrink) {
			showBack.setFromY(0);
			showBack.setToY(1);
		}
		showBack.setInterpolator(Interpolator.EASE_OUT);

		return new SequentialTransition(hideFront, showBack);
	}

	public GridPane getGameBoardPane() {
		return gameBoardPane;
	}

	public void pause() {
		sequence.getChildren().add(new PauseTransition(new Duration(pauseDuration)));
	}

	/**
	 * Play then reset the sequence
	 */
	public void play() {
		System.out.println("play:");
		sequence.play();
		sequence.getChildren().clear();
	}

	/**
	 * Compare and update the display to fit the data
	 */
	public void updateBoard() {
		ParallelTransition transition = new ParallelTransition();
		Group img;
		for (int i = numberOfRow - 1; i >= 0; --i) {
			for (int j = numberOfColumn - 1; j >= 0; --j) {
				if (grid[i][j] != Main.getBoard().getGrid()[i][j]) {
					grid[i][j] = Main.getBoard().getGrid()[i][j];
					Group imgView = imgGrid[i][j];
					if (grid[i][j] == null)
						img = new Group(new ImageView(emptyCell));
					else
						img = grid[i][j].getImage();
					Animation animation = getFlipAnimation(imgView, img, grid[i][j] == null);
					transition.getChildren().add(animation);
				}
			}
		}
		sequence.getChildren().add(transition);
	}
}

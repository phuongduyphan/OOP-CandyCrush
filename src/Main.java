import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	/** Windows Properties */
	private static final String screenTitle = "Candy Crush";

	/** GameBoard properties */
	private static final int numberOfColumn[] = { 5, 6, 8, 15 };

	private static final int numberOfRow[] = { 7, 8, 11, 20 };
	/** Timer Properties */
	private static final int time = 100; // sec

	private static final int timerUpdateInterval = 1000; // ms
	private static final int timerInitialDelay = 1000;
	/** Gameplay Properties */
	private static final int numberOfCandyColor = 6;

	/** Display Var */
	private static Stage stage;

	private static Scene scene;
	private static Board board;
	private static MainMenu mainMenu;
	private static GameBoard gameBoard;
	private static HeaderBoard headerBoard;
	private static TimeHandler timeHandler;
	private static int diff = 0;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage _stage) throws Exception {
		// Set up Candy
		Candy.init();

		// Set up menu
		mainMenu = new MainMenu();

		// Wrap up and display
		stage = _stage;
		stage.setTitle(screenTitle);
		stage.setResizable(false);
		Pane root = mainMenu.getMainMenuPane();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

	}

	/**
	 * Change the scene when hit play button Init the size according to the
	 * difficulty
	 * 
	 * @param _diff
	 *            difficulty
	 */
	public static void playButton(int _diff) {
		diff = _diff;
		// Set up Header Board and its controller
		try {
			headerBoard = new HeaderBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set up Game Board
		try {
			gameBoard = new GameBoard();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Set up Board
		board = new Board(numberOfRow[diff], numberOfColumn[diff], numberOfCandyColor);
		board.generateBoard();

		// Set up timer
		timeHandler = new TimeHandler(time);

		// Wrap up and display
		VBox root = new VBox();
		root.getChildren().addAll(headerBoard.getHeaderPane(), gameBoard.getGameBoardPane());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		gameBoard.play();
	}

	/**
	 * Is called between every timer-update-interval
	 * 
	 * @see timerUpdateInterval
	 */
	public static void timerAction() {
		headerBoard.setTimeValue(timeHandler.toString());
	}

	/**
	 * To be executed when the Time Handler runs out
	 */
	public static void timerEndAction() {
		System.out.println("ENDGAME");
	}

	public static Board getBoard() {
		return board;
	}

	public static GameBoard getGameBoard() {
		return gameBoard;
	}

	public static HeaderBoard getHeaderBoard() {
		return headerBoard;
	}

	public static int getNumberofcandycolor() {
		return numberOfCandyColor;
	}

	public static int getNumberofcolumn() {
		return numberOfColumn[diff];
	}

	public static int getNumberofrow() {
		return numberOfRow[diff];
	}

	public static int getTimerinitialdelay() {
		return timerInitialDelay;
	}

	public static int getTimerupdateinterval() {
		return timerUpdateInterval;
	}
}

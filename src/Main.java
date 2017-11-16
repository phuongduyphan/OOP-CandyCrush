import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
	/** Windows Properties */
	String screenTitle = "Candy Crush";

	/** GameBoard properties */
	private static final int windowWidth = 320;
	private static final int numberOfColumn = 10;
	private static final int numberOfRow = 5;
	private static final int numberOfCandyType = 15;

	/** Timer Properties */
	private static final int time = 100; // sec
	private static final int timerUpdateInterval = 1000; // ms
	private static final int timerInitialDelay = 1000;

	/** Display */
	private static Stage stage;
	private static Scene scene;
	private static GameBoard gameBoard;
	private static HeaderBoard headerBoard;

	/** Core */
	private static TimeHandler timeHandler;

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage _stage) throws Exception {
		// Set up Header Board and its controller
		headerBoard = new HeaderBoard();

		// Set up Game Board
		gameBoard = new GameBoard();

		// Set up timer
		timeHandler = new TimeHandler(time);

		// Wrap up and display
		VBox root = new VBox();
		root.getChildren().addAll(headerBoard.getHeaderPane(), gameBoard.getGameBoardPane());
		stage = _stage;
		stage.setTitle(screenTitle);
		stage.setResizable(false);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Is called between every timer-update-interval
	 * 
	 * @see timerUpdateInterval
	 */
	public static void timerAction() {
		headerBoard.setTimeValue(timeHandler.toString());
		// gameBoard.getERekt();
	}

	// To be executed when the Time Handler runs out
	public static void timerEndAction() {
		System.out.println("ENDGAME");
	}

	public static int getTimerupdateinterval() {
		return timerUpdateInterval;
	}

	public static int getTimerinitialdelay() {
		return timerInitialDelay;
	}

	public static int getNumberofcolumn() {
		return numberOfColumn;
	}

	public static int getNumberofrow() {
		return numberOfRow;
	}

	public static int getWindowwidth() {
		return windowWidth;
	}

	public static int getNumberofcandytype() {
		return numberOfCandyType;
	}
}

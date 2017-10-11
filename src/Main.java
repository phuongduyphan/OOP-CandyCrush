import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main controller of the game.
 *
 */
public class Main extends Application {
	// Windows Properties
	String screenTitle = "Candy Crush";
	/** For window's size, game board's size, candy sprite please go to GameBoard.java*/
	// Gameplay Properties
	private static final int time = 100; //in second

	private static Stage stage;
	private static Scene scene;
	public static GameBoard gameBoard;
	private static Pane headerPane;
	public static HeaderBoardController headerBoard; //headerBoardController
	private static FXMLLoader headerBoardLoader; 
	

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage _stage) throws Exception {
		// Set up Header Board and its controller
		headerBoardLoader = new FXMLLoader(getClass().getResource("HeaderBoard.fxml"));
		headerPane = headerBoardLoader.load();
		headerBoard = headerBoardLoader.getController();
		
		//Set up Game Board
		gameBoard = new GameBoard();
		
		// Set up timer
		TimeHandler.setTime(time);
		
		// Wrap up and display
		VBox root = new VBox();
		root.getChildren().addAll(headerPane, gameBoard.getGameBoardPane());
		stage = _stage;
		stage.setTitle(screenTitle);
		stage.setResizable(false);
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	// To be executed every time the Time Handler updates
	public static void timerAction() {
		headerBoard.setTimeValue(TimeHandler.getTimeLeft());
		gameBoard.getERekt();
	}
	
	// To be executed when the Time Handler runs out
	public static void timerEndAction() {
		System.out.println("ENDGAME");
	}
}

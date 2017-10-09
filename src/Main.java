import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * The Main controller of the game.
 *
 */
public class Main extends Application {
	// Windows Attributes
	String screenTitle = "Candy Crush";
	// Gameplay Attributes
	private static final int time = 10; //in second

	private static Stage stage;
	private static Scene scene;
	private static GameBoard gameBoard;
	private static Pane headerPane;
	private static HeaderBoardController headerBoardController;
	private static FXMLLoader headerBoardLoader; 
	

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage _stage) throws Exception {
		// Set up Header Board and its controller
		headerBoardLoader = new FXMLLoader(getClass().getResource("HeaderBoard.fxml"));
		headerPane = headerBoardLoader.load();
		headerBoardController = headerBoardLoader.getController();
		
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
		headerBoardController.setTimeValue(TimeHandler.getTimeLeft());
		gameBoard.getERekt();
	}
	
	// To be executed when the Time Handler runs out
	public static void timerEndAction() {
		System.out.println("ENDGAME");
	}
}

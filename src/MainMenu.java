import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class MainMenu {

	@FXML
	private ChoiceBox<String> sizeChoiceBox;

	@FXML
	private ImageView playButton;
	private Pane mainMenuPane;

	public MainMenu() throws IOException {
		FXMLLoader mainMenuLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		mainMenuLoader.setController(this);
		mainMenuPane = mainMenuLoader.load();
		playButton.setOnMouseClicked(e -> {
			playAction();
		});
		sizeChoiceBox.getItems().addAll("Small", "Medium", "Huge");
		sizeChoiceBox.setValue("Small");
	}

	public Pane getMainMenuPane() {
		return mainMenuPane;
	}

	/**
	 * Pass the chosen difficulty to main
	 * @see Main.playButton();
	 */
	private void playAction() {
		int diff = 0;
		switch (sizeChoiceBox.getValue()) {
		case "Small":
			diff = 0;
			break;
		case "Medium":
			diff = 1;
			break;
		case "Huge":
			diff = 2;
			break;
		default:
			diff = 0;
		}
		Main.playButton(diff);
	}

}

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class HeaderBoard {
	@FXML
	private Label timeValue;
	@FXML
	private Label scoreValue;
	private Pane headerPane;

	public HeaderBoard() throws IOException {
		FXMLLoader headerBoardLoader = new FXMLLoader(getClass().getResource("HeaderBoard.fxml"));
		headerBoardLoader.setController(this);
		headerPane = headerBoardLoader.load();
		headerPane.setPrefWidth(Main.getWindowwidth());
		headerPane.setPrefHeight(100);
	}

	public void setTimeValue(String str) {
		timeValue.setText(str);
	}

	public void setTimeValue(int str) {
		timeValue.setText(Integer.toString(str));
	}

	public void setScoreValue(String str) {
		scoreValue.setText(str);
	}

	public void setScoreValue(int str) {
		scoreValue.setText(Integer.toString(str));
		System.out.println("score " + str);
	}

	public javafx.scene.layout.Pane getHeaderPane() {
		return headerPane;
	}
}

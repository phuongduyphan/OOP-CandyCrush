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

	public HeaderBoard() {
		FXMLLoader headerBoardLoader = new FXMLLoader(getClass().getResource("HeaderBoard.fxml"));
		headerBoardLoader.setController(this);
		try {
			headerPane = headerBoardLoader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public javafx.scene.layout.Pane getHeaderPane() {
		return headerPane;
	}

	public void setScoreValue(int str) {
		scoreValue.setText(Integer.toString(str));
		System.out.println("score " + str);
	}

	public void setScoreValue(String str) {
		scoreValue.setText(str);
	}

	public void setTimeValue(int str) {
		timeValue.setText(Integer.toString(str));
	}

	public void setTimeValue(String str) {
		timeValue.setText(str);
	}
}
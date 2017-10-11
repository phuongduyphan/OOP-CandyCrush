/**
 * The Header Pane's controller class.
 *
 */
import javafx.scene.control.Label;

public class HeaderBoardController {

    public Label timeValue, scoreValue;
    
    public void setTimeValue(String str) {
    	timeValue.setText(str);
    }

    public void setScoreValue(String str) {
    	scoreValue.setText(str);
    }
}

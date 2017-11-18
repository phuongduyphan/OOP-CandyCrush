import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;

public class TimeHandler {
	private int updateInterval;
	private int initialDelay;
	private int timeLeft;
	private Timer timer;

	public TimeHandler(int time) {
		timeLeft = time;
		updateInterval = Main.getTimerupdateinterval();
		initialDelay = Main.getTimerinitialdelay();
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Platform.runLater(() -> timerAction());
			}
		}, initialDelay, updateInterval);
	}

	/**
	 * Convert the current time into String
	 * 
	 * @return mm:ss
	 */
	public String toString() {
		Integer min = new Integer(timeLeft / 60);
		Integer sec = new Integer(timeLeft % 60);
		return Integer.toString(min) + ":" + Integer.toString(sec);
	}

	/**
	 * Is called every updateInterval
	 * 
	 * @see updateInterval
	 */
	private void timerAction() {
		Main.timerAction();
		if (timeLeft == 0) {
			timer.cancel();
			Main.timerEndAction();
		}
		--timeLeft;
	}
}
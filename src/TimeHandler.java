import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

/**
 * A class which handles time counting down.
 * Only one instance can be created.
 * 
 * @Constructor TimeHandler(int t)
 * to begin counting down from t millisecond
 * 
 *  @getTimeLeft() <- static method
 *  return a string represents the time left in the form of m:s
 *
 */
public class TimeHandler {
	static int interval;
	static int period = 1000;
	static int initialDelay = 1000;
	private static int timeLeft;
	private static Timer timer;

	public static void setTime(int time) {
		TimeHandler.timeLeft = time;
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Platform.runLater(() -> timerAction());
			}
		}, initialDelay, period);
	}
	
	public static String getTimeLeft() {
		Integer min = new Integer(timeLeft/60);
		Integer sec = new Integer(timeLeft%60);
		return Integer.toString(min) + ":" + Integer.toString(sec);
	}

	//to be executed every period.
	private static void timerAction() {
		Main.timerAction();
		if (timeLeft == 0) {
			timer.cancel();
			Main.timerEndAction();
		}
		--timeLeft;
	}
}
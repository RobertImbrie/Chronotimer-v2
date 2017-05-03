package src;
import java.text.DecimalFormat;

public class Time {
	private long startTime; // Stores TimeStarted

	//Constructs a new time class and records the start time in nanotime
	public Time() {
		startTime = System.nanoTime();
	}

	//Updates the startTime value to be the current nanoTime
	public void ToggleTimer() {
		startTime = System.nanoTime();
	}

	//Parses the currentTime into seconds
	public String parseTime() { // Parse current time
		String result;
		final double seconds = ((double) this.getTime() / 1000000000);
		result = new DecimalFormat("#.###").format(seconds) + " Seconds";
		return result;
	}

	//Parses a  long val to time.
	public static String parseTime(long a) { // Parse any integer
		String result;
		final int hours = (int) a/3600000000000;
		final int minutes = (int) (a%3600000000000)/60000000000;
		final int seconds = (int) ((a%3600000000000)%60000000000)/1000000000;
		final int milliseconds = (int) (((a%3600000000000)%60000000000)%1000000000)/1000000;
		result = hours+":"+minutes+":"+seconds+"."+milliseconds;
		return result;
	}
	
	//Returns the current time as a long.
	public long getTime() {
		return System.nanoTime() - startTime;
	}
}

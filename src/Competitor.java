package src;

import java.io.BufferedWriter;
import java.io.IOException;

public class Competitor {
	private int bibNum;
	private long startTime;
	private long endTime;
	private boolean started; // True if started running false if not
	private boolean finished; // True if finsihed false if still running or DNF
	private int lane;
	BufferedWriter logWriter = null;

	/**
	 * the constructor that creates the competitor, with a bib#. Sets the start
	 * and end times to default values (-1) and started and finished to false;
	 */
	public Competitor(int bib) {
		bibNum = bib;
		startTime = -1;
		endTime = -1;
		started = false;
		finished = false;
		setLane(0);
	}
	
	public Competitor(int bib, BufferedWriter log) {
		bibNum = bib;
		startTime = -1;
		endTime = -1;
		started = false;
		finished = false;
		setLane(0);
		logWriter = log;
	}
	
	private void debug(String s){
		String msg = "Competitor - " + s;
		if(logWriter != null){
			try {
				logWriter.write(msg + "\n");
			} catch (IOException e) {
				System.out.println(msg);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Called when the competitor starts their run. Saves the time sets started
	 * to true.
	 */
	public void start(long t) {
		if (t < 0) {
			startTime = -1;
			started = false;
			return;
		}
		this.startTime = t;
		started = true;
	}

	/**
	 * Called when the competitor ends their run. Saves the time sets finsihed
	 * to true if finishing time is greater than start time. Else assumes DNF
	 * and sets finishing time to -1 and finished to false to signify DNF
	 */
	public void end(long time) {
		if (!started) {
			endTime = -1;
			finished = false;
		} else if (startTime > time) {
			this.endTime = -1;
			finished = false;
		} else if (time < 0) {
			endTime = -1;
			finished = false;
		} else {
			this.endTime = time;
			finished = true;
		}
	}

	/**
	 * resets the run of the competitor, returns the start and end time to a
	 * defult value, and sets started and finished to false. The bib Number will
	 * remain intact.
	 */
	public void reset() {
		startTime = -1;
		endTime = -1;
		started = false;
		finished = false;
	}

	/**
	 * provides the difference in the start time and end time
	 * 
	 * @return long - The total time of the run
	 */
	public long runTime() {
		if (!started || !finished)
			return -1;
		if (startTime == -1 || endTime == -1)
			return -1;
		return endTime - startTime;
	}

	/**
	 * overrides the toString for now: [bib:<bibNum>,start:<start>,end:
	 * <end>,final:]
	 * 
	 * @return String - String that represents this competitor
	 */
	@Override
	public String toString() {
		if ((started == true) && (finished == true)) {
			String finalTime = Time.parseTime(endTime - startTime);
			return "Competitor: " + bibNum + " --- " + finalTime;
		} else if ((started == true && finished == false)) {
			return "Competitor: " + bibNum + " --- DNF";
		} else {
			return "Competitor: " + bibNum + " --- Has Not Started";
		}
	}

	/* FOLLOWING METHODS ONLY USED FOR TESTING */
	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public int getBibNum() {
		return bibNum;
	}

	public boolean getStarted() {
		return started;
	}

	public boolean getFinished() {
		return finished;
	}

	public void setStartedTrue() {
		started = true;
	}

	public void setFinishedTrue() {
		finished = true;
	}

	public void setStartedFalse() {
		started = false;
	}

	public void setFinishedFalse() {
		finished = false;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}
}
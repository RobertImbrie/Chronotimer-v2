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
	transient BufferedWriter logWriter;

	/**
	 * Competitor Constructor:
	 * The constructor that creates the competitor, with a bib#. Sets the start
	 * and end times to default values (-1) and started and finished to false.
	 */
	public Competitor(int bib) {
		bibNum = bib;
		startTime = -1;
		endTime = -1;
		started = false;
		finished = false;
		setLane(0);
	}
	
	/**
	 * Competitor Constructor:
	 * The constructor that creates the competitor, with a bib#. Sets the start
	 * and end times to default values (-1) and started and finished to false,
	 * adds a BufferedWriter in to print for same file. Used for testing purposes. 
	 */
	public Competitor(int bib, BufferedWriter log) {
		bibNum = bib;
		startTime = -1;
		endTime = -1;
		started = false;
		finished = false;
		setLane(0);
		logWriter = log;
		this.debug(bib + " constructed.");
	}
	
	/**
	 * Competitor Debug:
	 * Debug method used for testing and following program calls through the system.
	 */
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
	 * Competitor Start:
	 * Called when a competitor starts their race. Sets started to true and stores the long value
	 * as start time. returns void.
	 */
	public void start(long t) {
		if (t >= 0L) {
			this.startTime = t;
			started = true;
		}
		this.debug(this.getBib() + " started at " + t);
	}

	/**
	 * Competitor End:
	 * Called when the competitor ends their run. Saves the time sets finsihed
	 * to true if finishing time is greater than start time. Else assumes DNF
	 * and sets finishing time to -1 and finished to false to signify DNF
	 * NOTE: DNF are fed in using *.end(-1);
	 */
	public void end(long time) {
		if (started && !finished && startTime <= time && time >= 0) {
			endTime = time;
			finished = true;
		}
		this.debug(this.getBib() + " ended at " + t);
	}

	/**
	 * Competitor Reset:
	 * Resets the run of the competitor, returns the start and end time to a
	 * defult value, and sets started and finished to false. The bib Number will
	 * remain intact.
	 */
	public void reset() {
		startTime = -1;
		endTime = -1;
		started = false;
		finished = false;
		this.debug(this.getBib() + " reset.");
	}

	/**
	 * Competitor runTime:
	 * Returns the total run time of a finished competitor. If a competitor has not finished
	 * then will return -1.
	 */
	public long runTime() {
		this.debug(this.getBib() + " giving run time.");
		if (!started || !finished)
			return -1;
		if (startTime == -1 || endTime == -1)
			return -1;
		return endTime - startTime;
	}

	/**
	 * Competitor toString:
	 * Returns competitor info as a string value in a format that can be displayed.
	 * In format: Competitor: <bibNum> --- <time>
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

	/*GETTERS AND SETTERS */
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

	public void setStarted(boolean b) {
		started = b;
	}

	public void setFinished(boolean b) {
		finished = b;
	}

	public int getLane() {
		return lane;
	}

	public void setLane(int lane) {
		this.lane = lane;
	}
	
	public void setBib(int i){
		bibNum = i;
	}
}

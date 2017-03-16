package src;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class ParaInd extends Race {
	ArrayList<Competitor> competitors;
	int curComp1, curComp2, nextComp;
	boolean laneOneHasStarted, laneTwoHasStarted;

	/**
	 * the constructor that creates the run, with default values
	 */
	public ParaInd() {
		competitors = new ArrayList<Competitor>();
		curComp1 = -1;
		curComp2 = -1;
		nextComp = 0;
	}
	
	/**
	 * the constructor that creates the run, with default values, with log writer
	 */
	public ParaInd(BufferedWriter log) {
		competitors = new ArrayList<Competitor>();
		curComp1 = -1;
		curComp2 = -1;
		nextComp = 0;
		logWriter = log;
	}
	
	//@Override		//private methods are not inharited
	private void debug(String s){
		String msg = "ParaInd - " + s;
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
	 * calls start if channel 1 or 3, and the appropriate lane has not started
	 * calls end if channel 2 or 4, and the appropriate lane has started
	 *
	 * @param channel
	 *            the channel that was triggered
	 * @param time
	 *            the time that the sensor was triggered
	 */
	public void trigger(int channel, long time) {
		if (channel == 1 && !laneOneHasStarted) {
			competitors.get(nextComp).start(time);
			curComp1 = nextComp;
			nextComp++;
			laneOneHasStarted = true;
			competitors.get(curComp1).setLane(1);
		} else if (channel == 3 && !laneTwoHasStarted) {
			competitors.get(nextComp).start(time);
			curComp2 = nextComp;
			nextComp++;
			competitors.get(curComp2).setLane(2);
			laneTwoHasStarted = true;
		} else if (channel == 2 && laneOneHasStarted) {
			competitors.get(curComp1).end(time);
			laneOneHasStarted = false;
		} else if (channel == 4 && laneTwoHasStarted) {
			competitors.get(curComp2).end(time);
			laneTwoHasStarted = false;
		}
	}

	/**
	 * provides a list of the competitors for this race
	 * 
	 * @param bib
	 *            the bib number of the competitor
	 * @return ArrayList<String> - The list of formatted strings that represent
	 *         the competitors
	 */
	public ArrayList<String> competitorList() {
		ArrayList<String> comps = new ArrayList<String>();
		for (int i = 0; i < competitors.size(); i++) {
			comps.add(competitors.get(i).toString());
		}
		return comps;
	}

	/**
	 * adds a new competitor to the list of competitors, does not add a
	 * competitor if the a competitor already exists with the same bib number
	 * 
	 * @param bib
	 *            the bib number of the competitor
	 * @return true if the competitor was added, false otherwise
	 */
	public boolean addCompetitor(int bib) {
		for (int i = 0; i < competitors.size(); i++) {
			if (competitors.get(i).getBibNum() == bib)
				return false;
		}
		competitors.add(new Competitor(bib));
		return true;
	}

	/**
	 * clears the list of competitors
	 * 
	 * @param
	 * @return String[] - a formatted list of strings of the competitors that
	 *         were in the list
	 */
	public String[] clear() {
		String[] listOfCompetitors = new String[competitors.size()];
		for (int i = 0; i < competitors.size(); i++) {
			listOfCompetitors[i] = competitors.get(i).toString();
		}
		competitors.clear();
		curComp1 = -1;
		curComp2 = -1;
		nextComp = 0;
		return listOfCompetitors;
	}

	/**
	 * finds a competitor with a matching bib and removes them from the list
	 * 
	 * @param bib
	 *            the bib number of the competitor
	 * @return String - A formatted string that represents the competitor
	 */
	public String removeCompetitorByBib(int bib) {
		for (int i = 0; i < competitors.size(); i++) {
			if (competitors.get(i).getBibNum() == bib) {
				if (nextComp > i) {
					nextComp --;
				}
				if (curComp1 > i) {
					curComp1 --;
				}
				if (curComp2 > i) {
					curComp2 --;
				}
				return competitors.remove(i).toString();
			}
		}
		return null;
	}

	/**
	 * removes the competitor in the position specified
	 * 
	 * @param position
	 *            - the zero indexed position of the competitor to be removed
	 * @return String - A formatted string that represents the competitor
	 */
	public String removeCompetitorByPos(int position) {
		if (competitors.size() <= position) {
			return null;
		}
		if (curStart > position) {
			curStart--;
			curFinish--;
		}
		String s = competitors.get(position).toString();
		competitors.remove(position);
		return s;
	}

	/**
	 * the current competitor is moved to the back of the queue, and the next
	 * competitor becomes the current
	 * 
	 * @return String[] - A list of formatted strings that represents the
	 *         competitors
	 */

	/*
	 * public String[] swapNext() { // TODO }
	 */

	/**
	 * indicates that the current competitor did not finish their run
	 */
	public void didNotFinish() {
		competitors.get(curFinish).end(-1);
		if (!competitors.get(curFinish).getStarted()) {
			competitors.get(curFinish).start(0);
		}
		if (curFinish == curStart)
			curStart++;
		curFinish++;
	}

	/**
	 * sets the start time of the current competitor
	 * 
	 * @param l
	 *            - the time the trigger was fired
	 * @return
	 */
	public void start(int channel, long l) {
		competitors.get(curStart).start(l);
		curStart++;
	}

	/**
	 * sets the time of the competitor at a specified position
	 * 
	 * @param t
	 *            - a newly created Time object containing the time the the
	 *            trigger was fired
	 * @param position
	 *            - the zero index position of the competitor to act on
	 */
	public void start(long t, int position) {
		competitors.get(position).start(t);
	}

	/**
	 * ends the run of the current competitor, send in null for DNF, this will
	 * return the duration of the run or null if the competitor did not finish,
	 * or -1 if there is not start time(the end time will be recorded)
	 * 
	 * @param t
	 *            - a newly created Time object containing the time the the
	 *            trigger was fired
	 * @return Time - the difference of time from the start to the finish
	 */
	public void end(long l) {
		if (curFinish <= curStart) {
			competitors.get(curFinish).end(l);
			curFinish++;
		}
	}

	/**
	 * ends the run of the current specified, send in null for DNF, thsi will
	 * return the duration of the run or null if the competitor did not finish,
	 * or -1 if there is not start time(the end time will be recorded)
	 * 
	 * @param t
	 *            - a newly created Time object containing the time the the
	 *            trigger was fired
	 * @param position
	 *            - the zero index position of the competitor to act on
	 */
	public void end(long t, int position) {
		if (curFinish == position)
			curFinish++;
		competitors.get(position).end(t);
	}

	/**
	 * resets the run of the current competitor, returns the start and end time
	 * to a default value, the bib Number will remain intact
	 * 
	 * @return long[3] - a three element array of Long containing the start
	 *         time, end time, and duration
	 */
	public long[] reset() {
		if (curStart == curFinish) {
			curStart--;
			curFinish--;
		} else {
			curStart--;
		}
		Competitor c = competitors.get(curFinish);

		long comp[] = new long[3];
		comp[0] = c.getStartTime();
		comp[1] = c.getEndTime();
		comp[2] = c.runTime();
		c.reset();
		return comp;
	}

	/**
	 * provides the difference in the start time and end time of the current
	 * specified
	 * 
	 * @param position
	 *            - the zero index position of the competitor to act on
	 * @return Time - The total time of the run
	 * @see
	 */
	public long runTime(int position) {
		return competitors.get(position).runTime();
	}

	/**
	 * provides the XML for this run
	 * 
	 * @return String - the XML that represents this run
	 * @see Image
	 */
	public String toXML() {
		// TODO
		return null;
	}

	/* FOLLOWING METHODS USED FOR TESTING ONLY */

	public ArrayList<Competitor> getCompetitors() {
		return competitors;
	}

	@Override
	public String toString() {
		String out = "";
		for (int i = 0; i < competitors.size(); i++) {
			out = out + "\t" + competitors.get(i).toString() + "\n";
		}
		return out;
	}
}

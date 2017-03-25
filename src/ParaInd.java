package src;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	 * the constructor that creates the run, with default values, with log
	 * writer
	 */
	public ParaInd(BufferedWriter log) {
		competitors = new ArrayList<Competitor>();
		curComp1 = -1;
		curComp2 = -1;
		nextComp = 0;
		logWriter = log;
	}

	// @Override //private methods are not inharited
	private void debug(String s) {
		String msg = "ParaInd - " + s;
		if (logWriter != null) {
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
		competitors.add(new Competitor(bib, logWriter));
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
					nextComp--;
				}
				if (curComp1 > i) {
					curComp1--;
				}
				if (curComp2 > i) {
					curComp2--;
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
		} else {
			competitors.remove(position);
			if (position < curComp1) {
				curComp1--;
			}
			if (position < curComp2) {
				curComp2--;
			}
		}
		String s = competitors.get(position).toString();
		competitors.remove(position);
		return s;
	}

	/**
	 * indicates that the current competitor did not finish their run
	 * 
	 * @param i
	 *            for lane
	 */
	public void didNotFinish(int lane) {
		if (lane == 1) {
			competitors.get(curComp1).end(-1);
		} else if (lane == 2) {
			competitors.get(curComp2).end(-1);
		}
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

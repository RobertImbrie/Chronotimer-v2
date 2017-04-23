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
	@SuppressWarnings("unused")
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
	 * @param channels
	 *            the channel that was triggered
	 * @param time
	 *            the time that the sensor was triggered
	 */
	public void trigger(int channel, long time) {
		if (nextComp + 1 < competitors.size() + 1) {
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
			}
		}

		if (channel == 2 && laneOneHasStarted && curComp1 < competitors.size() && curComp1 > -1) {
			competitors.get(curComp1).end(time);
			laneOneHasStarted = false;
		} else if (channel == 4 && laneTwoHasStarted && curComp2 < competitors.size() && curComp2 > -1) {
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
		laneOneHasStarted = false;
		laneTwoHasStarted = false;
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
		if (competitors.size() - 1 == 0) {
			String s = competitors.get(0).toString();
			competitors.remove(position);
			this.clear();
			return s;
		} else if (competitors.size() < position) {
			return null;
		} else {
			if (position < curComp1) {
				curComp1--;
			}
			if (position < curComp2) {
				curComp2--;
			}
			if (position < nextComp) {
				nextComp--;
			}
		}
		String s = competitors.get(position).toString();
		competitors.remove(position);
		return s;
	}

	/**
	 * 
	 */
	public long[] reset() {
		for (int i = 0; i < competitors.size(); i++) {
			competitors.get(i).reset();
		}
		curComp1 = -1;
		curComp2 = -1;
		nextComp = 0;
		laneOneHasStarted = false;
		laneTwoHasStarted = false;
		return null;
	}

	/**
	 * indicates that the current competitor did not finish their run
	 * 
	 * @param i
	 *            for lane
	 * @return
	 */

	public void didNotFinish(int lane) {
		if (lane == 1 && curComp1 > -1 && curComp1 < competitors.size()) {
			competitors.get(curComp1).end(-1);
		} else if (lane == 2 && curComp2 > -1 && curComp2 < competitors.size()) {
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

	public String toDisplay(long currentTime) {
		// GET HEADER
		String out = "CURRENTLY RUNNING RACE: PARALELL INDIVIDUAL";
		// DISPLAY START TIME OF RACE
		if (competitors.isEmpty() && !competitors.get(0).getStarted()) {
			out = out + "\n\n\t Race has not started yet.";
		} else {
			out = out + "\n\tRace Start Time: " + Time.parseTime(competitors.get(0).getStartTime());
			out = out + "\n\tCurrent Race Time: " + Time.parseTime(currentTime);
		}
		if (nextComp < competitors.size()) {
			out = out + "\n\n\tNext Start: " + competitors.get(nextComp).getBibNum();
		} else {
			out = out + "\n\n\tNext Racer: No Racer next.";
		}

		if (curComp1 < competitors.size()) {
			out = out + "\n\n\tCurrent racer lane 1: " + competitors.get(curComp1).getBibNum();
		} else {
			out = out + "\n\n\tCurrent racer lane 1: No racer to finish.";
		}

		if (curComp2 < competitors.size()) {
			out = out + "\n\n\tCurrent racer lane 2: " + competitors.get(curComp2).getBibNum();
		} else {
			out = out + "\n\n\tCurrent racer lane 2: No racer to finish.";
		}

		if (curComp1 - 1 < competitors.size() && (curComp1 - 1 > 0)) {
			out = out + "\n\n\tMost recent finish in lane 1: " + competitors.get(curComp1 - 1).getBibNum();
			out = out + "\n\n\t\tTime: " + Time.parseTime(competitors.get(curComp1 - 1).runTime());
		} else {
			out = out + "\n\n\tMost recent finish in lane 1: No racer to finish.";
			out = out + "\n\n\t\tTime: NA";
		}
		if (curComp2 - 1 < competitors.size() && (curComp2 - 1 > 0)) {
			out = out + "\n\n\tMost recent finish in lane 2: " + competitors.get(curComp2 - 1).getBibNum();
			out = out + "\n\n\t\tTime: " + Time.parseTime(competitors.get(curComp2 - 1).runTime());
		} else {
			out = out + "\n\n\tMost recent finish in lane 2: No racer to finish.";
			out = out + "\n\n\t\tTime: NA";
		}
		return out;
	}
}

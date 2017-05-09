package src;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Ind extends Race {
	int curStart;
	int curFinish;
	long startTime = -1;

	/**
	 * Ind Constructor: the constructor creates the run, with default values.
	 */
	public Ind() {
		competitors = new ArrayList<Competitor>();
		curStart = 0;
		curFinish = 0;
	}

	/**
	 * Ind Constructor: the constructor creates the run, with default values and
	 * a BufferedWriter
	 */
	public Ind(BufferedWriter log) {
		competitors = new ArrayList<Competitor>();
		curStart = 0;
		curFinish = 0;
		logWriter = log;
	}

	/**
	 * Ind Tigger: Handles the trigger pass ins from the main class. Only takes
	 * values on 1 or two. If first trigger, sets startTime to the time value
	 * passed in, then 1 starts the next racer and 2 stops them.
	 */
	@Override
	public void trigger(int channel, long time) {
		if (startTime == -1){
			debug("Race startTime recorded: " + time);
			startTime = time;
		}
		if (channel == 1) {
			debug("Racer start recorded: " + time);
			start(time - startTime);
		} else if (channel == 2) {
			debug("Racer end recorded: " + time);
			end(time - startTime);
		}
	}

	/**
	 * Ind Debug: Gives output statements that allow for us to debug code.
	 * Private method.
	 */
	private void debug(String s) {
		String msg = "Ind - " + s;
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
	 * Ind competitorList: Returns an arraylist that contains all the
	 * competitors.
	 */
	@Override
	public ArrayList<String> competitorList() {
		ArrayList<String> comps = new ArrayList<String>();
		for (int i = 0; i < competitors.size(); i++) {
			comps.add(competitors.get(i).toString());
		}
		return comps;
	}

	/**
	 * Ind add: adds a competitor as long as bib number is not previously in the
	 * list.
	 */
	@Override
	public boolean addCompetitor(int bib) {
		for (int i = 0; i < competitors.size(); i++) {
			if (competitors.get(i).getBibNum() == bib)
				return false;
		}
		competitors.add(new Competitor(bib, logWriter));
		return true;
	}

	/**
	 * Ind clear: clears the competitor list and returns a String[] containing
	 * all of the competitors that have been added.
	 */
	@Override
	public String[] clear() {
		String[] listOfCompetitors = new String[competitors.size()];
		for (int i = 0; i < competitors.size(); i++) {
			listOfCompetitors[i] = competitors.get(i).toString();
		}
		competitors.clear();
		curStart = 0;
		curFinish = 0;
		return listOfCompetitors;
	}

	/**
	 * Ind removeCompetitorByBib: Removes competitor by a fed in bib number.
	 * Returning the competitor info as a string if successful and null if it
	 * fails.
	 */
	@Override
	public String removeCompetitorByBib(int bib) {
		for (int i = 0; i < competitors.size(); i++) {
			if (competitors.get(i).getBibNum() == bib) {
				if (curStart > i) {
					curStart--;
					curFinish--;
				}
				return competitors.remove(i).toString();
			}
		}
		return null;
	}

	/**
	 * Ind removeCompetitorByPos: Removes competitor by a fed in 0-position in
	 * the array. Returning the competitor info as a string if successful and
	 * null if it fails.
	 */
	@Override
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
	 * Ind didNotFinish: Denotes that the current competitor did not finish
	 * their race, and sets values accordingly.
	 */
	@Override
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
	 * Ind start: Sets the start time of the curStart competitor.
	 */
	@Override
	public void start(long time) {
		if (curStart < competitors.size()) {
			competitors.get(curStart).start(time);
			curStart++;
		}
	}

	/**
	 * Ind start: Starts the competitor in the competitors array at the current
	 * position.
	 */
	public void start(long t, int position) {
		if (position < competitors.size()) {
			competitors.get(position).start(t);
		}
	}

	/**
	 * Ind end: Ends the next competitor to finish.
	 */
	@Override
	public void end(long l) {
		if (curFinish <= curStart) {
			competitors.get(curFinish).end(l);
			curFinish++;
		}
	}

	/**
	 * Ind end: Ends the next competitor to finish, given a position.
	 */
	public void end(long t, int position) {
		if (position <= curStart) {
			competitors.get(position).end(t);
		}
	}

	/**
	 * Ind reset: resets the run of the current competitor, returns the start
	 * and end time to a default value, the bib Number will remain intact.
	 * Returns a three element array of Long containing the start time, end
	 * time, and duration.
	 */
	@Override
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
	 * Ind runTime: returns the runtime as a long for the competitor at the
	 * given 0-index position.
	 */
	@Override
	public long runTime(int position) {
		return competitors.get(position).runTime();
	}

	/**
	 * Ind getCompetitors: the full arrayList of competitors.
	 */
	public ArrayList<Competitor> getCompetitors() {
		return competitors;
	}

	/**
	 * Ind toString: returns all competitors as a formatted string.
	 */
	@Override
	public String toString() {
		String out = "";
		for (int i = 0; i < competitors.size(); i++) {
			out = out + "\t" + competitors.get(i).toString() + "\n";
		}
		return out;
	}

	/**
	 * Ind toDisplay: Returns a formatted string to be displayed on the program's display pop up.
	 */
	@Override
	public String toDisplay(long currentTime) {
		// GET HEADER
		String out = "CURRENTLY RUNNING RACE: INDIVIDUAL";
		// DISPLAY START TIME OF RACE
		if (competitors.isEmpty() && !competitors.get(0).getStarted()) {
			out = out + "\n\n\t Race has not started yet.";
		} else {
			out = out + "\n\tRace Start Time: " + Time.parseTime(offsetTime);
			out = out + "\n\tCurrent Race Time: " + Time.parseTime(currentTime - startTime + offsetTime);
		}
		if (curStart < competitors.size()) {
			out = out + "\n\n\tNext Start: " + competitors.get(curStart).getBibNum();
		} else {
			out = out + "\n\n\tNext Racer: No Racer next.";
		}

		if (curFinish < competitors.size()) {
			out = out + "\n\n\tCurrent racer: " + competitors.get(curFinish).getBibNum();
		} else {
			out = out + "\n\n\tCurrent racer: No racer to finish.";
		}

		if (curFinish - 1 < competitors.size() && (curFinish - 1 > 0)) {
			out = out + "\n\n\tMost recent finish: " + competitors.get(curFinish - 1).getBibNum();
			out = out + "\n\n\t\tTime: " + Time.parseTime(competitors.get(curFinish - 1).runTime());
		} else {
			out = out + "\n\n\tMost recent finish: No racer to finish.";
			out = out + "\n\n\t\tTime: NA";

		}
		return out;
	}
}

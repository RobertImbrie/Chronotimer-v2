package src;

import java.io.BufferedWriter;
import java.util.ArrayList;

public class ParGrp extends Race {
	private boolean started;
	private boolean finished;
	private int compCount;
	private Competitor[] lastFinished;
	private long startTime;
	private long finishTime;

	/**
	 * ParGrp Constructor: Makes new race with initial values.
	 */
	public ParGrp() {
		started = false;
		startTime = -1;
		finished = false;
		finishTime = -1;
		compCount = 0;
		lastFinished = new Competitor[3];
		competitors = new ArrayList<Competitor>();
	}

	public ParGrp(BufferedWriter logWriter) {
		started = false;
		startTime = -1;
		finished = false;
		finishTime = -1;
		compCount = 0;
		this.logWriter = logWriter;
		lastFinished = new Competitor[3];
		competitors = new ArrayList<Competitor>();
	}

	/**
	 * PARGRP addCompetitor: Adds competitor if bib is not currently being used
	 * and if there are less than 8 racers.
	 */
	@Override
	public boolean addCompetitor(int bib) {
		// Check race hasn't started and there are less than 8 competitors
		if (started == true || competitors.size() >= 8) {
			return false;
		}
		// Check bib number hasn't been used.
		for (int i = 0; i < competitors.size(); i++) {
			if (bib == competitors.get(i).getBibNum()) {
				return false;
			}
		}
		// Otherwise create competitor and assign next lane.
		Competitor temp = new Competitor(bib);
		temp.setLane(competitors.size() + 1);
		competitors.add(temp);
		return true;
	}

	/**
	 * PARGRP trigger: Takes an int and a 1-index channel and sends in a time.
	 * Saves this time as either a start time or an end time depending on race
	 * status. The finish is recorded by channel.
	 */
	@Override
	public void trigger(int channel, long time) {
		// Check if race has started, if not fill all racers startTime with time
		if (channel == 1 && started == false) {
			started = true;
			startTime = time;
			for (int i = 0; i < competitors.size(); i++) {
				competitors.get(i).setStarted(true);
				competitors.get(i).start(time);
			}
			// If race has started and competitor has not finished and channel.
			// is currently running, end the racer on that channel.
		} else if (started == true && (channel - 1) < competitors.size()) {
			if (competitors.get(channel - 1).getFinished() == false) {
				competitors.get(channel - 1).end(time);
				competitors.get(channel - 1).setFinished(true);
				compCount++;
				if (compCount == competitors.size()) {
					finished = true;
					finishTime = time;
				}
				if (compCount == 1 || compCount == 2 || compCount == 3) {
					lastFinished[compCount - 1] = competitors.get(channel - 1);
				} else if (compCount > 3) {
					lastFinished[0] = lastFinished[1];
					lastFinished[1] = lastFinished[2];
					lastFinished[2] = competitors.get(channel - 1);
				}
			}
		}
	}

	/**
	 * PARGRP competitorList: Returns the competitors as formatted strings.
	 */
	@Override
	public ArrayList<String> competitorList() {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < competitors.size(); i++) {
			result.add(competitors.get(i).toString());
		}
		return result;
	}

	/**
	 * PARGRP clear: Returns a string[] of comeptitors and clears the race to
	 * default settings.
	 */
	public String[] clear() {
		String[] result = new String[competitors.size()];
		for (int i = 0; i < competitors.size(); i++) {
			result[i] = competitors.get(i).toString();
		}
		started = false;
		finished = false;
		compCount = 0;
		competitors.clear();
		startTime = -1;
		finishTime = -1;
		lastFinished[0] = null;
		lastFinished[1] = null;
		lastFinished[2] = null;
		return result;
	}

	/**
	 * PARGRP removeCompetitorByBib: Takes in a bib and removes the competitor
	 * if it is found, returns null if none found and a string if it is.
	 */
	public String removeCompetitorByBib(int bib) {
		String result = null;
		for (int i = 0; i < competitors.size(); i++) {
			if (bib == competitors.get(i).getBibNum()) {
				result = competitors.get(i).toString();
				competitors.remove(i);
			}
		}
		return result;
	}

	/**
	 * PARGRP runTime: returns the run time of the 0-index competitor.
	 */
	public long runTime(int position) {
		if (position < competitors.size() && competitors.get(position).getFinished() == true) {
			return (competitors.get(position).getEndTime() - competitors.get(position).getStartTime());
		} else {
			return -1;
		}
	}

	/**
	 * PARGRP toDisplay: Displays the start time and the last three finishes for
	 * the race as a formatted string.
	 */
	@Override
	public String toDisplay(long currentTime) {
		// GET HEADER
		String out = "CURRENTLY RUNNING RACE: PARALLEL GROUP";
		// DISPLAY START TIME OF RACE
		if (startTime == -1) {
			out = out + "\n\n\t Race has not started yet.";
		} else {
			out = out + "\n\tRace Start Time: " + Time.parseTime(startTime);
			out = out + "\n\tCurrent Race Time: " + Time.parseTime(currentTime);
			out = out + "\n\n\tMost Recent Finishes:";
			if (compCount == 1) {
				out = out + "n\t\t" + lastFinished[0].toString();
			}
			if (compCount == 2) {
				out = out + "n\t\t" + lastFinished[1].toString();
			}
			if (compCount >= 3) {
				out = out + "n\t\t" + lastFinished[2].toString();
			}
		}
		return out;
	}
}

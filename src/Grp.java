package src;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import sun.misc.Queue;

public class Grp extends Race {
	ArrayList<Competitor> competitors;
	Queue<Competitor> finishes;
	boolean hasStarted;
	long startTime;
	int curComp;
	Time t;

	transient BufferedWriter logWriter = null;

	public Grp() {
		competitors = new ArrayList<Competitor>();
		hasStarted = false;
		startTime = -1;
		finishes = new Queue<Competitor>();
		curComp = 0;
	}
	
	public Grp(BufferedWriter log) {
		competitors = new ArrayList<Competitor>();
		hasStarted = false;
		startTime = -1;
		finishes = new Queue<Competitor>();
		curComp = 0;
		logWriter = log;
	}

	public void trigger(int channel, long time) {
		if (channel == 1 && !hasStarted) {
			startTime = time;
			hasStarted = true;
		} else if (channel == 2 && hasStarted) {
			if (curComp < 9999) {
				Competitor temp = new Competitor(Integer.parseInt(String.format("%04d", curComp)));
				temp.start(startTime);
				temp.end(time);
				curComp++;
				competitors.add(temp);
				finishes.enqueue(temp);
			}
		} else {
			return;
		}
	}

	//TODO Are these used anywhere?? REMOVE SUPRESSION WARNING WHEN IT IS IN ALL RACE CLASSES
	@SuppressWarnings("unused")
	private void debug(String s) {
		String msg = "GRP Race - " + s;
		if (logWriter != null) {
			try {
				logWriter.write(msg + "\n");
			} catch (IOException e) {
				System.out.println(msg);
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> competitorList() {
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < competitors.size(); i++) {
			result.add(competitors.get(i).toString());
		}
		return result;
	}

	// IN GROUP USED TO ASSIGN BIBS TO FINISHES SINCE COMPETITORS ARE MADE AS
	// FINISHES ARE ENTERED
	public boolean addCompetitor(int bib) {
		if (!finishes.isEmpty()) {
			for(int i = 0; i < competitors.size(); i++){
				if(competitors.get(i).getBibNum() == bib){
					return false;
				}
			}
			Competitor temp = null;
			try {
				temp = finishes.dequeue();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (temp != null) {
				temp.setBib(bib);
				return true;
			}
		}
		return false;
	}

	public String[] clear() {
		String[] result = new String[competitors.size()];
		for (int i = 0; i < competitors.size(); i++) {
			result[i] = competitors.get(i).toString();
		}
		competitors.clear();
		finishes = new Queue<Competitor>();
		return result;
	}

	public long runTime(int position) {
		return competitors.get(position).runTime();
	}

	@Override
	public String toString() {
		String out = "";
		for (int i = 0; i < competitors.size(); i++) {
			out = out + "\t" + competitors.get(i).toString() + "\n";
		}
		return out;
	}
	
	@Override
	public String toDisplay(long currentTime) {
		//GET HEADER
		String out = "CURRENTLY RUNNING RACE: GROUP";
		//DISPLAY START TIME OF RACE
		if(startTime == -1){
			out = out + "\n\n\t Race has not started yet.";
		} else {
			out = out + "\n\tRace Start Time: " + Time.parseTime(startTime);
			out = out + "\n\tCurrent Race Time: " + Time.parseTime(currentTime);
			out = out + "\n\n\tMost Recent Finishes:";
		}
		if(curComp == 0){
			out = out + "\n\n\t No finishes Recored yet.";
		} else if (curComp  > 1){
			for (int i = curComp; (i > curComp-3) && (i >= 0); i--){
				out = out + "\n\t\t" + competitors.get(i).getBibNum() + ": " + competitors.get(i).runTime();
			}
		}	
		return out;
	}
}

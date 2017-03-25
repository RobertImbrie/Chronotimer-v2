package src;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Race{
	ArrayList<Competitor> competitors;
	transient BufferedWriter logWriter = null;
	
	
	public void trigger(int channel, long time){
		
	}
	
	private void debug(String s){
		String msg = "Race - " + s;
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
	 * provides a list of the competitors for this race
	 * 
	 * @param bib
	 *            the bib number of the competitor
	 * @return ArrayList<String> - The list of formatted strings that represent
	 *         the competitors
	 */
	public ArrayList<String> competitorList() {
		return null;
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
		return false;
	}
	
	/**
	 * clears the list of competitors
	 * 
	 * @param
	 * @return String[] - a formatted list of strings of the competitors that
	 *         were in the list
	 */
	public String[] clear() {
		//return new String[0];
		return null;
	}
	
	/**
	 * finds a competitor with a matching bib and removes them from the list
	 * 
	 * @param bib
	 *            the bib number of the competitor
	 * @return String - A formatted string that represents the competitor
	 */
	public String removeCompetitorByBib(int bib) {
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
		return null;
	}
	
	/**
	 * indicates that the current competitor did not finish their run
	 */
	public void didNotFinish() {
		
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
	public void start(long t) {
		
	}
	
	/**
	 * resets the run of the current competitor, returns the start and end time
	 * to a default value, the bib Number will remain intact
	 * 
	 * @return long[3] - a three element array of Long containing the start time,
	 *         end time, and duration
	 */
	public long[] reset() {
		return null;
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
		return 0;
	}
	
	@Override
	public String toString(){
		String out = "";
		for(int i = 0; i < competitors.size(); i++){
			out = out +  "\t" + competitors.get(i).toString() + "\n";
		}
		return out;
	}
}

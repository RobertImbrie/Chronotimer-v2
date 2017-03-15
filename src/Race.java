package src;

import java.util.ArrayList;

public class Race{
	ArrayList<Competitor> competitors;
	
	
	public void trigger(int channel, long time){
		
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
		return new String[0];
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

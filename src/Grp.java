package src;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class grp extends Race{
	ArrayList<Competitor> competitors;
  Queue<Competitor> finishes;
  
	transient BufferedWriter logWriter = null;
	
  public grp(){
    //TODO
  }
	
	public void trigger(int channel, long time){
		//TODO
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
	
	public ArrayList<String> competitorList() {
    //TODO
	}
	
	public boolean addCompetitor(int bib) {
    //TODO
	}
	
	public String[] clear() {
    //TODO
	}
	
	public String removeCompetitorByBib(int bib) {
    //TODO
	}
	
	public String removeCompetitorByPos(int position) {
    //TODO
	}
	
	public void didNotFinish() {
		//TODO
	}
	
	public void end(long l) {
		//TODO
	}

	public void start(long t) {
		//TODO
	}
	
	public long[] reset() {
		//TODO
	}
	
	public long runTime(int position) {
		//TODO
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

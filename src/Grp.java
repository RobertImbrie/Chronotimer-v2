package src;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

public class grp extends Race{
	ArrayList<Competitor> competitors;
  	Queue<Competitor> finishes;
	boolean hasStarted;
	long startTime;
	int CurComp;
  
	transient BufferedWriter logWriter = null;
	
  	public grp() {
	  	//TODO
	  	competitors = new ArrayList<Competitor>();
	  	hasStarted = false;
	  	startTime = -1;
	  	finishes = new Queue<Competitor>();
	  	curComp = 0;
  	}
	
	public void trigger(int channel, long time){
		if(channel == 1 && !hasStarted){
			startTime = time;
			hasStarted = true;			
		} else if (channel == 2 && hasStarted) {
			if(curComp < 9999){
				Competitor temp = new Competitor(sprintf("%04d", curComp));
				temp.setStart(startTime);
				temp.setFinish(time);
				curComp++;
				competitors.add(temp);
  				finishes.put(temp);
			}
		} else {
			return();	
		}
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
    		
	}
	
	//IN GROUP USED TO ASSIGN BIBS TO FINISHES SINCE COMPETITORS ARE MADE AS FINISHES ARE ENTERED
	public boolean addCompetitor(int bib) {
		if(!finishes.empty()){
			competitor temp = finishes.pop()
    			temp.setBib(bib);
		}
	}
	
	public String[] clear() {
		for(int i = 0; i < competitors.size(); i++){
			
		}
    		competitors.clear();
  		finishes.clear();
	}
	
	public String removeCompetitorByPos(int position) {
    		//TODO
	}
	
	public void didNotFinish() {
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

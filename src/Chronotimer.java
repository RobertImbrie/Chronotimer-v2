package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import com.google.gson.Gson;
//import com.google.gson.JsonElement;
import com.google.gson.GsonBuilder;

public class Chronotimer{
  ArrayList<Boolean> channels;
  long startTime, offsetTime;
  ArrayList<Race> races;
  boolean runStarted;
  String event;
  int currentRace;
  String outputLocation = java.io.File.separator;
  
  public Chronotimer(){
	  channels = new ArrayList<Boolean>();
    for(int i=0; i<8; i++)
      channels.add(false);
    startTime = System.nanoTime();
    runStarted = false;
  }
  
  public Chronotimer(long time){
    for(int i=0; i<8; i++)
      channels.add(false);
    startTime = time;
  }
  
  /** The TOG console/file command has a range from 1-8
   * This toggles the appropriate channel in the array from on (true) to off (false) or vice versa, from 0-7.*/
  public void toggle(int channel){
	  channels.set(channel-1, !channels.get(channel-1));
  }
  
  /** If the channel is on and there's an active run,
   * start a new competitor if channel 1, end a competitor if channel 2. */
  public void trigger(int channel, long time){
    if(channels.get(channel-1) && runStarted)
	    races.get(currentRace).trigger(time - startTime);
  }
  
  /** Sets the time offset in the file */
  public void setTime(long time){ offsetTime = time; } 
  
  /** If the event is IND, turns eventSelected to true, allowing new runs to be created.
   * Otherwise nothing happens.
   */
  public void setEvent(String event){
	  if(event.equalsIgnoreCase("IND") || event.equalsIgnoreCase("PARIND"))
			  event = event;
  }
  
  /** If there's no current run and an event has been selected,
   * start a new run, clearing all previous data.
   */
  public void newRun(){
	  if(!runStarted && event != null){
		  if(event.equalsIgnoreCase("IND"))
			  races.add(new Ind());
		  else
			  races.add(new ParaInd());
		  runStarted = true;
		  currentRace++;
	  }
  }
  
  /** Allows for a new run to be created. */
  public void endRun(){ runStarted=false; }
  
  /** Adds a competitor to run */
  public void addCompetitor(int bib){
	  races.get(currentRace).addCompetitor(bib);
  }
  
  /** Returns a formatted String representing the run,
     which is outputted by Simulator */
//  public ArrayList<String> print(){
//	return races.get(currentRace).competitorList();
//    //TODO
//  }
  
  public String print(){
	  String outputPath = outputLocation + "RUN0001.txt";
	  String outputPathJSON = outputLocation + "RUN0001.json";
	  String outputPathPlainTxt = outputLocation + "RUN0001.json";
	  //Gson g = new Gson();		//use for single line (not pretty)
	  Gson g = new GsonBuilder().setPrettyPrinting().create(); //use for pretty
	  
	  String json = g.toJson(this);		//maybe change <this> to <races>
	  String plainTxt = this.toString();
	//return run.competitorList();
    //TODO
	  BufferedWriter output = null;
	  try{
		  output = new BufferedWriter(new FileWriter(outputPath));
		  output.write(json);
		  output.close();
	  }
	  catch(Exception e){
		  System.out.println("ERROR - could not print outputPath");
	  }
	  try{
		  output = new BufferedWriter(new FileWriter(outputPathJSON));
		  output.write(json);
		  output.close();
	  }
	  catch(Exception e){
		  System.out.println("ERROR - could not print outputPathJSON");
	  }
	  try{
		  output = new BufferedWriter(new FileWriter(outputPathPlainTxt));
		  output.write(json);
		  output.close();
	  }
	  catch(Exception e){
		  System.out.println("ERROR - could not print outputPathPlainTxt");
	  }
	  return json;
  }
  
  @Override
  public String toString(){
	  String out = "\t\tRecipt\n\n\t**********\n\n";
	  for(int i = 0; i < races.size(); i++){
		  out = out +  races.get(i).toString() + "\n\n\t********************";
	  }
	  return out;
  }
}

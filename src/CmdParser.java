package src;

import java.io.BufferedWriter;
import java.io.IOException;

public class CmdParser{
  Chronotimer chronotimer;
  boolean powerOn, fileRead;
  long time;
  BufferedWriter logWriter = null;
  
  public CmdParser(BufferedWriter log){
	chronotimer = new Chronotimer(System.nanoTime(), log);
    powerOn = false;
    logWriter = log;
  }
  
  public CmdParser(String[] nextLine, BufferedWriter log){
	chronotimer = new Chronotimer(parseTime(nextLine[0]), log);
    powerOn = false;
    fileRead = true;
    logWriter = log;
    input(nextLine);
  }
  
  private void debug(String s){
		String msg = "CmdParser - " + s;
		if(logWriter != null){
			try {
				logWriter.write(msg + "\n");
			} catch (IOException e) {
				System.out.println(msg);
				e.printStackTrace();
			}
		}
	}
  
  /** Takes input from a file or console passed by Driver, and parses it into a method for use by the Chronotimer */
  public String input(String[] input){
    //If power is on, turn it off, and vice versa
    if(input[1].equalsIgnoreCase("POWER"))
      powerOn = !powerOn;
    
    /*Instead of resetting everything, the program is put on standby when the power is turned off
     * If power is on, read the command and pass it to the proper method in Chronotimer */
    else if(powerOn){
      if(input[1].equalsIgnoreCase("TOG")){
        chronotimer.toggle( Integer.parseInt(input[2]));
        debug("tog called");
      }
      
      else if(input[1].equalsIgnoreCase("TIME")){
        chronotimer.setTime( parseTime(input[2]));
        debug("time : " + parseTime(input[2]));
      }
      
      else if(input[1].equalsIgnoreCase("TRIG")){
    	chronotimer.trigger( Integer.parseInt(input[2]), Long.parseLong(input[0]));
    	debug("trig" + Integer.parseInt(input[2]));
      }
      
      else if(input[1].equalsIgnoreCase("EVENT")){
        chronotimer.setEvent(input[2]);
        debug("event : " + input[2]);
      }
      
      else if(input[1].equalsIgnoreCase("NEWRUN")){
        chronotimer.newRun();
        debug("newRun");
      }
      
      else if(input[1].equalsIgnoreCase("ENDRUN")){
        chronotimer.endRun();
        debug("endRun");
      }
      
      else if(input[1].equalsIgnoreCase("NUM")){
        chronotimer.addCompetitor( Integer.parseInt(input[2]));
        debug("num : " + Integer.parseInt(input[2]));
      }
      
      else if(input[1].equalsIgnoreCase("PRINT")){
        System.out.println( chronotimer.print());
        debug("print");
      }
	    
      else{
    	  String str = "";
    	  for(int i = 0; i < input.length; i++)
    		  str = str + input[i] + " ";
    	  debug("Invalid command - " + str);
	  }
      
    }
    return chronotimer.toString();
  }
  
  public String updateDisplay(long t){
	  return chronotimer.updateDisplay(t);
  }
  
  //this was is UIcontroller but this class uses it, 
  /** Turns time formatted in HH:MM:SS.S to a long */
	public long parseTime(String time){
		String[] timeString = time.split(":|\\.");
		return (Long.parseLong(timeString[0]) * 36000L //Convert to a single time unit (tenth-seconds), which is then converted to nanoseconds
 		 + Long.parseLong(timeString[1]) * 600L
		 + Long.parseLong(timeString[2]) * 10L
		 + Long.parseLong(timeString[3]))
		 * 100000000L;
	}
 
}

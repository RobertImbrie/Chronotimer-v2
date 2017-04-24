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
      if(input[1].equalsIgnoreCase("TOG"))
        chronotimer.toggle( Integer.parseInt(input[2]));
      
      else if(input[1].equalsIgnoreCase("TIME"))
        chronotimer.setTime( parseTime(input[2]));
      
      else if(input[1].equalsIgnoreCase("TRIG"))
    	chronotimer.trigger( Integer.parseInt(input[2]), input[0]);
      
      else if(input[1].equalsIgnoreCase("EVENT"))
        chronotimer.setEvent(input[2]);
      
      else if(input[1].equalsIgnoreCase("NEWRUN"))
        chronotimer.newRun();
      
      else if(input[0].equalsIgnoreCase("ENDRUN"))
        chronotimer.endRun();
      
      else if(input[0].equalsIgnoreCase("NUM"))
        chronotimer.addCompetitor( Integer.parseInt(input[2]));
      
      else if(input[1].equalsIgnoreCase("PRINT"))
        System.out.println( chronotimer.print());
	    
      else
	  debug("Invalid command");
    }
  }
 
}

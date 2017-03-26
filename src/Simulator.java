package src;

import java.io.BufferedWriter;
import java.io.IOException;

public class Simulator{
  Chronotimer chronotimer;
  boolean powerOn, fileRead;
  long time;
  BufferedWriter logWriter = null;
  
  public Simulator(BufferedWriter log){
	chronotimer = new Chronotimer(System.nanoTime(), log);
    powerOn = false;
    fileRead = false;
    logWriter = log;
  }
  
  public Simulator(String[] nextLine, BufferedWriter log){
	chronotimer = new Chronotimer(parseTime(nextLine[0]), log);
    powerOn = false;
    fileRead = true;
    logWriter = log;
    input(nextLine);
  }
  
  private void debug(String s){
		String msg = "Simulator - " + s;
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
  public void input(String[] input){
    //Special case for a file read - instead of using system time, parses and stores the time prefixing the commands in the file
    if(fileRead){ 
      time = parseTime(input[0]);         
      System.arraycopy(input, 1, input, 0, input.length - 1);
    } else
      time = System.nanoTime();
    //If power is on, turn it off, and vice versa
    if(input[0].equalsIgnoreCase("POWER"))
      powerOn = !powerOn;
    
    /*Instead of resetting everything, the program is put on standby when the power is turned off
     * If power is on, read the command and pass it to the proper method in Chronotimer */
    else if(powerOn){
      if(input[0].equalsIgnoreCase("TOG"))
        chronotimer.toggle( Integer.parseInt(input[1]));
      
      else if(input[0].equalsIgnoreCase("TIME"))
        chronotimer.setTime( parseTime(input[1]));
      
      else if(input[0].equalsIgnoreCase("TRIG"))
    	chronotimer.trigger( Integer.parseInt(input[1]), time);
      
      else if(input[0].equalsIgnoreCase("EVENT"))
        chronotimer.setEvent(input[1]);
      
      else if(input[0].equalsIgnoreCase("NEWRUN"))
        chronotimer.newRun();
      
      else if(input[0].equalsIgnoreCase("ENDRUN"))
        chronotimer.endRun();
      
      else if(input[0].equalsIgnoreCase("NUM"))
        chronotimer.addCompetitor( Integer.parseInt(input[1]));
      
      else if(input[0].equalsIgnoreCase("PRINT"))
        System.out.println( chronotimer.print());
    }
  }
  
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

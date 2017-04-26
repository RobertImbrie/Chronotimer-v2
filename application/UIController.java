package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import src.Chronotimer;
import src.CmdParser;

public class UIController {
	BufferedWriter logWriter;
	CmdParser commandParser;
	
	UIController(BufferedWriter b){
		logWriter = b;
		Chronotimer c = new Chronotimer(System.nanoTime(), logWriter);
		commandParser = new CmdParser(logWriter);
	}
	public String command(String c, long l){
		debug("command called");
		c = String.valueOf(l) +  " " + c;
		String result = commandParser.input( c.split(" ") );
//		return "'" + c + "' was called at " + l;
		return result;
	}
	 public String toggleChannel(int chan, boolean toState, long l){
		 debug("toggleChannel called");
		 command(("TOG " + chan), l);
		 return "channel was toggled " + chan;
	 }
	 public String togglePower(){
		 debug("togglePower called");
		 command("POWER ", System.nanoTime());
		 System.out.println("power toggled");
		 return "";
	 }
	 public String conn(int chan, String connType, long l){
		 return connType + l;
	 }
	 public String disc(int chan, long l){
		 return "sensor disconnescted on channel " + chan;
	 }
	 public String trig(int chan, long l){
		 command(("TRIG " + chan), l);
		 return "";
	 }
	 public String swap(long l){
		 command("SWAP", l);
		 return "";
	 }
	
	public void runFile(String filePath){
			try {
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				String nextLine;
				for (nextLine = reader.readLine(); nextLine != null && !nextLine.equalsIgnoreCase("EXIT"); nextLine = reader.readLine()){
					String command[] = nextLine.split("\\s+");
					long time = parseTime(command[0]);
					for(int i = 1; i<command.length; i++)
						nextLine += command[i];
					command( nextLine, time);
				}
				reader.close();
			}
			catch (FileNotFoundException e){
				debug("Invalid filepath on UIController");
			}
			catch(IOException e){
				System.out.println("IO Problem");
			}

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
  	
  	public String updateDisplay(long t){
  		return commandParser.updateDisplay(t);
  	}
	 
	 //----------
	 private void debug(String s){
			String msg = "UIController - " + s;
			if(logWriter != null){
				try {
					logWriter.write(msg + "\n");
				} catch (IOException e) {
					System.out.println(msg);
					e.printStackTrace();
				}
			}
		}

}

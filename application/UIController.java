package application;

import java.io.BufferedWriter;
import java.io.IOException;
import src.Chronotimer;

public class UIController {
	BufferedWriter logWriter;
	
	UIController(BufferedWriter b){
		logWriter = b;
		Chronotimer c = new Chronotimer(System.nanoTime(), logWriter);
	}
	public String command(String c, long l){
		debug("command called");
		return "'" + c + "' was called at " + l;
	}
	 public String toggleChannel(int chan, boolean toState, long l){
		 debug("toggleChannel called");
		 return "channel was toggled " + chan;
	 }
	 public String togglePower(){
		 debug("togglePower called");
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
		 return "";
	 }
	 public String swap(long l){
		 return "";
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

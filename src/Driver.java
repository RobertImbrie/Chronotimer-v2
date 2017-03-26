package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) throws IOException {
		String logPath = "debugLog.log";
		BufferedWriter logWriter = null;
		try{
			logWriter = new BufferedWriter(new FileWriter(logPath));
		}
		catch(Exception e){
			
		}
		CTController control = new CTController();
		Scanner userPrompt = new Scanner(System.in);
		boolean fileBeenRead = false;

		if (!control.controlFromFile()) { //controlFromFile returns false if the user select to read from console
			Simulator simulator = new Simulator(logWriter);
			for (String nextLine = userPrompt.nextLine(); !nextLine.equalsIgnoreCase("EXIT"); nextLine = userPrompt
					.nextLine())
				simulator.input(nextLine.split("\\s+")); //Until EXIT is entered, pass it as an array to simulator split by whitespace
		}
		else {
			while (!fileBeenRead) { //loops until user enters a valid filepath
				System.out.print("Enter the full file directory: ");
				
				try {
					BufferedReader reader = new BufferedReader(new FileReader(userPrompt.nextLine()));
					Simulator simulator = new Simulator(reader.readLine().split("\\s+"), logWriter);

					for (String nextLine = reader.readLine(); nextLine != null && !nextLine.equalsIgnoreCase("EXIT"); nextLine = reader.readLine())
						simulator.input(nextLine.split("\\s+"));
					fileBeenRead = true;
					reader.close();
				}
				catch (FileNotFoundException e) {
					System.out.println("Invalid filepath");
				}
			}
		}
	}
	
	
}

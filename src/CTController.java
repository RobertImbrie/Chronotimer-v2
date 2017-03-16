package src;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.IOException;

public class CTController {
	// For getting input if from console;
	private Scanner input = new Scanner(System.in);
	private BufferedWriter logWriter = null;

	public CTController() {}
	public CTController(BufferedWriter log) {
		logWriter = log;
	}
	
	private void debug(String s){
		String msg = "CtController - " + s;
		if(logWriter != null){
			try {
				logWriter.write(msg + "\n");
			} catch (IOException e) {
				System.out.println(msg);
				e.printStackTrace();
			}
		}
	}

	public boolean controlFromFile() {
		// Prompt for Entry
		System.out.println("Please enter control mode using 1 or 2:");
		System.out.println("\t1) Console Commands.");
		System.out.println("\t2) Read from file.");

		int response = 0;
		do {
			boolean test = false;
			while (test == false){
			    try{
			    	System.out.print("Entry: "); // Receive entry
			        response = input.nextInt();
			        test = true;
			        } catch (InputMismatchException e) {
			            input.next();
			            System.out.println("You didn't give integer. Try again");
			    }
			    if(response < 1 || response > 2){
			    	System.out.println("Please enter an integer 1 or 2.");
			    }
			}

		} while (response < 1 || response > 2); // Break out when 1 or 2

		if (response == 1) { // Return true when 1
			System.out.println("You have chosen console control.");
			return false;
		} else // Return fasle when 2
			System.out.println("You have chosen file read.");
		return true;
	}

}

package application;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.stage.Stage;
import java.util.*;

public class ChronoUI extends Application
{
	//String command = "";
	boolean update = true;
	//String[] commandList = {"", "TOG", "TIME", "TRIG", "EVENT", "NEWRUN", "ENDRUN", "NUM", "PRINT"};
	String[][] commandMatrix = {
							{""}, 
							{"TOG"}, 
							{"TIME"}, 
							{"TRIG"}, 
							{"NEWRUN"}, 
							{"ENDRUN"}, 
							{"NUM"}, 
							{"PRINT"},
							{"EVENT", "EVENT IND", "EVENT INDPAR", "EVENT GRP"},
							{"DNF"}
							};
	int commandInt = 0;
	int comX = 0;
	int comY = 0;
	String enterNum = "";
	boolean powerOn = false;
	static UIController c;	//Class to be made
	static BufferedWriter logWriter;
	static TextArea screenArea;
	static TextArea printArea;
	boolean printerPWR = false;
	
	public static void main(String[] args) 
	{
		String logPath = "debugLog.log";
		logWriter = null;
		try{
			logWriter = new BufferedWriter(new FileWriter(logPath, true));
			Date d = new Date();
			logWriter.write("\n\n****************************************************************\n");
			logWriter.write(d.toString() + "\n");
		}
		catch(Exception e){
			
		}
		c = new UIController(logWriter);
		if (args.length > 0){
			c.runFile(args[0]);
		}
		Application.launch(args);
		
	}
	
	@Override
	public void start(Stage stage) throws IOException
	{
		stage.setTitle("CHRONOTIMER 1009");
        addLayout(stage);

        stage.show();
	}
	public void updateEnterBox(TextField txt){
		//txt.setText(commandList[commandInt] + " " + enterNum);
		txt.setText(commandMatrix[comX][comY] + " " + enterNum);
	}
	
	private void debug(String s){
		String msg = "ChronoUI - " + s;
		if(logWriter != null){
			try {
				logWriter.write(msg + "\n");
			} catch (IOException e) {
				System.out.println(msg);
				e.printStackTrace();
			}
		}
	}
	
	public void addLayout(Stage primaryStage){
		// sets up the main components of the UI
		GridPane mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		//mainGrid.setPadding(new Insets(25, 25, 25, 25));
		mainGrid.setId("front_layout");
		
		VBox uiBox = new VBox();
		uiBox.getChildren().add(mainGrid);
		Scene scene = new Scene(uiBox, 300, 275);
		  URL url = this.getClass().getResource("application.css");
		    if (url == null) {
		        System.out.println("Resource not found. Aborting.");
		        System.exit(-1);
		    }
		    String css = url.toExternalForm(); 
		scene.getStylesheets().add(css);
		primaryStage.setScene(scene);
		
		// adds the section with the power button
		VBox powerBox = new VBox();
		powerBox.setPadding(new Insets(10));
		powerBox.setSpacing(8);
	    Button btnPower = new Button("Power");

	    powerBox.getChildren().add(btnPower);
	    mainGrid.add(powerBox, 0, 0);
	    
	    // adds the section with the channel triggers
	    GridPane trigGrid = new GridPane();
		trigGrid.setAlignment(Pos.CENTER);
		trigGrid.setHgap(10);
		trigGrid.setVgap(10);
		//trigGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Label lblStart = new Label("Start");
		trigGrid.add(lblStart, 0, 0);
		Button btnTrig1 = new Button("1");
		trigGrid.add(btnTrig1, 1, 0);
		Button btnTrig3 = new Button("3");
		trigGrid.add(btnTrig3, 2, 0);
		Button btnTrig5 = new Button("5");
		trigGrid.add(btnTrig5, 3, 0);
		Button btnTrig7 = new Button("7");
		trigGrid.add(btnTrig7, 4, 0);
		
		Label lblOddEnabled = new Label("Enabled/Disabled");
		trigGrid.add(lblOddEnabled, 0, 1);
		CheckBox chkEn1 = new CheckBox();
		trigGrid.add(chkEn1, 1, 1);
		CheckBox chkEn3 = new CheckBox();
		trigGrid.add(chkEn3, 2, 1);
		CheckBox chkEn5 = new CheckBox();
		trigGrid.add(chkEn5, 3, 1);
		CheckBox chkEn7 = new CheckBox();
		trigGrid.add(chkEn7, 4, 1);
		
		// handlers for the trigger buttons
		btnTrig1.setOnAction((e) ->{
			if (chkEn1.isSelected()){
				c.trig(1, System.nanoTime());
			}
		});
		btnTrig3.setOnAction((e) ->{
			if (chkEn3.isSelected()){
				c.trig(3, System.nanoTime());
			}
		});
		btnTrig5.setOnAction((e) ->{
			if (chkEn5.isSelected()){
				c.trig(5, System.nanoTime());
			}
		});
		btnTrig7.setOnAction((e) ->{
			if (chkEn7.isSelected()){
				c.trig(7, System.nanoTime());
			}
		});
		
		
		Label lblFinish = new Label("Finish");
		trigGrid.add(lblFinish, 0, 2);
		Button btnTrig2 = new Button("2");
		trigGrid.add(btnTrig2, 1, 2);
		Button btnTrig4 = new Button("4");
		trigGrid.add(btnTrig4, 2, 2);
		Button btnTrig6 = new Button("6");
		trigGrid.add(btnTrig6, 3, 2);
		Button btnTrig8 = new Button("8");
		trigGrid.add(btnTrig8, 4, 2);
		
		Label lblEvenEnabled = new Label("Enabled/Disabled");
		trigGrid.add(lblEvenEnabled, 0, 3);
		CheckBox chkEn2 = new CheckBox();
		trigGrid.add(chkEn2, 1, 3);
		CheckBox chkEn4 = new CheckBox();
		trigGrid.add(chkEn4, 2, 3);
		CheckBox chkEn6 = new CheckBox();
		trigGrid.add(chkEn6, 3, 3);
		CheckBox chkEn8 = new CheckBox();
		trigGrid.add(chkEn8, 4, 3);
		
		btnTrig2.setOnAction((e) ->{
			if (chkEn2.isSelected()){
				c.trig(2, System.nanoTime());
			}
		});
		btnTrig4.setOnAction((e) ->{
			if (chkEn4.isSelected()){
				c.trig(4, System.nanoTime());
			}
		});
		btnTrig6.setOnAction((e) ->{
			if (chkEn6.isSelected()){
				c.trig(6, System.nanoTime());
			}
		});
		btnTrig8.setOnAction((e) ->{
			if (chkEn8.isSelected()){
				c.trig(8, System.nanoTime());
			}
		});
		
		chkEn1.setDisable(true);
		chkEn2.setDisable(true);
		chkEn3.setDisable(true);
		chkEn4.setDisable(true);
		chkEn5.setDisable(true);
		chkEn6.setDisable(true);
		chkEn7.setDisable(true);
		chkEn8.setDisable(true);
		
		mainGrid.add(trigGrid, 1, 0);
		
		// adds the printer section
		VBox printBox = new VBox();
		printBox.setPadding(new Insets(10));
		printBox.setSpacing(8);
		printBox.setDisable(true);
		Button btnPrint = new Button("PrintPWR");
		printBox.getChildren().add(btnPrint);
		printArea = new TextArea();
		printArea.setEditable(false);
		btnPrint.setOnAction((e) ->{
			if (printerPWR){
				printerPWR = false;
				printArea.setText("");
			}
			else
				printerPWR = true;
		});
		printBox.getChildren().add(printArea);
		mainGrid.add(printBox, 2, 0);
		
		//adds the section with the directional and swap buttons, handlers further down
		VBox controlBox = new VBox();
		controlBox.setPadding(new Insets(10));
		controlBox.setSpacing(8);
		Button btnFunction = new Button("Function");
		controlBox.getChildren().add(btnFunction);
		HBox dPadBox = new HBox();
		dPadBox.setPadding(new Insets(10));
		dPadBox.setSpacing(8);
		Button btnLeft = new Button("<");
		Button btnRight = new Button(">");
		Button btnDown = new Button("V");
		Button btnUp = new Button("^");
		dPadBox.getChildren().add(btnLeft);
		dPadBox.getChildren().add(btnRight);
		dPadBox.getChildren().add(btnDown);
		dPadBox.getChildren().add(btnUp);
		controlBox.getChildren().add(dPadBox);
		Button btnSwap = new Button("Swap");
		btnSwap.setOnAction((e) ->{
			c.swap(System.nanoTime());
		});
		controlBox.getChildren().add(btnSwap);
		
		mainGrid.add(controlBox, 0, 1);
		
		
		// adds the screen updating task
        final Task task;
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int i = 0;
                System.out.println("taks is running");
                while(true){
                	if (i == Integer.MAX_VALUE-100)
                		i = 0;
                	//screenArea.setText("" + i);
                	i++;
                	if (update){
                		updateMessage(c.updateDisplay(System.nanoTime()));
                	}
                	else{
                		updateMessage(String.valueOf(i));
                	}
                	Thread.sleep(100);
                }
                //return null;
            }
        };
        
        // adds the section with the screen and command bar
		VBox screenBox = new VBox();
		screenArea = new TextArea();
		//screenArea.textProperty().bind(task.messageProperty());
		screenBox.getChildren().add(screenArea);
		TextField enterBox= new TextField();
		enterBox.setEditable(false);
		screenArea.setEditable(false);
		screenBox.getChildren().add(enterBox);
		mainGrid.add(screenBox, 1, 1);
		
		// adds the event handlers for the directional buttons
		btnLeft.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "1");
//			if (commandInt == commandList.length -1)
//				commandInt = 0;
//			else
//				commandInt++;
			
			if (comY == commandMatrix[comX].length -1)
				comY = 0;
			else
				comY++;
			updateEnterBox(enterBox);
		});
		btnUp.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "1");
//			if (commandInt == commandList.length -1)
//				commandInt = 0;
//			else
//				commandInt++;
			if (comX == commandMatrix.length -1)
				comX = 0;
			else
				comX++;
			updateEnterBox(enterBox);
		});
		btnRight.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "1");
//			if (commandInt == 0)
//				commandInt = commandList.length -1;
//			else
//				commandInt--;
			if (comY == 0)
				comY = commandMatrix[comX].length -1;
			else
				comY--;
			updateEnterBox(enterBox);
		});
		btnDown.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "1");
//			if (commandInt == 0)
//				commandInt = commandList.length -1;
//			else
//				commandInt--;
			if (comX == 0)
				comX = commandMatrix.length -1;
			else
				comX--;
			updateEnterBox(enterBox);
		});
		
		// adds the section with the number buttons
		GridPane numGrid = new GridPane();
		numGrid.setAlignment(Pos.CENTER);
		numGrid.setHgap(10);
		numGrid.setVgap(10);
		numGrid.setPadding(new Insets(25, 25, 25, 25));
		
		Button btn1 = new Button("1");
		Button btn2 = new Button("2");
		Button btn3 = new Button("3");
		Button btn4 = new Button("4");
		Button btn5 = new Button("5");
		Button btn6 = new Button("6");
		Button btn7 = new Button("7");
		Button btn8 = new Button("8");
		Button btn9 = new Button("9");
		Button btn0 = new Button("0");
		Button btnStar = new Button("*");
		Button btnPound = new Button("#");
		
		btn1.setOnAction(this:: numPad);
		btn1.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "1");
			enterNum = enterNum + "1";
			updateEnterBox(enterBox);
		});
		btn2.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "2");
			enterNum = enterNum + "2";
			updateEnterBox(enterBox);
		});
		btn3.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "3");
			enterNum = enterNum + "3";
			updateEnterBox(enterBox);
		});
		btn4.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "4");
			enterNum = enterNum + "4";
			updateEnterBox(enterBox);
		});
		btn5.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "5");
			enterNum = enterNum + "5";
			updateEnterBox(enterBox);
		});
		btn6.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "6");
			enterNum = enterNum + "6";
			updateEnterBox(enterBox);
		});
		btn7.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "7");
			enterNum = enterNum + "7";
			updateEnterBox(enterBox);
		});
		btn8.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "8");
			enterNum = enterNum + "8";
			updateEnterBox(enterBox);
		});
		btn9.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "9");
			enterNum = enterNum + "9";
			updateEnterBox(enterBox);
		});
		btn0.setOnAction((e) ->{
			//enterBox.setText(enterBox.getText() + "0");
			enterNum = enterNum + "0";
			updateEnterBox(enterBox);
		});
		btnStar.setOnAction((e) ->{
			//CLEAR
			enterNum = "";
			commandInt = 0;
			updateEnterBox(enterBox);
		});
		btnPound.setOnAction((e) ->{
			//ENTER
			//String com = commandList[commandInt] + " " + enterNum;
			String com = commandMatrix[comX][comY] + " " + enterNum;
			enterNum = "";
			commandInt = 0;
			comX = 0;
			comY = 0;
			updateEnterBox(enterBox);
			//screenArea.setText(screenArea.getText() + com + "\n");
			String returnTxt = c.command(com, System.nanoTime());	// this will send the command into the system once the controlller is created
			if(returnTxt != null){
				if (returnTxt.toUpperCase().contains("ERROR")){
					debug(returnTxt);
				}
				else{
//					printArea.setText(printArea.getText() + returnTxt + "\n");
					printArea.setText(returnTxt);
				}
			}
		});
		
		numGrid.add(btn1, 0, 0);
		numGrid.add(btn2, 1, 0);
		numGrid.add(btn3, 2, 0);
		numGrid.add(btn4, 0, 1);
		numGrid.add(btn5, 1, 1);
		numGrid.add(btn6, 2, 1);
		numGrid.add(btn7, 0, 2);
		numGrid.add(btn8, 1, 2);
		numGrid.add(btn9, 2, 2);
		numGrid.add(btn0, 1, 3);
		numGrid.add(btnStar, 0, 3);
		numGrid.add(btnPound, 2, 3);
		
		mainGrid.add(numGrid, 2, 1);
		
		// adds the section to "plug in" sensors to the "back" of the chronotimer
		HBox backView = new HBox();
		backView.setId("backView");
		GridPane channelGrid = new GridPane();
		Label lblChan1 = new Label("1");
		Label lblChan2 = new Label("2");
		Label lblChan3 = new Label("3");
		Label lblChan4 = new Label("4");
		Label lblChan5 = new Label("5");
		Label lblChan6 = new Label("6");
		Label lblChan7 = new Label("7");
		Label lblChan8 = new Label("8");
		Label lblChannel = new Label("Channel");
		

		CheckBox chkChan1 = new CheckBox();
		chkChan1.setOnAction((e) ->{
			String s = "";
			if (chkChan1.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn1.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(1, true, System.nanoTime());
				    s= c.conn(1, result.get(), System.nanoTime());
				}
				else
					chkChan1.setSelected(false);
			}
			else{
				chkEn1.setDisable(true);
				chkEn1.setSelected(false);
				//s = c.toggleChannel(1, false, System.nanoTime());
				s = c.disc(1,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		CheckBox chkChan2 = new CheckBox();
		chkChan2.setOnAction((e) ->{
			String s = "";
			if (chkChan2.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn2.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(2, true, System.nanoTime());
				    s= c.conn(2, result.get(), System.nanoTime());
				}
				else
					chkChan2.setSelected(false);
			}
			else{
				chkEn2.setDisable(true);
				chkEn2.setSelected(false);
				//s = c.toggleChannel(2, false, System.nanoTime());
				s = c.disc(2,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		CheckBox chkChan3 = new CheckBox();
		chkChan3.setOnAction((e) ->{
			String s = "";
			if (chkChan3.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn3.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(3, true, System.nanoTime());
				    s= c.conn(3, result.get(), System.nanoTime());
				}
				else
					chkChan3.setSelected(false);
			}
			else{
				chkEn3.setDisable(true);
				chkEn3.setSelected(false);
				//s = c.toggleChannel(3, false, System.nanoTime());
				s = c.disc(3,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		CheckBox chkChan4 = new CheckBox();
		chkChan4.setOnAction((e) ->{
			String s = "";
			if (chkChan4.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn4.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(4, true, System.nanoTime());
				    s= c.conn(4, result.get(), System.nanoTime());
				}
				else
					chkChan4.setSelected(false);
			}
			else{
				chkEn4.setDisable(true);
				chkEn4.setSelected(false);
				//s = c.toggleChannel(4, false, System.nanoTime());
				s = c.disc(4,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		CheckBox chkChan5 = new CheckBox();
		chkChan5.setOnAction((e) ->{
			String s = "";
			if (chkChan5.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn5.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(5, true, System.nanoTime());
				    s= c.conn(5, result.get(), System.nanoTime());
				}
				else
					chkChan5.setSelected(false);
			}
			else{
				chkEn5.setDisable(true);
				chkEn5.setSelected(false);
				//s = c.toggleChannel(5, false, System.nanoTime());
				s = c.disc(5,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		CheckBox chkChan6 = new CheckBox();
		chkChan6.setOnAction((e) ->{
			String s = "";
			if (chkChan6.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn6.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(6, true, System.nanoTime());
				    s= c.conn(6, result.get(), System.nanoTime());
				}
				else
					chkChan6.setSelected(false);
			}
			else{
				chkEn6.setDisable(true);
				chkEn6.setSelected(false);
				//s = c.toggleChannel(6, false, System.nanoTime());
				s = c.disc(6,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		CheckBox chkChan7 = new CheckBox();
		chkChan7.setOnAction((e) ->{
			String s = "";
			if (chkChan7.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn7.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(7, true, System.nanoTime());
				    s= c.conn(7, result.get(), System.nanoTime());
				}
				else
					chkChan7.setSelected(false);
			}
			else{
				chkEn7.setDisable(true);
				chkEn7.setSelected(false);
				//s = c.toggleChannel(7, false, System.nanoTime());
				s = c.disc(7,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		CheckBox chkChan8 = new CheckBox();
		chkChan8.setOnAction((e) ->{
			String s = "";
			if (chkChan8.isSelected()){
				ArrayList<String> choices = new ArrayList<>();
				choices.add("EYE");
				choices.add("GATE");
				choices.add("PAD");

				ChoiceDialog<String> dialog = new ChoiceDialog<>("EYE", choices);
				dialog.setTitle("SENSOR");
				dialog.setHeaderText("Select Sensor");
				dialog.setContentText("Select a sensor type:");

				// Traditional way to get the response value.
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					chkEn8.setDisable(false);
				    System.out.println("Your choice: " + result.get());
				//s = c.toggleChannel(8, true, System.nanoTime());
				    s= c.conn(8, result.get(), System.nanoTime());
				}
				else
					chkChan8.setSelected(false);
			}
			else{
				chkEn8.setDisable(true);
				chkEn8.setSelected(false);
				//s = c.toggleChannel(8, false, System.nanoTime());
				s = c.disc(8,  System.nanoTime());
			}
			if(powerOn){}
				//screenArea.setText(screenArea.getText() + "\n" + s);
		});
		
		channelGrid.add(lblChannel, 0, 0);
		channelGrid.add(lblChan1, 1, 0);
		channelGrid.add(lblChan3, 2, 0);
		channelGrid.add(lblChan5, 3, 0);
		channelGrid.add(lblChan7, 4, 0);
		channelGrid.add(chkChan1, 1, 1);
		channelGrid.add(chkChan3, 2, 1);
		channelGrid.add(chkChan5, 3, 1);
		channelGrid.add(chkChan7, 4, 1);
		channelGrid.add(lblChan2, 1, 2);
		channelGrid.add(lblChan4, 2, 2);
		channelGrid.add(lblChan6, 3, 2);
		channelGrid.add(lblChan8, 4, 2);
		channelGrid.add(chkChan2, 1, 3);
		channelGrid.add(chkChan4, 2, 3);
		channelGrid.add(chkChan6, 3, 3);
		channelGrid.add(chkChan8, 4, 3);
		
		backView.getChildren().add(channelGrid);
		
		// adds a button to enter a file to run, this can also be done on start as a commandline argument
		Button btnFile = new Button("Run From File");
		btnFile.setOnAction((e) ->{
			String s = "";

			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("File");
			dialog.setHeaderText("Run From File");
			dialog.setContentText("Enter the path of a :");

			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()){
				String res = result.get();
				c.runFile(res);
			}
		});
		
		backView.getChildren().add(btnFile);
		uiBox.getChildren().add(backView);
		
		
		//Disable most components to start
		trigGrid.setDisable(true);
		printBox.setDisable(true);
		controlBox.setDisable(true);
		screenBox.setDisable(true);
		numGrid.setDisable(true);
		
		//set up the power button after everything has been declared
	    btnPower.setOnAction((e) ->{
			//Toggle the power of the system
	    	if(powerOn){
	    		trigGrid.setDisable(true);
	    		printBox.setDisable(true);
	    		controlBox.setDisable(true);
	    		screenBox.setDisable(true);
	    		numGrid.setDisable(true);
	    		//screenArea.setText("");
	    		enterBox.setText("");
	    		powerOn = false;
	    		screenArea.textProperty().unbind();
	    		screenArea.textProperty().set("");
	    	}
	    	else{
	    		trigGrid.setDisable(false);
	    		printBox.setDisable(false);
	    		controlBox.setDisable(false);
	    		screenBox.setDisable(false);
	    		numGrid.setDisable(false);
	    		commandInt = 0;
	    		enterNum = "";
	    		powerOn = true;
	    		screenArea.textProperty().bind(task.messageProperty());
	    		new Thread(task).start();
	    	}
	    	String s = c.togglePower();
	    	//screenArea.setText(screenArea.getText() + "\n" + s);
	    	
	    	// saves the log file when the program exits
	    	primaryStage.setOnCloseRequest(event -> {
	    	    System.out.println("Stage is closing");
	    	    try {
					logWriter.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	});
		});
	}
		
	
	public void numPad(ActionEvent e){
		System.out.println("button pushed");
	}
	
}

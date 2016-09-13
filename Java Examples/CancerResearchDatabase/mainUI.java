package com.runtime;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.util.Random;

import javax.swing.*;

import java.sql.*;

public class mainUI {
	
	public static JFrame mainFrame;
	
	
	// patient objects
	public static JButton patientButton;
	public static JTextField patientField;
	public static JLabel patientFieldLabel;
	public static JLabel patientStatusLabel;
	
	// treatment center objects
	public static JButton researchButton;
	public static JComboBox<String> researchOptions;
	public static JLabel researchLabel;
	
	// individual cancer type stats objects
	public static JButton statButton;
	public static JComboBox<String> statOptions;
	public static JLabel statLabel;
	
	
	// graph objects, graph dropdown now also includes additional stats
	public static JButton graphButton;
	public static JComboBox<String> graphOptions;
	public static JLabel graphLabel;
	
	
	
	
	
	//db connection
	public static Connection db;
	
	
	public static void main(String[] args){
		
		db = connector();
		
		mainFrame = new JFrame("Cancer Research HUB");
		mainFrame.setSize(816, 621);
		mainFrame.setLocation(100, 100);
		mainFrame.setLayout(null);
		
		divideDrawer background = new divideDrawer();
		background.setSize(800, 600);
		background.setLocation(0, 0);
		
		//patient initializations
		patientFieldLabel = new JLabel("Enter the Patient ID:");
		patientFieldLabel.setSize(300, 50);
		patientFieldLabel.setLocation(25, 25);
		
		patientField = new JTextField();
		patientField.setSize(300, 50);
		patientField.setLocation(25, 75);
		
		patientStatusLabel = new JLabel(""); // either can be INVALID ID or PATIENT FOUND
		patientStatusLabel.setSize(100, 50);
		patientStatusLabel.setLocation(25, 150);
		
		patientButton = new JButton("Display Patient Record");
		patientButton.setSize(200, 50);
		patientButton.setLocation(125, 150);
		patientButton.addActionListener(new patientAction());
		
		//research center initializations
		researchLabel = new JLabel("Select a Research Centre:");
		researchLabel.setSize(300, 50);
		researchLabel.setLocation(25, 350);
		
		researchOptions = new JComboBox<String>();
		setLocations();
		researchOptions.setSize(300, 50);
		researchOptions.setLocation(25, 400);
		
		researchButton = new JButton("Get Info");
		researchButton.setSize(200, 50);
		researchButton.setLocation(125, 475);
		researchButton.addActionListener(new researchAction());
		
		
		//stat initializations
		statLabel = new JLabel("Select a Cancer Type:");
		statLabel.setSize(300, 50);
		statLabel.setLocation(425, 25);
				
		statOptions = new JComboBox<String>();
		statOptions.setSize(300, 50);
		statOptions.setLocation(425, 75);
		setCancerTypes();
				
		statButton = new JButton("Get Stats");
		statButton.setSize(200, 50);
		statButton.setLocation(525, 150);
		statButton.addActionListener(new typeAction());
		
		
		//graph initializations
		graphLabel = new JLabel("Select a Graph or Stat to Display:");
		graphLabel.setSize(300, 50);
		graphLabel.setLocation(425, 350);
						
		graphOptions = new JComboBox<String>();
		setGraphs();
		graphOptions.setSize(300, 50);
		graphOptions.setLocation(425, 400);
						
		graphButton = new JButton("Display Graph");
		graphButton.setSize(200, 50);
		graphButton.setLocation(525, 475);
		graphButton.addActionListener(new graphAction());
		
		
		mainFrame.add(patientButton);
		mainFrame.add(patientField);
		mainFrame.add(patientFieldLabel);
		mainFrame.add(patientStatusLabel);
		
		mainFrame.add(researchLabel);
		mainFrame.add(researchOptions);
		mainFrame.add(researchButton);
		
		mainFrame.add(statLabel);
		mainFrame.add(statOptions);
		mainFrame.add(statButton);
		
		mainFrame.add(graphLabel);
		mainFrame.add(graphOptions);
		mainFrame.add(graphButton);
		
		mainFrame.add(background); // must be on bottom to avoid leaks
		
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
	}
	
	
	public static class patientAction implements ActionListener{
		
		
		public void actionPerformed(ActionEvent e){
			
			int ID = -1;
			boolean isGood = false;
			
			try{
				
				ID = Integer.parseInt(patientField.getText());
				if(ID >= 0 && ID < 1000001)
					isGood = true;
				else
					patientStatusLabel.setText("INVALID ID");
				
			}
			catch(NumberFormatException e1){
				
				patientStatusLabel.setText("INVALID ID");
				
			}
			
			if(isGood){
				
				displayPatientFrame(ID);
				patientStatusLabel.setText("PATIENT FOUND");
				
			}
			
			
		}
		
	}
	
	public static class typeAction implements ActionListener{
		
		
		public void actionPerformed(ActionEvent e){
			
			String type = (String)statOptions.getSelectedItem();
			
			
			displayTypeFrame(type);
			
		}
		
	}
	
	
	public static class researchAction implements ActionListener{
		
		
		public void actionPerformed(ActionEvent e){
			
			String center = (String)researchOptions.getSelectedItem();
			
			
			displayLocationFrame(center);
			
		}
		
	}
	
	
	
	public static class graphAction implements ActionListener{
		
		
		public void actionPerformed(ActionEvent e){
			
			String graph = (String)graphOptions.getSelectedItem();
			
			if(graph.equals("Deaths by Age")){
				
				displayDeathsByAgeFrame();
				
			}
			else if(graph.equals("Type Pie Chart")){
				
				displayTypePieChartFrame();
				
			}
			else if(graph.equals("Deaths by Type Pie Chart")){
				
				displayDeathsByTypeChart();
				
			}
			else if(graph.equals("Deaths by Weight")){
				
				displayDeathsByWeight();
				
			}
			else if(graph.equals("Died With No Treatment")){
				
				String description = "Number of People Who Died With No Treatment";
				
				int count = 0;
				
				String query = "SELECT * FROM diedwithouttreatment ";
				
				ResultSet records = null;
				
				Statement request;
				try {
					
					request = db.createStatement();
					records = request.executeQuery(query);
					
					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
					count = records.getInt("count(*)");
					
				} 
				catch (SQLException e2) {
					
					// do nothing, new optionpane thrown below
					
				}
				
				displayStatFrame(description, count, false);
				
			}
			else if(graph.equals("Facilities/Events in Toronto")){
				
				String description = "Number of Facilities/Events in Toronto";
				
				int count = 0;
				
				String query = "SELECT count(*) FROM intoronto ";
				
				ResultSet records = null;
				
				Statement request;
				try {
					
					request = db.createStatement();
					records = request.executeQuery(query);
					
					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
					count = records.getInt("count(*)");
					
				} 
				catch (SQLException e2) {
					
					// do nothing, new optionpane thrown below
					
				}
				
				displayStatFrame(description, count, false);
				
			}
			else if(graph.equals("Focused on Low Survival")){
				
				String description = "Number of Facilities Focused on Low Survival";
				
				int count = 0;
				
				String query = "SELECT count(*) FROM focussedonlowsurvival ";
				
				ResultSet records = null;
				
				Statement request;
				try {
					
					request = db.createStatement();
					records = request.executeQuery(query);
					
					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
					count = records.getInt("count(*)");
					
				} 
				catch (SQLException e2) {
					
					// do nothing, new optionpane thrown below
					
				}
				
				displayStatFrame(description, count, false);
				
				
				
			}
			else if(graph.equals("Low Survival With No Research")){
				
				String description = "Low Survival Patients With No Research";
				
				int count = 0;
				
				String query = "SELECT count(*) FROM lowsurvivalnoresearch ";
				
				ResultSet records = null;
				
				Statement request;
				try {
					
					request = db.createStatement();
					records = request.executeQuery(query);
					
					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
					count = records.getInt("count(*)");
					
				} 
				catch (SQLException e2) {
					
					// do nothing, new optionpane thrown below
					
				}
				
				displayStatFrame(description, count, false);
				
				
			}
			else if(graph.equals("Cancers Caused By Tobacco")){
				
				String description = "Patients With Cancers Caused By Tobacco";
				
				int count = 0;
				
				String query = "SELECT count(*) FROM tobaccopatients ";
				
				ResultSet records = null;
				
				Statement request;
				try {
					
					request = db.createStatement();
					records = request.executeQuery(query);
					
					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
					count = records.getInt("count(*)");
					
				} 
				catch (SQLException e2) {
					
					// do nothing, new optionpane thrown below
					
				}
				
				displayStatFrame(description, count, false);
				
				
				
			}
			else if(graph.equals("Patients With Abdominal Pain")){
				
				String description = "Patients With Abdominal Pain";
				
				int count = 0;
				
				String query = "SELECT count(*) FROM abdominalpain ";
				
				ResultSet records = null;
				
				Statement request;
				try {
					
					request = db.createStatement();
					records = request.executeQuery(query);
					
					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
					count = records.getInt("count(*)");
					
				} 
				catch (SQLException e2) {
					
					// do nothing, new optionpane thrown below
					
				}
				
				displayStatFrame(description, count, false);
				
				
				
			}
			else if(graph.equals("Youth Victims")){
				
				String description = "Patients 25 and Under That Have Died";
				
				int count = 0;
				
				String query = "SELECT count(*) FROM youthvictims ";
				
				ResultSet records = null;
				
				Statement request;
				try {
					
					request = db.createStatement();
					records = request.executeQuery(query);
					
					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
					count = records.getInt("count(*)");
					
				} 
				catch (SQLException e2) {
					
					// do nothing, new optionpane thrown below
					
				}
				
				displayStatFrame(description, count, false);
				
				
				
			}
			else{
				
				JOptionPane.showMessageDialog(null, "Chart Not Implemented Yet!");
				
			}
			
		}
		
	}
	
	
	public static class treatmentAction implements ActionListener{
		
		public int patientID;
		
		public treatmentAction(int id){
			
			patientID = id;
			
		}
		
		
		public void actionPerformed(ActionEvent e){
			
			/////////////////////////////////////////
			// Query for records
			////////////////////////////////////////
			String query = "SELECT * FROM treatmentrecords "
					+ "WHERE patientID = " + patientID;
			
			ResultSet records = null;
			
			Statement request;
			try {
				
				request = db.createStatement();
				records = request.executeQuery(query);
				
				records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
				
			} 
			catch (SQLException e2) {
				
				// do nothing, new optionpane thrown below
				
			}
			
			if(records != null){
				
				boolean caught = false;
				
				boolean oneRecord = false;
				
				while(!caught){
					
					try{
						
						int recordID = records.getInt("recordID");
						
						displayTreatmentFrame(recordID);
						
						oneRecord = true;
						
						records.next();
						
					}
					catch(SQLException e2){
						
						caught = true;
						
					}
					
					
					
					
				}
				
				if(!oneRecord)
					JOptionPane.showMessageDialog(null, "This patient has no treatment records!");
				
			}
			else{
				
				JOptionPane.showMessageDialog(null, "This patient has no treatment records!");
				
				
			}
			
			
			
			
		}
		
	}
	
	
	public static class fundAction implements ActionListener{
		
		private String location;
		
		public fundAction(String loc){
			
			location = loc;
			
			
		}
		
		
		public void actionPerformed(ActionEvent e){
			
				/////////////////////////////////////////
				// Query for records
				////////////////////////////////////////
				String query = "SELECT * FROM funding "
							+ "WHERE benefactor = \'" + location + "\'";

				ResultSet records = null;

				Statement request;
				try {
	
					request = db.createStatement();
					records = request.executeQuery(query);

					records.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY

				} 
				catch (SQLException e2) {

					// do nothing, new optionpane thrown below

				}

				if(records != null){

					boolean caught = false;

					boolean oneRecord = false;

					while(!caught){

						try{

							String eventName = records.getString("event");

							displayFundingFrame(eventName);

							oneRecord = true;

							records.next();

						}
						catch(SQLException e2){

							caught = true;

						}




					}

					if(!oneRecord)
						JOptionPane.showMessageDialog(null, "This Research Facility has no funding records!");

				}
				else{

					JOptionPane.showMessageDialog(null, "This Research Facility has no funding records!");
	

				}
			
		}
		
		
	}
	
	
	
	
	public static void displayPatientFrame(int patientID){
		
		//querying database for single patient
		String query = "SELECT * FROM allpatients "
				+ "WHERE ID = " + patientID;
		
		ResultSet patient = null;
		
		Statement request;
		try {
			
			request = db.createStatement();
			patient = request.executeQuery(query);
			
			//patient.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
			
		} 
		catch (SQLException e) {
			
			// do nothing, new optionpane thrown below
			e.printStackTrace();
			
		}
		
		if(patient != null){
			
			//assign variables
			int age = 0;
			int height = 0;
			int weight = 0;
			String sex = "";
			String type = "";
			String fate = "";
			
			try {
				
				age = patient.getInt("age");
				height = patient.getInt("height");
				weight = patient.getInt("weight");
				sex = patient.getString("sex");
				type = patient.getString("type");
				fate = patient.getString("fate");
				
			} 
			catch (SQLException e) {
				
				//System.out.print("Failed To Retrieve Info");
				
			}
		
			
			Random rand = new Random();
		
			int x = rand.nextInt(1500 ) + 100;
			int y = rand.nextInt(600) + 100;
		
			JFrame newFrame = new JFrame(patientID + " Summary Record");
			newFrame.setSize(400, 300);
			newFrame.setLocation(x, y);
			newFrame.setLayout(null);
		
			JLabel IDLabel = new JLabel("ID: " + patientID);
			IDLabel.setSize(300, 25);
			IDLabel.setLocation(25, 10);
			
			JLabel ageLabel = new JLabel("Age: " + age);
			ageLabel.setSize(300, 25);
			ageLabel.setLocation(25, 35);
			
			JLabel heightLabel = new JLabel("Height: " + height);
			heightLabel.setSize(300, 25);
			heightLabel.setLocation(25, 60);
			
			JLabel weightLabel = new JLabel("Weight: " + weight);
			weightLabel.setSize(300, 25);
			weightLabel.setLocation(25, 85);
			
			JLabel sexLabel = new JLabel("Sex: " + sex);
			sexLabel.setSize(300, 25);
			sexLabel.setLocation(25, 110);
			
			JLabel typeLabel = new JLabel("Type: " + type);
			typeLabel.setSize(300, 25);
			typeLabel.setLocation(25, 135);
			
			JLabel fateLabel = new JLabel("Fate: " + fate);
			fateLabel.setSize(300, 25);
			fateLabel.setLocation(25, 160);
			
			JButton recordButton = new JButton("Get Treatment Records");
			recordButton.setSize(300, 50);
			recordButton.setLocation(25, 190);
			recordButton.addActionListener(new treatmentAction(patientID));
			
		
			newFrame.add(IDLabel);
			newFrame.add(ageLabel);
			newFrame.add(heightLabel);
			newFrame.add(weightLabel);
			newFrame.add(sexLabel);
			newFrame.add(typeLabel);
			newFrame.add(fateLabel);
			
			newFrame.add(recordButton);
			
			newFrame.setVisible(true);
		
		
		}
		else{
			
			JOptionPane.showMessageDialog(null, "Query Failed");
			
		}
		
	}
	
	public static void displayTypeFrame(String type){
		
		//querying database for single type
		String query = "SELECT * FROM cancertype "
						+ "WHERE type = \'" + type + "\'";
				
		ResultSet info = null;
				
		Statement request;
		try {
					
			request = db.createStatement();
			info = request.executeQuery(query);
					
			info.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
		} 
		catch (SQLException e) {
					
			// do nothing
					
		}
		
		
		if(info != null){
			
			//assign variables
			String description = "";
			String symptoms = "";
			String causes = "";
			String prevention = "";
			int rate = 0;
			
			try {
				
				description = info.getString("description");
				symptoms = info.getString("symptoms");
				causes = info.getString("causes");
				prevention = info.getString("prevention");
				rate = info.getInt("survivalrate");
				
			} 
			catch (SQLException e) {
				
				//System.out.print("Failed To Retrieve Info");
				
			}	
			
			
			Random rand = new Random();
			
			int x = rand.nextInt(1500 ) + 100;
			int y = rand.nextInt(600) + 100;
		
			JFrame newFrame = new JFrame(type + " Summmary");
			newFrame.setSize(400, 250);
			newFrame.setLocation(x, y);
			newFrame.setLayout(null);
			
			JLabel typeLabel = new JLabel("Cancer Type: " + type);
			typeLabel.setSize(300, 25);
			typeLabel.setLocation(25, 10);
			
			JLabel descLabel = new JLabel("Description: " + description);
			descLabel.setSize(500, 25);
			descLabel.setLocation(25, 35);
			
			JLabel sympLabel = new JLabel("Symptoms: " + symptoms);
			sympLabel.setSize(500, 25);
			sympLabel.setLocation(25, 60);
			
			JLabel causeLabel = new JLabel("Causes: " + causes);
			causeLabel.setSize(500, 25);
			causeLabel.setLocation(25, 85);
			
			JLabel prevLabel = new JLabel("Prevention: " + prevention);
			prevLabel.setSize(500, 25);
			prevLabel.setLocation(25, 110);
			
			JLabel survLabel = new JLabel("Survival Rate: " + rate);
			survLabel.setSize(300, 25);
			survLabel.setLocation(25, 135);
			
			
			newFrame.add(typeLabel);
			newFrame.add(descLabel);
			newFrame.add(sympLabel);
			newFrame.add(causeLabel);
			newFrame.add(prevLabel);
			newFrame.add(survLabel);
			
			newFrame.setVisible(true);
			
		
		}
		else{
			
			JOptionPane.showMessageDialog(null, "Query Failed");
			
		}
		
		
	}
	
	
	public static void displayLocationFrame(String center){
		
		//querying database for single research center
		String query = "SELECT * FROM researchfacilities "
								+ "WHERE name = \'" + center + "\'";
						
		ResultSet info = null;
						
		Statement request;
		try {
							
			request = db.createStatement();
			info = request.executeQuery(query);
							
			info.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
							
		} 
		catch (SQLException e) {
							
			// do nothing
							
		}
		
		if(info != null){
			
			//assign variables
			String name = "";
			String location = "";
			String subject = "";
			String type = "";
			
			try {
				
				name = info.getString("name");
				location = info.getString("location");
				subject = info.getString("subject");
				type = info.getString("type");
				
			} 
			catch (SQLException e) {
				
				//System.out.print("Failed To Retrieve Info");
				
			}	
			
			Random rand = new Random();
			
			int x = rand.nextInt(1500 ) + 100;
			int y = rand.nextInt(600) + 100;
		
			JFrame newFrame = new JFrame(name);
			newFrame.setSize(400, 250);
			newFrame.setLocation(x, y);
			newFrame.setLayout(null);
			
			JLabel nameLabel = new JLabel("Name: " + name);
			nameLabel.setSize(300, 25);
			nameLabel.setLocation(25, 10);
			
			JLabel locationLabel = new JLabel("Location: " + location);
			locationLabel.setSize(300, 25);
			locationLabel.setLocation(25, 35);
			
			JLabel subjectLabel = new JLabel("Subject: " + subject);
			subjectLabel.setSize(300, 25);
			subjectLabel.setLocation(25, 60);
			
			JLabel typeLabel = new JLabel("Type: " + type);
			typeLabel.setSize(300, 25);
			typeLabel.setLocation(25, 85);
			
			JButton fundButton = new JButton("Get Fund Raising Events");
			fundButton.setSize(200, 30);
			fundButton.setLocation(25, 120);
			fundButton.addActionListener(new fundAction(name));
			
			newFrame.add(nameLabel);
			newFrame.add(locationLabel);
			newFrame.add(subjectLabel);
			newFrame.add(typeLabel);
			newFrame.add(fundButton);
			
			newFrame.setVisible(true);
		
		
		}
		else{
			
			JOptionPane.showMessageDialog(null, "Query Failed");
			
		}
		
	}
	
	
	
	
	
	
	public static void displayTreatmentFrame(int id){
		
		//querying database for single research center
		String query = "SELECT * FROM treatmentrecords "
								+ "WHERE recordID = " + id;
						
		ResultSet info = null;
						
		Statement request;
		try {
							
			request = db.createStatement();
			info = request.executeQuery(query);
							
			info.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
							
		} 
		catch (SQLException e) {
							
			// do nothing
							
		}
		
		if(info != null){
			
			//assign variables
			int rid = 0;
			int pid = 0;
			String treatment = "";
			String response = "";
			
			try {
				
				rid = info.getInt("recordID");
				pid = info.getInt("patientID");
				treatment = info.getString("treatment");
				response = info.getString("response");
				
			} 
			catch (SQLException e) {
				
				//System.out.print("Failed To Retrieve Info");
				
			}	
			
			Random rand = new Random();
			
			int x = rand.nextInt(1500 ) + 100;
			int y = rand.nextInt(600) + 100;
		
			JFrame newFrame = new JFrame("Treatment Record " +id);
			newFrame.setSize(400, 200);
			newFrame.setLocation(x, y);
			newFrame.setLayout(null);
			
			JLabel idLabel = new JLabel("ID: " + id);
			idLabel.setSize(300, 25);
			idLabel.setLocation(25, 10);
			
			JLabel pidLabel = new JLabel("Patient ID: " + pid);
			pidLabel.setSize(300, 25);
			pidLabel.setLocation(25, 35);
			
			JLabel treatLabel = new JLabel("Treatment: " + treatment);
			treatLabel.setSize(300, 25);
			treatLabel.setLocation(25, 60);
			
			JLabel respLabel = new JLabel("Response: " + response);
			respLabel.setSize(300, 25);
			respLabel.setLocation(25, 85);
			
			newFrame.add(idLabel);
			newFrame.add(pidLabel);
			newFrame.add(treatLabel);
			newFrame.add(respLabel);
			
			newFrame.setVisible(true);
		
		
		}
		else{
			
			JOptionPane.showMessageDialog(null, "Query Failed");
			
		}
		
	}
	
	
	
	public static void displayFundingFrame(String event){
		
		//querying database for single research center
		String query = "SELECT * FROM funding "
								+ "WHERE event = \'" + event + "\'";
						
		ResultSet info = null;
						
		Statement request;
		try {
							
			request = db.createStatement();
			info = request.executeQuery(query);
							
			info.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
							
		} 
		catch (SQLException e) {
							
			// do nothing
							
		}
		
		if(info != null){
			
			//assign variables
			String eventName = "";
			String benefactor = "";
			String subject = "";
			String type = "";
			String location = "";
			int raised = 0;
			int target = 0;
			
			try {
				
				eventName = info.getString("event");
				benefactor = info.getString("benefactor");
				subject = info.getString("subject");
				type = info.getString("type");
				location = info.getString("location");
				raised = info.getInt("raised");
				target = info.getInt("target");
				
				
			} 
			catch (SQLException e) {
				
				//System.out.print("Failed To Retrieve Info");
				
			}	
			
			Random rand = new Random();
			
			int x = rand.nextInt(1500 ) + 100;
			int y = rand.nextInt(600) + 100;
		
			JFrame newFrame = new JFrame(eventName);
			newFrame.setSize(400, 250);
			newFrame.setLocation(x, y);
			newFrame.setLayout(null);
			
			JLabel nameLabel = new JLabel("Event Name: " + eventName);
			nameLabel.setSize(300, 25);
			nameLabel.setLocation(25, 10);
			
			JLabel benLabel = new JLabel("Benefactor: " + benefactor);
			benLabel.setSize(300, 25);
			benLabel.setLocation(25, 35);
			
			JLabel subLabel = new JLabel("Subject: " + subject);
			subLabel.setSize(300, 25);
			subLabel.setLocation(25, 60);
			
			JLabel typeLabel = new JLabel("Type Focussed On: " + type);
			typeLabel.setSize(300, 25);
			typeLabel.setLocation(25, 85);
			
			JLabel locLabel = new JLabel("Location: " + location);
			locLabel.setSize(300, 25);
			locLabel.setLocation(25, 110);
			
			JLabel raiseLabel = new JLabel("Funds Raised: " + raised);
			raiseLabel.setSize(300, 25);
			raiseLabel.setLocation(25, 135);
			
			JLabel targLabel = new JLabel("Target: " + target);
			targLabel.setSize(300, 25);
			targLabel.setLocation(25, 160);
			
			
			newFrame.add(nameLabel);
			newFrame.add(benLabel);
			newFrame.add(subLabel);
			newFrame.add(typeLabel);
			newFrame.add(locLabel);
			newFrame.add(raiseLabel);
			newFrame.add(targLabel);
			
			
			newFrame.setVisible(true);
		
		
		}
		else{
			
			JOptionPane.showMessageDialog(null, "Query Failed");
			
		}		
		
		
		
	}
	
	
	
	public static void displayStatFrame(String description, int stat, boolean isPercent){
		
		Random rand = new Random();
		
		int x = rand.nextInt(1500 ) + 100;
		int y = rand.nextInt(600) + 100;
		
		JFrame newFrame = new JFrame(description);
		newFrame.setSize(400, 200);
		newFrame.setLocation(x, y);
		newFrame.setLayout(null);
		
		JLabel descLabel = new JLabel(description + ":");
		descLabel.setSize(300, 25);
		descLabel.setLocation(10, 10);
		
		Font statFont = new Font("Dialog", Font.PLAIN, 50);
		
		JLabel statLabel = new JLabel(""+stat);
		statLabel.setSize(300, 50);
		statLabel.setLocation(10, 40);
		statLabel.setFont(statFont);
		
		if(isPercent)
			statLabel.setText(statLabel.getText() + "%");
		
		
		newFrame.add(descLabel);
		newFrame.add(statLabel);
		
		newFrame.setVisible(true);
		
		
	}
	
	
	
	
	public static class divideDrawer extends JPanel{
		
		public void paint(Graphics g){
			
			g.setColor(new Color(0, 0, 0));
			
			//vertical line
			g.fillRect(399, 0, 2, 600);
			
			
			//horizontal line
			g.fillRect(0, 299, 800, 2);
			
			
		}
	
	
	}
	
	
	
	
	public static Connection connector(){
		
		Connection conn = null;
		
		
		try{
			
			Class.forName("org.sqlite.JDBC");
			
			conn = DriverManager.getConnection("jdbc:sqlite:cancerResearchDB.sqlite");
			
			JOptionPane.showMessageDialog(null, "Connection Successful");
			
		}
		catch(Exception e){
			
			JOptionPane.showMessageDialog(null, "Connection Failed");
			
		}
		
		return conn;
		
	}
	
	
	
	
	public static void setCancerTypes(){
		
		//querying database for cancer types
		String query = "SELECT type FROM cancertype";
				
		ResultSet givenTypes = null;
				
		Statement request;
		try {
					
			request = db.createStatement();
			givenTypes = request.executeQuery(query);
					
			givenTypes.first(); // MOVE CURSOR TO FIRST ROW OF QUERY, DOES NOT DO AUTOMATICALLY
					
		} 
		catch (SQLException e) {
			
			// do nothing
			
		}
		
		for(int index = 0; index < 20; index++){
			
			try{
				
				statOptions.addItem(givenTypes.getString("type"));
				givenTypes.next();
				
			}
			catch(SQLException e){
				
				// do nothing
				
			}
			
		}	
		
	}
	
	
	
	
	public static void setLocations(){
		
		//querying database for locations
		String query = "SELECT name FROM researchfacilities";
						
		ResultSet locations = null;
						
		Statement request;
		try {
							
			request = db.createStatement();
			locations = request.executeQuery(query);
							
			locations.first(); // MOVE CURSOR TO FIRST ROW OF QUERY, DOES NOT DO AUTOMATICALLY
							
		} 
		catch (SQLException e) {
					
			// do nothing
					
		}
		
		boolean caught = false;
		while(!caught){
			
			try{
				
				researchOptions.addItem(locations.getString("name"));
				locations.next();
				
			}
			catch(SQLException e){
				
				caught = true;
				
			}
			
		}
		
	}
	
	
	
	public static void setGraphs(){
		
		// queries will be in each chart
		graphOptions.addItem("Deaths by Age");
		graphOptions.addItem("Deaths by Weight");
		
		graphOptions.addItem("Type Pie Chart");
		graphOptions.addItem("Deaths by Type Pie Chart");
		
		
	}
	
	
	
	///////////////////////////////////////////////////////////////////////
	//
	//
	//  GRAPHING FRAME FUNCTIONS
	//
	//
	//////////////////////////////////////////////////////////////////////
	
	
	
	public static void displayTypePieChartFrame(){
		
		int typeCounts[] = new int[20];
		
		boolean found = false;
		
		//querying database for 
		String breastquery = "SELECT * FROM breasttotal";
		String prostatequery = "SELECT * FROM prostatetotal";
		String lungquery = "SELECT * FROM lungtotal";
		String pancreaticquery = "SELECT * FROM pancreatictotal";
		String throatquery = "SELECT * FROM throattotal";
		String leukemiaquery = "SELECT * FROM leukemiatotal";
		String skinquery = "SELECT * FROM skintotal";
		String cervicalquery = "SELECT * FROM cervicaltotal";
		String colonquery = "SELECT * FROM colontotal";
		String esophagealquery = "SELECT * FROM esophagealtotal";
		String retinoblastomaquery = "SELECT * FROM retinoblastomatotal";
		String ovarianquery = "SELECT * FROM ovariantotal";
		String gastroquery = "SELECT * FROM gastrointestinaltotal";
		String liverquery = "SELECT * FROM livertotal";
		String oralquery = "SELECT * FROM oraltotal";
		String penilequery = "SELECT * FROM peniletotal";
		String rectalquery = "SELECT * FROM rectaltotal";
		String thyroidquery = "SELECT * FROM thyroidtotal";
		String testicularquery = "SELECT * FROM testiculartotal";
		String neuroquery = "SELECT * FROM neurototal";
										
		ResultSet info = null;
										
		Statement request;
		try {
											
			request = db.createStatement();
			info = request.executeQuery(breastquery);
			//info.first(); 
			
			typeCounts[0] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(prostatequery);
			//info.first(); 
			
			typeCounts[1] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(lungquery);
			//info.first(); 
			
			typeCounts[2] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(pancreaticquery);
			//info.first(); 
			
			typeCounts[3] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(throatquery);
			//info.first(); 
			
			typeCounts[4] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(leukemiaquery);
			//info.first(); 
			
			typeCounts[5] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(skinquery);
			//info.first(); 
			
			typeCounts[6] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(cervicalquery);
			//info.first(); 
			
			typeCounts[7] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(colonquery);
			//info.first(); 
			
			typeCounts[8] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(esophagealquery);
			//info.first(); 
			
			typeCounts[9] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(retinoblastomaquery);
			//info.first(); 
			
			typeCounts[10] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(ovarianquery);
			//info.first(); 
			
			typeCounts[11] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(gastroquery);
			//info.first(); 
			
			typeCounts[12] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(liverquery);
			//info.first(); 
			
			typeCounts[13] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(oralquery);
			//info.first(); 
			
			typeCounts[14] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(penilequery);
			//info.first(); 
			
			typeCounts[15] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(rectalquery);
			//info.first(); 
			
			typeCounts[16] = info.getInt("count(*)");
			
			request = db.createStatement();
			info = request.executeQuery(thyroidquery);
			//info.first(); 
			
			typeCounts[17] = info.getInt("count(*)");
			
			request = db.createStatement();
			info = request.executeQuery(testicularquery);
			//info.first(); 
			
			typeCounts[18] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(neuroquery);
			//info.first(); 
			
			typeCounts[19] = info.getInt("count(*)");
			
			
			found = true;
											
		} 
		catch (SQLException e) {
											
			e.printStackTrace();
											
		}
		
		
		
		if(found){
		
			JFrame newFrame = new JFrame("Cancer Patients Divided By Type");
			newFrame.setSize(800, 600);
			newFrame.setLocation(400, 400);
			
			JLabel titleLabel = new JLabel("All Patients Divided By Cancer Type");
			titleLabel.setSize(200, 25);
			titleLabel.setLocation(200, 10);
			
			JLabel legend[] = new JLabel[20];
			
			legend[0] = new JLabel("Breast");
			legend[1] = new JLabel("Prostate");
			legend[2] = new JLabel("Lung");
			legend[3] = new JLabel("Pancreatic");
			legend[4] = new JLabel("Throat");
			legend[5] = new JLabel("Leukemia");
			legend[6] = new JLabel("Skin");
			legend[7] = new JLabel("Cervical");
			legend[8] = new JLabel("Colon");
			legend[9] = new JLabel("Esophageal");
			legend[10] = new JLabel("Retino Blastoma");
			legend[11] = new JLabel("Ovarian");
			legend[12] = new JLabel("Gastro Intesinal");
			legend[13] = new JLabel("Liver");
			legend[14] = new JLabel("Oral");
			legend[15] = new JLabel("Penile");
			legend[16] = new JLabel("Rectal");
			legend[17] = new JLabel("Thyroid");
			legend[18] = new JLabel("Testicular");
			legend[19] = new JLabel("Neuro");
			
			for(int index = 0; index < 20; index++){
				
				legend[index].setSize(125, 25);
				legend[index].setLocation(565, 15 + (25*index));
				
				newFrame.add(legend[index]);
				
			}
			
		
			pieChartDrawer chart = new pieChartDrawer(typeCounts);
			
			
			newFrame.add(titleLabel);
			
			newFrame.add(chart); // must be on bottom
		
			newFrame.setVisible(true);
		
		}
		
	}
	
	
	public static void displayDeathsByTypeChart(){
		
		int typeCounts[] = new int[20];
		
		boolean found = false;
		
		//querying database for 
		String breastquery = "SELECT * FROM breastdeaths";
		String prostatequery = "SELECT * FROM prostatedeaths";
		String lungquery = "SELECT * FROM lungdeaths";
		String pancreaticquery = "SELECT * FROM pancreaticdeaths";
		String throatquery = "SELECT * FROM throatdeaths";
		String leukemiaquery = "SELECT * FROM leukemiadeaths";
		String skinquery = "SELECT * FROM skindeaths";
		String cervicalquery = "SELECT * FROM cervicaldeaths";
		String colonquery = "SELECT * FROM colondeaths";
		String esophagealquery = "SELECT * FROM esophagealdeaths";
		String retinoblastomaquery = "SELECT * FROM retinoblastomadeaths";
		String ovarianquery = "SELECT * FROM ovariandeaths";
		String gastroquery = "SELECT * FROM gastrointestinaldeaths";
		String liverquery = "SELECT * FROM liverdeaths";
		String oralquery = "SELECT * FROM oraldeaths";
		String penilequery = "SELECT * FROM peniledeaths";
		String rectalquery = "SELECT * FROM rectaldeaths";
		String thyroidquery = "SELECT * FROM thyroiddeaths";
		String testicularquery = "SELECT * FROM testiculardeaths";
		String neuroquery = "SELECT * FROM neurodeaths";
										
		ResultSet info = null;
										
		Statement request;
		try {
											
			request = db.createStatement();
			info = request.executeQuery(breastquery);
			//info.first(); 
			
			typeCounts[0] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(prostatequery);
			//info.first(); 
			
			typeCounts[1] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(lungquery);
			//info.first(); 
			
			typeCounts[2] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(pancreaticquery);
			//info.first(); 
			
			typeCounts[3] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(throatquery);
			//info.first(); 
			
			typeCounts[4] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(leukemiaquery);
			//info.first(); 
			
			typeCounts[5] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(skinquery);
			//info.first(); 
			
			typeCounts[6] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(cervicalquery);
			//info.first(); 
			
			typeCounts[7] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(colonquery);
			//info.first(); 
			
			typeCounts[8] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(esophagealquery);
			//info.first(); 
			
			typeCounts[9] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(retinoblastomaquery);
			//info.first(); 
			
			typeCounts[10] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(ovarianquery);
			//info.first(); 
			
			typeCounts[11] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(gastroquery);
			//info.first(); 
			
			typeCounts[12] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(liverquery);
			//info.first(); 
			
			typeCounts[13] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(oralquery);
			//info.first(); 
			
			typeCounts[14] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(penilequery);
			//info.first(); 
			
			typeCounts[15] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(rectalquery);
			//info.first(); 
			
			typeCounts[16] = info.getInt("count(*)");
			
			request = db.createStatement();
			info = request.executeQuery(thyroidquery);
			//info.first(); 
			
			typeCounts[17] = info.getInt("count(*)");
			
			request = db.createStatement();
			info = request.executeQuery(testicularquery);
			//info.first(); 
			
			typeCounts[18] = info.getInt("count(*)");
			
			
			request = db.createStatement();
			info = request.executeQuery(neuroquery);
			//info.first(); 
			
			typeCounts[19] = info.getInt("count(*)");
			
			
			found = true;
											
		} 
		catch (SQLException e) {
											
			// do nothing
											
		}
		
		
		
		if(found){
		
			JFrame newFrame = new JFrame("Total Deaths Divided By Type");
			newFrame.setSize(800, 600);
			newFrame.setLocation(400, 400);
			
			JLabel titleLabel = new JLabel("Total Deaths Divided By Type");
			titleLabel.setSize(200, 25);
			titleLabel.setLocation(200, 10);
			
			JLabel legend[] = new JLabel[20];
			
			legend[0] = new JLabel("Breast");
			legend[1] = new JLabel("Prostate");
			legend[2] = new JLabel("Lung");
			legend[3] = new JLabel("Pancreatic");
			legend[4] = new JLabel("Throat");
			legend[5] = new JLabel("Leukemia");
			legend[6] = new JLabel("Skin");
			legend[7] = new JLabel("Cervical");
			legend[8] = new JLabel("Colon");
			legend[9] = new JLabel("Esophageal");
			legend[10] = new JLabel("Retino Blastoma");
			legend[11] = new JLabel("Ovarian");
			legend[12] = new JLabel("Gastro Intesinal");
			legend[13] = new JLabel("Liver");
			legend[14] = new JLabel("Oral");
			legend[15] = new JLabel("Penile");
			legend[16] = new JLabel("Rectal");
			legend[17] = new JLabel("Thyroid");
			legend[18] = new JLabel("Testicular");
			legend[19] = new JLabel("Neuro");
			
			for(int index = 0; index < 20; index++){
				
				legend[index].setSize(125, 25);
				legend[index].setLocation(565, 15 + (25*index));
				
				newFrame.add(legend[index]);
				
			}
			
		
			pieChartDrawer chart = new pieChartDrawer(typeCounts);
			
			
			newFrame.add(titleLabel);
			
			newFrame.add(chart); // must be on bottom
		
			newFrame.setVisible(true);
		
		}
		
	}
		
	
		
	
	
	
	
	
	
	
	
	
	
	public static void displayDeathsByAgeFrame(){
		
		// collect data USE COUNT ONCE DB IS FINALIZED
		int twenties = 0;
		int thirties = 0;
		int fourties = 0;
		int fifties = 0;
		int sixties = 0;
		int seventies = 0;
		int eighties = 0;
		int nineties = 0;
		
		//querying database for single research center
		String query = "SELECT age FROM allpatients "
				+ "WHERE fate = 'died'";
								
		ResultSet info = null;
								
		Statement request;
		try {
									
			request = db.createStatement();
			info = request.executeQuery(query);
									
			info.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
									
		} 
		catch (SQLException e) {
									
			// do nothing
									
		}
		
		
		if(info != null){
			
			boolean caught = false;
			
			while(!caught){
				
				try{
					int current = info.getInt("age");
					
					if(current < 30)
						twenties++;
					else if(current >= 30 && current < 40)
						thirties++;
					else if(current >= 40 && current < 50)
						fourties++;
					else if(current >= 50 && current < 60)
						fifties++;
					else if(current >= 60 && current < 70)
						sixties++;
					else if(current >= 70 && current < 80)
						seventies++;
					else if(current >= 80 && current < 90)
						eighties++;
					else if(current >= 90)
						nineties++;
					
					info.next();
					
				}
				catch(SQLException e){
					
					caught = true;
					
				}
				
				
				
				
				
			}
			
			
			//SCALING 
			twenties /= 200;
			thirties /= 200;
			fourties /= 200;
			fifties /= 200;
			sixties /= 200;
			seventies /= 200;
			eighties /= 200;
			nineties /= 200;
			
			
			// draw frame
			JFrame newFrame = new JFrame("Deaths By Age");
			newFrame.setSize(800, 600);
			newFrame.setLocation(300, 300);
			
			barDrawer bar20s = new barDrawer(twenties); 
			bar20s.setLocation(120, 500 - twenties);
			bar20s.setSize(50, twenties);
			
			JLabel L20s = new JLabel("20s");
			L20s.setSize(50, 30);
			L20s.setLocation(140, 510);
			
			barDrawer bar30s = new barDrawer(thirties); 
			bar30s.setLocation(190, 500 - thirties);
			bar30s.setSize(50, thirties);
			
			JLabel L30s = new JLabel("30s");
			L30s.setSize(50, 30);
			L30s.setLocation(210, 510);
			
			barDrawer bar40s = new barDrawer(fourties); 
			bar40s.setLocation(260, 500 - fourties);
			bar40s.setSize(50, fourties);
			
			JLabel L40s = new JLabel("40s");
			L40s.setSize(50, 30);
			L40s.setLocation(280, 510);
			
			barDrawer bar50s = new barDrawer(fifties); 
			bar50s.setLocation(330, 500 - fifties);
			bar50s.setSize(50, fifties);
			
			JLabel L50s = new JLabel("50s");
			L50s.setSize(50, 30);
			L50s.setLocation(350, 510);
			
			barDrawer bar60s = new barDrawer(sixties); 
			bar60s.setLocation(400, 500 - sixties);
			bar60s.setSize(50, sixties);
			
			JLabel L60s = new JLabel("60s");
			L60s.setSize(50, 30);
			L60s.setLocation(420, 510);
		
			barDrawer bar70s = new barDrawer(seventies); 
			bar70s.setLocation(470, 500 - seventies);
			bar70s.setSize(50, seventies);
			
			JLabel L70s = new JLabel("70s");
			L70s.setSize(50, 30);
			L70s.setLocation(490, 510);
			
			barDrawer bar80s = new barDrawer(eighties); 
			bar80s.setLocation(540, 500 - eighties);
			bar80s.setSize(50, eighties);
			
			JLabel L80s = new JLabel("80s");
			L80s.setSize(50, 30);
			L80s.setLocation(560, 510);
			
			barDrawer bar90s = new barDrawer(nineties); 
			bar90s.setLocation(610, 500 - nineties);
			bar90s.setSize(50, nineties);
			
			JLabel L90s = new JLabel("90s");
			L90s.setSize(50, 30);
			L90s.setLocation(630, 510);
			
			axisDrawer axis = new axisDrawer();
			axis.setSize(800, 600);
			axis.setLocation(0, 0);
			
			JLabel axisLabel1 = new JLabel("20K");
			axisLabel1.setSize(50, 30);
			axisLabel1.setLocation(60, 385);
			
			JLabel axisLabel2 = new JLabel("40K");
			axisLabel2.setSize(50, 30);
			axisLabel2.setLocation(60, 285);
			
			JLabel axisLabel3 = new JLabel("60K");
			axisLabel3.setSize(50, 30);
			axisLabel3.setLocation(60, 185);
			
			JLabel axisLabel4 = new JLabel("80K");
			axisLabel4.setSize(50, 30);
			axisLabel4.setLocation(60, 85);
			
			JLabel XLabel = new JLabel("Age Range");
			XLabel.setSize(150, 30);
			XLabel.setLocation(350, 530);
			
			JLabel YLabel = new JLabel("Deaths");
			YLabel.setSize(50, 30);
			YLabel.setLocation(20, 250);
			
			JLabel titleLabel = new JLabel("Deaths By Age");
			titleLabel.setSize(150, 10);
			titleLabel.setLocation(350, 25);
			
			
			newFrame.add(L20s);
			newFrame.add(L30s);
			newFrame.add(L40s);
			newFrame.add(L50s);
			newFrame.add(L60s);
			newFrame.add(L70s);
			newFrame.add(L80s);
			newFrame.add(L90s);
			
			newFrame.add(bar20s);
			newFrame.add(bar30s);
			newFrame.add(bar40s);
			newFrame.add(bar50s);
			newFrame.add(bar60s);
			newFrame.add(bar70s);
			newFrame.add(bar80s);
			newFrame.add(bar90s);
			
			newFrame.add(axisLabel1);
			newFrame.add(axisLabel2);
			newFrame.add(axisLabel3);
			newFrame.add(axisLabel4);
			
			newFrame.add(XLabel);
			newFrame.add(YLabel);
			
			newFrame.add(titleLabel);
		
			newFrame.add(axis); // must be on bottom
		
		
			newFrame.setVisible(true);
		
		}
		else{
			
			JOptionPane.showMessageDialog(null, "Query Failed");
			
		}
	}
	
	
	
	
	
	public static void displayDeathsByWeight(){
		
		
		int w100 = 0;
		int w150 = 0;
		int w200 = 0;
		int w250 = 0;
				
		//querying database for single research center
		String query = "SELECT weight FROM allpatients "
						+ "WHERE fate = 'died'";
										
		ResultSet info = null;
										
		Statement request;
		try {
											
			request = db.createStatement();
			info = request.executeQuery(query);
											
			info.first(); // MOVE CURSOR TO ONLY ROW OF QUERY, DOES NOT DO AUTOMATICALLY
											
		} 
		catch (SQLException e) {
											
			// do nothing
											
		}
				
				
		if(info != null){
					
			boolean caught = false;
					
			while(!caught){
						
				try{
					int current = info.getInt("weight");
							
					if(current < 150)
						w100++;
					else if(current >= 150 && current < 200)
						w150++;
					else if(current >= 200 && current < 250)
						w200++;
					else if(current >= 250)
						w250++;
							
					info.next();
							
				}
				catch(SQLException e){
							
					caught = true;
							
				}
					
			}
					
					
			//SCALING 
			w100 /= 400;
			w150 /= 400;
			w200 /= 400;
			w250 /= 400;
					
					
			// draw frame
			JFrame newFrame = new JFrame("Deaths By Weight");
			newFrame.setSize(800, 600);
			newFrame.setLocation(300, 300);
					
			barDrawer bar100 = new barDrawer(w100); 
			bar100.setLocation(140, 500 - w100);
			bar100.setSize(50, w100);
					
			JLabel L100 = new JLabel("100-149");
			L100.setSize(50, 30);
			L100.setLocation(140, 510);
					
			barDrawer bar150 = new barDrawer(w150); 
			bar150.setLocation(280, 500 - w150);
			bar150.setSize(50, w150);
					
			JLabel L150 = new JLabel("150-199");
			L150.setSize(50, 30);
			L150.setLocation(280, 510);
					
			barDrawer bar200 = new barDrawer(w200); 
			bar200.setLocation(420, 500 - w200);
			bar200.setSize(50, w200);
					
			JLabel L200 = new JLabel("200-249");
			L200.setSize(50, 30);
			L200.setLocation(420, 510);
					
			barDrawer bar250 = new barDrawer(w250); 
			bar250.setLocation(560, 500 - w250);
			bar250.setSize(50, w250);
					
			JLabel L250 = new JLabel("250-300");
			L250.setSize(50, 30);
			L250.setLocation(560, 510);
					
					
					
			axisDrawer axis = new axisDrawer();
			axis.setSize(800, 600);
			axis.setLocation(0, 0);
					
			JLabel axisLabel1 = new JLabel("40K");
			axisLabel1.setSize(50, 30);
			axisLabel1.setLocation(60, 385);
					
			JLabel axisLabel2 = new JLabel("80K");
			axisLabel2.setSize(50, 30);
			axisLabel2.setLocation(60, 285);
					
			JLabel axisLabel3 = new JLabel("120K");
			axisLabel3.setSize(50, 30);
			axisLabel3.setLocation(60, 185);
					
			JLabel axisLabel4 = new JLabel("160K");
			axisLabel4.setSize(50, 30);
			axisLabel4.setLocation(60, 85);
					
			JLabel XLabel = new JLabel("Weight Range (lbs.)");
			XLabel.setSize(150, 30);
			XLabel.setLocation(350, 530);
					
			JLabel YLabel = new JLabel("Deaths");
			YLabel.setSize(50, 30);
			YLabel.setLocation(20, 250);
					
			JLabel titleLabel = new JLabel("Deaths By Weight");
			titleLabel.setSize(150, 10);
			titleLabel.setLocation(350, 25);
					
					
			newFrame.add(L100);
			newFrame.add(L150);
			newFrame.add(L200);
			newFrame.add(L250);
					
			newFrame.add(bar100);
			newFrame.add(bar150);
			newFrame.add(bar200);
			newFrame.add(bar250);
					
			newFrame.add(axisLabel1);
			newFrame.add(axisLabel2);
			newFrame.add(axisLabel3);
			newFrame.add(axisLabel4);
					
			newFrame.add(XLabel);
			newFrame.add(YLabel);
					
			newFrame.add(titleLabel);
				
			newFrame.add(axis); // must be on bottom
				
				
			newFrame.setVisible(true);
				
		}
		else{
					
			JOptionPane.showMessageDialog(null, "Query Failed");
					
		}		
		
	}
	
	
	
	
	
	
	
	
	
	public static class axisDrawer extends JPanel{
		
		public void paint(Graphics g){
			
			g.setColor(new Color(0, 0, 0));
			
			//vertical line
			g.fillRect(98, 0, 2, 600);
			
			
			//horizontal line
			g.fillRect(0, 500, 800, 2);
			
			
			// markers
			g.fillRect(90, 450, 10, 2);
			g.fillRect(90, 400, 10, 2);
			g.fillRect(90, 350, 10, 2);
			g.fillRect(90, 300, 10, 2);
			g.fillRect(90, 250, 10, 2);
			g.fillRect(90, 200, 10, 2);
			g.fillRect(90, 150, 10, 2);
			g.fillRect(90, 100, 10, 2);
			g.fillRect(90, 50, 10, 2);
			
		}
	
	
	}
	
	public static class barDrawer extends JPanel{
		
		private int ht;
		
		public barDrawer(int height){
			
			ht = height;
			
		}
		
		
		public void paint(Graphics g){
			
			g.setColor(new Color(0, 0, 0));
			
			//bar
			g.fillRect(0, 0, 50, ht);
			
		}
	
	
	}
	
	
	public static class pieChartDrawer extends JPanel{
		
		private int[] values;
		private Color[] colors;
		
		public pieChartDrawer(int[] v){
			
			values = v;
			
			colors = new Color[20];
			
			// explicitly set all possible chart colors
			colors[0] = new Color(255, 0, 0); // red
			colors[1] = new Color(0 , 255, 0); // green
			colors[2] = new Color(0 , 0, 255); // blue
			colors[3] = new Color(255 , 255, 0); // something
			colors[4] = new Color(255 , 0, 255); // something
			colors[5] = new Color(0 , 255, 255); // something
			colors[6] = new Color(35 , 120, 200); // aqua?
			colors[7] = new Color(150 , 35, 250); // purple
			colors[8] = new Color(240 , 100, 30); // orange
			colors[9] = new Color(125, 125, 125); // grey
			colors[10] = new Color(140 , 100, 60); // brown
			colors[11] = new Color(100 , 135, 70); // earthy green?
			colors[12] = new Color(65 , 85, 145); // steel blue
			colors[13] = new Color(90 , 60, 150); // violet
			colors[14] = new Color(30 , 30, 30); // dark grey
			colors[15] = new Color(125 , 125, 255); // ugly
			colors[16] = new Color(230 , 150, 200); // pink ish
			colors[17] = new Color(125 , 125, 0); // something
			colors[18] = new Color(125 , 0, 0); // burgundy
			colors[19] = new Color(0 , 125, 0); // awkward green
			
		}
		
		public void paint(Graphics g){
			
			Graphics2D g2 = (Graphics2D)g;
			
			double sum = 0;
			
			for(int index = 0; index < values.length; index++){
				
				sum += values[index];
					
			}
			
			double previousAngle = 0;
			
			for(int index = 0; index < values.length; index++){
				
				g.setColor(colors[index]);
				g2.setColor(colors[index]);
				
				g.fillRect(540, 20 + index*25, 20, 20); // legend squares
				
				double currentAngle = (values[index]/sum) * 360;
				
				Arc2D arc = new Arc2D.Double(20, 40, 500, 500, previousAngle, currentAngle, Arc2D.PIE);
				
				g2.fill(arc);
				
				previousAngle += currentAngle;
				
			}
			
			
		}
		
		
		
		
	}
	
	
	
	
}

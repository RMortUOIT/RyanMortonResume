package com.runtime;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;

import javax.swing.*;



/////////////////////////////////////////////
//
//
//Created 1 million patients with values weighted to mirror real statistics
//
//
///////////////////////////////////////////////////




public class generator {
	
	public static JFrame mainFrame;
	public static JButton randomButton;
	
	public static Connection db;
	
	
	public static void main(String[] args){
		
		
		
		mainFrame = new JFrame("Cancer Research Prototype");
		mainFrame.setSize(400, 300);
		mainFrame.setLocation(100, 100);
		mainFrame.setLayout(null);
		
		randomButton = new JButton("Randomize");
		randomButton.setSize(360, 180);
		randomButton.setLocation(10, 10);
		randomButton.addActionListener(new randomAction());
		
		mainFrame.add(randomButton);
		
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		db = connector();
		
		
	}
	
	public static class randomAction implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			for( int index = 0; index <= 1000000; index++){
				
				System.out.println(index);
				
				////////////////////////////////////
				// query start
				////////////////////////////////////
				
				Random rand = new Random();
				
				int age = getRandomAge();
				
				String pairing[] = getPairing();
				
				String sex = pairing[0];
				String type = pairing[1];
				
				boolean survived = getSurvival(type);
				
				int fateNumber = rand.nextInt(2) + 1;
				
				String fate = "";
				
				if(survived){
					
					if(fateNumber == 1)
						fate = "remission";
					else
						fate = "ongoing";
					
				}
				else 
					fate = "died";
				
				int weight = rand.nextInt(200) + 100;
				
				int height = rand.nextInt(50) + 150;
				
				
				
				String query = "INSERT INTO allpatients VALUES (" + Integer.toString(index) + 
						"," + Integer.toString(age) + "," + Integer.toString(height) + 
						"," +  Integer.toString(weight)   + ",\"" + sex +"\"" + ",\"" + type +"\""
						+ ",\"" + fate +"\"" +")";
				
				
				//System.out.println(query); // FOR TESTING
				
				
			
				try{
				
					//PreparedStatement addPst = dbConnect.prepareStatement(query);
					//addPst.executeQuery(); // since no input
				
					Statement addStatement = db.createStatement();
				
					addStatement.executeUpdate(query);
				
				
				}catch(Exception error){
			
					JOptionPane.showMessageDialog(null, "An Error Occured");
					System.err.println(error.getMessage());
			
				}
				
				
				///////////////////////////////////////
				// query end
				//////////////////////////////////////
				
			}
			
		}
		
	}
	
	
	public static int getRandomAge(){
		
		Random rand = new Random();
		
		int gen = rand.nextInt(100) + 1; // to decide tens digit
		
		int offset = rand.nextInt(9)+1; // to decide ones digit
		
		int age = 0;
		
		if( gen < 3 )
			age = 20 + offset;
		else if( gen >= 4 && gen <= 6 )
			age = 30 + offset;
		else if( gen >= 7 && gen <= 10 )
			age = 40 + offset;
		else if( gen >= 11 && gen <= 25 )
			age = 50 + offset;
		else if( gen >= 26 && gen <= 40 )
			age = 60 + offset;
		else if( gen >= 41 && gen <= 60 )
			age = 70 + offset;
		else if( gen >= 61 && gen <= 80 )
			age = 80 + offset;
		else if( gen >= 81 && gen <= 100 )
			age = 90 + offset;
		
		
		return age;
		
	}
	
	// returns sex and type of cancer
	public static String[] getPairing(){
		
		String pairing[] = new String[2];
		
		String sex = "";
		String type = "";
		
		Random rand = new Random();
		
		int sexRand = rand.nextInt(2) + 1;
		
		if(sexRand == 1)
			sex = "M";
		else
			sex = "F";
		
		
		int typeRand = rand.nextInt(100) + 1;
		int perRand = rand.nextInt(100) + 1;
		
		if( typeRand < 24 ){
			
			if( sex.equals("M") )
				type = "Prostate";
			else
				type = "Breast";
			
		}
		else if( typeRand >= 25 && typeRand <= 34 ){
			
			if( sex.equals("M") ){
				
				if( perRand > 80 )
					type = "Penile";
				else
					type = "Testicular";
				
			}
			else{
				
				if( perRand > 40 )
					type = "Cervical";
				else
					type = "Ovarian";
				
			}
			
		}
		else if( typeRand >= 35 && typeRand <= 47 )
			type = "Lung";
		else if( typeRand >= 48 && typeRand <= 50 )
			type = "Pancreatic";
		else if( typeRand == 51 )
			type = "Throat";
		else if( typeRand >= 52 && typeRand <= 54 )
			type = "Leukemia";
		else if( typeRand >= 55 && typeRand <= 58 )
			type = "Skin";
		else if( typeRand >= 59 && typeRand <= 63 )
			type = "Colon";
		else if( typeRand == 64 )
			type = "Esophageal";
		else if( typeRand == 65 )
			type = "Retino Blastoma";
		else if( typeRand >= 66 && typeRand <= 75 )
			type = "Gastro Intestinal";
		else if( typeRand >= 76 && typeRand <= 86 )
			type = "Liver";
		else if( typeRand >= 87 && typeRand <= 88 )
			type = "Oral";
		else if( typeRand >= 89 && typeRand <= 94 )
			type = "Rectal";
		else if( typeRand >= 95 && typeRand <= 98 )
			type = "Thyroid";
		else if( typeRand >= 99 )
			type = "Neuro";
		
		
		pairing[0] = sex;
		pairing[1] = type;
		
		return pairing;
		
	}
	
	public static boolean getSurvival(String type){
		
		boolean survived = false;
		
		Random rand = new Random();
		
		int survRand = rand.nextInt(100) + 1;
		
		if( type.equals("Breast")){
			
			if( survRand < 78 )
				survived = true;
				
		}
		else if( type.equals("Prostate")){
			
			if( survRand < 84 )
				survived = true;
				
		}
		else if( type.equals("Lung")){
			
			if( survRand < 18 )
				survived = true;
				
		}
		else if( type.equals("Neuro")){
			
			if( survRand < 18 )
				survived = true;
				
		}
		else if( type.equals("Pancreatic")){
			
			if( survRand < 6 )
				survived = true;
				
		}
		else if( type.equals("Throat")){
			
			if( survRand < 19 )
				survived = true;
				
		}
		else if( type.equals("Leukemia")){
			
			if( survRand < 48 )
				survived = true;
				
		}
		else if( type.equals("Skin")){
			
			if( survRand < 82 )
				survived = true;
				
		}
		else if( type.equals("Cervical")){
			
			if( survRand < 65 )
				survived = true;
				
		}
		else if( type.equals("Colon")){
			
			if( survRand < 61 )
				survived = true;
				
		}
		else if( type.equals("Esophageal")){
			
			if( survRand < 19 )
				survived = true;
				
		}
		else if( type.equals("Retino Blastoma")){
			
			if( survRand < 91 )
				survived = true;
				
		}
		else if( type.equals("Ovarian")){
			
			if( survRand < 46 )
				survived = true;
				
		}
		else if( type.equals("Gastro Intestinal")){
			
			if( survRand < 77 )
				survived = true;
				
		}
		else if( type.equals("Liver")){
			
			if( survRand < 12 )
				survived = true;
				
		}
		else if( type.equals("Oral")){
			
			if( survRand < 68 )
				survived = true;
				
		}
		else if( type.equals("Penile")){
			
			if( survRand < 73 )
				survived = true;
				
		}
		else if( type.equals("Rectal")){
			
			if( survRand < 63 )
				survived = true;
				
		}
		else if( type.equals("Thyroid")){
			
			if( survRand < 91 )
				survived = true;
				
		}
		else if( type.equals("Testicular")){
			
			if( survRand < 94 )
				survived = true;
				
		}
		
		
		return survived;
		
	}
	
	
	
	
	
	public static Connection connector(){
		
		Connection conn = null;
		
		
		
		Statement stmt = null;
		
		try{
			
			Class.forName("org.sqlite.JDBC");
			
			conn = DriverManager.getConnection("jdbc:sqlite:cancerResearchDB.sqlite");  // can only be run on local pc
			
			JOptionPane.showMessageDialog(null, "Connection Successful"); // for testing
			
			stmt = conn.createStatement();
			
			//String sql = "CREATE DATABASE cancerResearchPrototype";
			
			//stmt.executeUpdate(sql);
			
			
			
		}catch(Exception e){
		
		
			System.out.println("FAIL");
		
		
		}
		
		
		
		return conn;
		
	}
	
	
	
	
	
	
	
}
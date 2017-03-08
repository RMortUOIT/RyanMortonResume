package com.runtime;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;

import javax.swing.*;

public class GUI {
	
	private static String ADDRESS = "http://192.168.2.15:9214/";
	
	//GLOBALS
	private static String userName;
	private static String key;
	private static String manifestRaw;
	private static boolean isLogoutMode;
	private static boolean cancelLoad;
	
	private static Encoder encoder;
	
	private static Poster MasterPoster;
	
	//Frames
	private static JFrame loginFrame;
	private static JFrame settingsFrame;
	private static JFrame mainFrame;
	private static JFrame deleteCheckFrame;
	
	//Login items
	private static JLabel userNameLoginLabel;
	private static JTextField userNameLoginField;
	private static JLabel userPasswordLabel;
	private static JPasswordField userPasswordField;
	private static JButton userLoginButton;
	private static JButton settingsButton;
	
	//settings items
	private static JLabel addressLabel;
	private static JTextField addressField;
	private static JButton settingsDiscardButton;
	private static JButton settingsApplyButton;
	
	//MainFrame items
	private static JLabel uploadLabel;
	private static JLabel pathLabel;
	private static JTextField pathField;
	private static JCheckBox publicCheck;
	private static JButton uploadButton;
	
	private static JLabel downloadLabel;
	private static JLabel selectFileLabel;
	private static JComboBox<String> selectFileCombo;
	private static JCheckBox otherUserCheck;
	private static JComboBox<String> selectUserCombo;
	private static JButton downloadButton;
	private static JButton deleteButton;
	
	private static JProgressBar loadProgress;
	private static JLabel progressLabel;
	private static JButton logoutCancelButton; //handles cancel and logout actions
	
	
	
	//delete check items
	private static JLabel checkLabel;
	private static JButton yesButton;
	private static JButton cancelButton;
	
	
	
	//Fonts
	private static Font medFont;
	private static Font lgFont;
	
	public static void main(String[] args){
		
		encoder = new Encoder();
		
		MasterPoster = new Poster();
		
		medFont = new Font("SanSerif", Font.PLAIN, 20);
		lgFont = new Font("SanSerif", Font.PLAIN, 40);
		
		setupLogin();
		
		loginFrame.setVisible(true);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
	}
	
	
	
	
	
	///////////////////////////////////////////
	//
	// GUI SET UP TOOLS
	//
	//////////////////////////////
	private static void setupLogin(){
		
		loginFrame = new JFrame("Login");
		loginFrame.setSize(350, 300);
		loginFrame.setLocation(300, 300);
		loginFrame.setLayout(null);
		loginFrame.setResizable(false);
		
		userNameLoginLabel = new JLabel("User Name:");
		userNameLoginLabel.setSize(200, 30);
		userNameLoginLabel.setLocation(10, 10);
		userNameLoginLabel.setFont(medFont);
		
		userNameLoginField = new JTextField("");
		userNameLoginField.setSize(300, 40);
		userNameLoginField.setLocation(10, 50);
		userNameLoginField.setFont(medFont);
		
		userPasswordLabel = new JLabel("Password:");
		userPasswordLabel.setSize(200, 30);
		userPasswordLabel.setLocation(10, 100);
		userPasswordLabel.setFont(medFont);
		
		userPasswordField = new JPasswordField("");
		userPasswordField.setSize(300, 40);
		userPasswordField.setLocation(10, 140);
		userPasswordField.setFont(medFont);
		
		userLoginButton = new JButton("Login");
		userLoginButton.setSize(100, 40);
		userLoginButton.setLocation(210, 190);
		userLoginButton.addActionListener(new loginListener());
		userLoginButton.setFont(medFont);
		
		settingsButton = new JButton("Settings");
		settingsButton.setSize(120, 40);
		settingsButton.setLocation(10, 190);
		settingsButton.addActionListener(new settingsListener());
		settingsButton.setFont(medFont);
		
		loginFrame.add(userNameLoginLabel);
		loginFrame.add(userNameLoginField);
		loginFrame.add(userPasswordLabel);
		loginFrame.add(userPasswordField);
		loginFrame.add(userLoginButton);
		loginFrame.add(settingsButton);
		
	}
	
	
	private static void setupSettingsFrame(){
		
		settingsFrame = new JFrame("Settings");
		settingsFrame.setSize(350, 300);
		settingsFrame.setLocation(loginFrame.getX()+20, loginFrame.getY()+20);
		settingsFrame.setLayout(null);
		settingsFrame.setResizable(false);
		
		addressLabel = new JLabel("Current Address:");
		addressLabel.setSize(200, 30);
		addressLabel.setLocation(10, 10);
		addressLabel.setFont(medFont);
		
		addressField = new JTextField(ADDRESS);
		addressField.setSize(300, 40);
		addressField.setLocation(10, 50);
		addressField.setFont(medFont);
		
		settingsDiscardButton = new JButton("Cancel");
		settingsDiscardButton.setSize(120, 40);
		settingsDiscardButton.setLocation(10, 190);
		settingsDiscardButton.addActionListener(new discardListener());
		settingsDiscardButton.setFont(medFont);
		
		settingsApplyButton = new JButton("Apply");
		settingsApplyButton.setSize(120, 40);
		settingsApplyButton.setLocation(190, 190);
		settingsApplyButton.addActionListener(new applyListener());
		settingsApplyButton.setFont(medFont);
		
		settingsFrame.add(addressLabel);
		settingsFrame.add(addressField);
		settingsFrame.add(settingsDiscardButton);
		settingsFrame.add(settingsApplyButton);
		
	}
	
	
	
	
	private static void setupMainFrame(){
		
		isLogoutMode = true;
		
		mainFrame = new JFrame("DataTower Client");
		mainFrame.setSize(450, 610);
		mainFrame.setLocation(400, 400);
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);
		
		////////////////////////////////////////
		// UPLOAD SECTION
		///////////////////////////////////////
		
		uploadLabel = new JLabel("Upload File");
		uploadLabel.setSize(200, 40);
		uploadLabel.setLocation(10, 10);
		uploadLabel.setFont(lgFont);
		
		pathLabel = new JLabel("Path:");
		pathLabel.setSize(200, 30);
		pathLabel.setLocation(10, 50);
		pathLabel.setFont(medFont);
		
		pathField = new JTextField("ex. C:\\path\\to\\file.txt");
		pathField.setSize(400, 40);
		pathField.setLocation(10, 90);
		pathField.setFont(medFont);
		
		publicCheck = new JCheckBox("public file");
		publicCheck.setSize(150, 30);
		publicCheck.setLocation(10, 140);
		publicCheck.setFont(medFont);
		
		uploadButton = new JButton("Upload");
		uploadButton.setSize(100, 40);
		uploadButton.setLocation(310, 140);
		uploadButton.setFont(medFont);
		uploadButton.addActionListener(new uploadListener());
		
		
		/////////////////////////////////
		// DOWNLOAD SECTION
		////////////////////////////////
		
		downloadLabel = new JLabel("Download File");
		downloadLabel.setSize(270, 30);
		downloadLabel.setLocation(10, 230);
		downloadLabel.setFont(lgFont);
		
		selectFileLabel = new JLabel("Select file:");
		selectFileLabel.setSize(200, 30);
		selectFileLabel.setLocation(10, 270);
		selectFileLabel.setFont(medFont);
		
		selectFileCombo = new JComboBox<String>();
		selectFileCombo.setSize(400, 40);
		selectFileCombo.setLocation(10, 310);
		selectFileCombo.setFont(medFont);
		getDefaultOptions();
		
		otherUserCheck = new JCheckBox("<html>From Other<br>User</html>");
		otherUserCheck.setSize(110, 70);
		otherUserCheck.setLocation(10, 360);
		otherUserCheck.setFont(medFont);
		otherUserCheck.addActionListener(new userClickListener());
		
		String userOptions[] = getUserOptions();
		selectUserCombo = new JComboBox<String>(userOptions);
		selectUserCombo.setSize(150, 30);
		selectUserCombo.setLocation(125, 360);
		selectUserCombo.setFont(medFont);
		selectUserCombo.setEnabled(false);
		selectUserCombo.addActionListener(new userComboListener());
		
		downloadButton = new JButton("Download");
		downloadButton.setSize(125, 40);
		downloadButton.setLocation(285, 360);
		downloadButton.setFont(medFont);
		downloadButton.addActionListener(new downloadListener());
		
		deleteButton = new JButton("Delete");
		deleteButton.setSize(125, 40);
		deleteButton.setLocation(285, 410);
		deleteButton.setFont(medFont);
		deleteButton.addActionListener(new deleteCheckListener());
		
		/////////////////////////////////
		// Progress bar SECTION
		////////////////////////////////
		loadProgress = new JProgressBar();
		loadProgress.setSize(400, 30);
		loadProgress.setLocation(10, 460);
		
		progressLabel = new JLabel("Ready");
		progressLabel.setSize(400, 40);
		progressLabel.setLocation(10, 490);
		
		logoutCancelButton = new JButton("Logout");
		logoutCancelButton.setSize(125, 40);
		logoutCancelButton.setLocation(285, 500);
		logoutCancelButton.setFont(medFont);
		logoutCancelButton.addActionListener(new logoutCancelListener());
		
		
		mainFrame.add(uploadLabel);
		mainFrame.add(pathLabel);
		mainFrame.add(pathField);
		mainFrame.add(publicCheck);
		mainFrame.add(uploadButton);
		
		mainFrame.add(downloadLabel);
		mainFrame.add(selectFileLabel);
		mainFrame.add(selectFileCombo);
		mainFrame.add(otherUserCheck);
		mainFrame.add(selectUserCombo);
		mainFrame.add(downloadButton);
		mainFrame.add(deleteButton);
		
		mainFrame.add(loadProgress);
		mainFrame.add(progressLabel);
		mainFrame.add(logoutCancelButton);
		
		
	}
	
	
	
	
	
	public static void setupDeleteCheck(String fileName){
		
		deleteCheckFrame = new JFrame("Are You Sure");
		deleteCheckFrame.setSize(360, 240);
		deleteCheckFrame.setLocation(mainFrame.getX() + 100, mainFrame.getY() + 100);
		deleteCheckFrame.setLayout(null);
		deleteCheckFrame.setResizable(false);
		
		checkLabel = new JLabel("<html>Are you sure you want to delete<br> the file:<br><b><center>" + fileName + "</center></b></html>");
		checkLabel.setSize(310, 150);
		checkLabel.setLocation(10, 10);
		checkLabel.setFont(medFont);
		
		yesButton = new JButton("Yes");
		yesButton.setSize(125, 40);
		yesButton.setLocation(10, 150);
		yesButton.setFont(medFont);
		yesButton.addActionListener(new deleteListener(fileName));
		
		cancelButton = new JButton("Cancel");
		cancelButton.setSize(125, 40);
		cancelButton.setLocation(215, 150);
		cancelButton.setFont(medFont);
		cancelButton.addActionListener(new cancelListener());
		
		deleteCheckFrame.add(checkLabel);
		deleteCheckFrame.add(yesButton);
		deleteCheckFrame.add(cancelButton);
		
	}
	
	
	
	
	
	
	
	
	////////////////////////////////
	//
	// Action Listeners
	//
	/////////////////////////////
	private static class loginListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			String testName = userNameLoginField.getText();
			String testKey = new String(userPasswordField.getPassword());
			
			String request = ADDRESS
					+ "userMan"
					+ "=?="+testName
					+ "=?="+testKey;
			
			String result = MasterPoster.get(request);
			
			
			if(!(result.equals("NOT FOUND") || result.equals("REQUEST FAILED") || request == null)){ 
				
				userName = testName;
				key = testKey;
				manifestRaw = result;
				
				JOptionPane.showMessageDialog(loginFrame, "Login Success");
				
				setupMainFrame();
				
				//handover exit op
				loginFrame.setVisible(false);
				loginFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
				mainFrame.setVisible(true);
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
			}
			else{
				
				JOptionPane.showMessageDialog(loginFrame, "Login Failed");
				
			}
			
		}
		
	}
	
	
	
	private static class settingsListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			setupSettingsFrame();
			settingsFrame.setVisible(true);
			settingsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
		}
		
	}
	
	
	private static class discardListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			settingsFrame.setVisible(false);
			
		}
		
	}
	
	private static class applyListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			ADDRESS = addressField.getText();
			settingsFrame.setVisible(false);
			
		}
		
	}
	
	
	
	
	private static class downloadListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			downloadButton.setEnabled(false);
			uploadButton.setEnabled(false);
			deleteButton.setEnabled(false);
			selectFileCombo.setEnabled(false);
			otherUserCheck.setEnabled(false);
			selectUserCombo.setEnabled(false);
			
			logoutCancelButton.setText("Cancel");
			isLogoutMode = false;
			mainFrame.repaint();
			
			new downloadThread().start();
			
			
		}
		
		
	}
	
	
	
	
	
	
	private static class uploadListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			downloadButton.setEnabled(false);
			uploadButton.setEnabled(false);
			deleteButton.setEnabled(false);
			selectFileCombo.setEnabled(false);
			otherUserCheck.setEnabled(false);
			selectUserCombo.setEnabled(false);
			
			logoutCancelButton.setText("Cancel");
			isLogoutMode = false;
			mainFrame.repaint();
			
			String fileNameRaw = pathField.getText();
			String fileNameBreakDown[] = fileNameRaw.split("\\\\");
			String fileName = fileNameBreakDown[fileNameBreakDown.length-1];
			
			if(alreadyExists(fileName)){
				
				//TODO replace with overwrite option
				JOptionPane.showMessageDialog(mainFrame, "File already exists, please delete it to continue.");
				
				logoutCancelButton.setEnabled(true);
				downloadButton.setEnabled(true);
				uploadButton.setEnabled(true);
				deleteButton.setEnabled(true);
				selectFileCombo.setEnabled(true);
				otherUserCheck.setEnabled(true);
				if(otherUserCheck.isSelected())
					selectUserCombo.setEnabled(true);
				
			}
			else{
				
				new uploadThread(fileName, fileNameRaw).start();
				
			}
			
			
		}
		
		
	}
	
	
	
	private static class deleteCheckListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			String fileName = (String)selectFileCombo.getSelectedItem();
			
			setupDeleteCheck(fileName);
			deleteCheckFrame.setVisible(true);
			deleteCheckFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			
			
			
		}
		
		
	}
	
	
	
	//this listener actually performs the delete
	private static class deleteListener implements ActionListener{
		
		private String fileName;
		
		public deleteListener(String fn){
			
			fileName = fn;
			
		}
		
		public void actionPerformed(ActionEvent e){
			
			String request = ADDRESS
					+ "delete"
					+ "=?="+userName
					+ "=?="+key
					+ "=?="+percentEncode(fileName);
			
			
			String result = MasterPoster.get(request);
			
			
			if(!result.equals("Request Failed")){ 
				
				JOptionPane.showMessageDialog(deleteCheckFrame, "Delete Success");
				getDefaultOptions();
				mainFrame.repaint();
				
			}
			else{
				
				JOptionPane.showMessageDialog(deleteCheckFrame, "Delete Failed");
				
			}
			
			deleteCheckFrame.dispose();
			
		}
		
		
	}
	
	
	private static class cancelListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			deleteCheckFrame.dispose();
			
		}
		
		
	}
	
	
	
	//listens for button click, decides 1 of 2 actions based on mode
	private static class logoutCancelListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if(isLogoutMode){
				
				//clear values
				userName = null;
				key = null;
				manifestRaw = null;
				
				userNameLoginField.setText("");
				userPasswordField.setText("");
				
				//go back to login menu
				loginFrame.setVisible(true);
				loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				mainFrame.setVisible(false);
				mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				
			}
			else{
				
				// send order to cancel loading
				cancelLoad = true;
				
				//grey button out
				logoutCancelButton.setEnabled(false);
				
			}
			
		}
		
		
	}
	
	
	
	//listens for user check box to be clicked
	private static class userClickListener implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			
			if(selectUserCombo.isEnabled()){
				selectUserCombo.setEnabled(false);
				deleteButton.setEnabled(true);
				getDefaultOptions();
				
			}
			else{
				selectUserCombo.setEnabled(true);
				deleteButton.setEnabled(false);
				String fileOptions[] = getFileOptions((String)selectUserCombo.getSelectedItem());
				selectFileCombo.removeAllItems();
				for(int index = 0; index < fileOptions.length; index++){
					
					selectFileCombo.addItem(fileOptions[index]);
					
				}
				
			}
			
		}
		
		
	}
	
	
	//listens for user check box to be clicked
	private static class userComboListener implements ActionListener{
		
		String old;
		
		public userComboListener(){
			
			old = (String)selectUserCombo.getSelectedItem();
			
		}
		
		public void actionPerformed(ActionEvent e){
			
			//check if selection has changed
			String selection = (String)selectUserCombo.getSelectedItem();
			
			if(!(selection.equals(old))){
				
				old = selection;
			
				String fileOptions[] = getFileOptions((String)selectUserCombo.getSelectedItem());
				selectFileCombo.removeAllItems();
				for(int index = 0; index < fileOptions.length; index++){
					
					selectFileCombo.addItem(fileOptions[index]);
					
				}
			
			}
			
		}
		
		
	}
	
	
	
	
	/////////////////////////////////
	//
	// other tools
	//
	////////////////////////////////////
	
	//gets users own manifest
	private static void getDefaultOptions(){
		
		String items[] = new String[0];
		
		String request = ADDRESS
				+ "userMan"
				+ "=?="+userName
				+ "=?="+key;
		
		String result = MasterPoster.get(request);
		
		
		if(!result.equals("NOT FOUND")){ 
			
			manifestRaw = result;
		
			String breakDown[] = manifestRaw.split("\n");
			
			for(int index = 1; index < breakDown.length; index++){
				
				if(!(breakDown[index].split("==>")[0].equals("PRIVATEFILES"))){
					
					items = addElement(items, percentDecode(breakDown[index].split("-=3=-")[0]));
					
				}
				
			}
			
			selectFileCombo.removeAllItems();
			for(int index = 0; index < items.length; index++){
				
				selectFileCombo.addItem(items[index]);
				
			}
		
		}
		
	}
	
	//for public manifests
	private static String[] getFileOptions(String userName){
		
		String result[] = new String[0];
		
		String request = ADDRESS
				+ "getPub"
				+ "=?="+userName;
		
		String breakDown[] = MasterPoster.get(request).split("\n");
		
		
		for(int index = 0; index < breakDown.length; index++){
			
			if(!(breakDown[index].split("==>")[0].equals("PRIVATEFILES"))){
				
				result = addElement(result, percentDecode(breakDown[index].split("-=3=-")[0]));
				
			}
			
		}
		
		return result;
		
	}
	
	
	
	//implement
	private static String[] getUserOptions(){
		
		String path = ADDRESS + "getUsers";
		
		String raw[] = MasterPoster.get(path).split("\n");
		
		//remove self from list of public files
		String users[] = new String[0];
		for(int index = 0; index < raw.length; index++){
			
			if(!(raw[index].equals(userName)))
				users = addElement(users, raw[index]);
			
		}
		
		return users;
		
	}
	
	
	
	
	
	
	
	
	
	private static String[] addElement(String[] old, String item){
		
		String result[] = new String[old.length+1];
		
		for(int index = 0; index < old.length; index++){
			
			result[index] = old[index];
			
		}
		
		result[old.length] = item;
		
		return result;
		
	}
	
	
	
	
	private static String percentEncode(String old){
		
		String result = old;
		
		//TODO fill table
		result = result.replace(" ", "%20");
		
		return result;
		
	}
	
	
	private static String percentDecode(String old){
		
		String result = old;
		
		//TODO fill table
		result = result.replace("%20", " ");
		
		return result;
		
	}
	
	
	
	//needed to read part of a file
	public static byte[] readFileSegment(File file, long index, int count) {
	    
		System.out.println("Chunk Size: " + count);
		
		try{
			RandomAccessFile raf = new RandomAccessFile(file, "r");
		    byte[] buffer = new byte[count];
	        raf.seek(index);
	        raf.readFully(buffer, 0, count);
	        raf.close();
	        return buffer;
	    } 
		catch(Exception e){
	    	
			return new byte[0];
	    	
	    }
		
	}
	
	
	public static boolean alreadyExists(String fileName){
		
		boolean result = false; //assume false prove true
		
		String breakDown[] = manifestRaw.split("\n");
		
		for(int index = 1; index < breakDown.length; index++){
			
			if(!(breakDown[index].split("==>")[0].equals("PRIVATEFILES"))){
				
				String currentFile = percentDecode(breakDown[index].split("-=3=-")[0]);
				
				if(fileName.equals(currentFile)){
					
					result = true;
					break;
					
				}
				
			}
			
		}
		
		return result;
		
	}
	
	
	//if connection is lost ... wait for connection to come back
	public static void reconnect(){
		
		boolean reconnected = false;
		
		String request = ADDRESS
				+ "AreYouThere";
				
		while(!reconnected){
			
			if(cancelLoad)
				break;
			
			String result = MasterPoster.get(request);
			
			if(result.equals("yes")){
				reconnected = true;
				break;
			}
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				//do nothing
			}
			
		}
		
	}
	
	
	
	
	
	
	/////////////////////////
	// up and download threads
	////////////////////////
	private static class downloadThread extends Thread{
		
		public void run(){
			
			int success = 0;
			
			String fileName = (String)selectFileCombo.getSelectedItem();
			
			String request = "";
			
			if(otherUserCheck.isSelected()){
				
				request = ADDRESS
						+ "userFile"
						+ "=?="+((String)selectUserCombo.getSelectedItem())
						+ "=?="+percentEncode(fileName);
				
			}
			else{
				
				request = ADDRESS
						+ "userFile"
						+ "=?="+userName
						+ "=?="+percentEncode(fileName);
				
			}
			
			String body = "";
			
			try{
			
				body = MasterPoster.get(request+"=?=0"); //get chunk 0 which is actually number of chunks
				int chunkCount = Integer.parseInt(body);
				
				loadProgress.setValue(0);
				
				loadProgress.setMinimum(0);
				loadProgress.setMaximum(chunkCount);
				progressLabel.setText("Preparing Download . . . ");
				mainFrame.repaint();
				
				if(fileName.split("\\.").length == 1)
					fileName += ".txt";
				
				//clears file if it exists already
				FileOutputStream cleanWriter = new FileOutputStream(fileName);
				cleanWriter.write("".getBytes());
				cleanWriter.close();
				
				for(int index = 1; index <= chunkCount; index++){
					
					if(cancelLoad){
						
						progressLabel.setText("Cancelling download . . . ");
						mainFrame.repaint();
						
						//delete file to make sure it doesnt appear in download folder
						new File(fileName).delete();
						
						//break this loop
						break;
					}
					
					System.out.println("Getting chunk: " + index + "/" + chunkCount);
					
					FileOutputStream writer = new FileOutputStream(fileName, true);
					
					String tempRequest = request + "=?=" + index;
					
					boolean messageReceived = false;
					
					String tempBody = "REQUEST FAILED";
					
					while(!messageReceived){
						
						if(cancelLoad)
							break;
						
						tempBody = MasterPoster.get(tempRequest);
						
						if(tempBody.equals("REQUEST FAILED")){
							
							progressLabel.setText("Reconnecting . . . ");
							reconnect();
						}
						else{
							
							messageReceived = true;
							
						}
						
					}
					writer.write(encoder.decode(tempBody));
					
					writer.close();
					
					loadProgress.setValue(index);
					progressLabel.setText("Downloading: " + index + "/" + chunkCount + " packets");
					mainFrame.repaint();
					
				}
			
			}
			catch(Exception e1){
				
				success = 1;
				
			}
			
				
			if(success == 0 & !cancelLoad)
				JOptionPane.showMessageDialog(mainFrame, "Download Success");	
			else if(success == 0 & cancelLoad)
				JOptionPane.showMessageDialog(mainFrame, "Download Cancelled");
			else if(success == 1 & cancelLoad)
				JOptionPane.showMessageDialog(mainFrame, "Download Cancelled but corrupted file may have appeared in the download location. Please delete it");
			else
				JOptionPane.showMessageDialog(mainFrame, "Download Failed");
			
			if(cancelLoad)
				progressLabel.setText("Cancelled");
			else
				progressLabel.setText("Complete");
			
			logoutCancelButton.setText("Logout");
			isLogoutMode = true;
			cancelLoad = false;;
			
			logoutCancelButton.setEnabled(true);
			downloadButton.setEnabled(true);
			uploadButton.setEnabled(true);
			deleteButton.setEnabled(true);
			selectFileCombo.setEnabled(true);
			otherUserCheck.setEnabled(true);
			if(otherUserCheck.isSelected())
				selectUserCombo.setEnabled(true);
			
		}
		
	}
	
	
	
	private static class uploadThread extends Thread{
		
		private String fileName;
		private String fileNameRaw;
		
		public uploadThread(String fn, String raw){
			
			fileName = fn;
			fileNameRaw = raw;
			
		}
		
		public void run(){
			
			String request = ADDRESS
					+ "userWrite"
					+ "=?="+userName
					+ "=?="+key
					+ "=?="+percentEncode(fileName);
			
			if(publicCheck.isSelected())
				request += "=?=True";
			else
				request += "=?=False";
			
			
			try {
				//should be utf-8 encoded already
				int success = 0;
				
				File file = new File(fileNameRaw);
				
				long bodyLength = Files.size(file.toPath());
				System.out.println("Total file size: " + bodyLength + " bytes");
				
				long sections = bodyLength/15000000; //how many approx 15MB sections are needed
				System.out.println("Sections needed: " + sections);
				
				
				if((int)(bodyLength%15000000L) > 0)
					sections++;
				
				loadProgress.setValue(0);
				
				loadProgress.setMinimum(0);
				loadProgress.setMaximum((int)sections);
				progressLabel.setText("Preparing Upload . . . ");
				mainFrame.repaint();
					
				
				String firstPost = request + "=?=0";
				success = MasterPoster.Post(firstPost, ""+ sections);
				
				for(int index = 0; index < sections; index++){
					
					//check if upload is being cancelled
					if(cancelLoad){
						
						progressLabel.setText("Cancelling upload . . . ");
						mainFrame.repaint();
						
						//delete file to make sure it doesnt remain in data tower
						String deleteRequest = ADDRESS
						+ "delete"
						+ "=?="+userName
						+ "=?="+key
						+ "=?="+percentEncode(fileName);
						
						String deleteResponse = MasterPoster.get(deleteRequest);
						
						if(!deleteResponse.equals("FILE DELETED"))
							success = 1;
						//break this loop
						break;
					}
					
					long start = index*15000000L;
					
					byte rawInput[];
					
					if(index < sections-1)
						rawInput = readFileSegment(file, start, 15000000);
					else
						rawInput = readFileSegment(file, start, (int)(bodyLength%15000000L));
					
					String tempPost = request + "=?=" + (index+1);
					boolean messageReceived = false;
					
					while(!messageReceived){
						
						if(cancelLoad)
							break;
					
						success = MasterPoster.Post(tempPost, encoder.encode(rawInput));
						
						if(!(success == 0)){
							
							progressLabel.setText("Reconnecting . . . ");
							reconnect();
							
						}
						else{
							
							messageReceived = true;
							
						}
						
					
					}
					
					System.out.println("Sections Complete: " + (index+1) + "/" + sections);
					
					loadProgress.setValue(index+1);
					progressLabel.setText("Uploading: " + (index+1) + "/" + sections + " packets");
					mainFrame.repaint();
					
				}
				
				if(success == 0 & !cancelLoad)
					JOptionPane.showMessageDialog(mainFrame, "Upload Success");	
				else if(success == 0 & cancelLoad)
					JOptionPane.showMessageDialog(mainFrame, "Upload Cancelled");
				else if(success == 1 & cancelLoad)
					JOptionPane.showMessageDialog(mainFrame, "Cancel may not have registered with the server! If the file appears in your list of files it is most likely corrupted and should be deleted.");
				else
					JOptionPane.showMessageDialog(mainFrame, "Upload Failed");
				
			} catch (Exception e1) {
				// TODO assume for now its some file not found or invalid path exception

				JOptionPane.showMessageDialog(mainFrame, "File Not Found!");
				
				e1.printStackTrace();
			}
			
			getDefaultOptions();
			
			
			if(cancelLoad)
				progressLabel.setText("Cancelled");
			else
				progressLabel.setText("Complete");
			
			logoutCancelButton.setText("Logout");
			isLogoutMode = true;
			cancelLoad = false;
			
			
			mainFrame.repaint();
			
			logoutCancelButton.setEnabled(true);
			downloadButton.setEnabled(true);
			uploadButton.setEnabled(true);
			deleteButton.setEnabled(true);
			selectFileCombo.setEnabled(true);
			otherUserCheck.setEnabled(true);
			if(otherUserCheck.isSelected())
				selectUserCombo.setEnabled(true);
			
		}
		
	}
	

}

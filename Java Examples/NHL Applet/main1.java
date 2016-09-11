package com.runtime;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.swing.*;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;




public class main1 {
	
	private static JFrame mainFrame;
	private static String scoreBoardInfo;
	
	private static JLabel homeTeamLabel;
	private static JLabel awayTeamLabel;
	private static JLabel homeScoreLabel;
	private static JLabel awayScoreLabel;
	
	
	private static JLabel atLabel;
	private static JLabel timeLabel;
	
	private static JSONObject games;
	
	private static int gameIndex;
	
	private static Calendar date;
	
	public static void main(String[] args) throws Exception{
		
		// changes timezone to US/Hawaii so game day roll over is at 5:00am EST
		date = Calendar.getInstance();
		date.setTimeZone(TimeZone.getTimeZone("US/Hawaii"));
		
		mainFrame = new JFrame("NHL Scoreboard ALPHA - Scores From Oct 1st 2014");
		mainFrame.setSize(550, 300);
		mainFrame.setLocation(100, 100);
		mainFrame.setLayout(null);
		
		homeTeamLabel = new JLabel("home");
		homeTeamLabel.setLocation(400, 70);
		homeTeamLabel.setSize(400, 70);
		
		awayTeamLabel = new JLabel("away");
		awayTeamLabel.setLocation(10, 70);
		awayTeamLabel.setSize(340, 70);
		
		homeScoreLabel = new JLabel("#");
		homeScoreLabel.setLocation(325, 50);
		homeScoreLabel.setSize(300, 100);
		
		awayScoreLabel = new JLabel("#");
		awayScoreLabel.setLocation(160, 50);
		awayScoreLabel.setSize(300, 100);
		
		Font medFont = new Font(homeTeamLabel.getFont().getName(), Font.PLAIN, 42);
		Font lgFont = new Font(homeTeamLabel.getFont().getName(), Font.PLAIN, 70);
		
		homeTeamLabel.setFont(medFont);
		awayTeamLabel.setFont(medFont);
		
		homeScoreLabel.setFont(lgFont);
		awayScoreLabel.setFont(lgFont);
		
		
		
		atLabel = new JLabel("AT");
		atLabel.setLocation(225, 70);
		atLabel.setSize(400, 70);
		
		timeLabel = new JLabel("FINAL");
		timeLabel.setLocation(200, 150);
		timeLabel.setSize(400, 70);
		
		atLabel.setFont(medFont);
		timeLabel.setFont(medFont);
		
		mainFrame.add(timeLabel);
		mainFrame.add(atLabel);
		mainFrame.add(homeTeamLabel);
		mainFrame.add(awayTeamLabel);
		mainFrame.add(homeScoreLabel);
		mainFrame.add(awayScoreLabel);
		
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gameIndex = 0;
		
		updateScores();
		
		Timer updater = new Timer(5000, new updateScores());
		updater.start();
		
		
	}
	
	
	public static class updateScores implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
		
			updateScores();
		
		}
	
	}
	
	
	
	
	public static void updateScores(){
		
		HttpGet request;
		
		//format for date
		if(date.get(Calendar.MONTH)+1 >9 && date.get(Calendar.DAY_OF_MONTH) >9 ){
			request = new HttpGet("http://live.nhle.com/GameData/GCScoreboard/"+ 
				date.get(Calendar.YEAR)+"-"+ (date.get(Calendar.MONTH)+1)+"-"+date.get(Calendar.DAY_OF_MONTH) +".jsonp");
		}
		else if(date.get(Calendar.MONTH)+1 < 10 && date.get(Calendar.DAY_OF_MONTH) > 9 ){
			
			request = new HttpGet("http://live.nhle.com/GameData/GCScoreboard/"+ 
					date.get(Calendar.YEAR)+"-0"+ (date.get(Calendar.MONTH)+1)+"-"+date.get(Calendar.DAY_OF_MONTH) +".jsonp");
			
		}
		else if(date.get(Calendar.MONTH)+1 > 9 && date.get(Calendar.DAY_OF_MONTH) < 10 ){
			
			request = new HttpGet("http://live.nhle.com/GameData/GCScoreboard/"+ 
					date.get(Calendar.YEAR)+"-"+ (date.get(Calendar.MONTH)+1)+"-0"+date.get(Calendar.DAY_OF_MONTH) +".jsonp");
			
		}
		else{
			
			request = new HttpGet("http://live.nhle.com/GameData/GCScoreboard/"+ 
					date.get(Calendar.YEAR)+"-0"+ (date.get(Calendar.MONTH)+1)+"-0"+date.get(Calendar.DAY_OF_MONTH) +".jsonp");
			
		}
		
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
	
	
	
		try {
			response = client.execute(request);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		try {
			scoreBoardInfo = IOUtils.toString(response.getEntity().getContent());
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		//System.out.println(scoreBoardInfo);
		
		scoreBoardInfo = scoreBoardInfo.substring(15);
		
		//System.out.println(scoreBoardInfo);
		
		try{
		
			games = new JSONObject(scoreBoardInfo);
		
		
		
			JSONArray game = games.getJSONArray("games");
			
			
			
			JSONObject currentGame = game.getJSONObject(gameIndex);
			
			gameIndex++;
			
			if(gameIndex >= games.length())
				gameIndex = 0;
			
			String awayTeam = currentGame.getString("ata");
			String homeTeam = currentGame.getString("hta");
			
			int awayScore = currentGame.getInt("ats");
			int homeScore = currentGame.getInt("hts");
			
			String time = currentGame.getString("bsc");
			
			homeScoreLabel.setText("" + homeScore);
			awayScoreLabel.setText("" + awayScore);
			
			homeTeamLabel.setText(homeTeam);
			awayTeamLabel.setText(awayTeam);
			
			timeLabel.setText(time);
		
		}
		catch(JSONException e1){
			
			homeScoreLabel.setText("");
			awayScoreLabel.setText("");
			
			homeTeamLabel.setText("");
			awayTeamLabel.setText("NO GAMES TODAY");
			awayTeamLabel.setSize(550, 100);
			
			atLabel.setText("");
			timeLabel.setText("");
			
		}
		
		mainFrame.repaint();
		
	}
	
}

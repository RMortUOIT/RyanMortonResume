// This is the function that makes the timer "tick"
public void update(float deltaTime){
		
		
		cal = Calendar.getInstance();
		
		
		if(!timerActive){
			
			topBack = "topRed.png";
			botBack = "bottomGreen.png";
			
		}
		else{
			
			
			
			topBack = "topGreen.png";
			botBack = "bottomRed.png";
			
			
			// tick
			if(cal2.get(Calendar.SECOND) != cal.get(Calendar.SECOND)){
				
				totalSeconds--;
				
				min = (totalSeconds/60);
				sec = totalSeconds - (min*60);
				
				
				
			}
		
		
			if( totalSeconds == 0){
				timerActive = false;
				done = true;
				
				// beep
				SP.play(beepID, 1f, 1f, 1, 0, 1f);
				
			}
			
		}
		
		
		minTen = (min/10)+".png"; 
		minOne = (min%10)+".png";
		secTen = (sec/10)+".png";
		secOne = (sec%10)+".png";
		
		
		cal2 = Calendar.getInstance();
		
		
}
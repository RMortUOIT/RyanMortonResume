package com.runtime;

public class Encoder {
	
	
	public String encode(byte[] input){
		
		String temp[] = new String[input.length];
		
		for(int index = 0; index < input.length; index++){
			
			temp[index] = ""+(int)input[index];
			
		}
		
		String result = String.join("%", temp);
		
		return result;
		
	}
	
	public byte[] decode(String input){
		
		String breakDown[] = input.split("%");
		
		System.out.println(breakDown.length);
		
		byte[] result = new byte[breakDown.length];
		
		for(int index = 0; index < result.length; index++){
			
			result[index] = (byte)(Integer.parseInt(breakDown[index]));
			
		}
		
		return result;
		
	}
	
	
	

}

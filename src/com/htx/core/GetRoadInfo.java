package com.htx.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.*;

public class GetRoadInfo {

  public static String getinfo(String zoom, String minx, String miny, String maxx, String maxy) {
	  
	  	InetAddress serverAddr = null;
	  	Socket socket = null;

	  	String str = "";
	  	String strcon = zoom+"#"+minx+"#"+miny+"#"+maxx+"#"+maxy;
	  	try {
		    	serverAddr = InetAddress.getByName("210.209.93.36");
		    	socket = new Socket(serverAddr, 37210);
		    	PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
		    	out.println(strcon);
		    	out.flush();
		    	
		    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "GBK"));
	            str = in.readLine();          
	            socket.close();
	            
	  	} catch(Exception e) {
	  	} 
	  	
	  	return str;
	  }
}
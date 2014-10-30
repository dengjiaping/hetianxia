package com.htx.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.*;

import android.util.Log;

public class SignValue {

  public static String compute(String v, String t, String imei, String data, String api, String imsi, String appKey) {
  
  	InetAddress serverAddr = null;
  	Socket socket = null;
  	String message = data + "|" + appKey + "|" + api + "|" + v + "|" + imei + "|" + imsi + "|" + t;
  	String str = "";
  	
  	try {
  		Log.w("aaa", message);
	    	serverAddr = InetAddress.getByName("hetianxia888.eicp.net");

	    	socket = new Socket(serverAddr, 51706);
  	
	    	PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
	    	out.println(message);
	    	out.flush();
	    	
	    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            str = in.readLine();          
            socket.close();
	    	
  	} catch(Exception e) {
  	} 
  	
  	return str;
  }
}
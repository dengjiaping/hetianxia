package com.htx.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.net.*;

public class GetBarcodeInfo {

	public static String getinfo(String barcode) {

		InetAddress serverAddr = null;
		Socket socket = null;

		String str = "";
		
		try {
			serverAddr = InetAddress.getByName("htx.518666.net");

			socket = new Socket(serverAddr, 45237);

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(barcode);
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			str = in.readLine();
			socket.close();

		} catch (Exception e) {
		}
		return str;
	}

	public static String getmartprice(String loc, String barcode, String ref) {

		InetAddress serverAddr = null;
		Socket socket = null;

		String str = "";
		String strcon = loc + "#" + barcode + "#" + ref;

		try {
			serverAddr = InetAddress.getByName("htx.518666.net");

			socket = new Socket(serverAddr, 45237);

			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(socket.getOutputStream())), true);
			out.println(strcon);
			out.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "GBK"));
			str = in.readLine();
			socket.close();

		} catch (Exception e) {
		}

		
		return str;
	}
}
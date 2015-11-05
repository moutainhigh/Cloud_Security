package com.cn.ctbri.nio;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.net.UnknownHostException;

public class TestUTF8 {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\yuan\\Desktop\\ww.xml")));
        while(br.read() != -1){
            System.err.println(URLDecoder.decode(br.readLine(), "UTF-8"));
        }

	}
}

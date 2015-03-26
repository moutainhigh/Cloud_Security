package com.cn.ctbri.nio;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class Test {
	
	public static void main(String[] args) throws UnknownHostException, IOException {
		DatagramSocket  socket = new DatagramSocket();
		socket.connect(new InetSocketAddress("localhost", 514));
		socket.send(new DatagramPacket(new String("hello").getBytes(), new String("hello").getBytes().length));
		byte[] recvBuf = new byte[100];
//        DatagramPacket recvPacket 
//            = new DatagramPacket(recvBuf , recvBuf.length);
//		socket.receive(recvPacket);
//		System.err.println(new String(recvPacket.getData()));
	}
}

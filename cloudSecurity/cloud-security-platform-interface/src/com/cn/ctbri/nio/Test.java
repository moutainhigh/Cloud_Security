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
		String str = "<189>2013-07-18 15:51:56 128.18.74.109 %%01SEC/5/ATCKDF(l):log_type=ip_flow time=\"2013-07-18 15:51:56\" device_ip=128.18.74.109 device_type=CLEAN zone_id=13 zone_ip= biz_id=0 is_deszone=TRUE total_pps=340397 total_kbps=542507 TCP_PPS=0 TCP_KBPS=0 TCPFRAG_PPS=0 TCPFRAG_KBPS=0 UDP_PPS=0 UDP_KBPS=0 UDPFRAG_PPS=0 UDPFRAG_KBPS=0 ICMP_PPS=0 ICMP_KBPS=0 OTHER_PPS=340397 OTHER_KBPS=542507 SYN_PPS=0 SYNACK_PPS=0 ACK_PPS=0 FINRST_PPS=0 HTTP_PPS=0 HTTP_KBPS=0 HTTP_GET_PPS=0 HTTPS_PPS=0 HTTPS_KBPS=0 DNS_REQUEST_PPS=0 DNS_REQUEST_KBPS=0 DNS_REPLY_PPS=0 DNS_REPLY_KBPS=0 SIP_INVITE_PPS=0 SIP_INVITE_KBPS=0 TCP_INCREASE_CON=0 UDP_INCREASE_CON=0 ICMP_INCREASE_CON=0 OTHER_INCREASECON=0 TCP_CONCURCON=0 UDP_CONCURCON=0 ICMP_CONCURCON=0 OTHER_CONCURCON=0";
		socket.send(new DatagramPacket(str.getBytes(), str.getBytes().length));
//		socket.send(new DatagramPacket(new String("hello").getBytes(), new String("hello").getBytes().length));
		byte[] recvBuf = new byte[100];
//        DatagramPacket recvPacket 
//            = new DatagramPacket(recvBuf , recvBuf.length);
//		socket.receive(recvPacket);
//		System.err.println(new String(recvPacket.getData()));
	}
}

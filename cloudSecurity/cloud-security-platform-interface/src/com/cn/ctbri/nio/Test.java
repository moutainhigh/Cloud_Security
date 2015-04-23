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
		socket.connect(new InetSocketAddress("128.18.74.170", 443));
		String str = "<189>2013-07-18 15:51:56 128.18.74.109 %%01SEC/5/ATCKDF(l):log_type=ip_attack attack_time=\"2015-04-15 15:51:56\" start_time_attack=\"2015-04-16 06:00:00\" attack_type=15 zone_id=5 zone_ip=000 attacker=128.20.20.23,128.85.74.96,128.59.56.12 duration= total_pps=340397 total_kbps=542507 end_time=\"2015-04-14 06:00:00\" start_time_alert=\"2015-04-14 06:00:00\" TCPFRAG_PPS=0 TCPFRAG_KBPS=0 UDP_PPS=0 UDP_KBPS=0 UDPFRAG_PPS=0 UDPFRAG_KBPS=0 ICMP_PPS=0 ICMP_KBPS=0 OTHER_PPS=340397 OTHER_KBPS=542507 SYN_PPS=0 SYNACK_PPS=0 ACK_PPS=0 FINRST_PPS=0 HTTP_PPS=0 HTTP_KBPS=0 HTTP_GET_PPS=0 HTTPS_PPS=0 HTTPS_KBPS=0 DNS_REQUEST_PPS=0 DNS_REQUEST_KBPS=0 DNS_REPLY_PPS=0 DNS_REPLY_KBPS=0 SIP_INVITE_PPS=0 SIP_INVITE_KBPS=0 TCP_INCREASE_CON=0 UDP_INCREASE_CON=0 ICMP_INCREASE_CON=0 OTHER_INCREASECON=0 TCP_CONCURCON=0 UDP_CONCURCON=0 ICMP_CONCURCON=0 OTHER_CONCURCON=0";
		str = str.replaceAll( "\"", "\'");
		socket.send(new DatagramPacket(str.getBytes(), str.getBytes().length));
//		socket.send(new DatagramPacket(new String("hello").getBytes(), new String("hello").getBytes().length));
		byte[] recvBuf = new byte[100];
//        DatagramPacket recvPacket 
//            = new DatagramPacket(recvBuf , recvBuf.length);
//		socket.receive(recvPacket);
//		System.err.println(new String(recvPacket.getData()));
	}
}

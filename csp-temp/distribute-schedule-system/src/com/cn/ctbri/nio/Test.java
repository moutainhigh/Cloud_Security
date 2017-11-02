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
//		socket.connect(new InetSocketAddress("128.18.74.170", 443));
		socket.connect(new InetSocketAddress("localhost", 514));
		String str1 = "<189>2015-04-21 11:24:17 128.18.74.122 %%01SEC/5/ATCKDF(l):log_type=ip_attack device_ip=128.18.74.122 device_type=CLEAN zone_id=11 zone_ip=200.1.1.100 start_time_alert=\"2015-04-21 11:13:09\" start_time_attack=\"2015-04-21 11:13:09\" end_time=\"2015-04-21 11:13:14\" duration=5 attack_type=10 protocol=0 port=0 attack_status=ALERT drop_packets=0 attacker= ";
		String str = "<189>2015-04-21 11:24:17 128.18.74.190 %%01SEC/5/ATCKDF(l):log_type=ip_drop time=\"2015-04-21 11:24:17\" device_ip=128.18.74.190 device_type=CLEAN zone_id=9 zone_ip= biz_id=0 is_deszone=true is_ipLocation=false ipLocation_id=0 total_pps=5355379 total_kbps=2677689 tcp_pps=5355379 tcp_kbps=2677689 tcpfrag_pps=5355379 tcpfrag_kbps=2677689 udp_pps=0 udp_kbps=0 udpfrag_pps=0 udpfrag_kbps=0 icmp_pps=0 icmp_kbps=0 other_pps=0 other_kbps=0 syn_pps=5355381 synack_pps=0 ack_pps=0 finrst_pps=0 http_pps=5355381 http_kbps=0 http_get_pps=0 https_pps=0 https_kbps=0 dns_request_pps=0 dns_request_kbps=0 dns_reply_pps=0 dns_reply_kbps=0 sip_invite_pps=0 sip_invite_kbps=0 ";
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

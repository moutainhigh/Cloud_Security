package com.cn.ctbri.southapi.adapter.scanservice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Properties;

import net.sf.json.JSONObject;

public class NioClient {
	private static String baseURL;
	private static int basePort;
	static{	
		try {
			Properties properties = new Properties();
			properties.load(ScanServiceSocket.class.getClassLoader().getResourceAsStream("SystemServiceConfig.properties"));
			baseURL = properties.getProperty("ScanServiceBaseURL");
			basePort = Integer.parseInt(properties.getProperty("ScanServiceBasePort"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    //管道管理器
    private Selector selector;
    
    public NioClient init(String serverIp, int port) throws IOException{
        //获取socket通道
        SocketChannel channel = SocketChannel.open();
        
        channel.configureBlocking(false);
        //获得通道管理器
        selector=Selector.open();
        
        //客户端连接服务器，需要调用channel.finishConnect();才能实际完成连接。
        channel.connect(new InetSocketAddress(serverIp, port));
        //为该通道注册SelectionKey.OP_CONNECT事件
        channel.register(selector, SelectionKey.OP_CONNECT);
        return this;
    }
    
    public JSONObject listen(JSONObject jsonObject) throws IOException{
        System.out.println("socket客户端启动");
        //轮询访问selector
        JSONObject reJsonObject=new JSONObject();
        String mess="";
        while(mess.length()==0){
            //选择注册过的io操作的事件(第一次为SelectionKey.OP_CONNECT)
            selector.select();
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while(ite.hasNext()){
                SelectionKey key = ite.next();
                //删除已选的key，防止重复处理
                ite.remove();
                if(key.isConnectable()){
                    SocketChannel channel=(SocketChannel)key.channel();
                    
                    //如果正在连接，则完成连接
                    if(channel.isConnectionPending()){
                        channel.finishConnect();
                    }
                    
                    channel.configureBlocking(false);
                    //向服务器发送消息
/*                    JSONObject jo=new JSONObject();
       	         jo.put("type","CreateTask");
       	         jo.put("task_type","web_detect");
       	         jo.put("target", "www.anquanbang.net");
       	         jo.put("port", "80");
       	         jo.put("interval_time", "2");
       	         jo.put("start_time", "2017-09-21 16:00:00");
       	         jo.put("end_time", "2017-09-21 16:30:00");
       	         String str=jo.toString();
*/
                    jsonObject.put("type", "CreateTask");
                    String str=jsonObject.toString();
                    channel.write(ByteBuffer.wrap(str.getBytes()));
                    
                    //连接成功后，注册接收服务器消息的事件
                    channel.register(selector, SelectionKey.OP_READ);
                    System.out.println("客户端连接成功");
                }else if(key.isReadable()){ //有可读数据事件。
                    SocketChannel channel = (SocketChannel)key.channel();
                    
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    channel.read(buffer);
                    byte[] data = buffer.array();
                    String message = new String(data);
                    mess=message;
                    channel.close();
                }
            }
        }
        reJsonObject = JSONObject.fromObject(mess);
        return reJsonObject;
    }
}

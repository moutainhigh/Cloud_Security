package websocket.handler;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {
	 
 
    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;
 
 
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	System.out.println("ConnectionEstablished");
    	int message=0;
    	while(true){
    		if(session.isOpen()){
    			session.sendMessage(new TextMessage("hello"+message));    
    			message++;
    			Thread.sleep(10000);
    			continue;
    		}else{
    			return;
    		}
    	}
    }
 
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        users.remove(session);
    }
 
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove(session);
    }
 
    public boolean supportsPartialMessages() {
        return false;
    }
 
    /**
     * 缁欐墍鏈夊湪绾跨敤鎴峰彂閫佹秷鎭�
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public void handleMessage(WebSocketSession arg0, WebSocketMessage<?> arg1)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("arg0:"+arg0+",arg1:"+arg1.getPayload());
		sendMessageToUsers(new TextMessage(arg1.getPayload().toString().getBytes()));
		
	}
 
}
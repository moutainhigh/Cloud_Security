package websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/websocket")
public class WebSocketController {
	
	@RequestMapping("toClient.html")
	public String toClient(){
		return "client" ;
	}
}

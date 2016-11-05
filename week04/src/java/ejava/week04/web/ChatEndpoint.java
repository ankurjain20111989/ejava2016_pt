package ejava.week04.web;

import java.io.IOException;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@RequestScoped
@ServerEndpoint("/chat")
public class ChatEndpoint {

	private Session session;

	@OnOpen
	public void open(Session sess) {
		session = sess;
		System.out.println(">>> session id: " + sess.getId());
	}

	@OnMessage
	public void message(String text) {
		String msg = Json.createObjectBuilder()
				.add("text", text)
				.add("time", (new Date()).toString())
				.build()
				.toString();

		for (Session s: session.getOpenSessions())
			try {
				s.getBasicRemote().sendText(msg);
			} catch (IOException ex) {
				try { s.close(); } catch (IOException e) {} 
			}
	}
}

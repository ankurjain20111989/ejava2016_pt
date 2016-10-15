package ejava.wk02.business;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(
		mappedName = "jms/myQueue",
		activationConfig = {
			@ActivationConfigProperty(
					propertyName = "destinationType",
					propertyValue = "javax.jms.Queue"
			)
		}
)
public class QueueProcessor implements MessageListener {

	@Override
	public void onMessage(Message message) {
		TextMessage msg = (TextMessage)message;
		try {
			System.out.println(">>> QUEUE PROCESSOR: " + msg.getText());
		} catch (JMSException ex) {
			ex.printStackTrace();
		}
	}
	
}

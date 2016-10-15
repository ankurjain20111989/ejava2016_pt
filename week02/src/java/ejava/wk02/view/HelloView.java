package ejava.wk02.view;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

@Named
@ViewScoped
public class HelloView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Resource(mappedName = "jms/myFactory")
	private ConnectionFactory factory;

	@Resource(mappedName = "jms/myQueue")
	private Queue queue;

	private String newItem;

	private List<String> items = new LinkedList<>();

	public String getNewItem() {
		return newItem;
	}
	public void setNewItem(String newItem) {
		this.newItem = newItem;
	}

	public List<String> getItems() {
		return items;
	}
	public void setItems(List<String> items) {
		this.items = items;
	}

	public void addToItems() {
		items.add(newItem);
		newItem = null;
	}

	public String sendToWarehouse() {
		System.out.println(">>> item: " + items.size());
		try (JMSContext ctx = factory.createContext()) {
			JMSProducer producer = ctx.createProducer();
			TextMessage text = ctx.createTextMessage();
			try {
				System.out.println(">>> items: " + items.toString());
				text.setText(items.toString());
				producer.send(queue, text);
			} catch (JMSException ex) {
				ex.printStackTrace();
			}
		}
		return ("thankyou");
	}
	
}

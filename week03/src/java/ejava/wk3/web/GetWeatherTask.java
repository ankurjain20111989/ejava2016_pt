package ejava.wk3.web;

import java.io.PrintWriter;
import javax.json.JsonObject;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class GetWeatherTask implements Runnable {

	final private AsyncContext ctx;
	final private String city;

	public GetWeatherTask(AsyncContext ctx, String city) {
		this.ctx = ctx;
		this.city = city;
	}

	@Override
	public void run() {

		System.out.println("----> starting thread ");

		try {
			Thread.currentThread().sleep(5000);
		} catch (InterruptedException ex) { }

		System.out.println(">>> getting weather for city " + city);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://api.openweathermap.org/data/2.5/weather")
				.queryParam("q", city)
				.queryParam("appid", "__your_appid_here__");

		Invocation.Builder request = target.request(MediaType.APPLICATION_JSON);

		JsonObject result = request.get(JsonObject.class);

		HttpServletResponse resp = (HttpServletResponse)ctx.getResponse();

		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType(MediaType.APPLICATION_JSON);
		try (PrintWriter pw = resp.getWriter()) {
			pw.print(result.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("---> request completed. Resume");

		ctx.complete();
	}
	
}

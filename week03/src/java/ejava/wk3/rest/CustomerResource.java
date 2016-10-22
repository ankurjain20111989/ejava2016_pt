package ejava.wk3.rest;

import ejava.wk3.business.CustomerBean;
import ejava.wk3.model.Customer;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@RequestScoped
@Path("/customer")
public class CustomerResource {

	@EJB private CustomerBean customerBean;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCustomer(MultivaluedMap<String, String> formData) {
		String name = formData.getFirst("name");
		String email = formData.getFirst("email");

		JsonObject json = Json.createObjectBuilder()
				.add("name", name)
				.add("email", email)
				.build();

		System.out.println(String.format(">> name: %s, email: %s", name, email));

		return (Response.status(Response.Status.CREATED)
				.entity(json)
				.build());
	}

	@GET
	@Path("{cid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findCustomerById(@PathParam("cid") Integer cid) {
		Optional<Customer> opt = customerBean.find(cid);
		if (opt.isPresent())
			return (Response.ok(opt.get().toJSON())
					.build());

		return (Response.status(Response.Status.NOT_FOUND)
				.entity("Customer id not found:" + cid)
				.build());
	}

	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Response produceXML() {
		System.out.println(">>> producing XML");
		return (Response.ok().build());
	}

	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@Context UriInfo ui) {

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();

		// http://localhost:8080/week03/api
		UriBuilder uriBuilder = ui.getBaseUriBuilder();

		// http://localhost:8080/week03/api/customer
		uriBuilder = uriBuilder.path(CustomerResource.class);

		try {
			// http://localhost:8080/week03/api/customer/{cid}
			uriBuilder = uriBuilder.path(
					CustomerResource.class.getMethod("findCustomerById", Integer.class));

		} catch (Throwable t) { //Pokemon exception
			t.printStackTrace();
			return (Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
		}

		List<Customer> result = customerBean.findAll();

		for (Customer c: result) 
			arrBuilder.add(uriBuilder.clone().build(c.getCustomerId()).toString());

		return (Response.ok(arrBuilder.build())
				.build());
	}
}

package bench.training.camel.boot.jpa.app;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.stereotype.Component;

import bench.training.camel.boot.jpa.app.entities.Payment;
import bench.training.camel.boot.jpa.app.entities.User;

@Component
public class CamelRest extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
				.dataFormatProperty("prettyPrint", "true").apiContextPath("/api-doc")
				.apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0").apiProperty("cors", "true");

		rest("/users").description("User REST service").consumes(Constants.APP_JSON).produces(Constants.APP_JSON)

				.get().description("Retrieve all users").outType(User[].class).responseMessage().code(200)
				.message("Retrieved all users successfully").endResponseMessage()
				.to("bean:database?method=findAllUsers")

				.get("/{id}").description("Find user by ID").outType(User.class).param().name("id")
				.type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
				.responseMessage().code(200).message("User successfully returned").endResponseMessage()
				.to("bean:database?method=findUser(${header.id})")

				.get("/{id}/payments").description("Find user payments").outType(Payment[].class).param().name("id")
				.type(RestParamType.path).description("The ID of the user").dataType("integer").endParam()
				.responseMessage().code(200).message("User successfully returned").endResponseMessage()
				.to("activemq:restqueue")
				.to("bean:database?method=findUserPayments(${header.id})");

		rest("/users/create").description("User REST service").consumes(Constants.APP_JSON).produces(Constants.APP_JSON)

				.post().type(User.class).description("Create User").param().name("body").type(RestParamType.body)
				.dataType("json").endParam().responseMessage().code(200).message("User created successfully")
				.endResponseMessage().to("bean:database?method=createUser(${body})");

	}

}

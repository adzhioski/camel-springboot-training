package bench.training.camel.boot.jpa.app;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import bench.training.camel.boot.jpa.app.entities.Payment;
import bench.training.camel.boot.jpa.app.entities.User;
import bench.training.camel.boot.jpa.app.processor.CsvProcessor;
import bench.training.camel.boot.jpa.app.processor.MyAggregationStrategy;
import bench.training.camel.boot.jpa.app.processor.MyEnrichAggregationStrategy;
import bench.training.camel.boot.jpa.app.processor.OrdersProcessor;
import bench.training.camel.boot.jpa.app.processor.PaymentProcessor;

@Component
public class CamelRoutes extends RouteBuilder {

	private static final Logger log = LoggerFactory.getLogger(CamelRoutes.class);

	@Autowired
	private CsvProcessor csvProcessor;

	@Autowired
	private MyAggregationStrategy myAggregationStr;

	@Autowired
	private MyEnrichAggregationStrategy myEnrichAggregartionStrategy;

	@Autowired
	private PaymentProcessor paymentProcessor;

	@Autowired
	private OrdersProcessor ordersProcessor;

	@Value("${custom.endpoint.file}")
	private String fileEndpoint;

	@Value("${custom.endpoint.direct}")
	private String directEndpoint;

	@Value("${custom.endpoint.activemq}")
	private String activemqEndpoint;

	@Override
	public void configure() throws Exception {

		// Route from csv file to persist users
		from(fileEndpoint + Constants.USERS_CSV).routeId("reading-csv")
				.log(LoggingLevel.INFO, log, "Started reading csv file").unmarshal().csv().process(csvProcessor)
				.bean("database", "generateUsers(${body})").log(LoggingLevel.INFO, log, "csv file processed");

		// Splitting xml file
		from(fileEndpoint + Constants.ORDERS_XML).routeId("split-xml").split(xpath("/orders/order"))
				.process(ordersProcessor).log("${body}").to(directEndpoint + "splited-orders")
				.log(LoggingLevel.INFO, log, "Splitter started");

		// Route to aggregate only the messages wich have a category='books'
		from(directEndpoint + "splited-orders").log("Received book category: ${header.books}")
				.log(LoggingLevel.INFO, log, "Received other category: ${header.other}").routeId("aggr-xml")
				.aggregate(header("books"), myAggregationStr).ignoreInvalidCorrelationKeys().completionSize(3)
				.to(fileEndpoint + Constants.AGGREGATE_PRODUCED).log(LoggingLevel.INFO, log, "Aggregation successfull");

		// Route with enrich
		from("timer:generate?delay=5s&period={{example.period:3s}}").routeId("find-users-rest")
				.bean("database", "generateId").enrich("direct:enricher", myEnrichAggregartionStrategy).marshal()
				.json(JsonLibrary.Jackson).log(LoggingLevel.INFO, log, "find users route: ${body}");

		from(directEndpoint + "enricher").log("${body}")
				.setHeader(Exchange.HTTP_URI, simple("http://localhost:8080/camel/users/${body}")).transform()
				.simple("${null}").to("http://ignored").unmarshal().json(JsonLibrary.Jackson, User.class)
				.log(LoggingLevel.INFO, log,
						"id: ${body.id} name: ${body.name} address: ${body.address} payments: ${body.payments}");
		
		//Reading file payments and sending to activemq
		from(fileEndpoint + Constants.PAYMENTS_FILE).unmarshal().json(JsonLibrary.Jackson, Payment[].class)
				.split(body()).multicast().to("direct:json").to(activemqEndpoint + "payments");

		from(directEndpoint + "json").log(LoggingLevel.INFO, log,
				"${body.payerId}, ${body.status}, ${body.paymentGross}");
		
		//Reading from the queue enrich the messages and persist
		from(activemqEndpoint + "payments").routeId("payments-queue").process(paymentProcessor)
				.to("jpa:bench.training.camel.boot.jpa.app.entities.Payment")
				.enrich("bean:database?method=udpateUserPayments(${body})")
				.log(LoggingLevel.INFO, log, "${body.payerId}, ${body.status}, ${body.paymentGross}")
				.log(LoggingLevel.INFO, log, "done");
	}

}

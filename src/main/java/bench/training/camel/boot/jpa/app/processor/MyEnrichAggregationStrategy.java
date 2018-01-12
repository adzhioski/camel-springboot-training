package bench.training.camel.boot.jpa.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

import bench.training.camel.boot.jpa.app.entities.User;

@Component
public class MyEnrichAggregationStrategy implements AggregationStrategy {

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		User user = newExchange.getIn().getBody(User.class);
		user.setAddress("test");
		oldExchange.getOut().setBody(user);
		return oldExchange;
	}
	
}

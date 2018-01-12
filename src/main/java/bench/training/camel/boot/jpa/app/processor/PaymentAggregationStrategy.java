package bench.training.camel.boot.jpa.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.stereotype.Component;

import bench.training.camel.boot.jpa.app.entities.Payment;

@Component
public class PaymentAggregationStrategy implements AggregationStrategy{

	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		Payment p = oldExchange.getIn().getBody(Payment.class);
		oldExchange.getOut().setBody(p);
		return oldExchange;
	}

}

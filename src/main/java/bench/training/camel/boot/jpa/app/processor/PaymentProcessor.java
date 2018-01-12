package bench.training.camel.boot.jpa.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import bench.training.camel.boot.jpa.app.entities.Payment;
import bench.training.camel.boot.jpa.app.entities.PaymentStatus;

@Component
public class PaymentProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		Payment payment = exchange.getIn().getBody(Payment.class);
		payment.setStatus(PaymentStatus.COMPLETED);
	}
	

}

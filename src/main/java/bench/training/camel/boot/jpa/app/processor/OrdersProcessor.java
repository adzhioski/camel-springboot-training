package bench.training.camel.boot.jpa.app.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class OrdersProcessor implements Processor {
	public void process(Exchange exchange) throws Exception {
		String body = exchange.getIn().getBody(String.class);
		if (body.contains("category=\"books\"")) {
			exchange.getOut().setHeader("books", "yes");
		} else {
			exchange.getOut().setHeader("other", "yes");
		}
		exchange.getOut().setBody(body+"\n");
	}

}

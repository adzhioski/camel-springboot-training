package bench.training.camel.boot.jpa.app.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class CsvProcessor implements Processor{

	public void process(Exchange exchange) throws Exception {
		List<List<String>> data = (List<List<String>>)exchange.getIn().getBody();
		List<String> names = new ArrayList<String>();
		//traverse lines
		for (List<String> list : data) {
			for (String string : list) {
				names.add(string);
			}
		}
		exchange.getOut().setBody(names);
	}

}

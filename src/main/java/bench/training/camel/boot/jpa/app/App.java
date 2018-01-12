package bench.training.camel.boot.jpa.app;

import javax.jms.ConnectionFactory;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public ServletRegistrationBean camelServletRegistration() {
		ServletRegistrationBean camelServletRegistration = new ServletRegistrationBean(new CamelHttpTransportServlet(),
				"/camel");
		camelServletRegistration.setName("CamelRest");
		return camelServletRegistration;
	}

	@Bean
	public JmsComponent jmsComponent(final ConnectionFactory connectionFactory) {
		return JmsComponent.jmsComponentAutoAcknowledge(connectionFactory);
	}

	@Bean
	public CamelContextConfiguration contextConfiguration(final JmsComponent jmsComponent) {
		return new CamelContextConfiguration() {

			public void beforeApplicationStart(CamelContext camelContext) {
				camelContext.setStreamCaching(true);
				camelContext.addComponent("activemq", jmsComponent);
			}

			public void afterApplicationStart(CamelContext camelContext) {
			}
		};
	}

}

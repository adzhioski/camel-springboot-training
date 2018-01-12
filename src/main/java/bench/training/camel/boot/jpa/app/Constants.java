package bench.training.camel.boot.jpa.app;

public class Constants {
	private Constants() {}
	public static final String APP_JSON = "application/json";
	public static final String USERS_CSV = "src/main/resources/?fileName=users.csv&noop=true";
	public static final String ORDERS_XML = "src/main/resources/?fileName=orders-split.xml&noop=true";
	public static final String PAYMENTS_FILE = "src/main/resources?fileName=payments.json&noop=true";
	public static final String AGGREGATE_PRODUCED = "produced/?fileName=test-aggregate.xml";
	
	public static final String TEST_MOCK = "test-mock";
}

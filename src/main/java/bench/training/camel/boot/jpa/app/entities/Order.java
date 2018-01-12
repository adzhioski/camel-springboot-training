package bench.training.camel.boot.jpa.app.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order {
	private String name;
	private List<String> authors;
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getAuthors() {
		return authors;
	}
	@XmlElement
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
}

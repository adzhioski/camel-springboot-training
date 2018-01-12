package bench.training.camel.boot.jpa.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bench.training.camel.boot.jpa.app.entities.Book;
import bench.training.camel.boot.jpa.app.repositories.BookRepository;

@Component
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	public Book insertBook() {
		Book b = new Book();
		b.setBook("book1");
		return b;
	}
	
	public Iterable<Book> getBooks() {
		return bookRepository.findAll();
		
	}
	
	public long countBooks() {
		return 12L;
	}
}

package bench.training.camel.boot.jpa.app.repositories;

import org.springframework.data.repository.CrudRepository;
import bench.training.camel.boot.jpa.app.entities.Book;

public interface BookRepository extends CrudRepository<Book, Integer>{

}

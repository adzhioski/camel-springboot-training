package bench.training.camel.boot.jpa.app.repositories;

import org.springframework.data.repository.CrudRepository;

import bench.training.camel.boot.jpa.app.entities.User;

public interface UserRepository extends CrudRepository<User, Integer>{

}

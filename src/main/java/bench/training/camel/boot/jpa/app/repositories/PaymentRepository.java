package bench.training.camel.boot.jpa.app.repositories;

import org.springframework.data.repository.CrudRepository;

import bench.training.camel.boot.jpa.app.entities.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Integer>{

}

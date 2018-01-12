package bench.training.camel.boot.jpa.app.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import bench.training.camel.boot.jpa.app.entities.Payment;
import bench.training.camel.boot.jpa.app.entities.PaymentStatus;
import bench.training.camel.boot.jpa.app.entities.User;
import bench.training.camel.boot.jpa.app.repositories.PaymentRepository;
import bench.training.camel.boot.jpa.app.repositories.UserRepository;

@Component
@Transactional
public class Database {
	@Autowired
	private UserRepository userRpository;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	public User generateUser() {
		User u = new User();
		u.setName("test");
		return u;
	}
	
	public int generateId() {
		return 1;
	}
	
	public void createUser(User u) {
		User u1 = new User();
		u1.setName(u.getName());
		u1.setAddress(u.getAddress());
		userRpository.save(u1);
	}
	
	public void generateUsers(List<String> userNames) {
		List<User> users = new ArrayList<User>();
		for (String string : userNames) {
			User u = new User();
			u.setName(string);
			u.setPayments(new ArrayList<Payment>());
			userRpository.save(u);
			Payment p = new Payment();
			p.setPayerId(u.getId());
			p.setStatus(PaymentStatus.PENDING);
			p.setPaymentGross(3.59);
			paymentRepository.save(p);
			u.getPayments().add(p);
			users.add(u);
		}
		userRpository.save(users);
	}
	
	public Iterable<Payment> findUserPayments(int userId) {
		List<Payment> payments = new ArrayList<Payment>();
		for (Payment payment : userRpository.findOne(userId).getPayments()) {
			payments.add(payment);
		}
		return payments;
	}
	
	public void udpateUserPayments(Payment payment) {
		User user = userRpository.findOne(payment.getPayerId());
		user.getPayments().add(payment);
		userRpository.save(user);
	}
	
	
	public Iterable<User> findAllUsers() {
		return userRpository.findAll();
	}
	
	public User findUser(Integer id) {
		return userRpository.findOne(id);
	}
	
}

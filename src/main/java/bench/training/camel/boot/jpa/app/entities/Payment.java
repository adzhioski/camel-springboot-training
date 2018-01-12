package bench.training.camel.boot.jpa.app.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity (name="Payment")
@Table (name="payment")
public class Payment implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6474998726439530332L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column (name="PAYER_ID")
	private int payerId;
	private PaymentStatus status;
	private double paymentGross;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getPayerId() {
		return payerId;
	}
	public void setPayerId(int payerId) {
		this.payerId = payerId;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	public double getPaymentGross() {
		return paymentGross;
	}
	public void setPaymentGross(double paymentGross) {
		this.paymentGross = paymentGross;
	}
	
}

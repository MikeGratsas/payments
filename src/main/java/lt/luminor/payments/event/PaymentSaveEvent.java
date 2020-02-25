package lt.luminor.payments.event;

import org.springframework.context.ApplicationEvent;

import lt.luminor.payments.form.PaymentModel;

public class PaymentSaveEvent extends ApplicationEvent {

    /**
	 * 
	 */
	private static final long serialVersionUID = 423024422538385557L;
	
	private final PaymentModel payment;

	public PaymentSaveEvent(PaymentModel payment) {
		super(payment);
		this.payment = payment;
	}

	public PaymentModel getPayment() {
		return payment;
	}
}

package lt.luminor.payments.exceptions;

public class PaymentStatusNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4868511001838406482L;

	public PaymentStatusNotFoundException(Long id) {
		super("Payment status not found", id);
	}

}

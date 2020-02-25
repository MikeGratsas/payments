package lt.luminor.payments.exceptions;

public class PaymentNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8150181673293600563L;

	public PaymentNotFoundException(Long id) {
		super("Payment not found", id);
	}

}

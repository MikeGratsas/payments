package lt.luminor.payments.exceptions;

public class PaymentStatusUpdatedException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1087943135425331390L;

	public PaymentStatusUpdatedException(Long id) {
		super("Payment status was updated since last read", id);
	}

}

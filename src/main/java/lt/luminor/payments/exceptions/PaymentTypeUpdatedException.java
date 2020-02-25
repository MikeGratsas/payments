package lt.luminor.payments.exceptions;

public class PaymentTypeUpdatedException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 801900036097440280L;

	public PaymentTypeUpdatedException(Long id) {
		super("Payment type was updated since last read", id);
	}

}

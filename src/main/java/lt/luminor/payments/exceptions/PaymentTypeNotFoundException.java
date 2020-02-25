package lt.luminor.payments.exceptions;

public class PaymentTypeNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -626013949930365172L;

	public PaymentTypeNotFoundException(Long id) {
		super("Payment type not found", id);
	}

}

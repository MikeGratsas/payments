package lt.luminor.payments.exceptions;

public class CreditorBankRequiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3409197803637296188L;

	public CreditorBankRequiredException(String paymentType) {
		super("Creditor bank code is required for payment type: " + paymentType);
	}
}

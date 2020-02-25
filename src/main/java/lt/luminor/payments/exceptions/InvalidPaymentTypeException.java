package lt.luminor.payments.exceptions;

public class InvalidPaymentTypeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1612087128029936782L;

	public InvalidPaymentTypeException(String paymentType) {
		super("Payment type is invalid: " + paymentType);
	}
}

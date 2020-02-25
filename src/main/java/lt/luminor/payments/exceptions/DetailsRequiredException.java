package lt.luminor.payments.exceptions;

public class DetailsRequiredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8746592434994558370L;

	public DetailsRequiredException(String paymentType) {
		super("Details are required for payment type: " + paymentType);
	}
}

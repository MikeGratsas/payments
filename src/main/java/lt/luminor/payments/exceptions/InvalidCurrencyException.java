package lt.luminor.payments.exceptions;

public class InvalidCurrencyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7956466158773400961L;

	public InvalidCurrencyException(String currency, String paymentType) {
		super("Currency " + currency + "is not applicable for payment type: " + paymentType);
	}
}

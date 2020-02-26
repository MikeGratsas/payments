package lt.luminor.payments.exceptions;

import java.time.LocalDate;

public class InvalidPaymentDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2372672071341063348L;

	public InvalidPaymentDateException(LocalDate creationDate) {
		super("Payment was created on " + creationDate);
	}
}

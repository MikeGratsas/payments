package lt.luminor.payments.exceptions;

public class PaymentClientException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8150181673293600563L;

	public PaymentClientException(Long id) {
		super("Payment client is not authorized", id);
	}

}

package lt.luminor.payments.exceptions;

public class CurrencyUpdatedException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4514648308475603112L;

	public CurrencyUpdatedException(Long id) {
		super("Currency was updated since last read", id);
	}

}

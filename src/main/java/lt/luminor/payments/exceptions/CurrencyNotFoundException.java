package lt.luminor.payments.exceptions;

public class CurrencyNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7144207681928504985L;

	public CurrencyNotFoundException(Long id) {
		super("Currency not found", id);
	}

}

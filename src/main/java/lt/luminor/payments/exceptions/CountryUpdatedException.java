package lt.luminor.payments.exceptions;

public class CountryUpdatedException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3653571724570655358L;

	public CountryUpdatedException(Long id) {
		super("Country was updated since last read", id);
	}

}

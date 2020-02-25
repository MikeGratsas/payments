package lt.luminor.payments.exceptions;

public class CountryNotFoundException extends DataEntityException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9093112854373914862L;

	public CountryNotFoundException(Long id) {
		super("Country not found", id);
	}

}

package lt.luminor.payments.exceptions;

public class UserEmailExistsException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8984909331923293416L;
	
	private final String email;

    public UserEmailExistsException(String email) {
        super("There is an account with that E-mail address: " + email);
        this.email = email;
    }

	public String getEmail() {
		return email;
	}
}

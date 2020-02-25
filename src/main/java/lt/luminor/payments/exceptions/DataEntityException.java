package lt.luminor.payments.exceptions;

public class DataEntityException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7360025912881148035L;
	
	private final Long id;

    public DataEntityException(String message, Long id) {
        super(message + ": id = " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

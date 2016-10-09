package by.gsu.epamlab.dao.exceptions;

public class ValidationException extends DaoBusinessException {

	private static final long serialVersionUID = -9194840854190612303L;

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}
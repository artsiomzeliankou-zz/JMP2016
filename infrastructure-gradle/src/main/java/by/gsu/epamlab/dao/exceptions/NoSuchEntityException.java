package by.gsu.epamlab.dao.exceptions;

public class NoSuchEntityException extends DaoBusinessException {

	private static final long serialVersionUID = 1L;

	public NoSuchEntityException(String message) {
		super(message);
	}
	
	public NoSuchEntityException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}

package by.gsu.epamlab.dao.exceptions;

public class DaoBusinessException extends DaoException {

	private static final long serialVersionUID = 1L;

	public DaoBusinessException(String message) {
		super(message);
	}
	
	public DaoBusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}

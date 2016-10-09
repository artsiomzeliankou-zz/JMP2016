package by.gsu.epamlab.dao.exceptions;

public class DaoSystemException extends DaoException {

	private static final long serialVersionUID = 1L;

	public DaoSystemException(String message) {
		super(message);
	}
	
	public DaoSystemException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}

package telran.java45.accounting.dto.exception;

public class UserExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5651366311663924299L;
	
	public UserExistsException(String login) {
		super("User " + login + " exists");
	}

}

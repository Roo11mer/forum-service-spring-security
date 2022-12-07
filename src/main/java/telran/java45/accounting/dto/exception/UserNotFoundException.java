package telran.java45.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 7678258187101898079L;
	
	public UserNotFoundException(String login) {
		super("User " + login + " not found");
	}


}
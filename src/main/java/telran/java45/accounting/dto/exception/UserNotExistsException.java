package telran.java45.accounting.dto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2892505830124944080L;

	public UserNotExistsException(String login) {
		super("User " + login + " is not exists");
	}
}

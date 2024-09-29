package zeroone.developers.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an employee is invalid.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmployeeException extends RuntimeException{
    public EmployeeException(String message) {
        super(message);
    }

}

package zeroone.developers.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class OrganizationException extends RuntimeException{

    public OrganizationException(String message) {
        super(message);
    }



}

package zeroone.developers.employee.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zeroone.developers.employee.exception.ResourceNotFoundException;
import zeroone.developers.employee.payload.CustomApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        CustomApiResponse apiResponse = new CustomApiResponse(message,false);
        return new ResponseEntity<CustomApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

    }








}

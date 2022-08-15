package First.Application.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map> UserNotFoundExceptionHandler(UserNotFoundException exception){

        Map map = new HashMap();
        map.put("Error Message", exception.getMessage());
        map.put("Status Code", 404);
        map.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(map,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Map> RequestBodyExceptionHandler(HttpMessageNotReadableException exception){

        Map map = new HashMap();

        map.put("Error Message", exception.getMessage().split(":")[0]);
        map.put("Status Code", 400);
        map.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<Map> RequestParamsExceptionHandler(MissingServletRequestParameterException exception){

        Map map = new HashMap();
        map.put("Error Message", exception.getMessage().split("'")[0].trim());
        map.put("Status Code", 400);
        map.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationExceptionHandler(ConstraintViolationException exception){
        Map map = new HashMap();

        map.put("Status Code", 400);
        map.put("timestamp", LocalDateTime.now());

        exception.getConstraintViolations().stream().forEach((error)->{
            map.put(error.getPropertyPath(),error.getMessage());
        });

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BadRequestCustomException.class)
    public ResponseEntity<?> CustomBadRequestHandler(BadRequestCustomException exception){
        Map map = new HashMap();

        map.put("Status Code", 400);
        map.put("timestamp", LocalDateTime.now());
        map.put("Error Message", exception.getMessage());

        return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
    }
}

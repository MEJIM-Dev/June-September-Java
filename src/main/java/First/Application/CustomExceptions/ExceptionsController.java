package First.Application.CustomExceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsController {

    @ExceptionHandler(value = RuntimeException.class)
    public Map RuntimeExceptionsHandler(RuntimeException exception){
        Map map = new HashMap();
        map.put("Error Message", "s");
        map.put("Status Code", 404);
        return map;
    }


}

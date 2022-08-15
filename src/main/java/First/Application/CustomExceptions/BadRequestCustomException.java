package First.Application.CustomExceptions;

public class BadRequestCustomException extends Exception{

    public BadRequestCustomException(String message){
        super(message);
    }
}

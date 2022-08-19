package First.Application.Controller;

import First.Application.CustomExceptions.BadRequestCustomException;
import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.Auth.LoginData;
import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    UserServiceImplementation userImpl;

    @PostMapping(value = "/api/register",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public User Register(@RequestBody UserRegistrationObject uro){

        return userImpl.saveUser(uro);
    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> LoginUser(@RequestBody LoginData credentials) throws BadRequestCustomException, UserNotFoundException {

        return userImpl.loginUser(credentials);
    }


}

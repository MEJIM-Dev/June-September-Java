package First.Application.Controller;

import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @Autowired
    UserServiceImplementation userImpl;

    @PostMapping(value = "/register",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE})
    public User Register(@RequestBody UserRegistrationObject uro){

        return userImpl.saveUser(uro);
    }

}

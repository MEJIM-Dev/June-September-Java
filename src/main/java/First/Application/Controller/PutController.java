package First.Application.Controller;

import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PutController {
    @Autowired
    UserServiceImplementation userImpl;

    @PutMapping("/update/user")
    public User UpdateUser( @RequestBody() UserRegistrationObject uro) throws UserNotFoundException {

        return userImpl.updateUser(uro);
    }
}

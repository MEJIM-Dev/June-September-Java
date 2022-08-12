package First.Application.Controller;

import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.User;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveController {

    @Autowired
    UserServiceImplementation userImpl;

    @DeleteMapping("/remove/user")
    public Map RemoveUser(@RequestBody() User uro) throws UserNotFoundException {

       return userImpl.deleteUser(uro.getId());

    }

    @DeleteMapping("/remove/users")
    public Map RemoveUsers(@RequestParam("ids") String ids){

        return userImpl.deleteUsers(ids);
    }
}

package First.Application.Services;

import First.Application.CustomExceptions.BadRequestCustomException;
import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserServices {

    User saveUser(UserRegistrationObject user);
    List<User> findAllUser();
    User updateUser(UserRegistrationObject user) throws UserNotFoundException;
    Map deleteUser(Long id) throws UserNotFoundException;
    User findById(Long id) throws UserNotFoundException;
    Map deleteUsers(String ids) throws BadRequestCustomException;
    User findByEmail(String email) throws UserNotFoundException;

    String sendMail(String firstName, String email, String subject,String text, String fail, String success);
}

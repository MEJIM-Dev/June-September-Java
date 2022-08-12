package First.Application.Services;

import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserServices {

    User saveUser(UserRegistrationObject user);
    List<User> findAllUser();
    User updateUser(UserRegistrationObject user);
    Map deleteUser(Long id);
    User findById(Long id);
    Map deleteUsers(String ids);

}

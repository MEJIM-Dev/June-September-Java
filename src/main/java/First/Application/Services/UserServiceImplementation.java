package First.Application.Services;

import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImplementation implements UserServices{

    Map result = new HashMap<>();

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(UserRegistrationObject uro) {
        System.out.println(uro.toString());
        User user  = new User(uro.getFirstName(), uro.getLastname(), uro.getAge(),uro.getPassword(),uro.getEmail(), uro.getGender());
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UserRegistrationObject user) {
        //Write exception to block empty id
        Optional<User> dbUser = userRepository.findById(user.getId());

        if(dbUser.isEmpty()) {
            //throw a better custom exception
            throw new IllegalCallerException("Couldn't find user");
        }

        User oldUser = dbUser.get();

        if(user.getAge()>0){
            oldUser.setAge(user.getAge());
        }

        if(user.getPassword()!=null){
            oldUser.setPassword(user.getPassword());
        }

        if(user.getFirstName()!=null){
            oldUser.setFirstName(user.getFirstName());
        }

        if(user.getLastname()!=null){
            oldUser.setLastname(user.getLastname());
        }

        if(user.getEmail()!=null){
            oldUser.setEmail(user.getEmail());
        }

        if(user.getGender()!=null){
            oldUser.setGender(user.getGender());
        }

        return userRepository.save(oldUser);
    }

    @Override
    public Map deleteUser(Long id) {
        boolean available = userRepository.existsById(id);

        if(!available){
            result.put(id, "User doesn't exist");
            return result;
        }

        userRepository.deleteById(id);
        result.put(id, "deleted successfully");
        return result;
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new EmptyStackException();
        }
        return user.get();
    }

//    @Override
    public Map deleteUsers(String ids) {

        String data = ids.replaceAll("[\\{\\}]", "");

        String[] a = data.split(",");

        ArrayList<Long> query = new ArrayList<>();

        for (String value:a) {
            query.add(Long.parseLong(value));
        }

        for (Long id: query) {
                if(userRepository.existsById(id)){
                    userRepository.deleteById(id);
                    result.put(id, "deleted successfully");
                } else {
                    result.put(id, "User doesn't exist");
                }
        }

        return result;
//        userRepository.deleteAllById(query);
    }

    public User findByFirstname(String firstname){
         Optional<User> dbUser = userRepository.findByFirstname(firstname);

        if(dbUser.isEmpty()){
            return new User();
        }

        return dbUser.get();
    }
}

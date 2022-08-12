package First.Application.Services;

import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImplementation implements UserServices{

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
    public User updateUser(UserRegistrationObject user) throws UserNotFoundException {
        //Write exception to block empty id
        Optional<User> dbUser = userRepository.findById(user.getId());

        if(dbUser.isEmpty()) {
            throw new UserNotFoundException("Couldn't find user with id: " + user.getId());
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
    public Map deleteUser(Long id) throws UserNotFoundException {
        boolean available = userRepository.existsById(id);

        Map result = new HashMap<>();

        if(!available){
            throw new UserNotFoundException("Can't delete a user which doesn't exist");
        }

        userRepository.deleteById(id);
        result.put(id, "deleted successfully");
        return result;
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("Couldn't find user with id: " + id);
        }
        return user.get();
    }

//    @Override
    public Map deleteUsers(String ids) {
        Map result = new HashMap<>();

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
//        userRepository.deleteAllById(query); //without checking for existing id
    }

    public User findByFirstname(String firstname) throws UserNotFoundException {
         Optional<User> dbUser = userRepository.findByFirstname(firstname);

        if(dbUser.isEmpty()){
            throw new UserNotFoundException("User with firstname: "+firstname+" doesn't exist");
        }

        return dbUser.get();
    }
}

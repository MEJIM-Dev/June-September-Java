package First.Application.Services;

import First.Application.CustomExceptions.BadRequestCustomException;
import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.Auth.LoginData;
import First.Application.Model.ResponseEntity.ResponseObject;
import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImplementation implements UserServices{

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(UserRegistrationObject uro) {
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
    public Map deleteUsers(String ids) throws BadRequestCustomException {
        Map result = new HashMap<>();

        if(ids.matches("[0-9]{1,2}-[0-9]{1,3}")){
            System.out.println(ids);
            String start =ids.split("-")[0];
            String end =ids.split("-")[1];
            System.out.println(start+" "+end);

            if(Integer.parseInt(end)<=Integer.parseInt(start)){
                throw new BadRequestCustomException("Start must be smaller than end");
            }

            for (int i = Integer.parseInt(start); i <= Integer.parseInt(end); i++) {
                if(userRepository.existsById((long) i)){
                    userRepository.deleteById((long) i);
                    result.put(i, "deleted successfully");
                } else {
                    result.put(i, "User doesn't exist");
                }
            }

            return result;
        }

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

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UserNotFoundException("Couldn't find user with email: " + email);
        }
        return user.get();
    }

    public User findByFirstname(String firstname) throws UserNotFoundException {
         Optional<User> dbUser = userRepository.findByFirstname(firstname);

        if(dbUser.isEmpty()){
            throw new UserNotFoundException("User with firstname: "+firstname+" doesn't exist");
        }

        return dbUser.get();
    }

    public ResponseEntity<Object> loginUser(LoginData credentials) throws BadRequestCustomException, UserNotFoundException {
        if(credentials.getEmail().isEmpty() || credentials.getPassword().isEmpty()){
            throw new BadRequestCustomException("missing some information for login");
        }

        User user  = findByEmail(credentials.getEmail());

        if(!user.getPassword().equals(credentials.getPassword())){
            throw new BadRequestCustomException("Email or password is incorrect");
        }

        ResponseObject userInfo = new ResponseObject(user);

        return new ResponseEntity<>(userInfo,HttpStatus.OK);
    }
}

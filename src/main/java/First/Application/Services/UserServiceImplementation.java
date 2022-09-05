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
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class UserServiceImplementation implements UserServices{

    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

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

    @Override
    public String sendMail(String firstName, String email, String subject, String text, String fail, String success) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText("hello "+firstName+", \n"+text);
        try{
            javaMailSender.send(message);
        } catch (MailException e){
            System.out.println(e.getCause());
            return fail;
        }
        return success;
    }

    public String sendMailWithAtt(String firstName, String email, String subject, String text, String fail, String success) throws MessagingException {

        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper msgHelper = new MimeMessageHelper(msg,"utf-8");

        msgHelper.setText("<div style=\" display: flex; flex-direction: column\"> <div>hello "+firstName+", </div> <div style=\" margin-top: 10px\">"+text+"</div> </div>", true);
        msgHelper.setTo(email);
        msgHelper.setSubject(subject);

        try {
            javaMailSender.send(msg);
        } catch (Exception e){
            System.out.println(e.getMessage());
            return fail;
        }

        return success;
    }

    public User findByFirstname(String firstname) throws UserNotFoundException {
         Optional<User> dbUser = userRepository.findByFirstname(firstname);

        if(dbUser.isEmpty()){
            throw new UserNotFoundException("User with firstname: "+firstname+" doesn't exist");
        }

        return dbUser.get();
    }

    public ResponseObject loginUser(LoginData credentials) throws BadRequestCustomException, UserNotFoundException {
        if(credentials.getEmail().isEmpty() || credentials.getPassword().isEmpty()){
            throw new BadRequestCustomException("missing some information for login");
        }

        User user  = findByEmail(credentials.getEmail());

        if(!user.getPassword().equals(credentials.getPassword())){
            throw new BadRequestCustomException("Email or password is incorrect");
        }

        ResponseObject userInfo = new ResponseObject(user);

        return userInfo;
    }

    public ResponseObject reAuthenticate(String email) throws UserNotFoundException {
        User user = findByEmail(email);

        return new ResponseObject(user);
    }

    public String dashboardRerouter(HttpServletRequest req, Model model, String defaultView) throws UserNotFoundException {
//cookies based flow
        //        Cookie[] cookies = req.getCookies();
//        if(cookies!=null){
//            for (Cookie cookie: cookies) {
//                if(cookie.getName().equals("email")){
//                    ResponseObject dbUser = reAuthenticate(cookie.getValue());
//                    model.addAttribute("user",dbUser);
//                    return "dashboard";
//                }
//            }
//        }
        HttpSession session = req.getSession();
        if(!session.isNew()){
            ResponseObject dbUser = new  ResponseObject(
                    session.getAttribute("firstname").toString(),
                    session.getAttribute("lastname").toString(),
                    session.getAttribute("email").toString(),
                    session.getAttribute("gender").toString(),
                    Integer.parseInt(session.getAttribute("age").toString())
                    );
            model.addAttribute("user",dbUser);
            return "dashboard";
        }

        req.getSession().invalidate();
        return defaultView;
    }
}

package First.Application.Controller;

import First.Application.CustomExceptions.BadRequestCustomException;
import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.Auth.LoginData;
import First.Application.Model.ResponseEntity.ResponseObject;
import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    UserServiceImplementation userImpl;

    @GetMapping("/homepage")
    public String homepage(Model model) {
        Map map = new HashMap<>(){};
        model.addAttribute("ok", "asas");
        return "homepage";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest req) {
        return "dashboard";
    }


    @GetMapping("/login")
    public String Login(HttpServletRequest req) {
        return "login";
    }

    @GetMapping("/register")
    public String register(HttpServletRequest req) {
        return "register";
    }

//    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE,MediaType.APPLICATION_JSON_VALUE}
    @CrossOrigin()
    @PostMapping(value = "/register"
    )
    public String register(Model model, @ModelAttribute UserRegistrationObject user) {
//         @RequestBody() UserRegistrationObject user,
        System.out.println(user);
//        User dbUser =userImpl.saveUser(user);
//        System.out.println(dbUser);
        return "dashboard";
    }

    @CrossOrigin()
    @PostMapping(value = {"/login","Login"}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public String Login(Model model, @RequestBody LoginData user, HttpServletRequest req, HttpServletResponse res) throws UserNotFoundException, BadRequestCustomException {
        System.out.println(user);
        ResponseEntity<?> responseEntity =userImpl.loginUser(user);
        ResponseObject dbUser = (ResponseObject) responseEntity.getBody();
        System.out.println(dbUser);

        Cookie username = new Cookie("email", dbUser.getEmail());
        username.setPath("/");
        username.setMaxAge(10);
        res.addCookie(username);

        return "dashboard";
    }
}

package First.Application.Controller.MVC;

import First.Application.CustomExceptions.BadRequestCustomException;
import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.Auth.LoginData;
import First.Application.Model.ResponseEntity.ResponseObject;
import First.Application.Model.UserRegistrationObject;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MVCPost {

    @Autowired
    UserServiceImplementation userImpl;

    @PostMapping(value = "/register")
    public String register( @ModelAttribute UserRegistrationObject user ) {
        userImpl.saveUser(user);
        return "login";
    }

    @PostMapping("/login")
    public String login( @ModelAttribute LoginData user, Model model, HttpServletResponse res) throws UserNotFoundException, BadRequestCustomException {
        ResponseObject userinfo = userImpl.loginUser(user);
        model.addAttribute("user", userinfo);

        Cookie principal = new Cookie("email", user.getEmail());
        principal.setPath("/");
        principal.setHttpOnly(true);

        res.addCookie(principal);

        return "dashboard";
    }

//    @CrossOrigin()
//    @PostMapping(value = {"/login","Login"})
//    public String Login(Model model, @ModelAttribute LoginData user, HttpServletRequest req, HttpServletResponse res) throws UserNotFoundException, BadRequestCustomException {
//        System.out.println(user);
////        ResponseEntity<?> responseEntity =userImpl.loginUser(user);
////        ResponseObject dbUser = (ResponseObject) responseEntity.getBody();
////        System.out.println(dbUser);
////
////        Cookie username = new Cookie("email", dbUser.getEmail());
////        username.setPath("/");
////        username.setMaxAge(60*60*2);
////        System.out.println(": "+username.getMaxAge());
////        res.addCookie(username);
////        model.addAttribute("user", dbUser);
//
//        return "dashboard";
//    }
}

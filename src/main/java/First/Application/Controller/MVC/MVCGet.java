package First.Application.Controller.MVC;

import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MVCGet {

    @Autowired
    UserServiceImplementation userImpl;

    @GetMapping("/homepage")
    public String homepage(Model model) {
        model.addAttribute("page", "dynamic data");
        return "homepage";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpServletRequest req, Model model) throws UserNotFoundException {
        return userImpl.dashboardRerouter(req, model,"login");
    }

    @GetMapping("/login")
    public String Login(HttpServletRequest req, Model model) throws UserNotFoundException {
        return userImpl.dashboardRerouter(req, model,"login");
    }

    @GetMapping("/register")
    public String register(HttpServletRequest req, Model model) throws UserNotFoundException {
        return userImpl.dashboardRerouter(req, model, "register");
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse res, HttpServletRequest req){
        System.out.println("tried to logout");

// Cookie based approach
//        Cookie cookie = new Cookie("email", "");
//        cookie.setMaxAge(0);
//        cookie.setHttpOnly(true);
//        res.addCookie(cookie);

        //session based approach
        req.getSession().invalidate();

        return "login";
    }

    @GetMapping("/verify")
    public String verifyUser(){
        return "verified";
    }


}

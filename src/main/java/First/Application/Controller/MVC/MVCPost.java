package First.Application.Controller.MVC;

import First.Application.CustomExceptions.BadRequestCustomException;
import First.Application.CustomExceptions.UserNotFoundException;
import First.Application.Model.Auth.LoginData;
import First.Application.Model.ResponseEntity.ResponseObject;
import First.Application.Model.User;
import First.Application.Model.UserRegistrationObject;
import First.Application.Services.UserServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MVCPost {

    @Autowired
    UserServiceImplementation userImpl;

    @PostMapping(value = "/register")
    public String register( @ModelAttribute UserRegistrationObject user ) throws MessagingException {
        userImpl.saveUser(user);
//        return userImpl.sendMail(user.getFirstName(),user.getEmail(),"Complete yor registration", "click on the link http://localhost:8080/verify/ to verify your account \n \n Kind Regards, \n Admin", "register","login");
        return userImpl.sendMailWithAtt(user.getFirstName(),user.getEmail(),"Complete yor registration", "" +
                "<div style=\" display: flex; flex-direction:column \">" +
                        "<div> click on the link <a href=\"http://localhost:8080/verify/\">Verify</a>  to verify your account</div>" +
                " <div style=\" margin-top: 20px \" >Kind Regards,</div>" +
                " <div style=\" margin-top: 20px \">Admin </div> " +
                        "</div>",
                "register","login");
//        return "login";
    }

    @PostMapping("/login")

    public String login(@ModelAttribute LoginData user, Model model, HttpServletRequest req) throws UserNotFoundException, BadRequestCustomException {
        HttpSession session = req.getSession();

        if(session.isNew()){
            ResponseObject userinfo = userImpl.loginUser(user);

            model.addAttribute("user", userinfo);

            session.setAttribute("email", userinfo.getEmail());
            session.setAttribute("age", userinfo.getAge());
            session.setAttribute("gender", userinfo.getGender());
            session.setAttribute("firstname", userinfo.getFirstname());
            session.setAttribute("lastname", userinfo.getLastname());
            System.out.println(session.getId());
        }

        return "dashboard";
    }

    //    @PostMapping("/login")
//    public String login( @ModelAttribute LoginData user, Model model, HttpServletResponse res) throws UserNotFoundException, BadRequestCustomException {
//        ResponseObject userinfo = userImpl.loginUser(user);
//        model.addAttribute("user", userinfo);
//
//        Cookie principal = new Cookie("email", user.getEmail());
//        principal.setPath("/");
//        principal.setHttpOnly(true);
//
//        res.addCookie(principal);
//
//        return "dashboard";
//    }

}

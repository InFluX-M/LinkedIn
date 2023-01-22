package com.example.Linkedin.Controller;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.request.UserLogin;
import com.example.Linkedin.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {

    private UserService userService;

    @GetMapping("/")
    public String firstPage() {
        return "first";
    }

    @GetMapping("/landing")
    public String landingPage(Model model) {

        List<User> influential = new ArrayList<>();
        List<User> temp;
        for (int i = 0; i < 4; i++) {
            influential.add(userService.getAllUsers().get(i));
        }
        model.addAttribute("influential", influential);

        List<User> important = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            important.add(userService.getAllUsers().get(i));
        }
        model.addAttribute("important", important);

        return "landing";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute(name = "loginForm") UserLogin userLogin, Model model) {

        String username = userLogin.getUsername();
        String pass = userLogin.getPassword();

        try {
            User user = userService.getUserByUsername(username);
            System.err.println(user);
            if (user.getPassword().equals(pass)) {
                model.addAttribute("uname", username);
                model.addAttribute("pass", pass);
                UserService.loggedInUser = user;
                System.out.println("UserLogin Success\nUsername: " + username + "\nPassword: " + pass);
                return "redirect:/users/feed";
            } else {
                // if userLogin failed
                System.out.println("UserLogin Failed\nUsername: " + username + "\nPassword: " + pass);
                model.addAttribute("error", "Incorrect Password");
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            // if userLogin failed
            System.out.println("UserLogin Failed\nUsername: " + username + "\nPassword: " + pass);
            model.addAttribute("error", "Incorrect Username");
            return "login";
        }
    }
}

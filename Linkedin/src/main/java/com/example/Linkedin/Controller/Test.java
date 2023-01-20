package com.example.Linkedin.Controller;

import com.example.Linkedin.Model.request.UserLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class Test {

    @GetMapping("/")
    public String firstPage() {
        return "first";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute(name = "loginForm") UserLogin userLogin, Model model) {

        String username = userLogin.getUsername();
        String pass = userLogin.getPassword();

        if (username.equals("Admin") && pass.equals("Admin@123")) {
            model.addAttribute("uname", username);
            model.addAttribute("pass", pass);
            System.out.println("UserLogin Success\nUsername: " + username + "\nPassword: " + pass);
            return "first";
        }

        // if userLogin failed
        model.addAttribute("error", "Incorrect Username & Password");
        return "login";
    }

    @GetMapping("/login")
    public String listStudents(Model model) {
        List<UserLogin> students = List.of(
                new UserLogin("John", "em1"),
                new UserLogin("Mary", "em2"),
                new UserLogin("Peter", "em3"),
                new UserLogin("Kate", "em4"),
                new UserLogin("Bob", "em5")
        );
        model.addAttribute("users", students);
        return "login";
    }
}

package com.example.Linkedin.Controller;

import com.example.Linkedin.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public String allUsersPage(Model model) {
        model.addAttribute("list", userService.getAllUsers());
        return "all-users";
    }
}

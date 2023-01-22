package com.example.Linkedin.Controller;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/feed")
    public String profilePage(Model model) {
        //todo
        model.addAttribute("connectionList", userService.getAllUsers());
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(userService.getAllUsers().get(i));
        }
        model.addAttribute("suggestionList", list);
        model.addAttribute("login", userService.getAllUsers().get(0));
        model.addAttribute("influentialList", list);
        return "feed";
    }
}

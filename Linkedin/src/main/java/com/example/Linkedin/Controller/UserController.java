package com.example.Linkedin.Controller;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Service.MLService;
import com.example.Linkedin.Service.SuggestionService;
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
    private final SuggestionService suggestionService;
    private final MLService mlService;

    @GetMapping("/all")
    public String allUsersPage(Model model) {
        model.addAttribute("list", userService.getAllUsers());
        return "all-users";
    }

    @GetMapping("/feed")
    public String profilePage(Model model) throws Exception {
        User login = UserService.loggedInUser;

        model.addAttribute("login", login);
        model.addAttribute("connectionList", userService.getConnections(login.getUsername()));

        List<User> suggestion = new ArrayList<>();
        List<User> temp = mlService.getRecommendations(login.getId(), "university");
        for (int i = 0; i < 5; i++)
            suggestion.add(temp.get(i));
        model.addAttribute("suggestionList", suggestion);

        List<User> influential = new ArrayList<>();
        temp = suggestionService.getSuggestions(login.getId(), "University,Workplace");
        for (int i = 0; i < 5; i++)
            influential.add(temp.get(i));
        model.addAttribute("influentialList", influential);

        return "feed";
    }
}

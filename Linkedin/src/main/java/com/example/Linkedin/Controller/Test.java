package com.example.Linkedin.Controller;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.request.UserLogin;
import com.example.Linkedin.Model.response.UserProfile;
import com.example.Linkedin.Service.SuggestionService;
import com.example.Linkedin.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class Test {

    UserService userService;

    @GetMapping("/test")
    public String testPage(Model model) {
        model.addAttribute("user", userService.getAllUsers().get(1));
        return "user-profile";
    }

//    @GetMapping("/users/connections")
//    public String connectionsPage() {
//        return "connections";
//    }

    @GetMapping("/ps")
    public String test(Model model) {
        model.addAttribute("list", List.of(
                new UserLogin("a", "b"),
                new UserLogin("c", "d"),
                new UserLogin("e", "f"),
                new UserLogin("g", "h"),
                new UserLogin("i", "j"),
                new UserLogin("k", "l"),
                new UserLogin("m", "n"),
                new UserLogin("o", "p"),
                new UserLogin("q", "r")));
        System.out.println("test");
        return "profile-card";
    }

    SuggestionService suggestionService;

    @GetMapping("/c")
    public ResponseEntity<Void> readUsers() {
        suggestionService.createGraph();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/c2/{id}/{impacts}")
    public ResponseEntity<List<UserProfile>> readUsers(@PathVariable String id, @PathVariable String impacts) {
        return new ResponseEntity<>(suggestionService.getSuggestions(id, impacts).stream().map(User::toProfileResponse).toList(), HttpStatus.OK);
    }

}

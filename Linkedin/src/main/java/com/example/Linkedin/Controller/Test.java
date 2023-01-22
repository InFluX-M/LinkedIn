package com.example.Linkedin.Controller;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.request.UserLogin;
import com.example.Linkedin.Model.response.UserProfile;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Service.SuggestionService;
import com.example.Linkedin.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@AllArgsConstructor
public class Test {

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

    @GetMapping("/users/connections")
    public String connectionsPage() {
        return "connections";
    }

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

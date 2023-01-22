package com.example.Linkedin.Controller;

import com.example.Linkedin.Model.User;
import com.example.Linkedin.Model.request.UserSignup;
import com.example.Linkedin.Model.response.UserProfile;
import com.example.Linkedin.Model.response.UserResponse;
import com.example.Linkedin.Service.MLService;
import com.example.Linkedin.Service.SuggestionService;
import com.example.Linkedin.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody UserSignup user) {
        return new ResponseEntity<>(userService.signUp(user), HttpStatus.OK);
    }

    @PostMapping("/addConnection/{username}")
    public ResponseEntity<Void> addConnection(@PathVariable String username) {
        return new ResponseEntity<>(userService.addConnection(username), HttpStatus.OK);
    }

    @PostMapping("/login/{username}/{password}")
    public ResponseEntity<UserResponse> login( @PathVariable String username, @PathVariable String password) {
        return new ResponseEntity<>(userService.login(username, password), HttpStatus.OK);
    }

    @PostMapping("/addSpeciality/{speciality}")
    public ResponseEntity<UserResponse> addSpeciality(@PathVariable String speciality) {
        return new ResponseEntity<>(userService.addSpeciality(speciality), HttpStatus.OK);
    }

    @PostMapping("/removeSpeciality/{speciality}")
    public ResponseEntity<UserResponse> removeSpeciality(@PathVariable String speciality) {
        return new ResponseEntity<>(userService.removeSpeciality(speciality), HttpStatus.OK);
    }


    @GetMapping("/getSuggestion/{idUser}/{impact}")
    public ResponseEntity<List<UserProfile>> getSuggestion(@PathVariable String idUser, @PathVariable String impact) {
        return new ResponseEntity<>(suggestionService.getSuggestions(idUser, impact).stream().map(User::toProfileResponse).toList(), HttpStatus.OK);
    }

    @GetMapping("/getInfluence")
    public ResponseEntity<List<UserProfile>> getInfluence() {
        return new ResponseEntity<>(suggestionService.getInfluenceUsers().stream().map(User::toProfileResponse).toList(), HttpStatus.OK);
    }

    @GetMapping("/getRecomendation/{idUser}/{type}")
    public ResponseEntity<List<UserProfile>> getRecommendation(@PathVariable String idUser, @PathVariable String type) throws Exception {
        return new ResponseEntity<>(mlService.getRecommendations(idUser,  type).stream().map(User::toProfileResponse).toList(), HttpStatus.OK);
    }

    @GetMapping("/clusters/{type}")
    public ResponseEntity<List<List<UserProfile>>> getClusters(@PathVariable String type) {
        return new ResponseEntity<>(mlService.getClusters(type).stream().map(l->l.stream().map(User::toProfileResponse).toList()).toList(), HttpStatus.OK);
    }

    @GetMapping("/anomaly")
    public ResponseEntity<List<UserProfile>> getAnomalyDetection() throws Exception {
        return new ResponseEntity<>(mlService.getAnomalyDetection().stream().map(User::toProfileResponse).toList(), HttpStatus.OK);
    }



}

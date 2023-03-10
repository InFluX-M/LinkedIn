package com.example.Linkedin.File;

import com.example.Linkedin.Model.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {

    private FileService fileService;

    @PostMapping("/readUsers")
    public ResponseEntity<List<UserResponse>> readUsers() {
        return new ResponseEntity<>(fileService.readUsers(), HttpStatus.CREATED);
    }

//    @PostMapping("/writeUsers")
//    public ResponseEntity<Void> writeUsers() {
//        return new ResponseEntity<>(fileService.writeUsers(), HttpStatus.CREATED);
//    }
}

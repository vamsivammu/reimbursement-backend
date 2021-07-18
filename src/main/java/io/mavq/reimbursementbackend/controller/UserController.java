package io.mavq.reimbursementbackend.controller;

import io.mavq.reimbursementbackend.dto.SignInDto;
import io.mavq.reimbursementbackend.dto.SignUpDto;
import io.mavq.reimbursementbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/signin")
    public ResponseEntity<Map<String,String>> signin(@RequestBody SignInDto signInDto) throws Exception{
        return new ResponseEntity<>(userService.signin(signInDto),HttpStatus.OK);
    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Boolean> signup(@RequestBody SignUpDto signUpDto) throws Exception{
        return new ResponseEntity<>(userService.signup(signUpDto),HttpStatus.OK);
    }
}

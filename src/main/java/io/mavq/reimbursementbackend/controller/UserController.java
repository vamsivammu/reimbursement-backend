package io.mavq.reimbursementbackend.controller;

import io.mavq.reimbursementbackend.dto.SignInDto;
import io.mavq.reimbursementbackend.dto.SignUpDto;
import io.mavq.reimbursementbackend.dto.UserDto;
import io.mavq.reimbursementbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/signin")
    public ResponseEntity<UserDto> signin(@RequestBody SignInDto signInDto, HttpServletResponse response) throws ResponseStatusException{

        UserDto userDto = this.userService.signin(signInDto);
        String refreshToken = this.userService.encodeRefreshToken(String.valueOf(userDto.getId()));
        final Cookie cookie = new Cookie("jid",refreshToken);
        cookie.setMaxAge(8640000);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return new ResponseEntity<>(userService.signin(signInDto),HttpStatus.OK);

    }

    @PostMapping(value = "/signup")
    public ResponseEntity<Boolean> signup(@RequestBody SignUpDto signUpDto) throws ResponseStatusException{
        return new ResponseEntity<>(userService.signup(signUpDto),HttpStatus.OK);
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<UserDto> refreshToken(@CookieValue(value = "jid") String jid, HttpServletResponse response) throws ResponseStatusException{
        System.out.println("refresh point");

        if(jid == null){
            throw new ResponseStatusException(HttpStatus.resolve(406));
        }
        try{
            return new ResponseEntity<>(this.userService.verifyRefreshToken(jid),HttpStatus.OK);
        }catch (Exception e){
            final Cookie cookie = new Cookie("jid",null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Session expired");
        }
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletResponse response){
        final Cookie cookie = new Cookie("jid",null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.setStatus(200);
    }
}

package io.mavq.reimbursementbackend.interceptor;

import io.mavq.reimbursementbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("interceptor");
        Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));
        if(authHeader.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String[] arr = authHeader.get().split(" ");
        if(arr.length!=2){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.get().split(" ")[1];
        try{
            Map<String,String> userData = this.userService.verifyToken(token);
            request.setAttribute("user",userData);
            return true;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }
}

package io.mavq.reimbursementbackend.interceptor;

import io.mavq.reimbursementbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService = new UserService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("interceptor");
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
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
            request.setAttribute("userData",userData);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getCause());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            return;
        }

        if(response.getStatus() >=400 ){
            final Cookie cookie = new Cookie("jid",null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
    }
}

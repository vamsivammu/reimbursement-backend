package io.mavq.reimbursementbackend.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.mavq.reimbursementbackend.dto.SignInDto;
import io.mavq.reimbursementbackend.dto.SignUpDto;
import io.mavq.reimbursementbackend.model.User;
import io.mavq.reimbursementbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

//    @Autowired
//    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public UserService(){

    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

     public Map<String,String> signin(SignInDto signInDto) throws ResponseStatusException {
        Optional<User> userData = Optional.ofNullable(userRepository.findByEmail(signInDto.getEmail()));
        if(userData.isPresent()){
            if(this.comparePasswords(signInDto.getPassword(),userData.get().getPassword())){
                Map<String,String> map = new HashMap<String,String>();
                map.put("token",this.encodeToken(String.valueOf(userData.get().getId()),userData.get().getRole()));
                return map;
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Wrong password");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email not registered");
        }
    }

    public boolean signup(SignUpDto signUpDto) throws ResponseStatusException{
        Optional<User> userData = Optional.ofNullable(userRepository.findByEmail(signUpDto.getEmail()));
        if(userData.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already registered");
        }else{
            String hashed = this.getHashedPassword(signUpDto.getPassword());
            User user = new User(signUpDto);
            this.userRepository.save(user);
            return true;
        }
    }

    private boolean comparePasswords(String password, String passwordFromDb){
        return this.bCryptPasswordEncoder().matches(password,passwordFromDb);
    }

    public String encodeToken(String userId, String role){
        Algorithm alg = Algorithm.HMAC256("jwtsecret");
        Map<String,String> map = new HashMap<>();
        map.put("id",userId);
        map.put("role",role);
        String token = JWT.create().withPayload(map).withExpiresAt(new Date(System.currentTimeMillis() + 86400000)).sign(alg);
        return token;
    }

    public Map<String,String> verifyToken(String token) throws Exception {
         Algorithm alg = Algorithm.HMAC256("jwtsecret");
         JWTVerifier verifier = JWT.require(alg).build();
         Map<String, Claim> payload = verifier.verify(token).getClaims();
         Map<String,String> userData = new HashMap<>();
         userData.put("id",payload.get("id").asString());
         userData.put("role",payload.get("role").asString());
         return userData;
    }

    private String getHashedPassword(String password){
        return this.bCryptPasswordEncoder().encode(password);
    }
}

package com.fire.social.services;

import com.fire.social.database.Role;
import com.fire.social.database.RolesRepository;
import com.fire.social.database.User;
import com.fire.social.database.UsersRepository;
import com.fire.social.dto.responses.AuthorizationResponse;
import com.fire.security.database.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    TokensService tokensService;
    public ResponseEntity<AuthorizationResponse> authorization(String login, String password){
        User user = usersRepository.authorization(login, password);
        Role role = rolesRepository.findByName("user");
        if (user != null){
            String token = tokensService.findToken(user, role);
            return new ResponseEntity<>(
                    new AuthorizationResponse(token),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new AuthorizationResponse(null),
                HttpStatus.BAD_REQUEST
        );
    }

    public ResponseEntity<AuthorizationResponse> authorization(String token){
        Role role = rolesRepository.findByName("user");
        String tokenValue = tokensService.findToken(token, role);
        if (tokenValue != null){
            return new ResponseEntity<>(
                    new AuthorizationResponse(tokenValue),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new AuthorizationResponse(null),
                HttpStatus.BAD_REQUEST
        );
    }
}

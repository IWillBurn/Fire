package com.fire.security.services;

import com.fire.security.database.Role;
import com.fire.security.database.RolesRepository;
import com.fire.security.database.User;
import com.fire.security.database.UsersRepository;
import com.fire.security.dto.responses.RegistrationResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    TokensService tokensService;

    public ResponseEntity<RegistrationResponse> registration(String login, String password, String email){
        if (usersRepository.findByEmail(email) == null){
            Role userRole = rolesRepository.findByName("user");
            Role apiRole = rolesRepository.findByName("api");
            List<Role> roles = new ArrayList<>();
            roles.add(userRole);
            roles.add(apiRole);
            User user = new User(login, password, email, roles);
            user = usersRepository.save(user);
            String token = tokensService.generateToken(user, userRole);
            return new ResponseEntity<>(
                    new RegistrationResponse(token),
                    HttpStatus.CREATED
            );
        }
        return new ResponseEntity<>(
                new RegistrationResponse(null),
                HttpStatus.CONFLICT
        );
    }
}

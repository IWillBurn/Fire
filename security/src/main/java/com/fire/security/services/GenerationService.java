package com.fire.security.services;

import com.fire.security.database.*;
import com.fire.security.dto.responses.AuthorizationResponse;
import com.fire.security.dto.responses.GenerateResponse;
import com.fire.security.dto.responses.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GenerationService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    PermissionsRepository permissionsRepository;
    @Autowired
    TokensService tokensService;
    @Autowired
    PermissionService permissionService;

    public ResponseEntity<GenerateResponse> generate(String tokenValue, String roleName){
        System.out.println(tokenValue);
        Role role = rolesRepository.findByName(roleName);
        if (role == null){
            return new ResponseEntity<>(
                    new GenerateResponse(null),
                    HttpStatus.BAD_REQUEST
            );
        }
        Token token = tokensService.findToken(tokenValue);
        Permission permission = permissionsRepository.findByName("GENERATE");
        if (!permissionService.havePermission(token.getRole(), permission)){
            return new ResponseEntity<>(
                    new GenerateResponse(null),
                    HttpStatus.FORBIDDEN
            );
        }
        tokenValue = tokensService.findToken(token.getUser(), role);
        return new ResponseEntity<>(
                new GenerateResponse(tokenValue),
                HttpStatus.CREATED
        );
    }
}

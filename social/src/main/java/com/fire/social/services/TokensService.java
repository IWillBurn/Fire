package com.fire.social.services;

import com.fire.social.database.*;
import com.fire.social.dto.responses.PermissionsResponse;
import com.fire.social.dto.responses.ResetResponse;
import com.fire.security.database.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokensService {
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    TokensRepository tokensRepository;

    @Autowired
    PermissionsRepository permissionsRepository;

    @Autowired
    PermissionService permissionService;

    public String generateToken(User user, Role role){
        if (user.getRoles().contains(role)){
            Token token = new Token(generateTokenName(user, role), user, role);
            token = tokensRepository.save(token);
            return token.getToken();
        }
        return null;
    }

    public String findToken(User user, Role role){
        Token token = tokensRepository.findToken(user.getLogin(), role.getRoleName());
        if (token != null){
            return token.getToken();
        }
        String tokenValue = generateToken(user, role);
        return tokenValue;
    }

    public String findToken(String tokenValue, Role role){
        Token token = tokensRepository.findToken(tokenValue);
        if (token != null && token.getRole().equals(role)){
            return token.getToken();
        }
        return null;
    }

    public Token findToken(String tokenValue){
        Token token = tokensRepository.findToken(tokenValue);
        return token;
    }

    @Transactional
    public ResponseEntity<ResetResponse> resetAll(String tokenValue){
        Token token = findToken(tokenValue);
        Permission permission = permissionsRepository.findByName("RESET");
        if (!permissionService.havePermission(token.getRole(), permission)){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.FORBIDDEN
            );
        }
        User user = token.getUser();
        List<Token> tokens = tokensRepository.findAll(user.getId());
        tokensRepository.deleteAllInBatch(tokens);
        Role role = rolesRepository.findByName("user");
        return new ResponseEntity<>(
                new ResetResponse(generateToken(user, role)),
                HttpStatus.ACCEPTED
        );
    }

    public ResponseEntity<ResetResponse> getToken(String tokenValue, String roleName){
        Token token = findToken(tokenValue);
        Permission permission = permissionsRepository.findByName("GET");
        if (!permissionService.havePermission(token.getRole(), permission)){
            return new ResponseEntity<>(
                    null,
                    HttpStatus.FORBIDDEN
            );
        }
        Role role = rolesRepository.findByName(roleName);
        String gettingToken = findToken(token.getUser(), role);
        return new ResponseEntity<>(
                new ResetResponse(gettingToken),
                HttpStatus.OK
        );
    }

    public ResponseEntity<PermissionsResponse> getPermissions(String tokenValue){
        Token token = tokensRepository.findToken(tokenValue);
        if (token == null){
            return new ResponseEntity<>(
                    new PermissionsResponse(null),
                    HttpStatus.BAD_REQUEST
            );
        }
        return permissionService.getPermissions(token.getRole());
    }
    private String generateTokenName(User user, Role role){
        return "token-name-" + user.getEmail() + "-role-" + role.getRoleName();
    }
}

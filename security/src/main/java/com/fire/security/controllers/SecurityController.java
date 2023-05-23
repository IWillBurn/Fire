package com.fire.security.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fire.security.dto.requests.AuthorizationRequest;
import com.fire.security.dto.requests.GenerateRequest;
import com.fire.security.dto.requests.RegistrationRequest;
import com.fire.security.dto.responses.*;
import com.fire.security.services.*;
import com.sun.net.httpserver.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    RegistrationService registrationService;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    GenerationService generationService;

    @Autowired
    TokensService tokensService;
    @PostMapping(value = "/registration")
    public ResponseEntity<RegistrationResponse> registration(@RequestBody RegistrationRequest request){
        return registrationService.registration(request.getLogin(), request.getPassword(), request.getEmail());
    }

    @PostMapping(value = "/authorization")
    public ResponseEntity<AuthorizationResponse> authorization(@RequestBody AuthorizationRequest request){
        return authorizationService.authorization(request.getLogin(), request.getPassword());
    }

    @PostMapping(value = "/token/authorization")
    public ResponseEntity<AuthorizationResponse> authorization(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return authorizationService.authorization(token);
    }

    @PostMapping(value = "/tokens/generate")
    public ResponseEntity<GenerateResponse> generateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody GenerateRequest request){
        return generationService.generate(token, request.getRole());
    }

    @PutMapping(value = "/tokens/reset")
    public ResponseEntity<ResetResponse> resetToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token){
        return tokensService.resetAll(token);
    }

    @GetMapping(value = "/tokens/get")
    public ResponseEntity<ResetResponse> getToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestParam String role){
        return tokensService.getToken(token, role);
    }

    @GetMapping(value = "/tokens/permissions")
    public ResponseEntity<PermissionsResponse> getPermissions(@RequestParam String token){
        return tokensService.getPermissions(token);
    }
}

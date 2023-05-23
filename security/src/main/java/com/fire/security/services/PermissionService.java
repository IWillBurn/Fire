package com.fire.security.services;

import com.fire.security.database.*;
import com.fire.security.dto.responses.GenerateResponse;
import com.fire.security.dto.responses.PermissionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionService {

    @Autowired
    RolesRepository rolesRepository;
    public Boolean havePermission(Role role, Permission checkingPermission){
        for (Permission permission : role.getPermissions()){
            if (checkingPermission.equals(permission)){
                return true;
            }
        }
        return false;
    }

    public ResponseEntity<PermissionsResponse> getPermissions(Role role){
        List<String> permissions = new ArrayList<>();
        for (Permission permission : role.getPermissions()){
            permissions.add(permission.getPermName());
        }
        return new ResponseEntity<>(
                new PermissionsResponse(permissions),
                HttpStatus.OK
        );
    }
}

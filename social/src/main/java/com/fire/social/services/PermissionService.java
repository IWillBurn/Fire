package com.fire.social.services;

import com.fire.social.database.Permission;
import com.fire.social.database.Role;
import com.fire.social.database.RolesRepository;
import com.fire.social.dto.responses.PermissionsResponse;
import com.fire.security.database.*;
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

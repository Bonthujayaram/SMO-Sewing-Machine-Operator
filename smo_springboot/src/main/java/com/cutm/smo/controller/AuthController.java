package com.cutm.smo.controller;

import com.cutm.smo.dto.LoginRequest;
import com.cutm.smo.dto.RoleResponse;
import com.cutm.smo.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RoleResponse> login(@RequestBody LoginRequest request) {
        try {
            RoleResponse roleResponse = authService.verifyLogin(request);
            return ResponseEntity.ok(roleResponse);
        } catch (ResponseStatusException e) {
            RoleResponse errorResponse = new RoleResponse(e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(errorResponse);
        } catch (Exception e) {
            RoleResponse errorResponse = new RoleResponse("An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
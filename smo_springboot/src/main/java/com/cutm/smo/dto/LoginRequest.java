package com.cutm.smo.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String loginid;
    private String password;
}
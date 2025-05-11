package com.cutm.smo.dto;

public class RoleResponse {
    private String role;
    private String employeeName;

    // Constructor with three parameters
    public RoleResponse(String role, String employeeName) {
        this.role = role;
        this.employeeName = employeeName;
    }

    // Default constructor (required for Jackson)
    public RoleResponse() {
    }

    // Constructor for error cases (optional)
    public RoleResponse(String errorMessage) {
        this.role = errorMessage;
        this.employeeName = null;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

  
}
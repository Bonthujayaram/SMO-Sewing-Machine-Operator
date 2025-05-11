package com.cutm.smo.services;

import com.cutm.smo.dto.LoginRequest;
import com.cutm.smo.dto.RoleResponse;
import com.cutm.smo.models.EmployeeLoginTable;
import com.cutm.smo.models.RolesTable;
import com.cutm.smo.repositories.EmployeeLoginTableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private EmployeeLoginTableRepository employeeLoginRepo;

    public RoleResponse verifyLogin(LoginRequest request) {
        EmployeeLoginTable employee = employeeLoginRepo.findByLoginid(request.getLoginid());

        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid login ID");
        }

        if (!employee.getPassword().equals(request.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }

        if (!employee.isStatus()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Account is inactive");
        }

        RolesTable role = employee.getRole();
        if (role == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Role not found for user");
        }


        // Get employee name from EmployeeInfoTable
        String employeeName = employee.getEmployeeinfo() != null 
            ? employee.getEmployeeinfo().getName() 
            : "Unknown";
        if (employee.getEmployeeinfo() == null) {
            logger.warn("EmployeeInfo not found for login ID: {}", request.getLoginid());
        }

        logger.info("Returning role, name, and ID for user {}: Role - {}, Name - {}", 
            request.getLoginid(), role.getRolename(), employeeName);

        return new RoleResponse(role.getRolename(), employeeName);
    }
}
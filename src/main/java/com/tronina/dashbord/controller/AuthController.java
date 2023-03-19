package com.arturishmaev.documentflow.controller;

import com.arturishmaev.documentflow.entity.EmployeeEntity;
import com.arturishmaev.documentflow.service.GeneralService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {

    private final GeneralService<EmployeeEntity> employeeGeneralService;

    public AuthController(GeneralService<EmployeeEntity> employeeGeneralService) {
        this.employeeGeneralService = employeeGeneralService;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody EmployeeEntity employee) {

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(employee.getEmail(), employee.getPassword()));
        log.info("Success Authentication");
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody EmployeeEntity employee) {

        if (employeeGeneralService.existsByName(employee.getEmail())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        employeeGeneralService.saveOrUpdate(employee);
        log.info("Success Registration");
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}

package org.jds.sandbox.api.controller;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.jds.sandbox.api.authentication.Authenticate;
import org.jds.sandbox.api.authentication.NewUser;
import org.jds.sandbox.api.jwt.JwtResponse;
import org.jds.sandbox.api.model.Role;
import org.jds.sandbox.api.model.RoleName;
import org.jds.sandbox.api.model.User;
import org.jds.sandbox.api.repository.RoleRepository;
import org.jds.sandbox.api.repository.UserRepository;
import org.jds.sandbox.api.security.jwt.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/validate")
public class ValidateRestAPIs {
	
	Logger logger = LoggerFactory.getLogger(ValidateRestAPIs.class);

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/json")
    public ResponseEntity<?> validateJSON(@Valid @RequestBody Authenticate loginRequest) {

    	logger.info("Step 1");
    	logger.info("username: " + loginRequest.getUsername());
    	logger.info("password: " + loginRequest.getPassword());
    	
    	// Use: https://bcrypt-generator.com/    	
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        
        logger.info("Step 2");
        
        logger.info(authentication.toString());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        logger.info("Step 3");

        String jwt = jwtProvider.generateJwtToken(authentication);
        
        logger.info("Step 4: " + jwt);
        
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

}
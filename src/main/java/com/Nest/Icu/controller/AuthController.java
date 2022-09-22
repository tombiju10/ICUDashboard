package com.Nest.Icu.controller;

import com.Nest.Icu.config.JwtTokenUtility;
import com.Nest.Icu.model.AuthRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class AuthController {

	@Autowired
	private JwtTokenUtility jh;
	
	@Autowired
	private AuthenticationManager am;

	@Operation(summary="Authentication Controller")
	@PostMapping("/auth")
	public String generateToken(@RequestBody AuthRequest req) throws Exception {
		try {
			am.authenticate(new UsernamePasswordAuthenticationToken(req.getUserName(), req.getPassword()));
		}
		catch(Exception e) {
			throw new Exception("invalid username or password"+e.toString());
		}
		return jh.generateToken(req.getUserName());
	}
}

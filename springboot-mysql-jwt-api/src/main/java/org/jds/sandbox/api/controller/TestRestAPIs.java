package org.jds.sandbox.api.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestAPIs {
	
	@GetMapping("/api/test/public")
	public String publicAccess() {
		return "Public content";
	}

	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return "User content";
	}

	@GetMapping("/api/test/business")
	@PreAuthorize("hasRole('BUSINESS') or hasRole('ADMIN')")
	public String businessAccess() {
		return "Business content";
	}
	
	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin content";
	}
}
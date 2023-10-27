package com.walmart.LoginModule.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  //API can be accessed by any user irrespective of roles
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  //API can be accessed by user with any role USER, GUEST, ADMIN
  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('GUEST') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Page.";
  }

  //GUEST role user can access this API
  @GetMapping("/guest")
  @PreAuthorize("hasRole('GUEST')")
  public String moderatorAccess() {
    return "Guest Page.";
  }

  //ADMIN role user can access this API
  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Page.";
  }

  //API to identify user is logged-in or not
  @GetMapping("/home")
  public ResponseEntity<String> homePage(){
    Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
    if (authentication1.getName() != "anonymousUser" && authentication1 != null && authentication1.isAuthenticated()){
      return ResponseEntity.ok("Welcome, "+authentication1.getName()+"!!");
    }
    else {
      return ResponseEntity.ok("Not Logged in!!");
    }
  }
}

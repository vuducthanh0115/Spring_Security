package com.example.spring_security.basic.controller;

import com.example.spring_security.basic.entity.User;
import com.example.spring_security.basic.payload.LoginRequest;
import com.example.spring_security.basic.payload.LoginResponse;
import com.example.spring_security.basic.payload.RandomStuff;
import com.example.spring_security.basic.service.UserService;
import com.example.spring_security.basic.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;
    @Autowired
    private UserServiceImpl userService2;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //@PreAuthorize("hasRole('USER')")
    @GetMapping("/get-all")
    List<User> getAll() {
        return userService.getAll();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-by-id/{id}")
    Optional<User> getById(@PathVariable("id") Long id) {
        return userService.findId(id);
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    ResponseEntity<String> add(@RequestBody LoginRequest user) {
        User user2 = new User(user.getUsername(), user.getPassword());
        userService.add(user2);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/update/{id}")
    User update(@RequestBody User user, @PathVariable("id") Long id) {
        return userService.update(user, id);
    }

    @GetMapping("/delete/{id}")
    void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @GetMapping("/login")
    public LoginResponse authenticateUsers(@RequestBody LoginRequest loginRequest) {
        return userService2.authenticateUser(loginRequest);
    }
    @GetMapping("/random")
    public RandomStuff randomStuff(){
        return new RandomStuff("JWT hợp lệ");
    }
}

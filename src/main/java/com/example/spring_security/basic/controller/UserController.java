package com.example.spring_security.basic.controller;

import com.example.spring_security.basic.entity.User;
import com.example.spring_security.basic.payload.LoginRequest;
import com.example.spring_security.basic.payload.RandomStuff;
import com.example.spring_security.basic.service.UserService;
import com.example.spring_security.basic.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
    public String authenticateUsers(
            @RequestBody LoginRequest loginRequest,
            HttpSession session,
            @CookieValue(value = "login", defaultValue = "user_") String rand,
            HttpServletResponse httpServletResponse
    ) {
        var tmp = userService2.authenticateUser(loginRequest);
        Cookie cookie =null;
        if (tmp != null) {
            session.setAttribute("user", loginRequest);
            cookie = new Cookie("login", rand + UUID.randomUUID());
            cookie.setMaxAge(10);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            httpServletResponse.addCookie(cookie);
        }
        return cookie.getValue();
    }

    @GetMapping("/random")
    public ResponseEntity<String> randomStuff(HttpSession session) throws ChangeSetPersister.NotFoundException {
        //session.invalidate();
        LoginRequest loginRequest2 = (LoginRequest) session.getAttribute("user");
        if (loginRequest2 == null) {
            throw new ChangeSetPersister.NotFoundException();
        }
        return ResponseEntity.ok("Session - " + "UserName : " + loginRequest2.getUsername() + " " + "Password :" + loginRequest2.getPassword());

    }
}

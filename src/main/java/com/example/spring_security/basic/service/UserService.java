package com.example.spring_security.basic.service;
import com.example.spring_security.basic.entity.User;
import com.example.spring_security.basic.jwt.JwtTokenProvider;
import com.example.spring_security.basic.payload.LoginRequest;
import com.example.spring_security.basic.payload.LoginResponse;
import com.example.spring_security.basic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Transactional
@Service
public class UserService implements UserDetailsService {
    @Autowired
    PasswordEncoder passwordEncoder;

    private UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findId(Long id) {
        return userRepository.findById(id);
    }

    public User add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user, Long userId) {
        User tmplUser = userRepository.findById(userId).orElse(null);
        if (tmplUser != null) {
            return userRepository.save(tmplUser);
        }
        return null;
    }

    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDetails loadUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user;
    }
}

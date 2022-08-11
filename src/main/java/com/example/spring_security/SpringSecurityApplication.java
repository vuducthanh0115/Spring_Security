package com.example.spring_security;

import com.example.spring_security.basic.entity.User;
import com.example.spring_security.basic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = {
        "com.example.spring_security.basic",
})
public class SpringSecurityApplication  implements CommandLineRunner{
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("toan90");
        user.setPassword(passwordEncoder.encode("12345"));
        userRepository.save(user);
        System.out.println(user);
    }
}

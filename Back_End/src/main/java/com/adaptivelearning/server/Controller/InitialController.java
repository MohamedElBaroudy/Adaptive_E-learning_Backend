package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class InitialController implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){
        User user = userRepository.findByUsername("Admin");
        if(user != null)
            return;

        String password = passwordEncoder.encode("123456789123456789");
        User admin = new User("admin","user","admin@gmail.com","Admin",password, LocalDate.now(),(short) 1);
        userRepository.save(admin);
    }
}

package com.adaptivelearning.server.Controller;

import com.adaptivelearning.server.Model.Category;
import com.adaptivelearning.server.Model.User;
import com.adaptivelearning.server.Repository.CategoryRepository;
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
    CategoryRepository categoryRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args){
        addCategories();
//        userRepository.clearTokens();
        User user = userRepository.findByUsername("Admin");
        if(user != null)
            return;

        String password = passwordEncoder.encode("123456789123456789");
        User admin = new User("admin","user","admin@gmail.com","Admin",password, LocalDate.now(),(short) 1);
        userRepository.save(admin);
    }

    public void addCategories(){
        if (categoryRepository.findByName("Development") == null){
            Category category = new Category("Development",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("IT and Software") == null){
            Category category = new Category("IT and Software",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Personal Development") == null){
            Category category = new Category("Personal Development",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Design") == null){
            Category category = new Category("Design",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Marketing") == null){
            Category category = new Category("Marketing",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Music") == null){
            Category category = new Category("Music",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Math") == null){
            Category category = new Category("Math",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Science") == null){
            Category category = new Category("Science",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Social Science") == null){
            Category category = new Category("Social Science",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Language") == null){
            Category category = new Category("Language",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Engineering") == null){
            Category category = new Category("Engineering",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Business") == null){
            Category category = new Category("Business",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("History") == null){
            Category category = new Category("History",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Health&fitness") == null){
            Category category = new Category("Health&fitness",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Physics") == null){
            Category category = new Category("Physics",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Chemistry") == null){
            Category category = new Category("Chemistry",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Economics") == null){
            Category category = new Category("Economics",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Communication") == null){
            Category category = new Category("Communication",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Architecture") == null){
            Category category = new Category("Architecture",true);
            categoryRepository.save(category);
        }
        if (categoryRepository.findByName("Biology") == null){
            Category category = new Category("Biology",true);
            categoryRepository.save(category);
        }
    }
}

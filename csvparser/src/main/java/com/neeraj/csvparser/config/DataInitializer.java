package com.neeraj.csvparser.config;

import com.neeraj.csvparser.entity.User;
import com.neeraj.csvparser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
        // Create sample users if they don't exist
        createUserIfNotExists("admin", "admin123", "Admin", "User", "admin@company.com", User.Role.ADMIN);
        createUserIfNotExists("manager", "manager123", "Manager", "User", "manager@company.com", User.Role.MANAGER);
        createUserIfNotExists("employee", "employee123", "Employee", "User", "employee@company.com", User.Role.EMPLOYEE);
        createUserIfNotExists("john", "john123", "John", "Doe", "john@company.com", User.Role.EMPLOYEE);
        createUserIfNotExists("jane", "jane123", "Jane", "Smith", "jane@company.com", User.Role.EMPLOYEE);
    }
    
    private void createUserIfNotExists(String username, String password, String firstName, String lastName, String email, User.Role role) {
        if (!userService.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setRole(role);
            user.setEnabled(true);
            
            userService.saveUser(user);
            System.out.println("Created user: " + username);
        }
    }
} 
package com.techmart.service;

import com.techmart.entity.User;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;

@Remote
public interface AuthService {
    User login(String identifier, String password);
    boolean registerCustomer(User user);
    boolean isIdentifierTaken(String email, String mobile, String username);
}
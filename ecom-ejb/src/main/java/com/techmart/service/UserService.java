package com.techmart.service;

import com.techmart.entity.User;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface UserService {
    List<User> getAllCustomers();

    void toggleCustomerStatus(Long userId);
}

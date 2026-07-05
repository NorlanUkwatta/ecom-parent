package com.techmart.impl;

import com.techmart.core.enums.Role;
import com.techmart.entity.User;
import com.techmart.service.UserService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class UserServiceImpl implements UserService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public List<User> getAllCustomers() {
        return em.createQuery("SELECT u FROM User u WHERE u.role = :role ORDER BY u.id DESC", User.class)
                .setParameter("role", Role.CUSTOMER)
                .getResultList();
    }

    @Override
    public void toggleCustomerStatus(Long userId) {
        User user = em.find(User.class, userId);
        if (user != null && user.getRole() == Role.CUSTOMER) {
            user.setActive(!user.isActive());
            em.merge(user);
        }
    }
}

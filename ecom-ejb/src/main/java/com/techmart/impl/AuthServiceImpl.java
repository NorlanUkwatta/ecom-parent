package com.techmart.impl;

import com.techmart.entity.User;
import com.techmart.service.AuthService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.NoResultException;

@Stateless
public class AuthServiceImpl implements AuthService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public User login(String identifier, String password) {
        try {
            // Allows Admin (username) or Customer (email/mobile) to log in
            return em.createQuery(
                            "SELECT u FROM User u WHERE (u.email = :id OR u.mobile = :id OR u.username = :id) " +
                                    "AND u.password = :password AND u.active = true", User.class)
                    .setParameter("id", identifier)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean registerCustomer(User user) {
        if (isIdentifierTaken(user.getEmail(), user.getMobile(), user.getUsername())) {
            return false;
        }
        em.persist(user);
        return true;
    }

    @Override
    public boolean isIdentifierTaken(String email, String mobile, String username) {
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM User u WHERE u.email = :email OR u.mobile = :mobile OR u.username = :username", Long.class)
                .setParameter("email", email)
                .setParameter("mobile", mobile)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }
}
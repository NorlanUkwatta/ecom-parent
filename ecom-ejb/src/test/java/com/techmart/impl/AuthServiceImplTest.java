package com.techmart.impl;

import com.techmart.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @Mock
    private EntityManager em;

    @Mock
    private TypedQuery<User> userQuery;

    @Mock
    private TypedQuery<Long> countQuery;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success_ReturnsUser() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setActive(true);

        when(em.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
        when(userQuery.setParameter(anyString(), any())).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenReturn(mockUser);

        User result = authService.login("testuser", "password123");

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void testLogin_InvalidCredentials_ReturnsNull() {
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
        when(userQuery.setParameter(anyString(), any())).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenThrow(new NoResultException());

        User result = authService.login("wronguser", "wrongpass");

        assertNull(result);
    }

    @Test
    void testRegisterCustomer_WhenIdentifierTaken_ReturnsFalse() {
        when(em.createQuery(anyString(), eq(Long.class))).thenReturn(countQuery);
        when(countQuery.setParameter(anyString(), any())).thenReturn(countQuery);
        when(countQuery.getSingleResult()).thenReturn(1L);

        User newUser = new User();
        newUser.setEmail("taken@email.com");

        boolean result = authService.registerCustomer(newUser);

        assertFalse(result);
        verify(em, never()).persist(any(User.class));
    }
}
package com.techmart.impl;

import com.techmart.entity.Notification;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class NotificationServiceImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarkAsRead_FlipsBooleanAndMerges() {
        Notification mockNotif = new Notification();
        mockNotif.setId(100L);
        mockNotif.setRead(false);

        when(em.find(Notification.class, 100L)).thenReturn(mockNotif);

        notificationService.markAsRead(100L);

        assertTrue(mockNotif.isRead());
        verify(em, times(1)).merge(mockNotif);
    }
}
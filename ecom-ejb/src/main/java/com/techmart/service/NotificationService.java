package com.techmart.service;

import com.techmart.entity.Notification;
import com.techmart.entity.User;
import jakarta.ejb.Local;
import jakarta.ejb.Remote;

import java.util.List;

@Local
public interface NotificationService {
    List<Notification> getTop5UnreadNotifications(User user);
    void markAsRead(Long notificationId);
    List<Notification> getAllUserNotifications(User user);
    void markAllAsRead(User user);
    List<Notification> getAdminNotifications();
}

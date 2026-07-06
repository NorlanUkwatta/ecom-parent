package com.techmart.impl;

import com.techmart.core.enums.Role;
import com.techmart.core.monitoring.TrackPerformance;
import com.techmart.entity.Notification;
import com.techmart.entity.User;
import com.techmart.service.NotificationService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
@TrackPerformance
public class NotificationServiceImpl implements NotificationService {

    @PersistenceContext(unitName = "TechMartPU")
    private EntityManager em;

    @Override
    public List<Notification> getTop5UnreadNotifications(User user) {
        if (user.getRole() == Role.ADMIN) {
            return em.createQuery("SELECT n FROM Notification n WHERE n.targetRole = :role AND n.isRead = false ORDER BY n.createdAt DESC", Notification.class)
                    .setParameter("role", Role.ADMIN)
                    .setMaxResults(5)
                    .getResultList();
        } else {
            return em.createQuery("SELECT n FROM Notification n WHERE n.targetUser = :user AND n.isRead = false ORDER BY n.createdAt DESC", Notification.class)
                    .setParameter("user", user)
                    .setMaxResults(5)
                    .getResultList();
        }
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification n = em.find(Notification.class, notificationId);
        if (n != null) {
            n.setRead(true);
            em.merge(n);
        }
    }

    @Override
    public List<Notification> getAllUserNotifications(User user) {
        if (user.getRole() == Role.ADMIN) {
            return em.createQuery("SELECT n FROM Notification n WHERE n.targetRole = :role ORDER BY n.createdAt DESC", Notification.class)
                    .setParameter("role", Role.ADMIN)
                    .getResultList();
        } else {
            return em.createQuery("SELECT n FROM Notification n WHERE n.targetUser = :user ORDER BY n.createdAt DESC", Notification.class)
                    .setParameter("user", user)
                    .getResultList();
        }
    }

    @Override
    public void markAllAsRead(User user) {
        if (user.getRole() == Role.ADMIN) {
            em.createQuery("UPDATE Notification n SET n.isRead = true WHERE n.targetRole = :role AND n.isRead = false")
                    .setParameter("role", Role.ADMIN)
                    .executeUpdate();
        } else {
            em.createQuery("UPDATE Notification n SET n.isRead = true WHERE n.targetUser = :user AND n.isRead = false")
                    .setParameter("user", user)
                    .executeUpdate();
        }
    }

    @Override
    public List<Notification> getAdminNotifications() {
        return em.createQuery("SELECT n FROM Notification n WHERE n.targetRole = :role ORDER BY n.createdAt DESC", Notification.class)
                .setParameter("role", Role.ADMIN)
                .getResultList();
    }

}

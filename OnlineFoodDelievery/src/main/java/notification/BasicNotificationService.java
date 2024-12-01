package notification;

public class BasicNotificationService implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.printf("Notification to %s: %s%n", recipient, message);
    }
} 
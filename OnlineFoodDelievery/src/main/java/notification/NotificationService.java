package notification;

public interface NotificationService {
    /**
     * Sends a notification to a specific recipient.
     * 
     * @param recipient the recipient of the notification
     * @param message the notification message
     */
    void sendNotification(String recipient, String message);
} 
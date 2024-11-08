public interface NotificationService {
   void sendOrderConfirmation(Order order);

   void sendDriverAssigned(Order order, Driver driver);

   void sendOrderStatusUpdate(Order order, OrderStatus newStatus);

   void sendDeliveryComplete(Order order);
}
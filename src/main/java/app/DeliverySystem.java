package app;

import model.Driver;
import model.Order;

public class DeliverySystem {
   public void submitOrder(final Order order) {
      System.out.println("Order submitted: " + order);
   }

   public void assignOrderToDriver(final Order order, final Driver driver) {
      System.out.println("Order assigned to driver: " + driver.getName());
   }

   public void completeDelivery(final Long orderId, final Long driverId) {
      System.out.println("Delivery completed for order: " + orderId);
   }
}

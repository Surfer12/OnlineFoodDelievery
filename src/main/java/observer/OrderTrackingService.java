package observer;

import java.util.ArrayList;
import java.util.List;

import model.Order;

public class OrderTrackingService implements OrderSubject {
   private final List<OrderObserver> observers = new ArrayList<>();

   @Override
   public void attach(final OrderObserver observer) {
      this.observers.add(observer);
   }

   @Override
   public void detach(final OrderObserver observer) {
      this.observers.remove(observer);
   }

   @Override
   public void notifyObservers(final Order order) {
      for (final OrderObserver observer : this.observers) {
         observer.update(order, order.getStatus());
      }
   }
}
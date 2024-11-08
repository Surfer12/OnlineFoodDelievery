package observer;

import order.Order;

public interface OrderObserver {
   public void update(Order order);
}

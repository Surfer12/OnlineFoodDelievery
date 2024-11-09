package observer;

import model.Order;

public interface OrderObserver {
   public void update(Order order);
}

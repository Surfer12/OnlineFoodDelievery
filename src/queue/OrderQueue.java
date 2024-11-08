import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OrderQueue implements QueueOperations<Order> {
   private final Queue<Order> orders;
   private final int maxQueueSize;
   private final OrderValidator validator;

   public OrderQueue(int maxQueueSize) {
      this.orders = new LinkedList<>();
      this.maxQueueSize = maxQueueSize;
      this.validator = new OrderValidator();
   }

   @Override
   public void enqueue(Order order) {
      if (orders.size() >= maxQueueSize) {
         throw new QueueFullException("Order queue is at maximum capacity");
      }

      validator.validateOrder(order);
      orders.offer(order);
   }

   @Override
   public Order dequeue() {
      return orders.poll();
   }

   @Override
   public Order peek() {
      return orders.peek();
   }

   @Override
   public boolean isEmpty() {
      return orders.isEmpty();
   }

   @Override
   public int size() {
      return orders.size();
   }

   @Override
   public void clear() {
      orders.clear();
   }

   public List<Order> getPendingOrders() {
      return new ArrayList<>(orders);
   }
}
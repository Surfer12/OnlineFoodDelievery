package queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import model.Order;
import validation.OrderValidator;

public class OrderQueue implements QueueOperations<Order> {
   private final ConcurrentLinkedQueue<Order> orders;
   private final int maxQueueSize;
   private final OrderValidator validator;

   public OrderQueue(int maxQueueSize) {
      this.orders = new ConcurrentLinkedQueue<>();
      this.maxQueueSize = maxQueueSize;
      this.validator = new OrderValidator();
   }

   @Override
   public void enqueue(Order order) {
      try {
         if (orders.size() >= maxQueueSize) {
            throw new exception.QueueFullException("Order queue is at maximum capacity");
         }

         validator.validateOrder(order);
         orders.offer(order);
      } catch (exception.QueueFullException e) {
         System.err.println("Error in enqueue: " + e.getMessage());
         throw e;
      }
   }

   @Override
   public Optional<Order> dequeue() {
      return Optional.ofNullable(orders.poll());
   }

   @Override
   public Optional<Order> peek() {
      return Optional.ofNullable(orders.peek());
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

package rating;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;
import exceptions.QueueFullException;

public class RatingsHandler<T> implements RatingsBusinessLogic<T> {
   private final int maxRatings;
   private final Deque<T> ratingsQueue;

   public RatingsHandler(int maxRatings) {
      if (maxRatings <= 0) {
         throw new IllegalArgumentException("Maximum ratings must be positive");
      }
      this.maxRatings = maxRatings;
      this.ratingsQueue = new LinkedList<>();
   }

   @Override
   public void addRating(T rating) {
      if (rating == null) {
         throw new IllegalArgumentException("Rating cannot be null");
      }
      if (isRatingQueueFull()) {
         throw new QueueFullException("Ratings queue is at maximum capacity: " + maxRatings);
      }
      ratingsQueue.addLast(rating);
   }

   @Override
   public Optional<T> removeOldestRating() {
      return Optional.ofNullable(ratingsQueue.pollFirst());
   }

   @Override
   public Optional<T> getLatestRating() {
      return Optional.ofNullable(ratingsQueue.peekLast());
   }

   @Override
   public void enforceRatingQueueMaxSize() {
      while (ratingsQueue.size() > maxRatings) {
         ratingsQueue.removeFirst();
      }
   }

   @Override
   public void clearAllRatings() {
      ratingsQueue.clear();
   }

   @Override
   public boolean isRatingQueueEmpty() {
      return ratingsQueue.isEmpty();
   }

   @Override
   public boolean isRatingQueueFull() {
      return ratingsQueue.size() >= maxRatings;
   }

   @Override
   public int getCurrentRatingCount() {
      return ratingsQueue.size();
   }

   public double calculateAverageRating() {
      if (ratingsQueue.isEmpty()) {
         return 0.0;
      }
      return ratingsQueue.stream()
            .mapToDouble(rating -> (double) rating)
            .average()
            .orElse(0.0);
   }
}

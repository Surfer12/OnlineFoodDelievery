package rating;

import java.util.Optional;

/**
 * Manages a queue of ratings with size constraints and FIFO behavior.
 * 
 * @param <T> The type of rating being managed
 */
public interface RatingsBusinessLogic<T> {
   /**
    * Adds a new rating to the queue.
    * 
    * @throws QueueFullException if the queue is at maximum capacity
    */
   void addRating(T rating);

   /**
    * Removes and returns the oldest rating in the queue.
    * 
    * @return Optional containing the oldest rating, or empty if queue is empty
    */
   Optional<T> removeOldestRating();

   /**
    * Retrieves the most recently added rating without removing it.
    * 
    * @return Optional containing the newest rating, or empty if queue is empty
    */
   Optional<T> getLatestRating();

   void clearAllRatings();

   boolean isRatingQueueEmpty();

   boolean isRatingQueueFull();

   int getCurrentRatingCount();

   void enforceRatingQueueMaxSize();
}
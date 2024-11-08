package rating;

import java.util.ArrayList;
import java.util.List;

public class RatingsHandler implements RatingsBussinessLogic<Rating> {
   private static final int MAX_RATINGS = 10;
   private List<Rating> ratings;

   public RatingsHandler() {
      this.ratings = new ArrayList<>();
   }

   @Override
   public void add(Rating rating) {
      if (!rating.validate()) {
         throw new IllegalArgumentException("Invalid rating score");
      }

      if (isFull()) {
         remove(); // Remove oldest rating
      }
      ratings.add(rating);
   }

   @Override
   public Rating remove() {
      return ratings.isEmpty() ? null : ratings.remove(0); // Remove oldest rating
   }

   @Override
   public Rating get() {
      return ratings.isEmpty() ? null : ratings.get(0);
   }

   @Override
   public void clear() {
      ratings.clear();
   }

   @Override
   public boolean isEmpty() {
      return ratings.isEmpty();
   }

   @Override
   public boolean isFull() {
      return ratings.size() >= MAX_RATINGS;
   }

   @Override
   public int size() {
      return ratings.size();
   }

   @Override
   public void enforceMaxSize() {
      while (ratings.size() > MAX_RATINGS) {
         ratings.remove(0);
      }
   }

   public double calculateAverageRating() {
      if (ratings.isEmpty()) {
         return 0.0;
      }
      return ratings.stream()
            .mapToInt(Rating::getScore)
            .average()
            .orElse(0.0);
   }

   public List<Rating> getRatings() {
      return new ArrayList<>(ratings);
   }
}

package rating;

import java.time.LocalDateTime;

public class Rating {
   private Long id;
   private Long customerId;
   private Long driverId;
   private int score;
   private String comment;
   private LocalDateTime timestamp;

   public Rating(Long customerId, Long driverId, int score, String comment) {
      this.customerId = customerId;
      this.driverId = driverId;
      this.score = score;
      this.comment = comment;
      this.timestamp = LocalDateTime.now();
   }

   public boolean validate() {
      return score >= 1 && score <= 5;
   }

   public String getRatingDetails() {
      return String.format("Rating: %d/5 - %s", score, comment);
   }

   // Getters
   public Long getId() {
      return id;
   }

   public Long getCustomerId() {
      return customerId;
   }

   public Long getDriverId() {
      return driverId;
   }

   public int getScore() {
      return score;
   }

   public String getComment() {
      return comment;
   }

   public LocalDateTime getTimestamp() {
      return timestamp;
   }
}
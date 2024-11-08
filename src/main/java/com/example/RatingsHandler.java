package main.java.com.example;

import java.util.ArrayList;
import java.util.List;

public class RatingsHandler {
    private List<Rating> ratings;

    public RatingsHandler() {
        this.ratings = new ArrayList<>();
    }

    public void add(Rating rating) {
        if (rating.validate()) {
            ratings.add(rating);
        } else {
            throw new IllegalArgumentException("Invalid rating score");
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

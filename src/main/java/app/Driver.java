package app;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private final String name;
    private final String location;
    private final List<Integer> ratings;

    public Driver(final String name, final String location) {
        this.name = name;
        this.location = location;
        this.ratings = new ArrayList<>();
    }

    public void addRating(final int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        // If ratings list is full (10 ratings), remove the oldest rating
        if (this.ratings.size() >= 10) {
            this.ratings.remove(0);
        }
        this.ratings.add(rating);
    }

    public double getAverageRating() {
        if (this.ratings.isEmpty()) {
            return 0.0;
        }
        return this.ratings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }
} 
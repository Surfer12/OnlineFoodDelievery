package main.java.com.example;

public class Rating extends RatingsHandler<Integer> {
    private Driver driver;
    private int score;
    private String comments;

    public Rating(Driver driver, int score, String comments) {
        this.driver = driver;
        this.score = score;
        this.comments = comments;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getScore() {
        return score;
    }

    public String getComments() {
        return comments;
    }
}
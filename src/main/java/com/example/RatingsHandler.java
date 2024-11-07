package main.java.com.example;

public class RatingsHandler<T> implements RatingsBussinessLogic<T> {
    private RatingsBussinessLogic<T> ratings;

    public RatingsHandler() {
        this.ratings = new CircularBuffer<>();
    }

    @Override
    public void add(T element) {
        ratings.add(element);
    }

    @Override
    public T remove() {
        return ratings.remove();
    }

    @Override
    public T get() {
        return ratings.get();
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
        return ratings.isFull();
    }

    @Override
    public int size() {
        return ratings.size();
    }
}

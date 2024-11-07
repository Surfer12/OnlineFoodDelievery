package main.java.com.example;

import java.util.LinkedList;
import java.util.Queue;

public class RatingsHandler<T> implements RatingsBussinessLogic<T> {
    private Queue<T> ratingsQueue = new LinkedList<>();
    private static final int MAX_SIZE = 10;

    @Override
    public void add(T element) {
        if (ratingsQueue.size() >= MAX_SIZE) {
            ratingsQueue.poll(); // Remove oldest rating
        }
        ratingsQueue.offer(element);
    }

    @Override
    public T remove() {
        return ratingsQueue.poll();
    }

    @Override
    public T get() {
        return ratingsQueue.peek();
    }

    @Override
    public void clear() {
        ratingsQueue.clear();
    }

    @Override
    public boolean isEmpty() {
        return ratingsQueue.isEmpty();
    }

    @Override
    public boolean isFull() {
        return ratingsQueue.size() >= MAX_SIZE;
    }

    @Override
    public int size() {
        return ratingsQueue.size();
    }

    @Override
    public void enforceMaxSize() {
        while (ratingsQueue.size() > MAX_SIZE) {
            ratingsQueue.poll();
        }
    }
}

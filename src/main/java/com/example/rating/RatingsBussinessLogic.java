package main.java.com.example.rating;

public interface RatingsBussinessLogic<T> {
    void add(T element);

    T remove();

    T get();

    void clear();

    boolean isEmpty();

    boolean isFull();

    int size();

    void enforceMaxSize();
}

package main.java.com.example;

public interface CircularBuffer<T> {
    void add(T element);
    T remove();
    T get();
    void clear();
    boolean isEmpty();
    boolean isFull();
    int size();
}

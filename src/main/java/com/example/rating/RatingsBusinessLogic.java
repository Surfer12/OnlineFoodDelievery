package com.example.rating;

public interface RatingsBusinessLogic<T> {
   void add(T element);

   T remove();

   T get();

   void clear();

   boolean isEmpty();

   boolean isFull();

   int size();

   void enforceMaxSize();
}
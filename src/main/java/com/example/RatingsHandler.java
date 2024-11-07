package main.java.com.example;

public class RatingsHandler implements CircularBuffer<Integer>{
    private CircularBuffer<Integer> ratings;

     @Override
     public void add(Integer element) {
         ratings.add(element);
     }
 
     @Override
     public Integer remove() {
         return ratings.remove();
     }
 
     @Override
     public Integer get() {
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

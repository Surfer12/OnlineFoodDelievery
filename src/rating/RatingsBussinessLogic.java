package rating;

import java.util.Optional;

public interface RatingsBussinessLogic<T> {
    void add(T element);

    Optional<T> remove();

    Optional<T> get();

    void clear();

    boolean isEmpty();

    boolean isFull();

    int size();

    void enforceMaxSize();
}

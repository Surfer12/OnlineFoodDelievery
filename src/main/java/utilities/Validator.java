package utilities;

public interface Validator<T> {
    boolean isValid(T value);
}
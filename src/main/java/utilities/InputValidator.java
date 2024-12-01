package utilities;

public interface InputValidator<T> {
    T parse(String input);

    boolean isValid(T value);

    String getTypeName();
}
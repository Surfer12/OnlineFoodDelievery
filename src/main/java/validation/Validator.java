package validation;

public interface Validator<T> {
    T parse(String input);

    boolean validate(T input);

    boolean isValid(T value);

    String getTypeName();
}
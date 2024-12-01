package validation;

public interface Validator<T> {
    boolean validate(T input);
    T parse(String input);
    String getTypeName();
}
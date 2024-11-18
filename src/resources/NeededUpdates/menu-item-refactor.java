// Base Abstract Class
public abstract class MenuItem {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;

    protected MenuItem(Long id, String name, String description, double price, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available = true;
    }

    // Core abstract methods that all menu items must implement
    public abstract double calculateTotal();
    public abstract int getQuantity();
    
    // Common validation logic
    protected void validatePrice(double price) {
        if (price <= 0) {
            throw new ValidationException("Price must be greater than zero");
        }
    }

    // Null-safe getters using Optional
    public Optional<String> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<String> getCategory() {
        return Optional.ofNullable(category);
    }

    // Business logic methods
    public void updatePrice(double newPrice) {
        validatePrice(newPrice);
        this.price = newPrice;
    }

    // Basic getters/setters with validation
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public Long getId() {
        return id;
    }
}

// Concrete Implementation Example
public class Hamburger extends MenuItem {
    private int quantity;
    private boolean hasCheese;
    private boolean hasBacon;

    public Hamburger(Long id, String name, String description, double price, int quantity) {
        super(id, name, description, price, "HAMBURGER");
        this.quantity = quantity;
    }

    @Override
    public double calculateTotal() {
        double basePrice = getPrice() * quantity;
        if (hasCheese) basePrice += 0.50;
        if (hasBacon) basePrice += 1.00;
        return basePrice;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    // Hamburger-specific methods
    public void addCheese() {
        this.hasCheese = true;
    }

    public void addBacon() {
        this.hasBacon = true;
    }
}

// Size Enumeration with Business Logic
public enum Size {
    SMALL(0.8),
    MEDIUM(1.0),
    LARGE(1.2);

    private final double priceMultiplier;

    Size(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}

// Drink Implementation with Size Handling
public class Drink extends MenuItem {
    private Size size;
    private int quantity;

    public Drink(Long id, String name, String description, double basePrice, Size size, int quantity) {
        super(id, name, description, basePrice, "DRINK");
        this.size = size;
        this.quantity = quantity;
    }

    @Override
    public double calculateTotal() {
        return getPrice() * size.getPriceMultiplier() * quantity;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}

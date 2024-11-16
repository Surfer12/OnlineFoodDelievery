# Updated Class Diagram and Task Distribution

## Task Distribution
Overall will be divided into a single task that each individual will complete.

Ensure that each OOP Principle is implemented in the code and apparent throughout the project.

### OOP Principles:
- Polymorphism: 
- Abstraction: 
- Inheritance: 
- Encapsulation: 

The project tasks will be divided into four main areas of focus, each with specific OOP principle responsibilities and design pattern implementations: 

### 1. Order Processing and Workflow Team
**Focus Areas:**
- Polymorphism in Order and MenuItem classes
- Observer Pattern implementation
- Order lifecycle management

**Key Responsibilities:**
- Implement `Order` class with polymorphic behavior
- Develop `OrderTracker` and notification mechanisms
- Manage `OrderStatus` transitions
- Implement `OrderObserver` and `OrderSubject` interfaces

**OOP Principles:**
- Polymorphism: Different order processing for various menu items
- Abstraction: Hide complex order processing details
- Encapsulation: Protect order state and transitions

**Potential Team Member Task:**
Implement polymorphic order processing and notification system, ensuring clean separation of concerns.

### 2. User and Interaction Management Team
**Focus Areas:**
- Inheritance in Person, Customer, and Driver classes
- Builder Pattern for user and order creation
- Rating and feedback system

**Key Responsibilities:**
- Develop `Person` base class
- Implement `Customer` and `Driver` inheritance
- Create `OrderBuilder` with strong validation
- Manage `RatingsHandler` and rating mechanisms

**OOP Principles:**
- Inheritance: Extending base Person class
- Encapsulation: Protecting user data
- Abstraction: Providing clean interfaces for user interactions

**Potential Team Member Task:**
Design and implement user-related classes with robust builder patterns and inheritance structures.

### 3. Menu and Item Management Team
**Focus Areas:**
- Abstract and concrete menu item implementations
- Factory methods for menu item creation
- Price calculation and item management

**Key Responsibilities:**
- Implement `MenuItem` abstract class
- Create concrete classes like `Hamburger`, `Drink`
- Develop menu item factory
- Implement pricing and availability logic

**OOP Principles:**
- Abstraction: Abstract menu item base class
- Polymorphism: Different calculation methods for items
- Encapsulation: Protecting menu item properties

**Potential Team Member Task:**
Create a flexible and extensible menu item system with polymorphic behavior.

### 4. System Infrastructure and Concurrency Team
**Focus Areas:**
- Delivery system architecture
- Concurrent processing
- Driver matching and order queue management

**Key Responsibilities:**
- Implement `DeliverySystem`
- Develop thread-safe collections
- Create driver matching strategies
- Manage order queue and concurrent operations

**OOP Principles:**
- Dependency Inversion: Using interfaces for matching strategies
- Interface-based design
- Encapsulation of concurrent operations

**Potential Team Member Task:**
Design the system's core infrastructure with a focus on thread safety and modular design.

### Collaborative Approach

1. **Shared Design Document:** Maintain the class diagram and design principles document
2. **Regular Code Reviews:** Ensure consistency across implementations
3. **Interface Contracts:** Clearly define interfaces between teams
4. **Continuous Integration:** Use tools to validate interactions between components

### Learning Objectives for Each Team

1. **Order Processing Team:**
   - Advanced event-driven programming
   - Observer pattern
   - State management

2. **User and Interaction Team:**
   - Object-oriented design
   - Builder pattern
   - Inheritance hierarchies

3. **Menu and Item Team:**
   - Abstract class design
   - Polymorphic behavior
   - Factory methods

4. **System Infrastructure Team:**
   - Concurrent programming
   - Design patterns
   - System architecture

This approach allows each team to focus on a specific area while contributing to the overall system, providing a comprehensive learning experience in Java and object-oriented design. 

## Class Diagram

```mermaid
classDiagram
    %% Core Classes
    class Person {
        private Long id
        private String name
        private String address
        private String phone
        private String email
        public Long getId()
        public void setId(Long id)
        public String getName()
        public void setName(String name)
        public String getAddress()
        public void setAddress(String address)
        public String getPhone()
        public void setPhone(String phone)
        public String getEmail()
        public void setEmail(String email)
    }

    class Customer {
        private List~Order~ orderHistory
        public Order placeOrder(List~MenuItem~ items)
        public void rateDriver(Driver driver, int score, String comment)
        public List~Order~ getOrderHistory()
    }

    class Driver {
        private String vehicle
        private String licenseNumber
        private Location currentLocation
        private RatingsHandler ratings
        private Order currentOrder
        private boolean isAvailable
        public void acceptOrder(Order order)
        public void completeDelivery(Order order)
        public Optional~Order~ getCurrentOrder()
        public String getVehicle()
        public void setVehicle(String vehicle)
        public String getLicenseNumber()
        public void setLicenseNumber(String licenseNumber)
        public Location getCurrentLocation()
        public void setCurrentLocation(Location currentLocation)
        public boolean isAvailable()
        public void setAvailable(boolean isAvailable)
    }

    class MenuItem {
        <<abstract>>
        private Long id
        private String name
        private String description
        private double price
        private String category
        private boolean available
        public Long getId()
        public void setId(Long id)
        public String getName()
        public void setName(String name)
        public Optional~String~ getDescription()
        public double getPrice()
        public Optional~String~ getCategory()
        public boolean isAvailable()
        public void setAvailable(boolean available)
        public double calculateTotal()
        public void updatePrice(double newPrice)
        public String getDetails()
        public int getQuantity()
    }

    class Hamburger {
        private int quantity
        public double calculateTotal()
        public int getQuantity()
    }

    class Drink {
        private Size size
        private int quantity
        public double calculateTotal()
        public String getDetails()
        public Size getSize()
        public void setSize(Size size)
        public int getQuantity()
    }

    class Size {
        <<enumeration>>
        SMALL
        MEDIUM
        LARGE
        private double priceMultiplier
        public double getPriceMultiplier()
    }

    %% Order-related Classes
    class Order {
        private Long orderId
        private Long customerId
        private Long driverId
        private List~MenuItem~ items
        private OrderStatus status
        private double totalAmount
        private LocalDateTime orderTime
        private Payment payment
        private Location deliveryLocation
        public double calculateTotal()
        public void updateStatus(OrderStatus newStatus)
        public void processPayment(String paymentMethod)
        public Location getDeliveryLocation()
        public String getCustomerEmail()
        public void setEstimatedDeliveryTime(LocalDateTime time)
    }

    class OrderBuilder {
        private Long customerId
        private List~MenuItem~ items
        private Location deliveryLocation
        private String customerEmail
        public OrderBuilder withValidatedCustomerId(Long customerId)
        public OrderBuilder withCustomerEmail(String email)
        public OrderBuilder addItem(MenuItem item)
        public OrderBuilder withItems(List~MenuItem~ items)
        public OrderBuilder withDeliveryLocation(Location location)
        private void validateOrderRequirements()
        public Order build()
    }

    class OrderStatus {
        <<enumeration>>
        PLACED
        ACCEPTED
        IN_DELIVERY
        DELIVERED
    }

    %% Notification and Tracking
    class OrderSubject {
        <<interface>>
        public void attach(OrderObserver observer)
        public void detach(OrderObserver observer)
        public void notifyObservers(Order order)
    }

    class OrderObserver {
        <<interface>>
        public void update(Order order)
    }

    class CustomerNotifier {
        private NotificationService notificationService
        public void update(Order order)
    }

    class OrderTrackingService {
        private List~OrderObserver~ observers
        public void attach(OrderObserver observer)
        public void detach(OrderObserver observer)
        public void notifyObservers(Order order)
    }

    %% Payment and Ratings
    class Payment {
        private Long paymentId
        private Long orderId
        private String paymentMethod
        private double amount
        private LocalDateTime paymentTime
        private boolean isProcessed
        private boolean isRefunded
        public boolean processPayment()
        public boolean refundPayment()
    }

    class Rating {
        private Long id
        private Long customerId
        private Long driverId
        private int score
        private String comment
        private LocalDateTime timestamp
        public boolean validate()
        public String getRatingDetails()
    }

    class RatingsHandler~T~ {
        private int maxRatings
        private Deque~T~ ratingsQueue
        public void addRating(T rating)
        public double calculateAverageRating()
        public void clearAllRatings()
    }

    %% System Management
    class DeliverySystem {
        private OrderQueue orderQueue
        private Map~Long, Driver~ availableDrivers
        private Map~Long, Driver~ busyDrivers
        private OrderTracker orderTracker
        private DriverMatchingStrategy driverMatcher
        private NotificationService notificationService
        public void registerDriver(Driver driver)
        public void completeDelivery(Long orderId, Long driverId)
    }

    %% Relationships
    Person <|-- Customer
    Person <|-- Driver
    MenuItem <|-- Hamburger
    MenuItem <|-- Drink
    OrderSubject <|.. OrderTrackingService
    OrderObserver <|.. CustomerNotifier
    Order "*" o-- "*" MenuItem
    Customer "1" --> "*" Order
    Driver "1" --> "*" Order
    Driver "1" --> "1" RatingsHandler
    Customer "1" --> "*" Rating
    Driver "1" --> "*" Rating
    Order --> OrderStatus
    Order "1" --> "1" Payment
    OrderBuilder ..> Order : creates
    DeliverySystem --> OrderQueue
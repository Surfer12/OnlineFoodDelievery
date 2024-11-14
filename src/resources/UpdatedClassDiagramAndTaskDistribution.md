Updated flowchart 

Task Distribution: Overall will be divided into a single task that each individual will complete.

Ensure that each OOP Principle is implemented in the code and apparent throughout the project.


Polymorphism : 
Abstraction : 
Inheritance : 
Encapsulation : 




```mermaid
classDiagram
    %% Core Classes
    class Person {
        priv Long id
        priv String name
        priv String address
        priv String phone
        priv String email
        pub getId() Long
        pub getName() String
        pub getAddress() String
        pub getPhone() String
        pub getEmail() String
    }

    class Customer {
        priv List~Order~ orderHistory
        pub placeOrder(List~MenuItem~ items) Order
        pub rateDriver(Driver driver, int score, String comment)
        pub getOrderHistory() List~Order~
    }

    class Driver {
        priv String vehicle
        priv String licenseNumber
        priv Location currentLocation
        priv RatingsHandler ratings
        priv Order currentOrder
        priv boolean isAvailable
        pub acceptOrder(Order order)
        pub completeDelivery(Order order)
        pub getCurrentOrder() Optional~Order~
    }

    class MenuItem {
        <<abstract>>
        priv Long id
        priv String name
        priv String description
        priv double price
        priv String category
        priv boolean available
        pub calculateTotal() double
        pub updatePrice(double newPrice)
        pub isAvailable() boolean
        pub getDetails() String
    }

    class Hamburger {
        priv double basePrice
        pub calculateTotal() double
    }

    class Drink {
        priv Size size
        pub calculateTotal() double
    }

    class Size {
        <<enumeration>>
        SMALL
        MEDIUM
        LARGE
        priv double priceMultiplier
    }

    %% Order-related Classes
    class Order {
        priv Long orderId
        priv Long customerId
        priv Long driverId
        priv List~MenuItem~ items
        priv OrderStatus status
        priv double totalAmount
        priv LocalDateTime orderTime
        priv Payment payment
        priv Location deliveryLocation
        pub calculateTotal() double
        pub updateStatus(OrderStatus newStatus)
        pub processPayment(String paymentMethod)
        pub getDeliveryLocation() Location
        pub getCustomerEmail() String
        pub setEstimatedDeliveryTime(LocalDateTime time)
    }

    class OrderBuilder {
        priv Long customerId
        priv List~MenuItem~ items
        priv Location deliveryLocation
        priv String customerEmail
        pub withValidatedCustomerId(Long customerId) OrderBuilder
        pub withCustomerEmail(String email) OrderBuilder
        pub addItem(MenuItem item) OrderBuilder
        pub withItems(List~MenuItem~ items) OrderBuilder
        pub withDeliveryLocation(Location location) OrderBuilder
        priv validateOrderRequirements()
        pub build() Order
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
        pub attach(OrderObserver observer)
        pub detach(OrderObserver observer)
        pub notifyObservers(Order order)
    }

    class OrderObserver {
        <<interface>>
        pub update(Order order)
    }

    class CustomerNotifier {
        priv NotificationService notificationService
        pub update(Order order)
    }

    class OrderTrackingService {
        priv List~OrderObserver~ observers
        pub attach(OrderObserver observer)
        pub detach(OrderObserver observer)
        pub notifyObservers(Order order)
    }

    %% Payment and Ratings
    class Payment {
        priv Long paymentId
        priv Long orderId
        priv String paymentMethod
        priv double amount
        priv LocalDateTime paymentTime
        priv boolean isProcessed
        priv boolean isRefunded
        pub processPayment() boolean
        pub refundPayment() boolean
    }

    class Rating {
        priv Long id
        priv Long customerId
        priv Long driverId
        priv int score
        priv String comment
        priv LocalDateTime timestamp
        pub validate() boolean
        pub getRatingDetails() String
    }

    class RatingsHandler~T~ {
        priv int maxRatings
        priv Deque~T~ ratingsQueue
        pub addRating(T rating)
        pub calculateAverageRating() double
        pub clearAllRatings()
    }

    %% System Management
    class DeliverySystem {
        priv OrderQueue orderQueue
        priv Map~Long, Driver~ availableDrivers
        priv Map~Long, Driver~ busyDrivers
        priv OrderTracker orderTracker
        priv DriverMatchingStrategy driverMatcher
        priv NotificationService notificationService
        pub registerDriver(Driver driver)
        pub completeDelivery(Long orderId, Long driverId)
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
```
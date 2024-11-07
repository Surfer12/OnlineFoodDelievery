# Online Food Delivery System

This project is an online food delivery system that allows customers to place orders, rate drivers, and manage menu items. It is structured using Java and follows object-oriented principles.

## Project Structure

```
online-food-delivery-system
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           ├── Customer.java
│   │   │           ├── Order.java
│   │   │           ├── Driver.java
│   │   │           ├── MenuItem.java
│   │   │           └── Rating.java
│   │   └── resources
│   └── test
│       └── java
│           └── com
│               └── example
│                   ├── CustomerTest.java
│                   ├── OrderTest.java
│                   ├── DriverTest.java
│                   ├── MenuItemTest.java
│                   └── RatingTest.java
├── pom.xml
└── README.md
```

## Features

- **Customer Management**: Customers can place orders and rate drivers.
- **Order Processing**: Orders are managed with statuses and can contain multiple menu items.
- **Driver Management**: Drivers can accept orders and have ratings stored in a circular buffer.
- **Menu Management**: Menu items can be created and managed, with the ability to extend for specific items.

## Usage

- Create a `Customer` object to place orders and rate drivers.
- Use the `Order` class to manage order details and statuses.
- Implement the `Driver` class to handle order acceptance and rating updates.
- Define `MenuItem` objects for the available food items.

## Testing

Unit tests are provided for each class in the `src/test/java/com/example` directory. Run the tests using:
```
mvn test
```

## Contributing

Contributions are welcome! Please submit a pull request or open an issue for any enhancements or bug fixes.
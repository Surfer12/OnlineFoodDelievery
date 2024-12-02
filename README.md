# Online Food Delivery System

## Project Overview

This is a Java-based Online Food Delivery System that demonstrates key Object-Oriented Programming (OOP) principles.

## OOP Principles Implementation

### 1. Encapsulation

- Each class (`MenuItem`, `Driver`, `Order`, `OrderQueue`) encapsulates its data using private fields
- Getter methods provide controlled access to internal state
- Methods like `addRating()` in `Driver` control how internal data can be modified

### 2. Abstraction

- `ConsoleInputHandler` abstracts input validation logic
- `InputValidationUtils` provides abstract validation methods
- `OrderQueue` abstracts queue management details

### 3. Inheritance

- While this implementation doesn't use inheritance extensively, the `Order.OrderStatus` enum demonstrates a form of type abstraction

### 4. Polymorphism

- Method overriding in `MenuItem.toString()`
- Use of functional interfaces like `Predicate` in `ConsoleInputHandler`

## Data Structures

- `ArrayList` in `Driver` for managing ratings
- `LinkedList` (via `Queue`) in `OrderQueue` for FIFO order processing

## Key Classes

- `MenuItem`: Represents food items with name and price
- `Driver`: Manages driver information and ratings
- `Order`: Represents customer orders with status tracking
- `OrderQueue`: Manages order processing in First-In-First-Out order
- `InputValidationUtils`: Provides input validation methods
- `ConsoleInputHandler`: Generic input handling with validation

## Running the Application

1. Ensure you have Java installed
2. Compile the Java files
3. Run `DeliverySystemCLI`

## Future Improvements

- Implement persistent storage
- Add more comprehensive error handling
- Create a graphical user interface

## Project Requirements Checklist

- [x] Menu Item Management
- [x] Order Placement
- [x] Driver Management
- [x] Rating System
- [x] FIFO Order Processing

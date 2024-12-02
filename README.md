# Online Food Delivery System

## Project Overview

This is a Java-based Online Food Delivery System that demonstrates key Object-Oriented Programming (OOP) principles and provides a comprehensive solution for managing food orders.

## System Architecture

### Core Components

- `MenuItem`: Represents individual food items
- `Order`: Manages order lifecycle and details
- `Driver`: Tracks driver information and ratings
- `OrderQueue`: Implements First-In-First-Out order processing
- `InputValidationUtils`: Provides input validation mechanisms
- `ConsoleInputHandler`: Manages interactive input validation

## OOP Principles Implementation

### 1. Encapsulation

- Private and final fields prevent unauthorized modifications
- Controlled access through getter methods
- Immutable design for critical data

### 2. Abstraction

- Complex logic abstracted into specialized methods
- Enum-based status tracking in `Order`
- Functional interfaces for validation

### 3. Inheritance

- Minimal inheritance, focusing on composition
- Enum-based polymorphic behavior

### 4. Polymorphism

- Method overriding (e.g., `toString()`)
- Functional interfaces for flexible validation

## Data Structures

- `ArrayList`: Dynamic storage for menu items and ratings
- `LinkedList` (via `Queue`): FIFO order processing
- `Stream API`: Efficient data manipulation

## Key Features

- Menu item management
- Order placement and tracking
- Driver assignment
- Rating system
- Input validation
- FIFO order processing

## Technical Highlights

- Java 8+ features
- Immutable design principles
- Functional programming concepts
- Comprehensive input validation
- Extensible architecture

## Running the Application

1. Ensure Java 8+ is installed
2. Compile all Java files
3. Run `DeliverySystemCLI`

## Future Roadmap

- Persistent storage implementation
- Enhanced error handling
- Comprehensive unit testing
- Graphical user interface
- Advanced reporting and analytics

## Contributing

1. Fork the repository
2. Create feature branches
3. Submit pull requests
4. Follow existing code style and conventions

## License

[Specify your license here]

## Contact

[Your contact information]

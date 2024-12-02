# Contributing to Online Food Delivery System

Thank you for considering contributing to the Online Food Delivery System project! We welcome contributions from the community to help improve the project. Please take a moment to review these guidelines before you start contributing.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [How to Contribute](#how-to-contribute)
3. [Development Setup](#development-setup)
4. [Submitting Changes](#submitting-changes)
5. [Style Guide](#style-guide)
6. [Testing](#testing)
7. [Issue Reporting](#issue-reporting)
8. [Pull Request Process](#pull-request-process)
9. [License](#license)

## Code of Conduct

This project adheres to the Contributor Covenant [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior to [email@example.com](mailto:email@example.com).

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue on GitHub with detailed information about the bug, including steps to reproduce, expected behavior, and actual behavior.

### Suggesting Enhancements

If you have an idea for an enhancement, please create an issue on GitHub with a detailed description of the enhancement and why it would be beneficial.

### Contributing Code

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Make your changes.
4. Ensure your changes pass all tests.
5. Submit a pull request.
- **Do not commit `.hprof` files** as they are heap dumps used for debugging purposes.

## Development Setup

### Prerequisites

- Java 17
- Gradle

### Setup Steps

1. **Clone the repository**:
   ```sh
   git clone https://github.com/Surfer12/OnlineFoodDelieveryV.01.git
   cd OnlineFoodDelieveryV.01
   ```

2. **Build the project**:
   ```sh
   ./gradlew build
   ```

3. **Run the application**:
   ```sh
   ./gradlew run
   ```

## Submitting Changes

### Commit Messages

- Use clear and descriptive commit messages.
- Follow the format: `type(scope): description`, e.g., `feat(order): add new order processing logic`.

### Pull Requests

- Ensure your pull request (PR) is based on the latest `main` branch.
- Provide a clear description of the changes in the PR.
- Reference any related issues in the PR description.

## Style Guide

- Follow the existing code style and conventions.
- Use meaningful variable and method names.
- Write comments to explain complex logic.

### Code Formatting

- Ensure consistent code formatting and style across the entire codebase.
- Use a linter or code formatter to maintain code consistency.

## Testing

- Write unit tests for new features and bug fixes.
- Ensure all tests pass before submitting a PR.
- Use JUnit and Mockito for testing.

### Comprehensive Testing

- Implement comprehensive unit tests to cover edge cases and potential issues.
- Ensure higher code quality and reliability through thorough testing.

## Issue Reporting

- Search for existing issues before creating a new one.
- Provide detailed information about the issue, including steps to reproduce, expected behavior, and actual behavior.

## Pull Request Process

1. Ensure your changes pass all tests.
2. Create a pull request with a clear description of the changes.
3. Reference any related issues in the pull request description.
4. Wait for a review and address any feedback.

### Detailed Guidelines

- Follow the detailed guidelines for contributing to the project.
- Include information on the code style, testing, and the pull request process.

## License

By contributing to this project, you agree that your contributions will be licensed under the [MIT License](LICENSE).

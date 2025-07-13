# Restful Booker Platform Test Automation


## Key Components

### 1. Page Objects (`src/main/java/Pages/`)
- Implements Page Object Model (POM)
- Each web page has a corresponding class
- Encapsulates page elements and actions
- Promotes reusability and maintainability

### 2. Step Definitions (`src/test/java/StepDefs/`)
- Implements Cucumber step definitions
- Maps Gherkin steps to Java code
- Handles test logic and assertions

### 3. Feature Files (`src/test/resources/features/`)
- Written in Gherkin syntax
- Describes test scenarios in plain English
- Enables collaboration between technical and non-technical team members

### 4. 1. **ConfigReader file

   `TestContext.java` class serves as a centralized test data container with the following characteristics:
- It uses a static `Properties` object to store test data
- Loads 4 main test data properties during initialization: Firstname, Lastname, Email and Phone
- Provides a getter method to access the properties `getTestData(String key)`
- The configuration is used in the test steps, particularly in : `BookingStepDefs.java` where
- Test data can be referenced using `${propertyName}` syntax
  This configuration setup provides:
- Centralized test data management
- Easy configuration changes without code modification
- Support for both hardcoded and configurable test values
- Clean separation of test data from test logic


## Dependencies
- Java 21
- Maven
- Selenium WebDriver
- Cucumber
- TestNG
- Allure Reports
- AspectJ

## Setup Instructions

### Prerequisites
1. Java JDK 21
2. Maven
3. Allure Command Line Tool
4. Chrome/Firefox Browser
5. IntelliJ Idea CE

### Installation
1. Clone the repository:
   bash git clone [repository-url]


2. Install dependencies:
   bash mvn clean install
### Key Dependencies Being Installed
**Testing Frameworks**:
   - Cucumber (v7.18.0)
   - TestNG (v7.11.0)
   - JUnit (v4.13.2)

**Selenium Related**:
   - selenium-java (v4.32.0)
   - selenium-devtools-v136 (v4.32.0)
**Reporting Tools**:
   - Allure TestNG (v2.24.0)
   - AspectJ Weaver (v1.9.19)
**Utility Libraries**:
   - AssertJ Core (v3.22.0)
   - SLF4J API (v2.0.9)
   - Logback Classic (v1.4.11)


3. Install Allure (Mac OS):
   bash brew install allure
4. Running Tests
   bash mvn clean test
Run specific features using tags:

5.Generate and view Allure reports:
   bash mvn allure:serve


## BDD Benefits
1. **Collaboration**: Business-readable syntax enables better communication between stakeholders
2. **Living Documentation**: Features serve as both specifications and test documentation
3. **Reusability**: Step definitions can be reused across scenarios
4. **Maintainability**: Clear separation of test specification from implementation

## Framework Features

### 1. Parallel Execution
- TestNG parallel execution support
- Configurable thread count

### 2. Reporting
- Detailed Allure reports
- Screenshots on failure
- Step-by-step test execution details
- Test execution metrics

### 3. Configuration Management
- Properties file for environment variables
- Cross-browser testing support
- Configurable test data

### 4. Best Practices
   ### Page Object Model
- Base page class with common methods
- Separate page classes for different sections (BookingPage, HomePage)
- Encapsulated web elements and their operations
- Clear separation between test logic and page implementation

 ### Explicit waits
- Element presence verification
- Clickability checks before interactions
- Page load completion monitoring
- Custom wait conditions for specific business logic

  ### Clean code principles
- The framework uses SOLID principles , DRY(Dont Repeat Yourself) 
and Single Responsibility principle by implementing common methods within the Base class.

  
## Contributing
1. Follow the existing pattern for new features/page objects
2. Maintain test isolation
3. Add appropriate comments
4. Update documentation

## Troubleshooting
- Check chromedriver version compatibility
- Verify all dependencies are resolved
- Ensure correct Java version
- Check system PATH variables

# Performance Testing Guide for Restful Booker Platform

## Overview
This project implements performance testing focused on the Restful Booker booking platform to ensure optimal user experience and system stability.

Page Load Times
- Homepage load: < 3 seconds
- Room listings load: < 2 seconds
- Booking form load: < 1 second
- Images loading: < 4 seconds

API Response Times
- Room availability check: < 1 second
- Booking creation: < 2 seconds
- Form validation: < 500ms

# Run all performance tests
mvn test -Dcucumber.filter.tags="@performance"
# Generate performance report
mvn allure:serve


### Test Plan for Restful Booker Platform

### 1. Test Scope

### In Scope
- Room booking functionality
- Contact form submissions
- User interface elements
- Form validations
- Performance metrics

### Out of Scope
- Backend database testing
- Server infrastructure
- Third-party integrations
- Mobile app testing


### 2.1 Functional Testing
1. **Home Page navigation and verification**
    - Home Page loading
    - Verification of home page headers
    - Visibility of sections like Contact form, Rooms and Book Now options

2. **Room Reservation Flow**
    - Tapping Book now and navigation to Booking screen
    - Room reservation
    - Form submission
    - Booking confirmation/errors

2. **Contact Form**
    - Form field validation
    - Successful submission
    - Error handling


### 2.2 Non-Functional Testing
1. **Performance Testing**
    - Page load times
    - Form submission response

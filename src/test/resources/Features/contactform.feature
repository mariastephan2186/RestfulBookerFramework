# src/test/resources/features/booking_platform.feature
Feature: Restful Booker Platform
  As a user
  I want to book rooms and contact the hotel
  So that I can make reservations and get information

  Background:
    Given I am on the Restful Booker Platform homepage

  Scenario: Submit contact form
    When I fill in the contact form with following details:
      | name          | email            | phone      | subject     | message                            |
      | Marvin dillon | marvin@email.com | 1234567890 | RoomEnquiry | Is breakfast included?dadadadadada |
    And I click the submit button
    Then I should see the message "Thanks for getting in touch"

  Scenario: Validate empty contact form submission
    When I click the submit button without any entry
    Then I should see validation messages for required fields
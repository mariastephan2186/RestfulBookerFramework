Feature: Hotel Homepage
  As a potential hotel guest
  I want to navigate the hotel homepage
  So that I can explore rooms and make bookings

  Background:
    Given I am on the Restful Booker Platform homepage

  @header @navigation
  Scenario: Verify header navigation links are displayed
    Then the following navigation links should be visible:
      | Rooms     |
      | Booking   |
      | Amenities |
      | Location  |
      | Contact   |
      | Admin     |


  @booking @availability
  Scenario: Check room availability with valid dates
    When I go to booking section
    When I enter the following dates:
      | checkin    | checkout   |
      | 2025-07-10 | 2025-07-15 |
    And I click "Check Availability" button
    Then I should see available rooms for the selected dates

  @rooms001
  Scenario: Verify rooms section is displayed
    When I scroll to the rooms section
    Then I should see at least one room card displayed

  @rooms002
  Scenario: Verify room listing components
    When I scroll to the rooms section
    Then each room card should contain "Book Now" button


  @datepicker
  Scenario: Verify date picker functionality
    When I click on the check-in date field
    Then I should see the calendar widget






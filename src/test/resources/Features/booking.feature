Feature: Room Reservation
  As a potential guest
  I want to make room reservations
  So that I can book my stay at the hotel

  Background:
    Given I am on the Restful Booker Platform homepage

  Scenario: Reserve a room
    When I click the book button
    And I fill in the booking form with following details:
      | firstname    | lastname    | email    | phone    |
      | ${FIRSTNAME} | ${LASTNAME} | ${EMAIL} | ${PHONE} |
    And I click the Reserve Now button
    Then I should see the booking confirmation

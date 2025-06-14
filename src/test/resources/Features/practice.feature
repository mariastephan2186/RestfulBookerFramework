# src/test/resources/features/practice.feature
Feature: Practice Page Automation
  As a user
  I want to interact with various web elements
  So that I can practice automation

  Background:
    Given I am on the practice page

  Scenario: Radio Button Selection
    When I select radio button "1"
    Then the radio button "1" should be selected

  Scenario: Suggestion Box
    When I type "Ind" in the suggestion box
    Then I should see suggestions containing "India"

  Scenario: Dropdown Selection
    When I select "Option2" from the dropdown
    Then "Option2" should be selected in the dropdown

  Scenario: Checkbox Operations
    When I select checkbox "1"
    Then checkbox "1" should be selected
    When I deselect checkbox "2"
    Then checkbox "2" should not be selected

  Scenario: Window Handling
    When I click on the Open Window button
    Then I should be able to handle the new window

  Scenario: Alert Handling
    When I enter "John" and click Alert
    Then I should be able to handle the alert


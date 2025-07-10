Feature:  Homepage
  As a potential guest
  I want to navigate the homepage of the booking site quickly
  So that I can explore rooms and make bookings without any issues

  Background:
    Given I am on the Restful Booker Platform homepage

@performance @non-functional
Scenario: Verify page load performance
When I measure the page load time
Then the page should load within 3 seconds
And the Time to First Byte (TTFB) should be less than 800ms
Feature: Get city details

  Scenario: User calls web service to get details by city name
    Given a city exists with the name given
    When a user retrieves the detials of a city by its name
    Then the status code is 200
    And response includes the following
      | City                    | Chennai           |
      | Temperature             | 34 Degree celsius |
      | Humidity                | 63 Percent        |
      | "Weather Description"   | haze              |
      | "Wind Speed"            | 3.6 Km per hour   |
      | "Wind Direction degree" | 280 Degree        |

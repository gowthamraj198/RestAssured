Feature: Get city details

  Scenario: User calls web service to get details by city name
    Given a city exists with the name given
    When a user retrieves the detials of a city by its name
    Then the status code is 200
    And response includes the following
      | City                    | Chennai                                   |
      | Temperature             | 27 Degree celsius                         |
      | Humidity                | 88 Percent                                |
      | "Weather Description"   | thunderstorm with light rain              |
      | "Wind Speed"            | 2.1 Km per hour                           |
      | "Wind Direction degree" | 50 Degree                                 |

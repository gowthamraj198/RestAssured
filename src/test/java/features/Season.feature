Feature: Get circuit details

  Scenario Outline: User calls web service to get circuits by giving a year
    Given a circuit exists for the given year
    When  user hits the webservice with a "<year>"
    Then the status code is 200
    And total number of ids in response "<count>"

    Examples:
      | year                     | count                    |
      | 2016                     | 21                       |
      | 2017                     | 20                       |

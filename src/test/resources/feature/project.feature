Feature: ajouteprojects


  Scenario: Create new project with valid data
    Given the project manager is on the project creation page
    When he enters valid project title "a"
    And sets project description "aa"
    And selects a start date "2024-07-01"
    And selects an estimated end date "2024-12-31"
    And clicks the "Create Project" button
    Then the project should be created successfully

  Scenario: Create new project with invalid data
    Given the project manager is on the project creation page
    When he enters valid project title "a"
    And sets project description "aa"
    And selects a start date "2024-07-xx"
    And selects an estimated end date ""
    And clicks the "Create Project" button
    Then the project should not  be created

  Scenario Outline: Create new project
    Given the project manager is on the project creation page
    When he enters the following details : title "<title>" ,descreption "<descreption>" ,start date "<startDate>" ,estimate date "<estimateDate>"
    Then the response message should be "<response>"
    Examples:
      | title | descreption | startDate  | estimateDate | response |
#      | abc   | eeeee       | 2026-04-20 | 2026-04-30   | success  |
      | aya   | rrrr        | 2026-05-mm | 2026-05-2x   | failure  |
Feature: Sprint Planning

Background:
  Given empty database
    And database script 'sprint-planning.sql'
    And I am on the home page

Scenario: Velocity trend
  When I go on sprints page
  And I go on sprint planning page
  Then the trend values are '95', '102' and '110'

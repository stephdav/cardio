Feature: Sprint Planning

Background:
  Given empty database
    And database script 'sprint-planning.sql'
    And I am on the home page

Scenario: Velocity trend
  When I go on sprint planning page
  Then the trend values are '95', '102' and '110'
  When I go on sprints page
    And I select sprint '2'
    And I change data of day '10' to '90'
    And I validate data
    And I go on sprints page
  Then there is a sprint with dates '18/01/2016' and '29/01/2016', name '2', goal 'sprint 2', commitment '100' and velocity '90' in the list
  When I select sprint '5'
    And I change data of day '10' to '115'
    And I validate data
    And I go on sprints page
  Then there is a sprint with dates '29/02/2016' and '11/03/2016', name '5', goal 'sprint 5', commitment '100' and velocity '115' in the list
  When I go on sprint planning page
  Then the trend values are '93', '102' and '111'
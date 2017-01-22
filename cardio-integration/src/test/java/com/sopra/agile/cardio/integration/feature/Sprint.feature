Feature: Sprint

Background:
  Given empty database
    And database script 'sprint-background.sql'
    And I am on the sprints page
  Then there is a sprint with dates '2016-01-04' and '2016-01-15', name '1', goal 'sprint 1', commitment '25' and velocity '22' in the list
    And there is a sprint with dates '2016-01-18' and '2016-01-29', name '2', goal 'sprint 2', commitment '20' and velocity '21' in the list

Scenario: Update
  When I select sprint 1
  Then the sprint properties are dates '2016-01-04' and '2016-01-15', name '1', goal 'sprint 1' and commitment '25'
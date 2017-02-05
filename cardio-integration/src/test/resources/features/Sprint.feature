Feature: Sprint

Background:
  Given empty database
    And database script 'sprint-background.sql'
    And I am on the sprints page
  Then there is a sprint with dates '2016-01-04' and '2016-01-15', name '1', goal 'sprint 1', commitment '25' and velocity '22' in the list
    And there is a sprint with dates '2016-01-18' and '2016-01-29', name '2', goal 'sprint 2', commitment '20' and velocity '21' in the list

Scenario: Sprint details
  When I select sprint '1'
  Then the sprint properties are dates '2016-01-04' and '2016-01-15', name '1', goal 'sprint 1' and commitment '25'
    And the sprint data are: 0, 1, 5, 8, 9, 9, 14, 16, 21, 22
  When I go on sprints page
    And I select sprint '2'
  Then the sprint properties are dates '2016-01-18' and '2016-01-29', name '2', goal 'sprint 2' and commitment '20'
    And the sprint data are: 0, 0, 1, 4, 6, 8, 10, 14, 16, 21

Scenario: Update sprint properties
  When I select sprint '1'
  Then the sprint properties are dates '2016-01-04' and '2016-01-15', name '1', goal 'sprint 1' and commitment '25'
    And the sprint data are: 0, 1, 5, 8, 9, 9, 14, 16, 21, 22
  When I change commitment to '21'
    And I validate properties
    And I go on sprints page
  Then there is a sprint with dates '2016-01-04' and '2016-01-15', name '1', goal 'sprint 1', commitment '21' and velocity '22' in the list

Scenario: Update sprint data
  When I select sprint '2'
  Then the sprint properties are dates '2016-01-18' and '2016-01-29', name '2', goal 'sprint 2' and commitment '20'
    And the sprint data are: 0, 0, 1, 4, 6, 8, 10, 14, 16, 21
  When I change data of day '10' to '20'
    And I validate data
    And I go on sprints page
  Then there is a sprint with dates '2016-01-18' and '2016-01-29', name '2', goal 'sprint 2', commitment '20' and velocity '20' in the list

Scenario: Sprint update errors
  When I select sprint '1'
  Then the sprint properties are dates '2016-01-04' and '2016-01-15', name '1', goal 'sprint 1' and commitment '25'
    And the sprint data are: 0, 1, 5, 8, 9, 9, 14, 16, 21, 22
    And there is no error
  When I change endDate to '20/01/2016'
    And I validate properties
  Then there is an error 'sprint overlapping'
  When I change endDate to '15/01/2016'
    And I change name to '2'
    And I validate properties
  Then there is an error 'sprint with same name already exists'

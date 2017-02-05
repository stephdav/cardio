Feature: Sprints

Background:
  Given empty database

Scenario: Sprint form errors
  Given I am on the sprints page
    Then there is no error
  When I create a sprint with name '', startdate '' and enddate ''
    Then there is an error 'name is mandatory'
  When I create a sprint with name '1', startdate '' and enddate ''
    Then there is an error 'start date is mandatory'
  When I create a sprint with name '1', startdate '01/01/2015' and enddate ''
    Then there is an error 'end date is mandatory'
  When I create a sprint with name '1', startdate '01/01/2015' and enddate '31/12/2015'
    Then there is no error
    And there are 1 sprints displayed over 1
    And there is a sprint with dates '2015-01-01' and '2015-12-31', name '1', goal '-', commitment '0' and velocity '0' in the list

Scenario: Sprint creation errors
  Given database script 'sprints-afterScen1.sql'
  And I am on the sprints page
    Then there is no error
  When I create a sprint with name '1', startdate '01/01/2016' and enddate '31/01/2016'
    Then there is an error 'sprint with same name already exists'
  When I create a sprint with name '2', startdate '15/12/2015' and enddate '31/01/2016'
    Then there is an error 'sprint overlapping'
  When I create a sprint with name '2', startdate '01/01/2016' and enddate '31/01/2016'
    Then there is no error
    And there are 2 sprints displayed over 2
    And there is a sprint with dates '2016-01-01' and '2016-01-31', name '2', goal '-', commitment '0' and velocity '0' in the list

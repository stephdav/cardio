Feature: Starting a new project

Scenario: Empty dashboard
Given I am on the home page
Then it should have no sprint information

Scenario: Navigation
Given I am on the home page
When I go on sprints page
Then it should have no sprints

Scenario: Sprint creation error
Given I am on the sprints page
Then there is no error
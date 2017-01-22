Feature: Starting a new project

Background:
Given I am on the home page

Scenario: Empty dashboard
Then it should have no sprint information

Scenario: Navigation
When I go on sprints page
Then it should have no sprints

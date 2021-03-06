INSERT INTO USERS(LOGIN,FIRSTNAME,LASTNAME) VALUES ('SDD', 'Stephane', 'David');
INSERT INTO USERS(LOGIN,FIRSTNAME,LASTNAME) VALUES ('TBO', 'Tristan', 'Bouju');
INSERT INTO USERS(LOGIN,FIRSTNAME,LASTNAME) VALUES ('DRO', 'Daniel', 'Roina');
INSERT INTO USERS(LOGIN,FIRSTNAME,LASTNAME) VALUES ('JBA', 'Jennifer', 'Bassard');

INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('1', DATE '2016-09-19', DATE '2016-09-29', 'sprint 1', 30, 30);
INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('2', DATE '2016-10-03', DATE '2016-10-13', 'sprint 2', 35, 35);
INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('3', DATE '2016-10-17', DATE '2016-10-27', 'sprint 3', 45, 45);
INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('4', DATE '2016-10-31', DATE '2016-11-10', 'sprint 4', 40, 40);
INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('5', DATE '2016-11-14', DATE '2016-11-24', 'sprint 5', 50, 47);
INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('6', DATE '2016-11-28', DATE '2016-12-02', 'An operational basis', 45, 45);
INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('7', DATE '2016-12-05', DATE '2016-12-16', 'Sprints', 30, 35);
INSERT INTO SPRINTS(NAME,START_DATE,END_DATE,GOAL,COMMITMENT,VELOCITY) VALUES ('8', DATEADD('DAY', -7, SYSDATE), DATEADD('DAY', 6, SYSDATE), 'Burnup & burndown charts', 21, 13);

INSERT INTO SPRINT_DAYS VALUES (DATE '2016-09-29', 30);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-10-13', 35);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-10-27', 45);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-11-10', 40);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-11-24', 47);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-12-02', 45);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-12-16', 35);

INSERT INTO SPRINT_DAYS VALUES (DATEADD('DAY', -7, SYSDATE), 0);
INSERT INTO SPRINT_DAYS VALUES (DATEADD('DAY', -6, SYSDATE), 1);
INSERT INTO SPRINT_DAYS VALUES (DATEADD('DAY', -5, SYSDATE), 3);
INSERT INTO SPRINT_DAYS VALUES (DATEADD('DAY', -4, SYSDATE), 5);
INSERT INTO SPRINT_DAYS VALUES (DATEADD('DAY', -3, SYSDATE), 8);
INSERT INTO SPRINT_DAYS VALUES (DATEADD('DAY', -2, SYSDATE), 8);
INSERT INTO SPRINT_DAYS VALUES (DATEADD('DAY', -1, SYSDATE), 13);
INSERT INTO SPRINT_DAYS VALUES (SYSDATE, 14);

INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE,ASSIGNED) VALUES ('1st user story', 'READY', CURRENT_TIMESTAMP(), 50, 1, 1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE,ASSIGNED) VALUES ('2nd user story', 'READY', CURRENT_TIMESTAMP(), 200, 2, 2);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE,ASSIGNED) VALUES ('another story to do', 'READY', CURRENT_TIMESTAMP(), 200, 3, 3);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE,ASSIGNED) VALUES ('abc defgh ijklmn op qrstuv w xyz !', 'READY', CURRENT_TIMESTAMP(), 400, 5, 4);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 5', 'DRAFT',    CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 6', 'READY',    CURRENT_TIMESTAMP(), 100, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 7', 'PENDING',  CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 8', 'DONE',     CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 9', 'DRAFT',    CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 10', 'READY',   CURRENT_TIMESTAMP(), 200, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 11', 'PENDING', CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 12', 'DONE',    CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 13', 'DRAFT',   CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 14', 'READY',   CURRENT_TIMESTAMP(), 300, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 15', 'PENDING', CURRENT_TIMESTAMP(), -1, -1);
INSERT INTO STORIES(DESCRIPTION,STATUS,LAST_UPDATE,CONTRIBUTION,ESTIMATE) VALUES ('us 16', 'DONE',    CURRENT_TIMESTAMP(), -1, -1);

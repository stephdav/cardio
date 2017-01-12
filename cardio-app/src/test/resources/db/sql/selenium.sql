INSERT INTO USERS VALUES ('01', 'SDD', 'Stephane', 'David');
INSERT INTO USERS VALUES ('02', 'BBB', 'Babane', '');
INSERT INTO USERS VALUES ('03', 'TBO', 'Tristan', 'Bouju');
INSERT INTO USERS VALUES ('TO_BE_DELETED', 'TO', 'BE', 'DELETED');

INSERT INTO SPRINTS VALUES ('SPR-0', '0', DATE '2015-12-01', DATE '2015-12-31', 'sprint exploratoire', 21, 21);
INSERT INTO SPRINTS VALUES ('SPR-1', '1', DATE '2016-01-01', DATE '2016-01-31', 'sprint1 goal', 13, 11);
INSERT INTO SPRINTS VALUES ('SPR-2', 'current', DATEADD('DAY', -5, SYSDATE), DATEADD('DAY', 7, SYSDATE), 'sprint2 goal', 44, 23);
INSERT INTO SPRINTS VALUES ('TO_BE_DELETED', 'TO_BE_DELETED', DATE '2000-01-01', DATE '2000-12-31', NULL, 100, 0);

INSERT INTO SPRINT_DAYS VALUES (DATE '2015-12-31', 666);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-01-01', 0);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-01-04', 0);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-01-05', 5);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-01-06', 5);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-01-08', 6);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-01-31', 13);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-07-14', 123);
INSERT INTO SPRINT_DAYS VALUES (DATE '2016-08-15', 456);
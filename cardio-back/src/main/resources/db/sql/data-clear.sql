TRUNCATE TABLE SPRINTS;
ALTER SEQUENCE SEQ_SPRINTS RESTART WITH 1;

TRUNCATE TABLE SPRINT_DAYS;

TRUNCATE TABLE STORIES;
ALTER SEQUENCE SEQ_STORIES RESTART WITH 1;

DELETE FROM USERS;
ALTER SEQUENCE SEQ_USERS RESTART WITH 1;
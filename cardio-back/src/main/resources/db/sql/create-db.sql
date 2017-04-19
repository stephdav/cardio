CREATE SEQUENCE IF NOT EXISTS SEQ_USERS START WITH 1;
CREATE TABLE IF NOT EXISTS USERS (
  ID		LONG DEFAULT SEQ_USERS.NEXTVAL PRIMARY KEY,
  LOGIN		VARCHAR2(8),
  FIRSTNAME	VARCHAR2(64),
  LASTNAME	VARCHAR2(64)
);

CREATE SEQUENCE IF NOT EXISTS SEQ_SPRINTS START WITH 1;
CREATE TABLE IF NOT EXISTS SPRINTS (
  ID			LONG DEFAULT SEQ_USERS.NEXTVAL PRIMARY KEY,
  NAME			VARCHAR2(64),
  START_DATE	DATE,
  END_DATE		DATE,
  GOAL			VARCHAR2(256),
  COMMITMENT	INT,
  VELOCITY		INT
);

CREATE TABLE IF NOT EXISTS SPRINT_DAYS (
  DAY	DATE PRIMARY KEY,
  DONE	INT
);

CREATE SEQUENCE IF NOT EXISTS SEQ_STORIES START WITH 1;
CREATE TABLE IF NOT EXISTS STORIES (
  ID			LONG 			DEFAULT SEQ_STORIES.NEXTVAL PRIMARY KEY,
  DESCRIPTION	VARCHAR2(256),
  STATUS		VARCHAR2(32),
  LAST_UPDATE	TIMESTAMP		DEFAULT CURRENT_TIMESTAMP,
  CONTRIBUTION	INT DEFAULT -1,
  ESTIMATE		INT DEFAULT -1,
  ASSIGNED		LONG REFERENCES USERS(ID)
);
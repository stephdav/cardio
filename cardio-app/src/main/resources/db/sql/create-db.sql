CREATE TABLE IF NOT EXISTS USERS (
  ID		VARCHAR2(64) PRIMARY KEY,
  LOGIN		VARCHAR2(8),
  FIRSTNAME	VARCHAR2(64),
  LASTNAME	VARCHAR2(64)
);

CREATE TABLE IF NOT EXISTS SPRINTS (
  ID			VARCHAR2(64) PRIMARY KEY,
  NAME			VARCHAR2(8),
  START_DATE	DATE,
  END_DATE		DATE
);
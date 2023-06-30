create table if not exists EMPLOYEE (
  ID varchar(100) not null,
  NAME varchar(100) not null,
  FIRSTNAME varchar(100) not null,
  BIRTHDATE date,
  POSITION varchar(100),
  SALARY float,
  EXPERIENCE float,
  EMAIL varchar(100) not null,
  PRIMARY KEY ( ID )
);
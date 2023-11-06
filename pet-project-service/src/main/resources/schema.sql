DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS ROLES_PRIVILEGES;
DROP TABLE IF EXISTS USERS_ROLES;
DROP TABLE IF EXISTS PRIVILEGE;
DROP TABLE IF EXISTS ROLE;
DROP TABLE IF EXISTS USER;

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

create table if not exists USER (
    ID int not null auto_increment,
    name varchar(200) not null,
    password varchar(200) not null,
    token varchar(100),
    PRIMARY KEY (ID),
    UNIQUE (name, password, token)
);

create table if not exists ROLE (
    ID int not null,
    name varchar(100) not null,
    primary key(id)
);

create table if not exists PRIVILEGE (
    ID int not null,
    NAME varchar(100) not null,
    PRIMARY KEY (ID)
);

create table if not exists USERS_ROLES (
    ID int not null auto_increment,
    USER_ID int not null,
    ROLE_ID int not null,
    primary key(id),
    FOREIGN KEY (USER_ID) REFERENCES USER(ID),
    FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID),
    UNIQUE (USER_ID, ROLE_ID)
);

create table if not exists ROLES_PRIVILEGES (
   ID int not null auto_increment,
   ROLE_ID int not null,
   PRIVILEGE_ID int not null,
   primary key(id),
   FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID),
   FOREIGN KEY (PRIVILEGE_ID) REFERENCES PRIVILEGE(ID),
   UNIQUE (ROLE_ID, PRIVILEGE_ID)
);
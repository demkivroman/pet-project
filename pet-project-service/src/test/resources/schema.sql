# DROP TABLE IF EXISTS EMPLOYEE;
DROP TABLE IF EXISTS petdb_test.ROLES_PRIVILEGES;
DROP TABLE IF EXISTS petdb_test.ROLE;
DROP TABLE IF EXISTS petdb_test.PRIVILEGE;
DROP TABLE IF EXISTS petdb_test.u_Authority;
DROP TABLE IF EXISTS petdb_test.USER;
DROP TABLE IF EXISTS petdb_test.USER_SEQ;
DROP TABLE IF EXISTS petdb_test.u_Authority_SEQ;

# create table if not exists EMPLOYEE (
#     ID varchar(100) not null,
#     NAME varchar(100) not null,
#     FIRSTNAME varchar(100) not null,
#     BIRTHDATE date,
#     POSITION varchar(100),
#     SALARY float,
#     EXPERIENCE float,
#     EMAIL varchar(100) not null,
#     PRIMARY KEY ( ID )
# );

create table if not exists petdb_test.USER (
    ID int not null auto_increment,
    name varchar(200) not null,
    password varchar(200) not null,
    PRIMARY KEY (ID),
    UNIQUE (name, password)
);

create table if not exists petdb_test.USER_SEQ (
    next_val bigint not null
);

create table if not exists petdb_test.u_Authority (
    id int not null auto_increment,
    name varchar(200) not null,
    user_id int not null,
    primary key(id),
    FOREIGN KEY (user_id) REFERENCES USER(id),
    UNIQUE (id, user_id)
);

create table if not exists petdb_test.u_Authority_SEQ (
    next_val bigint not null
);

create table if not exists petdb_test.ROLE (
    ID int not null,
    name varchar(100) not null,
    primary key(id)
);

create table if not exists petdb_test.PRIVILEGE (
    ID int not null,
    NAME varchar(100) not null,
    PRIMARY KEY (ID)
);

create table if not exists petdb_test.ROLES_PRIVILEGES (
   ID int not null auto_increment,
   ROLE_ID int not null,
   PRIVILEGE_ID int not null,
   primary key(id),
   FOREIGN KEY (ROLE_ID) REFERENCES ROLE(ID),
   FOREIGN KEY (PRIVILEGE_ID) REFERENCES PRIVILEGE(ID),
   UNIQUE (ROLE_ID, PRIVILEGE_ID)
);
INSERT INTO ROLE (id, name) VALUES (1, 'ADMIN');
INSERT INTO ROLE (id, name) VALUES (2, 'USER');
INSERT INTO ROLE (id, name) VALUES (3, 'PET_USER');

INSERT INTO PRIVILEGE (id, name) VALUES (1, 'WRITE');
INSERT INTO PRIVILEGE (id, name) VALUES (2, 'READ');
INSERT INTO PRIVILEGE (id, name) VALUES (3, 'DELETE');

INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 1);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 2);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (1, 3);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (2, 2);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 1);
INSERT INTO ROLES_PRIVILEGES (ROLE_ID, PRIVILEGE_ID) VALUES (3, 2);

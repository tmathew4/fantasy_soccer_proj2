CREATE USER groupProject
IDENTIFIED BY p4ssw0rd
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to groupProject;
GRANT resource to groupProject;
GRANT create session TO groupProject;
GRANT create table TO groupProject;
GRANT create view TO groupProject;

conn groupProject /p4ssw0rd

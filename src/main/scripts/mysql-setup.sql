DROP DATABASE  orderservice;
DROP USER  `orderadmin`@`%`;
DROP USER  `orderuser`@`%`;
CREATE DATABASE  orderservice CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER  `orderadmin`@`%` IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, REFERENCES, INDEX, ALTER, EXECUTE, CREATE VIEW, SHOW VIEW,
    CREATE ROUTINE, ALTER ROUTINE, EVENT, TRIGGER ON `orderservice`.* TO `orderadmin`@`%`;
CREATE USER  `orderuser`@`%` IDENTIFIED BY 'password';
GRANT SELECT, INSERT, UPDATE, DELETE, SHOW VIEW ON `orderservice`.* TO `orderuser`@`%`;
FLUSH PRIVILEGES;
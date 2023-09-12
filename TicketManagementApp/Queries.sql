-- created DataBase
Create Database TicketManagement;
-- Using DataBase TicketManagement
use TicketManagement;
-- Verifying Table
show tables;
-- Creating Tickets Table
create table Tickets
(
id int primary key auto_increment,
client_id int,
ticket_code int  unique,
title varchar(50),
last_modified_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
status varchar(20),
constraint unique_id_client unique(id,client_id)
);
-- Verifying Table
show tables;
-- Added sample datas
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2021, 2021, 'Title_21', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2022, 2022, 'Title_22', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2023, 2023, 'Title_23', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2024, 2024, 'Title_24', 'Done');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2025, 2025, 'Title_25', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2026, 2026, 'Title_26', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2027, 2027, 'Title_27', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2028, 2028, 'Title_28', 'Done');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2029, 2029, 'Title_29', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2030, 2030, 'Title_30', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2031, 2031, 'Title_31', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2032, 2032, 'Title_32', 'Done');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2033, 2033, 'Title_33', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2034, 2034, 'Title_34', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2035, 2035, 'Title_35', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2036, 2036, 'Title_36', 'Done');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2037, 2037, 'Title_37', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2038, 2038, 'Title_38', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2039, 2039, 'Title_39', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2040, 2040, 'Title_40', 'Done');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2041, 2041, 'Title_41', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2042, 2042, 'Title_42', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2043, 2043, 'Title_43', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2044, 2044, 'Title_44', 'Done');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2045, 2045, 'Title_45', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2046, 2046, 'Title_46', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2047, 2047, 'Title_47', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2048, 2048, 'Title_48', 'Done');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2049, 2049, 'Title_49', 'Open');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2050, 2050, 'Title_50', 'Pending');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2051, 2051, 'Title_51', 'InProgress');
INSERT INTO tickets (client_id, ticket_code, title, status) VALUES (2052, 2052, 'Title_52', 'Done');
-- Getting tickets from tickets table
select * from tickets





create table Persons
(
    id         bigint auto_increment
        primary key,
    email      varchar(255) null,
    firstName  varchar(255) null,
    lastName   varchar(255) null,
    address_id bigint       null,
    constraint FK_Persons_address_id
        foreign key (address_id) references Addresses (id)
);

INSERT INTO ca1.Persons (id, email, firstName, lastName, address_id) VALUES (2, 'test@123.dk', 'John', 'Doe', null);
INSERT INTO ca1.Persons (id, email, firstName, lastName, address_id) VALUES (3, 'test@test.dk', 'Johhny', 'Boy', null);
INSERT INTO ca1.Persons (id, email, firstName, lastName, address_id) VALUES (4, 'test@dump.dk', 'Jolie', 'Dutty', null);
INSERT INTO ca1.Persons (id, email, firstName, lastName, address_id) VALUES (5, 'yeti@yosemite.dk', 'Yeti', 'Yosemite', null);
INSERT INTO ca1.Persons (id, email, firstName, lastName, address_id) VALUES (6, 'nol@dol.dk', 'Nol', 'Dol', null);

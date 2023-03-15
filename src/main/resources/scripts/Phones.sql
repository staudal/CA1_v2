create table Phones
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    number      varchar(255) null,
    person_id   bigint       null,
    constraint FK_Phones_person_id
        foreign key (person_id) references Persons (id)
);

INSERT INTO ca1.Phones (id, description, number, person_id) VALUES (2, 'Some comment', '12345678', 2);
INSERT INTO ca1.Phones (id, description, number, person_id) VALUES (3, 'Some comment', '87654321', 3);
INSERT INTO ca1.Phones (id, description, number, person_id) VALUES (4, 'Some comment', '74839251', 4);
INSERT INTO ca1.Phones (id, description, number, person_id) VALUES (5, 'Some comment', '23485203', 5);
INSERT INTO ca1.Phones (id, description, number, person_id) VALUES (6, 'Some comment', '69185029', 6);

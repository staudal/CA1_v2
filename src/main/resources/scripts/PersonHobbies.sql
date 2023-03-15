create table PersonHobbies
(
    person_id bigint not null,
    hobby_id  bigint not null,
    primary key (person_id, hobby_id),
    constraint FK_PersonHobbies_hobby_id
        foreign key (hobby_id) references Hobbies (id),
    constraint FK_PersonHobbies_person_id
        foreign key (person_id) references Persons (id)
);

INSERT INTO ca1.PersonHobbies (person_id, hobby_id) VALUES (2, 5);
INSERT INTO ca1.PersonHobbies (person_id, hobby_id) VALUES (3, 7);
INSERT INTO ca1.PersonHobbies (person_id, hobby_id) VALUES (4, 12);
INSERT INTO ca1.PersonHobbies (person_id, hobby_id) VALUES (5, 15);
INSERT INTO ca1.PersonHobbies (person_id, hobby_id) VALUES (6, 17);

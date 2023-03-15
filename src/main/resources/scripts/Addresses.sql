create table Addresses
(
    id             bigint auto_increment
        primary key,
    additionalInfo varchar(255) null,
    street         varchar(255) null,
    cityInfo_id    bigint       null,
    constraint FK_Addresses_cityInfo_id
        foreign key (cityInfo_id) references CityInfo (id)
);

INSERT INTO ca1.Addresses (id, additionalInfo, street, cityInfo_id) VALUES (2, 'Some comment', 'Teglgårdsvej', 4);
INSERT INTO ca1.Addresses (id, additionalInfo, street, cityInfo_id) VALUES (3, 'Some comment', 'Højvangen', 26);
INSERT INTO ca1.Addresses (id, additionalInfo, street, cityInfo_id) VALUES (4, 'Some comment', 'Duebakkevej', 74);
INSERT INTO ca1.Addresses (id, additionalInfo, street, cityInfo_id) VALUES (5, 'Some comment', 'Nyruphegn', 205);
INSERT INTO ca1.Addresses (id, additionalInfo, street, cityInfo_id) VALUES (6, 'Some comment', 'Polledrakkevej', 666);

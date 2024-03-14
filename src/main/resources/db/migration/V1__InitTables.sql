create table license_plates (
        id bigint primary key generated by default as identity,
        number character varying(255) unique not null
);

create table cars (
        id bigint primary key generated by default as identity,
        vin character varying(255) unique not null,
        color character varying(255),
        model character varying(255),
        year_of_manufacture integer,
        license_plate_id bigint unique,
        foreign key (license_plate_id) references license_plates (id)
         match simple on update no action on delete no action
);
create unique index cars_license_plate_id_idx on cars using btree (license_plate_id);


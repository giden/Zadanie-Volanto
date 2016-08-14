create table user_ (
username varchar(20) NOT NULL,
password varchar(50) NOT NULL,
email varchar(30) NOT NULL,
name varchar(30) NOT NULL,
surname varchar(50) NOT NULL,
locked boolean default false,
strength integer NOT NULL,
role_name varchar(10) NOT NULL,
country_id integer references country(id),
ideology_name varchar(20),
PRIMARY KEY (username)
);
create table country (
id serial,
name varchar(30),
currency_name varchar(5),
parity_rate varchar(5),
president_id varchar(20) references user_(username),
PRIMARY KEY (id)
);

create table auth (
username varchar(20) NOT NULL,
role varchar(10) NOT NULL,
token varchar(32) NOT NULL
);

insert into user_ values ('admin','123','admin@admin','administrator','Serwisu',false,5,'ROLE_ADMIN');
insert into user_ values ('user','123','user@user','uzytkownik','Serwisu',false,10,'ROLE_USER');

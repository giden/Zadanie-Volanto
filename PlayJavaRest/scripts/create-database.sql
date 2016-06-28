create table contact (
id serial,
name varchar(30),
surname varchar(50),
firmname varchar(100),
email varchar(30),
phone varchar(12),
PRIMARY KEY (id)
);
create table user_ (
username varchar(20),
password varchar(50),
email varchar(30),
name varchar(30),
surname varchar(50),
role_name varchar(10)
PRIMARY KEY (username)
);

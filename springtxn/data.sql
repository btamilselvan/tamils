create table student(
	sid int not null auto_increment primary key,
    name varchar(50) not null,
    class varchar(10),
	address varchar(50),
    active varchar(1) ,
    title varchar(50),
    version int
);
create database demo;

create table demo.categoria(
	id int not null AUTO_INCREMENT,
    nombre varchar(30),
    descripcion varchar(30),
    PRIMARY KEY (id) );

INSERT INTO demo.categoria(nombre, descripcion) VALUES ('cat1', 'desc1');

select * from demo.categoria;
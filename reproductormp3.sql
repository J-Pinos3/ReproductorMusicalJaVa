CREATE DATABASE mp3;
USE mp3;

CREATE TABLE IF NOT EXISTS album
(
	id_album int auto_increment primary key,
    nombre_album varchar(50) not null,
    foto_album longblob not null
);

CREATE TABLE IF NOT EXISTS cancion
(
	id_cancion int auto_increment primary key,
    titulo_cancion varchar(70) not null,
    autor_cancion varchar(70) not null,
    genero_cancion varchar(70) not null,
    anio_cancion date not null,
    song longblob not null
);
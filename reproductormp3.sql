CREATE DATABASE mp3;
USE mp3;

CREATE TABLE IF NOT EXISTS album
(
	id_album int auto_increment primary key,
    nombre_album varchar(50) not null,
    foto_album varchar(200) not null
);

CREATE TABLE IF NOT EXISTS cancion
(
	id_cancion int auto_increment primary key,
    titulo_cancion varchar(70) not null,
    autor_cancion varchar(70) not null,
    genero_cancion varchar(70) not null,
    anio_cancion date not null,
    song_file varchar(200) not null,
    
    id_album int,
    foreign key (id_album) references album(id_album) on delete cascade
);

SELECT * FROM cancion;
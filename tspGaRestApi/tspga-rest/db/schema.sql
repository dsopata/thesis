
create database if not exists tspga;
use tspga;

drop table if exists service_points;
drop table if exists clients;
drop table if exists drivers;

CREATE TABLE clients (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(30),
  last_name VARCHAR(30),
  cordinateX DOUBLE,
  cordinateY DOUBLE,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE service_points (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  client_id BIGINT(20) NOT NULL,
  name varchar(32) NOT NULL,
  details varchar(100),
  cordinateX DOUBLE,
  cordinateY DOUBLE,
  PRIMARY KEY (id),
  FOREIGN KEY (client_id) REFERENCES clients (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE drivers (
  id BIGINT(20) NOT NULL AUTO_INCREMENT,
  cordinateX DOUBLE,
  cordinateY DOUBLE,
  PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
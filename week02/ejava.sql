create database if not exists ejava;

use ejava;

create table team (
	team_id char(8) not null primary key,
	name varchar(128) not null
);

create table member (
	matriculation varchar(32) not null primary key,
	name varchar (128) not null,
	email varchar(128) not null,
	enroll_type enum('PT', 'FT'),
	team_id char(8) not null,
	constraint fk_team foreign key (team_id) references team(team_id)
)

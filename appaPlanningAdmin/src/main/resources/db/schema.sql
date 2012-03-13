drop table Absence if exists
drop table DejeunerExterne if exists
drop table Preferences if exists
drop table Projet if exists
drop table SaisieTemps if exists
drop table Site if exists
drop table Utilisateur if exists
create table Absence (id bigint generated by default as identity (start with 1), commentaire varchar(255), dateDebut timestamp not null, dateFin timestamp not null, debutPM bit not null, finAM bit not null, statut integer not null, type integer not null, utilisateur_id bigint, primary key (id))
create table DejeunerExterne (id integer generated by default as identity (start with 1), date timestamp not null, utilisateur_id bigint not null, primary key (id))
create table Preferences (userId bigint not null, datas varbinary(255), primary key (userId))
create table Projet (id bigint generated by default as identity (start with 1), annee integer not null, description varchar(100), nom varchar(30) not null, version integer, primary key (id))
create table SaisieTemps (id integer generated by default as identity (start with 1), commentaire varchar(100), date timestamp not null, heures integer not null, projet_id bigint not null, utilisateur_id bigint not null, primary key (id))
create table Site (id bigint generated by default as identity (start with 1), nom varchar(255) not null, version integer, primary key (id))
create table Utilisateur (id bigint generated by default as identity (start with 1), actif bit, deltaJoursConges integer, deltaJoursRTT integer, email varchar(80) not null, login varchar(30) not null, nbConges integer not null, nbRTT integer not null, nom varchar(30) not null, prenom varchar(30) not null, quatrevingt bit, role integer not null, version integer, primary key (id))
alter table Absence add constraint FK1C341A1D40393A86 foreign key (utilisateur_id) references Utilisateur
alter table DejeunerExterne add constraint FKA0DF95C340393A86 foreign key (utilisateur_id) references Utilisateur
alter table SaisieTemps add constraint FKB3C98C4B184DF70E foreign key (projet_id) references Projet
alter table SaisieTemps add constraint FKB3C98C4B40393A86 foreign key (utilisateur_id) references Utilisateur

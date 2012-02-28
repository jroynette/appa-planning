drop table Absence if exists
drop table Projet if exists
drop table SaisieTemps if exists
drop table Utilisateur if exists
create table Absence (id integer generated by default as identity (start with 1), dateDebut timestamp not null, dateFin timestamp not null, statut integer not null, type integer not null, utilisateur_id integer, primary key (id))
create table Projet (id integer generated by default as identity (start with 1), annee integer not null, description varchar(100), nom varchar(30) not null, primary key (id))
create table SaisieTemps (id integer generated by default as identity (start with 1), commentaire varchar(100), date timestamp not null, heures integer not null, projet_id integer not null, utilisateur_id integer not null, primary key (id))
create table Utilisateur (id integer generated by default as identity (start with 1), actif bit not null, login varchar(20) not null, nbConges integer not null, nbRTT integer not null, nom varchar(30) not null, password varchar(10), prenom varchar(30) not null, quatrevingt bit not null, role varchar(10) not null, primary key (id), unique (login))
alter table Absence add constraint FK1C341A1D40393A86 foreign key (utilisateur_id) references Utilisateur
alter table SaisieTemps add constraint FKB3C98C4B184DF70E foreign key (projet_id) references Projet
alter table SaisieTemps add constraint FKB3C98C4B40393A86 foreign key (utilisateur_id) references Utilisateur

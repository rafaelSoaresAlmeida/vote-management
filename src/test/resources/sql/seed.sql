insert into associate (cpf, name) values ('32025468300', 'Tiburcio');

insert into assembly (id, name, cpf) values (300, 'assembly loco', '32025468300');

insert into voting_session (id,no,opened,yes,cpf) VALUES (36,2,0,0,'32025468300');

insert into assembly (id, name, cpf, voting_session_id) values (99, 'assembly Closed', '32025468300',36);

insert into assembly (id, name, cpf) values (116, 'assembly voting session not created', '32025468300');


insert into voting_session (id, no ,opened, start_date, yes, cpf) VALUES (66,2,1,'2020-08-24 06:41:16.0',0,'32025468300');

insert into assembly (id, creation_date, name, cpf, voting_session_id) VALUES (177,'2020-08-24 03:26:16.0','assembly opened','32025468300',66);

set foreign_key_checks = 0;


insert into poll (poll_id,START_DATE,END_DATE,description) values (1,'2021-05-05','2021-06-06','Rostelecom');
insert into questions (question_id, question,poll_id,answer_type) values (1, 'Вам нравится качество услуг Ростелеком?',1,0);
insert into options (option_id, option_value, question_id) values (1, 'Да', 1);
insert into options (option_id, option_value, question_id) values (2, 'Нет', 1);
insert into options (option_id, option_value, question_id) values (3, 'Не в полной мере', 1);

insert into questions (question_id, question,poll_id,answer_type) values (2, 'Какими из услуг Ростелекома вы пользовались?',1,1);
insert into options (option_id, option_value, question_id) values (4, 'Интернет', 2);
insert into options (option_id, option_value, question_id) values (5, 'Телефон', 2);
insert into options (option_id, option_value, question_id) values (6, 'Никакими', 2);

insert into questions (question_id, question,poll_id,answer_type) values (3, 'Ваш адрес?',1,2);
insert into options (option_id, option_value, question_id) values (7, 'Мой адрес Советский Союз', 3);
insert into options (option_id, option_value, question_id) values (8, 'Этот ответ не должен приниматься', 3);

insert into questions (question_id, question,poll_id,answer_type) values (4, 'Это MULTIPLE опрос?',1,1);
insert into options (option_id, option_value, question_id) values (9, 'MULTIPLE №1', 4);
insert into options (option_id, option_value, question_id) values (10, 'MULTIPLE №2', 4);
insert into options (option_id, option_value, question_id) values (11, 'MULTIPLE № 3', 4);


insert into users (user_id, username, password, first_name, last_name, admin,anonymous) values (1, 'user', '$2a$10$JQOfG5Tqnf97SbGcKsalz.XpDQbXi1APOf2SHPVW27bWNioi9nI8y', 'Mickey', 'Mouse', 'no',false );
insert into users (user_id, username, password, first_name, last_name, admin,anonymous) values (6, 'admin', '$2a$10$JQOfG5Tqnf97SbGcKsalz.XpDQbXi1APOf2SHPVW27bWNioi9nI8y', 'Super', 'Admin', 'yes',false );

insert into roles (id,name) values (1,'ROLE_VISITOR');
insert into roles (id,name) values (2,'ROLE_ADMIN');

-- insert into USERS_ROLES (user_user_id,roles_id) values (1,1);
-- insert into USERS_ROLES (user_user_id,roles_id) values (2,1);
-- insert into USERS_ROLES (user_user_id,roles_id) values (3,1);
-- insert into USERS_ROLES (user_user_id,roles_id) values (4,1);
-- insert into USERS_ROLES (user_user_id,roles_id) values (5,1);
-- insert into USERS_ROLES (user_user_id,roles_id) values (6,2);

-- insert into single_votes (vote_id,option_id,user_id) values (1,1,1);
-- insert into single_votes (vote_id,option_id,user_id) values (2,4,1);
-- insert into single_votes (vote_id,option_id,user_id) values (3,7,1);
-- insert into single_votes (vote_id,option_id,user_id) values (4,1,2);
-- insert into single_votes (vote_id,option_id,user_id) values (5,5,2);
-- insert into single_votes (vote_id,option_id,user_id) values (6,8,2);
-- insert into single_votes (vote_id,option_id,user_id) values (7,2,6);
-- insert into single_votes (vote_id,option_id,user_id) values (8,5,6);
-- insert into single_votes (vote_id,option_id,user_id) values (9,8,6);
--
-- insert into text_answers (answer_id,answer_text,question_id,user_id) values (1,'Вот мой ответ юзера 1',3,1);
-- insert into text_answers (answer_id,answer_text,question_id,user_id) values (2,'Это ответ Юзера 2',3,2);




-- --Single votes
-- select o.*,q.poll_id from
-- (select v.vote_id,v.option_id,o.question_id,o.option_value  from (select * from single_votes where user_id = 1) v inner join options o  on v.option_id = o.option_id) o
-- left join questions q on o.question_id = q.question_id
--
-- --Multiple votes
-- SELECT vote_id,q.question_id,poll_id,option_id,option_value FROM MULTIPLE_VOTE_OPTIONS vo
-- left join MULTIPLE_VOTE v  on MULTIPLE_VOTE_vote_id = v.vote_id
-- left join options o on o.option_id = vo.options_option_id
-- left join questions q on o.question_id = q.question_id
-- where user_id = 6;
-- -- --Text Answers
--
-- SELECT q.question_id,poll_id,t.answer_text FROM TEXT_ANSWERS t
-- left join questions q on t.question_id = q.question_id
-- where user_id = 6




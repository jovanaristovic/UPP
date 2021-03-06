INSERT INTO authority (id, name) VALUES (1, 'ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (3, 'REVIEWER');
INSERT INTO authority (id, name) VALUES (4, 'REDACTOR');
INSERT INTO authority (id, name) VALUES (5, 'MAIN_REDACTOR');
INSERT INTO authority (id, name) VALUES (6, 'REDACTOR_SCIENCE_FIELD');
INSERT INTO authority (id, name) VALUES (7, 'AUTHOR');


INSERT INTO membership (id, date_from, date_to, is_active ) values (1, '2019-01-10 14:45', '2020-01-10 14:45', false);


INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Milica','Matic','user@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'user@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Demo','Demo','admin@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'admin@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Jovana','Ristovic','ristovicj96@gmail.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'ristovicj96@gmail.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username, mempership_id) VALUES (false, 'Milica','Matic','autor@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'autor', 1 );


INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Milica','Micic','urednik@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednik' );

INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Mila','Anic','urednikNaucne1@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne1' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Todor','Todorovic','urednikNaucne2@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne2' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Todor','Todorovic','urednikNaucne3@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne3' );


INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (true, 'Todor','Todorovic','recenzent1@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'recenzent' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (true, 'Todor','Todorovic','recenzent2@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'recenzent2' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (true, 'Todor','Todorovic','recenzent3@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'recenzent3' );

INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false , 'Todor','Todorovic','glavniUrednik1@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'glavniUrednik1' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false , 'Todor','Todorovic','glavniUrednik2@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'glavniUrednik2' );


INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false , 'Todor','Todorovic','urednikNaucne4@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne4' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false , 'Todor','Todorovic','urednikNaucne5@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne5' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false , 'Todor','Todorovic','urednikNaucne6@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne6' );

INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (true, 'Todor','Todorovic','recenzent4@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'recenzent4' );







INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 2);

INSERT INTO user_authority (user_id, authority_id) VALUES (4, 7);

INSERT INTO user_authority (user_id, authority_id) VALUES (5, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 6);
INSERT INTO user_authority (user_id, authority_id) VALUES (7, 6);
INSERT INTO user_authority (user_id, authority_id) VALUES (8, 6);
INSERT INTO user_authority (user_id, authority_id) VALUES (9, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (10, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (11, 3);

INSERT INTO user_authority (user_id, authority_id) VALUES (12, 5);

INSERT INTO user_authority (user_id, authority_id) VALUES (13, 5);

INSERT INTO user_authority (user_id, authority_id) VALUES (14, 6);
INSERT INTO user_authority (user_id, authority_id) VALUES (15, 6);
INSERT INTO user_authority (user_id, authority_id) VALUES (16, 6);




INSERT INTO journal (issn, payment, title, is_active) values ('123ff', 'potrebno placanje', 'Nacionalna_geografija', true);
INSERT INTO journal (issn, payment, title, is_active) values ('123kdk', 'open access', 'Cosmopolitan', true);

INSERT INTO journal_users(journal_id, users_id) values (1,12);
INSERT INTO journal_users(journal_id, users_id) values (2,13);
INSERT INTO journal_users(journal_id, users_id) values (1,14);
INSERT INTO journal_users(journal_id, users_id) values (2,15);

INSERT INTO journal_users(journal_id, users_id) values (1,9);
INSERT INTO journal_users(journal_id, users_id) values (2,10);
INSERT INTO journal_users(journal_id, users_id) values (1,11);
INSERT INTO journal_users(journal_id, users_id) values (2,17);






INSERT INTO scientificfields(id, name) values (1,'geografija');
INSERT INTO scientificfields(id, name) values (2,'fizika');
INSERT INTO scientificfields(id, name) values (3,'hemija');

INSERT INTO users_scientific_fields(users_id, scientific_fields_id) values (14, 1);
INSERT INTO users_scientific_fields(users_id, scientific_fields_id) values (15,2);
INSERT INTO users_scientific_fields(users_id, scientific_fields_id) values (16,3);


INSERT INTO work(apstrakt, key_words, pdf, title, scientific_field_id) values ('df','gf','dfg','fd',1 );












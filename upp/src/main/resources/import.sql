INSERT INTO authority (id, name) VALUES (1, 'ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (3, 'REVIEWER');
INSERT INTO authority (id, name) VALUES (4, 'REDACTOR');
INSERT INTO authority (id, name) VALUES (5, 'MAIN_REDACTOR');
INSERT INTO authority (id, name) VALUES (6, 'REDACTOR_SCIENCE_FIELD');





INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Milica','Matic','user@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'user@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Demo','Demo','admin@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'admin@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Jovana','Ristovic','ristovicj96@gmail.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'ristovicj96@gmail.com' );


INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Milica','Micic','urednik@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednik@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Mila','Anic','urednikNaucne1@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne1@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Todor','Todorovic','urednikNaucne2@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne2@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Todor','Todorovic','urednikNaucne3@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'urednikNaucne3@yahoo.com' );


INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (true, 'Todor','Todorovic','recenzent1@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'recenzent1@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (true, 'Todor','Todorovic','recenzent2@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'recenzent2@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (true, 'Todor','Todorovic','recenzent3@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'recenzent3@yahoo.com' );






INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (3, 2);

INSERT INTO user_authority (user_id, authority_id) VALUES (4, 4);

INSERT INTO user_authority (user_id, authority_id) VALUES (5, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (6, 4);
INSERT INTO user_authority (user_id, authority_id) VALUES (7, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (8, 3);
INSERT INTO user_authority (user_id, authority_id) VALUES (9, 3);









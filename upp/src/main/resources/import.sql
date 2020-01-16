INSERT INTO authority (id, name) VALUES (1, 'ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (3, 'REVIEWER');


INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Milica','Matic','user@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'user@yahoo.com' );
INSERT INTO users (reviewer, name, last_name, email,password, enabled, username) VALUES (false, 'Demo','Demo','admin@yahoo.com','$2a$10$d2bYEem94Do7dck2CP14M.p4u3r2CPb7Di9uyrkxdDF0ibSbU5Bpy',true, 'admin@yahoo.com' );



INSERT INTO user_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 1);





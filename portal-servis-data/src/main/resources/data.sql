SET FOREIGN_KEY_CHECKS=0;


INSERT INTO users (user_id, name, username, password, photo) VALUES (1, "Radovan", "1", "$2a$10$eirKPQctTCFA0HIItG.RZODjTlDURED6MJviOpll.PhQO3kbmwJde", "radovan.jpg");
INSERT INTO users (user_id, name, username, password, photo) VALUES (2, "Milovan", "2", "$2a$10$h/8CL4mxrdwgF4Tx4zxWLeHiIcfkySbUnK6Lt7IRxN7jKXR1wAJ/e", "cartman.jpg");
INSERT INTO users (user_id, name, username, password, photo) VALUES (3, "Zivan", "3", "$2a$10$sd2PJ.QG0Nv8JIZtoZTVUOuU6JdJRiJNdvRoHNu42gtGVqFdOiW4e", "cartman.jpg");

INSERT INTO posts (post_id, post_title, post_desc, post_photo, post_location, post_tags, post_likes, post_dislikes, created_at, created_by) 
VALUES (1, "Xrp the standard", "Hodl till you die", "to_the_moon.jpg" ,  "Novi Sad","tagovi", 12, 2 , 20180808, 1);

INSERT INTO posts (post_id, post_title, post_desc, post_photo,  post_location, post_tags, post_likes, post_dislikes, created_at, created_by) 
VALUES (2, "Liverpool sampion", "Pobeda mocnog Liverpoola u finalu lige sampiona", "liverpool.jpg", "Novi Sad","tagovi", 35, 0, 20180815, 2);

INSERT INTO posts (post_id, post_title, post_desc, post_photo,  post_location, post_tags, post_likes, post_dislikes, created_at, created_by) 
VALUES (3, "Mo Salah is the King", "Bogotac igrice", "mo_salah.jpg", "Novi Sad","tagovi", 32, 7, 20180817, 1 );

INSERT INTO posts (post_id, post_title, post_desc, post_photo,  post_location, post_tags, post_likes, post_dislikes, created_at, created_by) 
VALUES (4, "Jokara GOAT", "Osvojio dobro poznatu GOAT nagradu", "jokic.jpg", "Novi Sad","tagovi", 22, 3, 20180820, 1);


INSERT INTO comments (comment_id, comm_title, comm_desc, comm_likes, comm_dislikes, post_id, created_at, created_by) 
VALUES (1, "Bravo nasi", "Samo sloga Srbina spasava",  3, 4, 1, 20180808, 2);

INSERT INTO comments (comment_id, comm_title, comm_desc, comm_likes, comm_dislikes, post_id, created_at, created_by) 
VALUES (4, "do meseca", "i nazad", 1, 5, 7,  20180808, 2);

INSERT INTO comments (comment_id, comm_title, comm_desc, comm_likes, comm_dislikes, post_id, created_at, created_by) 
VALUES (3, "Jeeaaaaaaa", "Woahhhhhhh", 2, 6, 2, 20180808, 2);

INSERT INTO comments (comment_id, comm_title, comm_desc,  comm_likes, comm_dislikes, post_id, created_at, created_by) 
VALUES (5, "Najjaci smo", "Najjaci", 2, 12, 5, 20180808, 2);

INSERT INTO comments (comment_id, comm_title, comm_desc, comm_likes, comm_dislikes, post_id, created_at, created_by) 
VALUES (2, "Uaaa", "lolololo", 1, 3, 4, 20180808, 2);

INSERT INTO comments (comment_id, comm_title, comm_desc, comm_likes, comm_dislikes, post_id, created_at, created_by) 
VALUES (6, "Jes vala goat", "svaka mu cast", 3, 3, 4, 20180808, 2);


INSERT INTO role (role_id, name) VALUES (1, "ROLE_ADMINISTRATOR");
INSERT INTO role (role_id, name) VALUES (2, "ROLE_OBJAVLJIVAC");
INSERT INTO role (role_id, name) VALUES (3, "ROLE_KOMENTATOR");

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
INSERT INTO user_role (user_id, role_id) VALUES (3, 3);

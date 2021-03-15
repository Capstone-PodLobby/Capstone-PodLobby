
use podLobby;



# drop table if exists responses;
# drop table if exists comments;
# drop table if exists podcast_categories;
# drop table if exists categories;
# drop table if exists podcasts;
# drop table if exists requests;
# drop table if exists followed_users;
# drop table if exists users;


insert into users (about_me, email, is_admin, joined_at, password, profile_image, username, background_image) VALUES ('here is an about me, my name is matt', 'matt@podLobby.com', 1, '2020-01-10', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC', 'https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80', 'matt', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1');
insert into users (about_me, email, is_admin, joined_at, password, profile_image, username, background_image) VALUES ('here is an about me, my name is amber', 'amber@podLobby.com', 1, '2020-01-10', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC', 'https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80', 'amber', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1');
insert into users (about_me, email, is_admin, joined_at, password, profile_image, username, background_image) VALUES ('here is an about me, my name is gonzalo', 'gonzalo@podLobby.com', 1, '2020-01-10', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC', 'https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80', 'gonzalo', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1');
insert into users (about_me, email, is_admin, joined_at, password, profile_image, username, background_image) VALUES ('here is an about me, my name is caleb', 'caleb@podLobby.com', 1, '2020-01-10', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC', 'https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80', 'caleb', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1');
insert into users (about_me, email, is_admin, joined_at, password, profile_image, username, background_image) VALUES ('I am a test user', 'test@t.est', 0, '2020-01-01', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC', 'https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'testUser', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1');


insert into podcasts (created_at, description, embed_link, image, title, user_id) VALUES ('2020-01-11', 'We will talk about working on side projects and how to come up with ideas', 'https://anchor.fm/matthew-baker3/embed/episodes/Creating-a-side-project-es8rqk', 'https://q3p9g6n2.rocketcdn.me/wp-content/ml-loads/2017/02/microphone-podcast-radio-ss-1920.jpg', 'Creating a side Project', 1);
insert into podcasts (created_at, description, embed_link, image, title, user_id) VALUES ('2020-01-11', 'Welcome to the launch of the site Pod lobby', 'https://anchor.fm/matthew-baker3/embed/episodes/welcome-to-pod-lobby-esg3lf', 'https://q3p9g6n2.rocketcdn.me/wp-content/ml-loads/2017/02/microphone-podcast-radio-ss-1920.jpg', 'Welcome to the site', 1);

insert into categories (id, name) VALUES (1, 'tech');
insert into categories (id, name) VALUES (2, 'comedy');
insert into categories (id, name) VALUES (3, 'horror');
insert into categories (id, name) VALUES (4, 'news');
insert into categories (id, name) VALUES (5, 'finance');
insert into categories (id, name) VALUES (6, 'diy');

insert into podcast_categories (podcast_id, category_id) VALUES (1, 1);
insert into podcast_categories (podcast_id, category_id) VALUES (1, 6);
insert into podcast_categories (podcast_id, category_id) VALUES (2, 4);
insert into podcast_categories (podcast_id, category_id) VALUES (2, 6);

insert into requests (created_at, description, guest_count, is_active, title, user_id) VALUES ('2020-02-09', 'I would like to make an episode about life and I am looking for any kind of advice you may be able to offer', 2, 1, 'Life today', 1);

insert into comments (comment, created_at, podcast_id, user_id) VALUES ('I like the episode about side projects', '2020-02-11', 1, 2);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('Nice Podcast Matt!', '2020-02-11', 1, 3);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea!', '2020-02-11', 1, 4);

insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea! second', '2020-02-11', 2, 1);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('I really enjoyed the podcast!', '2020-02-11', 2, 4);


insert into responses (content, created_at, request_id, user_id) VALUES ('I like the idea and feel like I could offer some advice', '2020-02-09', 1, 2);

insert into followed_users (user_id, follow_id) VALUES (1, 2);

select * from users where id in (
    select follow_id from followed_users where user_id = 1

    );

# Added test users to database to verify that
insert into users (about_me, background_image, email, is_admin, joined_At, password, profile_image, username)
VALUES ('Here is some random info about me', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'abby@email.com', 0, '2021-01-01', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'abby'),
       ('Here is some random info about me', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'brandon@email.com', 0, '2021-01-01', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'brandon'),
       ('Here is some random info about me', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'chis@email.com', 0, '2021-01-03', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'chris'),
       ('Here is some random info about me', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'drew@email.com', 0, '2021-02-01', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'drew'),
       ('Here is some random info about me', 'https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'emily@email.com', 0, '2021-02-01', '$2a$10$wygL0OSDnArYf9YRA3UWZ.9JLAij1DK1aJDaFMk9S7gemTbwh5BIC','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'emily');

select * from users;

insert into podcasts (created_at, description, embed_link, image, title, user_id )
VALUES ('2021-01-01', 'this is a test podcast 1', 'https://anchor.fm/amber799/embed','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test1', 1),
       ('2021-01-01', 'this is a test podcast 2', 'https://anchor.fm/amber799/embed','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test2', 2),
       ('2021-01-01', 'this is a test podcast 3', 'https://open.spotify.com/embed-podcast/show/3TFuu4Eb7jGgR8cwGrQifa','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test3', 3),
       ('2021-01-01', 'this is a test podcast 4', 'https://anchor.fm/amber799/embed','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test4', 4),
       ('2021-01-01', 'this is a test podcast 5', 'https://open.spotify.com/embed-podcast/show/3TFuu4Eb7jGgR8cwGrQifa','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test5', 5),
       ('2021-01-01', 'this is a test podcast 6', 'https://anchor.fm/amber799/embed','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test6', 1),
       ('2021-01-01', 'this is a test podcast 7', 'https://anchor.fm/amber799/embed','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test7', 2);


insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea! third', '2020-02-11', 3, 1);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea! fourth', '2020-02-11', 4, 1);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea! fifth', '2020-02-11', 5, 1);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea! sixth', '2020-02-11', 6, 1);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea! seventh', '2020-02-11', 7, 1);
insert into comments (comment, created_at, podcast_id, user_id) VALUES ('That is a good idea! eighth', '2020-02-11', 8, 1);

select * from categories;

select * from podcasts;

select name from categories where id in (
    select category_id from podcast_categories where podcast_id = 2
    );

select *
from requests;

insert into podcast_categories (podcast_id, category_id) VALUES (1,1);
insert into podcast_categories (podcast_id, category_id) VALUES (2,2);
insert into podcast_categories (podcast_id, category_id) VALUES (3,3);
insert into podcast_categories (podcast_id, category_id) VALUES (4,4);
insert into podcast_categories (podcast_id, category_id) VALUES (5,5);
insert into podcast_categories (podcast_id, category_id) VALUES (6,6);
insert into podcast_categories (podcast_id, category_id) VALUES (7,1);


select * from podcasts where id in (
    select category_id from podcast_categories where category_id =4
    );

select * from comments;
# delete from comments where id = 8;
select * from followed_users where user_id = 1;
create database if not exists podLobby_db;

use podLobby_db;

# drop table if exists responses;
# drop table if exists comments;
# drop table if exists podcast_categories;
# drop table if exists categories;
# drop table if exists podcasts;
# drop table if exists requests;
# drop table if exists followed_users;
# drop table if exists users;

insert into users (about_me, email, is_admin, joined_at, password, profile_image, username) VALUES ('I enjoy coding and working on cars, hoping to find podcasts about technology', 'matt@podLobby.com', 1, '2020-01-10', 'test', 'https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80', 'test');

insert into users (about_me, email, is_admin, joined_at, password, profile_image, username) VALUES ('I enjoy coding and working on cars, hoping to find podcasts about technology', 'matt@podLobby.com', 1, '2020-01-10', 'password123', 'https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80', 'mdbaker19');

insert into users (about_me, email, is_admin, joined_at, password, profile_image, username) VALUES ('I am a test user', 'test@t.est', 0, '2020-01-01', 'tester', 'https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'testUser');


insert into podcasts (created_at, description, embed_link, image, title, user_id) VALUES ('2020-01-11', 'We will talk about working on side projects and how to come up with ideas', '<iframe src="https://anchor.fm/matthew-baker3/embed/episodes/Creating-a-side-project-es8rqk" height="102px" width="400px" frameborder="0" scrolling="no"></iframe>', 'https://q3p9g6n2.rocketcdn.me/wp-content/ml-loads/2017/02/microphone-podcast-radio-ss-1920.jpg', 'Creating a side Project', 1);

insert into categories (id, name) VALUES (1, 'tech');
insert into categories (id, name) VALUES (2, 'comedy');
insert into categories (id, name) VALUES (3, 'horror');
insert into categories (id, name) VALUES (4, 'news');
insert into categories (id, name) VALUES (5, 'finance');
insert into categories (id, name) VALUES (6, 'diy');

insert into podcast_categories (podcast_id, category_id) VALUES (1, 1);

insert into requests (created_at, description, guest_count, is_active, title, user_id) VALUES ('2020-02-09', 'I would like to make an episode about life and I am looking for any kind of advice you may be able to offer', 2, 1, 'Life today', 1);

insert into comments (comment, created_at, podcast_id, user_id) VALUES ('I like the episode about side projects', '2020-02-11', 1, 2);

insert into responses (content, created_at, request_id, user_id) VALUES ('I like the idea and feel like I could offer some advice', '2020-02-09', 1, 2);

insert into followed_users (user_id, follow_id) VALUES (1, 2);

select * from users where id in (
    select follow_id from followed_users where user_id = 1

    );

# Added test users to database to verify that
insert into users (id, about_me, email, is_admin, joined_At, password, profile_image, username)
VALUES (1, 'Here is some random info about me', 'abby@email.com', 0, '2021-01-01', 'password','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'abby'),
       (2, 'Here is some random info about me', 'brandon@email.com', 0, '2021-01-01', 'password','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'brandon'),
       (3, 'Here is some random info about me', 'chis@email.com', 0, '2021-01-03', 'password','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'chris'),
       (4, 'Here is some random info about me', 'drew@email.com', 0, '2021-02-01', 'password','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'drew'),
       (5, 'Here is some random info about me', 'emily@email.com', 0, '2021-02-01', 'password','https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'emily');

select * from users;

insert into podcasts (id, created_at, description, embed_link, image, title, user_id )
VALUES (1, '2021-01-01', 'this is a test podcast', '<iframe src="https://anchor.fm/amber799/embed" height="102px" width="400px" frameborder="0" scrolling="no"></iframe>','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test', 1),
       (2, '2021-01-01', 'this is a test podcast', '<iframe src="https://anchor.fm/amber799/embed" height="102px" width="400px" frameborder="0" scrolling="no"></iframe>','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test', 2),
       (3, '2021-01-01', 'this is a test podcast', '<iframe src="https://open.spotify.com/embed-podcast/show/3TFuu4Eb7jGgR8cwGrQifa" width="100%" height="232" frameborder="0" allowtransparency="true" allow="encrypted-media"></iframe>','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test', 3),
       (4, '2021-01-01', 'this is a test podcast', '<iframe src="https://anchor.fm/amber799/embed" height="102px" width="400px" frameborder="0" scrolling="no"></iframe>','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test', 4),
       (5, '2021-01-01', 'this is a test podcast', '<iframe src="https://open.spotify.com/embed-podcast/show/3TFuu4Eb7jGgR8cwGrQifa" width="100%" height="232" frameborder="0" allowtransparency="true" allow="encrypted-media"></iframe>','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test', 5),
       (6, '2021-01-01', 'this is a test podcast', '<iframe src="https://anchor.fm/amber799/embed" height="102px" width="400px" frameborder="0" scrolling="no"></iframe>','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test', 1),
       (7, '2021-01-01', 'this is a test podcast', '<iframe src="https://anchor.fm/amber799/embed" height="102px" width="400px" frameborder="0" scrolling="no"></iframe>','https://images.unsplash.com/photo-1567596388756-f6d710c8fc07?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80,1', 'test', 2);






select * from podcasts;

delete from podcasts where id = 7;




    

select * from categories;

select * from podcasts;

select name from categories where id in (
    select category_id from podcast_categories where podcast_id = 2
    );

select *
from requests;




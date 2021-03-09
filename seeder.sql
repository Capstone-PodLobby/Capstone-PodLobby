create database if not exists podLobby;

use podLobby;
drop table if exists user_podcast;
drop table if exists comments;
drop table if exists podcast_categories;
drop table if exists categories;
drop table if exists podcasts;
drop table if exists user_requested;
drop table if exists requested;
drop table if exists followed_user;
drop table if exists user;

create table if not exists user(
    id int unsigned auto_increment primary key,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255) not null,
    joined_at date not null,
    profile_image text,
    is_admin tinyint not null default 0,
    about_me text
);

create table if not exists podcasts(
    id int unsigned auto_increment primary key,
    user_id int unsigned not null,
    title varchar(255) not null,
    description varchar(255) not null,
    embed_link text not null,
    image text,
    created_at date not null,
    listen_count int default 0,
    foreign key (user_id) references user(id)
);

create table if not exists user_podcast(
    user_id int unsigned not null,
    podcast_id int unsigned not null,
    foreign key (user_id) references user(id),
    foreign key (podcast_id) references podcasts(id)
);

create table if not exists categories(
    id int unsigned auto_increment not null primary key,
    name varchar(255) not null
);

create table if not exists podcast_categories(
    category_id int unsigned not null,
    podcast_id int unsigned not null,
    foreign key (category_id) references categories(id),
    foreign key (podcast_id) references podcasts(id)
);

create table if not exists comments(
    user_id int unsigned not null,
    podcast_id int unsigned not null,
    comment varchar(255) not null,
    foreign key (user_id) references user(id),
    foreign key (podcast_id) references podcasts(id)
);

create table if not exists followed_user(
    user_id int unsigned not null,
    follow_id int unsigned not null,
    foreign key (user_id) references user(id)
);

create table if not exists requested(
    id int unsigned auto_increment primary key,
    user_id int unsigned not null,
    title varchar(255) not null,
    description varchar(255) not null,
    media_links text,
    created_at date not null,
    guest_count int not null,
    foreign key (user_id) references user(id)
);

create table if not exists user_requested(
    user_id int unsigned not null,
    request_id int unsigned not null,
    foreign key (user_id) references user(id),
    foreign key (request_id) references requested(id)
);

use podLobby;
insert into user (username, password, email, joined_at, profile_image, is_admin, about_me) values ('matt', 'matts', 'matt@m.a', '1999-01-01', 'imageURL', 1, 'working on the site');
insert into user (username, password, email, joined_at, profile_image, about_me) values ('nemo', 'husky', 'dogs@d.d', '2009-01-01', 'imageURL', 'i can do tricks, i am a dog');
insert into user (username, password, email, joined_at, profile_image, about_me) values ('mandy', 'mandys', 'm@a.ndy', '2002-01-01', 'imageURL', 'I can talk about design');

insert into podcasts (user_id, title, description, embed_link, image, created_at) VALUES (1, 'first', 'the first comedy', 'iframe1', 'podcastImage1', '1999-01-01');

insert into categories (name) VALUES ('comedy');
insert into categories (name) VALUES ('how to');

insert into podcast_categories (category_id, podcast_id) VALUES (1, 1);
insert into podcast_categories (category_id, podcast_id) VALUES (2, 1);

insert into requested (user_id, title, description, media_links, created_at, guest_count) VALUES (1, 'want to make this with 2 people', 'it will be about comedy', 'google.com/news', '1999-09-09', 2);
# ^^^ user 1 created a requested podcast post for others to see ^^^

insert into user_requested (user_id, request_id) VALUES (2, 1); # user 2 wants to work on requested post # 1
insert into user_requested (user_id, request_id) VALUES (3, 1); # user 3 wants to work on requested post # 1 as well

# user 2 comments on podcast id 1 this message
insert into comments (user_id, podcast_id, comment) VALUES (2, 1, 'i like the podcast! it was very funny');

# a user with id 2 clicks on the follow button to follow user 1
insert into followed_user (user_id, follow_id) VALUES (2, 1);

# a user with id 3 clicks on the follow button to follow user 1
insert into followed_user (user_id, follow_id) VALUES (3, 1);

#  if a user makes a podcast and wants to view it
select *
from podcasts
where user_id = 1;

# if a user wants to view the categories of their podcast posts
select name
from categories where categories.id IN (
    select category_id from podcast_categories where podcast_id in (
        select id
        from podcasts
        where user_id = 1
        )
    );

# if a user wants to see what users have commented on their podcast posts
select username from user where id in (
    select user_id from comments where podcast_id in (
        select id from podcasts where user_id = 1
        )
    );

# if a user wants to view their followers
select username as matts_followers
from user where id in (
    select user_id from followed_user where follow_id = 1
    );

# if a user wants to view all the users who want to work with them on their specific requested post
select username as people_requesting_to_collab
from user where id in (
    select user_id from  user_requested where request_id = 1
    );

# a new user sees a requested post and would like to work with the poster
insert into user (username, password, email, joined_at, profile_image, is_admin, about_me) values ('newUser', 'password', 'user@m.a', '1993-01-01', 'imageURL', 0, 'looking to discover new podcasts!');
# would like to work with them is 'clicked' for podcast named ?
insert into user_requested (user_id, request_id) VALUES (4, 1);


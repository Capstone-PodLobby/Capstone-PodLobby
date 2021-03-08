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
    id int unsigned not null primary key,
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
    guest_count int default 0,
    foreign key (user_id) references user(id)
);

create table if not exists user_requested(
    user_id int unsigned not null,
    request_id int unsigned not null,
    foreign key (user_id) references user(id),
    foreign key (request_id) references requested(id)
);

# create database if not exists podLobby;
#
# use podLobby;
# drop table if exists responses;
# drop table if exists comments;
# drop table if exists podcast_categories;
# drop table if exists categories;
# drop table if exists podcasts;
# drop table if exists requests;
# drop table if exists followed_users;
# drop table if exists users;
#
# insert into users (about_me, email, is_admin, joined_at, password, profile_image, username) VALUES ('I enjoy coding and working on cars, hoping to find podcasts about technology', 'matt@podLobby.com', 1, '2020-01-10', 'password123', 'https://images.unsplash.com/photo-1511367461989-f85a21fda167?ixid=MXwxMjA3fDB8MHxzZWFyY2h8Mnx8cHJvZmlsZXxlbnwwfHwwfA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80', 'mdbaker19');
#
# insert into users (about_me, email, is_admin, joined_at, password, profile_image, username) VALUES ('I am a test user', 'test@t.est', 0, '2020-01-01', 'tester', 'https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png', 'testUser');
#
#
# insert into podcasts (created_at, description, embed_link, image, title, user_id) VALUES ('2020-01-11', 'We will talk about working on side projects and how to come up with ideas', '<iframe src="https://anchor.fm/matthew-baker3/embed/episodes/Creating-a-side-project-es8rqk" height="102px" width="400px" frameborder="0" scrolling="no"></iframe>', 'https://q3p9g6n2.rocketcdn.me/wp-content/ml-loads/2017/02/microphone-podcast-radio-ss-1920.jpg', 'Creating a side Project', 1);
#
# insert into categories (id, name) VALUES (1, 'tech');
#
# insert into podcast_categories (podcast_id, category_id) VALUES (1, 1);
#
# insert into requests (created_at, description, guest_count, is_active, title, user_id) VALUES ('2020-02-09', 'I would like to make an episode about life and I am looking for any kind of advice you may be able to offer', 2, 1, 'Life today', 1);
#
# insert into comments (comment, created_at, podcast_id, user_id) VALUES ('I like the episode about side projects', '2020-02-11', 1, 2);
#
# insert into responses (content, created_at, request_id, user_id) VALUES ('I like the idea and feel like I could offer some advice', '2020-02-09', 1, 2);
#
# insert into followed_users (user_id, follow_id) VALUES (1, 2);
#
# select * from users where id in (
#     select follow_id from followed_users where user_id = 1
#     );


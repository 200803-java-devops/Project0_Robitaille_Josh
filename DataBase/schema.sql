create table users(
    index int primary key,
    username text,
    dialog text
);

insert into users values(1, 'userTest', 'Hello chat');
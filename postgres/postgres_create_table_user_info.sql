CREATE DATABASE vk
    WITH
    OWNER = vk_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

GRANT TEMPORARY, CONNECT ON DATABASE vk TO PUBLIC;

create user vk_user with encrypted password 'vk_user';
GRANT ALL ON DATABASE vk TO vk_user;

create table user_info (
    user_id integer NOT NULL UNIQUE,
    user_f_name  char[25],
    user_l_name  char[25],
    user_b_date  date,
    user_city  char[25],
    user_contacts  char[15],
    primary key(user_id)
);

ALTER TABLE IF EXISTS public.user_info
    OWNER to vk_user;

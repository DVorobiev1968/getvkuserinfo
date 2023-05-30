CREATE ROLE vk_user WITH
    LOGIN
    NOSUPERUSER
    NOCREATEDB
    NOCREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'vk_user';
COMMENT ON ROLE vk_user IS 'Testing vk_user for Application GetVKUserInfo';
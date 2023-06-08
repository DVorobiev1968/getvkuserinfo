drop table if exists user_info;
create table user_info (
                           user_id SERIAL PRIMARY KEY,
                           user_first_name  VARCHAR(25),
                           user_last_name  VARCHAR(25),
                           user_b_date  TIMESTAMP,
                           user_city  VARCHAR(25),
                           user_contacts  VARCHAR(25)
);
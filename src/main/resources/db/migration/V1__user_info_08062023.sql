create table user_info (
                           user_id SERIAL PRIMARY KEY,
                           user_f_name  VARCHAR(25),
                           user_l_name  VARCHAR(25),
                           user_b_date  TIMESTAMP,
                           user_city  VARCHAR(25),
                           user_contacts  VARCHAR(25)
);

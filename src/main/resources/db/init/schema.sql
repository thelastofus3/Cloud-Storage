create table user(
                     id bigint auto_increment primary key ,
                     username varchar(120) check ( length(username) > 3 ) unique not null ,
                     email varchar(255) unique not null ,
                     password varchar(100) check ( length(password) > 3 ) not null,
                    role varchar(100) not null
);
create table users(

    id bigint not null auto_increment,
    name varchar(100) not null,
    email varchar(100) unique,
    document varchar(22) unique,
    phone varchar(22) unique,

    primary key(id)

);

create table users_pin(
    id bigint not null auto_increment,
    pin int not null,
    generated_pins int not null,
    type varchar(100) not null,
    created_at timestamp not null,
    updated_at timestamp,
    user_id bigint not null,
    primary key(id),
    foreign key (user_id) references users(id)
);
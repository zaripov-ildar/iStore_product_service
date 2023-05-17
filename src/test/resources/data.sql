CREATE TABLE IF NOT EXISTS categories
(
    id         bigserial primary key,
    title      varchar(255) unique,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

CREATE TABLE IF NOT EXISTS products
(
    id          bigserial primary key,
    title       varchar(255),
    description text,
    price       numeric(8, 2) not null,
    category_id bigint references categories (id) on delete cascade ,
    created_at  timestamp default current_timestamp,
    updated_at  timestamp default current_timestamp
);

INSERT INTO categories(title)
VALUES ('food'),
       ('blanket');

INSERT INTO products (title, price, category_id)
values ('product #1', 1, 1),
       ('product #2', 2, 1),
       ('product #3', 3, 1),
       ('product #1', 1, 2),
       ('product #2', 2, 2),
       ('product #3', 3, 2);
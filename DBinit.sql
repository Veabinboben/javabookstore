CREATE TABLE books(
    id SERIAL PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    publish_date DATE NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    cover_link VARCHAR(80)
);

CREATE TABLE authors(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL,
    bio TEXT,
    photo_link VARCHAR(80)
);

CREATE TABLE genres(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE warehouses(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    location VARCHAR(50) NOT NULL
);

------

CREATE TABLE book_authors(
    id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(id),
    author_id INT REFERENCES authors(id)
);

CREATE TABLE book_genres(
    id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(id),
    genre_id INT REFERENCES genres(id)
);

CREATE TABLE book_warehouses(
    id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(id),
    warehouse_id INT REFERENCES warehouses(id),
    stock INT CHECK (stock >= 0)
);


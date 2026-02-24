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

CREATE TABLE city(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE warehouses(
    id SERIAL PRIMARY KEY,
    adress VARCHAR(50) NOT NULL,
    city_id INT REFERENCES city(id)
);

------

CREATE TABLE book_authors(
    id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(id) ON DELETE CASCADE,
    author_id INT REFERENCES authors(id),
    CONSTRAINT uniquie_book_author UNIQUE (book_id, author_id)
);

CREATE TABLE book_genres(
    id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(id) ON DELETE CASCADE,
    genre_id INT REFERENCES genres(id),
    CONSTRAINT uniquie_book_genre UNIQUE (book_id, genre_id)
);

CREATE TABLE stocks(
    id SERIAL PRIMARY KEY,
    book_id INT REFERENCES books(id) ON DELETE CASCADE,
    warehouse_id INT REFERENCES warehouses(id),
    CONSTRAINT uniquie_book_warehouse UNIQUE (book_id, warehouse_id),
    stock INT CHECK (stock >= 0)
);


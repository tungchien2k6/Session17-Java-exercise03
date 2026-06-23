-- Tạo bảng books
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    author VARCHAR(255) UNIQUE NOT NULL,
    published_year INT NOT NULL,
    price DECIMAL(10,2) NOT NULL
);
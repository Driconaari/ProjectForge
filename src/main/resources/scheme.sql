CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN NOT NULL  -- New column
);
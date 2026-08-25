CREATE TABLE news (
    id BIGSERIAL PRIMARY KEY,
    site VARCHAR(255) NOT NULL,
    title VARCHAR(500) NOT NULL,
    category VARCHAR(255),
    publication_date TIMESTAMP NOT NULL,
    link VARCHAR(2048) NOT NULL
);
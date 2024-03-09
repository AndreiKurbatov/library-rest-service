CREATE TABLE IF NOT EXISTS library.book_readers (
	id BIGINT PRIMARY KEY,
	book_reader_id BIGINT UNIQUE NOT NULL,
	age INTEGER
);

CREATE TABLE IF NOT EXISTS library.locations (
	id BIGINT PRIMARY KEY,
	book_reader_id BIGINT REFERENCES library.book_readers (book_reader_id),
	location_name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS library.authors (
	id BIGINT PRIMARY KEY, 
	author_name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS library.publishers (
	id BIGINT PRIMARY KEY,
	publisher_name TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS library.books (
	id BIGINT PRIMARY KEY,
	isbn VARCHAR(10) UNIQUE NOT NULL,
	book_title TEXT,
	author_name TEXT REFERENCES library.authors (author_name),
	publisher_name TEXT REFERENCES library.publishers (publisher_name),
	publication_year INTEGER,
	image_url_s TEXT,
	image_url_m TEXT,
	image_url_l TEXT
);

CREATE TABLE IF NOT EXISTS library.book_ratings (
	id BIGINT PRIMARY KEY,
	book_reader_id BIGINT REFERENCES library.book_readers (book_reader_id),
	isbn VARCHAR(10) REFERENCES library.books (isbn),
	book_rating INTEGER
);

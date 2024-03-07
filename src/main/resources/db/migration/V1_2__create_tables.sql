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
	author_name TEXT
);

CREATE TABLE IF NOT EXISTS library.publishers (
	id BIGINT PRIMARY KEY,
	publisher_name TEXT
);

CREATE TABLE IF NOT EXISTS library.books (
	id BIGINT PRIMARY KEY,
	isbn VARCHAR(10) UNIQUE NOT NULL,
	book_title TEXT,
	author_id BIGINT REFERENCES library.authors (id),
	publisher_id BIGINT REFERENCES library.publishers (id),
	publication_year INTEGER,
	image_url_l TEXT
);

CREATE TABLE IF NOT EXISTS library.book_ratings (
	id BIGINT PRIMARY KEY,
	book_reader_id BIGINT REFERENCES library.book_readers (book_reader_id),
	isbn VARCHAR(10) REFERENCES library.books (isbn),
	book_rating INTEGER
);

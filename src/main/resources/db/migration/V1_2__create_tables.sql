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
	author_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS library.publishers (
	id BIGINT PRIMARY KEY,
	publisher_name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS library.books (
	id BIGINT PRIMARY KEY,
	isbn VARCHAR(10) UNIQUE NOT NULL,
	book_title TEXT,
	author_name VARCHAR(255) REFERENCES library.authors (author_name),
	publisher_name VARCHAR(255) REFERENCES library.publishers (publisher_name),
	publication_year INTEGER,
	image_url_s TEXT,
	image_url_m TEXT,
	image_url_l TEXT
);

CREATE TABLE IF NOT EXISTS library.book_ratings (
	id BIGINT PRIMARY KEY,
	book_reader_id BIGINT REFERENCES library.book_readers (book_reader_id),
	isbn VARCHAR(10) REFERENCES library.books (isbn),
	book_rating INTEGER,
	CONSTRAINT one_book_one_book_reader_one_feedback UNIQUE (book_reader_id, isbn)
);

CREATE TABLE IF NOT EXISTS library.book_readers_books (
	isbn VARCHAR(10) REFERENCES library.books (isbn), 
	book_reader_id BIGINT REFERENCES library.book_readers (book_reader_id),
	CONSTRAINT pk_book_readers_books PRIMARY KEY (isbn, book_reader_id)
);

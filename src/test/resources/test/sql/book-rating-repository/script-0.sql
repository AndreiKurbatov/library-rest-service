INSERT INTO library.authors (id, author_name) VALUES (10001, 'Author Name');

INSERT INTO library.publishers (id, publisher_name) VALUES (10001, 'Publisher Name');

INSERT INTO library.books (id, isbn, book_title, author_name, publisher_name, publication_year, image_url_s, image_url_m, image_url_l) VALUES 
(10001, '1234567890', 'The Book', 'Author Name', 'Publisher Name', 1234, 'ref1', 'ref2', 'ref3');

INSERT INTO library.book_readers (id, book_reader_id, age) VALUES 
(10001, 10111, 12),
(10002, 10112, 52),
(10003, 10113, 23);

INSERT INTO library.book_ratings (id, book_reader_id, isbn, book_rating) VALUES 
(10001, 10111, '1234567890', 10),
(10002, 10112, '1234567890', 5),
(10003, 10113, '1234567890', 8);



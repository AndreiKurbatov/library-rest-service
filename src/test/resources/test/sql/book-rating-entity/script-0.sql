INSERT INTO library.books (id, isbn, book_title,  publication_year, image_url_s, image_url_m, image_url_l)
VALUES (10002, '1234567890', 'Example Book Title', 2022, 'http://example.com/small.jpg', 'http://example.com/medium.jpg', 'http://example.com/large.jpg');

INSERT INTO library.book_readers (id, book_reader_id, age)
VALUES (10001, 10001, 30);

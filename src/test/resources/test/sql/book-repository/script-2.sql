INSERT INTO library.books (id, isbn, book_title, publication_year, image_url_l) VALUES
(1, '1234567890', 'Sample Book 1', 2022, 'https://example.com/book1.jpg'),
(2, '0987654321', 'Sample Book 2', 2021, 'https://example.com/book2.jpg'),
(3, '5432109876', 'Sample Book 3', 2020, 'https://example.com/book3.jpg');

INSERT INTO library.book_readers (id, book_reader_id, age) VALUES 
(10001, 1, 18),
(10002, 2, 20), 
(10003, 3, 22),
(10004, 4, 24);

INSERT INTO library.locations (id, book_reader_id, location_name) VALUES 
(10005, 1, 'location1'),
(10006, 1, 'location2'),
(10007, 1, 'location3'),
(10008, 2, 'location1'),
(10009, 2, 'location2'),
(10010, 3, 'location1'),
(10011, 4, 'location1'),
(10012, 4, 'location2'),
(10013, 4, 'location3'),
(10014, 4, 'location4');

INSERT INTO library.book_readers_books (isbn, book_reader_id) VALUES 
('1234567890', 1),
('0987654321', 2),
('5432109876', 3);

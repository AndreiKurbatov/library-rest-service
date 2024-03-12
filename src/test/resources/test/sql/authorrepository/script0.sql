INSERT INTO university.authors (id, author_name) VALUES
(1, 'John Doe'),
(2, 'Jane Smith'),
(3, 'Alice Johnson');

INSERT INTO university.publishers (id, publisher_name) VALUES
(1, 'ABC Publishing'),
(2, 'XYZ Publications'),
(3, 'Acme Books');

INSERT INTO university.books (id, isbn, book_title, author_id, publisher_id, publication_year, image_url_l) VALUES
(1, '1234567890', 'Sample Book 1', 1, 1, 2022, 'https://example.com/book1.jpg'),
(2, '0987654321', 'Sample Book 2', 2, 2, 2021, 'https://example.com/book2.jpg'),
(3, '5432109876', 'Sample Book 3', 3, 3, 2020, 'https://example.com/book3.jpg');

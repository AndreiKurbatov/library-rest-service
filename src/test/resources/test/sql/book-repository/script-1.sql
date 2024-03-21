INSERT INTO library.books (id, isbn, book_title, publication_year, image_url_s, image_url_m, image_url_l)
VALUES
    (10001, 'ISBN1', 'Book Title 1', 2000, 'url_s_1', 'url_m_1', 'url_l_1'),
    (10002, 'ISBN2', 'Book Title 2', 2005, 'url_s_2', 'url_m_2', 'url_l_2'),
    (10003, 'ISBN3', 'Book Title 3', 2010, 'url_s_3', 'url_m_3', 'url_l_3');

-- 10 users with an average age of 10
INSERT INTO library.book_readers (id, book_reader_id, age)
VALUES
    (10000, 10000, 8),
    (10001, 10001, 7),
    (10002, 10002, 10),
    (10003, 10003, 12),
    (10004, 10004, 11),
    (10005, 10005, 9),
    (10006, 10006, 13),
    (10007, 10007, 6),
    (10008, 10008, 8),
    (10009, 10009, 7);
    
-- 10 users with an average age of 20
INSERT INTO library.book_readers (id, book_reader_id, age)
VALUES
    (10010, 10010, 18),
    (10011, 10011, 17),
    (10012, 10012, 20),
    (10013, 10013, 22),
    (10014, 10014, 21),
    (10015, 10015, 19),
    (10016, 10016, 23),
    (10017, 10017, 16),
    (10018, 10018, 18),
    (10019, 10019, 17);
    
-- 10 users with an average age of 30
INSERT INTO library.book_readers (id, book_reader_id, age)
VALUES
    (10020, 10020, 28),
    (10021, 10021, 27),
    (10022, 10022, 30),
    (10023, 10023, 32),
    (10024, 10024, 31),
    (10025, 10025, 29),
    (10026, 10026, 33),
    (10027, 10027, 26),
    (10028, 10028, 28),
    (10029, 10029, 27);

INSERT INTO library.book_readers_books (isbn, book_reader_id) 
VALUES 
	('ISBN1', 10000),
	('ISBN1', 10001),
	('ISBN1', 10002),
	('ISBN1', 10003),
	('ISBN1', 10004),
	('ISBN1', 10005),
	('ISBN1', 10006),
	('ISBN1', 10007),
	('ISBN1', 10008),
	('ISBN1', 10009),
	('ISBN2', 10010),
	('ISBN2', 10011),
	('ISBN2', 10012),
	('ISBN2', 10013),
	('ISBN2', 10014),
	('ISBN2', 10015),
	('ISBN2', 10016),
	('ISBN2', 10017),
	('ISBN2', 10018),
	('ISBN2', 10019),
	('ISBN3', 10020),
	('ISBN3', 10021),
	('ISBN3', 10022),
	('ISBN3', 10023),
	('ISBN3', 10024),
	('ISBN3', 10025),
	('ISBN3', 10026),
	('ISBN3', 10027),
	('ISBN3', 10028),
	('ISBN3', 10029);
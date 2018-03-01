INSERT INTO user (username, password) VALUES 
	('peter@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri'),
	('john@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri'),
	('katie@example.com', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri');

INSERT INTO friend(user_id, friend_id) VALUES
	(3, 4),
	(3, 5),
	(4, 3),
	(4, 5),
	(5, 3),
	(5, 4)
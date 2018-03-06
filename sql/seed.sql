INSERT INTO user (username, name, password) VALUES
	('peter@example.com', 'Peter Pace', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri'),
	('john@example.com', 'John Judd', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri'),
	('katie@example.com', 'Katie Kemp', '$2a$10$D4OLKI6yy68crm.3imC9X.P2xqKHs5TloWUcr6z5XdOqnTrAK84ri');

INSERT INTO friend(user_id, friend_id) VALUES
	(1, 2),
	(1, 3),
	(2, 1),
	(2, 3),
	(3, 1),
	(3, 2)
BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;






CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	age varchar(50) NOT NULL,
	

	CONSTRAINT PK_users PRIMARY KEY (user_id)
	
);

INSERT INTO users (username,password_hash,role, first_name, last_name, age) 
VALUES ('tim123','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Tim', 'C', '24');
INSERT INTO users (username,password_hash,role, first_name, last_name, age) 
VALUES ('cambo123','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'Cam', 'C', 'Wise old man');
INSERT INTO users (username,password_hash,role, first_name, last_name, age) 
VALUES ('sarah123','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN', 'Sarah', 'M', '22');
INSERT INTO users (username,password_hash,role, first_name, last_name, age) 
VALUES ('josh123','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER', 'Josh', 'J', '26');

-- CREATE TABLE user_info (
-- 	user_id int NOT NULL,
-- 	first_name varchar(50) NOT NULL,
-- 	last_name varchar(50) NOT NULL,
-- 	age varchar(50) NOT NULL,

-- 	CONSTRAINT PK_user_info PRIMARY KEY (user_id),
-- 	CONSTRAINT fk_user_info FOREIGN KEY (user_id) REFERENCES users (user_id)
-- );

CREATE TABLE family (
	family_id serial,
	family_name varchar(50) NOT NULL,

	CONSTRAINT pk_family PRIMARY KEY (family_id)
	
);

INSERT INTO family (family_name) VALUES ('Bibli Team');
INSERT INTO family (family_name) VALUES ('demo_family');
-- INSERT INTO family_members (parent, family_name, user_id) VALUES (true,'Test Family2',3);
-- INSERT INTO family_members (parent, family_name, user_id) VALUES (false, 'Test Family',4);


CREATE TABLE user_family (
	family_id int NOT NULL,
	user_id int NOT NULL,

	CONSTRAINT fk_user_family_family FOREIGN KEY (family_id) REFERENCES family (family_id),
	CONSTRAINT fk_user_family_user FOREIGN KEY (user_id) REFERENCES users (user_id),
	CONSTRAINT pk_user_family PRIMARY KEY (family_id, user_id)
);
INSERT INTO user_family (family_id, user_id) VALUES (1,1);
INSERT INTO user_family (family_id, user_id) VALUES (1,2);
INSERT INTO user_family (family_id, user_id) VALUES (1,3);
INSERT INTO user_family (family_id, user_id) VALUES (1,4);


CREATE TABLE books (
	book_id serial,
	title varchar(50) NOT NULL,
	author varchar(50) NOT NULL,
	isbn numeric NOT NULL,

	CONSTRAINT pk_books PRIMARY KEY (book_id)
);

INSERT INTO books (title, author, isbn) VALUES ('East of Eden', 'John Steinbeck', 9780142000656);
INSERT INTO books (title, author, isbn) VALUES ('Little Programmer That Could', 'Katie Dwyer', 9781440630361);
INSERT INTO books (title, author, isbn) VALUES ('Eragon', 'Christopher Paolini', 9781440630361);
INSERT INTO books (title, author, isbn) VALUES ('The Wise Mans Fear', 'Patrick Rothfuss', 9781440630361);
INSERT INTO books (title, author, isbn) VALUES ('The Hound of Rowan', 'Henry H. Neff', 9781440630361);
INSERT INTO books (title, author, isbn) VALUES ('1984', 'George Orwell', 9781440630361);
INSERT INTO books (title, author, isbn) VALUES ('The Fountainhead', 'Ayn Rand', 9781440630361);
INSERT INTO books (title, author, isbn) VALUES ('How to Generate Sql Datasets', 'Person Typing', 9781440630361);


CREATE TABLE reading_log (
	log_id serial,
	user_id int NOT NULL,
	book_id int NOT NULL,
	time_read int default 0,
	book_format varchar(50) default 'not started',
	notes text default 'no notes',
	is_complete boolean default false,
	log_date date NOT NULL default CURRENT_DATE,
	

	CONSTRAINT pk_reading_log PRIMARY KEY (log_id),
	CONSTRAINT fk_reading_log FOREIGN KEY (book_id) REFERENCES books (book_id)
);

INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (1, 8, 30, '2020-08-01');
INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (2, 2, 45, '2020-08-02');
INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (3, 1, 15, '2020-08-02');
INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (4, 1, 120, '2020-08-03');
INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (1, 5, 60, '2020-08-03');
INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (1, 5, 30, '2020-08-04');
INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (4, 1, 15, '2020-08-04');
INSERT INTO reading_log (user_id, book_id, time_read, log_date) VALUES (4, 1, 125, '2020-08-06');

CREATE TABLE prizes (
	prize_id serial,
	name varchar(100) NOT NULL,
	description varchar(300) NOT NULL,
	milestone int NOT NULL,  -- mintues to read for goal
	user_role varchar(30) DEFAULT 'BOTH',
	max_prizes int DEFAULT 0,  -- 0 should indicate unlimited prizes can be awarded
	start_date date NOT NULL,
	end_date date NOT NULL,
	family_id int NOT NULL,
	is_active boolean DEFAULT true,

	CONSTRAINT pk_prizes PRIMARY KEY (prize_id),
	CONSTRAINT fk_prizes FOREIGN KEY (family_id) REFERENCES family (family_id) 
	
);

INSERT INTO prizes (name, description, milestone,  max_prizes, start_date, end_date, family_id, is_active)
VALUES ('BigReader', 'Woah, Big Reader! Redeem for free icecream', 100, 2, '2020-08-01', '2020-08-04', 1, true);

INSERT INTO prizes (name, description, milestone, start_date, end_date, family_id)
VALUES ('StarReader', 'You are a Star! Pizza Night!', 1000,  '2020-08-01', '2020-08-15', 1);

INSERT INTO prizes (name, description, milestone, user_role, start_date, end_date, family_id)
VALUES ('Movie Date', 'Get a Baby Sitter!', 1000, 'USER_ADMIN',  '2020-07-01', '2020-08-10', 1);

CREATE TABLE family_prizes (
	family_prize_id serial,
	username varchar(50) NOT NULL,
	family_id int NOT NULL,
	prize_id int NOT NULL,

	CONSTRAINT pk_family_prizes PRIMARY KEY (family_prize_id)
);

INSERT INTO family_prizes (username, family_id, prize_id) VALUES ('tim123', 1, 1);
INSERT INTO family_prizes (username, family_id, prize_id) VALUES ('cambo123', 1, 1);
INSERT INTO family_prizes (username, family_id, prize_id) VALUES ('sarah123', 1, 3);





COMMIT TRANSACTION;

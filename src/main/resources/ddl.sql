CREATE TABLE age_categories
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  category VARCHAR(255) NOT NULL
);
CREATE TABLE careers
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title VARCHAR(50) NOT NULL
);
CREATE TABLE comments
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  text LONGTEXT NOT NULL,
  user_id INT NOT NULL,
  movies_id INT NOT NULL
);
CREATE TABLE countries
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL
);
CREATE TABLE genres
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL
);
CREATE TABLE movies
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  duration INT NOT NULL,
  release_date DATE,
  rating REAL,
  description LONGTEXT,
  img_url VARCHAR(255),
  age_categories_id INT NOT NULL,
  imdb_id INT NOT NULL
);
CREATE TABLE movies_countries
(
  countries_id INT NOT NULL,
  movies_id INT NOT NULL,
  PRIMARY KEY (countries_id, movies_id)
);
CREATE TABLE movies_genres
(
  genres_id INT NOT NULL,
  movies_id INT NOT NULL,
  PRIMARY KEY (genres_id, movies_id)
);
CREATE TABLE movies_persons
(
  movies_id INT NOT NULL,
  persons_id INT NOT NULL,
  careers_id INT NOT NULL,
  PRIMARY KEY (movies_id, persons_id, careers_id)
);
CREATE TABLE persons
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  fullname VARCHAR(255) NOT NULL,
  imdb_id VARCHAR(255),
  birthdate DATE,
  photo_url VARCHAR(255)
);
CREATE TABLE roles
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  role VARCHAR(255) NOT NULL
);
CREATE TABLE users
(
  id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  fullname VARCHAR(255) NOT NULL,
  image VARCHAR(255)
);
CREATE TABLE users_roles
(
  users_id INT NOT NULL,
  roles_id INT NOT NULL,
  PRIMARY KEY (users_id, roles_id)
);
CREATE UNIQUE INDEX category ON age_categories (category);
CREATE UNIQUE INDEX title ON careers (title);
ALTER TABLE comments ADD FOREIGN KEY (movies_id) REFERENCES movies (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE comments ADD FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE INDEX fk_comments_movies1 ON comments (movies_id);
CREATE INDEX fk_comments_users1 ON comments (user_id);
CREATE UNIQUE INDEX title ON countries (title);
CREATE UNIQUE INDEX title ON genres (title);
ALTER TABLE movies ADD FOREIGN KEY (age_categories_id) REFERENCES age_categories (id) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE UNIQUE INDEX imdb_id ON movies (imdbId);
CREATE INDEX fk_movies_age_categories1 ON movies (age_categories_id);
ALTER TABLE movies_countries ADD FOREIGN KEY (countries_id) REFERENCES countries (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE movies_countries ADD FOREIGN KEY (movies_id) REFERENCES movies (id) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE INDEX fk_movies_countries_movies1 ON movies_countries (movies_id);
ALTER TABLE movies_genres ADD FOREIGN KEY (genres_id) REFERENCES genres (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE movies_genres ADD FOREIGN KEY (movies_id) REFERENCES movies (id) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE INDEX fk_movies_genres_movies1 ON movies_genres (movies_id);
ALTER TABLE movies_persons ADD FOREIGN KEY (careers_id) REFERENCES careers (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE movies_persons ADD FOREIGN KEY (movies_id) REFERENCES movies (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE movies_persons ADD FOREIGN KEY (persons_id) REFERENCES persons (id) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE INDEX fk_movies_persons_careers1 ON movies_persons (careers_id);
CREATE INDEX fk_movies_persons_persons1 ON movies_persons (persons_id);
CREATE UNIQUE INDEX imdb_id ON persons (imdb_id);
CREATE UNIQUE INDEX role ON roles (role);
CREATE UNIQUE INDEX email ON users (email);
ALTER TABLE users_roles ADD FOREIGN KEY (roles_id) REFERENCES roles (id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE users_roles ADD FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;
CREATE INDEX fk_users_roles_roles1 ON users_roles (roles_id);

DROP ALL OBJECTS;
CREATE TABLE IF NOT EXISTS films (
    film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR,
    description VARCHAR(200),
    release_date DATE,
    duration INTEGER,
    MPA_rating_id INTEGER
);
CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    email VARCHAR,
    login VARCHAR,
    name VARCHAR,
    birthday DATE
);
CREATE TABLE IF NOT EXISTS genres (
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR
);
CREATE TABLE IF NOT EXISTS MPA_ratings (
    MPA_rating_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR
);
CREATE TABLE IF NOT EXISTS film_genres (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id INTEGER REFERENCES films(film_id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres(genre_id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS film_likes (
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id INTEGER REFERENCES films(film_id) ON DELETE CASCADE,
    user_who_liked_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS user_friends(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE,
    friend_id INTEGER REFERENCES users(user_id) ON DELETE CASCADE,
    is_confirmed BOOLEAN
);
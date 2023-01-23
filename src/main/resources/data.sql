DELETE FROM genres WHERE name IN ('Комедия', 'Драма', 'Мультфильм', 'Триллер', 'Документальный', 'Боевик');
INSERT INTO genres(name)
VALUES ('Комедия'), ('Драма'), ('Мультфильм'), ('Триллер'), ('Документальный'), ('Боевик');
DELETE FROM MPA_RATINGS WHERE name IN ('G', 'PG', 'PG-13', 'R', 'NC-17');
INSERT INTO MPA_RATINGS(name)
VALUES ('G'), ('PG'), ('PG-13'), ('R'), ('NC-17');
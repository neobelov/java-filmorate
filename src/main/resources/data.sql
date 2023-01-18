DELETE FROM GENRES WHERE GENRE_ID IN (1,2,3,4,5,6);
INSERT INTO genres(genre_id, name)
VALUES (1,'Комедия'), (2,'Драма'), (3,'Мультфильм'), (4,'Триллер'), (5,'Документальный'), (6,'Боевик');
DELETE FROM MPA_RATINGS WHERE MPA_RATING_ID IN (1,2,3,4,5);
INSERT INTO MPA_RATINGS(MPA_RATING_ID,name)
VALUES (1,'G'), (2,'PG'), (3,'PG-13'), (4,'R'), (5,'NC-17');
INSERT INTO categories (name) VALUES ('Programación');
INSERT INTO categories (name) VALUES ('Ficción');
INSERT INTO categories (name) VALUES ('Historia');

INSERT INTO books (title, author, isbn, publication_date, rating, price, stock, is_visible, category_id, image, summary)
VALUES ('Spring Boot en Acción', 'Craig Walls', '9781617292545', '2016-11-28', 4.5, 29.99, 100, TRUE, 1, 'https://m.media-amazon.com/images/I/413r-85hmzL._SX342_SY445_.jpg','Spring Boot en Acción de Craig Walls es una guía práctica para dominar Spring Boot, facilitando el desarrollo de aplicaciones Java con menos configuración. El libro enseña a integrar Spring Boot efectivamente para automatizar la configuración y mejorar la eficiencia de proyectos.');

INSERT INTO books (title, author, isbn, publication_date, rating, price, stock, is_visible, category_id, image, summary)
VALUES ('Java: The Complete Reference', 'Herbert Schildt', '9781260440232', '2018-12-10', 4.8, 45.00, 150, TRUE, 1, 'https://m.media-amazon.com/images/I/51xYxhz7xzL._SX342_SY445_.jpg', 'Java: The Complete Reference de Herbert Schildt es una guía completa que abarca desde los fundamentos hasta las características avanzadas de Java, proporcionando herramientas esenciales para el desarrollo eficaz en este lenguaje.');

INSERT INTO books (title, author, isbn, publication_date, rating, price, stock, is_visible, category_id, image, summary)
VALUES ('Cien años de soledad', 'Gabriel García Márquez', '9780307474728', '1967-05-30', 4.9, 19.99, 200, TRUE, 2, 'https://image.cdn0.buscalibre.com/5b59f73590f0b5ca578b4568.__RS360x360__.jpg', 'Cien años de soledad es una novela escrita por Gabriel García Márquez, publicada en 1967. Es considerada una obra maestra de la literatura hispanoamericana y universal, y uno de los libros más traducidos y leídos en español. La novela narra la historia de la familia Buendía a lo largo de siete generaciones en el pueblo ficticio de Macondo.');

INSERT INTO books (title, author, isbn, publication_date, rating, price, stock, is_visible, category_id, image, summary)
VALUES ('Don Quijote de la Mancha', 'Miguel de Cervantes', '9788420412149', '1605-01-16', 4.7, 25.00, 80, TRUE, 2, 'https://images.cdn1.buscalibre.com/fit-in/360x360/a6/18/a618be10eae5c2a608ec6e22e6917e29.jpg', 'Don Quijote de la Mancha es una novela escrita por el español Miguel de Cervantes Saavedra. Publicada su primera parte con el título de El ingenioso hidalgo don Quijote de la Mancha a comienzos de 1605, es una de las obras más destacadas de la literatura española y la literatura universal, y una de las más traducidas.');

INSERT INTO books (title, author, isbn, publication_date, rating, price, stock, is_visible, category_id, image, summary)
VALUES ('Sapiens: De animales a dioses', 'Yuval Noah Harari', '9780062316097', '2011-02-04', 4.8, 22.50, 120, TRUE, 3, 'https://m.media-amazon.com/images/I/811PTyrckTL._SY466_.jpg', 'Sapiens: De animales a dioses de Yuval Noah Harari explora la historia de la humanidad desde las primeras especies del género Homo hasta el presente, analizando cómo los humanos han llegado a dominar el planeta y transformar el mundo con sus revoluciones cognitiva, agrícola, científica e industrial.');

INSERT INTO books (title, author, isbn, publication_date, rating, price, stock, is_visible, category_id, image, summary)
VALUES ('Breve historia del tiempo', 'Stephen Hawking', '9780553380163', '1988-04-01', 4.6, 18.99, 90, FALSE, 3, 'https://m.media-amazon.com/images/I/51IOknSRuHL._SY445_SX342_.jpg', 'Breve historia del tiempo de Stephen Hawking es una obra que explica conceptos complejos del universo, como los agujeros negros, la teoría del big bang y la física de partículas, de manera accesible para el público general, desafiando nuestra percepción del tiempo y el espacio.');


INSERT INTO books (title, author, isbn, publication_date, rating, price, stock, is_visible, category_id, image, summary)
VALUES ('Novelas ejemplares', 'Miguel de Cervantes', '9780140442489', '1613-01-01', 4.7, 22.00, 120, TRUE, 2, 'https://images.cdn1.buscalibre.com/fit-in/360x360/ed/23/ed23bc5b499b128e119a6f2397cb9bd1.jpg', 'Novelas ejemplares es una serie de relatos cortos escritos por Miguel de Cervantes entre 1590 y 1612. Este compendio incluye doce novelas que combinan elementos morales, cómicos y picarescos, ofreciendo una rica exploración de los vicios y virtudes humanas.');

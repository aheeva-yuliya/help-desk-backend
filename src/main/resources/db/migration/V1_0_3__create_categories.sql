CREATE TABLE categories
(
    id       SERIAL NOT NULL,
    category VARCHAR(255),
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

INSERT INTO categories(id, category)
VALUES (1, 'Application & Services'),
       (2, 'Benefits & Paper Work'),
       (3, 'Hardware & Software'),
       (4, 'People Management'),
       (5, 'Security & Access'),
       (6, 'Workplaces & Facilities');

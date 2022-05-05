CREATE TABLE urgencies
(
    id      SERIAL NOT NULL,
    urgency VARCHAR(255),
    CONSTRAINT pk_priorities PRIMARY KEY (id)
);

INSERT INTO urgencies(id, urgency)
VALUES (1, 'Critical'),
       (2, 'High'),
       (3, 'Average'),
       (4, 'Low');
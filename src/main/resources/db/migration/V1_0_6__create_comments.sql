CREATE TABLE comments
(
    id        BIGSERIAL                               NOT NULL,
    owner_id  INTEGER                                 NOT NULL,
    text      VARCHAR(255),
    date      TIMESTAMP WITHOUT TIME ZONE,
    ticket_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_comments PRIMARY KEY (id)
);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_TICKET FOREIGN KEY (ticket_id) REFERENCES tickets (id);
CREATE TABLE feedbacks
(
    id        BIGSERIAL                               NOT NULL,
    owner_id  INTEGER                                 NOT NULL,
    rate      INTEGER                                 NOT NULL,
    comment   VARCHAR(255),
    date      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    ticket_id BIGINT                                  NOT NULL,
    CONSTRAINT pk_feedbacks PRIMARY KEY (id)
);

ALTER TABLE feedbacks
    ADD CONSTRAINT FK_FEEDBACKS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE feedbacks
    ADD CONSTRAINT FK_FEEDBACKS_ON_TICKET FOREIGN KEY (ticket_id) REFERENCES tickets (id);
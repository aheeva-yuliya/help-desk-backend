CREATE TABLE records
(
    id          BIGSERIAL NOT NULL,
    ticket_id   BIGINT                                  NOT NULL,
    date        TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    action      VARCHAR(255)                            NOT NULL,
    owner_id    INTEGER                                 NOT NULL,
    description VARCHAR(255),
    CONSTRAINT pk_records PRIMARY KEY (id)
);

ALTER TABLE records
    ADD CONSTRAINT FK_RECORDS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE records
    ADD CONSTRAINT FK_RECORDS_ON_TICKET FOREIGN KEY (ticket_id) REFERENCES tickets (id);
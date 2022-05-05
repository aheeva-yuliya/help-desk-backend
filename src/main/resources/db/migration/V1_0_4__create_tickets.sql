CREATE TABLE tickets
(
    id                      BIGSERIAL NOT NULL,
    name                    VARCHAR(255),
    description             VARCHAR(255),
    created_on              TIMESTAMP WITHOUT TIME ZONE,
    desired_resolution_date date,
    assignee_id             INTEGER,
    owner_id                INTEGER   NOT NULL,
    state                   VARCHAR(255),
    category_id             INTEGER,
    urgency_id              INTEGER,
    approver_id             INTEGER,
    CONSTRAINT pk_tickets PRIMARY KEY (id)
);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_APPROVER FOREIGN KEY (approver_id) REFERENCES users (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_ASSIGNEE FOREIGN KEY (assignee_id) REFERENCES users (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_OWNER FOREIGN KEY (owner_id) REFERENCES users (id);

ALTER TABLE tickets
    ADD CONSTRAINT FK_TICKETS_ON_URGENCY FOREIGN KEY (urgency_id) REFERENCES urgencies (id);

CREATE INDEX owner_idx ON tickets (owner_id);
CREATE INDEX state_idx ON tickets (state);

insert into tickets(name, description, created_on, desired_resolution_date, assignee_id, owner_id, state, category_id,
                    urgency_id, approver_id)
values ('name', 'description', '2022-01-01 01:00', '2022-01-05', NULL, 2, 'DRAFT', 1, 1, NULL),
       ('nameY', 'descriptionY', '2022-01-01 02:00', '2022-01-04', NULL, 2, 'APPROVED', 1, 3, 4),
       ('nameL', 'descriptionJ', '2022-01-01 03:00', '2022-01-08', 6, 4, 'PROGRESS', 1, 1, 3),
       ('nameW', 'descriptionJ', '2022-01-01 03:00', '2022-01-04', NULL, 3, 'NEW', 1, 1, NUll),
       ('nameW', 'descriptionJ', '2022-01-01 03:00', '2022-01-06', 6, 3, 'CANCELED', 1, 1, 3),
       ('nameW', 'descriptionJ', '2022-01-01 03:00', '2022-01-07', 7, 3, 'DONE', 1, 2, 4),
       ('nameW', 'descriptionJ', '2022-01-01 03:00', '2022-01-07', 7, 4, 'DONE', 1, 3, 3);
;
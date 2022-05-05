CREATE TABLE users
(
    id          SERIAL         NOT NULL,
    name        VARCHAR(255),
    email       VARCHAR(255)   UNIQUE NOT NULL,
    password    VARCHAR(255)   UNIQUE NOT NULL,
    role        VARCHAR(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);
create index user_email_index on users (email);

insert into users(name, email, password, role)
values
       ('yuliya', 'yuliya.aheeva@gmail.com','$2a$10$DUCkcQ6LWLu2jTNIUpqIJ.MApnGKbeBtynYuemgG3r.i7fmaVuSEC', 'EMPLOYEE'),
       ('user1', 'employee1_mogilev@yopmail.com','$2a$10$F9FqQ4oeaoc2v3EiXHs64.JdFb1vA4J86PAbrluxNduca.uJFouMa', 'EMPLOYEE'),
       ('user2', 'employee2_mogilev@yopmail.com','$2a$10$QSRdDEF4Q61dWK4JYyDewOWq2cgDAiTlEZC622uNpk6ki/kY3xjHW', 'EMPLOYEE'),
       ('manager1', 'manager1_mogilev@yopmail.com','$2a$10$eiOYSMMIyPolkhiL.kK8meRhjNfDHtcI2cyKOJy2L406WmPNeEnfm', 'MANAGER'),
       ('manager2', 'manager2_mogilev@yopmail.com','$2a$10$n/46njNnDzOSk63lKKsrmeJwRowhryWkf/O.JTnLw9SqK.X7WDB.O', 'MANAGER'),
       ('engineer1', 'engineer1_mogilev@yopmail.com','$2a$10$PZB7jGCAdlSwTg6/fKTeQOUJ.Vz2loS0n8JbxYaEiK8aBL.MhWDUC', 'ENGINEER'),
       ('engineer2', 'engineer2_mogilev@yopmail.com','$2a$10$apZRhCWUA482iZFAcj2nweGhvnRPwpys595lLL/kWGEjWnA.l09Fm', 'ENGINEER');

-- yuliya 123456
-- user1 employee1
-- user2 employee2
-- manager1 manager1
-- manager2 manager2
-- engineer1 engineer1
-- engineer2 engineer2
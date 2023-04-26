--liquibase formatted sql

--changeset liquibase:1 labels:ddl
--comment: Create user table
CREATE TABLE IF NOT EXISTS user
(
    username    text,
    roles       frozen<set<text>>,
    age         int,
    info        text,
    image_url    text,
    PRIMARY KEY (username)
);
-- rollback DROP TABLE user

--changeset liquibase:2 labels:ddl
--comment: Create login table
CREATE TABLE IF NOT EXISTS login
(
    username text,
    password text,
    PRIMARY KEY (username)
);
-- rollback DROP TABLE login
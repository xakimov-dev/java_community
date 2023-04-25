--liquibase formatted sql

--changeset liquibase:1 labels:ddl
--comment: Create node table
CREATE TABLE IF NOT EXISTS node
(
    tenant_id          text,
    uri                text,
    type               text,
    subtype            text,
    instance           text,
    name               text,
    description        text,
    location           text,
    connection_type    text,
    connection_details text,
    PRIMARY KEY (tenant_id, uri)
);
-- rollback DROP TABLE node

--changeset liquibase:2 labels:ddl
--comment: Create node_endpoint table
CREATE TABLE IF NOT EXISTS node_endpoint
(
    tenant_id          text,
    uri                text,
    endpoint           text,
    name               text,
    description        text,
    connection_type    text,
    connection_details text,
    PRIMARY KEY (tenant_id, uri, endpoint)
);
-- rollback DROP TABLE node_endpoint

--changeset liquibase:3 labels:ddl
--comment: Create node_property table
CREATE TABLE IF NOT EXISTS node_property
(
    tenant_id     text,
    uri           text,
    endpoint      text,
    property_name text,
    property_type text,
    data_type     text,
    single_value  text,
    set_value     set<text>,
    PRIMARY KEY (tenant_id, uri, endpoint, property_name)
);
-- rollback DROP TABLE node_property

--changeset liquibase:4 labels:ddl
--comment: Create property_index index on node_property (property_name)
CREATE INDEX IF NOT EXISTS property_index ON node_property (property_name);
--rollback DROP INDEX property_index;

--changeset liquibase:5 labels:ddl
--comment: Create history table
CREATE TABLE IF NOT EXISTS history
(
    tenant_id     text,
    uri           text,
    endpoint      text,
    property_name text,
    timestamp     timestamp,
    action        text,
    modified_by   text,
    property_type text,
    data_type     text,
    single_value  text,
    set_value     frozen<set<text>>,
    PRIMARY KEY (tenant_id, uri, endpoint, property_name, timestamp)
);
-- rollback DROP TABLE history

--changeset liquibase:6 labels:ddl
--comment: Create connection_type table
CREATE TABLE IF NOT EXISTS connection_type (
    tenant_id           text,
    type                text,
    description         text,
    PRIMARY KEY (tenant_id, type)
);
-- rollback DROP TABLE connection_type

--changeset liquibase:7 labels:ddl
--comment: Create connection_type_props table
CREATE TABLE IF NOT EXISTS connection_type_props (
    tenant_id           text,
    type                text,
    property_name       text,
    data_type           text,
    mandatory           boolean,
    hidden              boolean,
    "order"               int,
    PRIMARY KEY (tenant_id, type, property_name)
);
-- rollback DROP TABLE connection_type table

--changeset liquibase:8 labels:ddl
--comment: Create user table
CREATE TABLE IF NOT EXISTS user
(
    username    text,
    roles       frozen<set<text>>,
    tenant_id   text,
    tenant_name text,
    PRIMARY KEY (username)
);
-- rollback DROP TABLE user

--changeset liquibase:9 labels:ddl
--comment: Create login table
CREATE TABLE IF NOT EXISTS login
(
    username text,
    password text,
    PRIMARY KEY (username)
);
-- rollback DROP TABLE login

--changeset liquibase:10 labels:ddl
--comment: Add column endpoint_modify to the connection_type_props table
ALTER TABLE connection_type_props ADD
    (
    endpoint_modify boolean
    );
-- rollback ALTER TABLE connection_type_props DROP (endpoint_modify)
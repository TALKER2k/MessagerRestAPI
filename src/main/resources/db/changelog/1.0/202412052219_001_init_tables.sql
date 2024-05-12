CREATE TABLE users
(
    id                 BIGINT PRIMARY KEY,
    username           VARCHAR(20)  NOT NULL UNIQUE,
    firstname          VARCHAR(20),
    lastname           VARCHAR(20),
    email              VARCHAR(50)  NOT NULL UNIQUE,
    password           VARCHAR(120) NOT NULL,
    active             VARCHAR(50)  NOT NULL,
    last_activity_time TIMESTAMP,
    hobby              VARCHAR(50),
    profession         VARCHAR(50),
    city               VARCHAR(50),
    country            VARCHAR(50)
);


CREATE TABLE roles
(
    id   BIGINT PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL REFERENCES users (id),
    role_id BIGINT    NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE user_friends
(
    user_id   BIGINT NOT NULL REFERENCES users (id),
    friend_id BIGINT NOT NULL REFERENCES users (id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE conversations
(
    id        BIGINT PRIMARY KEY,
    first_id  BIGINT NOT NULL REFERENCES users (id),
    second_id BIGINT NOT NULL REFERENCES users (id)
);

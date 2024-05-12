CREATE TABLE messages
(
    id              BIGINT PRIMARY KEY,
    conversation_id BIGINT REFERENCES conversations (id),
    sender_id       BIGINT REFERENCES users (id),
    message_text    varchar(200),
    sent_at         TIMESTAMP
);

CREATE SEQUENCE users_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE conversations_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE messages_sequence START WITH 1 INCREMENT BY 1;
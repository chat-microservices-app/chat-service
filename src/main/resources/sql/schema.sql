-- table should store the user's data that comes from the user management system
CREATE TABLE IF NOT EXISTS user_read_only (
    user_id UUID NOT NULL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    picture_url VARCHAR(255) NOT NULL,
    defaultRole VARCHAR(255) NOT NULL
);

-- store the rooms created by the users
CREATE TABLE IF NOT EXISTS room (
    room_id UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v1(),
    room_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user_read_only(user_id)
);

-- store the messages submitted in a room
CREATE TABLE IF NOT EXISTS messageForm (
    message_id UUID NOT NULL PRIMARY KEY DEFAULT uuid_generate_v1(),
    user_id UUID NOT NULL,
    room_id UUID NOT NULL,
    messageForm VARCHAR NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NULL,
    FOREIGN KEY (user_id) REFERENCES user_read_only(user_id),
    FOREIGN KEY (room_id) REFERENCES room(room_id)
);
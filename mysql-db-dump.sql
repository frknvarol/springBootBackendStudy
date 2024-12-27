/*
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    level INT NOT NULL DEFAULT 1,
    coins INT NOT NULL DEFAULT 2000,
    ab_group CHAR(1) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS event (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS partnership (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user1_id BIGINT NOT NULL,
    user2_id BIGINT NOT NULL,
    helium_collected INT NOT NULL DEFAULT 0,
    balloon_progress INT NOT NULL DEFAULT 0,
    event_id BIGINT NOT NULL,
    reward_claimed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user1_id) REFERENCES user(id),
    FOREIGN KEY (user2_id) REFERENCES user(id),
    FOREIGN KEY (event_id) REFERENCES event(id)
);

CREATE TABLE IF NOT EXISTS leaderboard (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    level INT NOT NULL,
    username VARCHAR(255) NOT NULL
    FOREIGN KEY (user_id) REFERENCES user(id)
);


CREATE TABLE IF NOT EXISTS invitation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    inviter_id BIGINT NOT NULL,
    invited_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ab_group CHAR(1) NOT NULL,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED', 'DEPRECATED') DEFAULT 'PENDING',
    FOREIGN KEY (inviter_id) REFERENCES user(id),
    FOREIGN KEY (invited_id) REFERENCES user(id),
    FOREIGN KEY (event_id) REFERENCES event(id)

);



CREATE INDEX idx_user_id ON leaderboard (user_id);
CREATE INDEX idx_level ON leaderboard (level);

*/


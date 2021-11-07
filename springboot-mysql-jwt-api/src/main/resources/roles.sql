create user 'api'@'%' identified by 'admin';
GRANT ALL PRIVILEGES ON api.* TO 'api'@'%' WITH GRANT OPTION;
SHOW GRANTS FOR 'api'@'%';
FLUSH PRIVILEGES;


CREATE TABLE IF NOT EXISTS roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS user_roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)  ENGINE=INNODB;

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_BUSINESS');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

INSERT into api.users (id, name, username, password, email) values (1,'Tom','Tom','$2a$12$t0YfjU0hI6Z29XqebzoBSuhuqdiKllYBGANniz88T8Cp2nwEwEQDS', 'tom@gmail.com'),(2,'Paul','Paul','$2a$12$DNF9pWdtz26Gy/XdW2XPtum1fdyx9W5IIu3YdbVAB4cO4bNiHLy6K','paul@gmail.com'),(3,'George','George','$2a$12$rDvppGmEuWvfdxFhiDg52OLydrecq5XiSlfpOz8FkTp184rH0QEAu','george@gmail.com'),(4,'Mathew','Mathew','$2a$12$QHW9NQK2I3iWBsNW/Qnkau6oEOdi/5T26VhNbWSQ.S4FsG7l/rzhm','mathew@gmail.com'),(5,'Ryan','Ryan','$2a$12$7903S2O4wQ1cjzVK4LEToOGQ.PjPhGWDAP12zpqEmUGBdt.Jema72','ryan@gmail.com');

INSERT into api.user_roles (id, role_id, user_id) values (1,1,1),(2,2,1),(3,1,2),(4,2,3),(5,3,4);

INSERT into api.roles (id, name) values (1,'ROLE_USER'),(2,'ROLE_BUSINESS'),(3,'ROLE_ADMIN');

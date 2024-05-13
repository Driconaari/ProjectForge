CREATE DATABASE IF NOT EXISTS pct_db2;
USE pct_db2;

DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS role;

CREATE TABLE role (
                      role_id INT NOT NULL AUTO_INCREMENT NOT NULL,
                      role VARCHAR(45) NOT NULL,
                      PRIMARY KEY (role_id)
);

CREATE TABLE user (
                      user_id INT NOT NULL AUTO_INCREMENT,
                      username VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      role_id INT NOT NULL,
                      PRIMARY KEY (user_id),
                      FOREIGN KEY (role_id) REFERENCES role (role_id),
                      UNIQUE (username)
);

CREATE TABLE project (
                         project_id INT NOT NULL AUTO_INCREMENT,
                         project_name VARCHAR(255) NOT NULL,
                         project_description VARCHAR(1000),
                         start_date DATE CHECK (start_date >= '2000-01-01' AND start_date <= '3000-12-31'),
                         end_date DATE CHECK (end_date >= '2000-01-01' AND end_date <= '3000-12-31'),
                         user_id INT NOT NULL,
                         PRIMARY KEY (project_id),
                         FOREIGN KEY (user_id) REFERENCES user (user_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE task (
                      task_id INT NOT NULL AUTO_INCREMENT,
                      task_name VARCHAR(255) NOT NULL,
                      hours DOUBLE CHECK (hours >= 0 AND hours <= 9999),
                      start_date DATE CHECK (start_date >= '2000-01-01' AND start_date <= '3000-12-31'),
                      end_date DATE CHECK (end_date >= '2000-01-01' AND end_date <= '3000-12-31'),
                      status INT NOT NULL,
                      project_id INT NOT NULL,
                      PRIMARY KEY (task_id),
                      FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE subtask (
                         subtask_id INT NOT NULL AUTO_INCREMENT,
                         subtask_name VARCHAR(255) NOT NULL,
                         hours DOUBLE CHECK (hours >= 0 AND hours <= 9999),
                         start_date DATE CHECK (start_date >= '2000-01-01' AND start_date <= '3000-12-31'),
                         end_date DATE CHECK (end_date >= '2000-01-01' AND end_date <= '3000-12-31'),
                         status INT NOT NULL,
                         task_id INT NOT NULL,
                         PRIMARY KEY (subtask_id),
                         FOREIGN KEY (task_id) REFERENCES task (task_id) ON DELETE CASCADE ON UPDATE CASCADE
);

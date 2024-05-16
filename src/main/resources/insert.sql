-- Insert into role
INSERT INTO role (role) VALUES ('USER 1');
INSERT INTO role (role) VALUES ('ADMIN 2');

-- Insert into user
INSERT INTO user (username, password, role_id) VALUES ('bob', 'bob', 1);
INSERT INTO user (username, password, role_id) VALUES ('User2', 'Password2', 2);

-- Insert into project
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project 1', 'Description 1', '2022-01-01', '2022-12-31', 1);
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project 2', 'Description 2', '2023-01-01', '2023-12-31', 2);

-- Insert into task
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task 1', 10, '2022-01-01', '2022-12-31', 1, 1);
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task 2', 20, '2023-01-01', '2023-12-31', 2, 2);

-- Insert into subtask
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('Subtask 1', 5, '2022-01-01', '2022-12-31', 1, 1);
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('Subtask 2', 10, '2023-01-01', '2023-12-31', 2, 2);
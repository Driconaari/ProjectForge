USE eksamen;

-- Insert into role
INSERT INTO role (role) VALUES ('USER 1');
INSERT INTO role (role) VALUES ('ADMIN 2');


-- Insert into user
-- after inserstion, password is 'mand'
INSERT INTO user (username, password, email, role_id) VALUES ('mand', '$2a$10$ukVU7pwssLL/zYZxVjt0CuFw98TRRBs6tR0qLRLsQZ/1VxkxChOaK', 'mand@mand.com', 2);

-- Insert into project
-- Insert more projects
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project 3', 'Description 3', '2024-01-01', '2024-05-26', 1);
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project 4', 'Description 4', '2024-02-01', '2024-05-26', 2);
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project 5', 'Description 5', '2024-03-01', '2024-05-26', 1);
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project 6', 'Description 6', '2024-04-01', '2024-05-26', 2);

-- Insert more tasks
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task 3', 30, '2024-01-01', '2024-05-26', 1, 1);
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task 4', 40, '2024-02-01', '2024-05-26', 2, 2);
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task 5', 50, '2024-03-01', '2024-05-26', 1, 1);
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task 6', 60, '2024-04-01', '2024-05-26', 2, 2);
-- Insert into subtask
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('Subtask 1', 5, '2022-01-01', '2022-12-31', 1, 1);
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('Subtask 2', 10, '2023-01-01', '2023-12-31', 2, 2);
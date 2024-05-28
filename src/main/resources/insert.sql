USE eksamen;

-- Insert into role
INSERT INTO role (role, role_name) VALUES ('USER 1', 'Role Name');
INSERT INTO role (role) VALUES ('ADMIN 2', 'Role Name');


-- Insert into user
-- after inserstion, password is 'mand'
INSERT INTO user (username, password, email, role_id) VALUES ('mand', '$2a$10$ukVU7pwssLL/zYZxVjt0CuFw98TRRBs6tR0qLRLsQZ/1VxkxChOaK', 'mand@mand.com', 2);

-- Insert more projects
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project Alpha', 'Description Alpha', '2024-01-01', '2024-05-26', 1);
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project Beta', 'Description Beta', '2024-02-01', '2024-06-26', 1);
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project Gamma', 'Description Gamma', '2024-03-01', '2024-07-26', 1);
INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('Project Delta', 'Description Delta', '2024-04-01', '2024-08-26', 1);

-- Insert more tasks
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task Alpha', 30, '2024-01-01', '2024-05-26', 1, 1);
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task Beta', 40, '2024-02-01', '2024-06-26', 2, 1);
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task Gamma', 50, '2024-03-01', '2024-07-26', 1, 1);
INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('Task Delta', 60, '2024-04-01', '2024-08-26', 2, 1);

-- Insert into subtask
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('Subtask Alpha', 5, '2022-01-01', '2022-12-31', 1, 1);
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('Subtask Beta', 10, '2023-01-01', '2023-12-31', 1, 1);
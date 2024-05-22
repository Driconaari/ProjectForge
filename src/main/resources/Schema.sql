-- Drop tables if they exist to avoid conflicts
DROP TABLE IF EXISTS IndividualResourceTracking;
DROP TABLE IF EXISTS ResourceAllocation;
DROP TABLE IF EXISTS Resources;
DROP TABLE IF EXISTS Workdays;
DROP TABLE IF EXISTS TimeConsumption;
DROP TABLE IF EXISTS Subtasks;
DROP TABLE IF EXISTS Tasks;
DROP TABLE IF EXISTS Subprojects;
DROP TABLE IF EXISTS Projects;
DROP TABLE IF EXISTS users;

SHOW TABLES;


-- Create table for users
CREATE TABLE users
(
    id       BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles    VARCHAR(255) NOT NULL,
    is_admin TINYINT      NOT NULL
);

-- Create table for projects
CREATE TABLE Projects
(
    ProjectID   INT PRIMARY KEY AUTO_INCREMENT,
    ProjectName VARCHAR(255),
    Description TEXT,
    Deadline    DATE
);

-- Create table for subprojects
CREATE TABLE Subprojects
(
    SubprojectID   INT PRIMARY KEY AUTO_INCREMENT,
    ProjectID      INT,
    SubprojectName VARCHAR(255),
    Description    TEXT,
    Deadline       DATE,
    FOREIGN KEY (ProjectID) REFERENCES Projects (ProjectID)
);

-- Create table for tasks
CREATE TABLE Tasks
(
    TaskID      INT AUTO_INCREMENT PRIMARY KEY,
    TaskName    VARCHAR(255) NOT NULL,
    Description TEXT NOT NULL,
    Deadline    DATE NOT NULL
);

-- Create table for subtasks
CREATE TABLE Subtasks
(
    SubtaskID           INT PRIMARY KEY AUTO_INCREMENT,
    TaskID              INT,
    SubtaskName         VARCHAR(255),
    Description         TEXT,
    Duration            INT,          -- Duration in days or hours
    ResourceRequirement VARCHAR(255), -- Resource needed for subtask
    Deadline            DATE,
    FOREIGN KEY (TaskID) REFERENCES Tasks (TaskID)
);

-- Create table for time consumption
CREATE TABLE TimeConsumption
(
    TimeID    INT PRIMARY KEY AUTO_INCREMENT,
    TaskID    INT,
    SubtaskID INT,
    TimeSpent INT, -- Time spent on task/subtask
    Date      DATE,
    FOREIGN KEY (TaskID) REFERENCES Tasks (TaskID),
    FOREIGN KEY (SubtaskID) REFERENCES Subtasks (SubtaskID)
);

-- Create table for user-defined workdays
CREATE TABLE Workdays
(
    WorkdayID   INT PRIMARY KEY AUTO_INCREMENT,
    UserID      INT, -- If tracking workdays for individual users
    Date        DATE,
    HoursWorked INT
);

-- Create table for resources
CREATE TABLE Resources
(
    ResourceID      INT PRIMARY KEY AUTO_INCREMENT,
    ResourceName    VARCHAR(255),
    CompetencyLevel VARCHAR(50) -- Beginner, Intermediate, Advanced
);

-- Create table for resource allocation
CREATE TABLE ResourceAllocation
(
    AllocationID   INT PRIMARY KEY AUTO_INCREMENT,
    TaskID         INT,
    ResourceID     INT,
    HoursAllocated INT,
    FOREIGN KEY (TaskID) REFERENCES Tasks (TaskID),
    FOREIGN KEY (ResourceID) REFERENCES Resources (ResourceID)
);

-- Create table for individual resource tracking
CREATE TABLE IndividualResourceTracking
(
    TrackingID  INT PRIMARY KEY AUTO_INCREMENT,
    ResourceID  INT,
    TaskID      INT,
    Date        DATE,
    HoursWorked INT,
    FOREIGN KEY (ResourceID) REFERENCES Resources (ResourceID),
    FOREIGN KEY (TaskID) REFERENCES Tasks (TaskID)
);

INSERT INTO users (username, email, password, roles, is_admin)
VALUES ('sophus', 'test', 'sophus', 'ADMIN', 1);

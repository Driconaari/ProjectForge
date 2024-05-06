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
    ProjectID   INT PRIMARY KEY,
    ProjectName VARCHAR(255),
    Description TEXT,
    Deadline    DATE
);

-- Create table for subprojects
CREATE TABLE Subprojects
(
    SubprojectID   INT PRIMARY KEY,
    ProjectID      INT,
    SubprojectName VARCHAR(255),
    Description    TEXT,
    Deadline       DATE,
    FOREIGN KEY (ProjectID) REFERENCES Projects (ProjectID)
);

-- Create table for tasks
CREATE TABLE Tasks
(
    TaskID              INT PRIMARY KEY,
    SubprojectID        INT,
    TaskName            VARCHAR(255),
    Description         TEXT,
    Duration            INT,          -- Duration in days or hours
    ResourceRequirement VARCHAR(255), -- Resource needed for task
    Deadline            DATE,
    FOREIGN KEY (SubprojectID) REFERENCES Subprojects (SubprojectID)
);

-- Create table for subtasks
CREATE TABLE Subtasks
(
    SubtaskID           INT PRIMARY KEY,
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
    TimeID    INT PRIMARY KEY,
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
    WorkdayID   INT PRIMARY KEY,
    UserID      INT, -- If tracking workdays for individual users
    Date        DATE,
    HoursWorked INT
);

-- Create table for resources
CREATE TABLE Resources
(
    ResourceID      INT PRIMARY KEY,
    ResourceName    VARCHAR(255),
    CompetencyLevel VARCHAR(50) -- Beginner, Intermediate, Advanced
);

-- Create table for resource allocation
CREATE TABLE ResourceAllocation
(
    AllocationID   INT PRIMARY KEY,
    TaskID         INT,
    ResourceID     INT,
    HoursAllocated INT,
    FOREIGN KEY (TaskID) REFERENCES Tasks (TaskID),
    FOREIGN KEY (ResourceID) REFERENCES Resources (ResourceID)
);

-- Create table for individual resource tracking
CREATE TABLE IndividualResourceTracking
(
    TrackingID  INT PRIMARY KEY,
    ResourceID  INT,
    TaskID      INT,
    Date        DATE,
    HoursWorked INT,
    FOREIGN KEY (ResourceID) REFERENCES Resources (ResourceID),
    FOREIGN KEY (TaskID) REFERENCES Tasks (TaskID)
);

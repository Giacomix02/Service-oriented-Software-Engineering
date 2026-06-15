-- Create the Artist table first since Song depends on it
CREATE TABLE Artist (
                        ID INT PRIMARY KEY,
                        Name VARCHAR(255),
                        Description TEXT
);

-- Create the Song table with a foreign key referencing Artist
CREATE TABLE Song (
                      ID INT PRIMARY KEY,
                      Name VARCHAR(255),
                      Description TEXT,
                      Views INT,
                      ID_Artist INT,
                      FOREIGN KEY (ID_Artist) REFERENCES Artist(ID)
);
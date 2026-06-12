CREATE TABLE Event (
                       ID INT PRIMARY KEY,
                       Event_Global_ID INT,
                       Name VARCHAR(255),
                       "Artist Name" VARCHAR(255),
                       Location VARCHAR(255),
                       Description TEXT
);


CREATE TABLE Ticket (
                        ID INT PRIMARY KEY,
                        Price FLOAT,
                        Seat VARCHAR(50),
                        Event_ID INT,
                        FOREIGN KEY (Event_ID) REFERENCES Event(ID)
);
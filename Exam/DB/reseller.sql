CREATE DATABASE IF NOT EXISTS reseller;
USE reseller;

CREATE TABLE Event (
                       ID INT PRIMARY KEY,
                       Event_Global_ID INT,
                       Name VARCHAR(255),
                       Artist_Name VARCHAR(255),
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

INSERT INTO Event (ID, Event_Global_ID, Name, Artist_Name, Location, Description) VALUES
                                                                                      (1, 9001, 'The Midnight Echo Live in London', 'The Midnight Echo', 'O2 Academy, London', 'Intimate live performance featuring new tracks.'),
                                                                                      (2, 9002, 'Aura World Tour - NYC', 'Aura', 'Madison Square Garden, NY', 'First stop of the massive global stadium tour.'),
                                                                                      (3, 9003, 'Aura World Tour - LA', 'Aura', 'SoFi Stadium, LA', 'West coast stop of the global stadium tour.'),
                                                                                      (4, 9004, 'EDM Festival 2026', 'DJ Vertex', 'Tomorrowland Mainstage', 'Closing set for the festival.');


INSERT INTO Ticket (ID, Price, Seat, Event_ID) VALUES
                                                   (10, 60.00, 'GA-Standing', 1),
                                                   (11, 80.00, 'Balcony-A1', 1),
                                                   (12, 200.00, 'Sec 101, Row A, Seat 1', 2),
                                                   (13, 200.00, 'Sec 101, Row A, Seat 2', 2),
                                                   (14, 600.00, 'VIP-Pit-1', 2),
                                                   (15, 150.00, 'Sec 205, Row C, Seat 15', 3),
                                                   (16, 350.00, '3-Day-Pass-Wristband', 4);
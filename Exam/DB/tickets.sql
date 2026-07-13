CREATE DATABASE IF NOT EXISTS tickets;
USE tickets;

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



-- Insert Events
-- Note: Using double quotes for "Artist Name" to match your schema definition
INSERT INTO Event (ID, Event_Global_ID, Name, Artist_Name, Location, Description) VALUES
(1, 9001, 'The Midnight Echo Live in London', 'The Midnight Echo', 'O2 Academy, London', 'Intimate live performance featuring new tracks.'),
(2, 9002, 'Aura World Tour - NYC', 'Aura', 'Madison Square Garden, NY', 'First stop of the massive global stadium tour.'),
(3, 9003, 'Aura World Tour - LA', 'Aura', 'SoFi Stadium, LA', 'West coast stop of the global stadium tour.'),
(4, 9004, 'EDM Festival 2026', 'DJ Vertex', 'Tomorrowland Mainstage', 'Closing set for the festival.');

-- Insert Tickets (Event_ID references Event.ID)
INSERT INTO Ticket (ID, Price, Seat, Event_ID) VALUES
-- Tickets for Event 1 (The Midnight Echo)
(1, 45.00, 'GA-Standing', 1),
(2, 45.00, 'GA-Standing', 1),
(3, 65.00, 'Balcony-A1', 1),

-- Tickets for Event 2 (Aura in NYC)
(4, 150.00, 'Sec 101, Row A, Seat 1', 2),
(5, 150.00, 'Sec 101, Row A, Seat 2', 2),
(6, 450.00, 'VIP-Pit-1', 2),

-- Tickets for Event 3 (Aura in LA)
(7, 125.00, 'Sec 205, Row C, Seat 15', 3),
(8, 125.00, 'Sec 205, Row C, Seat 16', 3),

-- Tickets for Event 4 (DJ Vertex)
(9, 300.00, '3-Day-Pass-Wristband', 4);
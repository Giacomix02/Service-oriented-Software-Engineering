CREATE TABLE Streaming_Service (
                                   ID INT PRIMARY KEY,
                                   Name VARCHAR(255) NOT NULL,
                                   Description TEXT
);

CREATE TABLE Song (
                      ID INT PRIMARY KEY
);


CREATE TABLE Aviability (
                            ID_Song INT,
                            ID_Streaming_Service INT,

                            PRIMARY KEY (ID_Song, ID_Streaming_Service),

                            FOREIGN KEY (ID_Song) REFERENCES Song(ID) ON DELETE CASCADE,
                            FOREIGN KEY (ID_Streaming_Service) REFERENCES Streaming_Service(ID) ON DELETE CASCADE
);
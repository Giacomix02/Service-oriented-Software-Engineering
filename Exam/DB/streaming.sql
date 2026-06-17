CREATE DATABASE IF NOT EXISTS StreamingAvailability;
USE StreamingAvailability;

CREATE TABLE streaming_service(
                                   id INT PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   description TEXT,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE song(
                      id INT PRIMARY KEY,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE availability(
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              id_song INT,
                              id_streaming_service INT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                              FOREIGN KEY (id_song) REFERENCES song(id) ON DELETE CASCADE,
                              FOREIGN KEY (id_streaming_service) REFERENCES streaming_service(ID) ON DELETE CASCADE
);



-- Insert Streaming Services
INSERT INTO streaming_service (id, name, description) VALUES
                                                          (1, 'Spotify', 'Leading global audio streaming subscription service.'),
                                                          (2, 'Apple Music', 'Premium ad-free audio and video streaming service.'),
                                                          (3, 'Tidal', 'High-fidelity music streaming platform.');

-- Insert Songs (IDs must match the ones from the Music Stats DB)
INSERT INTO song (id) VALUES
                          (101),
                          (102),
                          (201),
                          (202),
                          (301);

-- Insert Availability (Junction table mapping Songs to Streaming Services)
INSERT INTO availability (id_song, id_streaming_service) VALUES
                                                           (101, 1),
                                                           (101, 2),
                                                           (102, 1),
                                                           (201, 1),
                                                           (201, 2),
                                                           (201, 3),
                                                           (202, 1),
                                                           (202, 2),
                                                           (301, 1),
                                                           (301, 3);
CREATE DATABASE IF NOT EXISTS MusicStats;
USE MusicStats;


-- Create the Artist table first since Song depends on it
CREATE TABLE artist (
                        id INT PRIMARY KEY,
                        name VARCHAR(255),
                        description TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the Song table with a foreign key referencing Artist
CREATE TABLE song (
                      id INT PRIMARY KEY,
                      name VARCHAR(255),
                      description TEXT,
                      views INT,
                      artist_id INT,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (artist_id) REFERENCES artist(id)
);


-- Insert Artists
INSERT INTO artist (id, name, description)VALUES
                                               (1, 'The Midnight Echo', 'Indie rock band known for atmospheric live shows.'),
                                               (2, 'Aura', 'Global pop sensation and chart-topper.'),
                                               (3, 'DJ Vertex', 'Electronic music producer and DJ.');

-- Insert Songs (ID_Artist references Artist.ID)
INSERT INTO song (id, name, description, views, artist_id) VALUES
                                                               (101, 'Neon Skies', 'Lead single from their sophomore album.', 1500000, 1),
                                                               (102, 'Fading Light', 'A slow, acoustic ballad.', 850000, 1),
                                                               (201, 'Glass Heart', 'Viral hit of the summer.', 45000000, 2),
                                                               (202, 'Running', 'Upbeat dance track.', 32000000, 2),
                                                               (301, 'Bass Drop 3000', 'Festival anthem.', 5000000, 3);
-- Create the train table
CREATE TABLE IF NOT EXISTS train (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    train_name VARCHAR(255) NOT NULL,
    section_a_capacity INT NOT NULL,
    section_b_capacity INT NOT NULL,
    available_seats_sectiona INT NOT NULL,
    available_seats_sectionb INT NOT NULL
);

-- Create the ticket table
CREATE TABLE IF NOT EXISTS ticket (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    source VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    section CHAR(1) NOT NULL,
    train_id BIGINT NOT NULL,
    price DOUBLE NOT NULL DEFAULT 20.0,
    CONSTRAINT fk_train FOREIGN KEY (train_id) REFERENCES train(id)
);

-- Insert initial trains
-- Insert initial trains
INSERT INTO train (train_name, section_a_capacity, section_b_capacity, available_seats_sectiona, available_seats_sectionb) VALUES
('Express Line', 40, 40, 40, 40);

INSERT INTO train (train_name, section_a_capacity, section_b_capacity, available_seats_sectiona, available_seats_sectionb) VALUES
('Regional Line', 40, 40, 40, 40);

-- Insert initial tickets
-- Assuming Train ID 1 for "Express Line" and Train ID 2 for "Regional Line"
INSERT INTO ticket (first_name, last_name, email, source, destination, section, train_id, price) VALUES
('John', 'Doe', 'john.doe@example.com', 'City A', 'City B', 'A', 1, 20.0);

INSERT INTO ticket (first_name, last_name, email, source, destination, section, train_id, price) VALUES
('Jane', 'Smith', 'jane.smith@example.com', 'City C', 'City D', 'B', 1, 20.0);

INSERT INTO ticket (first_name, last_name, email, source, destination, section, train_id, price) VALUES
('Alice', 'Johnson', 'alice.johnson@example.com', 'City E', 'City F', 'A', 2, 20.0);

INSERT INTO ticket (first_name, last_name, email, source, destination, section, train_id, price) VALUES
('Bob', 'Brown', 'bob.brown@example.com', 'City G', 'City H', 'B', 2, 20.0);
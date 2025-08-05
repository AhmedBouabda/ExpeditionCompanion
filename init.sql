-- Expedition Companion Database Initialization Script
-- This script sets up the initial database structure

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS expedition_companion;
USE expedition_companion;

-- Grant permissions to the application user
GRANT ALL PRIVILEGES ON expedition_companion.* TO 'expedition_user'@'%';
FLUSH PRIVILEGES;

-- Sample data for testing (optional)
-- Note: Actual tables will be created by JPA/Hibernate when the application starts

-- Create a test table to verify database connectivity
CREATE TABLE IF NOT EXISTS database_test (
    id INT AUTO_INCREMENT PRIMARY KEY,
    test_message VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert test data
INSERT INTO database_test (test_message) VALUES ('Database initialization successful');

-- Future: Add initial data for expeditions, equipment categories, etc.
-- These will be uncommented and expanded as the application develops

/*
-- Sample expedition statuses
INSERT INTO expedition_status (name, description) VALUES 
('planned', 'Expedition is in planning phase'),
('active', 'Expedition is currently ongoing'),
('completed', 'Expedition has been completed'),
('cancelled', 'Expedition has been cancelled');

-- Sample equipment categories
INSERT INTO equipment_category (name, description) VALUES 
('safety', 'Safety equipment and gear'),
('navigation', 'Navigation tools and instruments'),
('shelter', 'Tents, sleeping bags, and shelter equipment'),
('clothing', 'Expedition clothing and footwear'),
('food', 'Food supplies and cooking equipment'),
('medical', 'Medical supplies and first aid equipment');

-- Sample difficulty levels
INSERT INTO difficulty_level (name, description, risk_level) VALUES 
('easy', 'Suitable for beginners', 1),
('moderate', 'Requires some experience', 2),
('challenging', 'Requires significant experience', 3),
('extreme', 'For expert adventurers only', 4);
*/
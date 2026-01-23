-- Organization Table Schema
CREATE TABLE IF NOT EXISTS organization (
    org_id INT AUTO_INCREMENT PRIMARY KEY,
    org_name VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Index for faster lookup
CREATE INDEX idx_org_name ON organization(org_name);

-- Sample data (optional)
-- INSERT INTO organization (org_name, address, phone, email) 
-- VALUES ('Sample Organization', '123 Main Street', '1234567890', 'sample@example.com');

-- Update database schema for product transaction logging

-- Create product_transaction table if it doesn't exist
CREATE TABLE IF NOT EXISTS product_transaction (
    product_id INT NOT NULL,
    transaction_id INT NOT NULL,
    quantity INT NOT NULL,
    price_at_transaction DECIMAL(10, 2) NULL,
    PRIMARY KEY (product_id, transaction_id),
    CONSTRAINT product_transaction_ibfk_1
        FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    CONSTRAINT product_transaction_ibfk_2
        FOREIGN KEY (transaction_id) REFERENCES transaction (id) ON DELETE CASCADE
);

-- Create index on transaction_id for better query performance
CREATE INDEX IF NOT EXISTS transaction_id ON product_transaction (transaction_id);

-- If you have an old producttransaction table, you can migrate data and drop it
-- First, check if the old table exists and migrate data if needed:
-- INSERT INTO product_transaction (product_id, transaction_id, quantity, price_at_transaction)
-- SELECT product_id, transaction_id, quantity, price_at_transaction 
-- FROM producttransaction 
-- WHERE NOT EXISTS (
--     SELECT 1 FROM product_transaction pt 
--     WHERE pt.product_id = producttransaction.product_id 
--     AND pt.transaction_id = producttransaction.transaction_id
-- );

-- Then drop the old table:
-- DROP TABLE IF EXISTS producttransaction;

-- Ensure position table exists (if not already created)
CREATE TABLE IF NOT EXISTS position (
    id INT AUTO_INCREMENT PRIMARY KEY,
    shelves VARCHAR(50) NULL,
    row_and_column VARCHAR(50) NULL
);

-- Ensure transaction table exists with correct structure
CREATE TABLE IF NOT EXISTS transaction (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_number VARCHAR(100) NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL,
    total_amount DECIMAL(12, 2) NOT NULL,
    transaction_type ENUM('IMPORT', 'EXPORT') NOT NULL,
    CONSTRAINT transaction_number UNIQUE (transaction_number)
); 
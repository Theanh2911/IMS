-- Create position table
CREATE TABLE IF NOT EXISTS `position` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `shelves` VARCHAR(50),
    `row_and_column` VARCHAR(50),
    UNIQUE KEY `unique_position` (`shelves`, `row_and_column`)
);

-- Create product_position table for many-to-many relationship
CREATE TABLE IF NOT EXISTS `product_position` (
    `product_id` INT NOT NULL,
    `position_id` INT NOT NULL,
    `quantity_at_position` INT DEFAULT 0,
    PRIMARY KEY (`product_id`, `position_id`),
    FOREIGN KEY (`product_id`) REFERENCES `product`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`position_id`) REFERENCES `position`(`id`) ON DELETE CASCADE
);

-- Add foreign key constraint to product table for main position
ALTER TABLE `product` 
ADD CONSTRAINT `fk_product_location`
FOREIGN KEY (`position_id`) REFERENCES `position`(`id`) ON DELETE SET NULL;

-- Insert some sample positions
INSERT IGNORE INTO `position` (`shelves`, `row_and_column`) VALUES
('A', 'R1-C1'),
('A', 'R1-C2'),
('A', 'R1-C3'),
('A', 'R2-C1'),
('A', 'R2-C2'),
('B', 'R1-C1'),
('B', 'R1-C2'),
('B', 'R2-C1'),
('C', 'R1-C1'),
('C', 'R1-C2'); 
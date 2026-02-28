-- Seed Categories (hierarchical)
INSERT INTO categories (name, description, parent_id) VALUES
('Electronics', 'Electronic products', NULL),
('Clothing', 'Apparel and fashion', NULL),
('Books', 'Printed and digital books', NULL),
('Mobile Phones', 'Smartphones and accessories', 1),
('Laptops', 'Laptops and accessories', 1),
('T-Shirts', 'Casual t-shirts', 2),
('Fiction', 'Fiction books', 3);

-- Seed Products
INSERT INTO products (name, description, price, stock_quantity, category_id, sku, is_active) VALUES
('iPhone 15 Pro', 'Latest iPhone with A17 Pro chip', 999.99, 50, 4, 'IPH15PRO001', true),
('MacBook Pro 14"', 'M3 Pro chip, 16GB RAM', 1999.99, 25, 5, 'MBP14M3PRO001', true),
('Cotton T-Shirt', '100% cotton casual t-shirt', 19.99, 200, 6, 'TSHIRT001', true),
('iPhone Case', 'Protective case for iPhone 15', 29.99, 100, 4, 'IPHCASE001', true),
('Python Programming', 'Complete Python guide', 39.99, 150, 7, 'BOOKPY001', true);

-- Seed Users
INSERT INTO users (username, email, password, first_name, last_name, phone, role) VALUES
('john_doe', 'john@example.com', '$2a$10$demo', 'John', 'Doe', '+1234567890', 'CUSTOMER'),
('admin_user', 'admin@ecommerce.com', '$2a$10$demo', 'Admin', 'User', '+0987654321', 'ADMIN');

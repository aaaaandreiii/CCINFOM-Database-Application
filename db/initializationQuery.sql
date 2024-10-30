-- Table 1: Customer Information
CREATE TABLE IF NOT EXISTS Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    phone_number VARCHAR(20),
    delivery_address TEXT,
    customer_rating DECIMAL(2, 1) CHECK (customer_rating BETWEEN 0 AND 5)
);

-- Table 6: Item Entities
CREATE TABLE IF NOT EXISTS Item (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    item_category VARCHAR(50),
    srp DECIMAL(10, 2),
    brand VARCHAR(50),
    description TEXT
);

-- Table 7: Supplier Information
CREATE TABLE IF NOT EXISTS Supplier (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_fname VARCHAR(50),
    supplier_lname VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    address TEXT,
    supplier_rating DECIMAL(2, 1) CHECK (supplier_rating BETWEEN 0 AND 5)
);

-- Table 2: Shopping Cart
CREATE TABLE IF NOT EXISTS ShoppingCart (
    shoppingcart_entry_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    item_id INT,
    quantity INT,
    supplier_id INT,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (item_id) REFERENCES Item(item_id),
    FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
);

-- Table 3: Wishlist
CREATE TABLE IF NOT EXISTS Wishlist (
    wishlist_entry_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    item_id INT,
    supplier_id INT,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (item_id) REFERENCES Item(item_id),
    FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
);

-- Table 4: Buyer Order Information
CREATE TABLE IF NOT EXISTS OrderInfo (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date DATE,
    supplier_id INT,
    total_amount DECIMAL(10, 2),
    status ENUM('Pending', 'Completed', 'Cancelled', 'Returned'),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id),
    FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
);

-- Table 5: Order Items
CREATE TABLE IF NOT EXISTS OrderItem (
    order_detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    item_id INT,
    quantity INT,
    price_at_order DECIMAL(10, 2),
    FOREIGN KEY (order_id) REFERENCES OrderInfo(order_id),
    FOREIGN KEY (item_id) REFERENCES Item(item_id)
);

-- Table 8: Supplier Inventory
CREATE TABLE IF NOT EXISTS Inventory (
    inventory_entry_id INT AUTO_INCREMENT PRIMARY KEY,
    item_id INT,
    supplier_id INT,
    quantity INT,
    FOREIGN KEY (item_id) REFERENCES Item(item_id),
    FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
);

INSERT INTO Customer (first_name, last_name, email, phone_number, delivery_address, customer_rating)
VALUES
('John', 'Doe', 'johndoe@example.com', '123-456-7890', '123 Main St', 4.5),
('Jane', 'Smith', 'janesmith@example.com', '987-654-3210', '456 Elm St', 3.8);

INSERT INTO Item (name, item_category, srp, brand, description)
VALUES
('Laptop', 'Electronics', 899.99, 'Acer', 'Powerful laptop for work and play'),
('Smartphone', 'Electronics', 599.99, 'Samsung', 'Latest smartphone with advanced features'),
('Jeans', 'Clothing', 49.99, 'Levis', 'Classic denim jeans');

INSERT INTO Supplier (supplier_fname, supplier_lname, email, phone, address, supplier_rating)
VALUES
('Alice', 'Johnson', 'alicejohnson@example.com', '555-123-4567', '789 Oak St', 4.2),
('Bob', 'Williams', 'bobwilliams@example.com', '555-987-6543', '321 Pine St', 3.9);

INSERT INTO ShoppingCart (customer_id, item_id, quantity, supplier_id)
VALUES
(1, 1, 2, 1),
(2, 2, 1, 2);

INSERT INTO Wishlist (customer_id, item_id, supplier_id)
VALUES
(1, 3, 1),
(2, 1, 2);

INSERT INTO OrderInfo (customer_id, order_date, supplier_id, total_amount, status)
VALUES
(1, '2023-11-22', 1, 1799.98, 'Completed'),
(2, '2023-12-01', 2, 599.99, 'Pending');

INSERT INTO OrderItem (order_id, item_id, quantity, price_at_order)
VALUES
(1, 1, 2, 899.99),
(2, 2, 1, 599.99);

INSERT INTO Inventory (item_id, supplier_id, quantity)
VALUES
(1, 1, 100),
(2, 2, 50),
(3, 1, 200);


SELECT * FROM customer;
SELECT * FROM inventory;
SELECT * FROM item;
SELECT * FROM orderinfo;
SELECT * FROM orderitem;
SELECT * FROM shoppingcart;
SELECT * FROM supplier;
SELECT * FROM wishlist;
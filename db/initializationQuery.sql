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
('John', 'Doe', 'johndoe@gmail.com', '123-456-7890', '123 Main St', 4.5),
('Miles','Morales','mmorales@stark.com','847-712-1683','Stark Tower','4.8'),
('Jane', 'Smith', 'janesmith@yahoo.com', '987-654-3210', '456 Elm St', 3.8),
('Jason','Todd','redhood@wayne.com','410-608-8293','Wayne Manor','0.9'),
('Adam','Smasher','adamsmasher@arasaka.com','493-936-9846','1231 Arasaka Tower','0.2'),
('Lucyna','Kushinada','lucyk@arasaka.com','703-008-2742','96 District Tower','4.8'),
('Sunday','Oak','sundayoak@dreamscape.com','777-777-7777','197 The Reverie','1.7'),
('Otto','Octavius','ottooctavius@oscorp.com','888-888-8888','72 Oscorp Research Center','5.0'),
('Eddie','Brock','ebrock@dailybugle.com','348-916-6398','58 Bugle Building','2.3'),
('Bruce','Wayne','notbatman@wayne.com','847-847-8475','Wayne Manor','5.0'),
('Scott','Lang','slang@stark.com','729-913-1070','Stark Tower','3.1'),
('Welt','Yang','weltyang@astral.com','100-000-0000','Astral Express','2.9'),
('Raiden','Mei','acheron@honkai.com','000-468-9462','13 Epsilon Avenue','1.0'),
('Bronya','Rand','bronyarand@belobog.com','794-519-5296','Architect Hall','3.5'),
('Lynx','Landau','lynxlandau@belobog.com','562-497-1435','Snowhill Plains','2.0'),
('Gepard','Landau','gepardlandau@silvermane.com','820-957-0818','Flats Outpost','2.6'),
('Quentin','Beck','qbeck@stark.com','028-173-0371','Stark Tower','1.3'),
('Serval','Landau','servalll@belobog.com','057-179-3975','Neverwinter','3.6'),
('Luka','Strongarm','lukaapowa@belobog.com','572-158-9076','Fight Club','2.3'),
('Leon','Kennedy','leonkennedy@rcpd.com','769-297-1302','19 Raccoon St.','3.5'),
('Gwen','Stacy','gwenstacy@oscorp.com','451-995-2638','29 Oscorp Research Center','3.9'),
('Pelageya','Sergeyevna','pelasergey@silvermane.com','889-048-1468','Flats Outpost','4.9'),
('Sampo','Koski','sampoo@maskedfool.com','444-018-6529','Snowhill Plains','0.6'),
('Eren','Yeager','erenyeager@paradis.com','011-978-5270','42 Maria Wall','0.3'),
('Peter','Parker','pparker@dailybugle.com','675-294-0237','58 Bugle Building','3.0'),
('Ken','Kaneki','kkaneki@anteiku.com','100-700-1000','17th Ward Dormitory','1.9'),
('David','Martinez','davidmartinez@netrun.com','957-295-6391','96 District Tower','4.2'),
('Kishou','Arima','karima@ccg.com','562-075-0819','1966 CCG Headquarters','3.4'),
('Dan','Heng','danheng@astral.com','795-539-1973','Astral Express','4.0'),
('Curtis','Connors','curtisconnors@oscorp.com','623-946-4562','31 Oscorp Research Center','4.4'),
('Franz','Kafka','franzkafka@yahoo.com','363-057-1479','42 Kierling St.','3.3'),
('Osamu','Dazai','osamudazai@yahoo.com','791-429-9741','94 Kanagi District','0.7'),
('Dick','Grayson','nightwing@wayne.com','204-297-1497','Wayne Manor','1.5'),
('Clark','Kent','ckent@dailyplanet.com','509-320-4298','1320 Daily Planet','4.6'),
('Barry','Allen','theflash@jump.com','555-222-5555','77 Jump St.','5.0'),
('Himeko','Murata','himekomurata@astral.com','412-530-7639','Astral Express','3.7'),
('John','Constantine','jcon@myspace.com','130-666-1398','60 Baptist St.','0.6'),
('Luke','Skywalker','lukeskywalker@gmail.com','420-108-5379','17 Tatooine Drive','1.0'),
('John Jonah','Jameson','jjonahjameson@dailybugle.com','719-364-8356','58 Bugle Building','1.1'),
('Anakin','Skywalker','vader@empire.com','428-420-1968','89 Death Star','0.0'),
('Michael','Afton','purpleguy@freddys.com','429-083-2144','67 Freddy Pizzeria','0.5'),
('Rick','Sanchez','wubbalubba@dubdub.com','891-230-1089','22 Orchard Drive','4.0'),
('Walter','White','cook@albuquerque.com','429-407-1197','308 Negra Arroyo Lane','2.8'),
('Van','Hellsing','vhellsing@myspace.com','149-938-0489','Roman Avenue','3.1'),
('Mary','Jane','mjane@dailybugle.com','618-957-9372','58 Bugle Building','3.4'),
('Tony','Stark','tonystark@stark.com','902-178--0283','Stark Tower','5.0'),
('Ada','Wong','adawong@raccoon.com','893-319-9479','19 Raccoon St.','3.9'),
('Suguru','Geto','monkey@jjk.com','420-104-8958','Jujutsu Technical College','1.0'),
('Dokja','Kim','reader@orv.com','049-051-4951','Seoul Dome','4.2'),
('Satoru','Gojo','purple@jjk.com','410-050-5050','Jujutsu Technical College','4.4');

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

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
('RG RX-78-2 Gundam', 1, 29.99, 'Bandai', 'Real Grade model of the classic RX-78-2 Gundam.'),
('HGUC Zaku II', 1, 19.99, 'Bandai', 'High Grade model of the iconic Zaku II.'),
('MG RX-0 Unicorn Gundam', 1, 74.99, 'Bandai', 'Master Grade Unicorn Gundam with transformation feature.'),
('PG 00 Raiser', 1, 199.99, 'Bandai', 'Perfect Grade model of 00 Raiser with detailed components.'),
('HG Wing Gundam', 1, 25.99, 'Bandai', 'High Grade model of the Wing Gundam.'),
('MG Exia', 1, 64.99, 'Bandai', 'Master Grade Exia model with multiple weapons.'),
('HG Aile Strike Gundam', 1, 22.99, 'Bandai', 'High Grade Aile Strike Gundam model.'),
('RG Sazabi', 1, 34.99, 'Bandai', 'Real Grade model of Sazabi.'),
('HG GM Sniper II', 1, 27.99, 'Bandai', 'High Grade model of GM Sniper II.'),
('MG Sinanju', 1, 89.99, 'Bandai', 'Master Grade Sinanju model with intricate design.'),
('HG RX-78-2 Gundam (Revive)', 1, 19.99, 'Bandai', 'Updated High Grade model of RX-78-2.'),
('HG Dom', 1, 24.99, 'Bandai', 'High Grade model of the Dom.'),
('MG Tallgeese', 1, 54.99, 'Bandai', 'Master Grade Tallgeese model from Gundam Wing.'),
('HG Barbatos', 1, 22.99, 'Bandai', 'High Grade model of Barbatos from Iron-Blooded Orphans.'),
('MG Gundam AGE-1', 1, 59.99, 'Bandai', 'Master Grade Gundam AGE-1 with detailed mechanics.'),
('Pikachu (Base Set)', 2, 5.99, 'The Pokémon Company', 'Classic Pikachu card from Base Set.'),
('Charizard (Base Set)', 2, 299.99, 'The Pokémon Company', 'Rare Charizard card from Base Set.'),
('Mewtwo (Base Set)', 2, 24.99, 'The Pokémon Company', 'Powerful Mewtwo card from Base Set.'),
('Eevee (Evolutions)', 2, 9.99, 'The Pokémon Company', 'Eevee card with multiple evolutions.'),
('Gyarados (Shiny)', 2, 49.99, 'The Pokémon Company', 'Shiny Gyarados card from XY series.'),
('Lucario (XY)', 2, 14.99, 'The Pokémon Company', 'Lucario card from XY series.'),
('Dragonite (Base Set)', 2, 19.99, 'The Pokémon Company', 'Dragonite card from Base Set.'),
('Greninja (XY)', 2, 12.99, 'The Pokémon Company', 'Greninja card with unique abilities.'),
('Zygarde (XY)', 2, 8.99, 'The Pokémon Company', 'Zygarde card from XY series.'),
('Umbreon (Evolutions)', 2, 39.99, 'The Pokémon Company', 'Rare Umbreon card from Evolutions.'),
('Alakazam (Base Set)', 2, 29.99, 'The Pokémon Company', 'Powerful psychic Pokémon card.'),
('Snorlax (Base Set)', 2, 24.99, 'The Pokémon Company', 'Classic Snorlax card from Base Set.'),
('Blastoise (Base Set)', 2, 89.99, 'The Pokémon Company', 'Iconic Blastoise card with high defense.'),
('Venusaur (Base Set)', 2, 49.99, 'The Pokémon Company', 'Strong Grass-type Pokémon card.'),
('Raichu (Base Set)', 2, 19.99, 'The Pokémon Company', 'Electric-type evolution of Pikachu.'),
('Hirono Doll - Red', 3, 14.99, 'Hirono', 'Cute red Hirono plush doll.'),
('Hirono Doll - Blue', 3, 14.99, 'Hirono', 'Adorable blue Hirono plush doll.'),
('Hirono Keychain', 3, 6.99, 'Hirono', 'Hirono themed keychain.'),
('Hirono Cup', 3, 12.99, 'Hirono', 'Hirono themed cup for drinks.'),
('Hirono T-shirt', 3, 19.99, 'Hirono', 'T-shirt featuring Hirono design.'),
('Hirono Hat', 3, 18.99, 'Hirono', 'Stylish Hirono cap.'),
('Hirono Backpack', 3, 29.99, 'Hirono', 'Backpack with Hirono design.'),
('Hirono Notebook', 3, 9.99, 'Hirono', 'Notebook featuring Hirono artwork.'),
('Hirono Stickers', 3, 4.99, 'Hirono', 'Set of Hirono stickers.'),
('Hirono Mug', 3, 10.99, 'Hirono', 'Mug with Hirono graphic.'),
('Labubu Plush - Pink', 4, 15.99, 'Labubu', 'Soft pink Labubu plush toy.'),
('Labubu Plush - Blue', 4, 15.99, 'Labubu', 'Soft blue Labubu plush toy.'),
('Labubu Figure', 4, 29.99, 'Labubu', 'Collectible Labubu figure.'),
('Labubu Tote Bag', 4, 19.99, 'Labubu', 'Tote bag featuring Labubu design.'),
('Labubu Pencil Case', 4, 8.99, 'Labubu', 'Pencil case with Labubu theme.'),
('Labubu Phone Case', 4, 12.99, 'Labubu', 'Phone case with Labubu graphics.'),
('Labubu Wall Art', 4, 24.99, 'Labubu', 'Art print featuring Labubu.'),
('Labubu Keychain', 4, 6.99, 'Labubu', 'Labubu themed keychain.'),
('Labubu Sticker Set', 4, 5.99, 'Labubu', 'Set of stickers featuring Labubu.'),
('Labubu Cap', 4, 18.99, 'Labubu', 'Cap with Labubu design.'),
('Sonny Angel - Series 1', 5, 9.99, 'Sonny Angels', 'Collectible figure from Series 1.'),
('Sonny Angel - Animal Series', 5, 12.99, 'Sonny Angels', 'Animal themed Sonny Angel figure.'),
('Sonny Angel - Garden Series', 5, 12.99, 'Sonny Angels', 'Garden themed Sonny Angel figure.'),
('Sonny Angel - Seasonal Series', 5, 12.99, 'Sonny Angels', 'Seasonal themed Sonny Angel figure.'),
('Sonny Angel - Rare Edition', 5, 19.99, 'Sonny Angels', 'Limited edition rare Sonny Angel.'),
('Sonny Angel - Superhero Series', 5, 14.99, 'Sonny Angels', 'Superhero themed Sonny Angel figure.'),
('Sonny Angel - Sports Series', 5, 14.99, 'Sonny Angels', 'Sports themed Sonny Angel figure.'),
('Sonny Angel - Birthday Series', 5, 14.99, 'Sonny Angels', 'Birthday themed Sonny Angel figure.'),
('Sonny Angel - Festival Series', 5, 14.99, 'Sonny Angels', 'Festival themed Sonny Angel figure.'),
('Sonny Angel - Christmas Series', 5, 14.99, 'Sonny Angels', 'Christmas themed Sonny Angel figure.'),
('Smiski - Glow in the Dark', 6, 9.99, 'Smiski', 'Glow in the dark Smiski figure.'),
('Smiski - Sitting', 6, 8.99, 'Smiski', 'Sitting Smiski figure.'),
('Smiski - Dancing', 6, 8.99, 'Smiski', 'Dancing Smiski figure.'),
('Smiski - Sleeping', 6, 8.99, 'Smiski', 'Sleeping Smiski figure.'),
('Smiski - Winter Edition', 6, 10.99, 'Smiski', 'Winter themed Smiski figure.'),
('Smiski - Summer Edition', 6, 10.99, 'Smiski', 'Summer themed Smiski figure.');

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

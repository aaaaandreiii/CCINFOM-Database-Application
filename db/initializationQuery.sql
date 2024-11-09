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
('Satoru','Gojo','purple@jjk.com','410-050-5050','Jujutsu Technical College','4.4'),
('Dominic','Toretto','dom@fnf.com','539-249-9063','48 Los Ricos St.','2.6'),
('Isagi','Yoichi','isagi@bluelock.com','892-139-1397','7th Hiraga Road','3.8'),
('Rukia','Kuchiki','rukiak@soul.com','108-249-1379','10 Selaz Drive','2.1'),
('Ichigo','Kurosaki','ichigok@soul.com','950-134-6491','13 Soul Society','3.7'),
('Uryu','Ishida','uryuishida@quincy.com','411-439-5397','9 Akiwara Drive','4.9'),
('Orihime','Inoue','orihihi@soul.com','429-131-5491','2 Akudama Drive','4.1'),
('Kisuke','Urahara','kurahara@soul.com','359-139-5497','Hara Avenue','2.9'),
('Miku','Hatsune','hatsunemiku@vocaloid.com','452-962-9537','99 Miku Building','4.2'),
('Dean','Yu','deanyu@bumble.com','520-123-6048','1 Deans Corporation','0.2'),
('Peter','Griffin','pgrif@quahog.com','530-234-6498','12 Hog St.','0.0'),
('Emma', 'Johnson', 'emma.johnson@gmail.com', '408-555-1010', '123 Oak St', 3.2),
('Liam', 'Miller', 'liam.miller@yahoo.com', '510-555-1020', '456 Pine St', 4.8),
('Olivia', 'Williams', 'olivia.williams@outlook.com', '650-555-1030', '789 Maple Ave', 2.9),
('Noah', 'Davis', 'noah.davis@gmail.com', '408-555-1040', '101 Elm St', 4.1),
('Ava', 'Garcia', 'ava.garcia@hotmail.com', '415-555-1050', '202 Birch Rd', 3.5),
('Sophia', 'Rodriguez', 'sophia.rodriguez@yahoo.com', '510-555-1060', '303 Cedar Blvd', 4.2),
('Jackson', 'Martinez', 'jackson.martinez@aol.com', '925-555-1070', '404 Oak Dr', 3.7),
('Mia', 'Hernandez', 'mia.hernandez@outlook.com', '650-555-1080', '505 Pine Ln', 2.6),
('Lucas', 'Lopez', 'lucas.lopez@gmail.com', '707-555-1090', '606 Maple St', 4.3),
('Amelia', 'Gonzalez', 'amelia.gonzalez@yahoo.com', '408-555-1100', '707 Cedar St', 3.0),
('Elijah', 'Perez', 'elijah.perez@hotmail.com', '925-555-1110', '808 Birch Dr', 4.6),
('Isabella', 'Wilson', 'isabella.wilson@aol.com', '415-555-1120', '909 Oak Ln', 2.8),
('Ethan', 'Anderson', 'ethan.anderson@gmail.com', '510-555-1130', '1011 Pine Blvd', 4.0),
('Harper', 'Thomas', 'harper.thomas@yahoo.com', '650-555-1140', '1212 Maple Dr', 3.9),
('James', 'Jackson', 'james.jackson@outlook.com', '707-555-1150', '1313 Cedar Ave', 4.1),
('Charlotte', 'White', 'charlotte.white@hotmail.com', '408-555-1160', '1414 Birch Rd', 4.7),
('Benjamin', 'Lee', 'benjamin.lee@gmail.com', '925-555-1170', '1515 Oak Blvd', 3.4),
('Mason', 'Young', 'mason.young@aol.com', '510-555-1180', '1616 Pine Ln', 2.5),
('Amos', 'King', 'amos.king@yahoo.com', '650-555-1190', '1717 Maple Ave', 4.8),
('Ella', 'Scott', 'ella.scott@outlook.com', '707-555-1200', '1818 Cedar Dr', 4.2),
('Henry', 'Wright', 'henry.wright@hotmail.com', '925-555-1210', '1919 Birch St', 3.3),
('Zoe', 'Lopez', 'zoe.lopez@gmail.com', '510-555-1220', '2020 Oak Ln', 3.6),
('Alexander', 'Hill', 'alexander.hill@aol.com', '650-555-1230', '2121 Pine Blvd', 4.5),
('Aiden', 'Adams', 'aiden.adams@yahoo.com', '707-555-1240', '2222 Maple Dr', 4.0),
('Lily', 'Baker', 'lily.baker@gmail.com', '925-555-1250', '2323 Cedar Ave', 4.7),
('Daniel', 'Nelson', 'daniel.nelson@hotmail.com', '510-555-1260', '2424 Birch Ln', 3.9),
('Grace', 'Carter', 'grace.carter@aol.com', '650-555-1270', '2525 Oak St', 4.1),
('Jack', 'Mitchell', 'jack.mitchell@yahoo.com', '707-555-1280', '2626 Pine Rd', 3.8),
('Scarlett', 'Perez', 'scarlett.perez@outlook.com', '925-555-1290', '2727 Maple Blvd', 2.4),
('Isaac', 'Roberts', 'isaac.roberts@gmail.com', '408-555-1300', '2828 Cedar Dr', 4.0),
('Chloe', 'Evans', 'chloe.evans@aol.com', '650-555-1310', '2929 Birch Ave', 3.2),
('Samuel', 'Murphy', 'samuel.murphy@yahoo.com', '707-555-1320', '3030 Oak Blvd', 3.5),
('Victoria', 'Ross', 'victoria.ross@hotmail.com', '925-555-1330', '3131 Pine Ln', 4.3),
('Gabriel', 'Ward', 'gabriel.ward@aol.com', '510-555-1340', '3232 Maple Ave', 4.6),
('Layla', 'Harris', 'layla.harris@gmail.com', '650-555-1350', '3333 Cedar Blvd', 4.2),
('David', 'Cooper', 'david.cooper@outlook.com', '707-555-1360', '3434 Birch St', 3.1),
('Juliana', 'Bennett', 'juliana.bennett@yahoo.com', '925-555-1370', '3535 Oak Dr', 4.4),
('Jackson', 'Murphy', 'jackson.murphy@hotmail.com', '408-555-1380', '3636 Pine Blvd', 2.7),
('Madison', 'James', 'madison.james@aol.com', '650-555-1390', '3737 Maple Rd', 3.0),
('Moo','Deng','moodeng@viet.com','305-529-2429','Khao Kheow Open Zoo','5.0');

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

INSERT INTO Supplier (supplier_fname, supplier_lname, email, phone, address, supplier_rating) VALUES
('Andres', 'Martinez', 'andres.martinez@gmail.com', '(02) 8123-4567', '1234 Rizal Street, Quezon City, Metro Manila', 4.5),
('Maria', 'Garcia', 'maria.garcia@gmail.com', '(02) 8123-4568', '5678 Bonifacio Avenue, Makati City, Metro Manila', 3.2),
('Jose', 'Reyes', 'jose.reyes@gmail.com', '(02) 8123-4569', '9101 Luna Street, Pasig City, Metro Manila', 4.0),
('Lia', 'Cruz', 'lia.cruz@gmail.com', '(02) 8123-4570', '1213 Magsaysay Boulevard, Manila', 2.8),
('Juan', 'Dela Cruz', 'juan.delacruz@gmail.com', '(02) 8123-4571', '1415 Rizal Avenue, Cebu City', 3.6),
('Sofia', 'Bautista', 'sofia.bautista@gmail.com', '(02) 8123-4572', '1617 Josefa Street, Quezon City, Metro Manila', 4.9),
('Marco', 'Santos', 'marco.santos@gmail.com', '(02) 8123-4573', '1819 Villalobos Road, Taguig City', 4.1),
('Liza', 'Flores', 'liza.flores@gmail.com', '(02) 8123-4574', '2021 Quezon Avenue, San Juan', 3.7),
('Antonio', 'Morales', 'antonio.morales@gmail.com', '(02) 8123-4575', '2223 Padre Faura Street, Manila', 4.3),
('Angela', 'Villanueva', 'angela.villanueva@gmail.com', '(02) 8123-4576', '2425 Andres Bonifacio Avenue, Caloocan City', 2.5),
('Carlos', 'Alvarez', 'carlos.alvarez@gmail.com', '(02) 8123-4577', '2627 Dela Rosa Street, Makati', 3.1),
('Ella', 'Aguilar', 'ella.aguilar@gmail.com', '(02) 8123-4578', '2829 G. Tuazon Street, Quezon City', 4.4),
('Rafael', 'Ocampo', 'rafael.ocampo@gmail.com', '(02) 8123-4579', '3031 Alonzo Street, Manila', 1.9),
('Angelica', 'Pineda', 'angelica.pineda@gmail.com', '(02) 8123-4580', '3233 Estrada Avenue, Pasay City', 3.4),
('Leo', 'Rondona', 'leo.rondona@gmail.com', '(02) 8123-4581', '3435 Rizal Street, Bacolod', 4.7),
('Isabella', 'De Leon', 'isabella.deleon@gmail.com', '(02) 8123-4582', '3637 Lopez Jaena Street, Iloilo', 4.2),
('Martin', 'Natividad', 'martin.natividad@gmail.com', '(02) 8123-4583', '3839 San Pedro Street, Batangas', 2.3),
('Christine', 'Salvador', 'christine.salvador@gmail.com', '(02) 8123-4584', '4041 Quezon Boulevard, Cavite', 3.9),
('Noel', 'Santos', 'noel.santos@gmail.com', '(02) 8123-4585', '4243 Morales Road, Davao', 4.1),
('Ruth', 'Aguinaldo', 'ruth.aguinaldo@gmail.com', '(02) 8123-4586', '4445 Araneta Avenue, Manila', 4.8),
('Bong', 'Pineda', 'bong.pineda@gmail.com', '(02) 8123-4587', '4647 San Jose Street, Taguig', 2.6),
('Trisha', 'Alcantara', 'trisha.alcantara@gmail.com', '(02) 8123-4588', '4849 Nueva Ecija Road, Nueva Ecija', 3.5),
('Alvin', 'Torres', 'alvin.torres@gmail.com', '(02) 8123-4589', '5051 Taal Avenue, Batangas', 1.8),
('Yvette', 'Rosales', 'yvette.rosales@gmail.com', '(02) 8123-4590', '5253 Subic Bay Road, Zambales', 4.0),
('Kevin', 'Velasco', 'kevin.velasco@gmail.com', '(02) 8123-4591', '5455 Tarlac Street, Tarlac', 2.2),
('Wendy', 'Canto', 'wendy.canto@gmail.com', '(02) 8123-4592', '5657 Sta. Rosa Road, Laguna', 3.8),
('Patricia', 'Ibañez', 'patricia.ibanez@gmail.com', '(02) 8123-4593', '5859 Quezon Hill, Baguio', 4.4),
('Miguel', 'Bacani', 'miguel.bacani@gmail.com', '(02) 8123-4594', '6061 Abad Santos Avenue, Manila', 3.3),
('Cathy', 'Manalo', 'cathy.manalo@gmail.com', '(02) 8123-4595', '6263 La Salle Street, Bacoor', 2.9),
('Gerry', 'Macapagal', 'gerry.macapagal@gmail.com', '(02) 8123-4596', '6465 Rizal Hill, Bataan', 1.5),
('Nina', 'Velasquez', 'nina.velasquez@gmail.com', '(02) 8123-4597', '6667 Jose Abad Santos Avenue, San Fernando', 4.3),
('Ronnie', 'Limon', 'ronnie.limon@gmail.com', '(02) 8123-4598', '6869 Dela Torre Street, Angeles City', 3.0),
('Chloe', 'Fernando', 'chloe.fernando@gmail.com', '(02) 8123-4599', '7071 Ponce Street, Makati', 4.6),
('Ben', 'Chua', 'ben.chua@gmail.com', '(02) 8123-4600', '7273 Santiago Street, Quezon City', 3.8),
('Diana', 'Lim', 'diana.lim@gmail.com', '(02) 8123-4601', '7475 Dumaguete Road, Negros', 2.1),
('Eric', 'Panganiban', 'eric.panganiban@gmail.com', '(02) 8123-4602', '7677 Clark Avenue, Pampanga', 4.2),
('Leah', 'Tiongson', 'leah.tiongson@gmail.com', '(02) 8123-4603', '7879 La Union Road, La Union', 3.6),
('Rico', 'Valenzuela', 'rico.valenzuela@gmail.com', '(02) 8123-4604', '8081 Aurora Boulevard, Manila', 4.5),
('Lourdes', 'Riviera', 'lourdes.riviera@gmail.com', '(02) 8123-4605', '8283 Maginhawa Street, Quezon City', 2.8),
('Lino', 'Gonzales', 'lino.gonzales@gmail.com', '(02) 8123-4606', '8485 K-4 Street, Manila', 3.9),
('Lia', 'De Jesus', 'lia.dejesus@gmail.com', '(02) 8123-4607', '8687 M.H. Del Pilar Street, Quezon City', 4.0),
('Sam', 'Zamora', 'sam.zamora@gmail.com', '(02) 8123-4608', '8889 Hapag Kubo Avenue, Cebu City', 1.7),
('Tina', 'Mendoza', 'tina.mendoza@gmail.com', '(02) 8123-4609', '9090 Manila Bay Street, Manila', 3.4),
('Nico', 'Delos Santos', 'nico.delossantos@gmail.com', '(02) 8123-4610', '9292 Barangay Malaya, Rizal', 2.6),
('Ella', 'Sison', 'ella.sison@gmail.com', '(02) 8123-4611', '9494 Sampaguita Street, Batangas', 4.8),
('Pia', 'Noble', 'pia.noble@gmail.com', '(02) 8123-4612', '9696 Taal Vista Avenue, Tagaytay', 4.1),
('Rafael', 'Sumulong', 'rafael.sumulong@gmail.com', '(02) 8123-4613', '9898 Aldea Hill, Cavite', 3.5),
('Karla', 'Fernandez', 'karla.fernandez@gmail.com', '(02) 8123-4614', '10101 C. Raymundo Avenue, Pasig City', 2.2),
('Vince', 'Alba', 'vince.alba@gmail.com', '(02) 8123-4615', '11111 San Rafael Street, Quezon City', 4.3),
('Jade', 'Mabini', 'jade.mabini@gmail.com', '(02) 8123-4616', '12121 San Juanico Avenue, Manila', 3.8),
('Diego', 'Nuno', 'diego.nuno@gmail.com', '(02) 8123-4617', '13131 Subangdaku Street, Cebu City', 2.5),
('Jasmine', 'Alcantara', 'jasmine.alcantara@gmail.com', '(02) 8123-4618', '14141 Balay Ni Mayang Street, Batangas', 4.7),
('Chloe', 'Liu', 'chloe.liu@gmail.com', '(02) 8123-4619', '15151 Bagong Silang Road, Quezon City', 3.6),
('Philip', 'Wong', 'philip.wong@gmail.com', '(02) 8123-4620', '16161 Sitio Mahayag, Davao', 2.8),
('Nina', 'Chua', 'nina.chua@gmail.com', '(02) 8123-4621', '17171 Abada Street, Manila', 4.2),
('Alvin', 'Roxas', 'alvin.roxas@gmail.com', '(02) 8123-4622', '18181 Batasan Hills, Quezon City', 4.9),
('Samantha', 'Cimafranca', 'samantha.cimafranca@gmail.com', '(02) 8123-4623', '19191 Pansol Street, Calamba', 3.1),
('Dave', 'Pia', 'dave.pia@gmail.com', '(02) 8123-4624', '20202 De La Paz Street, Manila', 2.5),
('Karla', 'Gonzalez', 'karla.gonzalez@gmail.com', '(02) 8123-4625', '21212 San Antonio Village, Makati City', 1.6),
('Melanie', 'Ayala', 'melanie.ayala@gmail.com', '(02) 8123-4626', '22222 Marikina River Road, Marikina', 4.5),
('Fernando', 'Salazar', 'fernando.salazar@gmail.com', '(02) 8123-4627', '23232 Mindanao Avenue, Quezon City', 3.7),
('Kathy', 'Ponce', 'kathy.ponce@gmail.com', '(02) 8123-4628', '24242 Hermosa Street, Pampanga', 4.4),
('Rick', 'Salas', 'rick.salas@gmail.com', '(02) 8123-4629', '25252 Remedios Street, Manila', 2.9),
('May', 'Rivera', 'may.rivera@gmail.com', '(02) 8123-4630', '26262 Flores Avenue, Quezon City', 1.4),
('Winston', 'Santos', 'winston.santos@gmail.com', '(02) 8123-4631', '27272 11th Avenue, Makati', 4.3),
('Cherry', 'Santiago', 'cherry.santiago@gmail.com', '(02) 8123-4632', '28282 7th Street, Manila', 2.6),
('Elijah', 'Montalbo', 'elijah.montalbo@gmail.com', '(02) 8123-4633', '29292 9th Avenue, Makati', 3.5),
('Trisha', 'Pangalangan', 'trisha.pangalangan@gmail.com', '(02) 8123-4634', '30303 Palanas Street, Baguio', 1.8),
('Ariana', 'Villafuerte', 'ariana.villafuerte@gmail.com', '(02) 8123-4635', '31313 Sultan Kudarat, Cotabato', 4.6),
('Miguel', 'Estrella', 'miguel.estrella@gmail.com', '(02) 8123-4636', '32323 Magsaysay Avenue, Caloocan City', 3.3),
('Jade', 'Rafael', 'jade.rafael@gmail.com', '(02) 8123-4637', '33333 Albay Avenue, Legaspi', 2.4),
('Ella', 'Panganiban', 'ella.panganiban@gmail.com', '(02) 8123-4638', '34343 Bayani Road, Manila', 1.2),
('Clara', 'Ricardo', 'clara.ricardo@gmail.com', '(02) 8123-4639', '35353 Rizal Avenue, Antipolo', 4.8);


-- INSERT INTO ShoppingCart (customer_id, item_id, quantity, supplier_id) VALUES


-- INSERT INTO Wishlist (customer_id, item_id, supplier_id) VALUES


-- INSERT INTO OrderInfo (customer_id, order_date, supplier_id, total_amount, status) VALUES

-- INSERT INTO OrderItem (order_id, item_id, quantity, price_at_order) VALUES

-- INSERT INTO Inventory (item_id, supplier_id, quantity) VALUES



SELECT * FROM customer;
SELECT * FROM inventory;
SELECT * FROM item;
SELECT * FROM orderinfo;
SELECT * FROM orderitem;
SELECT * FROM shoppingcart;
SELECT * FROM supplier;
SELECT * FROM wishlist;

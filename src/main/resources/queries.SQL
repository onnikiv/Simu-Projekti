CREATE DATABASE menu;
USE menu;

CREATE TABLE categories (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE menu_items (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
category_id INT NOT NULL,
prep_time_minutes INT NOT NULL,
FOREIGN KEY (category_id) REFERENCES categories(id)
);

INSERT INTO menu_items (name, category_id, prep_time_minutes) VALUES
      ('Garlic Bread', 1, 5),
      ('Bruschetta', 1, 7),
      ('Caesar Salad', 1, 6),
      ('Stuffed Mushrooms', 1, 8),
      ('Tomato Soup', 1, 10);

INSERT INTO menu_items (name, category_id, prep_time_minutes) VALUES
      ('Grilled Steak', 2, 20),
      ('Spaghetti Carbonara', 2, 15),
      ('Chicken Alfredo', 2, 18),
      ('Salmon Teriyaki', 2, 22),
      ('Margherita Pizza', 2, 12),
      ('BBQ Ribs', 2, 25),
      ('Vegetarian Stir Fry', 2, 14),
      ('Lamb Chops', 2, 30),
      ('Cheeseburger & Fries', 2, 12),
      ('Tuna Poke Bowl', 2, 16);

INSERT INTO menu_items (name, category_id, prep_time_minutes) VALUES
      ('Chocolate Lava Cake', 3, 12),
      ('New York Cheesecake', 3, 10),
      ('Tiramisu', 3, 15),
      ('Apple Pie', 3, 18),
      ('Ice Cream Sundae', 3, 5);




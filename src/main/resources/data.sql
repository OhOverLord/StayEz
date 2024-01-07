-- Insert hotels
INSERT INTO hotel (name, address, description, stars, country, city) VALUES
('Hotel Sunshine', '123 Sunny St, Sunville', 'A lovely sunny place to stay.', 4, 'USA', 'Sunville'),
('Mountain Retreat', '45 Mountain Rd, Mountainville', 'Escape in the tranquility of the mountains.', 5, 'Canada', 'Mountainville'),
('Beachside Inn', '789 Ocean Dr, Beachtown', 'Enjoy the sound of waves at our beachside retreat.', 3, 'Australia', 'Beachtown');

-- Insert rooms for each hotel
INSERT INTO room (guests_count, number, type, price_per_night, availability, hotel_id) VALUES
(2, '101', 'Standard', 100.00, TRUE, 1),
(2, '102', 'Deluxe', 150.00, TRUE, 1),
(2, '103', 'Suite', 200.00, TRUE, 1),
(4, '201', 'Standard', 120.00, TRUE, 2),
(4, '202', 'Deluxe', 170.00, TRUE, 2),
(4, '203', 'Suite', 220.00, TRUE, 2),
(3, '301', 'Standard', 110.00, TRUE, 3),
(3, '302', 'Deluxe', 160.00, TRUE, 3),
(3, '303', 'Suite', 210.00, TRUE, 3);

-- Insert customers
INSERT INTO customer (first_name, last_name, phone_number, email, address) VALUES
('Jane', 'Smith', '234-567-8901', 'jane.smith@example.com', '456 Elm St, Cityville');


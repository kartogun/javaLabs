CREATE TABLE IF NOT EXISTS clothes (
                                       id SERIAL PRIMARY KEY,
                                       type VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    size VARCHAR(10) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    material VARCHAR(100),
    length VARCHAR(20),
    sleeve_type VARCHAR(20),
    season VARCHAR(20),
    has_hood BOOLEAN,
    shoe_size INT,
    shoe_type VARCHAR(50)
    );
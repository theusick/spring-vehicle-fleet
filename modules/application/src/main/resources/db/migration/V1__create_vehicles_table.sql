CREATE SEQUENCE IF NOT EXISTS vehicles_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE IF NOT EXISTS vehicles
(
    id      BIGINT           NOT NULL,
    price   DOUBLE PRECISION NOT NULL,
    year    INTEGER          NOT NULL,
    mileage INTEGER          NOT NULL,
    color   VARCHAR(255),
    CONSTRAINT pk_vehicles PRIMARY KEY (id)
);

INSERT INTO vehicles (id, price, year, mileage, color) VALUES
(1, 1500000.00, 2023, 5000, 'Красный'),
(2, 2200000.00, 2019, 12000, 'Blue'),
(3, 185000.00, 2020, 158340, 'Белый'),
(4, 300000.00, 2022, 210301, 'Black');

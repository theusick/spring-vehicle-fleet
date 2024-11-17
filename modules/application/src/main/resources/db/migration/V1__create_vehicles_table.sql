CREATE SEQUENCE IF NOT EXISTS vehicle_brands_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS vehicles_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE vehicle_brands
(
    id               BIGINT           NOT NULL,
    name             VARCHAR(255),
    type             VARCHAR(255),
    seats            INTEGER          NOT NULL,
    fuel_tank        DOUBLE PRECISION NOT NULL,
    payload_capacity DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_vehicle_brands PRIMARY KEY (id)
);

CREATE TABLE vehicles
(
    id           BIGINT           NOT NULL,
    year         INTEGER          NOT NULL,
    mileage      INTEGER          NOT NULL,
    color        VARCHAR(255),
    price        DOUBLE PRECISION NOT NULL,
    plate_number VARCHAR(255),
    brand_id     BIGINT           NOT NULL,
    CONSTRAINT pk_vehicles PRIMARY KEY (id)
);

ALTER TABLE vehicles
    ADD CONSTRAINT FK_VEHICLES_ON_BRAND FOREIGN KEY (brand_id) REFERENCES vehicle_brands (id);

INSERT INTO vehicle_brands (id, name, type, seats, fuel_tank, payload_capacity) VALUES
(1, 'Toyota', 'Легковой', 5, 50.0, 500),
(2, 'Mercedes-Benz', 'Легковой', 5, 70.0, 600),
(3, 'Volvo', 'Грузовой', 2, 120.0, 8000),
(4, 'Volkswagen', 'Легковой', 5, 55.0, 600),
(5, 'MAN', 'Грузовой', 2, 150.0, 10000),
(6, 'Scania', 'Автобус', 50, 200.0, 1000);

INSERT INTO vehicles (id, year, mileage, color, price, plate_number, brand_id) VALUES
(1, 2023, 5000, 'Красный', 1500000.00, 'O481XX147', 1),
(2, 2019, 12000, 'Blue', 2200000.00, 'T725MO102', 2),
(3, 2020, 158340, 'Белый', 185000.00, 'P609CA29', 3),
(4, 2022, 210301, 'Black', 300000.00, 'O663BA96', 4),
(5, 2021, 50000, 'Серебристый', 2500000.00, 'A123BC177', 5),
(6, 2023, 10000, 'Зеленый', 1800000.00, 'B456DF300', 6);

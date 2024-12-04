CREATE TABLE enterprises
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255),
    city VARCHAR(255),
    CONSTRAINT pk_enterprises PRIMARY KEY (id)
);

CREATE TABLE drivers
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name          VARCHAR(255),
    age           INTEGER                                 NOT NULL,
    salary        DOUBLE PRECISION                        NOT NULL,
    enterprise_id BIGINT                                  NOT NULL,
    vehicle_id    BIGINT,
    active        BIT,
    driver_id     BIGINT,
    CONSTRAINT pk_drivers PRIMARY KEY (id)
);

CREATE TABLE vehicle_brands
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name             VARCHAR(255),
    type             VARCHAR(255),
    seats            INTEGER                                 NOT NULL,
    fuel_tank        DOUBLE PRECISION                        NOT NULL,
    payload_capacity DOUBLE PRECISION                        NOT NULL,
    CONSTRAINT pk_vehicle_brands PRIMARY KEY (id)
);

CREATE TABLE vehicles
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    year          INTEGER                                 NOT NULL,
    mileage       INTEGER                                 NOT NULL,
    color         VARCHAR(255),
    price         DOUBLE PRECISION                        NOT NULL,
    license_plate VARCHAR(255),
    enterprise_id BIGINT                                  NOT NULL,
    brand_id      BIGINT                                  NOT NULL,
    CONSTRAINT pk_vehicles PRIMARY KEY (id)
);

ALTER TABLE drivers
    ADD CONSTRAINT FK_DRIVERS_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES vehicles (id);

ALTER TABLE drivers
    ADD CONSTRAINT FK_DRIVERS_ON_ENTERPRISE FOREIGN KEY (enterprise_id) REFERENCES enterprises (id);

ALTER TABLE drivers
    ADD CONSTRAINT FK_DRIVERS_ON_VEHICLE FOREIGN KEY (vehicle_id) REFERENCES vehicles (id);

ALTER TABLE vehicles
    ADD CONSTRAINT FK_VEHICLES_ON_BRAND FOREIGN KEY (brand_id) REFERENCES vehicle_brands (id) ON DELETE CASCADE;

ALTER TABLE vehicles
    ADD CONSTRAINT FK_VEHICLES_ON_ENTERPRISE FOREIGN KEY (enterprise_id) REFERENCES enterprises (id);

INSERT INTO enterprises (id, name, city)
VALUES (1, 'Логистика ООО', 'Москва'),
       (2, 'Автопарк Плюс', 'Санкт-Петербург'),
       (3, 'Транспорт Сервис', 'Новосибирск'),
       (4, 'Vehicle Fleet', 'Chicago');

SELECT setval(pg_get_serial_sequence('enterprises', 'id'), (SELECT MAX(id) FROM enterprises));

INSERT INTO vehicle_brands (id, name, type, seats, fuel_tank, payload_capacity)
VALUES (1, 'Toyota Prius', 'Легковой', 5, 50.0, 500),
       (2, 'Mercedes-Benz S-Class', 'Легковой', 5, 70.0, 600),
       (3, 'Volvo FH16', 'Грузовой', 2, 120.0, 8000),
       (4, 'Volkswagen Golf', 'Легковой', 5, 55.0, 600),
       (5, 'MAN TGX', 'Грузовой', 2, 150.0, 10000),
       (6, 'Scania Citywide', 'Автобус', 50, 200.0, 1000);

SELECT setval(pg_get_serial_sequence('vehicle_brands', 'id'), (SELECT MAX(id) FROM vehicle_brands));

INSERT INTO vehicles (id, year, mileage, color, price, license_plate, brand_id, enterprise_id)
VALUES (1, 2023, 5000, 'Красный', 1500000.00, 'O481XX147', 1, 1),
       (2, 2019, 12000, 'Синий', 2200000.00, 'T725MO102', 2, 1),
       (3, 2020, 158340, 'Белый', 185000.00, 'P609CA29', 3, 2),
       (4, 2022, 210301, 'Черный', 300000.00, 'O663BA96', 4, 2),
       (5, 2021, 50000, 'Серебристый', 2500000.00, 'A123BC177', 5, 3),
       (6, 2023, 10000, 'Зеленый', 1800000.00, 'B456DF300', 6, 3),
       (7, 2021, 0, 'Желтый', 2000000.00, 'K987NN89', 6, 2),
       (8, 2022, 30000, 'Оранжевый', 2800000.00, 'V456OP77', 5, 1),
       (9, 2023, 5000, 'Синий', 1500000.00, 'N123CC01', 1, 3);

SELECT setval(pg_get_serial_sequence('vehicles', 'id'), (SELECT MAX(id) FROM vehicles));

INSERT INTO drivers (id, name, age, salary, enterprise_id, vehicle_id, active, driver_id)
VALUES (1, 'Иван Иванов', 35, 70000.00, 1, 1, B'1', NULL),
       (2, 'Петр Петров', 40, 80000.00, 1, 2, B'1', NULL),
       (3, 'Алексей Смирнов', 29, 60000.00, 2, 3, B'1', NULL),
       (4, 'Евгений Сидоров', 50, 90000.00, 2, 4, B'1', NULL),
       (5, 'Ольга Кузнецова', 33, 75000.00, 3, 5, B'1', NULL),
       (6, 'Анна Орлова', 28, 65000.00, 3, 6, B'1', NULL),
       (7, 'Максим Васильев', 45, 85000.00, 2, NULL, B'0', NULL),
       (8, 'Екатерина Новикова', 39, 72000.00, 1, NULL, B'0', NULL);

SELECT setval(pg_get_serial_sequence('drivers', 'id'), (SELECT MAX(id) FROM drivers));

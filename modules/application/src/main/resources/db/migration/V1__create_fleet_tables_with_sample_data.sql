CREATE TABLE drivers
(
    id                BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name              VARCHAR(255)                            NOT NULL,
    age               INTEGER                                 NOT NULL,
    salary            DOUBLE PRECISION                        NOT NULL,
    enterprise_id     BIGINT                                  NOT NULL,
    active_vehicle_id BIGINT,
    CONSTRAINT pk_drivers PRIMARY KEY (id)
);

CREATE TABLE enterprises
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255)                            NOT NULL,
    city VARCHAR(255)                            NOT NULL,
    CONSTRAINT pk_enterprises PRIMARY KEY (id)
);

CREATE TABLE manager_enterprises
(
    enterprise_id BIGINT NOT NULL,
    manager_id    BIGINT NOT NULL,
    CONSTRAINT pk_manager_enterprises PRIMARY KEY (enterprise_id, manager_id)
);

CREATE TABLE manager_entity_roles
(
    manager_entity_id BIGINT NOT NULL,
    roles             INTEGER
);

CREATE TABLE managers
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username VARCHAR(256)                            NOT NULL,
    password VARCHAR(128)                            NOT NULL,
    enabled  BIT                                     NOT NULL,
    CONSTRAINT pk_managers PRIMARY KEY (id)
);

CREATE TABLE vehicle_brands
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name             VARCHAR(255)                            NOT NULL,
    type             VARCHAR(255)                            NOT NULL,
    seats            INTEGER                                 NOT NULL,
    fuel_tank        DOUBLE PRECISION                        NOT NULL,
    payload_capacity DOUBLE PRECISION                        NOT NULL,
    CONSTRAINT pk_vehicle_brands PRIMARY KEY (id)
);

CREATE TABLE vehicle_drivers
(
    vehicle_id BIGINT NOT NULL,
    driver_id  BIGINT NOT NULL,
    CONSTRAINT pk_vehicle_drivers PRIMARY KEY (vehicle_id, driver_id)
);

CREATE TABLE vehicles
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    year          INTEGER                                 NOT NULL,
    mileage       INTEGER                                 NOT NULL,
    color         VARCHAR(255)                            NOT NULL,
    price         DOUBLE PRECISION                        NOT NULL,
    license_plate VARCHAR(30)                             NOT NULL,
    enterprise_id BIGINT                                  NOT NULL,
    brand_id      BIGINT                                  NOT NULL,
    CONSTRAINT pk_vehicles PRIMARY KEY (id)
);

ALTER TABLE drivers
    ADD CONSTRAINT uc_drivers_active_vehicle UNIQUE (active_vehicle_id);

ALTER TABLE drivers
    ADD CONSTRAINT FK_DRIVERS_ON_ACTIVE_VEHICLE FOREIGN KEY (active_vehicle_id) REFERENCES vehicles (id);

ALTER TABLE drivers
    ADD CONSTRAINT FK_DRIVERS_ON_ENTERPRISE FOREIGN KEY (enterprise_id) REFERENCES enterprises (id);

ALTER TABLE vehicles
    ADD CONSTRAINT FK_VEHICLES_ON_BRAND FOREIGN KEY (brand_id) REFERENCES vehicle_brands (id) ON DELETE CASCADE;

ALTER TABLE vehicles
    ADD CONSTRAINT FK_VEHICLES_ON_ENTERPRISE FOREIGN KEY (enterprise_id) REFERENCES enterprises (id);

ALTER TABLE vehicle_drivers
    ADD CONSTRAINT FK_VEHICLE_DRIVERS_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES drivers (id);

ALTER TABLE vehicle_drivers
    ADD CONSTRAINT FK_VEHICLE_DRIVERS_ON_VEHICLE FOREIGN KEY (vehicle_id) REFERENCES vehicles (id);

ALTER TABLE manager_entity_roles
    ADD CONSTRAINT fk_manager_entity_roles_on_manager_entity FOREIGN KEY (manager_entity_id) REFERENCES managers (id);

ALTER TABLE manager_enterprises
    ADD CONSTRAINT fk_manent_on_enterprise_entity FOREIGN KEY (enterprise_id) REFERENCES enterprises (id);

ALTER TABLE manager_enterprises
    ADD CONSTRAINT fk_manent_on_manager_entity FOREIGN KEY (manager_id) REFERENCES managers (id);

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

INSERT INTO drivers (id, name, age, salary, enterprise_id)
VALUES (1, 'Иван Иванов', 35, 70000.00, 1),
       (2, 'Петр Петров', 40, 80000.00, 1),
       (3, 'Алексей Смирнов', 29, 60000.00, 2),
       (4, 'Евгений Сидоров', 50, 90000.00, 2),
       (5, 'Ольга Кузнецова', 33, 75000.00, 3),
       (6, 'Анна Орлова', 28, 65000.00, 3),
       (7, 'Максим Васильев', 45, 85000.00, 2),
       (8, 'Екатерина Новикова', 39, 72000.00, 1);

SELECT setval(pg_get_serial_sequence('drivers', 'id'), (SELECT MAX(id) FROM drivers));

UPDATE drivers SET active_vehicle_id = 1 WHERE id = 1;
UPDATE drivers SET active_vehicle_id = 9 WHERE id = 2;
UPDATE drivers SET active_vehicle_id = 7 WHERE id = 3;
UPDATE drivers SET active_vehicle_id = 2 WHERE id = 4;
UPDATE drivers SET active_vehicle_id = 3 WHERE id = 5;
UPDATE drivers SET active_vehicle_id = 8 WHERE id = 6;
UPDATE drivers SET active_vehicle_id = 5 WHERE id = 7;

INSERT INTO vehicle_drivers (vehicle_id, driver_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 3),
       (2, 4),
       (2, 5),
       (2, 6),
       (3, 5),
       (3, 2),
       (3, 7),
       (4, 1),
       (4, 6),
       (4, 7),
       (5, 4),
       (5, 6),
       (5, 7),
       (6, 2),
       (6, 3),
       (6, 8),
       (7, 3),
       (7, 8),
       (8, 4),
       (8, 1),
       (8, 6),
       (9, 2),
       (9, 8),
       (9, 5),
       (9, 7);

INSERT INTO managers (id, username, password, enabled)
VALUES
    (1, 'manager1', '$2a$10$xFA33DOUCWXY2dWigYKIMe0vTqpQmZ6CPzubj8imzvcCTmBB/x9w6', B'1'),
    (2, 'manager2', '$2a$10$kx59TQifTCb4U0gD2QbOCOD5XIFnmb0afMW7BG927h..zRuB7gGoW', B'1'),
    (3, 'user1', '$2a$10$xFA33DOUCWXY2dWigYKIMe0vTqpQmZ6CPzubj8imzvcCTmBB/x9w6', B'1');

INSERT INTO manager_entity_roles (manager_entity_id, roles)
VALUES
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);

INSERT INTO manager_enterprises (enterprise_id, manager_id)
VALUES
    (1, 1),
    (2, 1);

INSERT INTO manager_enterprises (enterprise_id, manager_id)
VALUES
    (2, 2),
    (3, 2);

SELECT setval(pg_get_serial_sequence('managers', 'id'), (SELECT MAX(id) FROM managers));

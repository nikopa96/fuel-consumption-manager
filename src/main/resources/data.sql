DROP SCHEMA PUBLIC CASCADE;

CREATE SEQUENCE fuel_consumption_seq START WITH 7;

CREATE TABLE fuel_consumption (
  fuel_consumption_id BIGINT PRIMARY KEY,
  driver_id BIGINT NOT NULL,
  fuel_type VARCHAR(64) NOT NULL,
  price_per_liter_in_eur DECIMAL(10,5) NOT NULL,
  volume_in_liters DECIMAL(10,5) NOT NULL,
  total_price_in_eur DECIMAL(10,5),
  date DATE NOT NULL
);

INSERT INTO fuel_consumption(fuel_consumption_id, driver_id, fuel_type, price_per_liter_in_eur, volume_in_liters, total_price_in_eur, date)
  VALUES (1, 1, 'D', 1.229, 5, 6.145, DATE '2019-04-11');
INSERT INTO fuel_consumption(fuel_consumption_id, driver_id, fuel_type, price_per_liter_in_eur, volume_in_liters, total_price_in_eur, date)
  VALUES (2, 1, 'D', 1.229, 6, 7.374, DATE '2019-04-12');
INSERT INTO fuel_consumption(fuel_consumption_id, driver_id, fuel_type, price_per_liter_in_eur, volume_in_liters, total_price_in_eur, date)
  VALUES (3, 2, '95', 1.229, 4, 4.916, DATE '2019-04-12');
INSERT INTO fuel_consumption(fuel_consumption_id, driver_id, fuel_type, price_per_liter_in_eur, volume_in_liters, total_price_in_eur, date)
  VALUES (4, 2, 'D', 1.229, 4, 4.916, DATE '2019-05-12');
INSERT INTO fuel_consumption(fuel_consumption_id, driver_id, fuel_type, price_per_liter_in_eur, volume_in_liters, total_price_in_eur, date)
  VALUES (5, 2, 'D', 1.229, 4, 4.916, DATE '2019-05-13');
INSERT INTO fuel_consumption(fuel_consumption_id, driver_id, fuel_type, price_per_liter_in_eur, volume_in_liters, total_price_in_eur, date)
  VALUES (6, 3, '95', 1.888, 4, 7.552, DATE '2019-05-13');
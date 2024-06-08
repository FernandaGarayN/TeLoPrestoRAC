INSERT INTO subsidiary (name, address, phone_number)
VALUES  ('San Miguel', 'Gran Avenida José Miguel Carrera 5345', 223456789),
        ('Recoleta', 'Avenida Perú 2345', 223456788),
        ('San Bernardo', 'Gran Avenida José Miguel Carrera 14180', 223456790);


INSERT INTO cars (plate_code, brand, model, subsidiary_id, color, factory_year, capacity, daily_cost, type, image)
VALUES ('ABCD01', 'Chevrolet', 'Sail', 1, 'Black', '2018', '5', '70000', 'Sedan', '/autos/chevroletnegro.jpg'),
       ('XYZW23', 'Chevrolet', 'Sail', 1, 'White', '2020', '5', '70000','Sedan', '/autos/chevroletblanco.jpg'),
       ('EFGH45', 'Hyundai', 'Elantra', 2, 'Blue', '2012', '5', '50000', 'Sedan', '/autos/HyundaiAzul.jpg'),
       ('IJKL67', 'Chery', 'Tiggo3', 1, 'Grey', '2022', '5', '90000', 'SUV', '/autos/Cherygris.jpg'),
       ('MNOP89', 'Mazda', 'CX-5', 1, 'Red', '2021', '2', '60000', 'SUV', '/autos/mazdarojo.jpg'),
       ('QRST10', 'Nissan', 'Pathfinder',1, 'Grey', '2018', '7', '60000', 'SUV', '/autos/Nissangris.jpg'),
       ('UVWX12', 'Chevrolet', 'V34', 3, 'Blue', '2023', '4', '30000', 'SUV', '/autos/Chevroletazul.jpg');

INSERT INTO client (username, rut, first_name, last_name, email, phone_number, address, gift_points)
VALUES
    ('rulloa', '151913318', 'Rodrigo', 'Ulloa', 'rulload@telopresto.cl', 226251693, 'Calle Principal 123', 100),
    ('fgaray', '167967051', 'Fernanda', 'Garay', 'fgaray@telopresto.cl', 226216418, 'Avenida Central 456', 50),
    ('psanchez','5555555555', 'Pedro', 'Sánchez', 'pedro@example.com', 225342263, 'Calle Secundaria 789', 75);

-- Reservas para Juan Pérez
INSERT INTO reservation (client_id, car_id, start_at, end_at, status)
VALUES
    (1, 1, '2023-01-15', '2023-01-31', 'pending'), -- Asigna el car_id adecuado
    (1, 2, '2023-02-15', '2023-02-28', 'ok'); -- Asigna el car_id adecuado

-- Reservas para María González
INSERT INTO reservation (client_id, car_id, start_at, end_at, status)
VALUES
    (2, 3,'2023-03-15', '2023-03-20', 'pending'), -- Asigna el car_id adecuado
    (2, 4,'2023-02-10', '2023-02-15', 'ok'); -- Asigna el car_id adecuado

-- Reservas para Pedro Sánchez
INSERT INTO reservation (client_id, car_id, start_at, end_at, status)
VALUES
    (3, 5,'2023-01-02', '2023-01-15', 'pending'), -- Asigna el car_id adecuado
    (3, 6,'2023-04-15', '2023-04-25', 'ok'); -- Asigna el car_id adecuado;

-- Empleados para Sucursal 1
INSERT INTO employee (subsidiary_id, first_name, last_name, position, hire_date)
VALUES
    (1, 'Carlos', 'Martínez', 'Gerente', '2022-01-15'),
    (1, 'Ana', 'López', 'Asistente', '2022-03-10');
2022-01-15
-- Empleados para Sucursal 2
INSERT INTO employee (subsidiary_id, first_name, last_name, position, hire_date)
VALUES
    (2, 'Luis', 'García', 'Gerente', '2022-02-20'),
    (2, 'Elena', 'Rodríguez', 'Asistente', '2022-04-05');

-- Empleados para Sucursal 3
INSERT INTO employee (subsidiary_id, first_name, last_name, position, hire_date)
VALUES
    (3, 'Miguel', 'Hernández', 'Gerente', '2022-03-25'),
    (3, 'Isabel', 'Pérez', 'Asistente', '2022-05-15');

-- Comentarios para Reserva 1 (asumiendo que reservation_id = 1)
INSERT INTO comments (reservation_id, description, rate)
VALUES
    (1, 'Muy buen servicio, coche limpio y en buen estado.', 5),
    (1, 'Fácil proceso de recogida y entrega.', 4);

-- Comentarios para Reserva 2 (asumiendo que reservation_id = 2)
INSERT INTO comments (reservation_id, description, rate)
VALUES
    (2, 'El coche presentaba algunos problemas mecánicos.', 3),
    (2, 'El personal fue amable, pero hubo retrasos en la entrega.', 3);

-- Repite el proceso para las otras reservas.

-- Insert para payments con reservation_id del 1 al 6
INSERT INTO payments (reservation_id, type, amount, payment_date) VALUES (1, 'Tarjeta de Crédito', 500, '2023-10-26');
INSERT INTO payments (reservation_id, type, amount, payment_date) VALUES (2, 'Tarjeta de Crédito', 600, '2023-10-27');
INSERT INTO payments (reservation_id, type, amount, payment_date) VALUES (3, 'Transferencia Bancaria', 700, '2023-10-28');
INSERT INTO payments (reservation_id, type, amount, payment_date) VALUES (4, 'Efectivo', 800, '2023-10-29');
INSERT INTO payments (reservation_id, type, amount, payment_date) VALUES (5, 'Tarjeta de Débito', 900, '2023-10-30');
INSERT INTO payments (reservation_id, type, amount, payment_date) VALUES (6, 'PayPal', 1000, '2023-10-31');


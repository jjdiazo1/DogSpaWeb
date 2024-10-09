//Articulos

INSERT INTO ARTICULO_ENTITY (ID, DESCRIPCION, IMAGEN, NOMBRE, PRECIO)
VALUES (1, 'Mant�n a tu perro entretenido y evita el comportamiento destructivo con nuestro juguete interactivo de rompecabezas que desaf�a su mente y fomenta el juego.', 'https://tiendapurpura.co/wp-content/uploads/2023/06/61C7kCzRUyL._AC_SL1500_.jpg'
, 'Rompecabezas', 70000);

INSERT INTO ARTICULO_ENTITY (ID, DESCRIPCION, IMAGEN, NOMBRE, PRECIO)
VALUES (2, 'Juguetes duraderos y resistentes para perros, no t�xicos y antimordeduras, garantizan que tu perro pueda morder y jugar durante mucho tiempo, no se da�ar�.'
, 'https://i0.wp.com/m.media-amazon.com/images/I/61udco-xMpS._AC_SL1500_.jpg?fit=1500%2C1429&ssl=1g', 'Juguete para Morder', 70000);

INSERT INTO ARTICULO_ENTITY (ID, DESCRIPCION, IMAGEN, NOMBRE, PRECIO)
VALUES (3, 'Este maravilloso juguete har� que tu perro o gato se mantengan activos y se beneficien con el desaf�o de "trabajar" por su alimento y se divierta en el proceso.'
, 'https://http2.mlstatic.com/D_NQ_NP_2X_690024-MCO48024980144_102021-F.webp', 'Dispensador para perro premios', 35000);

INSERT INTO ARTICULO_ENTITY (ID, DESCRIPCION, IMAGEN, NOMBRE, PRECIO)
VALUES (4, 'Dogwood de Petstages es un juguete masticable para perros que contiene el sabor natural de la madera y la textura que aman los perros sin los efectos secundarios del astillado que produce la madera real.'
, 'https://petcol.co/cdn/shop/products/juguete-perros-petstages-dogwood-mordedero-palo-madera-l-1_380x.jpg?v=1570446049', 'Dogwood de Petstages', 87000);

INSERT INTO ARTICULO_ENTITY (ID, DESCRIPCION, IMAGEN, NOMBRE, PRECIO)
VALUES (5, 'Portapasabocas kong classic para perro, porta snack, marca kong. Es ideal para perros que adoran la forma cl�sica de los huesos, pero necesitan un juguete de goma seguro y resistente. Se puede rellenar.'
, 'https://laika.com.co/cdn-cgi/image/fit=scale-down,width=400,format=auto,quality=80,onerror=redirect/https://d23qt3x1ychzdy.cloudfront.net/dev_images_products/23178_156586_Kong___Classic_Portapasabocas_1651684148_500x500.jpg', 'Kong classic', 66000);


//Articulos Sedes
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (1, 1);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (1, 2);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (1, 3);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (1, 4);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (1, 5);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (2, 1);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (2, 2);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (3, 3);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (4, 4);
INSERT INTO ARTICULO_ENTITY_SEDES (ARTICULOS_ID, SEDES_ID) VALUES (4, 5);

//Sedes
INSERT INTO SEDE_ENTITY (NOMBRE, DIRECCION, TELEFONO, INSTAGRAM, CIUDAD)
VALUES ('SEDE 1', 'Cra 63D #24A-2', 309293822, 'DogSpaSede1', 'Bogota');

INSERT INTO SEDE_ENTITY (NOMBRE, DIRECCION, TELEFONO, INSTAGRAM, CIUDAD)
VALUES ('SEDE 2', 'Cra 69D #24A-2', 309293492, 'DogSpaSede2', 'Barranquilla');

INSERT INTO SEDE_ENTITY (NOMBRE, DIRECCION, TELEFONO, INSTAGRAM, CIUDAD)
VALUES ('SEDE 3', 'Cra 68D #24A-2', 309296392, 'DogSpaSede3', 'Armenia');

INSERT INTO SEDE_ENTITY (NOMBRE, DIRECCION, TELEFONO, INSTAGRAM, CIUDAD)
VALUES ('SEDE 4', 'Cra 90D #24A-2', 309493892, 'DogSpaSede4', 'Cartagena');

INSERT INTO SEDE_ENTITY (NOMBRE, DIRECCION, TELEFONO, INSTAGRAM, CIUDAD)
VALUES ('SEDE 5', 'Cra 21D #24A-2', 309493892, 'DogSpaSede5', 'Chia');

INSERT INTO SEDE_ENTITY (NOMBRE, DIRECCION, TELEFONO, INSTAGRAM, CIUDAD)
VALUES ('SEDE 6', 'Cra 100 #24A-2', 309493892, 'DogSpaSede6', 'Cali');


INSERT INTO SEDE_ENTITY (NOMBRE, DIRECCION, TELEFONO, INSTAGRAM, CIUDAD)
VALUES ('SEDE 7', 'Cra 90 #24A-2', 309493892, 'DogSpaSede7', 'Bucaramanga');

//RESERVA
INSERT INTO RESERVA_ENTITY (ID, DUENO_MASCOTA, EDAD_MASCOTA, FECHA, NOMBRE_MASCOTA, RAZA_MASCOTA, TELEFONO, TIPO_MASCOTA, TRABAJADOR_ENCARGADO, SEDE_ID) VALUES 
(1, 'Juan Pérez', 3, '2024-05-01', 'Firulais', 'Golden Retriever', 123456, 'Perro', 'Ana Gómez', 1),
(2, 'María González', 2, '2024-05-02', 'Max', 'Pomerania', 987654, 'Perro', 'Carlos Fernández', 1),
(3, 'José Martínez', 4, '2024-05-03', 'Bella', 'Labrador Retriever', 456123, 'Perro', 'Laura Hernández', 2),
(4, 'Lucía López', 1, '2024-05-04', 'Rocky', 'Bulldog Inglés', 321987, 'Perro', 'David Ramírez', 2),
(5, 'Miguel Álvarez', 5, '2024-05-05', 'Coco', 'Shih Tzu', 789456, 'Perro', 'Mariana Ríos', 3),
(6, 'Andrea García', 6, '2024-05-06', 'Lucky', 'Beagle', 654321, 'Perro', 'Felipe Torres', 3),
(7, 'Roberto Sánchez', 2, '2024-05-07', 'Simba', 'Chihuahua', 852963, 'Perro', 'Rosa Mendoza', 4),
(8, 'Carla Herrera', 7, '2024-05-08', 'Nala', 'Yorkshire Terrier', 741852, 'Perro', 'Luis Castillo', 4),
(9, 'Ana Jiménez', 3, '2024-05-09', 'Rex', 'Doberman', 96374, 'Perro', 'Paula Morales', 4),
(10, 'Enrique Rodríguez', 8, '2024-05-10', 'Duke', 'Gran Danés', 123789, 'Perro', 'Jorge Navarro', 4);

//SERVICIOS
-- Servicios básicos
INSERT INTO SERVICIO_ENTITY (ID, DESCRIPCION, NOMBRE, PRECIO, SERVICIOS_DE_RESERVA_ID) VALUES 
(1, 'Baño y secado estándar', 'Baño estándar', 25.00, 1),
(2, 'Corte de pelo básico', 'Corte básico', 30.00, 1),
(3, 'Limpieza de oídos', 'Limpieza de oídos', 15.00, 1);

-- Servicios premium
INSERT INTO SERVICIO_ENTITY (ID, DESCRIPCION, NOMBRE, PRECIO, SERVICIOS_DE_RESERVA_ID) VALUES 
(4, 'Baño y secado premium con aceites esenciales', 'Baño premium', 45.00, 2),
(5, 'Corte de pelo premium con estilo', 'Corte premium', 50.00, 2),
(6, 'Tratamiento dental para perros', 'Limpieza dental', 40.00, 2);

-- Servicios adicionales
INSERT INTO SERVICIO_ENTITY (ID, DESCRIPCION, NOMBRE, PRECIO, SERVICIOS_DE_RESERVA_ID) VALUES 
(7, 'Masaje relajante para perros', 'Masaje', 35.00, 3),
(8, 'Sesión de entrenamiento básico', 'Entrenamiento', 60.00, 3),
(9, 'Spa completo para perros', 'Spa completo', 120.00, 3);

//PAQUETES
INSERT INTO PAQUETE_ENTITY (ID, NOMBRE, PRECIO) VALUES 
(1, 'Paquete básico', 50.00),
(2, 'Paquete premium', 100.00),
(3, 'Paquete deluxe', 150.00),
(4, 'Paquete super deluxe', 200.00),
(5, 'Paquete spa total', 250.00),
(6, 'Paquete relax', 75.00),
(7, 'Paquete entrenamiento y spa', 175.00),
(8, 'Paquete cuidados especiales', 120.00),
(9, 'Paquete limpieza y estilo', 90.00),
(10, 'Paquete entrenamiento avanzado', 200.00);

//PAQUETES_SEDES
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (1, 1);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (1, 2);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (1, 3);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (1, 4);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (2, 4);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (2, 1);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (2, 2);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (3, 3);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (4, 4);
INSERT INTO PAQUETE_ENTITY_SEDES (PAQUETES_DE_SERVICIOS_ID, SEDES_ID) VALUES (3, 4);

//PAQUETES_SERVICIOS
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (1, 1);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (1, 2);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (1, 3);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (1, 4);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (2, 4);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (2, 1);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (2, 2);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (3, 3);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (4, 4);
INSERT INTO SERVICIO_ENTITY_PAQUETES (SERVICIOS_ID, PAQUETES_ID) VALUES (3, 4);


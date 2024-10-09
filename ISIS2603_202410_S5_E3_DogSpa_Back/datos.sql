-- Para poner en http://localhost:8080/api/h2-console para insertar los datos. (Primero hay que correr el back)

--SERVICIO
INSERT INTO SERVICIO_ENTITY (ID, DESCRIPCION, IMAGEN, NOMBRE, PRECIO, SERVICIOS_DE_RESERVA_ID) VALUES
(1, 'Servicio de cuidado diario', 'https://images.pexels.com/photos/1573919/pexels-photo-1573919.jpeg', 'Cuidado Diario', 30.00, null),
(2, 'Servicio de paseo', 'https://images.pexels.com/photos/1254140/pexels-photo-1254140.jpeg', 'Paseo', 20.00, null),
(3, 'Servicio de entrenamiento básico','https://images.pexels.com/photos/2197906/pexels-photo-2197906.jpeg', 'Entrenamiento Básico', 50.00, null),
(4, 'Servicio de peluquería', 'https://images.pexels.com/photos/3361739/pexels-photo-3361739.jpeg', 'Peluquería', 40.00, null),
(5, 'Servicio de veterinaria', 'https://images.pexels.com/photos/2623968/pexels-photo-2623968.jpeg', 'Veterinaria', 60.00, null),
(6, 'Servicio de adiestramiento avanzado', 'https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg', 'Adiestramiento Avanzado', 100.00, null),
(7, 'Servicio de hospedaje', 'https://images.pexels.com/photos/1458925/pexels-photo-1458925.jpeg', 'Hospedaje', 80.00, null),
(8, 'Servicio de guardería', 'https://images.pexels.com/photos/1562983/pexels-photo-1562983.jpeg', 'Guardería', 25.00, null),
(9, 'Servicio de fisioterapia', 'https://images.pexels.com/photos/1906153/pexels-photo-1906153.jpeg', 'Fisioterapia', 70.00, null);
(10, 'Servicio de grooming', 'https://images.pexels.com/photos/1954515/pexels-photo-1954515.jpeg', 'Grooming', 40.00, null);
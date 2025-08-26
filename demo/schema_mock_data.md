### 1. Roles y Gestión de Usuarios

```sql
-- Roles
INSERT INTO Role (id, name, description) VALUES
(1, 'CLIENT', 'Huésped del hotel con acceso a reservas y servicios'),
(2, 'ADMIN', 'Acceso completo de administración del sistema'),
(3, 'OPERATOR', 'Personal del hotel con acceso operacional');
```

### 1.1. Usuarios del Sistema

```sql

-- Usuarios colombianos con datos auténticos
INSERT INTO User (user_id, email, password, full_name, phone, national_id, selected_pet, created_at, enabled) VALUES
(1, 'carlos.mendoza@gmail.com', '$2a$10$encrypted_password_hash', 'Carlos Mendoza', '+57-300-1234567', '1234567890', 'DOG', CURRENT_TIMESTAMP, true),
(2, 'maria.rodriguez@hotmail.com', '$2a$10$encrypted_password_hash', 'María Rodríguez', '+57-310-9876543', '0987654321', 'CAT', CURRENT_TIMESTAMP, true),
(3, 'andres.gomez@yahoo.com', '$2a$10$encrypted_password_hash', 'Andrés Gómez', '+57-320-5555555', '5555555555', 'DOG', CURRENT_TIMESTAMP, true),
(4, 'lucia.herrera@gmail.com', '$2a$10$encrypted_password_hash', 'Lucía Herrera', '+57-315-7777777', '7777777777', 'BIRD', CURRENT_TIMESTAMP, true),
(5, 'diego.vargas@outlook.com', '$2a$10$encrypted_password_hash', 'Diego Vargas', '+57-301-8888888', '8888888888', 'DOG', CURRENT_TIMESTAMP, true),
(6, 'sofia.castro@gmail.com', '$2a$10$encrypted_password_hash', 'Sofía Castro', '+57-311-2222222', '2222222222', 'CAT', CURRENT_TIMESTAMP, true),
(7, 'miguel.torres@yahoo.com', '$2a$10$encrypted_password_hash', 'Miguel Torres', '+57-321-3333333', '3333333333', 'BIRD', CURRENT_TIMESTAMP, true),
(8, 'isabella.moreno@hotmail.com', '$2a$10$encrypted_password_hash', 'Isabella Moreno', '+57-312-4444444', '4444444444', 'DOG', CURRENT_TIMESTAMP, true);

-- Asignación de roles a usuarios colombianos
INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- Carlos Mendoza como cliente
(2, 1), -- María Rodríguez como cliente
(3, 1), -- Andrés Gómez como cliente
(4, 1), -- Lucía Herrera como cliente
(5, 2), -- Diego Vargas como administrador
(6, 1), -- Sofía Castro como cliente
(7, 3), -- Miguel Torres como operador
(8, 1); -- Isabella Moreno como cliente
```

### 2. Hoteles Colombianos (5 Destinos)

```sql
-- Hoteles representando destinos colombianos
INSERT INTO Hotel (id, name, description, address, city, state, country, phone, email, latitude, longitude, created_at) VALUES
(1, 'Runa Sagrada Cartagena', 'Hotel boutique en el corazón de la ciudad amurallada, fusionando encanto colonial con lujo moderno', 'Centro Histórico, Cartagena', 'Cartagena', 'Bolívar', 'Colombia', '+57-5-664-9999', 'cartagena@runasagrada.com', 10.3910, -75.4794, NOW()),
(2, 'Runa Sagrada Eje Cafetero', 'Hotel de plantación cafetera que ofrece auténtica cultura paisa y vistas montañosas impresionantes', 'Vereda La Esperanza, Manizales', 'Manizales', 'Caldas', 'Colombia', '+57-6-887-5555', 'ejecafetero@runasagrada.com', 5.0689, -75.5174, NOW()),
(3, 'Runa Sagrada San Andrés', 'Resort frente al mar celebrando la cultura raizal con acceso al mar de siete colores', 'Bahía Sardina, San Andrés', 'San Andrés', 'San Andrés y Providencia', 'Colombia', '+57-8-512-7777', 'sanandres@runasagrada.com', 12.5847, -81.7005, NOW()),
(4, 'Runa Sagrada Santa Marta', 'Eco-lodge puerta de entrada al Parque Nacional Tayrona y comunidades indígenas de la Sierra Nevada', 'Sector Neguanje, Tayrona', 'Santa Marta', 'Magdalena', 'Colombia', '+57-5-421-8888', 'santamarta@runasagrada.com', 11.3167, -74.1167, NOW()),
(5, 'Runa Sagrada Villa de Leyva', 'Hotel mansión colonial preservando el patrimonio boyacense con talleres artesanales', 'Plaza Mayor, Villa de Leyva', 'Villa de Leyva', 'Boyacá', 'Colombia', '+57-8-732-6666', 'villadeleyva@runasagrada.com', 5.6333, -73.5333, NOW());
```

### 3. Tipos de Habitaciones Temáticas Colombianas (4 Categorías por Hotel)

```sql
-- Tipos de habitaciones con temática regional colombiana
INSERT INTO RoomType (id, name, description, base_price, max_occupancy, amenities) VALUES
(1, 'Estándar Regional', 'Habitaciones cómodas con decoración típica de cada región colombiana', 120000, 2, 'WiFi, AC, Baño privado, Arte local, Textiles regionales'),
(2, 'Deluxe Cultural', 'Habitaciones amplias con elementos culturales auténticos de la región', 180000, 3, 'WiFi, AC, Minibar, Balcón, Mobiliario artesanal, Biblioteca regional'),
(3, 'Suite Ancestral', 'Suites de lujo con sala separada y diseño premium colombiano', 280000, 4, 'WiFi, AC, Minibar, Sala de estar, Ropa de cama premium, Colección cultural'),
(4, 'Familiar Colombiana', 'Habitaciones familiares amplias con espacios conectados y temática local', 220000, 6, 'WiFi, AC, Cocineta, Literas, Juegos tradicionales, Juguetes artesanales');
```

### 3.1. Habitaciones Temáticas por Destino Colombiano

```sql
-- Habitaciones específicas para cada hotel con temática regional

-- CARTAGENA DE INDIAS - Temática Colonial Caribeña
INSERT INTO Room (id, hotel_id, room_type_id, room_number, floor_number, res_status, cle_status, theme_name, theme_description) VALUES
-- Piso 1: Estándar Colonial
(1, 1, 1, '101', 1, 'AVAILABLE', 'CLEAN', 'Balcones Coloniales', 'Habitación con balcón típico cartagenero y vista a calles empedradas'),
(2, 1, 1, '102', 1, 'AVAILABLE', 'DIRTY', 'Murallas Históricas', 'Decoración inspirada en las fortificaciones de Cartagena'),
(3, 1, 1, '103', 1, 'AVAILABLE', 'DIRTY', 'Getsemaní Colorido', 'Colores vibrantes del barrio más bohemio de Cartagena'),
(4, 1, 1, '104', 1, 'AVAILABLE', 'CLEAN', 'Plaza de Armas', 'Ambiente de la plaza principal con elementos militares coloniales'),

-- Piso 2: Deluxe Caribeño
(5, 1, 2, '201', 2, 'AVAILABLE', 'CLEAN', 'Palacio del Marqués', 'Elegancia aristocrática de los palacios cartageneros'),
(6, 1, 2, '202', 2, 'BOOKED', 'CLEAN', 'Bóvedas del Mar', 'Inspirada en las bóvedas que protegían del mar Caribe'),
(7, 1, 2, '203', 2, 'AVAILABLE', 'DIRTY', 'Jardín Tropical', 'Exuberante vegetación caribeña en espacios interiores'),
(8, 1, 2, '204', 2, 'AVAILABLE', 'CLEAN', 'Brisa Marina', 'Elementos náuticos y vista panorámica al mar'),

-- EJE CAFETERO - Temática Paisa y Cafetera
(9, 2, 1, '101', 1, 'AVAILABLE', 'CLEAN', 'Finca Cafetera', 'Decoración con elementos de la cultura cafetera tradicional'),
(10, 2, 1, '102', 1, 'BOOKED', 'DIRTY', 'Bahareque Paisa', 'Arquitectura típica paisa con bahareque y guadua'),
(11, 2, 2, '201', 2, 'AVAILABLE', 'CLEAN', 'Valle del Cocora', 'Inspirada en el valle de las palmas de cera'),
(12, 2, 2, '202', 2, 'AVAILABLE', 'DIRTY', 'Pueblo Patrimonio', 'Elementos de los pueblos patrimonio del Eje Cafetero'),

-- SAN ANDRÉS - Temática Raizal y Caribeña
(13, 3, 1, '101', 1, 'AVAILABLE', 'CLEAN', 'Mar de Siete Colores', 'Paleta cromática del famoso mar de San Andrés'),
(14, 3, 1, '102', 1, 'AVAILABLE', 'CLEAN', 'Cultura Raizal', 'Elementos auténticos de la cultura raizal isleña'),
(15, 3, 2, '201', 2, 'BOOKED', 'DIRTY', 'Johnny Cay', 'Inspirada en la famosa isla con arena blanca'),
(16, 3, 2, '202', 2, 'AVAILABLE', 'CLEAN', 'Hoyo Soplador', 'Elementos naturales únicos de la isla'),

-- SANTA MARTA - Temática Tayrona y Sierra Nevada
(17, 4, 1, '101', 1, 'AVAILABLE', 'DIRTY', 'Tayrona Ancestral', 'Elementos de la civilización Tayrona precolombina'),
(18, 4, 1, '102', 1, 'AVAILABLE', 'CLEAN', 'Sierra Nevada', 'Inspirada en la montaña costera más alta del mundo'),
(19, 4, 2, '201', 2, 'BOOKED', 'CLEAN', 'Ciudad Perdida', 'Temática arqueológica de Teyuna'),
(20, 4, 2, '202', 2, 'AVAILABLE', 'DIRTY', 'Kogui Sagrado', 'Elementos de la cultura indígena Kogui'),

-- VILLA DE LEYVA - Temática Colonial Boyacense
(21, 5, 1, '101', 1, 'AVAILABLE', 'CLEAN', 'Plaza Mayor', 'Inspirada en la plaza empedrada más grande de Colombia'),
(22, 5, 1, '102', 1, 'AVAILABLE', 'CLEAN', 'Casa Colonial', 'Arquitectura colonial boyacense auténtica'),
(23, 5, 2, '201', 2, 'BOOKED', 'DIRTY', 'Ruta de los Fósiles', 'Temática paleontológica única de la región'),
(24, 5, 2, '202', 2, 'AVAILABLE', 'CLEAN', 'Observatorio Muisca', 'Elementos astronómicos de la cultura muisca');
```

### 5. Amenidades Colombianas

```sql
-- Amenidades del hotel reflejando la hospitalidad colombiana
INSERT INTO Amenity (id, name, description, category) VALUES
(1, 'Piscina Infinity', 'Piscina infinita con vistas panorámicas', 'Recreación'),
(2, 'Spa Ancestral', 'Terapias de sanación tradicional con técnicas indígenas', 'Bienestar'),
(3, 'Restaurante Gourmet', 'Gastronomía fina con cocina regional colombiana', 'Gastronomía'),
(4, 'Bar de Cócteles', 'Cócteles artesanales con frutas y licores locales', 'Gastronomía'),
(5, 'Taller de Artesanías', 'Talleres prácticos con artesanos locales', 'Cultural'),
(6, 'Biblioteca Cultural', 'Colección de literatura e historia colombiana', 'Cultural'),
(7, 'Gimnasio Eco', 'Centro de fitness con equipos sostenibles', 'Fitness'),
(8, 'Jardín Botánico', 'Jardín de plantas nativas con hierbas medicinales', 'Naturaleza'),
(9, 'Salón de Eventos', 'Espacio versátil para celebraciones y reuniones', 'Negocios'),
(10, 'Terraza Mirador', 'Terraza en azotea con vistas regionales', 'Recreación'),
(11, 'Centro de Negocios', 'Centro de negocios con tecnología moderna', 'Negocios'),
(12, 'Servicio de Concierge', 'Asistencia personalizada al huésped 24/7', 'Servicio');
```

### 6. Asignaciones de Amenidades por Hotel

```sql
-- Asignar amenidades a hoteles (cada hotel obtiene 8-10 amenidades)
INSERT INTO hotel_amenities (hotel_id, amenity_id) VALUES
-- Cartagena (Lujo colonial)
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 9), (1, 10), (1, 12),
-- Eje Cafetero (Cultura cafetera)
(2, 1), (2, 2), (2, 3), (2, 5), (2, 6), (2, 7), (2, 8), (2, 11), (2, 12),
-- San Andrés (Resort playero)
(3, 1), (3, 2), (3, 3), (3, 4), (3, 7), (3, 8), (3, 9), (3, 10), (3, 12),
-- Santa Marta (Eco-aventura)
(4, 1), (4, 2), (4, 3), (4, 5), (4, 7), (4, 8), (4, 10), (4, 11), (4, 12),
-- Villa de Leyva (Patrimonio cultural)
(5, 2), (5, 3), (5, 4), (5, 5), (5, 6), (5, 8), (5, 9), (5, 11), (5, 12);
```

### 7. Servicios Gastronómicos Colombianos (24 Elementos)

```sql
-- Cocina regional colombiana (24 elementos gastronómicos)
INSERT INTO ServiceOffering (id, name, description, category, subcategory, base_price, duration_minutes, max_participants, hotel_id) VALUES
-- Platos Principales (8 elementos)
(1, 'Bandeja Paisa Tradicional', 'Plato paisa completo con frijoles, arroz, chicharrón y arepa', 'Gastronomía', 'Plato Principal', 35000, 60, 50, 2),
(2, 'Sancocho Costeño', 'Guiso tradicional costeño con pescado, yuca y plátano', 'Gastronomía', 'Plato Principal', 32000, 45, 40, 1),
(3, 'Ajiaco Santafereño', 'Sopa de pollo y papa estilo Bogotá con mazorca y alcaparras', 'Gastronomía', 'Plato Principal', 28000, 45, 45, 5),
(4, 'Pescado Frito Isleño', 'Pescado frito con arroz de coco y patacones, estilo San Andrés', 'Gastronomía', 'Plato Principal', 38000, 50, 35, 3),
(5, 'Lechona Tolimense', 'Cerdo relleno asado con arroz y arvejas, tradición tolimense', 'Gastronomía', 'Plato Principal', 42000, 60, 30, 4),
(6, 'Mondongo Antioqueño', 'Sopa tradicional de mondongo con verduras y hierbas', 'Gastronomía', 'Plato Principal', 30000, 50, 25, 2),
(7, 'Cazuela de Mariscos', 'Cazuela de mariscos con leche de coco, estilo caribeño', 'Gastronomía', 'Plato Principal', 45000, 55, 30, 1),
(8, 'Trucha a la Plancha', 'Trucha a la plancha con hierbas de los Andes', 'Gastronomía', 'Plato Principal', 36000, 40, 35, 5),

-- Postres (8 elementos)
(9, 'Tres Leches Costeño', 'Torta de tres leches con frutas tropicales', 'Gastronomía', 'Postre', 15000, 20, 60, 1),
(10, 'Arequipe con Brevas', 'Brevas con dulce de leche, especialidad antioqueña', 'Gastronomía', 'Postre', 12000, 15, 50, 2),
(11, 'Cocadas Isleñas', 'Dulces de coco de la tradición isleña de San Andrés', 'Gastronomía', 'Postre', 8000, 10, 80, 3),
(12, 'Cuajada con Melao', 'Queso fresco con miel de caña, estilo boyacense', 'Gastronomía', 'Postre', 10000, 15, 40, 5),
(13, 'Torta de Natas', 'Torta de natas de la costa caribeña', 'Gastronomía', 'Postre', 14000, 20, 45, 1),
(14, 'Manjar Blanco', 'Delicia blanca del Valle del Cauca', 'Gastronomía', 'Postre', 11000, 15, 55, 4),
(15, 'Postre de Níspero', 'Postre de níspero de la región cafetera', 'Gastronomía', 'Postre', 13000, 18, 35, 2),
(16, 'Dulce de Papayuela', 'Conserva dulce de papayuela de montaña', 'Gastronomía', 'Postre', 9000, 12, 60, 5),

-- Bebidas (8 elementos)
(17, 'Café de Origen Especial', 'Café de origen único de fincas locales', 'Gastronomía', 'Bebida', 8000, 15, 100, 2),
(18, 'Agua de Panela con Limón', 'Agua de panela con limón, refresco tradicional', 'Gastronomía', 'Bebida', 5000, 10, 120, 5),
(19, 'Jugo de Corozo', 'Jugo de fruta de palma corozo, especialidad caribeña', 'Gastronomía', 'Bebida', 7000, 10, 80, 1),
(20, 'Chicha de Maíz', 'Bebida tradicional de maíz de cultura indígena', 'Gastronomía', 'Bebida', 6000, 15, 60, 4),
(21, 'Lulada Vallecaucana', 'Bebida de lulo con hielo y limón', 'Gastronomía', 'Bebida', 8000, 10, 90, 4),
(22, 'Chocolate Santafereño', 'Chocolate caliente con queso, tradición bogotana', 'Gastronomía', 'Bebida', 9000, 20, 70, 5),
(23, 'Coco Loco Isleño', 'Cóctel de coco con ron local', 'Gastronomía', 'Bebida', 15000, 15, 40, 3),
(24, 'Aguardiente Antioqueño', 'Experiencia de degustación de licor de anís tradicional', 'Gastronomía', 'Bebida', 12000, 30, 25, 2);
```

### 8. Tours Culturales Colombianos (15 Tours - 3 por Hotel)

```sql
-- Tours regionales mostrando cultura y naturaleza colombiana (15 tours)
INSERT INTO ServiceOffering (id, name, description, category, subcategory, base_price, duration_minutes, max_participants, hotel_id) VALUES
-- Tours Cartagena (3)
(25, 'Tour Ciudad Amurallada', 'Tour caminando por la Cartagena colonial con perspectivas históricas', 'Tour', 'Cultural', 45000, 180, 20, 1),
(26, 'Islas del Rosario', 'Excursión en bote a islas coralinas con snorkel y tiempo de playa', 'Tour', 'Naturaleza', 85000, 480, 15, 1),
(27, 'Palenque Cultural', 'Visita a San Basilio de Palenque, primer pueblo africano libre en América', 'Tour', 'Cultural', 65000, 360, 12, 1),

-- Tours Eje Cafetero (3)
(28, 'Finca Cafetera Tradicional', 'Experiencia en finca cafetera con cosecha y degustación', 'Tour', 'Cultural', 55000, 240, 18, 2),
(29, 'Valle de Cocora', 'Caminata por bosque de palmas de cera, árbol nacional de Colombia', 'Tour', 'Naturaleza', 70000, 300, 16, 2),
(30, 'Pueblo Patrimonio Salamina', 'Tour por pueblo colonial con arquitectura tradicional y artesanías', 'Tour', 'Cultural', 40000, 180, 25, 2),

-- Tours San Andrés (3)
(31, 'Hoyo Soplador y Cueva Morgan', 'Exploración de géiser natural y cueva de piratas', 'Tour', 'Naturaleza', 35000, 120, 30, 3),
(32, 'Cultura Raizal', 'Inmersión en cultura raizal con música, danza y gastronomía', 'Tour', 'Cultural', 50000, 240, 20, 3),
(33, 'Acuario y Johnny Cay', 'Viaje en bote a acuario natural y playa prístina', 'Tour', 'Naturaleza', 75000, 360, 25, 3),

-- Tours Santa Marta (3)
(34, 'Tayrona Ancestral', 'Caminata a sitios arqueológicos indígenas Tayrona', 'Tour', 'Cultural', 80000, 420, 14, 4),
(35, 'Ciudad Perdida Teyuna', 'Caminata de varios días a la Ciudad Perdida de la civilización Tayrona', 'Tour', 'Aventura', 450000, 2880, 8, 4),
(36, 'Avistamiento de Aves Sierra Nevada', 'Observación de aves en la cordillera costera más alta del mundo', 'Tour', 'Naturaleza', 60000, 300, 12, 4),

-- Tours Villa de Leyva (3)
(37, 'Ruta de los Fósiles', 'Tour paleontológico con descubrimientos de fósiles y museos', 'Tour', 'Educativo', 35000, 180, 20, 5),
(38, 'Observatorio Astronómico', 'Experiencia de observación estelar con perspectivas astronómicas precolombinas', 'Tour', 'Educativo', 45000, 150, 15, 5),
(39, 'Viñedos Boyacenses', 'Tour de degustación de vinos en viñedos de alta altitud de Boyacá', 'Tour', 'Cultural', 65000, 240, 18, 5);
```

### 9. Horarios de Servicios

```sql
-- Horarios de muestra para tours y gastronomía
INSERT INTO ServiceSchedule (id, service_offering_id, day_of_week, start_time, end_time, is_active) VALUES
-- Servicios gastronómicos diarios
(1, 1, 'DAILY', '12:00:00', '15:00:00', true), -- Almuerzo Bandeja Paisa
(2, 17, 'DAILY', '06:00:00', '10:00:00', true), -- Servicio de café
(3, 22, 'DAILY', '07:00:00', '09:00:00', true), -- Desayuno de chocolate

-- Horarios de tours
(4, 25, 'DAILY', '09:00:00', '12:00:00', true), -- Tour caminando Cartagena
(5, 28, 'MONDAY,WEDNESDAY,FRIDAY', '08:00:00', '12:00:00', true), -- Finca cafetera
(6, 31, 'TUESDAY,THURSDAY,SATURDAY', '14:00:00', '16:00:00', true), -- Naturaleza San Andrés
(7, 34, 'SATURDAY,SUNDAY', '06:00:00', '13:00:00', true), -- Caminata Tayrona
(8, 37, 'DAILY', '10:00:00', '13:00:00', true); -- Ruta de fósiles
```

### 10. Métodos de Pago

```sql
-- Métodos de pago para el mercado colombiano
INSERT INTO PaymentMethod (id, name, description, is_active, processing_fee) VALUES
(1, 'Efectivo', 'Pago en efectivo en pesos colombianos', true, 0.00),
(2, 'Tarjeta de Crédito', 'Pagos con tarjeta de crédito (Visa, Mastercard)', true, 3.50),
(3, 'Tarjeta Débito', 'Pagos con tarjeta débito', true, 2.00),
(4, 'PSE', 'Pagos Seguros en Línea - banca en línea colombiana', true, 2.50),
(5, 'Nequi', 'Billetera digital popular en Colombia', true, 1.50),
(6, 'Daviplata', 'Plataforma de pagos digitales de Davivienda', true, 1.50);
```

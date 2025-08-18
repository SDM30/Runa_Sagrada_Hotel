package com.runasagrada.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.runasagrada.demo.entities.HotelService;

@Repository
public class HotelServiceRepository {
        // Base de datos falsa
        private Map<Integer, HotelService> data = new HashMap<>();

        public HotelServiceRepository() {
                // Existing items
                data.put(1, new HotelService(
                                1,
                                "Gastronomía Ancestral",
                                "Comida",
                                "Sabores auténticos de la cocina tradicional colombiana, preparados con ingredientes locales y técnicas ancestrales.",
                                LocalDate.now().plusDays(1),
                                LocalTime.of(19, 0),
                                20,
                                45.99,
                                "Disponible",
                                "https://images.unsplash.com/photo-1544025162-d76694265947?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTB8fGNvbG9tYmlhbiUyMGZvb2R8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=80",
                                10.3910,
                                -75.4794));

                data.put(2, new HotelService(
                                2,
                                "Tours Sagrados",
                                "Tours",
                                "Expediciones guiadas por lugares místicos y sitios arqueológicos, conectando con la sabiduría ancestral.",
                                LocalDate.now().plusDays(1),
                                LocalTime.of(8, 30),
                                15,
                                65.50,
                                "Disponible",
                                "https://images.unsplash.com/photo-1582979512210-99b6a53386f3?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Y29sb21iaWFuJTIwYXJjaGFlb2xvZ3l8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=80",
                                5.6333,
                                -73.5333));

                data.put(3, new HotelService(
                                3,
                                "Rituales de Bienestar",
                                "Hotel",
                                "Terapias tradicionales y ceremonias de sanación inspiradas en las prácticas indígenas colombianas.",
                                LocalDate.now().plusDays(1),
                                LocalTime.of(16, 0),
                                10,
                                75.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1605291535559-9a6bf0b8f9e1?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8c3Bpcml0dWFsJTIwY2VyZW1vbnl8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=80",
                                4.7109,
                                -74.0721));

                data.put(4, new HotelService(
                                4,
                                "Hospedaje Boutique",
                                "Hotel",
                                "Habitaciones únicas diseñadas con elementos artesanales y decoración inspirada en culturas precolombinas.",
                                LocalDate.now(),
                                LocalTime.of(15, 0),
                                1,
                                120.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Nnx8Ym91dGlxdWUlMjBob3RlbHxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=80",
                                4.6370,
                                -75.5710));

                data.put(5, new HotelService(
                                5,
                                "Ecoturismo",
                                "Tours",
                                "Experiencias sostenibles que preservan y celebran la biodiversidad única de los ecosistemas colombianos.",
                                LocalDate.now().plusDays(2),
                                LocalTime.of(9, 0),
                                12,
                                55.75,
                                "Disponible",
                                "https://images.unsplash.com/photo-1588943211346-0908a1fb0b01?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8Y29sb21iaWFuJTIwbmF0dXJlfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=80",
                                12.5847,
                                -81.7005));

                data.put(6, new HotelService(
                                6,
                                "Cultura Viva",
                                "Tours",
                                "Talleres de artesanías, música tradicional y danzas folclóricas con maestros de comunidades locales.",
                                LocalDate.now().plusDays(3),
                                LocalTime.of(14, 0),
                                25,
                                35.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1517457373958-b7bdd4587205?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8Y29sb21iaWFuJTIwY3VsdHVyZXxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=80",
                                11.2408,
                                -74.1990));

                data.put(7, new HotelService(
                                7,
                                "Ceremonia del Cacao Sagrado",
                                "Tours",
                                "Participa en un ritual ancestral de conexión espiritual con el cacao como elemento sagrado.",
                                LocalDate.now().plusDays(2),
                                LocalTime.of(17, 30),
                                8,
                                85.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1598890777035-a9fe7d1b1d66?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8Y2FjYW8lMjBjZXJlbW9ueXxlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=80",
                                5.6333,
                                -73.5333));

                data.put(8, new HotelService(
                                8,
                                "Avistamiento de Aves",
                                "Tours",
                                "Descubre la biodiversidad de Colombia a través de sus especies de aves más representativas.",
                                LocalDate.now().plusDays(1),
                                LocalTime.of(6, 0),
                                10,
                                40.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1551085254-e96b210db58a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8OHx8YmlyZCUyMHdhdGNoaW5nfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=80",
                                4.7109,
                                -74.0721));

                data.put(9, new HotelService(
                                9,
                                "Senderismo Místico",
                                "Tours",
                                "Explora caminos ancestrales y conecta con la naturaleza en rutas llenas de energía y tradición.",
                                LocalDate.now().plusDays(2),
                                LocalTime.of(7, 0),
                                15,
                                50.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1586015555751-63bb77f4322a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGNvbG9tYmlhbiUyMGhpa2luZ3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=80",
                                11.2408,
                                -74.1990));

                // New Hotel items
                data.put(10, new HotelService(
                                10,
                                "Suite Presidencial",
                                "Hotel",
                                "La experiencia más exclusiva con vista panorámica, jacuzzi privado y servicio de mayordomo 24/7.",
                                LocalDate.now(),
                                LocalTime.of(15, 0),
                                1,
                                350.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1564501049412-61c2a3083791?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTF8fGx1eHVyeSUyMGhvdGVsfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=80",
                                4.6014,
                                -74.0661));

                data.put(11, new HotelService(
                                11,
                                "Cabañas Ecológicas",
                                "Hotel",
                                "Alojamiento sostenible en medio de la naturaleza, construido con materiales autóctonos y energía solar.",
                                LocalDate.now(),
                                LocalTime.of(14, 0),
                                5,
                                95.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1605346576608-92f1346b67d6?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8ZWNvJTIwY2FiaW5zfGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=80",
                                6.2442,
                                -75.5736));

                // New Comida items
                data.put(12, new HotelService(
                                12,
                                "Taller de Café Premium",
                                "Comida",
                                "Aprende sobre el proceso del café colombiano desde el grano hasta la taza, con cata guiada.",
                                LocalDate.now().plusDays(2),
                                LocalTime.of(10, 0),
                                8,
                                30.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1445116572660-236099ec97a0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTh8fGNvZmZlZSUyMHRhc3Rpbmd8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&w=800&q=80",
                                5.0689,
                                -75.5174));

                data.put(13, new HotelService(
                                13,
                                "Cena con Chef Estrella",
                                "Comida",
                                "Menú degustación de 7 platos con maridaje de vinos, preparado por nuestro chef galardonado.",
                                LocalDate.now().plusDays(3),
                                LocalTime.of(20, 0),
                                12,
                                120.00,
                                "Disponible",
                                "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8ZmluZSUyMGRpbmluZ3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=800&q=80",
                                6.2518,
                                -75.5636));

                data.put(14, new HotelService(
                                14,
                                "Desayuno Tradicional",
                                "Comida",
                                "Desayuno completo con arepas, huevos pericos, chocolate caliente y frutas tropicales.",
                                LocalDate.now().plusDays(1),
                                LocalTime.of(7, 30),
                                30,
                                15.50,
                                "Disponible",
                                "https://images.unsplash.com/photo-1551504734-5ee1c4a1479b?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8Y29sb21iaWFuJTIwYnJlYWtmYXN0fGVufDB8fDB8fHww&auto=format&fit=crop&w=800&q=80",
                                10.9639,
                                -74.7964));
        }

        public HotelService getServiceById(int id) {
                return data.get(id);
        }

        public Collection<HotelService> getAllServices() {
                return data.values();
        }

        public void save(HotelService service) {
                if (service.getId() == null) {
                        int size = data.size();
                        int lastId = data.get(size).getId();
                        service.setId(lastId + 1);
                        data.put(service.getId(), service);
                } else {
                        data.put(service.getId(), service);
                }
        }

        public void delete(int id) {
                data.remove(id);
        }
}

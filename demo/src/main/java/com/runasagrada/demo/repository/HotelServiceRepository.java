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
                                "https://example.com/images/gastronomia.jpg",
                                10.3910, // Latitud Cartagena
                                -75.4794 // Longitud Cartagena
                ));

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
                                "https://example.com/images/tours.jpg",
                                5.6333, // Latitud Villa de Leyva
                                -73.5333 // Longitud Villa de Leyva
                ));

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
                                "https://example.com/images/rituales.jpg",
                                4.7109, // Latitud Bogotá
                                -74.0721 // Longitud Bogotá
                ));

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
                                "https://example.com/images/hospedaje.jpg",
                                4.6370, // Latitud Eje Cafetero
                                -75.5710 // Longitud Eje Cafetero
                ));

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
                                "https://example.com/images/ecoturismo.jpg",
                                12.5847, // Latitud San Andrés
                                -81.7005 // Longitud San Andrés
                ));

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
                                "https://example.com/images/cultura.jpg",
                                11.2408, // Latitud Santa Marta
                                -74.1990 // Longitud Santa Marta
                ));

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
                                "https://example.com/images/cacao.jpg",
                                5.6333, // Latitud Villa de Leyva
                                -73.5333 // Longitud Villa de Leyva
                ));

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
                                "https://example.com/images/aves.jpg",
                                4.7109, // Latitud Bogotá
                                -74.0721 // Longitud Bogotá
                ));

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
                                "https://example.com/images/senderismo.jpg",
                                11.2408, // Latitud Santa Marta
                                -74.1990 // Longitud Santa Marta
                ));
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

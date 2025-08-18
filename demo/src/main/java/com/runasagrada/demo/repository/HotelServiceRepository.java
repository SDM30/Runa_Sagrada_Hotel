package com.runasagrada.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import org.springframework.stereotype.Repository;
import com.runasagrada.demo.entities.HotelService;

@Repository
public class HotelServiceRepository {
        private Map<Integer, HotelService> data = new HashMap<>();

        // URLs de imágenes organizadas por categoría - Enlaces actualizados y
        // verificados
        private static final class ImageUrls {
                // Gastronomía
                static final String GASTRONOMIA_1 = "https://www.paulinacocina.net/wp-content/uploads/2024/01/arepas-de-huevo-Paulina-Cocina-Recetas-1722251878.jpg";
                static final String GASTRONOMIA_2 = "https://i.blogs.es/bb0cca/bandeja_paisa/1200_900.jpg";
                static final String GASTRONOMIA_3 = "https://www.semana.com/resizer/v2/GBBYJH5YMZC6PEINHE3HZZH4TY.jpg?auth=f21d7fbf15c15316b80dd213fb2c635e4445db8e08133172d69a4956d7f417db&smart=true&quality=75&width=1920&height=1080&fitfill=false";

                // Tours arqueológicos
                static final String TOURS_ARQUEOLOGICOS_1 = "https://paseovallenato.com/wp-content/uploads/nabusimake-y-espiritualidad-top.jpg";
                static final String TOURS_ARQUEOLOGICOS_2 = "https://www.semana.com/resizer/v2/ZRKLGUHQV5GSTLO2SQSLNE7VGI.jpeg?auth=ca2d608992531b9ea91c1cd67c8f03d457ab55a7fae3299013a2a4b23c9baa0e&smart=true&quality=75&width=1280&height=720";
                static final String TOURS_ARQUEOLOGICOS_3 = "https://portalenlace.com.co/wp-content/uploads/2024/04/nabusimake.jpeg";

                // Bienestar y rituales
                static final String RITUALES_1 = "https://blumont.org/wp-content/uploads/2020/02/Apagada-del-fuego_17_VPeretti-1024x683.jpg";
                static final String RITUALES_2 = "https://www.cric-colombia.org/portal/wp-content/uploads/2024/06/IMG-20240621-WA0120-scaled.jpg";
                static final String RITUALES_3 = "https://www.cric-colombia.org/portal/wp-content/uploads/2018/09/02.-Once-a%C3%B1os-practicando-y-vivenciando-el-ritual-mayor-del-pueblo-nasa-el-sakhelu-territorio-de-Jambal%C3%B3.jpg";

                // Hospedaje boutique
                static final String HOSPEDAJE_BOUTIQUE_1 = "https://www.estelarsantamar.com/media/uploads/cms_apps/imagenes/_URV1496_M_OK_2_1_1.jpg?q=pr:sharp/rs:fill/w:1920/h:800/f:jpg";
                static final String HOSPEDAJE_BOUTIQUE_2 = "https://www.planessantamarta.com.co/wp-content/uploads/2023/11/habitacion-doble-con-vista-al-mar-hotel-be-la-sierra2.jpg";
                static final String HOSPEDAJE_BOUTIQUE_3 = "https://be-la-sierra-santa-marta.santamarta-choice-hotels.com/data/Images/OriginalPhoto/12137/1213775/1213775257/image-santa-marta-magdalena-hotel-be-la-sierra-46.JPEG";

                // Ecoturismo
                static final String ECOTURISMO_1 = "https://elsolazsuites.com/wp-content/uploads/2022/06/ecoturismo-en-villa-de-leyva.jpg";
                static final String ECOTURISMO_2 = "https://tutourencartagena.com/wp-content/uploads/2017/01/buceo-en-cartagena-cartagena-colombia-tutourencartagena.jpg";
                static final String ECOTURISMO_3 = "https://radionacional-v3.s3.amazonaws.com/s3fs-public/styles/portadas_relaciona_4_3/public/node/article/field_image/San%20Andr%C3%A9s%20Colprensa.jpg?h=96a96008&itok=31WwVuLy      ";

                // Cultura viva
                static final String CULTURA_1 = "https://www.infobae.com/resizer/v2/H4BSBL5F7JEH7ELIPDGLKO5OBQ.jpg?auth=da6890b5ced46d170fe76fcc186b721e5495c108b33bec0fff4cfd53e527e538&smart=true&width=1200&height=900&quality=85";
                static final String CULTURA_2 = "https://live.staticflickr.com/4136/4925539026_db69e6ec6e_b.jpg";
                static final String CULTURA_3 = "https://visitvalle.travel/wp-content/uploads/2024/08/festival-de-la-bandola-sevilla.webp";

                // Ceremonia del cacao
                static final String CACAO_1 = "https://wakana.es/wp-content/uploads/2019/01/M-OF-W-YogaDSCF0152w.jpg";
                static final String CACAO_2 = "https://thehouseofaia.com/wp-content/uploads/2024/02/240111-cacao-img.webp";
                static final String CACAO_3 = "https://bodaespiritual.com/wp-content/uploads/2023/09/ceremonia-del-cacao-1200x646.png";

                // Avistamiento de aves
                static final String AVES_1 = "https://cdn.prod.website-files.com/64df6dd37ac6a0dbb9d03cb3/659bfb376102d36e421df403_6-resultado.jpeg";
                static final String AVES_2 = "https://blogs.eluniversal.com.co/sites/default/files/styles/interna/public/2022-09/colibri-portada-shutterstock_1176281404.jpg?itok=m9cp2BgQ";
                static final String AVES_3 = "https://www.rcnradio.com/_next/image?url=https%3A%2F%2Ffiles.rcnradio.com%2Fpublic%2Fstyles%2F16_9%2Fpublic%2F2024-05%2Fimg_0718_0.jpg%3FVersionId%3DkGlGqZsZOwJW_Cu84R1PGspi.rxCp6h6%26itok%3DBnhqtmHX&w=3840&q=75";

                // Senderismo
                static final String SENDERISMO_1 = "https://www.esariri.com/wp-content/uploads/2022/09/296122789_3527452994148567_1098327290177545856_n.jpg";
                static final String SENDERISMO_2 = "https://bogota.gov.co/sites/default/files/2022-08/la-aguadora.jpg";
                static final String SENDERISMO_3 = "https://colombiavisible.com/wp-content/uploads/2023/04/Senderismo-Bogota-1-1024x576.jpg";

                // Suite presidencial
                static final String SUITE_PRESIDENCIAL_1 = "https://www.sofitelbarucalablanca.com/wp-content/uploads/sites/19/2023/04/DUF_7068-v-ok-1170x780.jpg";
                static final String SUITE_PRESIDENCIAL_2 = "https://s3.amazonaws.com/static-webstudio-accorhotels-usa-1.wp-ha.fastbooking.com/wp-content/uploads/sites/19/2022/03/11175445/DUF_7063-v-ok-1170x780.jpg";
                static final String SUITE_PRESIDENCIAL_3 = "https://www.sofitelbarucalablanca.com/wp-content/uploads/sites/19/2023/04/DUF_7066-v-ok-1170x780.jpg";

                // Cabañas ecológicas
                static final String CABANAS_1 = "https://a0.muscache.com/im/pictures/hosting/Hosting-U3RheVN1cHBseUxpc3Rpbmc6MTgxMTg3OTI=/original/318d3435-c2ea-4b59-94e9-fba4f10b99cd.jpeg";
                static final String CABANAS_2 = "https://a0.muscache.com/im/pictures/miso/Hosting-25479833/original/80ae5655-034b-4573-8b87-9492772cc2c3.jpeg";
                static final String CABANAS_3 = "https://a0.muscache.com/im/pictures/hosting/Hosting-12347806/original/afebb259-a5ce-4057-a93c-b98e200e0678.jpeg";

                // Taller de café
                static final String CAFE_1 = "https://educafes.com/wp-content/uploads/2018/06/whatsapp-image-2018-06-25-at-4-59-55-pm3.jpeg";
                static final String CAFE_2 = "https://educafes.com/wp-content/uploads/2015/12/img_20151130_111827469.jpg";
                static final String CAFE_3 = "https://cafedecolombia.us/wp-content/uploads/2024/10/WhatsApp-Image-2024-10-14-at-6.19.32-PM-scaled.jpeg";

                // Cena con chef
                static final String CHEF_1 = "https://revistamomentos.co/wp-content/uploads/2019/11/Plato-sostenible-1900x1266_c.jpeg";
                static final String CHEF_2 = "https://radionacional-v3.s3.amazonaws.com/s3fs-public/styles/portadas_relaciona_4_3/public/node/article/field_image/COLP_EXT_127797.jpg?h=f639aea9&itok=FrRSntMG";
                static final String CHEF_3 = "https://www.metropolitan-touring.com/wp-content/uploads/2024/04/Cande.webp";

                // Desayuno tradicional
                static final String DESAYUNO_1 = "https://i.ytimg.com/vi/r4FgfmO3zLg/maxresdefault.jpg";
                static final String DESAYUNO_2 = "https://saboresdecolombianj.com/wp-content/uploads/2025/04/HUEVOS-Y-AREPA-CON-QUESO.jpg";
                static final String DESAYUNO_3 = "https://live.staticflickr.com/6209/6147056621_15c60d28cf_b.jpg";
        }

        public HotelServiceRepository() {
                initializeServices();
        }

        private void initializeServices() {
                // 1. Gastronomía Ancestral
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
                                List.of(ImageUrls.GASTRONOMIA_1, ImageUrls.GASTRONOMIA_2, ImageUrls.GASTRONOMIA_3),
                                10.3910, -75.4794));

                // 2. Tours Sagrados
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
                                List.of(ImageUrls.TOURS_ARQUEOLOGICOS_1, ImageUrls.TOURS_ARQUEOLOGICOS_2,
                                                ImageUrls.TOURS_ARQUEOLOGICOS_3),
                                5.6333, -73.5333));

                // 3. Rituales de Bienestar
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
                                List.of(ImageUrls.RITUALES_1, ImageUrls.RITUALES_2, ImageUrls.RITUALES_3),
                                4.7109, -74.0721));

                // 4. Hospedaje Boutique
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
                                List.of(ImageUrls.HOSPEDAJE_BOUTIQUE_1, ImageUrls.HOSPEDAJE_BOUTIQUE_2,
                                                ImageUrls.HOSPEDAJE_BOUTIQUE_3),
                                4.6370, -75.5710));

                // 5. Ecoturismo
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
                                List.of(ImageUrls.ECOTURISMO_1, ImageUrls.ECOTURISMO_2, ImageUrls.ECOTURISMO_3),
                                12.5847, -81.7005));

                // 6. Cultura Viva
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
                                List.of(ImageUrls.CULTURA_1, ImageUrls.CULTURA_2, ImageUrls.CULTURA_3),
                                11.2408, -74.1990));

                // 7. Ceremonia del Cacao Sagrado
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
                                List.of(ImageUrls.CACAO_1, ImageUrls.CACAO_2, ImageUrls.CACAO_3),
                                5.6333, -73.5333));

                // 8. Avistamiento de Aves
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
                                List.of(ImageUrls.AVES_1, ImageUrls.AVES_2, ImageUrls.AVES_3),
                                4.7109, -74.0721));

                // 9. Senderismo Místico
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
                                List.of(ImageUrls.SENDERISMO_1, ImageUrls.SENDERISMO_2, ImageUrls.SENDERISMO_3),
                                11.2408, -74.1990));

                // 10. Suite Presidencial
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
                                List.of(ImageUrls.SUITE_PRESIDENCIAL_1, ImageUrls.SUITE_PRESIDENCIAL_2,
                                                ImageUrls.SUITE_PRESIDENCIAL_3),
                                4.6014, -74.0661));

                // 11. Cabañas Ecológicas
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
                                List.of(ImageUrls.CABANAS_1, ImageUrls.CABANAS_2, ImageUrls.CABANAS_3),
                                6.2442, -75.5736));

                // 12. Taller de Café Premium
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
                                List.of(ImageUrls.CAFE_1, ImageUrls.CAFE_2, ImageUrls.CAFE_3),
                                5.0689, -75.5174));

                // 13. Cena con Chef Estrella
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
                                List.of(ImageUrls.CHEF_1, ImageUrls.CHEF_2, ImageUrls.CHEF_3),
                                6.2518, -75.5636));

                // 14. Desayuno Tradicional
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
                                List.of(ImageUrls.DESAYUNO_1, ImageUrls.DESAYUNO_2, ImageUrls.DESAYUNO_3),
                                10.9639, -74.7964));
        }

        public HotelService getServiceById(int id) {
                return data.get(id);
        }

        public Collection<HotelService> getAllServices() {
                return data.values();
        }

        public void save(HotelService service) {
                if (service.getId() == null) {
                        int newId = data.size() + 1;
                        service.setId(newId);
                }
                data.put(service.getId(), service);
        }

        public void delete(int id) {
                data.remove(id);
        }
}
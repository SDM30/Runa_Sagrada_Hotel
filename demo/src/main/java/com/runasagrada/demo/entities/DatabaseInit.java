package com.runasagrada.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.runasagrada.demo.repository.ClientRepository;
import com.runasagrada.demo.repository.ServiceOfferingRepository;
import com.runasagrada.demo.repository.HotelUserRepository;
import com.runasagrada.demo.service.ServiceScheduleService;

import jakarta.transaction.Transactional;
import com.runasagrada.demo.repository.RoomRepository;
import com.runasagrada.demo.repository.RoomTypeRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Transactional
public class DatabaseInit implements ApplicationRunner {

	@Autowired
	ServiceOfferingRepository serviceRepository;

	@Autowired
	HotelUserRepository userRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ServiceScheduleService scheduleService;

	@Autowired
	private RoomTypeRepository roomTypeRepository;

	@Autowired
	private RoomRepository roomRepository;

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
		static final String ECOTURISMO_3 = "https://radionacional-v3.s3.amazonaws.com/s3fs-public/styles/portadas_relaciona_4_3/public/node/article/field_image/San%20Andr%C3%A9s%20Colprensa.jpg?h=96a96008&itok=31WwVuLy";

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

	@Override
	public void run(org.springframework.boot.ApplicationArguments args) throws Exception {
		// === Crear servicios (sin fecha/hora/capacidad) ===
		ServiceOffering gastronomia = serviceRepository.save(new ServiceOffering(
				"Gastronomía Ancestral", "Comida",
				"Sabores auténticos de la cocina tradicional colombiana, preparados con ingredientes locales y técnicas ancestrales.",
				List.of(), 45900, "Disponible",
				List.of(ImageUrls.GASTRONOMIA_1, ImageUrls.GASTRONOMIA_2, ImageUrls.GASTRONOMIA_3),
				10.3910, -75.4794));

		ServiceOffering tours = serviceRepository.save(new ServiceOffering(
				"Tours Sagrados", "Tours",
				"Expediciones guiadas por lugares místicos y sitios arqueológicos, conectando con la sabiduría ancestral.",
				List.of(), 65500, "Disponible",
				List.of(ImageUrls.TOURS_ARQUEOLOGICOS_1, ImageUrls.TOURS_ARQUEOLOGICOS_2,
						ImageUrls.TOURS_ARQUEOLOGICOS_3),
				5.6333, -73.5333));

		ServiceOffering rituales = serviceRepository.save(new ServiceOffering(
				"Rituales de Bienestar", "Hotel",
				"Terapias tradicionales y ceremonias de sanación inspiradas en las prácticas indígenas colombianas.",
				List.of(), 75000, "Disponible",
				List.of(ImageUrls.RITUALES_1, ImageUrls.RITUALES_2, ImageUrls.RITUALES_3),
				4.7109, -74.0721));

		ServiceOffering boutique = serviceRepository.save(new ServiceOffering(
				"Hospedaje Boutique", "Hotel",
				"Habitaciones únicas diseñadas con elementos artesanales y decoración inspirada en culturas precolombinas.",
				List.of(), 120000, "Disponible",
				List.of(ImageUrls.HOSPEDAJE_BOUTIQUE_1, ImageUrls.HOSPEDAJE_BOUTIQUE_2, ImageUrls.HOSPEDAJE_BOUTIQUE_3),
				4.6370, -75.5710));

		ServiceOffering ecoturismo = serviceRepository.save(new ServiceOffering(
				"Ecoturismo", "Tours",
				"Experiencias sostenibles que preservan y celebran la biodiversidad única de los ecosistemas colombianos.",
				List.of(), 55750, "Disponible",
				List.of(ImageUrls.ECOTURISMO_1, ImageUrls.ECOTURISMO_2, ImageUrls.ECOTURISMO_3),
				12.5847, -81.7005));

		ServiceOffering cultura = serviceRepository.save(new ServiceOffering(
				"Cultura Viva", "Tours",
				"Talleres de artesanías, música tradicional y danzas folclóricas con maestros de comunidades locales.",
				List.of(), 35000, "Disponible",
				List.of(ImageUrls.CULTURA_1, ImageUrls.CULTURA_2, ImageUrls.CULTURA_3),
				11.2408, -74.1990));

		ServiceOffering cacao = serviceRepository.save(new ServiceOffering(
				"Ceremonia del Cacao Sagrado", "Tours",
				"Participa en un ritual ancestral de conexión espiritual con el cacao como elemento sagrado.",
				List.of(), 85000, "Disponible",
				List.of(ImageUrls.CACAO_1, ImageUrls.CACAO_2, ImageUrls.CACAO_3),
				5.6333, -73.5333));

		ServiceOffering aves = serviceRepository.save(new ServiceOffering(
				"Avistamiento de Aves", "Tours",
				"Descubre la biodiversidad de Colombia a través de sus especies de aves más representativas.",
				List.of(), 40000, "Disponible",
				List.of(ImageUrls.AVES_1, ImageUrls.AVES_2, ImageUrls.AVES_3),
				4.7109, -74.0721));

		ServiceOffering senderismo = serviceRepository.save(new ServiceOffering(
				"Senderismo Místico", "Tours",
				"Explora caminos ancestrales y conecta con la naturaleza en rutas llenas de energía y tradición.",
				List.of(), 50000, "Disponible",
				List.of(ImageUrls.SENDERISMO_1, ImageUrls.SENDERISMO_2, ImageUrls.SENDERISMO_3),
				11.2408, -74.1990));

		ServiceOffering suite = serviceRepository.save(new ServiceOffering(
				"Suite Presidencial", "Hotel",
				"La experiencia más exclusiva con vista panorámica, jacuzzi privado y servicio de mayordomo 24/7.",
				List.of(), 350.00, "Disponible",
				List.of(ImageUrls.SUITE_PRESIDENCIAL_1, ImageUrls.SUITE_PRESIDENCIAL_2, ImageUrls.SUITE_PRESIDENCIAL_3),
				4.6014, -74.0661));

		ServiceOffering cabanas = serviceRepository.save(new ServiceOffering(
				"Cabañas Ecológicas", "Hotel",
				"Alojamiento sostenible en medio de la naturaleza, construido con materiales autóctonos y energía solar.",
				List.of(), 95.00, "Disponible",
				List.of(ImageUrls.CABANAS_1, ImageUrls.CABANAS_2, ImageUrls.CABANAS_3),
				6.2442, -75.5736));

		ServiceOffering cafe = serviceRepository.save(new ServiceOffering(
				"Taller de Café Premium", "Comida",
				"Aprende sobre el proceso del café colombiano desde el grano hasta la taza, con cata guiada.",
				List.of(), 30.00, "Disponible",
				List.of(ImageUrls.CAFE_1, ImageUrls.CAFE_2, ImageUrls.CAFE_3),
				5.0689, -75.5174));

		ServiceOffering chef = serviceRepository.save(new ServiceOffering(
				"Cena con Chef Estrella", "Comida",
				"Menú degustación de 7 platos con maridaje de vinos, preparado por nuestro chef galardonado.",
				List.of(), 120.00, "Disponible",
				List.of(ImageUrls.CHEF_1, ImageUrls.CHEF_2, ImageUrls.CHEF_3),
				6.2518, -75.5636));

		ServiceOffering desayuno = serviceRepository.save(new ServiceOffering(
				"Desayuno Tradicional", "Comida",
				"Desayuno completo con arepas, huevos pericos, chocolate caliente y frutas tropicales.",
				List.of(), 15.50, "Disponible",
				List.of(ImageUrls.DESAYUNO_1, ImageUrls.DESAYUNO_2, ImageUrls.DESAYUNO_3),
				10.9639, -74.7964));

		// === Schedules por servicio: usar scheduleService con un ServiceSchedule base
		// ===
		scheduleService.seedSchedules(
				new ServiceSchedule(gastronomia, LocalDate.now().plusDays(1), LocalTime.of(19, 0), 20), 7);
		scheduleService.seedSchedules(new ServiceSchedule(tours, LocalDate.now().plusDays(1), LocalTime.of(8, 30), 15),
				7);
		scheduleService
				.seedSchedules(new ServiceSchedule(rituales, LocalDate.now().plusDays(1), LocalTime.of(16, 0), 10), 7);
		scheduleService.seedSchedules(new ServiceSchedule(boutique, LocalDate.now(), LocalTime.of(15, 0), 1), 7);
		scheduleService
				.seedSchedules(new ServiceSchedule(ecoturismo, LocalDate.now().plusDays(2), LocalTime.of(9, 0), 12), 7);
		scheduleService
				.seedSchedules(new ServiceSchedule(cultura, LocalDate.now().plusDays(3), LocalTime.of(14, 0), 25), 7);
		scheduleService.seedSchedules(new ServiceSchedule(cacao, LocalDate.now().plusDays(2), LocalTime.of(17, 30), 8),
				7);
		scheduleService.seedSchedules(new ServiceSchedule(aves, LocalDate.now().plusDays(1), LocalTime.of(6, 0), 10),
				7);
		scheduleService
				.seedSchedules(new ServiceSchedule(senderismo, LocalDate.now().plusDays(2), LocalTime.of(7, 0), 15), 7);
		scheduleService.seedSchedules(new ServiceSchedule(suite, LocalDate.now(), LocalTime.of(15, 0), 1), 7);
		scheduleService.seedSchedules(new ServiceSchedule(cabanas, LocalDate.now(), LocalTime.of(14, 0), 5), 7);
		scheduleService.seedSchedules(new ServiceSchedule(cafe, LocalDate.now().plusDays(2), LocalTime.of(10, 0), 8),
				7);
		scheduleService.seedSchedules(new ServiceSchedule(chef, LocalDate.now().plusDays(3), LocalTime.of(20, 0), 12),
				7);
		scheduleService
				.seedSchedules(new ServiceSchedule(desayuno, LocalDate.now().plusDays(1), LocalTime.of(7, 30), 30), 7);

		// === Usuarios y Clientes ===
		// === Usuarios y Clientes ===
		HotelUser user1 = new HotelUser("John Doe", "john.doe@example.com", "password123", "1234567890", "1234567890");
		HotelUser user2 = new HotelUser("Jane Doe", "jane.doe@example.com", "password123", "0987654321", "0987654321");
		HotelUser user3 = new HotelUser("Bob Smith", "bob.smith@example.com", "password123", "5555555555",
				"5555555555");

		// +7 adicionales (todos con email único)
		HotelUser user4 = new HotelUser("Carlos Mendoza", "carlos.mendoza@gmail.com", "password123", "3001234567",
				"CC1001");
		HotelUser user5 = new HotelUser("María Rodríguez", "maria.rodriguez@hotmail.com", "password123", "3109876543",
				"CC1002");
		HotelUser user6 = new HotelUser("Andrés Gómez", "andres.gomez@yahoo.com", "password123", "3205555555",
				"CC1003");
		HotelUser user7 = new HotelUser("Lucía Herrera", "lucia.herrera@gmail.com", "password123", "3157777777",
				"CC1004");
		HotelUser user8 = new HotelUser("Diego Vargas", "diego.vargas@outlook.com", "password123", "3018888888",
				"CC1005");
		HotelUser user9 = new HotelUser("Sofía Castro", "sofia.castro@gmail.com", "password123", "3112222222",
				"CC1006");
		HotelUser user10 = new HotelUser("Miguel Torres", "miguel.torres@yahoo.com", "password123", "3213333333",
				"CC1007");

		// Guarda usuarios
		userRepository.saveAll(java.util.Arrays.asList(
				user1, user2, user3, user4, user5, user6, user7, user8, user9, user10));

		// Crea y guarda clientes 1:1 con los usuarios
		clientRepository.saveAll(java.util.Arrays.asList(
				new Client(user1), new Client(user2), new Client(user3),
				new Client(user4), new Client(user5), new Client(user6),
				new Client(user7), new Client(user8), new Client(user9),
				new Client(user10)));

		// =========================
		// 1) ROOM TYPES (asegurar 5)
		// =========================
		Map<String, RoomType> typesByName = roomTypeRepository.findAll().stream()
				.collect(Collectors.toMap(rt -> rt.getName().toLowerCase(Locale.ROOT), rt -> rt, (a, b) -> a,
						LinkedHashMap::new));

		RoomType rtStd = ensureType(typesByName, "Estándar Regional",
				"Habitaciones cómodas con decoración típica de cada región colombiana",
				new BigDecimal("120000"), 2, "WiFi, AC, Baño privado, Arte local, Textiles regionales");

		RoomType rtDel = ensureType(typesByName, "Deluxe Cultural",
				"Habitaciones amplias con elementos culturales auténticos de la región",
				new BigDecimal("180000"), 3, "WiFi, AC, Minibar, Balcón, Mobiliario artesanal, Biblioteca regional");

		RoomType rtSuite = ensureType(typesByName, "Suite Ancestral",
				"Suites de lujo con sala separada y diseño premium colombiano",
				new BigDecimal("280000"), 4,
				"WiFi, AC, Minibar, Sala de estar, Ropa de cama premium, Colección cultural");

		RoomType rtFam = ensureType(typesByName, "Familiar Colombiana",
				"Habitaciones familiares amplias con espacios conectados y temática local",
				new BigDecimal("220000"), 6, "WiFi, AC, Cocineta, Literas, Juegos tradicionales, Juguetes artesanales");

		RoomType rtEco = ensureType(typesByName, "Eco Boutique",
				"Habitación eco-friendly con materiales locales y energía renovable",
				new BigDecimal("200000"), 3, "WiFi, Ventilación natural, Kit ecológico, Terraza verde");

		Map<Integer, RoomType> typeByFloor = Map.of(
				1, rtStd, 2, rtDel, 3, rtSuite, 4, rtFam, 5, rtEco);

		// ==========================================
		// 2) ROOMS (100) — solo si no existen rooms
		// ==========================================
		if (roomRepository.count() == 0L) {
			for (int hotelId = 1; hotelId <= 5; hotelId++) {
				for (int floor = 1; floor <= 5; floor++) {
					RoomType floorType = typeByFloor.get(floor);
					for (int i = 1; i <= 4; i++) {
						Room r = new Room();
						r.setHotelId((long) hotelId);
						r.setRoomType(floorType);

						// N° habitación único por hotel (ej: "1-101", "2-101", etc.)
						String roomNumber = String.format("%d-%d0%d", hotelId, floor, i);
						r.setRoomNumber(roomNumber);

						r.setFloorNumber(floor);

						// Estados demo: variedad para que los filtros muestren algo
						Room.ReservationStatus res = switch (i) {
							case 2 -> Room.ReservationStatus.BOOKED;
							case 3 -> Room.ReservationStatus.OCCUPIED;
							case 4 -> Room.ReservationStatus.MAINTENANCE;
							default -> Room.ReservationStatus.AVAILABLE;
						};
						r.setResStatus(res);

						r.setCleStatus((i % 2 == 0) ? Room.CleaningStatus.DIRTY : Room.CleaningStatus.CLEAN);

						// Temas por hotel/piso (nombres culturales)
						r.setThemeName(themeNameFor(hotelId, floor));
						r.setThemeDescription("Habitación temática personalizada por destino y piso.");

						roomRepository.save(r);
					}
				}
			}
		}

	}

	/** Crea el RoomType si no existe por nombre; si existe, lo reutiliza. */
	private RoomType ensureType(Map<String, RoomType> existing,
			String name, String desc, BigDecimal price, Integer maxOcc, String amenities) {
		RoomType found = existing.get(name.toLowerCase(Locale.ROOT));
		if (found != null)
			return found;

		RoomType rt = new RoomType();
		rt.setName(name);
		rt.setDescription(desc);
		rt.setBasePrice(price);
		rt.setMaxOccupancy(maxOcc);
		rt.setAmenities(amenities);

		rt = roomTypeRepository.save(rt);
		existing.put(name.toLowerCase(Locale.ROOT), rt);
		return rt;
	}

	/** Nombres de tema por hotel (1..5) y piso (1..5). */
	private String themeNameFor(int hotelId, int floor) {
		return switch (hotelId) {
			case 1 -> switch (floor) {
				case 1 -> "Balcones Coloniales";
				case 2 -> "Brisa Marina";
				case 3 -> "Palacio Virreinal";
				case 4 -> "Casa de Familias";
				default -> "Terraza Tropical";
			};
			case 2 -> switch (floor) {
				case 1 -> "Finca Cafetera";
				case 2 -> "Valle del Cocora";
				case 3 -> "Balcón Montañero";
				case 4 -> "Casa Paisa";
				default -> "Guadual Eco";
			};
			case 3 -> switch (floor) {
				case 1 -> "Mar de Siete Colores";
				case 2 -> "Johnny Cay";
				case 3 -> "Cayo Acuario";
				case 4 -> "Casa Raizal";
				default -> "Brisa Caribe";
			};
			case 4 -> switch (floor) {
				case 1 -> "Tayrona Ancestral";
				case 2 -> "Sierra Nevada";
				case 3 -> "Ciudad Perdida";
				case 4 -> "Kogui Sagrado";
				default -> "Eco Tayrona";
			};
			case 5 -> switch (floor) {
				case 1 -> "Plaza Mayor";
				case 2 -> "Casa Colonial";
				case 3 -> "Observatorio Muisca";
				case 4 -> "Hogar Boyacense";
				default -> "Viñedos Andinos";
			};
			default -> "Tema Regional";
		};
	}
}

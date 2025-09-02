package com.runasagrada.demo.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.runasagrada.demo.repository.ClientRepository;
import com.runasagrada.demo.repository.HotelServiceRepository;
import com.runasagrada.demo.repository.HotelUserRepository;
import com.runasagrada.demo.service.ServiceScheduleService;

import jakarta.transaction.Transactional;

@Controller
@Transactional
public class DatabaseInit implements ApplicationRunner {

	@Autowired
	HotelServiceRepository serviceRepository;

	@Autowired
	HotelUserRepository userRepository;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	ServiceScheduleService scheduleService;

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

		static final String PISCINA = "https://www.patiodepilar.com/img/cms/Piscina%20Infinity/piscina-infinita-que-es.jpg";
		static final String SPA = "https://cf.bstatic.com/xdata/images/hotel/max1024x768/483180906.jpg?k=cb923aa311360020d113175cd13556cafbe65103f2c2ab90a5e7553fab302c03&o=&hp=1";
		static final String RESTAURANTE = "https://lirp.cdn-website.com/ba701a07/dms3rep/multi/opt/Hotel-Las-Americas-Cartagena-de-Indias-Colombia---Las-Americas-Hotels-Group---Gastronom-C3-ADa---Erre-de-Ram-C3-B3n-Freixa-6-640w.png";
		static final String BAR = "https://www.palladiohotelbuenosaires.com/wp-content/uploads/sites/7/2021/11/palladio_hotel_mgallery_restaurant_negresco_bar_slide_01-2200x1200.jpg";
		static final String ARTESANIAS = "https://media-cdn.tripadvisor.com/media/photo-s/10/c7/82/f1/getlstd-property-photo.jpg";
		static final String BIBLIOTECA = "https://media.admagazine.com/photos/6585f094b83aa25ed6cd5397/master/w_1600%2Cc_limit/Library%2520Hotel.jpg";
		static final String GIMNASIO = "https://image-tc.galaxy.tf/wijpeg-blu54hydt85v0io6vu4lmnjrr/gimnasio_standard.jpg?crop=112%2C0%2C1777%2C1333";
		static final String JARDIN = "https://z.cdrst.com/foto/hotel-sf/123119a8/granderesp/foto-hotel-12310efe.jpg";
		static final String SALON = "https://www.hotelestequendama.com.co/assets/cache/uploads/tequendama-hoteles/Hotel%20Tequendama%20Bogot%C3%A1/salas/salon-crystal-view/1920x1080/interior-sala-crystal-view-mesas-boda-tequendama-hoteles-santa-fe-bogota-colombia-1691572149.jpg";
		static final String TERRAZA = "https://media-cdn.tripadvisor.com/media/photo-s/06/74/87/fc/best-western-plus-93.jpg";
		static final String NEGOSCIOS = "https://img.pikbest.com/wp/202346/wood-room-modern-style-business-meeting-in-3d-rendering_9730531.jpg!w700wp";
		static final String CONCIERGE = "https://www.ncakey.org/wp-content/uploads/2017/10/bell-2.jpg";

		static final String BANDEJA = "https://i.blogs.es/bb0cca/bandeja_paisa/1200_900.jpg";
		static final String SANCOCHO = "https://www.cheekyfoods.com.au/cdn/shop/articles/Untitled_design_87.jpg?v=1698649815";
		static final String AJIACO = "https://www.semana.com/resizer/v2/GBBYJH5YMZC6PEINHE3HZZH4TY.jpg?auth=f21d7fbf15c15316b80dd213fb2c635e4445db8e08133172d69a4956d7f417db&smart=true&quality=75&width=1920&height=1080&fitfill=false";
		static final String PESCADO = "https://us.123rf.com/450wm/echeverriurrealuis/echeverriurrealuis2311/echeverriurrealuis231100466/218354595-fried-horse-mackerel-fish-with-coconut-rice-patacon-and-vegetable-salad.jpg";
		static final String LECHONA = "https://media.istockphoto.com/id/1442283646/photo/lechona-with-rice-arepa-and-potato-on-a-white-plate-and-a-background-with-plants.jpg?s=612x612&w=0&k=20&c=3GsgmLQfsWEJcPjOld1n2Fhhb2kICye50IU557P7m4I=";
		static final String MONDONGO = "https://media.istockphoto.com/id/1205769953/photo/mondongo-typical-dish-of-the-center-of-colombia.jpg?s=612x612&w=0&k=20&c=BJL_ngH3nOc_LRkxFAdl8j2OCaAcpNGQ9w765AEmkZM=";
		static final String CAZUELA = "https://media.istockphoto.com/id/607991782/es/foto/paella-tradicional-espa%C3%B1ola-con-marisco-y-pollo.jpg?s=612x612&w=0&k=20&c=2q56wjPHIcSqje4SbsJdvA7Zy0I2Xy67XSdE6pQrmlo=";
		static final String TRUCHA = "https://thumbs.dreamstime.com/b/prendedero-de-la-trucha-cocinada-109983171.jpg";
		static final String TRELECHES = "https://easyways.cl/storage/20211229090337postre-tres-leches.jpg";
		static final String AREQUIPE = "https://easyways.cl/storage/20211229090337postre-tres-leches.jpg";
		static final String COCADAS = "https://www.shutterstock.com/image-photo/peruvian-cocadas-traditional-coconut-dessert-600nw-380640118.jpg";
		static final String CUAJADA = "https://static.bainet.es/clip/315db07b-3610-42cc-9c94-8abe9baef742_source-aspect-ratio_1600w_0.jpg";
		static final String TORTA = "https://api.photon.aremedia.net.au/wp-content/uploads/sites/4/2021/07/23/12909/HL1121E15-scaled.jpg?fit=2560%2C2134";
		static final String MANJAR = "https://elrinconcolombiano.com/wp-content/uploads/2023/04/Manjar-blanco-receta-colombiana.jpg";
		static final String NISPERO = "https://cloudfront-us-east-1.images.arcpublishing.com/infobae/ETKI3DOT3NFMBEKOML6FNT346M.jpg";
		static final String PAPAYUELA = "https://yucatan.travel/wp-content/uploads/2022/09/365-Sabores-en-Yucata%CC%81n-Dulce-de-papaya-con-queso-de-bola-3.jpg";
		static final String BEBIDACAFE = "https://st2.depositphotos.com/1773130/7605/i/450/depositphotos_76054953-stock-photo-iced-coffee-in-a-tall.jpg";
		static final String AGUAPANELA = "https://media.istockphoto.com/id/1181234339/es/foto/aguapanela-casera-fresca-agua-de-panela-o-aguadulce-una-popular-bebida-dulce-latinoamericana.jpg?s=612x612&w=0&k=20&c=wv78sAHw4zohimed6F0xdQrE7VIGtL6whjbpagllnek=";
		static final String COROZO = "https://imagenes.eltiempo.com/files/image_1200_535/uploads/2024/02/20/65d4e89c2c395.jpeg";
		static final String CHICHA = "https://cdn.colombia.com/gastronomia/2011/08/03/chicha-1604.gif";
		static final String LULADA = "https://www.elespectador.com/resizer/VQS-41ig6YKYg4qcH5zr5B1XXBw=/arc-anglerfish-arc2-prod-elespectador/public/GTELHVJGBZARLL3GLVUEGRCMJY.JPG";
		static final String CHOCOLATE = "https://sabor.eluniverso.com/wp-content/uploads/2023/12/shutterstock_1665115558-1024x683.jpg";
		static final String COCO = "https://jappi.com.co/wp-content/uploads/2023/03/Jappi-Final.webp";
		static final String AGUARDIENTE = "https://desquite.com/en/wp-content/uploads/2025/03/Desquite-Tradicion-Artisanal-Authentic-Colombian-Aguardiente-m.webp";
		static final String AMURALLADA = "https://viajerofacil.com/wp-content/uploads/2019/07/Webp.net-resizeimage-11-min.jpg";
		static final String ISLAS = "https://www.cartagenaexplorer.com/wp-content/uploads/2020/07/Depositphotos_156273740_xl-2015-scaled.jpg";
		static final String PALENQUE = "https://turismo.encolombia.com/wp-content/uploads/2019/09/Cartagena-de-Indias.jpg";
		static final String FINCA = "https://dynamic-media-cdn.tripadvisor.com/media/photo-o/07/19/08/27/finca-el-ocaso-salento.jpg?w=900&h=-1&s=1";
		static final String COCORA = "https://content-viajes.nationalgeographic.com.es/medio/2020/04/03/y-por-fin-el-valle_a092a848_1257x835.jpg";
		static final String PUEBLO = "https://www.infobae.com/new-resizer/GTDQWXVcyONBZkezz8NbuyrMMa4=/arc-anglerfish-arc2-prod-infobae/public/3WMFVPC5OFBF3LI652Z6V4LS2Q.jpg";
		static final String CUEVA = "https://www.regiocantabrorum.es/img/publicaciones/441/cueva_los_tornillos_index.jpg";
		static final String RAIZAL = "https://regionesnaturalescolombia.com/wp-content/uploads/2023/03/Traje-tipico-de-la-region-insular.png";
		static final String ACUARIO = "https://www.arserver.info/img/excursions/40/acuario-rio-de-janeiro-aquario-16.jpg";
		static final String TAYRONA = "https://ciudadperdidacolombia.com/wp-content/uploads/2023/12/todo-sobre-los-tairona.jpg";
		static final String TEYUNA = "https://content-viajes.nationalgeographic.com.es/medio/2019/09/16/istock-501625632_0eac7a9a_1200x630.jpg";
		static final String AVES = "https://media.istockphoto.com/id/153187546/es/foto/p%C3%A1jaro-watcher-silueta.jpg?s=612x612&w=0&k=20&c=Z7bdiVI9amwRG9NA8OEiNaM93F2CmTFQLzBkwfxUCFM=";
		static final String FOSILES = "https://humanidades.com/wp-content/uploads/2018/09/fosiles-e1579375905679.jpg";
		static final String OBSERVATORIO = "https://pbs.twimg.com/media/DUa0PLFUQAAlYJl.jpg";
		static final String VINO = "https://raizdeguzman.com/wp-content/uploads/2019/05/vinedos-raiz.png";
	}

	@Override
	public void run(org.springframework.boot.ApplicationArguments args) throws Exception {
		// === Crear servicios (sin fecha/hora/capacidad) ===
		HotelService gastronomia = serviceRepository.save(new HotelService(
				"Gastronomía Ancestral", "Comida",
				"Sabores auténticos de la cocina tradicional colombiana, preparados con ingredientes locales y técnicas ancestrales.",
				List.of(), 45900, "Disponible",
				List.of(ImageUrls.GASTRONOMIA_1, ImageUrls.GASTRONOMIA_2, ImageUrls.GASTRONOMIA_3),
				10.3910, -75.4794));

		HotelService tours = serviceRepository.save(new HotelService(
				"Tours Sagrados", "Tour",
				"Expediciones guiadas por lugares místicos y sitios arqueológicos, conectando con la sabiduría ancestral.",
				List.of(), 65500, "Disponible",
				List.of(ImageUrls.TOURS_ARQUEOLOGICOS_1, ImageUrls.TOURS_ARQUEOLOGICOS_2,
						ImageUrls.TOURS_ARQUEOLOGICOS_3),
				5.6333, -73.5333));

		HotelService rituales = serviceRepository.save(new HotelService(
				"Rituales de Bienestar", "Hotel",
				"Terapias tradicionales y ceremonias de sanación inspiradas en las prácticas indígenas colombianas.",
				List.of(), 75000, "Disponible",
				List.of(ImageUrls.RITUALES_1, ImageUrls.RITUALES_2, ImageUrls.RITUALES_3),
				4.7109, -74.0721));

		HotelService boutique = serviceRepository.save(new HotelService(
				"Hospedaje Boutique", "Hotel",
				"Habitaciones únicas diseñadas con elementos artesanales y decoración inspirada en culturas precolombinas.",
				List.of(), 120000, "Disponible",
				List.of(ImageUrls.HOSPEDAJE_BOUTIQUE_1, ImageUrls.HOSPEDAJE_BOUTIQUE_2, ImageUrls.HOSPEDAJE_BOUTIQUE_3),
				4.6370, -75.5710));

		HotelService ecoturismo = serviceRepository.save(new HotelService(
				"Ecoturismo", "Tour",
				"Experiencias sostenibles que preservan y celebran la biodiversidad única de los ecosistemas colombianos.",
				List.of(), 55750, "Disponible",
				List.of(ImageUrls.ECOTURISMO_1, ImageUrls.ECOTURISMO_2, ImageUrls.ECOTURISMO_3),
				12.5847, -81.7005));

		HotelService cultura = serviceRepository.save(new HotelService(
				"Cultura Viva", "Tour",
				"Talleres de artesanías, música tradicional y danzas folclóricas con maestros de comunidades locales.",
				List.of(), 35000, "Disponible",
				List.of(ImageUrls.CULTURA_1, ImageUrls.CULTURA_2, ImageUrls.CULTURA_3),
				11.2408, -74.1990));

		HotelService cacao = serviceRepository.save(new HotelService(
				"Ceremonia del Cacao Sagrado", "Tour",
				"Participa en un ritual ancestral de conexión espiritual con el cacao como elemento sagrado.",
				List.of(), 85000, "Disponible",
				List.of(ImageUrls.CACAO_1, ImageUrls.CACAO_2, ImageUrls.CACAO_3),
				5.6333, -73.5333));

		HotelService aves = serviceRepository.save(new HotelService(
				"Avistamiento de Aves", "Tour",
				"Descubre la biodiversidad de Colombia a través de sus especies de aves más representativas.",
				List.of(), 40000, "Disponible",
				List.of(ImageUrls.AVES_1, ImageUrls.AVES_2, ImageUrls.AVES_3),
				4.7109, -74.0721));

		HotelService senderismo = serviceRepository.save(new HotelService(
				"Senderismo Místico", "Tour",
				"Explora caminos ancestrales y conecta con la naturaleza en rutas llenas de energía y tradición.",
				List.of(), 500000, "Disponible",
				List.of(ImageUrls.SENDERISMO_1, ImageUrls.SENDERISMO_2, ImageUrls.SENDERISMO_3),
				11.2408, -74.1990));

		HotelService suite = serviceRepository.save(new HotelService(
				"Suite Presidencial", "Hotel",
				"La experiencia más exclusiva con vista panorámica, jacuzzi privado y servicio de mayordomo 24/7.",
				List.of(), 350000, "Disponible",
				List.of(ImageUrls.SUITE_PRESIDENCIAL_1, ImageUrls.SUITE_PRESIDENCIAL_2, ImageUrls.SUITE_PRESIDENCIAL_3),
				4.6014, -74.0661));

		HotelService cabanas = serviceRepository.save(new HotelService(
				"Cabañas Ecológicas", "Hotel",
				"Alojamiento sostenible en medio de la naturaleza, construido con materiales autóctonos y energía solar.",
				List.of(), 9500000, "Disponible",
				List.of(ImageUrls.CABANAS_1, ImageUrls.CABANAS_2, ImageUrls.CABANAS_3),
				6.2442, -75.5736));

		HotelService cafe = serviceRepository.save(new HotelService(
				"Taller de Café Premium", "Comida",
				"Aprende sobre el proceso del café colombiano desde el grano hasta la taza, con cata guiada.",
				List.of(), 300000, "Disponible",
				List.of(ImageUrls.CAFE_1, ImageUrls.CAFE_2, ImageUrls.CAFE_3),
				5.0689, -75.5174));

		HotelService chef = serviceRepository.save(new HotelService(
				"Cena con Chef Estrella", "Comida",
				"Menú degustación de 7 platos con maridaje de vinos, preparado por nuestro chef galardonado.",
				List.of(), 120000, "Disponible",
				List.of(ImageUrls.CHEF_1, ImageUrls.CHEF_2, ImageUrls.CHEF_3),
				6.2518, -75.5636));

		HotelService desayuno = serviceRepository.save(new HotelService(
				"Desayuno Tradicional", "Comida",
				"Desayuno completo con arepas, huevos pericos, chocolate caliente y frutas tropicales.",
				List.of(), 15.50, "Disponible",
				List.of(ImageUrls.DESAYUNO_1, ImageUrls.DESAYUNO_2, ImageUrls.DESAYUNO_3),
				10.9639, -74.7964));

		// Amenidad 1: Piscina Infinity (ya estaba con imagen)
		HotelService piscina = serviceRepository.save(new HotelService(
				"Piscina Infinity",
				"Hotel",
				"Piscina infinita con vistas panorámicas",
				List.of(),
				49900,
				"Disponible",
				List.of(ImageUrls.PISCINA),
				10.3910, -75.4794));

		// Amenidad 2: Spa Ancestral
		HotelService spa = serviceRepository.save(new HotelService(
				"Spa Ancestral",
				"Hotel",
				"Terapias de sanación tradicional con técnicas indígenas",
				List.of(),
				52900,
				"Disponible",
				List.of(ImageUrls.SPA),
				10.3910, -75.4794));

		// Amenidad 3: Restaurante Gourmet
		HotelService restaurante = serviceRepository.save(new HotelService(
				"Restaurante Gourmet",
				"Hotel",
				"Gastronomía fina con cocina regional colombiana",
				List.of(),
				57900,
				"Disponible",
				List.of(ImageUrls.RESTAURANTE),
				10.3910, -75.4794));

		// Amenidad 4: Bar de Cócteles
		HotelService bar = serviceRepository.save(new HotelService(
				"Bar de Cócteles",
				"Hotel",
				"Cócteles artesanales con frutas y licores locales",
				List.of(),
				45900,
				"Disponible",
				List.of(ImageUrls.BAR),
				10.3910, -75.4794));

		// Amenidad 5: Taller de Artesanías
		HotelService taller = serviceRepository.save(new HotelService(
				"Taller de Artesanías",
				"Hotel",
				"Talleres prácticos con artesanos locales",
				List.of(),
				48900,
				"Disponible",
				List.of(ImageUrls.ARTESANIAS),
				10.3910, -75.4794));

		// Amenidad 6: Biblioteca Cultural
		HotelService biblioteca = serviceRepository.save(new HotelService(
				"Biblioteca Cultural",
				"Hotel",
				"Colección de literatura e historia colombiana",
				List.of(),
				41900,
				"Disponible",
				List.of(ImageUrls.BIBLIOTECA),
				10.3910, -75.4794));

		// Amenidad 7: Gimnasio Eco
		HotelService gimnasio = serviceRepository.save(new HotelService(
				"Gimnasio Eco",
				"Hotel",
				"Centro de fitness con equipos sostenibles",
				List.of(),
				39900,
				"Disponible",
				List.of(ImageUrls.GIMNASIO),
				10.3910, -75.4794));

		// Amenidad 8: Jardín Botánico
		HotelService jardin = serviceRepository.save(new HotelService(
				"Jardín Botánico",
				"Hotel",
				"Jardín de plantas nativas con hierbas medicinales",
				List.of(),
				44900,
				"Disponible",
				List.of(ImageUrls.JARDIN),
				10.3910, -75.4794));

		// Amenidad 9: Salón de Eventos
		HotelService salon = serviceRepository.save(new HotelService(
				"Salón de Eventos",
				"Hotel",
				"Espacio versátil para celebraciones y reuniones",
				List.of(),
				55900,
				"Disponible",
				List.of(ImageUrls.SALON),
				10.3910, -75.4794));

		// Amenidad 10: Terraza Mirador
		HotelService terraza = serviceRepository.save(new HotelService(
				"Terraza Mirador",
				"Hotel",
				"Terraza en azotea con vistas regionales",
				List.of(),
				42900,
				"Disponible",
				List.of(ImageUrls.TERRAZA),
				10.3910, -75.4794));

		// Amenidad 11: Centro de Negocios
		HotelService negocios = serviceRepository.save(new HotelService(
				"Centro de Negocios",
				"Hotel",
				"Centro de negocios con tecnología moderna",
				List.of(),
				53900,
				"Disponible",
				List.of(ImageUrls.NEGOSCIOS),
				10.3910, -75.4794));

		// Amenidad 12: Servicio de Concierge
		HotelService concierge = serviceRepository.save(new HotelService(
				"Servicio de Concierge",
				"Hotel",
				"Asistencia personalizada al huésped 24/7",
				List.of(),
				46900,
				"Disponible",
				List.of(ImageUrls.CONCIERGE),
				10.3910, -75.4794));

		/* === Platos Principales === */
		HotelService bandeja = serviceRepository.save(new HotelService(
				"Bandeja Paisa Tradicional",
				"Comida",
				"Plato Principal - Plato paisa completo con frijoles, arroz, chicharrón y arepa",
				List.of(),
				35000,
				"Disponible",
				List.of(ImageUrls.BANDEJA),
				10.3910, -75.4794));

		HotelService sancocho = serviceRepository.save(new HotelService(
				"Sancocho Costeño",
				"Comida",
				"Plato Principal - Guiso tradicional costeño con pescado, yuca y plátano",
				List.of(),
				32000,
				"Disponible",
				List.of(ImageUrls.SANCOCHO),
				10.3910, -75.4794));

		HotelService ajiaco = serviceRepository.save(new HotelService(
				"Ajiaco Santafereño",
				"Comida",
				"Plato Principal - Sopa de pollo y papa estilo Bogotá con mazorca y alcaparras",
				List.of(),
				28000,
				"Disponible",
				List.of(ImageUrls.AJIACO),
				10.3910, -75.4794));

		HotelService pescado = serviceRepository.save(new HotelService(
				"Pescado Frito Isleño",
				"Comida",
				"Plato Principal - Pescado frito con arroz de coco y patacones, estilo San Andrés",
				List.of(),
				38000,
				"Disponible",
				List.of(ImageUrls.PESCADO),
				10.3910, -75.4794));

		HotelService lechona = serviceRepository.save(new HotelService(
				"Lechona Tolimense",
				"Comida",
				"Plato Principal - Cerdo relleno asado con arroz y arvejas, tradición tolimense",
				List.of(),
				42000,
				"Disponible",
				List.of(ImageUrls.LECHONA),
				10.3910, -75.4794));

		HotelService mondongo = serviceRepository.save(new HotelService(
				"Mondongo Antioqueño",
				"Comida",
				"Plato Principal - Sopa tradicional de mondongo con verduras y hierbas",
				List.of(),
				30000,
				"Disponible",
				List.of(ImageUrls.MONDONGO),
				10.3910, -75.4794));

		HotelService cazuela = serviceRepository.save(new HotelService(
				"Cazuela de Mariscos",
				"Comida",
				"Plato Principal - Cazuela de mariscos con leche de coco, estilo caribeño",
				List.of(),
				45000,
				"Disponible",
				List.of(ImageUrls.CAZUELA),
				10.3910, -75.4794));

		HotelService trucha = serviceRepository.save(new HotelService(
				"Trucha a la Plancha",
				"Comida",
				"Plato Principal - Trucha a la plancha con hierbas de los Andes",
				List.of(),
				36000,
				"Disponible",
				List.of(ImageUrls.TRUCHA),
				10.3910, -75.4794));

		/* === Postres === */
		HotelService tresLeches = serviceRepository.save(new HotelService(
				"Tres Leches Costeño",
				"Comida",
				"Postre - Torta de tres leches con frutas tropicales",
				List.of(),
				15000,
				"Disponible",
				List.of(ImageUrls.TRELECHES),
				10.3910, -75.4794));

		HotelService arequipe = serviceRepository.save(new HotelService(
				"Arequipe con Brevas",
				"Comida",
				"Postre - Brevas con dulce de leche, especialidad antioqueña",
				List.of(),
				12000,
				"Disponible",
				List.of(ImageUrls.AREQUIPE),
				10.3910, -75.4794));

		HotelService coco = serviceRepository.save(new HotelService(
				"Cocadas Isleñas",
				"Comida",
				"Postre - Dulces de coco de la tradición isleña de San Andrés",
				List.of(),
				8000,
				"Disponible",
				List.of(ImageUrls.COCADAS),
				10.3910, -75.4794));

		HotelService cuajada = serviceRepository.save(new HotelService(
				"Cuajada con Melao",
				"Comida",
				"Postre - Queso fresco con miel de caña, estilo boyacense",
				List.of(),
				10000,
				"Disponible",
				List.of(ImageUrls.CUAJADA),
				10.3910, -75.4794));

		HotelService torta = serviceRepository.save(new HotelService(
				"Torta de Natas",
				"Comida",
				"Postre - Torta de natas de la costa caribeña",
				List.of(),
				14000,
				"Disponible",
				List.of(ImageUrls.TORTA),
				10.3910, -75.4794));

		HotelService manjar = serviceRepository.save(new HotelService(
				"Manjar Blanco",
				"Comida",
				"Postre - Delicia blanca del Valle del Cauca",
				List.of(),
				11000,
				"Disponible",
				List.of(ImageUrls.MANJAR),
				10.3910, -75.4794));

		HotelService nispero = serviceRepository.save(new HotelService(
				"Postre de Níspero",
				"Comida",
				"Postre - Postre de níspero de la región cafetera",
				List.of(),
				13000,
				"Disponible",
				List.of(ImageUrls.NISPERO),
				10.3910, -75.4794));

		HotelService papayuela = serviceRepository.save(new HotelService(
				"Dulce de Papayuela",
				"Comida",
				"Postre - Conserva dulce de papayuela de montaña",
				List.of(),
				9000,
				"Disponible",
				List.of(ImageUrls.PAPAYUELA),
				10.3910, -75.4794));

		/* === Bebidas === */
		HotelService babidaCafe = serviceRepository.save(new HotelService( // (nombre de variable con typo, ok)
				"Café de Origen Especial",
				"Comida",
				"Bebida - Café de origen único de fincas locales",
				List.of(),
				8000,
				"Disponible",
				List.of(ImageUrls.BEBIDACAFE),
				10.3910, -75.4794));

		HotelService aguaPanela = serviceRepository.save(new HotelService(
				"Agua de Panela con Limón",
				"Comida",
				"Bebida - Agua de panela con limón, refresco tradicional",
				List.of(),
				5000,
				"Disponible",
				List.of(ImageUrls.AGUAPANELA),
				10.3910, -75.4794));

		HotelService jugoCorozo = serviceRepository.save(new HotelService(
				"Jugo de Corozo",
				"Comida",
				"Bebida - Jugo de fruta de palma corozo, especialidad caribeña",
				List.of(),
				7000,
				"Disponible",
				List.of(ImageUrls.COROZO),
				10.3910, -75.4794));

		HotelService chicha = serviceRepository.save(new HotelService(
				"Chicha de Maíz",
				"Comida",
				"Bebida - Bebida tradicional de maíz de cultura indígena",
				List.of(),
				6000,
				"Disponible",
				List.of(ImageUrls.CHICHA),
				10.3910, -75.4794));

		HotelService lulada = serviceRepository.save(new HotelService(
				"Lulada Vallecaucana",
				"Comida",
				"Bebida - Bebida de lulo con hielo y limón",
				List.of(),
				8000,
				"Disponible",
				List.of(ImageUrls.LULADA),
				10.3910, -75.4794));

		HotelService chocolate = serviceRepository.save(new HotelService(
				"Chocolate Santafereño",
				"Comida",
				"Bebida - Chocolate caliente con queso, tradición bogotana",
				List.of(),
				9000,
				"Disponible",
				List.of(ImageUrls.CHOCOLATE),
				10.3910, -75.4794));

		HotelService cocoLoco = serviceRepository.save(new HotelService(
				"Coco Loco Isleño",
				"Comida",
				"Bebida - Cóctel de coco con ron local",
				List.of(),
				15000,
				"Disponible",
				List.of(ImageUrls.COCO),
				10.3910, -75.4794));

		HotelService aguardiente = serviceRepository.save(new HotelService(
				"Aguardiente Antioqueño",
				"Comida",
				"Bebida - Experiencia de degustación de licor de anís tradicional",
				List.of(),
				12000,
				"Disponible",
				List.of(ImageUrls.AGUARDIENTE),
				10.3910, -75.4794));

		/* === Tours Cartagena === */
		HotelService tourCartagena = serviceRepository.save(new HotelService(
				"Tour Ciudad Amurallada", "Tour",
				"Cultural - Tour caminando por la Cartagena colonial con perspectivas históricas",
				List.of(), 45000, "Disponible", List.of(ImageUrls.AMURALLADA),
				10.3910, -75.4794));

		HotelService tourIslas = serviceRepository.save(new HotelService(
				"Islas del Rosario", "Tour",
				"Naturaleza - Excursión en bote a islas coralinas con snorkel y tiempo de playa",
				List.of(), 85000, "Disponible", List.of(ImageUrls.ISLAS),
				10.3910, -75.4794));

		HotelService tourPalenque = serviceRepository.save(new HotelService(
				"Palenque Cultural", "Tour",
				"Cultural - Visita a San Basilio de Palenque, primer pueblo africano libre en América",
				List.of(), 65000, "Disponible", List.of(ImageUrls.PALENQUE),
				10.3910, -75.4794));

		/* === Tours Eje Cafetero === */
		HotelService tourFincaCafetera = serviceRepository.save(new HotelService(
				"Finca Cafetera Tradicional", "Tour",
				"Cultural - Experiencia en finca cafetera con cosecha y degustación",
				List.of(), 55000, "Disponible", List.of(ImageUrls.FINCA),
				10.3910, -75.4794));

		HotelService tourValleCocora = serviceRepository.save(new HotelService(
				"Valle de Cocora", "Tour",
				"Naturaleza - Caminata por bosque de palmas de cera, árbol nacional de Colombia",
				List.of(), 70000, "Disponible", List.of(ImageUrls.COCORA),
				10.3910, -75.4794));

		HotelService tourSalamina = serviceRepository.save(new HotelService(
				"Pueblo Patrimonio Salamina", "Tour",
				"Cultural - Tour por pueblo colonial con arquitectura tradicional y artesanías",
				List.of(), 40000, "Disponible", List.of(ImageUrls.PUEBLO),
				10.3910, -75.4794));

		/* === Tours San Andrés === */
		HotelService tourHoyo = serviceRepository.save(new HotelService(
				"Hoyo Soplador y Cueva Morgan", "Tour",
				"Naturaleza - Exploración de géiser natural y cueva de piratas",
				List.of(), 35000, "Disponible", List.of(ImageUrls.CUEVA),
				10.3910, -75.4794));

		HotelService tourRaizal = serviceRepository.save(new HotelService(
				"Cultura Raizal", "Tour",
				"Cultural - Inmersión en cultura raizal con música, danza y gastronomía",
				List.of(), 50000, "Disponible", List.of(ImageUrls.RAIZAL),
				10.3910, -75.4794));

		HotelService tourAcuario = serviceRepository.save(new HotelService(
				"Acuario y Johnny Cay", "Tour",
				"Naturaleza - Viaje en bote a acuario natural y playa prístina",
				List.of(), 75000, "Disponible", List.of(ImageUrls.ACUARIO),
				10.3910, -75.4794));

		/* === Tours Santa Marta === */
		HotelService tourTayrona = serviceRepository.save(new HotelService(
				"Tayrona Ancestral", "Tour",
				"Cultural - Caminata a sitios arqueológicos indígenas Tayrona",
				List.of(), 80000, "Disponible", List.of(ImageUrls.TAYRONA),
				10.3910, -75.4794));

		HotelService tourCiudadPerdida = serviceRepository.save(new HotelService(
				"Ciudad Perdida Teyuna", "Tour",
				"Aventura - Caminata de varios días a la Ciudad Perdida de la civilización Tayrona",
				List.of(), 450000, "Disponible", List.of(ImageUrls.TEYUNA),
				10.3910, -75.4794));

		HotelService tourSierraNevada = serviceRepository.save(new HotelService(
				"Avistamiento de Aves Sierra Nevada", "Tour",
				"Naturaleza - Observación de aves en la cordillera costera más alta del mundo",
				List.of(), 60000, "Disponible", List.of(ImageUrls.AVES),
				10.3910, -75.4794));

		/* === Tours Villa de Leyva === */
		HotelService tourFosiles = serviceRepository.save(new HotelService(
				"Ruta de los Fósiles", "Tour",
				"Educativo - Tour paleontológico con descubrimientos de fósiles y museos",
				List.of(), 35000, "Disponible", List.of(ImageUrls.FOSILES),
				10.3910, -75.4794));

		HotelService tourAstro = serviceRepository.save(new HotelService(
				"Observatorio Astronómico", "Tour",
				"Educativo - Experiencia de observación estelar con perspectivas astronómicas precolombinas",
				List.of(), 45000, "Disponible", List.of(ImageUrls.OBSERVATORIO),
				10.3910, -75.4794));

		HotelService tourVinos = serviceRepository.save(new HotelService(
				"Viñedos Boyacenses", "Tour",
				"Cultural - Tour de degustación de vinos en viñedos de alta altitud de Boyacá",
				List.of(), 65000, "Disponible", List.of(ImageUrls.VINO),
				10.3910, -75.4794));

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
		HotelUser user1 = new HotelUser("John Doe", "john.doe@example.com", "password123", "1234567890", "1234567890");
		HotelUser user2 = new HotelUser("Jane Doe", "jane.doe@example.com", "password123", "0987654321", "0987654321");
		HotelUser user3 = new HotelUser("Bob Smith", "bob.smith@example.com", "password123", "5555555555",
				"5555555555");

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);

		Client client1 = new Client(user1);
		Client client2 = new Client(user2);
		Client client3 = new Client(user3);

		clientRepository.save(client1);
		clientRepository.save(client2);
		clientRepository.save(client3);
	}
}

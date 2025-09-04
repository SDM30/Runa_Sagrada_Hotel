class ParallaxController {
  constructor() {
    this.config = {
      breakpoints: {
        xs: { amp: 0.6, scale: 1.18 },
        sm: { amp: 0.65, scale: 1.16 },
        md: { amp: 0.8, scale: 1.12 },
        lg: { amp: 1.0, scale: 1.08 },
        xl: { amp: 1.0, scale: 1.08 },
        xxl: { amp: 1.0, scale: 1.08 },
      },
      navbarScrollThreshold: 8,
      cloudAnimationSpeed: { frequency: 300, drift: 40, amplitude: 10 },
    };
    this.state = { ticking: false, drift: 0, lastScrollY: 0 };
    this.elements = {
      layers: Array.from(document.querySelectorAll(".layer")),
      navbar: document.querySelector(".navbar.nav-glass"),
      brandLogo: document.querySelector(".brand-logo"),
    };
    this.init();
  }

  init() {
    if (this.elements.layers.length === 0) {
      console.warn("ParallaxController: No parallax layers found");
      return;
    }
    this.bindEvents();
    this.updateBrandLogo();
    this.applyParallax(0);
    console.log(
      "ParallaxController: Initialized with",
      this.elements.layers.length,
      "layers"
    );
  }

  bindEvents() {
    document.addEventListener("scroll", this.handleScroll.bind(this), {
      passive: true,
    });
    window.addEventListener(
      "resize",
      this.debounce(() => {
        this.updateBrandLogo();
        this.applyParallax(window.scrollY || window.pageYOffset);
      }, 100)
    );
  }

  handleScroll() {
    if (!this.state.ticking) {
      requestAnimationFrame(() => {
        this.applyParallax(window.scrollY || window.pageYOffset);
        this.state.ticking = false;
      });
      this.state.ticking = true;
    }
  }

  getCurrentBreakpoint() {
    const w = window.innerWidth;
    return w >= 1400
      ? "xxl"
      : w >= 1200
      ? "xl"
      : w >= 992
      ? "lg"
      : w >= 768
      ? "md"
      : w >= 576
      ? "sm"
      : "xs";
  }
  getBreakpointSettings() {
    return this.config.breakpoints[this.getCurrentBreakpoint()];
  }

  updateNavbar(scrollY) {
    if (this.elements.navbar)
      this.elements.navbar.classList.toggle(
        "scrolled",
        scrollY > this.config.navbarScrollThreshold
      );
  }
  updateBrandLogo() {
    if (this.elements.brandLogo) {
      const bp = this.getCurrentBreakpoint();
      this.elements.brandLogo.style.height =
        bp === "lg" || bp === "xl" || bp === "xxl" ? "52px" : "40px";
    }
  }

  calculateLayerTransform(layer, scrollY, settings) {
    const { amp, scale } = settings;
    const depth = parseFloat(layer.dataset.depth || "0.3"),
      ydir = parseFloat(layer.dataset.ydir || "1");
    const xdir = parseFloat(layer.dataset.xdir || "0"),
      xmul = parseFloat(layer.dataset.xmul || "0");
    const offset = parseFloat(layer.dataset.offset || "0"),
      translateY = scrollY * depth * amp * ydir + offset;
    let translateX = 0;
    if (layer.classList.contains("clouds")) {
      const { frequency, drift, amplitude } = this.config.cloudAnimationSpeed;
      translateX =
        Math.sin(scrollY / frequency + this.state.drift / drift) * amplitude;
    } else if (xdir !== 0 && xmul > 0) translateX = scrollY * xmul * xdir * amp;
    return {
      translateX: translateX.toFixed(2),
      translateY: translateY.toFixed(2),
      scale,
    };
  }

  applyParallax(scrollY) {
    this.state.drift += 0.5;
    this.updateNavbar(scrollY);
    const settings = this.getBreakpointSettings();
    this.elements.layers.forEach((layer) => {
      const t = this.calculateLayerTransform(layer, scrollY, settings);
      layer.style.transform = `translate3d(${t.translateX}px, ${t.translateY}px, 0) scale(${t.scale})`;
    });
    this.state.lastScrollY = scrollY;
  }
  debounce(func, wait) {
    let timeout;
    return (...args) => {
      clearTimeout(timeout);
      timeout = setTimeout(() => func(...args), wait);
    };
  }
}

// Hotel Slider
class HotelSlider {
  constructor() {
    this.destinations = [
      {
        id: "cartagena",
        name: "Cartagena de Indias",
        description:
          "Ciudad amurallada, patrimonio histórico y cultura caribeña.",
        image: "/images/hotels/cartagena.jpg",
        accentColor: "#e67e22",
        alt: "Vista panorámica de Cartagena de Indias",
        airport: "CTG",
      },
      {
        id: "ejecafe",
        name: "Eje Cafetero",
        description:
          "Paisajes cafeteros, cultura paisa y naturaleza exuberante.",
        image: "/images/hotels/ejecafe.webp",
        accentColor: "#8B4513",
        alt: "Paisajes del Eje Cafetero",
        airport: "UIO",
      },
      {
        id: "sanandres",
        name: "San Andrés",
        description:
          "Mar de siete colores, cultura raizal y playas paradisíacas.",
        image: "/images/hotels/sanandres.webp",
        accentColor: "#00bfb3",
        alt: "Playas de San Andrés",
        airport: "ADZ",
      },
      {
        id: "santamarta",
        name: "Santa Marta",
        description: "Sierra Nevada, Tayrona y patrimonio natural único.",
        image: "/images/hotels/santamarta.jpg",
        accentColor: "#228B22",
        alt: "Parque Tayrona en Santa Marta",
        airport: "SMR",
      },
      {
        id: "villaleiva",
        name: "Villa de Leyva",
        description:
          "Arquitectura colonial, historia y tranquilidad boyacense.",
        image: "/images/hotels/villaleiva.jpg",
        accentColor: "#4285f4",
        alt: "Plaza principal de Villa de Leyva",
        airport: "BOG",
      },
    ];
    this.state = {
      currentIndex: 0,
      isTransitioning: false,
      autoplayInterval: null,
      autoplayDelay: 5000,
    };
    this.elements = {
      mainImage: document.getElementById("hotel-image"),
      title: document.getElementById("hotel-title"),
      description: document.getElementById("hotel-desc"),
      cta: document.getElementById("hotel-cta"),
      thumbs: document.getElementById("hotel-thumbs"),
    };
    this.init();
  }

  init() {
    if (!this.validateElements()) {
      console.error("HotelSlider: Required elements not found");
      return;
    }
    this.preloadImages();
    this.renderThumbnails();
    this.bindEvents();
    this.goToSlide(0, true);
    this.startAutoplay();
    console.log(
      "HotelSlider: Initialized with",
      this.destinations.length,
      "destinations"
    );
  }
  validateElements() {
    return ["mainImage", "title", "description", "cta"].every(
      (key) => this.elements[key] !== null
    );
  }
  preloadImages() {
    this.destinations.forEach((dest) => {
      const img = new Image();
      img.src = dest.image;
    });
  }

  createThumbnail(index) {
    const dest = this.destinations[index],
      btn = document.createElement("button");
    btn.type = "button";
    btn.className = "hotel-thumb";
    btn.style.backgroundImage = `url(${dest.image})`;
    btn.setAttribute("aria-label", `Ver ${dest.name}`);
    btn.setAttribute("data-index", index);
    btn.setAttribute("role", "tab");
    btn.setAttribute("aria-selected", index === this.state.currentIndex);
    btn.addEventListener("click", () => this.goToSlide(index));
    btn.addEventListener("keydown", (e) =>
      this.handleThumbnailKeydown(e, index)
    );
    return btn;
  }
  renderThumbnails() {
    if (this.elements.thumbs) {
      this.elements.thumbs.innerHTML = "";
      this.destinations.forEach((_, i) =>
        this.elements.thumbs.appendChild(this.createThumbnail(i))
      );
    }
  }

  updateActiveThumbnail() {
    document.querySelectorAll(".hotel-thumb").forEach((thumb, i) => {
      const isActive = i === this.state.currentIndex;
      thumb.classList.toggle("active", isActive);
      thumb.setAttribute("aria-selected", isActive);
      thumb.setAttribute("tabindex", isActive ? "0" : "-1");
    });
  }
  hexToRgba(hex, alpha) {
    const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    if (!result) return `rgba(245, 158, 11, ${alpha})`;
    const [, r, g, b] = result;
    return `rgba(${parseInt(r, 16)}, ${parseInt(g, 16)}, ${parseInt(
      b,
      16
    )}, ${alpha})`;
  }

  goToSlide(index, force = false) {
    if (this.state.isTransitioning && !force) return;
    const normalizedIndex =
      ((index % this.destinations.length) + this.destinations.length) %
      this.destinations.length;
    if (normalizedIndex === this.state.currentIndex && !force) return;
    this.state.isTransitioning = true;
    this.state.currentIndex = normalizedIndex;
    const destination = this.destinations[normalizedIndex];
    this.updateMainContent(destination);
    this.updateActiveThumbnail();
    setTimeout(() => {
      this.state.isTransitioning = false;
    }, 300);
    this.restartAutoplay();
  }

  updateMainContent(destination) {
    const testImg = new Image();
    testImg.onload = () => {
      this.elements.mainImage.style.backgroundImage = `url(${destination.image})`;
    };
    testImg.onerror = () => {
      console.error(`Failed to load main image: ${destination.image}`);
    };
    testImg.src = destination.image;
    this.elements.mainImage.setAttribute("aria-label", destination.alt);
    this.elements.title.textContent = destination.name;
    this.elements.description.textContent = destination.description;
    const shadowColor = this.hexToRgba(destination.accentColor, 0.28);
    this.elements.cta.style.boxShadow = `0 10px 24px ${shadowColor}`;
    this.elements.cta.setAttribute(
      "aria-label",
      `Ver más sobre ${destination.name}`
    );
  }

  nextSlide() {
    this.goToSlide(this.state.currentIndex + 1);
  }
  previousSlide() {
    this.goToSlide(this.state.currentIndex - 1);
  }

  handleThumbnailKeydown(event, index) {
    let targetIndex = index;
    const actions = {
      ArrowUp: () => (targetIndex = index - 1),
      ArrowLeft: () => (targetIndex = index - 1),
      ArrowDown: () => (targetIndex = index + 1),
      ArrowRight: () => (targetIndex = index + 1),
      Home: () => (targetIndex = 0),
      End: () => (targetIndex = this.destinations.length - 1),
    };
    if (!actions[event.key]) return;
    event.preventDefault();
    actions[event.key]();
    this.goToSlide(targetIndex);
    setTimeout(() => {
      const newActiveThumbnail = document.querySelector(".hotel-thumb.active");
      if (newActiveThumbnail) newActiveThumbnail.focus();
    }, 50);
  }

  handleGlobalKeydown(event) {
    if (event.target.tagName === "INPUT" || event.target.tagName === "TEXTAREA")
      return;
    const actions = {
      ArrowUp: () => this.previousSlide(),
      ArrowLeft: () => this.previousSlide(),
      ArrowDown: () => this.nextSlide(),
      ArrowRight: () => this.nextSlide(),
    };
    if (actions[event.key]) {
      event.preventDefault();
      actions[event.key]();
    }
  }

  startAutoplay() {
    this.stopAutoplay();
    this.state.autoplayInterval = setInterval(() => {
      this.nextSlide();
    }, this.state.autoplayDelay);
  }
  stopAutoplay() {
    if (this.state.autoplayInterval) {
      clearInterval(this.state.autoplayInterval);
      this.state.autoplayInterval = null;
    }
  }
  restartAutoplay() {
    this.startAutoplay();
  }

  bindEvents() {
    document.addEventListener("keydown", this.handleGlobalKeydown.bind(this));
    if (this.elements.mainImage) {
      this.elements.mainImage.addEventListener("mouseenter", () =>
        this.stopAutoplay()
      );
      this.elements.mainImage.addEventListener("mouseleave", () =>
        this.startAutoplay()
      );
    }
    document.addEventListener("visibilitychange", () => {
      if (document.hidden) this.stopAutoplay();
      else this.startAutoplay();
    });
  }
}

// Booking Bar
class BookingBar {
  constructor() {
    this.airports = {
      origin: [
        {
          code: "BOG",
          name: "Bogotá - El Dorado",
          country: "Colombia",
          flag: "🇨🇴",
        },
        {
          code: "MDE",
          name: "Medellín - José María Córdova",
          country: "Colombia",
          flag: "🇨🇴",
        },
        {
          code: "CLO",
          name: "Cali - Alfonso Bonilla Aragón",
          country: "Colombia",
          flag: "🇨🇴",
        },
        {
          code: "BAQ",
          name: "Barranquilla - Ernesto Cortissoz",
          country: "Colombia",
          flag: "🇨🇴",
        },
        {
          code: "BGA",
          name: "Bucaramanga - Palonegro",
          country: "Colombia",
          flag: "🇨🇴",
        },
        {
          code: "MIA",
          name: "Miami International",
          country: "Estados Unidos",
          flag: "🇺🇸",
        },
        {
          code: "JFK",
          name: "Nueva York - JFK",
          country: "Estados Unidos",
          flag: "🇺🇸",
        },
        {
          code: "MAD",
          name: "Madrid - Barajas",
          country: "España",
          flag: "🇪🇸",
        },
        {
          code: "LIM",
          name: "Lima - Jorge Chávez",
          country: "Perú",
          flag: "🇵🇪",
        },
      ],
      destination: [
        {
          code: "CTG",
          name: "Cartagena - Rafael Núñez",
          destination: "Cartagena de Indias",
          flag: "🇨🇴",
        },
        {
          code: "UIO",
          name: "Pereira - Matecaña",
          destination: "Eje Cafetero",
          flag: "🇨🇴",
        },
        {
          code: "ADZ",
          name: "San Andrés - Gustavo Rojas Pinilla",
          destination: "San Andrés",
          flag: "🇨🇴",
        },
        {
          code: "SMR",
          name: "Santa Marta - Simón Bolívar",
          destination: "Santa Marta",
          flag: "🇨🇴",
        },
        {
          code: "BOG",
          name: "Bogotá - El Dorado",
          destination: "Villa de Leyva",
          flag: "🇨🇴",
        },
      ],
    };
    this.selectedFrom = null;
    this.selectedTo = null;
    this.passengers = { adults: 2, children: 0, infants: 0, rooms: 1 };
    this.maxPassengers = { adults: 9, children: 8, infants: 4, rooms: 5 };
    this.init();
  }

  init() {
    this.createPassengerDropdown();
    this.bindEvents();
    this.setupDateInputs();
    this.populateDropdowns();
    this.updatePassengerDisplay();
  }

  bindEvents() {
    const fromInput = document.getElementById("from-airport");
    const fromDropdown = document.getElementById("from-dropdown");
    fromInput?.parentElement.addEventListener("click", (e) => {
      e.stopPropagation();
      this.toggleDropdown(fromDropdown);
      this.closeOtherDropdowns(fromDropdown);
    });

    const toInput = document.getElementById("to-airport");
    const toDropdown = document.getElementById("to-dropdown");
    toInput?.parentElement.addEventListener("click", (e) => {
      e.stopPropagation();
      this.toggleDropdown(toDropdown);
      this.closeOtherDropdowns(toDropdown);
    });

    const passengerInputGroup = document.getElementById(
      "passengers-input-group"
    );
    const passengerDropdown = document.getElementById("passengers-dropdown");

    passengerInputGroup?.addEventListener("click", (e) => {
      e.stopPropagation();
      this.toggleDropdown(passengerDropdown);
      this.closeOtherDropdowns(passengerDropdown);
    });

    passengerDropdown?.addEventListener("click", (e) => {
      e.stopPropagation();
      const target = e.target.closest("button");
      if (!target) return;

      const type = target.dataset.type;
      const action = target.dataset.action;

      if (type && action) {
        const change = action === "plus" ? 1 : -1;
        this.updatePassengers(type, change);
      }
    });

    document.getElementById("search-btn")?.addEventListener("click", (e) => {
      e.preventDefault();
      this.handleSearch();
    });

    document.addEventListener("click", () => {
      this.closeAllDropdowns();
    });
  }

  setupDateInputs() {
    const checkinDate = document.getElementById("checkin-date");
    const checkoutDate = document.getElementById("checkout-date");
    const today = new Date().toISOString().split("T")[0];
    if (checkinDate) checkinDate.min = today;
    if (checkoutDate) checkoutDate.min = today;
    checkinDate?.addEventListener("change", (e) => {
      if (checkoutDate) {
        checkoutDate.min = e.target.value;
        if (checkoutDate.value && checkoutDate.value <= e.target.value) {
          const nextDay = new Date(e.target.value);
          nextDay.setDate(nextDay.getDate() + 1);
          checkoutDate.value = nextDay.toISOString().split("T")[0];
        }
      }
    });
  }

  createPassengerDropdown() {
    const dropdown = document.getElementById("passengers-dropdown");
    if (!dropdown) return;

    dropdown.innerHTML = `
        ${this.createCounter("adults", "Adultos", "Mayores de 12 años")}
        ${this.createCounter("children", "Niños", "De 2 a 12 años")}
        ${this.createCounter("infants", "Bebés", "Menores de 2 años")}
        <div class="dropdown-divider"></div>
        ${this.createCounter(
          "rooms",
          "Habitaciones",
          "Máximo 8 personas por habitación"
        )}
    `;
  }

  createCounter(type, label, description) {
    const count = this.passengers[type];
    return `
          <div class="d-flex justify-content-between align-items-center px-3 py-2">
              <div>
                  <div class="fw-bold">${label}</div>
                  <small class="text-muted">${description}</small>
              </div>
              <div class="d-flex align-items-center">
                  <button class="btn btn-sm btn-outline-secondary rounded-circle" type="button" data-type="${type}" data-action="minus" aria-label="Disminuir ${label}">-</button>
                  <span id="${type}-count" class="mx-2" style="min-width: 20px; text-align: center;">${count}</span>
                  <button class="btn btn-sm btn-outline-secondary rounded-circle" type="button" data-type="${type}" data-action="plus" aria-label="Aumentar ${label}">+</button>
              </div>
          </div>
      `;
  }

  populateDropdowns() {
    this.populateDropdown("from-dropdown", this.airports.origin, false);
    this.populateDropdown("to-dropdown", this.airports.destination, true);
  }

  populateDropdown(dropdownId, airports, isDestination = false) {
    const dropdown = document.getElementById(dropdownId);
    if (!dropdown) return;
    const template = isDestination
      ? (airport) =>
          `<div class="dropdown-item" data-code="${airport.code}"><span class="flag">${airport.flag}</span><strong>${airport.code}</strong> - ${airport.destination}<br><small class="text-muted">${airport.name}</small></div>`
      : (airport) =>
          `<div class="dropdown-item" data-code="${airport.code}"><span class="flag">${airport.flag}</span><strong>${airport.code}</strong> - ${airport.name}<br><small class="text-muted">${airport.country}</small></div>`;
    dropdown.innerHTML = airports.map(template).join("");
    dropdown.addEventListener("click", (e) => {
      const item = e.target.closest(".dropdown-item");
      if (!item) return;
      const code = item.dataset.code;
      const airport = airports.find((a) => a.code === code);
      if (isDestination) {
        this.selectToAirport(airport);
      } else {
        this.selectFromAirport(airport);
      }
    });
  }

  selectFromAirport(airport) {
    this.selectedFrom = airport;
    const input = document.getElementById("from-airport");
    if (input)
      input.value = `${airport.flag} ${airport.code} - ${airport.name}`;
    this.closeAllDropdowns();
  }

  selectToAirport(airport) {
    this.selectedTo = airport;
    const input = document.getElementById("to-airport");
    if (input)
      input.value = `${airport.flag} ${airport.code} - ${airport.destination}`;
    this.closeAllDropdowns();
  }

  updatePassengers(type, change) {
    const newCount = this.passengers[type] + change;
    const min = type === "adults" || type === "rooms" ? 1 : 0;
    const max = this.maxPassengers[type];
    if (newCount >= min && newCount <= max) {
      this.passengers[type] = newCount;
      this.updatePassengerDisplay();
    }
  }

  updatePassengerDisplay() {
    const passengersInput = document.getElementById("passengers");
    const adultsCount = document.getElementById("adults-count");
    const childrenCount = document.getElementById("children-count");
    const infantsCount = document.getElementById("infants-count");
    const roomsCount = document.getElementById("rooms-count");

    if (adultsCount) adultsCount.textContent = this.passengers.adults;
    if (childrenCount) childrenCount.textContent = this.passengers.children;
    if (infantsCount) infantsCount.textContent = this.passengers.infants;
    if (roomsCount) roomsCount.textContent = this.passengers.rooms;

    if (passengersInput) {
      const parts = [];
      if (this.passengers.adults > 0)
        parts.push(
          `${this.passengers.adults} adulto${
            this.passengers.adults > 1 ? "s" : ""
          }`
        );
      if (this.passengers.children > 0)
        parts.push(
          `${this.passengers.children} niño${
            this.passengers.children > 1 ? "s" : ""
          }`
        );
      if (this.passengers.infants > 0)
        parts.push(
          `${this.passengers.infants} bebé${
            this.passengers.infants > 1 ? "s" : ""
          }`
        );
      if (this.passengers.rooms > 0)
        parts.push(
          `${this.passengers.rooms} hab${this.passengers.rooms > 1 ? "s" : ""}`
        );
      passengersInput.value =
        parts.length > 0 ? parts.join(", ") : "2 adultos, 1 hab.";
    }
  }

  toggleDropdown(dropdown) {
    if (!dropdown) return;
    dropdown.classList.toggle("show");
  }

  closeOtherDropdowns(except) {
    document
      .querySelectorAll(".booking-bar .dropdown-menu")
      .forEach((dropdown) => {
        if (dropdown !== except) dropdown.classList.remove("show");
      });
  }

  closeAllDropdowns() {
    document
      .querySelectorAll(".booking-bar .dropdown-menu")
      .forEach((dropdown) => {
        dropdown.classList.remove("show");
      });
  }

  handleSearch() {
    const checkinDate = document.getElementById("checkin-date")?.value;
    const checkoutDate = document.getElementById("checkout-date")?.value;
    if (!this.selectedFrom) {
      alert("Por favor selecciona el aeropuerto de origen");
      return;
    }
    if (!this.selectedTo) {
      alert("Por favor selecciona el destino");
      return;
    }
    if (!checkinDate) {
      alert("Por favor selecciona la fecha de llegada");
      return;
    }
    if (!checkoutDate) {
      alert("Por favor selecciona la fecha de salida");
      return;
    }
    const searchData = {
      from: this.selectedFrom,
      to: this.selectedTo,
      checkin: checkinDate,
      checkout: checkoutDate,
      passengers: this.passengers,
    };
    console.log("Búsqueda realizada:", searchData);
    alert(
      `Búsqueda realizada:\n\nDesde: ${this.selectedFrom.name}\nHacia: ${
        this.selectedTo.destination
      }\nLlegada: ${checkinDate}\nSalida: ${checkoutDate}\nPasajeros: ${JSON.stringify(
        this.passengers
      )}`
    );
  }
}

// Initialize
document.addEventListener("DOMContentLoaded", () => {
  const parallaxController = new ParallaxController(),
    hotelSlider = new HotelSlider(),
    bookingBar = new BookingBar();

  // Mobile sidebar toggle - Configuración para el panel de administración
  const sidebarToggle = document.getElementById("sidebarToggle");
  if (sidebarToggle) {
    sidebarToggle.addEventListener("click", function () {
      const sidebar = document.getElementById("sidebar");
      if (sidebar) {
        sidebar.classList.toggle("show");
      }
    });

    // Close sidebar when clicking outside on mobile
    document.addEventListener("click", function (event) {
      const sidebar = document.getElementById("sidebar");
      const toggle = document.getElementById("sidebarToggle");

      if (sidebar && toggle && window.innerWidth <= 768) {
        if (!sidebar.contains(event.target) && !toggle.contains(event.target)) {
          sidebar.classList.remove("show");
        }
      }
    });
  }

  // Sidebar navigation
  document
    .querySelectorAll(".sidebar-nav-link[data-section]")
    .forEach((link) => {
      link.addEventListener("click", function (e) {
        e.preventDefault();

        // Remove active class from all links
        document
          .querySelectorAll(".sidebar-nav-link")
          .forEach((l) => l.classList.remove("active"));

        // Add active class to clicked link
        this.classList.add("active");

        // Get section name
        const section = this.getAttribute("data-section");

        // Close sidebar on mobile
        if (window.innerWidth <= 768) {
          const sidebar = document.getElementById("sidebar");
          if (sidebar) {
            sidebar.classList.remove("show");
          }
        }
      });
    });

  window.addEventListener("error", (e) =>
    console.error("Global error:", e.error)
  );
  if ("performance" in window) {
    window.addEventListener("load", () => {
      const loadTime = performance.now();
      console.log(`Page loaded in ${loadTime.toFixed(2)}ms`);
    });
  }
});

const nav = document.querySelector(".navbar-services");

//staffPage.html

// Load client information into update modal
const modalUCliente = document.getElementById("modalUCliente");
if (modalUCliente) {
  modalUCliente.addEventListener("show.bs.modal", function (event) {
    var button = event.relatedTarget;
    var clientId = button.getAttribute("data-id");

    fetch("/ops/client/update/" + clientId)
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        const clientIdEdit = document.getElementById("clientIdEdit");
        const nameEdit = document.getElementById("nameEdit");
        const emailEdit = document.getElementById("emailEdit");
        const phoneEdit = document.getElementById("phoneEdit");
        const nationalIdEdit = document.getElementById("nationalIdEdit");

        if (clientIdEdit) clientIdEdit.value = data.id;
        if (nameEdit) nameEdit.value = data.name;
        if (emailEdit) emailEdit.value = data.email;
        if (phoneEdit) phoneEdit.value = data.phone;
        if (nationalIdEdit) nationalIdEdit.value = data.nationalId;
      })
      .catch((err) => console.error("Error cargando cliente:", err));
  });
}

function passDelClient(clientId) {
  fetch("/ops/client/delete/" + clientId)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
    })
    .catch((err) => console.error("Error cargando cliente:", err));
}

function loadRoomData(element) {
  const id = element.getAttribute("data-id");
  const name = element.getAttribute("data-name");
  const capacity = element.getAttribute("data-capacity");
  const basePrice = element.getAttribute("data-baseprice");
  const description = element.getAttribute("data-description");

  document.getElementById("updateId").value = id;
  document.getElementById("updateName").value = name;
  document.getElementById("updateCapacity").value = capacity;
  document.getElementById("updateBasePrice").value = basePrice;
  document.getElementById("updateDescription").value = description || "";
}

document.addEventListener("DOMContentLoaded", function () {
  // Initialize Bootstrap tooltips
  var tooltipTriggerList = [].slice.call(
    document.querySelectorAll('[data-bs-toggle="tooltip"]')
  );
  var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl);
  });

  // Initialize components
  if (typeof ParallaxController !== 'undefined') {
    new ParallaxController();
  }
  
  if (typeof HotelSlider !== 'undefined') {
    new HotelSlider();
  }
  
  if (typeof BookingBar !== 'undefined') {
    new BookingBar();
  }
});

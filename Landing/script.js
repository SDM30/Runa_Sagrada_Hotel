// JavaScript for Runa Sagrada Hotel Landing Page

// Initialize all functionality when DOM is loaded
document.addEventListener('DOMContentLoaded', function() {
    const navbar = document.querySelector('.navbar');
    const navLinks = document.querySelectorAll('.nav-link');
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('.navbar-collapse');
    
    // Navbar scroll effect and active link highlighting
    window.addEventListener('scroll', debounce(() => {
        navbar.classList.toggle('scrolled', window.scrollY > 50);
        
        let current = '';
        document.querySelectorAll('section[id]').forEach(section => {
            if (scrollY >= (section.offsetTop - 200)) {
                current = section.getAttribute('id');
            }
        });
        
        navLinks.forEach(link => {
            link.classList.toggle('active', link.getAttribute('href') === `#${current}`);
        });
    }, 10));
    
    // Close mobile menu and smooth scrolling
    navLinks.forEach(link => {
        link.addEventListener('click', (e) => {
            if (navbarCollapse.classList.contains('show')) navbarToggler.click();
            
            const targetId = link.getAttribute('href');
            if (targetId.startsWith('#')) {
                e.preventDefault();
                const target = document.querySelector(targetId);
                if (target) target.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    });
    
    // Initialize other features
    initScrollAnimations();
    initBookingForm();
    initImageLazyLoading();
    initParallaxEffect();
    initLoginButton();
});

// Navbar functionality (consolidated into main DOMContentLoaded)

// Smooth scrolling functionality (consolidated into main DOMContentLoaded)

// Scroll animations
function initScrollAnimations() {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach((entry) => {
            if (entry.isIntersecting) {
                entry.target.style.animation = 'fadeInUp 0.6s ease forwards';
                observer.unobserve(entry.target);
            }
        });
    }, { threshold: 0.1, rootMargin: '0px 0px -50px 0px' });
    
    document.querySelectorAll('.hotel-card, .service-card, .experience-card, .testimonial-card').forEach(card => {
        card.style.cssText = 'opacity: 0; transform: translateY(30px);';
        observer.observe(card);
    });
}

// Booking form functionality
function initBookingForm() {
    const form = document.getElementById('bookingForm');
    const checkIn = document.getElementById('checkIn');
    const checkOut = document.getElementById('checkOut');
    
    if (!form) return;
    
    const today = new Date().toISOString().split('T')[0];
    if (checkIn) checkIn.min = today;
    if (checkOut) checkOut.min = today;
    
    if (checkIn && checkOut) {
        checkIn.addEventListener('change', () => {
            const date = new Date(checkIn.value);
            date.setDate(date.getDate() + 1);
            checkOut.min = date.toISOString().split('T')[0];
        });
    }
    
    form.addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const data = Object.fromEntries(formData);
        
        if (!data.checkIn || !data.checkOut || !data.guests || !data.roomType) {
            return showNotification('Please fill in all fields', 'error');
        }
        
        if (new Date(data.checkIn) >= new Date(data.checkOut)) {
            return showNotification('Check-out date must be after check-in date', 'error');
        }
        
        showNotification('Searching for available rooms...', 'info');
        setTimeout(() => showNotification('Great! We found available rooms. Redirecting to booking page...', 'success'), 2000);
    });
}

// Utility functions
function showNotification(message, type = 'info') {
    document.querySelectorAll('.notification').forEach(n => n.remove());
    
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.innerHTML = `<span>${message}</span><button onclick="this.parentElement.remove()">&times;</button>`;
    
    const colors = { success: '#28a745', error: '#dc3545', warning: '#ffc107', info: '#17a2b8' };
    notification.style.cssText = `position:fixed;top:20px;right:20px;z-index:10000;padding:15px 20px;border-radius:8px;color:white;background:${colors[type]||colors.info};box-shadow:0 4px 12px rgba(0,0,0,0.15);transform:translateX(100%);transition:transform 0.3s ease;max-width:400px;`;
    
    document.body.appendChild(notification);
    setTimeout(() => notification.style.transform = 'translateX(0)', 100);
    setTimeout(() => {
        if (notification.parentElement) {
            notification.style.transform = 'translateX(100%)';
            setTimeout(() => notification.remove(), 300);
        }
    }, 5000);
}

function initImageLazyLoading() {
    const observer = new IntersectionObserver((entries) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.classList.add('loaded');
                img.removeAttribute('data-src');
                observer.unobserve(img);
            }
        });
    }, { threshold: 0.1, rootMargin: '50px' });
    
    document.querySelectorAll('img[data-src]').forEach(img => observer.observe(img));
}

function initParallaxEffect() {
    const elements = document.querySelectorAll('.parallax');
    if (elements.length > 0) {
        window.addEventListener('scroll', debounce(() => {
            const scrolled = window.pageYOffset * -0.5;
            elements.forEach(el => el.style.transform = `translateY(${scrolled}px)`);
        }, 10));
    }
}

// Card interactions (consolidated)
function initCardInteractions() {
    const hotelCards = document.querySelectorAll('.hotel-card');
    const serviceCards = document.querySelectorAll('.service-card');
    
    hotelCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            this.style.transform = 'translateY(-10px)';
            this.style.boxShadow = '0 20px 40px rgba(6, 39, 70, 0.15)';
        });
        card.addEventListener('mouseleave', function() {
            this.style.transform = 'translateY(0)';
            this.style.boxShadow = '0 10px 30px rgba(6, 39, 70, 0.1)';
        });
    });
    
    serviceCards.forEach(card => {
        card.addEventListener('mouseenter', function() {
            const icon = this.querySelector('.service-icon');
            if (icon) icon.style.transform = 'scale(1.1) rotate(5deg)';
        });
        card.addEventListener('mouseleave', function() {
            const icon = this.querySelector('.service-icon');
            if (icon) icon.style.transform = 'scale(1) rotate(0deg)';
        });
    });
}

// Form validation helpers
function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validatePhone(phone) {
    const phoneRegex = /^[\+]?[1-9][\d]{0,2}[\s\-]?[\(]?[\d]{1,3}[\)]?[\s\-]?[\d]{3,4}[\s\-]?[\d]{3,4}$/;
    return phoneRegex.test(phone);
}

function initLoginButton() {
    const btn = document.querySelector('.login-btn');
    if (btn) btn.addEventListener('click', e => {
        e.preventDefault();
        showNotification('Login functionality will be implemented soon!', 'info');
    });
}

function debounce(func, wait) {
    let timeout;
    return function(...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func(...args), wait);
    };
}

// Error handling and performance monitoring
window.addEventListener('error', e => console.error('JavaScript Error:', e.error));
window.addEventListener('load', () => {
    setTimeout(() => {
        const perfData = performance.getEntriesByType('navigation')[0];
        if (perfData) console.log(`Page load time: ${perfData.loadEventEnd - perfData.loadEventStart}ms`);
    }, 0);
});

// Module exports (if needed)
if (typeof module !== 'undefined' && module.exports) {
    module.exports = { showNotification, validateEmail, validatePhone, debounce };
}

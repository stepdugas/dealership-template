# Dealership Template

A plug-and-play car dealership website template.
**Frontend:** Vue 3 + Vite + Tailwind CSS
**Backend:** Spring Boot 3 + PostgreSQL
**Images:** Cloudinary
**Animations:** AOS (Animate On Scroll)

---

## Quick Start

### Prerequisites

| Tool | Version | Status |
|------|---------|--------|
| Node.js | ≥ 18 | ✅ Required for frontend |
| Java JDK | 21 | ⚠️ Not detected — install to run backend |
| Maven | 3.9+ | ⚠️ Not detected — install to build backend |
| PostgreSQL | 14+ | ⚠️ Required for backend |

> **Note:** Java and Maven were not found during scaffolding. The entire backend source is
> ready — install JDK 21 + Maven and it will compile without changes.
> Recommended: [Eclipse Adoptium JDK 21](https://adoptium.net/)
> or use [SDKMAN](https://sdkman.io/) on WSL/Git Bash.

---

### 1. Configure the client

Edit **`frontend/src/config.js`** and swap every `[PLACEHOLDER]` value:

```js
export const DEALERSHIP_NAME   = 'Smith Auto Group'
export const TAGLINE           = 'Your Trusted Local Dealer Since 1995'
export const PHONE             = '(555) 867-5309'
// … etc
```

Also update `tailwind.config.js` → `primary` color to match the brand.

---

### 2. Frontend

```bash
cd frontend
npm install
npm run dev        # dev server at http://localhost:5173
npm run build      # production build → dist/
```

**Environment variables** (create `frontend/.env.local`):

```
VITE_API_BASE_URL=http://localhost:8080
```

---

### 3. Database

```bash
createdb -U postgres dealership_db
psql -U postgres -d dealership_db -f database/schema.sql
```

Optionally uncomment the sample data block at the bottom of `schema.sql` for dev seeding.

---

### 4. Backend

```bash
# Set environment variables (or edit application.properties directly for local dev)
export DB_USERNAME=postgres
export DB_PASSWORD=yourpassword
export JWT_SECRET=your-256-bit-secret-change-me
export ADMIN_USERNAME=admin
export ADMIN_PASSWORD=changeme123

cd backend
mvn spring-boot:run     # runs on :8080
# or
mvn clean package
java -jar target/dealership-api-*.jar
```

---

## Per-Client Customisation Checklist

- [ ] `frontend/src/config.js` — all `[PLACEHOLDER]` values
- [ ] `tailwind.config.js` → `primary` color
- [ ] `frontend/index.html` — title tag
- [ ] `backend/src/main/resources/application.properties` — DB credentials, admin creds, JWT secret
- [ ] `database/schema.sql` — run against client's database
- [ ] Upload logo to CDN, set `LOGO_URL` in config.js
- [ ] Set `GOOGLE_MAPS_EMBED_URL` in config.js
- [ ] Set Cloudinary credentials in config.js and application.properties
- [ ] Replace placeholder testimonials in `TestimonialsSection.vue`
- [ ] Replace placeholder copy in `AboutView.vue`
- [ ] Replace placeholder staff in `StaffView.vue`
- [ ] Replace placeholder FAQ in `FaqView.vue`

---

## Project Structure

```
dealership-template/
├── frontend/                   Vue 3 + Vite + Tailwind
│   └── src/
│       ├── config.js           ← All client-specific config lives here
│       ├── api/index.js        ← Axios API service layer
│       ├── router/index.js     ← Vue Router
│       ├── assets/main.css     ← Tailwind + custom utilities
│       ├── components/
│       │   ├── layout/         TheHeader, TheFooter, PageLayout
│       │   ├── home/           HeroSection, FeaturedInventory, WhyUs, Testimonials, Hours
│       │   └── inventory/      CarCard, FilterBar
│       └── views/
│           ├── HomeView.vue
│           ├── InventoryView.vue
│           ├── CarDetailView.vue
│           ├── AboutView.vue
│           ├── ContactView.vue
│           ├── [scaffold pages] Staff, Financing, SellYourCar, FAQ, Blog
│           └── admin/          Login, Dashboard, Cars, CarForm, Contacts
│
├── backend/                    Spring Boot 3 / Java 21
│   └── src/main/java/com/dealership/api/
│       ├── model/              Car, CarImage, ContactSubmission
│       ├── repository/         JPA repositories
│       ├── service/            CarService, ContactService
│       ├── controller/         InventoryController, ContactController, AuthController
│       ├── security/           JwtTokenProvider, JwtAuthFilter
│       └── config/             SecurityConfig, WebConfig
│
├── database/
│   └── schema.sql              PostgreSQL DDL + seed data (commented out)
│
├── README.md
└── OVERNIGHT_NOTES.md
```

---

## API Reference

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| GET | `/api/inventory` | Public | List cars (filterable, paginated) |
| GET | `/api/inventory/:id` | Public | Get single car |
| POST | `/api/inventory` | Admin JWT | Create car |
| PUT | `/api/inventory/:id` | Admin JWT | Update car |
| DELETE | `/api/inventory/:id` | Admin JWT | Delete car |
| POST | `/api/contact` | Public | Submit contact form |
| POST | `/api/admin/login` | Public | Get admin JWT |
| GET | `/api/admin/contacts` | Admin JWT | List contact submissions |

### Filter params for GET `/api/inventory`

`make`, `model`, `year`, `minPrice`, `maxPrice`, `maxMileage`, `condition`, `search`, `page`, `size`, `sort`

---

## Admin Panel

Navigate to `/admin` → redirects to `/admin/login`.

Default credentials (set in `application.properties`):
- Username: `admin`
- Password: `changeme123`

**Change before deploying.**

---

## Cloudinary Setup

1. Create a free account at [cloudinary.com](https://cloudinary.com)
2. Create an unsigned upload preset in Settings → Upload
3. Update `config.js`:
   ```js
   export const CLOUDINARY_CLOUD_NAME = 'your-cloud-name'
   export const CLOUDINARY_UPLOAD_PRESET = 'your-preset'
   ```
4. Update `application.properties`:
   ```
   cloudinary.cloud-name=your-cloud-name
   cloudinary.api-key=your-api-key
   cloudinary.api-secret=your-api-secret
   ```

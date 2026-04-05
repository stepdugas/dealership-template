# New Client Setup Guide
**From signed intake form → live website**

---

## What You'll Need Before Starting

- The completed client intake form (from Netlify dashboard)
- Access to your Railway account (railway.com)
- Access to your Netlify account (netlify.com)
- Your Cloudinary account credentials (cloudinary.com)
- About 30–45 minutes

---

## Step 1 — Collect Info From the Intake Form

Log into **netlify.com → Forms → client-intake** and open the client's submission. You'll reference these values throughout setup:

| Intake Form Field | Where It Goes |
|---|---|
| Business Name | Admin Settings → Business Name |
| Tagline | Admin Settings → Tagline |
| Phone | Admin Settings → Phone |
| Email | Admin Settings → Email |
| Address | Admin Settings → Address |
| City / State / ZIP | Admin Settings → City, State ZIP |
| Business Hours | Admin Settings → Business Hours |
| Facebook URL | Admin Settings → Facebook URL |
| Instagram URL | Admin Settings → Instagram URL |
| Primary Color | `tailwind.config.js` (see Step 4) |
| Notes / Special Requests | Review before launch |

Keep this submission open in a browser tab — you'll copy from it frequently.

---

## Step 2 — Create a New Railway Project (Backend + Database)

Each client gets their own backend so their data stays separate.

1. Go to **railway.com** and click **New Project**
2. Choose **Empty Project**
3. Name it something like `clientname-dealership` (e.g. `dugas-dealership`)
4. Click **Add Service → Database → PostgreSQL** to add the database
5. Click **Add Service → GitHub Repo** and select `stepdugas/dealership-template`
   - Set the **Root Directory** to `backend`
6. Click on the backend service, go to **Variables**, and add all of these:

```
ADMIN_USERNAME=admin
ADMIN_PASSWORD=          ← create a secure password for the client
MANAGER_USERNAME=manager
MANAGER_PASSWORD=        ← create a separate password for the manager
JWT_SECRET=              ← generate a random 32+ character string (use passwordsgenerator.net)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=dugas.openclaw@gmail.com
MAIL_PASSWORD=           ← your Gmail app password (crmo prgj mzbi duym)
CLOUDINARY_CLOUD_NAME=dnq90fk5x
CLOUDINARY_API_KEY=597451288358816
CLOUDINARY_API_SECRET=cjH2yicZrRYFl7KaHwcziu8lOJU
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

7. Go to the backend service → **Settings → Networking** → click **Generate Domain**
8. Copy the Railway URL — it will look like `https://clientname-dealership.up.railway.app`
9. Wait 3–5 minutes for the first deploy to finish (watch the deploy logs)

---

## Step 3 — Create a New Netlify Site (Frontend)

Each client also gets their own Netlify site.

1. Go to **netlify.com → Add new site → Import an existing project**
2. Connect to GitHub → select `stepdugas/dealership-template`
3. Configure the build:
   - **Base directory:** `frontend`
   - **Build command:** `npm install && npm run build`
   - **Publish directory:** `frontend/dist`
4. Before deploying, go to **Site configuration → Environment variables** and add:

```
VITE_API_BASE_URL=        ← paste the Railway URL from Step 2
VITE_CLOUDINARY_CLOUD_NAME=dnq90fk5x
VITE_CLOUDINARY_UPLOAD_PRESET=dealership_uploads
```

5. Click **Deploy site**
6. Once deployed, go to **Site configuration → Domain management**
7. Change the Netlify subdomain to something like `clientname-cars` (e.g. `dugas-cars.netlify.app`)

---

## Step 4 — Set the Brand Color (Optional)

If the client picked a color other than the default blue:

1. Open `frontend/tailwind.config.js` in the repo
2. Find the `primary` color line and update the hex value to match their chosen color
3. Commit and push — Netlify will auto-redeploy

> **Tip:** If you're not comfortable editing code, skip this for now. The default blue works fine and you can always update it later.

---

## Step 5 — Log Into Admin Panel and Enter Business Info

1. Go to `https://[their-netlify-site].netlify.app/admin/login`
2. Log in with `admin` / the password you set in Railway Step 2
3. Go to **Settings** and fill in every field from the intake form:
   - Business Name, Tagline, Phone, Email, Address
   - Business Hours (check "Closed" for any closed days)
   - Facebook / Instagram URLs if provided
   - Pick the Hero Image that best fits their dealership vibe
   - Set the **Notification Email** to the email address where the dealer wants to receive "I'm Interested" alerts
4. Click **Save Changes**

---

## Step 6 — Add Initial Inventory

If the client provided any starting inventory (cars they want listed right away):

1. In the admin panel go to **Inventory → Add New Vehicle**
2. Fill in the details for each car
3. Upload photos using the photo upload area (they go straight to Cloudinary)
4. Set Status to **Available**
5. Optionally fill in **Estimated Value** if they want a price comparison shown

---

## Step 7 — Test Everything

Run through this checklist before handing off to the client:

- [ ] Homepage loads with correct business name, phone, and hero image
- [ ] Business hours show correctly
- [ ] Inventory page shows added cars
- [ ] Car detail page shows photos and info
- [ ] "I'm Interested" button sends an email to the dealer's notification address
- [ ] Contact page form submits successfully
- [ ] Admin login works with the password you set
- [ ] Admin can add/edit/delete a car
- [ ] Manager login works with the manager password you set

---

## Step 8 — Connect Their Domain (If They Have One)

If the client has their own domain (e.g. `dugasauto.com`):

1. In Netlify → **Domain management → Add a domain**
2. Enter their domain name
3. Netlify will give you DNS records to add — send these to the client (or their web host)
4. Records to add at their domain registrar:
   - **CNAME** `www` → `[their-netlify-site].netlify.app`
   - **A record** `@` → Netlify's load balancer IP (shown in Netlify)
5. DNS can take up to 24 hours to fully propagate
6. Netlify handles the SSL certificate automatically once DNS is connected

> **If they don't have a domain yet:** The `*.netlify.app` URL works fine while they get one. GoDaddy and Namecheap are good options (~$15/year).

---

## Step 9 — Client Handoff

Send the client:

1. **Their website URL**
2. **Admin panel URL:** `https://[their-site]/admin/login`
3. **Admin username:** `admin`
4. **Admin password:** (the one you set)
5. **Manager username:** `manager` (for an office manager or employee — limited access)
6. **Manager password:** (the one you set)

Recommended message to include:
> "Log into your admin panel to manage inventory, update your business hours, and change your homepage image anytime. If you ever need to reset a password or make a bigger change, just reach out."

---

## Passwords Reference Card

Keep a secure record of each client's credentials. Recommended: use a password manager like **Bitwarden** (free) or **1Password**.

| What | Value |
|---|---|
| Admin username | `admin` |
| Admin password | *(set in Step 2 — save it)* |
| Manager username | `manager` |
| Manager password | *(set in Step 2 — save it)* |
| Railway project URL | *(backend URL from Step 2)* |
| Netlify site URL | *(frontend URL from Step 3)* |

---

## Quick Troubleshooting

**Site loads but shows placeholder text like [DEALERSHIP_NAME]**
→ Go to Admin → Settings and save the business info. The site pulls from the database, not the code.

**Photos aren't uploading**
→ Check that `VITE_CLOUDINARY_CLOUD_NAME` and `VITE_CLOUDINARY_UPLOAD_PRESET` are set in Netlify env vars.

**"I'm Interested" emails aren't arriving**
→ Check the Notification Email in Admin → Settings. Also check spam folder.

**Admin login says invalid credentials**
→ Double-check the `ADMIN_PASSWORD` env var in Railway matches what you're typing.

**Backend is down / inventory won't load**
→ Check Railway deploy logs. If the deploy failed, redeploy from the Railway dashboard.

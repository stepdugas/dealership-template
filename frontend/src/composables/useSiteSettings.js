/**
 * useSiteSettings — reactive site settings fetched from the backend.
 * Falls back to static config.js values if the backend hasn't been set up yet.
 *
 * Usage:
 *   import { siteSettings, fetchSiteSettings, HERO_PRESETS } from '../composables/useSiteSettings'
 */
import { reactive } from 'vue'
import axios from 'axios'
import {
  DEALERSHIP_NAME, TAGLINE, PHONE, EMAIL,
  ADDRESS, CITY_STATE_ZIP, FACEBOOK_URL, INSTAGRAM_URL, HOURS,
  API_BASE_URL,
} from '../config'

// Hero image presets — admin picks one, it's saved to DB as a key
export const HERO_PRESETS = [
  {
    key: 'sports',
    label: 'Sports & Luxury',
    url: 'https://images.unsplash.com/photo-1492144534655-ae79c964c9d7?auto=format&fit=crop&w=1920&q=80',
  },
  {
    key: 'muscle',
    label: 'Muscle & Classic',
    url: 'https://images.unsplash.com/photo-1544636331-e26879cd4d9b?auto=format&fit=crop&w=1920&q=80',
  },
  {
    key: 'truck',
    label: 'Trucks & Off-Road',
    url: 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?auto=format&fit=crop&w=1920&q=80',
  },
  {
    key: 'suv',
    label: 'SUVs & Crossovers',
    url: 'https://images.unsplash.com/photo-1519641471654-76ce0107ad1b?auto=format&fit=crop&w=1920&q=80',
  },
  {
    key: 'lot',
    label: 'Classic Dealership',
    url: 'https://images.unsplash.com/photo-1550355291-bbee04a92027?auto=format&fit=crop&w=1920&q=80',
  },
]

function getHeroUrl(key) {
  const preset = HERO_PRESETS.find((p) => p.key === key)
  return preset ? preset.url : HERO_PRESETS[0].url
}

// Reactive settings object — components read from this
export const siteSettings = reactive({
  businessName: DEALERSHIP_NAME,
  tagline: TAGLINE,
  phone: PHONE,
  email: EMAIL,
  address: ADDRESS,
  cityStateZip: CITY_STATE_ZIP,
  facebookUrl: FACEBOOK_URL,
  instagramUrl: INSTAGRAM_URL,
  hours: { ...HOURS },
  notificationEmail: '',
  heroImage: 'sports',
  heroUrl: HERO_PRESETS[0].url,
  // Financing page
  financingBlurb:    '',
  financingApplyUrl: '',
  // Page toggles — false by default, admin enables per client
  showStaff:           false,
  showFinancing:       false,
  showScheduleService: false,
})

// Fetch settings from backend and populate the reactive object
export async function fetchSiteSettings() {
  try {
    const res = await axios.get(`${API_BASE_URL}/api/public/settings`)
    const s = res.data

    if (s.business_name)  siteSettings.businessName  = s.business_name
    if (s.tagline)        siteSettings.tagline        = s.tagline
    if (s.phone)          siteSettings.phone          = s.phone
    if (s.email)          siteSettings.email          = s.email
    if (s.address)        siteSettings.address        = s.address
    if (s.city_state_zip) siteSettings.cityStateZip   = s.city_state_zip
    if (s.facebook_url)   siteSettings.facebookUrl    = s.facebook_url
    if (s.instagram_url)  siteSettings.instagramUrl   = s.instagram_url
    if (s.notification_email) siteSettings.notificationEmail = s.notification_email

    // Hours
    const days = ['monday','tuesday','wednesday','thursday','friday','saturday','sunday']
    days.forEach((day) => {
      if (s[`hours_${day}`]) siteSettings.hours[day] = s[`hours_${day}`]
    })

    // Hero image
    if (s.hero_image) {
      siteSettings.heroImage = s.hero_image
      siteSettings.heroUrl   = getHeroUrl(s.hero_image)
    }

    // Financing
    if (s.financing_blurb)     siteSettings.financingBlurb    = s.financing_blurb
    if (s.financing_apply_url) siteSettings.financingApplyUrl = s.financing_apply_url

    // Page toggles
    siteSettings.showStaff           = s.page_staff            === 'true'
    siteSettings.showFinancing       = s.page_financing        === 'true'
    siteSettings.showScheduleService = s.page_schedule_service === 'true'
  } catch {
    // Backend not running or not set up — silently fall back to config.js defaults
  }
}

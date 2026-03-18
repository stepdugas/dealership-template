/**
 * ============================================================
 * DEALERSHIP CONFIGURATION — swap these values per client
 * ============================================================
 * All placeholders use [BRACKET] notation so a find-replace
 * across the project will catch anything missed here.
 * ============================================================
 */

export const DEALERSHIP_NAME   = '[DEALERSHIP_NAME]'           // e.g. "Smith Auto Group"
export const TAGLINE           = '[TAGLINE]'                   // e.g. "Your Trusted Local Dealer Since 1995"
export const PHONE             = '[PHONE]'                     // e.g. "(555) 867-5309"
export const EMAIL             = '[EMAIL]'                     // e.g. "info@smithauto.com"
export const ADDRESS           = '[ADDRESS]'                   // e.g. "123 Main Street"
export const CITY_STATE_ZIP    = '[CITY_STATE_ZIP]'            // e.g. "Springfield, IL 62701"
export const LOGO_URL          = '[LOGO_URL]'                  // e.g. "/logo.png" or a CDN URL

// Business hours — displayed in header, footer, contact page
export const HOURS = {
  monday:    '[MON_HOURS]',    // e.g. "9:00 AM – 7:00 PM"
  tuesday:   '[TUE_HOURS]',
  wednesday: '[WED_HOURS]',
  thursday:  '[THU_HOURS]',
  friday:    '[FRI_HOURS]',
  saturday:  '[SAT_HOURS]',   // e.g. "9:00 AM – 5:00 PM"
  sunday:    '[SUN_HOURS]',   // e.g. "Closed"
}

// Google Maps embed URL — paste the embed src from Google Maps
export const GOOGLE_MAPS_EMBED_URL = '[GOOGLE_MAPS_EMBED_URL]'

// Social media — set to null to hide icon
export const FACEBOOK_URL  = '[FACEBOOK_URL]'   // e.g. "https://facebook.com/smithauto"
export const INSTAGRAM_URL = '[INSTAGRAM_URL]'  // e.g. "https://instagram.com/smithauto"
export const TWITTER_URL   = null               // Set to URL string to enable

// Primary brand color (CSS hex) — also update tailwind.config.js > primary
export const PRIMARY_COLOR = '#6172f0'

// Backend API base URL — update for production
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

// Cloudinary config — update with client credentials
export const CLOUDINARY_CLOUD_NAME = '[CLOUDINARY_CLOUD_NAME]'
export const CLOUDINARY_UPLOAD_PRESET = '[CLOUDINARY_UPLOAD_PRESET]'

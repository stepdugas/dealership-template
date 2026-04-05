<template>
  <div class="max-w-3xl mx-auto">
    <div class="flex items-center justify-between mb-8">
      <h1 class="text-2xl font-bold text-gray-900">Site Settings</h1>
      <button
        @click="save"
        :disabled="saving"
        class="px-6 py-2 bg-blue-600 text-white font-semibold rounded-lg hover:bg-blue-700
               disabled:opacity-50 disabled:cursor-not-allowed transition"
      >
        {{ saving ? 'Saving...' : 'Save Changes' }}
      </button>
    </div>

    <!-- Success / Error banner -->
    <div v-if="successMsg" class="mb-6 p-4 bg-green-50 border border-green-200 rounded-lg text-green-800 text-sm font-medium">
      ✓ {{ successMsg }}
    </div>
    <div v-if="errorMsg" class="mb-6 p-4 bg-red-50 border border-red-200 rounded-lg text-red-800 text-sm font-medium">
      {{ errorMsg }}
    </div>

    <div v-if="loading" class="text-center py-20 text-gray-400">Loading settings...</div>

    <div v-else class="space-y-8">

      <!-- BUSINESS INFO -->
      <section class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <h2 class="text-base font-semibold text-gray-900 mb-5 pb-3 border-b border-gray-100">
          Business Information
        </h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-1">Business Name</label>
            <input v-model="form.business_name" type="text" class="field" placeholder="e.g. Dugas Auto Sales" />
          </div>
          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-1">Tagline</label>
            <input v-model="form.tagline" type="text" class="field" placeholder="e.g. Quality Cars, Honest Deals" />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Phone Number</label>
            <input v-model="form.phone" type="tel" class="field" placeholder="(555) 123-4567" />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Email Address</label>
            <input v-model="form.email" type="email" class="field" placeholder="contact@yourdealership.com" />
          </div>
          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-1">Street Address</label>
            <input v-model="form.address" type="text" class="field" placeholder="123 Main Street" />
          </div>
          <div class="md:col-span-2">
            <label class="block text-sm font-medium text-gray-700 mb-1">City, State ZIP</label>
            <input v-model="form.city_state_zip" type="text" class="field" placeholder="Akron, OH 44301" />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Facebook URL</label>
            <input v-model="form.facebook_url" type="url" class="field" placeholder="https://facebook.com/yourpage" />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">Instagram URL</label>
            <input v-model="form.instagram_url" type="url" class="field" placeholder="https://instagram.com/yourpage" />
          </div>
        </div>
      </section>

      <!-- CUSTOMER INQUIRY EMAIL -->
      <section class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <h2 class="text-base font-semibold text-gray-900 mb-1 pb-3 border-b border-gray-100">
          Customer Inquiry Notifications
        </h2>
        <p class="text-sm text-gray-500 mb-4 mt-3">
          When a customer clicks "I'm Interested" on a vehicle, this email address will receive the notification.
        </p>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1">Notification Email</label>
          <input v-model="form.notification_email" type="email" class="field max-w-sm" placeholder="owner@yourdealership.com" />
        </div>
      </section>

      <!-- BUSINESS HOURS -->
      <section class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <h2 class="text-base font-semibold text-gray-900 mb-5 pb-3 border-b border-gray-100">
          Business Hours
        </h2>
        <div class="space-y-3">
          <div
            v-for="day in days"
            :key="day.key"
            class="flex flex-wrap items-center gap-3 p-3 bg-gray-50 rounded-lg"
            :class="{ 'opacity-50': form[day.closedKey] }"
          >
            <span class="w-24 text-sm font-semibold text-gray-700">{{ day.label }}</span>
            <input
              v-model="form[day.openKey]"
              type="time"
              class="field w-32"
              :disabled="form[day.closedKey]"
            />
            <span class="text-gray-400 text-sm">to</span>
            <input
              v-model="form[day.closeKey]"
              type="time"
              class="field w-32"
              :disabled="form[day.closedKey]"
            />
            <label class="flex items-center gap-2 ml-auto text-sm text-gray-600 cursor-pointer">
              <input
                v-model="form[day.closedKey]"
                type="checkbox"
                class="w-4 h-4 accent-blue-600 cursor-pointer"
              />
              Closed
            </label>
          </div>
        </div>
      </section>

      <!-- HERO IMAGE PICKER -->
      <section class="bg-white rounded-xl shadow-sm border border-gray-200 p-6">
        <h2 class="text-base font-semibold text-gray-900 mb-1 pb-3 border-b border-gray-100">
          Homepage Background Image
        </h2>
        <p class="text-sm text-gray-500 mb-5 mt-3">
          Pick the vibe that fits your dealership best. This is the big image customers see first.
        </p>
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
          <div
            v-for="preset in HERO_PRESETS"
            :key="preset.key"
            @click="form.hero_image = preset.key"
            class="relative cursor-pointer rounded-xl overflow-hidden border-4 transition-all"
            :class="form.hero_image === preset.key
              ? 'border-blue-500 ring-2 ring-blue-300'
              : 'border-transparent hover:border-gray-300'"
          >
            <img :src="preset.url" :alt="preset.label" class="w-full h-32 object-cover" />
            <div class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"></div>
            <div class="absolute bottom-2 left-3 text-white text-sm font-semibold">{{ preset.label }}</div>
            <div
              v-if="form.hero_image === preset.key"
              class="absolute top-2 right-2 bg-blue-500 text-white rounded-full w-6 h-6 flex items-center justify-center text-xs font-bold"
            >
              ✓
            </div>
          </div>
        </div>
      </section>

      <!-- Save button (bottom) -->
      <div class="flex justify-end pb-8">
        <button
          @click="save"
          :disabled="saving"
          class="px-8 py-3 bg-blue-600 text-white font-semibold rounded-lg hover:bg-blue-700
                 disabled:opacity-50 disabled:cursor-not-allowed transition"
        >
          {{ saving ? 'Saving...' : 'Save Changes' }}
        </button>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { adminGetSettings, adminUpdateSettings } from '../../api/index'
import { HERO_PRESETS, fetchSiteSettings } from '../../composables/useSiteSettings'

const loading  = ref(true)
const saving   = ref(false)
const successMsg = ref('')
const errorMsg   = ref('')

const days = [
  { label: 'Monday',    key: 'monday',    openKey: 'hours_monday_open',    closeKey: 'hours_monday_close',    closedKey: 'hours_monday_closed' },
  { label: 'Tuesday',   key: 'tuesday',   openKey: 'hours_tuesday_open',   closeKey: 'hours_tuesday_close',   closedKey: 'hours_tuesday_closed' },
  { label: 'Wednesday', key: 'wednesday', openKey: 'hours_wednesday_open', closeKey: 'hours_wednesday_close', closedKey: 'hours_wednesday_closed' },
  { label: 'Thursday',  key: 'thursday',  openKey: 'hours_thursday_open',  closeKey: 'hours_thursday_close',  closedKey: 'hours_thursday_closed' },
  { label: 'Friday',    key: 'friday',    openKey: 'hours_friday_open',    closeKey: 'hours_friday_close',    closedKey: 'hours_friday_closed' },
  { label: 'Saturday',  key: 'saturday',  openKey: 'hours_saturday_open',  closeKey: 'hours_saturday_close',  closedKey: 'hours_saturday_closed' },
  { label: 'Sunday',    key: 'sunday',    openKey: 'hours_sunday_open',    closeKey: 'hours_sunday_close',    closedKey: 'hours_sunday_closed' },
]

const form = reactive({
  business_name: '',
  tagline: '',
  phone: '',
  email: '',
  address: '',
  city_state_zip: '',
  facebook_url: '',
  instagram_url: '',
  notification_email: '',
  hero_image: 'sports',
  // hours
  hours_monday_open: '09:00',    hours_monday_close: '18:00',    hours_monday_closed: false,
  hours_tuesday_open: '09:00',   hours_tuesday_close: '18:00',   hours_tuesday_closed: false,
  hours_wednesday_open: '09:00', hours_wednesday_close: '18:00', hours_wednesday_closed: false,
  hours_thursday_open: '09:00',  hours_thursday_close: '18:00',  hours_thursday_closed: false,
  hours_friday_open: '09:00',    hours_friday_close: '18:00',    hours_friday_closed: false,
  hours_saturday_open: '10:00',  hours_saturday_close: '17:00',  hours_saturday_closed: false,
  hours_sunday_open: '',         hours_sunday_close: '',         hours_sunday_closed: true,
})

onMounted(async () => {
  try {
    const res = await adminGetSettings()
    const s = res.data
    // Populate text fields
    const textFields = ['business_name','tagline','phone','email','address',
                        'city_state_zip','facebook_url','instagram_url','notification_email','hero_image']
    textFields.forEach(k => { if (s[k]) form[k] = s[k] })

    // Populate hours
    days.forEach(day => {
      if (s[day.openKey])  form[day.openKey]  = s[day.openKey]
      if (s[day.closeKey]) form[day.closeKey] = s[day.closeKey]
      if (s[day.closedKey] !== undefined) form[day.closedKey] = s[day.closedKey] === 'true'
    })
  } catch {
    // New deployment — form stays at defaults
  } finally {
    loading.value = false
  }
})

async function save() {
  saving.value = true
  successMsg.value = ''
  errorMsg.value = ''

  try {
    // Build flat updates object for the backend
    const updates = {}
    const textFields = ['business_name','tagline','phone','email','address',
                        'city_state_zip','facebook_url','instagram_url','notification_email','hero_image']
    textFields.forEach(k => { updates[k] = form[k] })

    // Build hours strings and save raw fields
    days.forEach(day => {
      updates[day.openKey]   = form[day.openKey]
      updates[day.closeKey]  = form[day.closeKey]
      updates[day.closedKey] = String(form[day.closedKey])

      // Human-readable hours string for the public settings endpoint
      updates[`hours_${day.key}`] = form[day.closedKey]
        ? 'Closed'
        : `${formatTime(form[day.openKey])} – ${formatTime(form[day.closeKey])}`
    })

    await adminUpdateSettings(updates)

    // Refresh the global site settings so the live site updates immediately
    await fetchSiteSettings()

    successMsg.value = 'Settings saved! Changes are now live on your site.'
    window.scrollTo({ top: 0, behavior: 'smooth' })
  } catch {
    errorMsg.value = 'Failed to save. Make sure the backend is running and try again.'
  } finally {
    saving.value = false
  }
}

function formatTime(t) {
  if (!t) return ''
  const [h, m] = t.split(':')
  const hour = parseInt(h)
  const ampm = hour >= 12 ? 'PM' : 'AM'
  const displayHour = hour % 12 || 12
  return `${displayHour}:${m} ${ampm}`
}
</script>

<style scoped>
.field {
  @apply w-full px-3 py-2 border border-gray-300 rounded-lg text-sm
         focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent
         disabled:bg-gray-100 disabled:cursor-not-allowed transition;
}
</style>

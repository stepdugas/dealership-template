<template>
  <div class="min-h-screen bg-gradient-to-br from-slate-900 to-slate-800 py-12 px-4">
    <div class="max-w-2xl mx-auto">
      <!-- Header -->
      <div class="text-center mb-12">
        <h1 class="text-4xl font-bold text-white mb-2">Get Your Dealership Online</h1>
        <p class="text-slate-300">Tell us about your business so we can customize your site</p>
      </div>

      <!-- Form -->
      <form @submit.prevent="submitForm" class="bg-white rounded-lg shadow-xl p-8">
        <!-- Business Name -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Business Name *</label>
          <input
            v-model="form.businessName"
            type="text"
            required
            placeholder="e.g., John's Used Cars"
            class="form-input"
          />
          <span v-if="errors.businessName" class="text-red-500 text-sm mt-1">{{ errors.businessName }}</span>
        </div>

        <!-- Business Tagline -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Business Tagline</label>
          <input
            v-model="form.tagline"
            type="text"
            placeholder="e.g., Quality Cars, Honest Deals"
            class="form-input"
          />
        </div>

        <!-- Logo Upload -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Logo Upload</label>
          <div class="border-2 border-dashed border-gray-300 rounded-lg p-6 text-center hover:border-primary cursor-pointer transition">
            <input
              @change="handleLogoUpload"
              type="file"
              accept="image/*"
              class="hidden"
              ref="logoInput"
            />
            <button
              type="button"
              @click="$refs.logoInput.click()"
              class="text-primary hover:underline"
            >
              Click to upload logo
            </button>
            <p class="text-gray-500 text-sm mt-2">PNG, JPG up to 5MB</p>
            <p v-if="logoFileName" class="text-green-600 text-sm mt-2">✓ {{ logoFileName }}</p>
          </div>
        </div>

        <!-- Contact Info -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Phone *</label>
            <input
              v-model="form.phone"
              type="tel"
              required
              placeholder="(555) 123-4567"
              class="form-input"
            />
            <span v-if="errors.phone" class="text-red-500 text-sm mt-1">{{ errors.phone }}</span>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Email *</label>
            <input
              v-model="form.email"
              type="email"
              required
              placeholder="contact@business.com"
              class="form-input"
            />
            <span v-if="errors.email" class="text-red-500 text-sm mt-1">{{ errors.email }}</span>
          </div>
        </div>

        <!-- Address -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Full Address *</label>
          <input
            v-model="form.address"
            type="text"
            required
            placeholder="123 Main Street"
            class="form-input"
          />
        </div>

        <!-- City, State, ZIP -->
        <div class="grid grid-cols-2 md:grid-cols-3 gap-4 mb-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">City *</label>
            <input
              v-model="form.city"
              type="text"
              required
              placeholder="Akron"
              class="form-input"
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">State *</label>
            <input
              v-model="form.state"
              type="text"
              required
              placeholder="OH"
              maxlength="2"
              class="form-input"
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">ZIP *</label>
            <input
              v-model="form.zip"
              type="text"
              required
              placeholder="44301"
              class="form-input"
            />
          </div>
        </div>

        <!-- Hours -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Business Hours *</label>
          <div class="space-y-3">
            <div v-for="day in days" :key="day" class="flex items-center gap-4">
              <span class="w-20 font-medium text-gray-700">{{ day }}</span>
              <input
                v-model="form.hours[day].open"
                type="time"
                class="form-input w-32"
              />
              <span class="text-gray-500">to</span>
              <input
                v-model="form.hours[day].close"
                type="time"
                class="form-input w-32"
              />
            </div>
          </div>
        </div>

        <!-- Brand Color -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Primary Brand Color</label>
          <div class="flex items-center gap-4">
            <input
              v-model="form.primaryColor"
              type="color"
              class="h-12 w-20 rounded cursor-pointer"
            />
            <span class="text-gray-600">{{ form.primaryColor }}</span>
          </div>
        </div>

        <!-- Social Media -->
        <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Facebook URL</label>
            <input
              v-model="form.facebook"
              type="url"
              placeholder="https://facebook.com/yourpage"
              class="form-input"
            />
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Instagram URL</label>
            <input
              v-model="form.instagram"
              type="url"
              placeholder="https://instagram.com/yourpage"
              class="form-input"
            />
          </div>
        </div>

        <!-- About Us Content -->
        <div class="mb-6 border border-gray-200 rounded-lg p-5 space-y-4">
          <p class="text-sm font-semibold text-gray-700">About Us Page Content <span class="text-gray-400 font-normal">(optional — you can fill this in later)</span></p>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Your Story</label>
            <textarea
              v-model="form.aboutBlurb"
              rows="4"
              placeholder="Tell customers who you are. e.g., Family-owned since 1998, serving the Akron area with quality used vehicles and honest deals. No-pressure, transparent pricing, and a team that actually cares."
              class="form-input"
            ></textarea>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Mission Statement</label>
            <textarea
              v-model="form.aboutMission"
              rows="2"
              placeholder='e.g., "To provide every customer with an honest, transparent, and enjoyable car-buying experience."'
              class="form-input"
            ></textarea>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Year Founded</label>
            <input v-model="form.aboutYearFounded" type="text" placeholder="e.g. 1998" class="form-input max-w-xs" />
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-2">Stats / Bragging Rights <span class="text-gray-400 font-normal">(leave blank to hide)</span></label>
            <div class="grid grid-cols-2 gap-3">
              <div>
                <label class="block text-xs text-gray-500 mb-1">Years in Business</label>
                <input v-model="form.aboutStatYears" type="text" placeholder="e.g. 20+" class="form-input" />
              </div>
              <div>
                <label class="block text-xs text-gray-500 mb-1">Vehicles Sold</label>
                <input v-model="form.aboutStatVehicles" type="text" placeholder="e.g. 1,000+" class="form-input" />
              </div>
              <div>
                <label class="block text-xs text-gray-500 mb-1">5-Star Reviews</label>
                <input v-model="form.aboutStatReviews" type="text" placeholder="e.g. 500+" class="form-input" />
              </div>
              <div>
                <label class="block text-xs text-gray-500 mb-1">Team Members</label>
                <input v-model="form.aboutStatTeam" type="text" placeholder="e.g. 12" class="form-input" />
              </div>
            </div>
          </div>
        </div>

        <!-- Domain -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-2">Desired Website Domain</label>
          <input
            v-model="form.domain"
            type="text"
            placeholder="e.g., johnsusedcars.com — or 'I need help choosing one'"
            class="form-input"
          />
          <p class="text-xs text-gray-400 mt-1">We'll confirm availability and help you get it set up.</p>
        </div>

        <!-- Optional Pages -->
        <div class="mb-6">
          <label class="block text-sm font-medium text-gray-700 mb-3">Optional Pages <span class="text-gray-400 font-normal">(check all you'd like)</span></label>
          <div class="space-y-2">
            <label v-for="page in availablePages" :key="page.value" class="flex items-start gap-3 cursor-pointer">
              <input
                type="checkbox"
                :value="page.value"
                v-model="form.pages"
                class="mt-0.5 w-4 h-4 accent-blue-600"
              />
              <span class="text-sm text-gray-700">
                <span class="font-medium">{{ page.label }}</span>
                <span class="text-gray-400"> — {{ page.description }}</span>
              </span>
            </label>
          </div>
        </div>

        <!-- Notes -->
        <div class="mb-8">
          <label class="block text-sm font-medium text-gray-700 mb-2">Additional Notes</label>
          <textarea
            v-model="form.notes"
            rows="4"
            placeholder="Any special requests or info we should know?"
            class="form-input"
          ></textarea>
        </div>

        <p v-if="errors.submit" class="text-red-500 text-sm text-center mb-3">{{ errors.submit }}</p>

        <!-- Submit -->
        <button
          type="submit"
          :disabled="submitting"
          class="w-full btn-primary py-3 font-semibold rounded-lg"
        >
          {{ submitting ? 'Submitting...' : 'Submit' }}
        </button>
      </form>

      <!-- Success Message -->
      <div v-if="submitted" class="mt-8 p-6 bg-green-50 border border-green-200 rounded-lg">
        <h2 class="text-green-800 font-bold mb-2">✓ Form Submitted!</h2>
        <p class="text-green-700">We'll review your information and set up your site soon.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import axios from 'axios'
import { API_BASE_URL } from '../config'

const form = reactive({
  businessName: '',
  tagline: '',
  logo: null,
  phone: '',
  email: '',
  address: '',
  city: '',
  state: '',
  zip: '',
  hours: {
    Monday: { open: '09:00', close: '18:00' },
    Tuesday: { open: '09:00', close: '18:00' },
    Wednesday: { open: '09:00', close: '18:00' },
    Thursday: { open: '09:00', close: '18:00' },
    Friday: { open: '09:00', close: '18:00' },
    Saturday: { open: '10:00', close: '17:00' },
    Sunday: { open: '', close: '' }
  },
  primaryColor: '#3b82f6',
  facebook: '',
  instagram: '',
  aboutBlurb: '',
  aboutMission: '',
  aboutYearFounded: '',
  aboutStatYears: '',
  aboutStatVehicles: '',
  aboutStatReviews: '',
  aboutStatTeam: '',
  domain: '',
  pages: [],
  notes: ''
})

const availablePages = [
  { value: 'Meet the Staff',    label: 'Meet the Staff',    description: 'Team grid with photos, names, titles, and bios' },
  { value: 'Financing',         label: 'Financing',         description: 'Payment calculator + application form or external link' },
  { value: 'Schedule Service',  label: 'Schedule Service',  description: 'Calendar connector or built-in appointment request form' },
]

const days = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday']
const errors = ref({})
const submitting = ref(false)
const submitted = ref(false)
const logoFileName = ref('')
const logoInput = ref(null)

const handleLogoUpload = (e) => {
  const file = e.target.files[0]
  if (file) {
    logoFileName.value = file.name
    const reader = new FileReader()
    reader.onload = (event) => {
      form.logo = event.target.result
    }
    reader.readAsDataURL(file)
  }
}

const validateForm = () => {
  errors.value = {}
  
  if (!form.businessName) errors.value.businessName = 'Business name is required'
  if (!form.phone) errors.value.phone = 'Phone is required'
  if (!form.email) errors.value.email = 'Email is required'
  if (!form.address) errors.value.address = 'Address is required'
  
  return Object.keys(errors.value).length === 0
}

const submitForm = async () => {
  if (!validateForm()) return
  submitting.value = true

  // Flatten hours: { Monday: { open, close } } → { Monday: "9:00 AM – 6:00 PM" }
  const hoursFlat = {}
  for (const [day, val] of Object.entries(form.hours)) {
    hoursFlat[day] = (val.open && val.close) ? `${val.open} – ${val.close}` : 'Closed'
  }

  try {
    await axios.post(`${API_BASE_URL}/api/client-intake`, {
      businessName: form.businessName,
      tagline:      form.tagline,
      phone:        form.phone,
      email:        form.email,
      address:      form.address,
      city:         form.city,
      state:        form.state,
      zip:          form.zip,
      hours:        hoursFlat,
      primaryColor: form.primaryColor,
      facebook:     form.facebook,
      instagram:    form.instagram,
      aboutBlurb:        form.aboutBlurb,
      aboutMission:      form.aboutMission,
      aboutYearFounded:  form.aboutYearFounded,
      aboutStatYears:    form.aboutStatYears,
      aboutStatVehicles: form.aboutStatVehicles,
      aboutStatReviews:  form.aboutStatReviews,
      aboutStatTeam:     form.aboutStatTeam,
      domain:            form.domain,
      pages:        form.pages,
      notes:        form.notes,
    })
    submitted.value = true
  } catch {
    errors.value.submit = 'Something went wrong. Please email us directly at openclaw@gmail.com'
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.form-input {
  @apply w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition;
}

.btn-primary {
  @apply bg-blue-600 text-white hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed transition;
}
</style>

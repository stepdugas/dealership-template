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
  notes: ''
})

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
  
  // Simulate submission (in real app, send to backend)
  setTimeout(() => {
    console.log('Form submitted:', form)
    submitted.value = true
    submitting.value = false
  }, 1500)
}
</script>

<style scoped>
.form-input {
  @apply w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary focus:border-transparent outline-none transition;
}

.btn-primary {
  @apply bg-blue-600 text-white hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed transition;
}
</style>

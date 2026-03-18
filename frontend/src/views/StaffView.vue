<!--
  StaffView — Meet the Staff scaffold page.
  [SCAFFOLD] Replace placeholder cards with real staff data.
-->
<template>
  <PageLayout>
    <div class="bg-dark-800 pt-28 pb-12">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-4xl font-extrabold text-white" data-aos="fade-up">Meet the Team</h1>
        <p class="text-gray-400 mt-2" data-aos="fade-up" data-aos-delay="100">
          The friendly faces behind {{ DEALERSHIP_NAME }}.
        </p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
        <div
          v-for="(member, i) in staff"
          :key="member.name"
          class="card text-center p-6"
          data-aos="fade-up"
          :data-aos-delay="i * 80"
        >
          <!-- [STAFF_PHOTO] -->
          <div class="w-24 h-24 rounded-full bg-gray-200 mx-auto mb-4 overflow-hidden">
            <img
              v-if="member.photo && !member.photo.startsWith('[')"
              :src="member.photo"
              :alt="member.name"
              class="w-full h-full object-cover"
            />
            <div v-else class="w-full h-full flex items-center justify-center text-gray-400 text-2xl font-bold">
              {{ member.name[0] }}
            </div>
          </div>
          <h3 class="font-bold text-gray-900">{{ member.name }}</h3>
          <p class="text-primary-600 text-sm font-medium">{{ member.title }}</p>
          <p class="text-gray-500 text-sm mt-2">{{ member.bio }}</p>
        </div>
      </div>
    </div>
  </PageLayout>
</template>

<script setup>
import PageLayout from '../components/layout/PageLayout.vue'
import { DEALERSHIP_NAME } from '../config'

// [STAFF_LIST] — replace with real staff data
const staff = [
  { name: '[STAFF_NAME_1]', title: '[STAFF_TITLE_1]', photo: '[STAFF_PHOTO_1]', bio: '[STAFF_BIO_1]' },
  { name: '[STAFF_NAME_2]', title: '[STAFF_TITLE_2]', photo: '[STAFF_PHOTO_2]', bio: '[STAFF_BIO_2]' },
  { name: '[STAFF_NAME_3]', title: '[STAFF_TITLE_3]', photo: '[STAFF_PHOTO_3]', bio: '[STAFF_BIO_3]' },
  { name: '[STAFF_NAME_4]', title: '[STAFF_TITLE_4]', photo: '[STAFF_PHOTO_4]', bio: '[STAFF_BIO_4]' },
]
</script>

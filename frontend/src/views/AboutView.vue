<!--
  AboutView — About Us page.
  All copy uses [BRACKET] placeholders — swap per client.
-->
<template>
  <PageLayout>
    <!-- Hero banner -->
    <div class="relative bg-dark-800 pt-28 pb-20 overflow-hidden">
      <div class="absolute inset-0 opacity-20">
        <img
          src="https://images.unsplash.com/photo-1560958089-b8a1929cea89?auto=format&fit=crop&w=1920&q=60"
          alt=""
          class="w-full h-full object-cover"
        />
      </div>
      <div class="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <p class="text-primary-400 text-sm font-semibold uppercase tracking-widest mb-3" data-aos="fade-up">Our Story</p>
        <h1 class="text-4xl md:text-5xl font-extrabold text-white" data-aos="fade-up" data-aos-delay="100">
          About {{ DEALERSHIP_NAME }}
        </h1>
        <p class="text-gray-300 text-lg mt-4 max-w-2xl" data-aos="fade-up" data-aos-delay="200">
          <!-- [ABOUT_TAGLINE] -->
          Serving the community with integrity and passion since [FOUNDING_YEAR].
        </p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-20">
      <div class="grid grid-cols-1 lg:grid-cols-2 gap-16 items-center">

        <!-- Story text -->
        <div data-aos="fade-right">
          <h2 class="section-title mb-6">Who We Are</h2>

          <!-- [ABOUT_STORY_PARAGRAPH_1] -->
          <p class="text-gray-600 leading-relaxed mb-5">
            [ABOUT_STORY_PARAGRAPH_1] — e.g. "Founded in [FOUNDING_YEAR] by [FOUNDER_NAME],
            [DEALERSHIP_NAME] has been a cornerstone of the [CITY] automotive community for over
            [YEARS_IN_BUSINESS] years. What started as a small family operation has grown into one
            of the region's most trusted dealerships."
          </p>

          <!-- [ABOUT_STORY_PARAGRAPH_2] -->
          <p class="text-gray-600 leading-relaxed mb-5">
            [ABOUT_STORY_PARAGRAPH_2] — e.g. "We believe car buying should be simple and stress-free.
            Our no-pressure approach and commitment to transparent pricing means you always know
            exactly what you're getting — and what you're paying."
          </p>

          <!-- [ABOUT_STORY_PARAGRAPH_3] -->
          <p class="text-gray-600 leading-relaxed">
            [ABOUT_STORY_PARAGRAPH_3] — e.g. "Whether you're looking for your first car or your
            tenth, our team of experts is here to guide you every step of the way."
          </p>
        </div>

        <!-- Image -->
        <div data-aos="fade-left">
          <div class="rounded-2xl overflow-hidden shadow-2xl">
            <!-- [ABOUT_PHOTO] — replace with dealership/showroom photo -->
            <img
              src="https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?auto=format&fit=crop&w=900&q=80"
              alt="[DEALERSHIP_NAME] showroom"
              class="w-full h-96 object-cover"
            />
          </div>
        </div>
      </div>

      <!-- Stats row -->
      <div class="grid grid-cols-2 md:grid-cols-4 gap-8 mt-20">
        <div
          v-for="(stat, i) in stats"
          :key="stat.label"
          class="text-center"
          data-aos="fade-up"
          :data-aos-delay="i * 100"
        >
          <p class="text-4xl font-extrabold text-primary-600">{{ stat.value }}</p>
          <p class="text-gray-500 text-sm mt-2">{{ stat.label }}</p>
        </div>
      </div>

      <!-- Mission statement -->
      <div class="mt-20 bg-dark-800 rounded-2xl p-10 text-center" data-aos="fade-up">
        <h2 class="text-2xl font-bold text-white mb-4">Our Mission</h2>
        <p class="text-gray-300 text-lg leading-relaxed max-w-3xl mx-auto">
          <!-- [MISSION_STATEMENT] -->
          "[MISSION_STATEMENT] — e.g. To provide every customer with an honest, transparent, and
          enjoyable car-buying experience while delivering the highest quality vehicles at fair prices."
        </p>
      </div>
    </div>
  </PageLayout>
</template>

<script setup>
import PageLayout from '../components/layout/PageLayout.vue'
import { DEALERSHIP_NAME } from '../config'

// [ABOUT_STATS] — replace with real numbers
const stats = [
  { value: '[XX]+',   label: 'Years in Business' },
  { value: '[X,XXX]+', label: 'Vehicles Sold' },
  { value: '[XXX]+',  label: 'Five-Star Reviews' },
  { value: '[XX]+',   label: 'Team Members' },
]
</script>

<!--
  FinancingView — Financing scaffold page.
  [SCAFFOLD] Wire up finance application form when ready.
-->
<template>
  <PageLayout>
    <div class="bg-dark-800 pt-28 pb-12">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-4xl font-extrabold text-white" data-aos="fade-up">Financing</h1>
        <p class="text-gray-400 mt-2" data-aos="fade-up" data-aos-delay="100">
          Get pre-approved today — all credit situations welcome.
        </p>
      </div>
    </div>

    <div class="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-16 text-center" data-aos="fade-up">
      <div class="bg-primary-50 rounded-2xl p-12 border border-primary-100">
        <div class="w-16 h-16 bg-primary-100 rounded-2xl flex items-center justify-center mx-auto mb-6">
          <svg class="w-8 h-8 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
          </svg>
        </div>
        <h2 class="text-2xl font-bold text-gray-900 mb-3">Flexible Financing Options</h2>
        <!-- [FINANCING_COPY] -->
        <p class="text-gray-600 leading-relaxed mb-8">
          [FINANCING_COPY] — e.g. We work with a network of trusted lenders to find the best rate for your
          situation. Whether you have excellent credit or are rebuilding, we'll help get you behind the wheel.
        </p>
        <RouterLink to="/contact" class="btn-primary text-base px-10 py-4">
          Apply Now — Contact Us
        </RouterLink>
        <p class="text-gray-400 text-sm mt-4">Or call <a :href="'tel:' + PHONE" class="underline hover:text-gray-600">{{ PHONE }}</a></p>
      </div>
    </div>
  </PageLayout>
</template>

<script setup>
import { RouterLink } from 'vue-router'
import PageLayout from '../components/layout/PageLayout.vue'
import { PHONE } from '../config'
</script>

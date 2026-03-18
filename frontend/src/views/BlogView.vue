<!--
  BlogView — Blog scaffold page.
  [SCAFFOLD] Connect to CMS or add posts array when ready.
-->
<template>
  <PageLayout>
    <div class="bg-dark-800 pt-28 pb-12">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-4xl font-extrabold text-white" data-aos="fade-up">Blog & News</h1>
        <p class="text-gray-400 mt-2">Tips, updates, and automotive news.</p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <div
          v-for="(post, i) in posts"
          :key="i"
          class="card"
          data-aos="fade-up"
          :data-aos-delay="i * 80"
        >
          <div class="h-48 bg-gray-200 flex items-center justify-center text-gray-400 text-sm">
            [POST_IMAGE_{{ i + 1 }}]
          </div>
          <div class="p-6">
            <p class="text-xs text-primary-500 font-semibold uppercase tracking-wide mb-2">{{ post.category }}</p>
            <h3 class="font-bold text-gray-900 text-lg leading-snug mb-2">{{ post.title }}</h3>
            <p class="text-gray-500 text-sm leading-relaxed">{{ post.excerpt }}</p>
            <button class="text-primary-600 text-sm font-semibold mt-4 hover:underline">Read More →</button>
          </div>
        </div>
      </div>
    </div>
  </PageLayout>
</template>

<script setup>
import PageLayout from '../components/layout/PageLayout.vue'

// [BLOG_POSTS] — replace with real posts or CMS integration
const posts = [
  { title: '[POST_TITLE_1]', category: '[CATEGORY_1]', excerpt: '[POST_EXCERPT_1]' },
  { title: '[POST_TITLE_2]', category: '[CATEGORY_2]', excerpt: '[POST_EXCERPT_2]' },
  { title: '[POST_TITLE_3]', category: '[CATEGORY_3]', excerpt: '[POST_EXCERPT_3]' },
]
</script>

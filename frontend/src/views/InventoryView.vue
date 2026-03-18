<!--
  InventoryView — full inventory page with filter bar and paginated car grid.
-->
<template>
  <PageLayout>
    <!-- Page header banner -->
    <div class="bg-dark-800 pt-28 pb-12">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <h1 class="text-3xl md:text-4xl font-extrabold text-white">Browse Inventory</h1>
        <p class="text-gray-400 mt-2">
          {{ total !== null ? `${total} vehicle${total !== 1 ? 's' : ''} available` : 'Loading…' }}
        </p>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-10">

      <!-- Filter bar -->
      <FilterBar v-model="filters" class="mb-8" />

      <!-- Loading skeletons -->
      <div v-if="loading" class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
        <div v-for="n in 9" :key="n" class="card animate-pulse">
          <div class="h-52 bg-gray-200"></div>
          <div class="p-5 space-y-3">
            <div class="h-4 bg-gray-200 rounded w-3/4"></div>
            <div class="h-3 bg-gray-200 rounded w-1/2"></div>
            <div class="h-8 bg-gray-200 rounded w-full mt-4"></div>
          </div>
        </div>
      </div>

      <!-- Error state -->
      <div v-else-if="error" class="text-center py-24 text-gray-500">
        <p class="text-lg">Failed to load inventory. Please try again later.</p>
        <button class="btn-primary mt-6" @click="fetchInventory">Retry</button>
      </div>

      <!-- Empty state -->
      <div v-else-if="cars.length === 0" class="text-center py-24 text-gray-500">
        <p class="text-lg font-medium">No vehicles matched your filters.</p>
        <p class="mt-2 text-sm">Try adjusting or clearing the filters above.</p>
      </div>

      <!-- Car grid -->
      <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
        <CarCard
          v-for="(car, i) in cars"
          :key="car.id"
          :car="car"
          data-aos="fade-up"
          :data-aos-delay="(i % 3) * 80"
        />
      </div>

      <!-- Pagination -->
      <div v-if="totalPages > 1" class="flex items-center justify-center gap-2 mt-12">
        <button
          class="px-4 py-2 rounded-lg border font-medium text-sm transition-colors"
          :class="page === 0 ? 'opacity-40 cursor-not-allowed' : 'hover:bg-gray-100'"
          :disabled="page === 0"
          @click="page--"
        >
          ← Prev
        </button>

        <span class="text-sm text-gray-500 px-4">
          Page {{ page + 1 }} of {{ totalPages }}
        </span>

        <button
          class="px-4 py-2 rounded-lg border font-medium text-sm transition-colors"
          :class="page >= totalPages - 1 ? 'opacity-40 cursor-not-allowed' : 'hover:bg-gray-100'"
          :disabled="page >= totalPages - 1"
          @click="page++"
        >
          Next →
        </button>
      </div>
    </div>
  </PageLayout>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import PageLayout from '../components/layout/PageLayout.vue'
import FilterBar  from '../components/inventory/FilterBar.vue'
import CarCard    from '../components/inventory/CarCard.vue'
import { getInventory } from '../api'

const cars       = ref([])
const loading    = ref(true)
const error      = ref(false)
const total      = ref(null)
const page       = ref(0)
const totalPages = ref(1)
const PAGE_SIZE  = 12

const filters = ref({})

async function fetchInventory() {
  loading.value = true
  error.value   = false
  try {
    const res = await getInventory({ ...filters.value, page: page.value, size: PAGE_SIZE })
    const data = res.data

    // Handle both Spring Page<> responses and plain arrays
    if (data.content !== undefined) {
      cars.value       = data.content
      total.value      = data.totalElements
      totalPages.value = data.totalPages
    } else {
      cars.value       = data
      total.value      = data.length
      totalPages.value = 1
    }
  } catch {
    error.value = true
  } finally {
    loading.value = false
  }
}

// Re-fetch when filters or page changes, reset to page 0 on filter change
watch(filters, () => { page.value = 0; fetchInventory() }, { deep: true })
watch(page, fetchInventory)

onMounted(fetchInventory)
</script>

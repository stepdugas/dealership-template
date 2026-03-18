<!--
  AdminDashboardView — shell layout for all admin sub-pages.
  Contains the sidebar nav and renders child routes via <RouterView>.
-->
<template>
  <div class="min-h-screen bg-gray-100 flex flex-col md:flex-row">

    <!-- Sidebar -->
    <aside
      class="w-full md:w-64 bg-dark-900 text-white flex flex-col flex-shrink-0"
      :class="sidebarOpen ? 'block' : 'hidden md:flex'"
    >
      <!-- Sidebar header -->
      <div class="flex items-center justify-between px-6 py-5 border-b border-white/10">
        <RouterLink to="/" class="font-bold text-lg">{{ DEALERSHIP_NAME }}</RouterLink>
        <button class="md:hidden text-gray-400 hover:text-white" @click="sidebarOpen = false">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
          </svg>
        </button>
      </div>

      <!-- Nav links -->
      <nav class="flex-1 px-4 py-6 space-y-1">
        <RouterLink
          v-for="link in navLinks"
          :key="link.to"
          :to="link.to"
          class="flex items-center gap-3 px-4 py-2.5 rounded-lg text-sm font-medium
                 text-gray-300 hover:text-white hover:bg-white/10 transition-colors"
          active-class="bg-primary-600 text-white"
        >
          <component :is="link.icon" class="w-5 h-5" />
          {{ link.label }}
        </RouterLink>
      </nav>

      <!-- Sign out -->
      <div class="px-4 pb-6 border-t border-white/10 pt-4">
        <button
          class="flex items-center gap-3 px-4 py-2.5 w-full rounded-lg text-sm font-medium
                 text-gray-400 hover:text-white hover:bg-red-500/20 transition-colors"
          @click="signOut"
        >
          <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"/>
          </svg>
          Sign Out
        </button>
      </div>
    </aside>

    <!-- Main area -->
    <div class="flex-1 flex flex-col min-w-0">
      <!-- Mobile topbar -->
      <header class="md:hidden bg-dark-900 text-white flex items-center justify-between px-4 py-3">
        <button @click="sidebarOpen = true" class="p-2">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"/>
          </svg>
        </button>
        <span class="font-semibold">Admin Panel</span>
        <div class="w-10"></div>
      </header>

      <!-- Page content -->
      <main class="flex-1 p-6 overflow-auto">
        <RouterView />
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, h } from 'vue'
import { RouterLink, RouterView, useRouter } from 'vue-router'
import { DEALERSHIP_NAME } from '../../config'

const router     = useRouter()
const sidebarOpen = ref(false)

// Simple SVG icon wrappers for sidebar nav
const CarIcon = { render: () => h('svg', { fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2',
    d: 'M9 17a2 2 0 11-4 0 2 2 0 014 0zM19 17a2 2 0 11-4 0 2 2 0 014 0z'}),
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2',
    d: 'M13 16V6a1 1 0 00-1-1H4a1 1 0 00-1 1v10l2 1h8zM13 16l2 1h4l2-1v-5l-3.5-5.5H13v5z'}),
]) }

const MailIcon = { render: () => h('svg', { fill: 'none', stroke: 'currentColor', viewBox: '0 0 24 24' }, [
  h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2',
    d: 'M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z'}),
]) }

const navLinks = [
  { label: 'Inventory',    to: '/admin/cars',     icon: CarIcon },
  { label: 'Contacts',     to: '/admin/contacts', icon: MailIcon },
]

function signOut() {
  localStorage.removeItem('admin_token')
  router.push({ name: 'AdminLogin' })
}
</script>

<template>
  <!-- RouterView renders whichever page component matches the current route -->
  <RouterView />
</template>

<script setup>
import { RouterView } from 'vue-router'
import { DEALERSHIP_NAME } from './config'

// Update the browser tab title with the dealership name
document.title = DEALERSHIP_NAME
</script>

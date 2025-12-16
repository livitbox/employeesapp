<script setup lang="ts">
import { RouterView } from 'vue-router'
import Toolbar from 'primevue/toolbar'
import Button from 'primevue/button'
</script>

<template>
  <header>
    <Toolbar>
      <template #start>
        <nav>
          <RouterLink to="/">
            <Button label="Home" class="mr-2" text />
          </RouterLink>
          <RouterLink to="/upsert">
            <Button label="Insert" class="mr-2" text />
          </RouterLink>
        </nav>
      </template>
    </Toolbar>
  </header>
  <RouterView />
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}
</style>

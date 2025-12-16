import { createRouter, createWebHistory } from 'vue-router'
import UpsertView from '@/view/UpsertView.vue'
import DataView from '@/view/DataView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: DataView,
    },
    {
      path: '/upsert/:id?',
      name: 'upsert',
      component: UpsertView,
    },
  ],
})

export default router

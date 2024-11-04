import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      component: HomeView
    },
    {
      path: '/manage',
      name: 'manage',
      component: ()=>import('../views/Manage.vue'),
      children:[
        {
          path:'user',
          name:'user',
          component: ()=>import('../views/UserManage.vue')
        }
      ]
    }
  ]
})

export default router

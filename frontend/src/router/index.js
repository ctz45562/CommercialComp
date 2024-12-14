import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path:'/',
      redirect:'/login'
    },
    {
      path: '/home',
      name: 'home',
      component: ()=>import('../views/Home.vue'),
      children:[
        {
          path:'rand',
          meta:{title:'随便逛逛'},
          component:()=>import('../views/Rand.vue')
        },
        {
          path:'search',
          meta: {title:'搜索'},
          component: ()=>import('../views/Search.vue')
        },
        {
          path:'info',
          meta: {title:'个人信息'},
          component: ()=>import('../views/Info.vue')
        }
      ]
    },{
      path: '/mobile',
      name: 'mobile',
      component: ()=>import('../views/HomeMobile.vue'),
      children:[
        {
          path:'rand',
          meta:{title:'随便逛逛'},
          component:()=>import('../views/RandMobile.vue')
        },
        {
          path:'search',
          name:'search',
          meta: {title:'搜索'},
          component: ()=>import('../views/SearchMobile.vue')
        },
        {
          path:'info',
          meta: {title:'个人信息'},
          component: ()=>import('../views/Info.vue')
        }
      ]
    },
    {
      path: '/manage',
      name: 'manage',
      component: ()=>import('../views/Manage.vue'),
      children:[
        {
          path:'user',
          meta: {title:'用户管理'},
          component: ()=>import('../views/UserManage.vue')
        },
        {
          path:'commercial',
          meta: {title:'商品管理'},
          component: ()=>import('../views/CommercialManage.vue')
        },
        {
          path:'info',
          meta: {title:'个人信息'},
          component: ()=>import('../views/Info.vue')
        }
      ]
    },
    {
      path:'/login',
      name:'login',
      meta:{title:'登录'},
      component: ()=>import('../views/Login.vue')
    },
    {
      path:'/register',
      name:'register',
      meta:{title:'注册'},
      component: ()=>import('../views/Register.vue')
    }
  ]
})
router.beforeEach((to, from, next) => {
  document.title = to.meta.title;
  next()
})
export default router

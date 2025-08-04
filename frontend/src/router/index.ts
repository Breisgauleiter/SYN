import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

// Sacred Consciousness Views
const SacredHome = () => import('@/views/SacredHome.vue')
const AuthLogin = () => import('@/views/auth/AuthLogin.vue')
const AuthRegister = () => import('@/views/auth/AuthRegister.vue')
const ConsciousnessProfile = () => import('@/views/consciousness/ConsciousnessProfile.vue')
const GeneKeysExplorer = () => import('@/views/gene-keys/GeneKeysExplorer.vue')
const CommunityCodonRings = () => import('@/views/community/CommunityCodonRings.vue')
const SynQuestsDashboard = () => import('@/views/quests/SynQuestsDashboard.vue')
const SacredGeometryLab = () => import('@/views/sacred-geometry/SacredGeometryLab.vue')
const ContributingIntegrationTest = () => import('@/views/test/ContributingIntegrationTest.vue')

// Sacred Route Definitions
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'SacredHome',
    component: SacredHome,
    meta: {
      title: 'SYNtopia 2.0 - Sacred Consciousness Platform',
      requiresAuth: false,
      transition: 'sacred-fade',
      consciousness: 'home'
    }
  },
  {
    path: '/auth',
    name: 'Auth',
    redirect: '/auth/login',
    children: [
      {
        path: 'login',
        name: 'AuthLogin',
        component: AuthLogin,
        meta: {
          title: 'Sacred Login - SYNtopia',
          requiresAuth: false,
          transition: 'sacred-slide-up'
        }
      },
      {
        path: 'register',
        name: 'AuthRegister',
        component: AuthRegister,
        meta: {
          title: 'Sacred Registration - SYNtopia',
          requiresAuth: false,
          transition: 'sacred-slide-up'
        }
      }
    ]
  },
  {
    path: '/consciousness',
    name: 'Consciousness',
    children: [
      {
        path: 'profile',
        name: 'ConsciousnessProfile',
        component: ConsciousnessProfile,
        meta: {
          title: 'Consciousness Profile - SYNtopia',
          requiresAuth: true,
          consciousness: 'profile'
        }
      }
    ]
  },
  {
    path: '/gene-keys',
    name: 'GeneKeys',
    children: [
      {
        path: 'explorer',
        name: 'GeneKeysExplorer',
        component: GeneKeysExplorer,
        meta: {
          title: 'Gene Keys Explorer - SYNtopia',
          requiresAuth: true,
          consciousness: 'gene-keys'
        }
      }
    ]
  },
  {
    path: '/community',
    name: 'Community',
    children: [
      {
        path: 'codon-rings',
        name: 'CommunityCodonRings',
        component: CommunityCodonRings,
        meta: {
          title: 'Codon Rings Community - SYNtopia',
          requiresAuth: true,
          consciousness: 'community'
        }
      }
    ]
  },
  {
    path: '/syn-quests',
    name: 'SynQuests',
    component: SynQuestsDashboard,
    meta: {
      title: 'SYN Quests Dashboard - SYNtopia',
      requiresAuth: true,
      consciousness: 'quests'
    }
  },
  {
    path: '/test/contributing',
    name: 'ContributingTest',
    component: ContributingIntegrationTest,
    meta: {
      title: 'Contributing Integration Test - SYNtopia',
      requiresAuth: false,
      consciousness: 'test'
    }
  },
  {
    path: '/sacred-geometry',
    name: 'SacredGeometry',
    component: SacredGeometryLab,
    meta: {
      title: 'Sacred Geometry Lab - SYNtopia',
      requiresAuth: true,
      consciousness: 'geometry'
    }
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/NotFound.vue'),
    meta: {
      title: 'Sacred Path Not Found - SYNtopia',
      requiresAuth: false
    }
  }
]

// Create Sacred Router
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else if (to.hash) {
      return {
        el: to.hash,
        behavior: 'smooth'
      }
    } else {
      return { top: 0, behavior: 'smooth' }
    }
  }
})

// Sacred Navigation Guards
router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()
  
  // Set document title
  document.title = to.meta.title as string || 'SYNtopia 2.0'
  
  // Check authentication requirements
  if (to.meta.requiresAuth && !authStore.isAuthenticated) {
    next({ name: 'AuthLogin', query: { redirect: to.fullPath } })
    return
  }
  
  // Redirect authenticated users away from auth pages
  if (to.path.startsWith('/auth') && authStore.isAuthenticated) {
    next({ name: 'SacredHome' })
    return
  }
  
  next()
})

// Sacred After Each Hook
router.afterEach((to, from) => {
  // Track consciousness navigation
  console.log(`🧭 Sacred Navigation: ${from.name} → ${to.name}`)
  
  // Update consciousness state based on route
  if (to.meta.consciousness) {
    // TODO: Update consciousness tracking
  }
})

export default router

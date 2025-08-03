<template>
  <div id="app" class="sacred-consciousness-app">
    <!-- Sacred Consciousness Header -->
    <SacredHeader v-if="!isAuthPage" />
    
    <!-- Main Sacred Content Area -->
    <main class="sacred-main" :class="{ 'auth-layout': isAuthPage }">
      <router-view v-slot="{ Component, route }">
        <transition 
          :name="route.meta.transition || 'sacred-fade'"
          mode="out-in"
          appear
        >
          <component :is="Component" :key="route.path" />
        </transition>
      </router-view>
    </main>
    
    <!-- Sacred Consciousness Navigation -->
    <SacredNavigation v-if="!isAuthPage && isAuthenticated" />
    
    <!-- Sacred Geometry Background -->
    <SacredGeometryBackground />
    
    <!-- Consciousness Notifications -->
    <ConsciousnessNotifications />
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useConsciousnessStore } from '@/stores/consciousness'

// Sacred Components
import SacredHeader from '@/components/layout/SacredHeader.vue'
import SacredNavigation from '@/components/layout/SacredNavigation.vue'
import SacredGeometryBackground from '@/components/sacred-geometry/SacredGeometryBackground.vue'
import ConsciousnessNotifications from '@/components/consciousness/ConsciousnessNotifications.vue'

// Sacred Stores
const authStore = useAuthStore()
const consciousnessStore = useConsciousnessStore()
const route = useRoute()

// Sacred Computed Properties
const isAuthPage = computed(() => 
  route.path.includes('/auth') || route.path.includes('/login') || route.path.includes('/register')
)

const isAuthenticated = computed(() => authStore.isAuthenticated)

// Sacred Lifecycle
onMounted(async () => {
  // Initialize Sacred Consciousness
  await consciousnessStore.initializeSacredConsciousness()
  
  // Check authentication status
  await authStore.checkAuthStatus()
  
  // Start consciousness monitoring
  consciousnessStore.startConsciousnessMonitoring()
})
</script>

<style lang="scss">
.sacred-consciousness-app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--sacred-deep-space);
  color: var(--sacred-silver);
  font-family: 'Inter', sans-serif;
  overflow-x: hidden;
}

.sacred-main {
  flex: 1;
  position: relative;
  z-index: 1;
  
  &.auth-layout {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 100vh;
  }
}

/* Sacred Transitions */
.sacred-fade-enter-active,
.sacred-fade-leave-active {
  transition: all 0.618s cubic-bezier(0.618, 0, 0.382, 1);
}

.sacred-fade-enter-from {
  opacity: 0;
  transform: translateY(20px) scale(0.95);
}

.sacred-fade-leave-to {
  opacity: 0;
  transform: translateY(-20px) scale(1.05);
}

/* Sacred Scroll Behavior */
html {
  scroll-behavior: smooth;
}

/* Sacred Selection */
::selection {
  background: var(--sacred-gold);
  color: var(--sacred-deep-space);
}

::-moz-selection {
  background: var(--sacred-gold);
  color: var(--sacred-deep-space);
}
</style>

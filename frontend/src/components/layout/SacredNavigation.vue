<template>
  <nav class="sacred-navigation" :class="{ 'mobile-open': isMobileOpen }">
    <div class="nav-container">
      <!-- Primary Navigation -->
      <ul class="nav-primary">
        <li class="nav-item">
          <router-link to="/" class="nav-link" @click="closeMobile">
            <span class="nav-icon">🧘</span>
            <span class="nav-text">Consciousness</span>
          </router-link>
        </li>
        
        <li class="nav-item">
          <router-link to="/gene-keys/explorer" class="nav-link" @click="closeMobile">
            <span class="nav-icon">🔑</span>
            <span class="nav-text">Gene Keys</span>
          </router-link>
        </li>
        
        <li class="nav-item">
          <router-link to="/syn-quests" class="nav-link" @click="closeMobile">
            <span class="nav-icon">🎯</span>
            <span class="nav-text">SynQuests</span>
          </router-link>
        </li>
        
        <li class="nav-item">
          <router-link to="/sacred-geometry" class="nav-link" @click="closeMobile">
            <span class="nav-icon">🌀</span>
            <span class="nav-text">Sacred Geometry</span>
          </router-link>
        </li>
        
        <li class="nav-item">
          <router-link to="/community/codon-rings" class="nav-link" @click="closeMobile">
            <span class="nav-icon">👥</span>
            <span class="nav-text">Community</span>
          </router-link>
        </li>
      </ul>
      
      <!-- Consciousness Metrics -->
      <div class="nav-metrics" v-if="isAuthenticated">
        <div class="metric-item">
          <span class="metric-icon">🌟</span>
          <span class="metric-value">{{ consciousnessScore }}</span>
        </div>
        
        <div class="metric-item">
          <span class="metric-icon">✨</span>
          <span class="metric-value">{{ synchronicityCount }}</span>
        </div>
        
        <div class="metric-item">
          <span class="metric-icon">🎯</span>
          <span class="metric-value">{{ completedQuests }}</span>
        </div>
      </div>
      
      <!-- Secondary Actions -->
      <div class="nav-actions">
        <button 
          class="notification-btn"
          @click="toggleNotifications"
          :class="{ active: hasNotifications }"
        >
          <span class="notification-icon">🔔</span>
          <span v-if="notificationCount > 0" class="notification-badge">{{ notificationCount }}</span>
        </button>
        
        <button class="sacred-mode-toggle" @click="toggleSacredMode">
          <span class="mode-icon">{{ sacredModeIcon }}</span>
        </button>
      </div>
    </div>
    
    <!-- Mobile Navigation Overlay -->
    <div 
      v-if="isMobileOpen" 
      class="mobile-overlay" 
      @click="closeMobile"
    ></div>
  </nav>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useConsciousnessStore } from '@/stores/consciousness'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()
const consciousnessStore = useConsciousnessStore()

// Props
interface Props {
  isMobileOpen?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  isMobileOpen: false
})

// Emits
const emit = defineEmits<{
  closeMobile: []
  toggleNotifications: []
}>()

// State
const sacredMode = ref<'light' | 'dark' | 'cosmic'>('cosmic')
const showingTooltip = ref<string | null>(null)

// Computed
const isAuthenticated = computed(() => authStore.isAuthenticated)
const consciousnessScore = computed(() => 
  consciousnessStore.metrics?.consciousnessScore || 0
)
const synchronicityCount = computed(() => 
  consciousnessStore.synchronicities?.length || 0
)
const completedQuests = computed(() => 
  consciousnessStore.completedQuests?.length || 0
)
const notificationCount = computed(() => 
  consciousnessStore.notifications?.filter(n => !n.read).length || 0
)
const hasNotifications = computed(() => notificationCount.value > 0)

const sacredModeIcon = computed(() => {
  const icons = {
    light: '☀️',
    dark: '🌙', 
    cosmic: '🌌'
  }
  return icons[sacredMode.value]
})

// Methods
function closeMobile() {
  emit('closeMobile')
}

function toggleNotifications() {
  emit('toggleNotifications')
}

function toggleSacredMode() {
  const modes: Array<'light' | 'dark' | 'cosmic'> = ['cosmic', 'dark', 'light']
  const currentIndex = modes.indexOf(sacredMode.value)
  const nextIndex = (currentIndex + 1) % modes.length
  sacredMode.value = modes[nextIndex]
  
  // Apply theme to document
  document.documentElement.setAttribute('data-theme', sacredMode.value)
  
  console.log(`🎨 Sacred mode switched to: ${sacredMode.value}`)
}

function showTooltip(item: string) {
  showingTooltip.value = item
}

function hideTooltip() {
  showingTooltip.value = null
}

// Keyboard navigation
function handleKeydown(event: KeyboardEvent) {
  if (event.altKey && event.key >= '1' && event.key <= '5') {
    event.preventDefault()
    const routes = ['/', '/gene-keys/explorer', '/syn-quests', '/sacred-geometry', '/community/codon-rings']
    const index = parseInt(event.key) - 1
    if (routes[index]) {
      router.push(routes[index])
    }
  }
}

onMounted(() => {
  // Load consciousness data for metrics
  if (isAuthenticated.value) {
    consciousnessStore.loadUserData()
  }
  
  // Set initial theme
  document.documentElement.setAttribute('data-theme', sacredMode.value)
  
  // Add keyboard shortcuts
  document.addEventListener('keydown', handleKeydown)
  
  console.log('🧭 Sacred Navigation activated')
})

onUnmounted(() => {
  document.removeEventListener('keydown', handleKeydown)
})
</script>

<style scoped lang="scss">
.sacred-navigation {
  position: fixed;
  left: 0;
  top: 80px; // Below header
  bottom: 0;
  width: 280px;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-right: 1px solid rgba(255, 255, 255, 0.1);
  z-index: 900;
  transform: translateX(-100%);
  transition: transform 0.3s ease;
  
  &.mobile-open {
    transform: translateX(0);
  }
}

.nav-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 2rem 0;
}

.nav-primary {
  list-style: none;
  padding: 0;
  margin: 0;
  flex: 1;
  
  .nav-item {
    margin-bottom: 0.5rem;
    
    .nav-link {
      display: flex;
      align-items: center;
      gap: 1rem;
      padding: 1rem 2rem;
      color: var(--sacred-light);
      text-decoration: none;
      transition: all 0.3s ease;
      position: relative;
      
      &:hover {
        background: rgba(255, 255, 255, 0.08);
        color: var(--sacred-gold);
        transform: translateX(5px);
      }
      
      &.router-link-active {
        background: rgba(255, 215, 0, 0.1);
        color: var(--sacred-gold);
        border-right: 3px solid var(--sacred-gold);
        
        &::before {
          content: '';
          position: absolute;
          left: 0;
          top: 0;
          bottom: 0;
          width: 4px;
          background: var(--sacred-gold);
        }
      }
      
      .nav-icon {
        font-size: 1.2rem;
        width: 24px;
        text-align: center;
      }
      
      .nav-text {
        font-weight: 500;
        font-size: 0.95rem;
      }
    }
  }
}

.nav-metrics {
  padding: 1.5rem 2rem;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  
  .metric-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0.5rem 0;
    color: var(--sacred-light);
    
    .metric-icon {
      font-size: 1rem;
    }
    
    .metric-value {
      font-weight: 600;
      color: var(--sacred-blue);
    }
  }
}

.nav-actions {
  padding: 1.5rem 2rem;
  display: flex;
  gap: 1rem;
  justify-content: center;
  
  .notification-btn {
    position: relative;
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 12px;
    padding: 0.75rem;
    color: var(--sacred-light);
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(255, 255, 255, 0.15);
      transform: translateY(-2px);
    }
    
    &.active {
      background: rgba(255, 215, 0, 0.2);
      border-color: var(--sacred-gold);
      color: var(--sacred-gold);
    }
    
    .notification-icon {
      font-size: 1.1rem;
    }
    
    .notification-badge {
      position: absolute;
      top: -5px;
      right: -5px;
      background: #ff4757;
      color: white;
      font-size: 0.7rem;
      font-weight: 600;
      padding: 0.2rem 0.4rem;
      border-radius: 10px;
      min-width: 18px;
      text-align: center;
    }
  }
  
  .sacred-mode-toggle {
    background: rgba(255, 255, 255, 0.1);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 12px;
    padding: 0.75rem;
    color: var(--sacred-light);
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(255, 255, 255, 0.15);
      transform: translateY(-2px);
    }
    
    .mode-icon {
      font-size: 1.1rem;
    }
  }
}

.mobile-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: -1;
}

// Sacred theme variations
:global([data-theme="light"]) {
  .sacred-navigation {
    background: rgba(255, 255, 255, 0.95);
    border-right-color: rgba(0, 0, 0, 0.1);
    
    .nav-link {
      color: var(--sacred-dark);
      
      &:hover {
        background: rgba(0, 0, 0, 0.05);
      }
    }
    
    .nav-metrics {
      border-color: rgba(0, 0, 0, 0.1);
    }
    
    .nav-actions button {
      background: rgba(0, 0, 0, 0.05);
      border-color: rgba(0, 0, 0, 0.1);
      color: var(--sacred-dark);
    }
  }
}

:global([data-theme="dark"]) {
  .sacred-navigation {
    background: rgba(0, 0, 0, 0.9);
    border-right-color: rgba(255, 255, 255, 0.2);
  }
}

// Desktop - always visible
@media (min-width: 1024px) {
  .sacred-navigation {
    position: static;
    transform: translateX(0);
    height: auto;
    min-height: calc(100vh - 80px);
  }
  
  .mobile-overlay {
    display: none;
  }
}

// Tablet and mobile - collapsible
@media (max-width: 1023px) {
  .sacred-navigation {
    position: fixed;
    z-index: 1000;
    top: 0;
    height: 100vh;
    width: 320px;
    
    .nav-container {
      padding-top: 6rem; // Account for mobile header
    }
  }
}

// Mobile responsiveness
@media (max-width: 480px) {
  .sacred-navigation {
    width: 280px;
  }
  
  .nav-link {
    padding: 1rem 1.5rem !important;
    
    .nav-text {
      font-size: 0.9rem !important;
    }
  }
  
  .nav-metrics {
    padding: 1rem 1.5rem !important;
  }
  
  .nav-actions {
    padding: 1rem 1.5rem !important;
    
    button {
      padding: 0.6rem !important;
    }
  }
}

// Smooth animations
.nav-link {
  &::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 2rem;
    right: 2rem;
    height: 2px;
    background: var(--sacred-gold);
    transform: scaleX(0);
    transition: transform 0.3s ease;
    transform-origin: left;
  }
  
  &:hover::after {
    transform: scaleX(1);
  }
  
  &.router-link-active::after {
    transform: scaleX(1);
  }
}

// Accessibility improvements
.nav-link:focus {
  outline: 2px solid var(--sacred-gold);
  outline-offset: 2px;
}

.notification-btn:focus,
.sacred-mode-toggle:focus {
  outline: 2px solid var(--sacred-gold);
  outline-offset: 2px;
}

// Subtle glow effects
.nav-item:hover .nav-icon {
  filter: drop-shadow(0 0 8px currentColor);
}

.notification-btn.active .notification-icon {
  filter: drop-shadow(0 0 8px var(--sacred-gold));
}
</style>

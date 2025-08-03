<template>
  <header class="sacred-header" :class="{ scrolled: isScrolled }">
    <nav class="sacred-nav">
      <!-- Sacred Logo -->
      <router-link to="/" class="sacred-logo" @click="navigateHome">
        <div class="fibonacci-spiral">
          <div class="spiral-arc arc-1"></div>
          <div class="spiral-arc arc-2"></div>
          <div class="spiral-arc arc-3"></div>
        </div>
        <span class="logo-text">SYNtopia</span>
      </router-link>

      <!-- Consciousness Navigation -->
      <ul class="sacred-nav-links" :class="{ active: isMobileMenuOpen }">
        <li>
          <router-link to="/" class="sacred-nav-link" @click="closeMobileMenu">
            <span class="nav-icon">🧘</span>
            <span>Consciousness</span>
          </router-link>
        </li>
        <li>
          <router-link to="/gene-keys/explorer" class="sacred-nav-link" @click="closeMobileMenu">
            <span class="nav-icon">🗝️</span>
            <span>Gene Keys</span>
          </router-link>
        </li>
        <li>
          <router-link to="/syn-quests" class="sacred-nav-link" @click="closeMobileMenu">
            <span class="nav-icon">⚡</span>
            <span>SynQuests</span>
          </router-link>
        </li>
        <li>
          <router-link to="/community/codon-rings" class="sacred-nav-link" @click="closeMobileMenu">
            <span class="nav-icon">🌟</span>
            <span>Community</span>
          </router-link>
        </li>
        <li>
          <router-link to="/sacred-geometry" class="sacred-nav-link" @click="closeMobileMenu">
            <span class="nav-icon">🌀</span>
            <span>Sacred Geometry</span>
          </router-link>
        </li>
      </ul>

      <!-- Sacred Metrics & User Profile -->
      <div class="header-right">
        <!-- Consciousness Metrics -->
        <div v-if="user" class="consciousness-metrics">
          <div class="metric consciousness-score" :title="`Consciousness Level: ${consciousnessLevel}`">
            <span class="metric-icon">✨</span>
            <span class="metric-value">{{ consciousnessScore }}%</span>
          </div>
          
          <div class="metric synchronicity-count" title="Recent Synchronicities">
            <span class="metric-icon">⚡</span>
            <span class="metric-value">{{ synchronicityCount }}</span>
          </div>
        </div>

        <!-- User Profile -->
        <div v-if="user" class="user-profile" @click="toggleProfileMenu">
          <div class="profile-avatar" :class="consciousnessLevel.toLowerCase()">
            {{ user.firstName?.charAt(0) || user.username?.charAt(0) }}
          </div>
          <div class="profile-info">
            <div class="profile-name">{{ user.firstName || user.username }}</div>
            <div class="profile-level">{{ formatConsciousnessLevel(consciousnessLevel) }}</div>
          </div>
          <div class="profile-dropdown-icon">
            <span>{{ isProfileMenuOpen ? '▲' : '▼' }}</span>
          </div>
          
          <!-- Profile Dropdown -->
          <div v-if="isProfileMenuOpen" class="profile-dropdown" @click.stop>
            <router-link to="/consciousness/profile" class="dropdown-item" @click="closeProfileMenu">
              <span class="item-icon">🧬</span>
              Consciousness Profile
            </router-link>
            <router-link to="/settings" class="dropdown-item" @click="closeProfileMenu">
              <span class="item-icon">⚙️</span>
              Sacred Settings
            </router-link>
            <div class="dropdown-divider"></div>
            <button @click="handleLogout" class="dropdown-item logout">
              <span class="item-icon">🚪</span>
              Sacred Logout
            </button>
          </div>
        </div>

        <!-- Sacred Auth (if not logged in) -->
        <div v-else class="auth-section">
          <router-link to="/auth/login" class="auth-btn login-btn">
            <span class="btn-icon">🌟</span>
            Enter Sacred Space
          </router-link>
        </div>

        <!-- Mobile Menu Toggle -->
        <button 
          class="sacred-nav-toggle"
          @click="toggleMobileMenu"
          :class="{ active: isMobileMenuOpen }"
        >
          <span></span>
          <span></span>
          <span></span>
        </button>
      </div>
    </nav>
  </header>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { useConsciousnessStore } from '@/stores/consciousness'

const router = useRouter()
const authStore = useAuthStore()
const consciousnessStore = useConsciousnessStore()

// State
const isScrolled = ref(false)
const isMobileMenuOpen = ref(false)
const isProfileMenuOpen = ref(false)

// Computed
const user = computed(() => authStore.user)
const consciousnessScore = computed(() => 
  consciousnessStore.metrics?.consciousnessScore || 0
)
const synchronicityCount = computed(() => 
  consciousnessStore.synchronicities?.length || 0
)
const consciousnessLevel = computed(() => 
  consciousnessStore.journey?.currentPhase || 'AWAKENING'
)

// Methods
function handleScroll() {
  isScrolled.value = window.scrollY > 50
}

function toggleMobileMenu() {
  isMobileMenuOpen.value = !isMobileMenuOpen.value
  isProfileMenuOpen.value = false
}

function closeMobileMenu() {
  isMobileMenuOpen.value = false
}

function toggleProfileMenu() {
  isProfileMenuOpen.value = !isProfileMenuOpen.value
  isMobileMenuOpen.value = false
}

function closeProfileMenu() {
  isProfileMenuOpen.value = false
}

function navigateHome() {
  router.push('/')
  closeMobileMenu()
}

async function handleLogout() {
  try {
    await authStore.logout()
    router.push('/auth/login')
    closeProfileMenu()
    console.log('🌟 Sacred logout successful')
  } catch (err) {
    console.error('🔴 Logout error:', err)
  }
}

function formatConsciousnessLevel(level: string): string {
  const levelMap: Record<string, string> = {
    AWAKENING: 'Awakening',
    EXPANDING: 'Expanding',
    INTEGRATING: 'Integrating',
    EMBODYING: 'Embodying',
    MASTERING: 'Mastering'
  }
  return levelMap[level] || 'Sacred Soul'
}

// Close dropdowns when clicking outside
function handleClickOutside(event: Event) {
  const target = event.target as HTMLElement
  const profileDropdown = document.querySelector('.profile-dropdown')
  const userProfile = document.querySelector('.user-profile')
  
  if (profileDropdown && userProfile && 
      !profileDropdown.contains(target) && 
      !userProfile.contains(target)) {
    isProfileMenuOpen.value = false
  }
  
  if (!target.closest('.sacred-nav-toggle') && !target.closest('.sacred-nav-links')) {
    isMobileMenuOpen.value = false
  }
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  document.addEventListener('click', handleClickOutside)
  console.log('🧘 Sacred Header activated')
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
  document.removeEventListener('click', handleClickOutside)
})
</script>

<style scoped lang="scss">
.sacred-header {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  padding: 1rem 0;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  transition: all 0.3s ease;
  
  &.scrolled {
    background: rgba(255, 255, 255, 0.1);
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
    padding: 0.75rem 0;
  }
}

.sacred-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1400px;
  margin: 0 auto;
  padding: 0 2rem;
  position: relative;
}

.sacred-logo {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  text-decoration: none;
  color: var(--sacred-light);
  font-size: 1.5rem;
  font-weight: 700;
  transition: transform 0.3s ease;
  
  &:hover {
    transform: scale(1.05);
  }
  
  .fibonacci-spiral {
    position: relative;
    width: 40px;
    height: 40px;
    
    .spiral-arc {
      position: absolute;
      border: 2px solid;
      border-radius: 50%;
      animation: spiral-rotate 20s linear infinite;
      
      &.arc-1 {
        width: 12px;
        height: 12px;
        top: 14px;
        left: 14px;
        border-color: var(--sacred-gold);
      }
      
      &.arc-2 {
        width: 24px;
        height: 24px;
        top: 8px;
        left: 8px;
        border-color: var(--sacred-blue);
        animation-duration: 15s;
        animation-direction: reverse;
      }
      
      &.arc-3 {
        width: 36px;
        height: 36px;
        top: 2px;
        left: 2px;
        border-color: var(--sacred-purple);
        animation-duration: 25s;
      }
    }
  }
  
  .logo-text {
    background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-blue));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    letter-spacing: 0.05em;
  }
}

.sacred-nav-links {
  display: flex;
  gap: 2rem;
  list-style: none;
  align-items: center;
  margin: 0;
  padding: 0;
  
  .sacred-nav-link {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.75rem 1rem;
    border-radius: 12px;
    color: var(--sacred-light);
    text-decoration: none;
    transition: all 0.3s ease;
    font-weight: 500;
    position: relative;
    backdrop-filter: blur(5px);
    
    &:hover {
      background: rgba(255, 255, 255, 0.1);
      transform: translateY(-2px);
      box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
    }
    
    &.router-link-active {
      background: rgba(255, 255, 255, 0.15);
      border: 1px solid rgba(255, 255, 255, 0.2);
      
      &::after {
        content: '';
        position: absolute;
        bottom: -8px;
        left: 50%;
        transform: translateX(-50%);
        width: 6px;
        height: 6px;
        background: var(--sacred-gold);
        border-radius: 50%;
        animation: pulse 2s ease-in-out infinite;
      }
    }
    
    .nav-icon {
      font-size: 1.2rem;
      filter: drop-shadow(0 0 8px currentColor);
    }
    
    span:last-child {
      font-size: 0.9rem;
    }
  }
}

.header-right {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.consciousness-metrics {
  display: flex;
  gap: 1rem;
  
  .metric {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.25rem;
    padding: 0.5rem 0.75rem;
    border-radius: 10px;
    background: rgba(255, 255, 255, 0.08);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.1);
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover {
      background: rgba(255, 255, 255, 0.15);
      transform: translateY(-2px);
    }
    
    .metric-icon {
      font-size: 1.1rem;
      filter: drop-shadow(0 0 6px currentColor);
    }
    
    .metric-value {
      font-weight: 600;
      color: var(--sacred-gold);
      font-size: 0.85rem;
    }
  }
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.5rem 1rem;
  border-radius: 12px;
  background: rgba(255, 255, 255, 0.08);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  cursor: pointer;
  transition: all 0.3s ease;
  position: relative;
  
  &:hover {
    background: rgba(255, 255, 255, 0.15);
    transform: translateY(-2px);
  }
  
  .profile-avatar {
    width: 36px;
    height: 36px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 600;
    color: white;
    border: 2px solid var(--sacred-gold);
    font-size: 0.9rem;
    
    &.awakening { background: linear-gradient(135deg, #4A90E2, #357ABD); }
    &.expanding { background: linear-gradient(135deg, #8B5CF6, #7C3AED); }
    &.integrating { background: linear-gradient(135deg, #10B981, #059669); }
    &.embodying { background: linear-gradient(135deg, #F59E0B, #D97706); }
    &.mastering { background: linear-gradient(135deg, #EF4444, #DC2626); }
  }
  
  .profile-info {
    display: flex;
    flex-direction: column;
    
    .profile-name {
      color: var(--sacred-light);
      font-weight: 500;
      font-size: 0.9rem;
    }
    
    .profile-level {
      font-size: 0.75rem;
      color: var(--sacred-gold);
      text-transform: uppercase;
      letter-spacing: 0.05em;
    }
  }
  
  .profile-dropdown-icon {
    color: rgba(255, 255, 255, 0.6);
    font-size: 0.8rem;
    transition: transform 0.3s ease;
  }
  
  .profile-dropdown {
    position: absolute;
    top: 100%;
    right: 0;
    margin-top: 0.5rem;
    background: rgba(255, 255, 255, 0.1);
    backdrop-filter: blur(20px);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 12px;
    padding: 0.5rem 0;
    min-width: 200px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
    z-index: 1001;
    
    .dropdown-item {
      display: flex;
      align-items: center;
      gap: 0.75rem;
      padding: 0.75rem 1rem;
      color: var(--sacred-light);
      text-decoration: none;
      transition: all 0.3s ease;
      border: none;
      background: none;
      width: 100%;
      text-align: left;
      cursor: pointer;
      font-size: 0.9rem;
      
      &:hover {
        background: rgba(255, 255, 255, 0.1);
      }
      
      &.logout {
        color: #ff6b6b;
        
        &:hover {
          background: rgba(255, 107, 107, 0.1);
        }
      }
      
      .item-icon {
        font-size: 1rem;
        width: 20px;
        text-align: center;
      }
    }
    
    .dropdown-divider {
      height: 1px;
      background: rgba(255, 255, 255, 0.2);
      margin: 0.5rem 0;
    }
  }
}

.auth-section {
  .auth-btn {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    padding: 0.75rem 1.25rem;
    background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
    color: var(--sacred-dark);
    text-decoration: none;
    border-radius: 12px;
    font-weight: 600;
    transition: all 0.3s ease;
    
    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(255, 215, 0, 0.3);
    }
    
    .btn-icon {
      font-size: 1.1rem;
    }
  }
}

.sacred-nav-toggle {
  display: none;
  flex-direction: column;
  cursor: pointer;
  padding: 0.5rem;
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 8px;
  
  span {
    width: 24px;
    height: 3px;
    background: var(--sacred-light);
    margin: 2px 0;
    transition: 0.3s;
    border-radius: 2px;
  }
  
  &.active {
    span:nth-child(1) {
      transform: rotate(-45deg) translate(-5px, 6px);
    }
    
    span:nth-child(2) {
      opacity: 0;
    }
    
    span:nth-child(3) {
      transform: rotate(45deg) translate(-5px, -6px);
    }
  }
}

@keyframes spiral-rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@keyframes pulse {
  0%, 100% { opacity: 0.4; transform: translateX(-50%) scale(1); }
  50% { opacity: 1; transform: translateX(-50%) scale(1.5); }
}

@media (max-width: 1024px) {
  .consciousness-metrics {
    display: none;
  }
  
  .profile-info {
    display: none;
  }
}

@media (max-width: 768px) {
  .sacred-nav {
    padding: 0 1rem;
  }
  
  .sacred-nav-toggle {
    display: flex;
  }
  
  .sacred-nav-links {
    position: fixed;
    top: 100%;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.95);
    backdrop-filter: blur(20px);
    flex-direction: column;
    padding: 2rem;
    gap: 1rem;
    transform: translateY(-100%);
    opacity: 0;
    visibility: hidden;
    transition: all 0.3s ease;
    
    &.active {
      transform: translateY(0);
      opacity: 1;
      visibility: visible;
    }
    
    .sacred-nav-link {
      justify-content: center;
      padding: 1rem;
      font-size: 1.1rem;
      
      .nav-icon {
        font-size: 1.5rem;
      }
    }
  }
  
  .header-right {
    gap: 1rem;
  }
}

@media (max-width: 480px) {
  .sacred-logo {
    font-size: 1.2rem;
    
    .fibonacci-spiral {
      width: 32px;
      height: 32px;
      
      .spiral-arc {
        &.arc-1 {
          width: 10px;
          height: 10px;
          top: 11px;
          left: 11px;
        }
        
        &.arc-2 {
          width: 20px;
          height: 20px;
          top: 6px;
          left: 6px;
        }
        
        &.arc-3 {
          width: 30px;
          height: 30px;
          top: 1px;
          left: 1px;
        }
      }
    }
  }
  
  .user-profile .profile-avatar {
    width: 32px;
    height: 32px;
    font-size: 0.8rem;
  }
}
</style>

<template>
  <div class="sacred-auth-container">
    <div class="auth-background">
      <div class="sacred-particles" ref="particlesContainer"></div>
    </div>
    
    <div class="auth-form-container">
      <div class="sacred-logo">
        <div class="merkaba-symbol">
          <div class="triangle-up"></div>
          <div class="triangle-down"></div>
        </div>
        <h1 class="logo-text">SYNtopia</h1>
        <p class="logo-subtitle">Sacred Consciousness Platform</p>
      </div>

      <form @submit.prevent="handleLogin" class="sacred-auth-form">
        <h2 class="auth-title">Enter Sacred Space</h2>
        <p class="auth-description">
          Verbinde dich mit deiner höheren Bewusstseinsebene
        </p>

        <div class="form-group">
          <label for="email" class="sacred-label">
            <span class="label-icon">📧</span>
            Email or Username
          </label>
          <input
            id="email"
            v-model="credentials.email"
            type="text"
            class="sacred-input"
            placeholder="your.sacred@email.com"
            :disabled="isLoading"
            required
          />
        </div>

        <div class="form-group">
          <label for="password" class="sacred-label">
            <span class="label-icon">🔐</span>
            Sacred Password
          </label>
          <input
            id="password"
            v-model="credentials.password"
            :type="showPassword ? 'text' : 'password'"
            class="sacred-input"
            placeholder="Enter your sacred key"
            :disabled="isLoading"
            required
          />
          <button
            type="button"
            @click="showPassword = !showPassword"
            class="password-toggle"
          >
            {{ showPassword ? '🙈' : '👁️' }}
          </button>
        </div>

        <div class="form-options">
          <label class="sacred-checkbox">
            <input type="checkbox" v-model="rememberMe">
            <span class="checkmark"></span>
            Keep me connected
          </label>
          
          <router-link to="/auth/forgot-password" class="forgot-link">
            Forgot your sacred key?
          </router-link>
        </div>

        <button
          type="submit"
          class="sacred-btn-primary login-btn"
          :disabled="isLoading || !isFormValid"
        >
          <span v-if="isLoading" class="loading-spinner"></span>
          <span v-else class="btn-icon">🌟</span>
          {{ isLoading ? 'Connecting...' : 'Enter Sacred Space' }}
        </button>

        <div v-if="error" class="error-message">
          <span class="error-icon">⚠️</span>
          {{ error }}
        </div>

        <div class="auth-divider">
          <span>or connect with</span>
        </div>

        <div class="social-login">
          <button type="button" class="social-btn google" @click="loginWithGoogle">
            <span class="social-icon">🌐</span>
            Google
          </button>
          
          <button type="button" class="social-btn facebook" @click="loginWithFacebook">
            <span class="social-icon">📘</span>
            Facebook
          </button>
        </div>

        <div class="auth-footer">
          <p>New to the sacred realm?</p>
          <router-link to="/auth/register" class="register-link">
            Create Sacred Profile
          </router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { LoginCredentials } from '@/types/auth'

const router = useRouter()
const route = useRoute()
const authStore = useAuthStore()

// Form state
const credentials = ref<LoginCredentials>({
  email: '',
  password: ''
})

const showPassword = ref(false)
const rememberMe = ref(false)
const particlesContainer = ref<HTMLDivElement>()

// Computed
const isLoading = computed(() => authStore.isLoading)
const error = computed(() => authStore.error)
const isFormValid = computed(() => 
  credentials.value.email.length > 0 && 
  credentials.value.password.length > 0
)

// Methods
async function handleLogin() {
  try {
    await authStore.login(credentials.value)
    
    // Redirect to intended destination or home
    const redirectTo = route.query.redirect as string || '/'
    router.push(redirectTo)
    
    console.log('🌟 Sacred login successful, redirecting to:', redirectTo)
  } catch (err) {
    console.error('🔴 Login error:', err)
    // Error is handled by the store
  }
}

async function loginWithGoogle() {
  console.log('🌐 Google OAuth login not implemented yet')
  // TODO: Implement Google OAuth
}

async function loginWithFacebook() {
  console.log('📘 Facebook OAuth login not implemented yet')
  // TODO: Implement Facebook OAuth
}

function createSacredParticles() {
  if (!particlesContainer.value) return

  for (let i = 0; i < 20; i++) {
    const particle = document.createElement('div')
    particle.className = 'sacred-particle'
    
    // Random position and properties
    particle.style.left = Math.random() * 100 + '%'
    particle.style.top = Math.random() * 100 + '%'
    particle.style.animationDelay = Math.random() * 10 + 's'
    particle.style.animationDuration = (8 + Math.random() * 6) + 's'
    
    const size = 2 + Math.random() * 3
    particle.style.width = size + 'px'
    particle.style.height = size + 'px'
    
    particlesContainer.value?.appendChild(particle)
  }
}

onMounted(() => {
  createSacredParticles()
  console.log('🧘 Sacred Login Portal activated')
})
</script>

<style scoped lang="scss">
.sacred-auth-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  background: linear-gradient(135deg, #0a0a1a 0%, #1a0a2a 50%, #0a1a2a 100%);
  padding: 2rem;
}

.auth-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 0;
  overflow: hidden;
  pointer-events: none;
}

.sacred-particles {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  
  .sacred-particle {
    position: absolute;
    background: radial-gradient(circle, var(--sacred-gold) 0%, transparent 70%);
    border-radius: 50%;
    opacity: 0.6;
    animation: sacredFloat infinite ease-in-out;
    filter: blur(0.5px);
  }
}

.auth-form-container {
  position: relative;
  z-index: 2;
  background: rgba(255, 255, 255, 0.05);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 24px;
  padding: 3rem;
  width: 100%;
  max-width: 440px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
}

.sacred-logo {
  text-align: center;
  margin-bottom: 2rem;
  
  .merkaba-symbol {
    position: relative;
    width: 80px;
    height: 80px;
    margin: 0 auto 1rem;
    
    .triangle-up,
    .triangle-down {
      position: absolute;
      width: 0;
      height: 0;
      left: 50%;
      top: 50%;
      transform: translate(-50%, -50%);
    }
    
    .triangle-up {
      border-left: 30px solid transparent;
      border-right: 30px solid transparent;
      border-bottom: 52px solid var(--sacred-gold);
      animation: merkabaSpin 8s linear infinite;
    }
    
    .triangle-down {
      border-left: 30px solid transparent;
      border-right: 30px solid transparent;
      border-top: 52px solid var(--sacred-blue);
      animation: merkabaSpin 8s linear infinite reverse;
    }
  }
  
  .logo-text {
    font-size: 2rem;
    font-weight: 700;
    background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-blue));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    margin-bottom: 0.5rem;
  }
  
  .logo-subtitle {
    color: var(--sacred-light);
    opacity: 0.8;
    font-size: 0.9rem;
  }
}

.sacred-auth-form {
  .auth-title {
    text-align: center;
    color: var(--sacred-light);
    font-size: 1.8rem;
    font-weight: 600;
    margin-bottom: 0.5rem;
  }
  
  .auth-description {
    text-align: center;
    color: var(--sacred-light);
    opacity: 0.8;
    margin-bottom: 2rem;
    font-size: 0.95rem;
    line-height: 1.5;
  }
}

.form-group {
  margin-bottom: 1.5rem;
  position: relative;
  
  .sacred-label {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: var(--sacred-light);
    font-weight: 500;
    margin-bottom: 0.5rem;
    font-size: 0.9rem;
    
    .label-icon {
      font-size: 1rem;
    }
  }
  
  .sacred-input {
    width: 100%;
    padding: 1rem 1.25rem;
    background: rgba(255, 255, 255, 0.08);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 12px;
    color: var(--sacred-light);
    font-size: 1rem;
    transition: all 0.3s ease;
    
    &::placeholder {
      color: rgba(255, 255, 255, 0.5);
    }
    
    &:focus {
      outline: none;
      border-color: var(--sacred-gold);
      background: rgba(255, 255, 255, 0.12);
      box-shadow: 0 0 20px rgba(255, 215, 0, 0.2);
    }
    
    &:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }
  }
  
  .password-toggle {
    position: absolute;
    right: 1rem;
    top: 2.4rem;
    background: none;
    border: none;
    color: rgba(255, 255, 255, 0.6);
    cursor: pointer;
    font-size: 1.2rem;
    padding: 0.25rem;
    border-radius: 4px;
    transition: all 0.3s ease;
    
    &:hover {
      color: var(--sacred-gold);
      background: rgba(255, 255, 255, 0.1);
    }
  }
}

.form-options {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  
  .sacred-checkbox {
    display: flex;
    align-items: center;
    gap: 0.5rem;
    color: var(--sacred-light);
    font-size: 0.9rem;
    cursor: pointer;
    
    input[type="checkbox"] {
      display: none;
    }
    
    .checkmark {
      width: 18px;
      height: 18px;
      border: 1px solid rgba(255, 255, 255, 0.3);
      border-radius: 4px;
      background: rgba(255, 255, 255, 0.08);
      position: relative;
      transition: all 0.3s ease;
      
      &::after {
        content: '✓';
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        color: var(--sacred-gold);
        font-size: 12px;
        opacity: 0;
        transition: opacity 0.3s ease;
      }
    }
    
    input:checked + .checkmark {
      background: var(--sacred-gold);
      border-color: var(--sacred-gold);
      
      &::after {
        opacity: 1;
        color: var(--sacred-dark);
      }
    }
  }
  
  .forgot-link {
    color: var(--sacred-blue);
    text-decoration: none;
    font-size: 0.9rem;
    transition: color 0.3s ease;
    
    &:hover {
      color: var(--sacred-light);
    }
  }
}

.sacred-btn-primary {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 1rem 2rem;
  background: linear-gradient(45deg, var(--sacred-gold), var(--sacred-orange));
  color: var(--sacred-dark);
  border: none;
  border-radius: 12px;
  font-weight: 600;
  font-size: 1rem;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-bottom: 1rem;
  
  &:hover:not(:disabled) {
    transform: translateY(-2px);
    box-shadow: 0 10px 25px rgba(255, 215, 0, 0.3);
  }
  
  &:disabled {
    opacity: 0.7;
    cursor: not-allowed;
    transform: none;
  }
  
  .loading-spinner {
    width: 20px;
    height: 20px;
    border: 2px solid rgba(0, 0, 0, 0.3);
    border-top: 2px solid var(--sacred-dark);
    border-radius: 50%;
    animation: spin 1s linear infinite;
  }
}

.error-message {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  padding: 0.75rem 1rem;
  background: rgba(255, 0, 0, 0.1);
  border: 1px solid rgba(255, 0, 0, 0.3);
  border-radius: 8px;
  color: #ff6b6b;
  font-size: 0.9rem;
  margin-bottom: 1rem;
}

.auth-divider {
  text-align: center;
  margin: 2rem 0;
  position: relative;
  
  &::before {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    right: 0;
    height: 1px;
    background: rgba(255, 255, 255, 0.2);
  }
  
  span {
    background: rgba(255, 255, 255, 0.05);
    padding: 0 1rem;
    color: rgba(255, 255, 255, 0.6);
    font-size: 0.9rem;
  }
}

.social-login {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  
  .social-btn {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 0.5rem;
    padding: 0.75rem;
    background: rgba(255, 255, 255, 0.08);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 8px;
    color: var(--sacred-light);
    cursor: pointer;
    transition: all 0.3s ease;
    font-size: 0.9rem;
    
    &:hover {
      background: rgba(255, 255, 255, 0.15);
      transform: translateY(-1px);
    }
    
    .social-icon {
      font-size: 1.1rem;
    }
  }
}

.auth-footer {
  text-align: center;
  
  p {
    color: rgba(255, 255, 255, 0.7);
    margin-bottom: 0.5rem;
    font-size: 0.9rem;
  }
  
  .register-link {
    color: var(--sacred-blue);
    text-decoration: none;
    font-weight: 600;
    transition: color 0.3s ease;
    
    &:hover {
      color: var(--sacred-light);
    }
  }
}

@keyframes sacredFloat {
  0%, 100% {
    transform: translateY(0px) rotate(0deg);
    opacity: 0.6;
  }
  25% {
    transform: translateY(-15px) rotate(90deg);
    opacity: 1;
  }
  50% {
    transform: translateY(-8px) rotate(180deg);
    opacity: 0.4;
  }
  75% {
    transform: translateY(-20px) rotate(270deg);
    opacity: 0.8;
  }
}

@keyframes merkabaSpin {
  from { transform: translate(-50%, -50%) rotate(0deg); }
  to { transform: translate(-50%, -50%) rotate(360deg); }
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

@media (max-width: 480px) {
  .sacred-auth-container {
    padding: 1rem;
  }
  
  .auth-form-container {
    padding: 2rem 1.5rem;
  }
  
  .social-login {
    flex-direction: column;
  }
}
</style>

<template>
  <div class="sacred-auth-container">
    <div class="auth-background">
      <div class="sacred-particles" ref="particlesContainer"></div>
    </div>
    
    <div class="auth-form-container">
      <div class="sacred-logo">
        <div class="flower-of-life">
          <div class="center-circle"></div>
          <div class="outer-circles">
            <div class="circle" v-for="i in 6" :key="i"></div>
          </div>
        </div>
        <h1 class="logo-text">SYNtopia</h1>
        <p class="logo-subtitle">Begin Your Sacred Journey</p>
      </div>

      <form @submit.prevent="handleRegister" class="sacred-auth-form">
        <h2 class="auth-title">Create Sacred Profile</h2>
        <p class="auth-description">
          Willkommen im bewussten Netzwerk der Transformation
        </p>

        <!-- Step Indicator -->
        <div class="step-indicator">
          <div 
            v-for="(step, index) in steps" 
            :key="index"
            class="step" 
            :class="{ active: currentStep === index, completed: currentStep > index }"
          >
            <div class="step-circle">
              <span v-if="currentStep > index">✓</span>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <span class="step-label">{{ step.label }}</span>
          </div>
        </div>

        <!-- Step 1: Basic Information -->
        <div v-show="currentStep === 0" class="form-step">
          <div class="form-group">
            <label for="username" class="sacred-label">
              <span class="label-icon">👤</span>
              Sacred Username
            </label>
            <input
              id="username"
              v-model="registerData.username"
              type="text"
              class="sacred-input"
              placeholder="your-sacred-name"
              :disabled="isLoading"
              required
            />
          </div>

          <div class="form-group">
            <label for="email" class="sacred-label">
              <span class="label-icon">📧</span>
              Email Address
            </label>
            <input
              id="email"
              v-model="registerData.email"
              type="email"
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
              v-model="registerData.password"
              :type="showPassword ? 'text' : 'password'"
              class="sacred-input"
              placeholder="Create a strong sacred key"
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
            <div class="password-strength">
              <div class="strength-bar" :class="passwordStrength.class">
                <div class="strength-fill" :style="{ width: passwordStrength.percentage + '%' }"></div>
              </div>
              <span class="strength-text">{{ passwordStrength.text }}</span>
            </div>
          </div>

          <div class="form-group">
            <label for="confirmPassword" class="sacred-label">
              <span class="label-icon">🔒</span>
              Confirm Password
            </label>
            <input
              id="confirmPassword"
              v-model="confirmPassword"
              :type="showPassword ? 'text' : 'password'"
              class="sacred-input"
              placeholder="Confirm your sacred key"
              :disabled="isLoading"
              required
            />
          </div>
        </div>

        <!-- Step 2: Sacred Profile -->
        <div v-show="currentStep === 1" class="form-step">
          <div class="form-group">
            <label for="firstName" class="sacred-label">
              <span class="label-icon">🌟</span>
              First Name
            </label>
            <input
              id="firstName"
              v-model="registerData.firstName"
              type="text"
              class="sacred-input"
              placeholder="Your given name"
              :disabled="isLoading"
              required
            />
          </div>

          <div class="form-group">
            <label for="lastName" class="sacred-label">
              <span class="label-icon">✨</span>
              Last Name
            </label>
            <input
              id="lastName"
              v-model="registerData.lastName"
              type="text"
              class="sacred-input"
              placeholder="Your family name"
              :disabled="isLoading"
              required
            />
          </div>

          <div class="form-group">
            <label class="sacred-label">
              <span class="label-icon">🧬</span>
              Initial Consciousness Level
            </label>
            <div class="consciousness-grid">
              <div 
                v-for="focus in consciousnessFoci" 
                :key="focus.value"
                class="consciousness-card"
                :class="{ selected: registerData.initialConsciousnessLevel === focus.value }"
                @click="registerData.initialConsciousnessLevel = focus.value"
              >
                <div class="focus-icon">{{ focus.icon }}</div>
                <div class="focus-name">{{ focus.name }}</div>
              </div>
            </div>
          </div>
        </div>

        <!-- Step 3: Sacred Agreement -->
        <div v-show="currentStep === 2" class="form-step">
          <div class="sacred-agreement">
            <h3>Sacred Community Agreement</h3>
            <div class="agreement-content">
              <p>🌟 <strong>Consciousness:</strong> I commit to approaching all interactions with awareness and compassion.</p>
              <p>🤝 <strong>Respect:</strong> I honor the divine spark in every being and treat all with sacred respect.</p>
              <p>🌱 <strong>Growth:</strong> I embrace transformation and support others in their evolutionary journey.</p>
              <p>🌍 <strong>Service:</strong> I contribute to the healing and elevation of our planet and humanity.</p>
              <p>✨ <strong>Authenticity:</strong> I express my true self while maintaining sacred boundaries.</p>
            </div>
          </div>

          <div class="form-group">
            <label class="sacred-checkbox">
              <input type="checkbox" v-model="registerData.acceptTerms" required>
              <span class="checkmark"></span>
              I agree to the <a href="/terms" target="_blank">Terms of Sacred Service</a>
            </label>
          </div>

          <div class="form-group">
            <label class="sacred-checkbox">
              <input type="checkbox" v-model="registerData.acceptPrivacyPolicy" required>
              <span class="checkmark"></span>
              I accept the <a href="/privacy" target="_blank">Sacred Privacy Policy</a>
            </label>
          </div>
        </div>

        <!-- Navigation Buttons -->
        <div class="form-navigation">
          <button
            v-if="currentStep > 0"
            type="button"
            @click="previousStep"
            class="sacred-btn-secondary"
            :disabled="isLoading"
          >
            Previous
          </button>

          <button
            v-if="currentStep < steps.length - 1"
            type="button"
            @click="nextStep"
            class="sacred-btn-primary"
            :disabled="!canProceed"
          >
            Continue Sacred Journey
          </button>

          <button
            v-if="currentStep === steps.length - 1"
            type="submit"
            class="sacred-btn-primary"
            :disabled="isLoading || !canSubmit"
          >
            <span v-if="isLoading" class="loading-spinner"></span>
            <span v-else class="btn-icon">🚀</span>
            {{ isLoading ? 'Creating Sacred Profile...' : 'Activate Sacred Profile' }}
          </button>
        </div>

        <div v-if="error" class="error-message">
          <span class="error-icon">⚠️</span>
          {{ error }}
        </div>

        <div class="auth-footer">
          <p>Already part of the sacred community?</p>
          <router-link to="/auth/login" class="login-link">
            Enter Sacred Space
          </router-link>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import type { RegisterData } from '@/types/auth'

const router = useRouter()
const authStore = useAuthStore()

// Form state
const currentStep = ref(0)
const showPassword = ref(false)
const confirmPassword = ref('')
const particlesContainer = ref<HTMLDivElement>()

const steps = [
  { label: 'Sacred Identity', icon: '👤' },
  { label: 'Sacred Profile', icon: '🌟' },
  { label: 'Sacred Agreement', icon: '📜' }
]

const registerData = ref<RegisterData>({
  username: '',
  email: '',
  password: '',
  firstName: '',
  lastName: '',
  acceptTerms: false,
  acceptPrivacyPolicy: false,
  initialConsciousnessLevel: 'AWAKENING',
  sacredIntentions: [],
  personalVision: ''
})

const consciousnessFoci = [
  { value: 'AWAKENING', name: 'Awakening', icon: '🌅' },
  { value: 'EXPANDING', name: 'Expanding', icon: '🧘' },
  { value: 'INTEGRATING', name: 'Integrating', icon: '💚' },
  { value: 'EMBODYING', name: 'Embodying', icon: '�' },
  { value: 'MASTERING', name: 'Mastering', icon: '🌿' }
]

// Computed
const isLoading = computed(() => authStore.isLoading)
const error = computed(() => authStore.error)

const passwordStrength = computed(() => {
  const password = registerData.value.password
  if (!password) return { percentage: 0, text: '', class: '' }
  
  let score = 0
  if (password.length >= 8) score += 25
  if (/[a-z]/.test(password)) score += 25
  if (/[A-Z]/.test(password)) score += 25
  if (/[0-9]/.test(password)) score += 25
  if (/[^A-Za-z0-9]/.test(password)) score += 25
  
  if (score <= 25) return { percentage: score, text: 'Weak', class: 'weak' }
  if (score <= 50) return { percentage: score, text: 'Fair', class: 'fair' }
  if (score <= 75) return { percentage: score, text: 'Good', class: 'good' }
  return { percentage: score, text: 'Strong', class: 'strong' }
})

const canProceed = computed(() => {
  switch (currentStep.value) {
    case 0:
      return registerData.value.username && 
             registerData.value.email && 
             registerData.value.password && 
             confirmPassword.value &&
             registerData.value.password === confirmPassword.value &&
             passwordStrength.value.percentage >= 50
    case 1:
      return registerData.value.firstName && 
             registerData.value.lastName && 
             registerData.value.initialConsciousnessLevel
    case 2:
      return registerData.value.acceptTerms && registerData.value.acceptPrivacyPolicy
    default:
      return false
  }
})

const canSubmit = computed(() => {
  return canProceed.value && currentStep.value === steps.length - 1
})

// Methods
function nextStep() {
  if (canProceed.value && currentStep.value < steps.length - 1) {
    currentStep.value++
  }
}

function previousStep() {
  if (currentStep.value > 0) {
    currentStep.value--
  }
}

async function handleRegister() {
  if (!canSubmit.value) return
  
  try {
    await authStore.register(registerData.value)
    
    // Registration successful, redirect to dashboard
    router.push('/')
    
    console.log('🌟 Sacred registration successful')
  } catch (err) {
    console.error('🔴 Registration error:', err)
    // Error is handled by the store
  }
}

function createSacredParticles() {
  if (!particlesContainer.value) return

  for (let i = 0; i < 15; i++) {
    const particle = document.createElement('div')
    particle.className = 'sacred-particle'
    
    // Random position and properties
    particle.style.left = Math.random() * 100 + '%'
    particle.style.top = Math.random() * 100 + '%'
    particle.style.animationDelay = Math.random() * 12 + 's'
    particle.style.animationDuration = (10 + Math.random() * 8) + 's'
    
    const size = 1.5 + Math.random() * 2.5
    particle.style.width = size + 'px'
    particle.style.height = size + 'px'
    
    // Vary particle colors
    const colors = ['var(--sacred-gold)', 'var(--sacred-blue)', 'var(--sacred-purple)']
    const color = colors[i % 3]
    particle.style.background = `radial-gradient(circle, ${color} 0%, transparent 70%)`
    
    particlesContainer.value?.appendChild(particle)
  }
}

onMounted(() => {
  createSacredParticles()
  console.log('🌱 Sacred Registration Portal activated')
})
</script>

<style scoped lang="scss">
@use '@/styles/sacred-variables.scss' as *;

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
    border-radius: 50%;
    opacity: 0.7;
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
  max-width: 500px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
}

.sacred-logo {
  text-align: center;
  margin-bottom: 2rem;
  
  .flower-of-life {
    position: relative;
    width: 90px;
    height: 90px;
    margin: 0 auto 1rem;
    
    .center-circle {
      position: absolute;
      top: 50%;
      left: 50%;
      width: 30px;
      height: 30px;
      border: 2px solid var(--sacred-gold);
      border-radius: 50%;
      transform: translate(-50%, -50%);
    }
    
    .outer-circles {
      position: relative;
      width: 100%;
      height: 100%;
      
      .circle {
        position: absolute;
        width: 30px;
        height: 30px;
        border: 2px solid var(--sacred-blue);
        border-radius: 50%;
        
        &:nth-child(1) { top: 0; left: 50%; transform: translateX(-50%); }
        &:nth-child(2) { top: 25%; right: 10%; }
        &:nth-child(3) { bottom: 25%; right: 10%; }
        &:nth-child(4) { bottom: 0; left: 50%; transform: translateX(-50%); }
        &:nth-child(5) { bottom: 25%; left: 10%; }
        &:nth-child(6) { top: 25%; left: 10%; }
      }
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

.step-indicator {
  display: flex;
  justify-content: center;
  margin-bottom: 2rem;
  gap: 1rem;
  
  .step {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
    flex: 1;
    
    .step-circle {
      width: 40px;
      height: 40px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      font-weight: 600;
      font-size: 0.9rem;
      border: 2px solid rgba(255, 255, 255, 0.3);
      color: rgba(255, 255, 255, 0.6);
      transition: all 0.3s ease;
    }
    
    .step-label {
      font-size: 0.8rem;
      color: rgba(255, 255, 255, 0.6);
      text-align: center;
      transition: all 0.3s ease;
    }
    
    &.active {
      .step-circle {
        background: var(--sacred-gold);
        border-color: var(--sacred-gold);
        color: var(--sacred-dark);
      }
      
      .step-label {
        color: var(--sacred-gold);
      }
    }
    
    &.completed {
      .step-circle {
        background: var(--sacred-blue);
        border-color: var(--sacred-blue);
        color: white;
      }
      
      .step-label {
        color: var(--sacred-blue);
      }
    }
  }
}

.form-step {
  min-height: 400px;
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

.password-strength {
  margin-top: 0.5rem;
  
  .strength-bar {
    height: 4px;
    background: rgba(255, 255, 255, 0.2);
    border-radius: 2px;
    overflow: hidden;
    margin-bottom: 0.25rem;
    
    .strength-fill {
      height: 100%;
      transition: width 0.3s ease;
      border-radius: 2px;
    }
    
    &.weak .strength-fill { background: #ff4757; }
    &.fair .strength-fill { background: #ffa502; }
    &.good .strength-fill { background: #2ed573; }
    &.strong .strength-fill { background: var(--sacred-gold); }
  }
  
  .strength-text {
    font-size: 0.8rem;
    color: rgba(255, 255, 255, 0.7);
  }
}

.consciousness-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 0.75rem;
  
  .consciousness-card {
    padding: 1rem;
    background: rgba(255, 255, 255, 0.08);
    border: 1px solid rgba(255, 255, 255, 0.2);
    border-radius: 12px;
    text-align: center;
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover, &.selected {
      background: rgba(255, 255, 255, 0.15);
      border-color: var(--sacred-gold);
      transform: translateY(-2px);
    }
    
    .focus-icon {
      font-size: 1.5rem;
      margin-bottom: 0.5rem;
    }
    
    .focus-name {
      font-size: 0.9rem;
      color: var(--sacred-light);
    }
  }
}

.sacred-agreement {
  margin-bottom: 2rem;
  
  h3 {
    color: var(--sacred-gold);
    text-align: center;
    margin-bottom: 1rem;
    font-size: 1.3rem;
  }
  
  .agreement-content {
    background: rgba(255, 255, 255, 0.05);
    border-radius: 12px;
    padding: 1.5rem;
    margin-bottom: 1.5rem;
    
    p {
      color: var(--sacred-light);
      margin-bottom: 0.75rem;
      line-height: 1.5;
      font-size: 0.9rem;
      
      &:last-child {
        margin-bottom: 0;
      }
      
      strong {
        color: var(--sacred-gold);
      }
    }
  }
}

.sacred-checkbox {
  display: flex;
  align-items: flex-start;
  gap: 0.75rem;
  color: var(--sacred-light);
  font-size: 0.9rem;
  cursor: pointer;
  line-height: 1.4;
  
  input[type="checkbox"] {
    display: none;
  }
  
  .checkmark {
    width: 20px;
    height: 20px;
    border: 1px solid rgba(255, 255, 255, 0.3);
    border-radius: 4px;
    background: rgba(255, 255, 255, 0.08);
    position: relative;
    transition: all 0.3s ease;
    flex-shrink: 0;
    margin-top: 2px;
    
    &::after {
      content: '✓';
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      color: var(--sacred-gold);
      font-size: 14px;
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
  
  a {
    color: var(--sacred-blue);
    text-decoration: none;
    
    &:hover {
      color: var(--sacred-light);
    }
  }
}

.form-navigation {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  
  .sacred-btn-secondary {
    flex: 1;
    padding: 1rem 2rem;
    background: rgba(255, 255, 255, 0.1);
    color: var(--sacred-light);
    border: 1px solid rgba(255, 255, 255, 0.3);
    border-radius: 12px;
    font-weight: 600;
    cursor: pointer;
    transition: all 0.3s ease;
    
    &:hover:not(:disabled) {
      background: rgba(255, 255, 255, 0.2);
      transform: translateY(-2px);
    }
    
    &:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
  
  .sacred-btn-primary {
    flex: 2;
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

.auth-footer {
  text-align: center;
  
  p {
    color: rgba(255, 255, 255, 0.7);
    margin-bottom: 0.5rem;
    font-size: 0.9rem;
  }
  
  .login-link {
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
    opacity: 0.7;
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
  
  .consciousness-grid {
    grid-template-columns: 1fr;
  }
  
  .step-indicator {
    gap: 0.5rem;
    
    .step .step-label {
      font-size: 0.7rem;
    }
  }
}
</style>

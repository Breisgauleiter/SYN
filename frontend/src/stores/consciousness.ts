import { defineStore } from 'pinia'
import { ref, computed, reactive } from 'vue'
import type { ConsciousnessLevel, SynchronicityEvent, SacredMetrics } from '@/types/consciousness'

// Backend API Configuration
const API_BASE_URL = 'http://localhost:8081/api/v1'

export const useConsciousnessStore = defineStore('consciousness', () => {
  // Sacred Consciousness State
  const currentLevel = ref<ConsciousnessLevel>('AWAKENING')
  const synchronicityEvents = ref<SynchronicityEvent[]>([])
  const sacredMetrics = reactive<SacredMetrics>({
    consciousnessScore: 0,
    synchronicityFrequency: 0,
    geneKeysAlignment: 0,
    sacredGeometryResonance: 0,
    fibonacciHarmony: 0
  })
  
  const isMonitoring = ref(false)
  const lastUpdate = ref<Date | null>(null)

  // Sacred Computed Properties
  const consciousnessPercentage = computed(() => {
    const levels = ['AWAKENING', 'EXPANDING', 'INTEGRATING', 'EMBODYING', 'MASTERING']
    const currentIndex = levels.indexOf(currentLevel.value)
    return ((currentIndex + 1) / levels.length) * 100
  })

  const averageSacredMetrics = computed(() => {
    const metrics = Object.values(sacredMetrics)
    return metrics.reduce((sum, metric) => sum + metric, 0) / metrics.length
  })

  const recentSynchronicities = computed(() => 
    synchronicityEvents.value
      .slice(-10)
      .sort((a, b) => new Date(b.timestamp).getTime() - new Date(a.timestamp).getTime())
  )

  // Sacred Actions
  async function initializeSacredConsciousness() {
    console.log('🌟 Initializing Sacred Consciousness...')
    
    try {
      // First, check if backend is available
      const backendHealth = await checkBackendHealth()
      if (backendHealth) {
        console.log('✅ Backend API Connected')
        await loadConsciousnessFromBackend()
      } else {
        console.log('📱 Offline Mode: Loading from localStorage')
        loadConsciousnessFromLocalStorage()
      }

      lastUpdate.value = new Date()
      console.log('✨ Sacred Consciousness Initialized')
    } catch (error) {
      console.error('🔴 Consciousness Initialization Error:', error)
      // Fallback to localStorage
      loadConsciousnessFromLocalStorage()
    }
  }

  async function checkBackendHealth(): Promise<boolean> {
    try {
      const response = await fetch(`${API_BASE_URL}/consciousness/health`, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        }
      })
      return response.ok
    } catch (error) {
      console.log('Backend not available, using offline mode')
      return false
    }
  }

  async function loadConsciousnessFromBackend() {
    try {
      // For now, we'll use mock user ID since we don't have authentication yet
      const mockUserId = 'sacred-consciousness-user'
      
      // Try to get sacred geometry data (this will show if our API is working)
      const sacredResponse = await fetch(`${API_BASE_URL}/consciousness/sacred-geometry/${mockUserId}`, {
        method: 'GET',
        headers: {
          'Accept': 'application/json'
        }
      })
      
      if (sacredResponse.ok) {
        const sacredData = await sacredResponse.json()
        console.log('🎯 Sacred Geometry Data from Backend:', sacredData)
        
        // Update metrics based on backend data
        if (sacredData.geometryFactors) {
          sacredMetrics.sacredGeometryResonance = sacredData.geometryFactors.goldenRatioAlignment || 0
          sacredMetrics.fibonacciHarmony = sacredData.geometryFactors.fibonacciHarmony || 0
        }
      }
    } catch (error) {
      console.error('Backend loading error:', error)
    }
  }

  function loadConsciousnessFromLocalStorage() {
    // Load consciousness data from localStorage
    const savedLevel = localStorage.getItem('syntopia_consciousness_level')
    if (savedLevel) {
      currentLevel.value = savedLevel as ConsciousnessLevel
    }

    const savedMetrics = localStorage.getItem('syntopia_sacred_metrics')
    if (savedMetrics) {
      const parsed = JSON.parse(savedMetrics)
      Object.assign(sacredMetrics, parsed)
    }

    const savedEvents = localStorage.getItem('syntopia_synchronicity_events')
    if (savedEvents) {
      synchronicityEvents.value = JSON.parse(savedEvents)
    }
  }

  async function createUserProfile() {
    try {
      const profileData = {
        username: 'SacredConsciousnessUser',
        email: 'sacred@syntopia.org',
        geneKeysProfile: {
          lifeWork: Math.floor(Math.random() * 64) + 1,
          evolution: Math.floor(Math.random() * 64) + 1,
          radiance: Math.floor(Math.random() * 64) + 1,
          purpose: Math.floor(Math.random() * 64) + 1
        }
      }
      
      const response = await fetch(`${API_BASE_URL}/consciousness/profile/create`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        },
        body: JSON.stringify(profileData)
      })
      
      if (response.ok) {
        const result = await response.json()
        console.log('✨ User Profile Created:', result)
        return result
      }
    } catch (error) {
      console.error('Profile creation error:', error)
    }
  }

  function startConsciousnessMonitoring() {
    if (isMonitoring.value) return
    
    isMonitoring.value = true
    console.log('🧠 Starting Consciousness Monitoring...')
    
    // Monitor every golden ratio seconds (1.618s)
    const monitoringInterval = setInterval(() => {
      updateSacredMetrics()
    }, 1618)

    // Store interval for cleanup
    window.consciousnessMonitoringInterval = monitoringInterval
  }

  function stopConsciousnessMonitoring() {
    isMonitoring.value = false
    
    if (window.consciousnessMonitoringInterval) {
      clearInterval(window.consciousnessMonitoringInterval)
      window.consciousnessMonitoringInterval = null
    }
    
    console.log('🌙 Consciousness Monitoring Stopped')
  }

  function updateSacredMetrics() {
    // Calculate consciousness score based on various factors
    sacredMetrics.consciousnessScore = calculateConsciousnessScore()
    
    // Update synchronicity frequency
    sacredMetrics.synchronicityFrequency = calculateSynchronicityFrequency()
    
    // Update Gene Keys alignment
    sacredMetrics.geneKeysAlignment = Math.random() * 100 // TODO: Real calculation
    
    // Update sacred geometry resonance
    sacredMetrics.sacredGeometryResonance = calculateSacredGeometryResonance()
    
    // Update fibonacci harmony
    sacredMetrics.fibonacciHarmony = calculateFibonacciHarmony()
    
    lastUpdate.value = new Date()
    
    // Persist to localStorage
    localStorage.setItem('syntopia_sacred_metrics', JSON.stringify(sacredMetrics))
  }

  function calculateConsciousnessScore(): number {
    // Sacred algorithm combining various consciousness factors
    const baseScore = averageSacredMetrics.value
    const synchronicityBonus = synchronicityEvents.value.length * 2
    const timeBonus = Math.sin(Date.now() / 1000) * 10 // Sacred time wave
    
    return Math.min(100, Math.max(0, baseScore + synchronicityBonus + timeBonus))
  }

  function calculateSynchronicityFrequency(): number {
    const recentEvents = synchronicityEvents.value.filter(
      event => new Date().getTime() - new Date(event.timestamp).getTime() < 24 * 60 * 60 * 1000
    )
    return recentEvents.length
  }

  function calculateSacredGeometryResonance(): number {
    // Golden ratio based calculation
    const goldenRatio = 1.618
    const currentTime = Date.now() / 1000
    const goldenWave = Math.sin(currentTime / goldenRatio) * 50 + 50
    
    return goldenWave
  }

  function calculateFibonacciHarmony(): number {
    // Fibonacci sequence harmony calculation
    const fibonacci = [1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89]
    const currentMinute = new Date().getMinutes()
    const fibIndex = currentMinute % fibonacci.length
    
    return (fibonacci[fibIndex] / 89) * 100
  }

  function recordSynchronicityEvent(event: Omit<SynchronicityEvent, 'id' | 'timestamp'>) {
    const synchronicityEvent: SynchronicityEvent = {
      id: crypto.randomUUID(),
      timestamp: new Date().toISOString(),
      ...event
    }
    
    synchronicityEvents.value.push(synchronicityEvent)
    
    // Keep only last 100 events
    if (synchronicityEvents.value.length > 100) {
      synchronicityEvents.value = synchronicityEvents.value.slice(-100)
    }
    
    // Persist to localStorage
    localStorage.setItem('syntopia_synchronicity_events', JSON.stringify(synchronicityEvents.value))
    
    console.log('🌟 Synchronicity Recorded:', synchronicityEvent.type)
  }

  function elevateConsciousnessLevel() {
    const levels: ConsciousnessLevel[] = ['AWAKENING', 'EXPANDING', 'INTEGRATING', 'EMBODYING', 'MASTERING']
    const currentIndex = levels.indexOf(currentLevel.value)
    
    if (currentIndex < levels.length - 1) {
      currentLevel.value = levels[currentIndex + 1]
      localStorage.setItem('syntopia_consciousness_level', currentLevel.value)
      
      recordSynchronicityEvent({
        type: 'CONSCIOUSNESS_ELEVATION',
        description: `Consciousness elevated to ${currentLevel.value}`,
        significance: 'HIGH'
      })
      
      console.log('🚀 Consciousness Elevated to:', currentLevel.value)
    }
  }

  function resetConsciousness() {
    currentLevel.value = 'AWAKENING'
    synchronicityEvents.value = []
    Object.keys(sacredMetrics).forEach(key => {
      sacredMetrics[key as keyof SacredMetrics] = 0
    })
    
    localStorage.removeItem('syntopia_consciousness_level')
    localStorage.removeItem('syntopia_sacred_metrics')
    localStorage.removeItem('syntopia_synchronicity_events')
    
    console.log('🔄 Consciousness Reset')
  }

  return {
    // State
    currentLevel,
    synchronicityEvents,
    sacredMetrics,
    isMonitoring,
    lastUpdate,
    
    // Getters
    consciousnessPercentage,
    averageSacredMetrics,
    recentSynchronicities,
    
    // Actions
    initializeSacredConsciousness,
    startConsciousnessMonitoring,
    stopConsciousnessMonitoring,
    updateSacredMetrics,
    recordSynchronicityEvent,
    elevateConsciousnessLevel,
    resetConsciousness,
    
    // API Actions
    checkBackendHealth,
    createUserProfile
  }
})

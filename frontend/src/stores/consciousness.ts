import { defineStore } from 'pinia'
import { ref, computed, reactive } from 'vue'
import type { ConsciousnessLevel, SynchronicityEvent, SacredMetrics } from '@/types/consciousness'

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

      lastUpdate.value = new Date()
      console.log('✨ Sacred Consciousness Initialized')
    } catch (error) {
      console.error('🔴 Consciousness Initialization Error:', error)
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
    resetConsciousness
  }
})
